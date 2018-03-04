package com.cubikosolutions.dampgl.ejemplopcpartes.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Bitacora;
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Parte;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.BitacoraProveedor;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.ParteProveedor;
import com.cubikosolutions.dampgl.ejemplopcpartes.volley.ParteVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Sincronizacion {
    private static final String LOGTAG = "PartesOnline - Sincronizacion";
    private static ContentResolver resolvedor;
    private static Context contexto;
    private static boolean esperandoRespuestaDeServidor = false;

    public Sincronizacion(Context contexto){
        this.resolvedor = contexto.getContentResolver();
        this.contexto = contexto;
        recibirActualizacionesDelServidor(); //La primera vez se cargan los datos siempre
    }

    public synchronized static boolean isEsperandoRespuestaDeServidor() {
        return esperandoRespuestaDeServidor;
    }

    public synchronized static void setEsperandoRespuestaDeServidor(boolean esperandoRespuestaDeServidor) {
        Sincronizacion.esperandoRespuestaDeServidor = esperandoRespuestaDeServidor;
    }

    public synchronized boolean sincronizar(){
        Log.i("sincronizacion","SINCRONIZAR");

        if(isEsperandoRespuestaDeServidor()){
            return true;
        }

        if(G.VERSION_ADMINISTRADOR){
            enviarActualizacionesAlServidor();
        } else {
            recibirActualizacionesDelServidor();
        }

        return true;
    }



    private static void enviarActualizacionesAlServidor(){
        ArrayList<Bitacora> registrosBitacora = BitacoraProveedor.readAllRecord(resolvedor);
        for(Bitacora bitacora : registrosBitacora){

            switch(bitacora.getOperacion()){
                case G.OPERACION_INSERTAR:
                    Parte parte = null;
                    try {
                        parte = ParteProveedor.readRecord(resolvedor, bitacora.getID_Parte());
                        ParteVolley.addParte(parte, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_MODIFICAR:
                    try {
                        parte = ParteProveedor.readRecord(resolvedor, bitacora.getID_Parte());
                        ParteVolley.updateParte(parte, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_BORRAR:
                    ParteVolley.delParte(bitacora.getID_Parte(), true, bitacora.getID());
                    break;
            }
            Log.i("sincronizacion", "acabo de enviar");
        }
    }

    private static void recibirActualizacionesDelServidor(){
        ParteVolley.getAllParte();
    }

    public static void realizarActualizacionesDelServidorUnaVezRecibidas(JSONArray jsonArray){
        Log.i("sincronizacion", "recibirActualizacionesDelServidor");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<Parte> registrosNuevos = new ArrayList<>();
            ArrayList<Parte> registrosViejos = ParteProveedor.readAllRecord(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
            for(Parte i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++ ){
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Parte(obj.getInt("PK_ID"), obj.getString("fecha"), obj.getString("cliente"), obj.getString("motivo"), obj.getString("resolucion")));
            }

            for(Parte parte: registrosNuevos) {
                try {
                    if(identificadoresDeRegistrosViejos.contains(parte.getID())) {
                        ParteProveedor.updateRecord(resolvedor, parte);
                    } else {
                        ParteProveedor.insertRecord(resolvedor, parte);
                    }
                    identificadoresDeRegistrosActualizados.add(parte.getID());
                } catch (Exception e){
                    Log.i("sincronizacion",
                            "Probablemente el registro ya existía en la BD."+"" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
            }

            for(Parte parte: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(parte.getID())){
                    try {
                        ParteProveedor.deleteRecord(resolvedor, parte.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + parte.getID());
                    }
                }
            }

            ParteVolley.getAllParte(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
