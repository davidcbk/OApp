package com.cubikosolutions.dampgl.ejemplopcpartes.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.Utilidades;
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Bitacora;
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Parte;

import java.io.IOException;
import java.util.ArrayList;


public class ParteProveedor {
    static public Uri insertRecord(ContentResolver resolver, Parte parte){

        Uri uri = Contrato.Parte.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Parte.FECHA, parte.getFecha());
        values.put(Contrato.Parte.CLIENTE, parte.getCliente());
        values.put(Contrato.Parte.MOTIVO, parte.getMotivo());
        values.put(Contrato.Parte.RESOLUCION, parte.getResolucion());

       return resolver.insert(uri, values);
    }

    public static void insertRecordConBitacora(ContentResolver resolver, Parte parte){
       Uri uri = insertRecord(resolver,parte);
       parte.setID(Integer.parseInt(uri.getLastPathSegment()));


       Bitacora bitacora = new Bitacora();
       bitacora.setID_Parte(parte.getID());
       bitacora.setOperacion(G.OPERACION_INSERTAR);

       BitacoraProveedor.insertRecord(resolver,bitacora);

    }



    static public void deleteRecord(ContentResolver resolver, int parteId){
        Uri uri = Uri.parse(Contrato.Parte.CONTENT_URI + "/" + parteId);
        resolver.delete(uri, null, null);
    }

    public static void deleteRecordConBitacora(ContentResolver resolver, int parteId){
        deleteRecord(resolver,parteId);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Parte(parteId);
        bitacora.setOperacion(G.OPERACION_BORRAR);

        BitacoraProveedor.insertRecord(resolver,bitacora);

    }

    static public void updateRecord(ContentResolver resolver, Parte parte){
        Uri uri = Uri.parse(Contrato.Parte.CONTENT_URI + "/" + parte.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Parte.FECHA, parte.getFecha());
        values.put(Contrato.Parte.CLIENTE, parte.getCliente());
        values.put(Contrato.Parte.MOTIVO, parte.getMotivo());
        values.put(Contrato.Parte.RESOLUCION, parte.getResolucion());

        resolver.update(uri, values, null,null);
    }

    public static void updateRecordConBitacora(ContentResolver resolver, Parte parte){
        updateRecord(resolver,parte);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_Parte(parte.getID());
        bitacora.setOperacion(G.OPERACION_MODIFICAR);

        BitacoraProveedor.insertRecord(resolver,bitacora);

    }


    static public Parte readRecord(ContentResolver resolver,int parteId){
        Uri uri = Uri.parse(Contrato.Parte.CONTENT_URI + "/" + parteId);
        String[] projection = {
                Contrato.Parte._ID,
                Contrato.Parte.FECHA,
                Contrato.Parte.CLIENTE,
                Contrato.Parte.MOTIVO,
                Contrato.Parte.RESOLUCION
        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            Parte parte = new Parte();
            parte.setID(parteId);
            parte.setFecha(cursor.getString(cursor.getColumnIndex(Contrato.Parte.FECHA)));
            parte.setCliente(cursor.getString(cursor.getColumnIndex(Contrato.Parte.CLIENTE)));
            parte.setMotivo(cursor.getString(cursor.getColumnIndex(Contrato.Parte.MOTIVO)));
            parte.setResolucion(cursor.getString(cursor.getColumnIndex(Contrato.Parte.RESOLUCION)));

            return parte;

        }

        return null;
    }

    static public ArrayList<Parte> readAllRecord(ContentResolver resolver){
        Uri uri = Contrato.Parte.CONTENT_URI;

        String[] projection = {
                Contrato.Parte._ID,
                Contrato.Parte.FECHA,
                Contrato.Parte.CLIENTE,
                Contrato.Parte.MOTIVO,
                Contrato.Parte.RESOLUCION


        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Parte> partes = new ArrayList<>();
        Parte parte;

        while(cursor.moveToNext()){
            parte = new Parte();

            parte.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Parte._ID)));
            parte.setFecha(cursor.getString(cursor.getColumnIndex(Contrato.Parte.FECHA)));
            parte.setCliente(cursor.getString(cursor.getColumnIndex(Contrato.Parte.CLIENTE)));
            parte.setMotivo(cursor.getString(cursor.getColumnIndex(Contrato.Parte.MOTIVO)));
            parte.setResolucion(cursor.getString(cursor.getColumnIndex(Contrato.Parte.RESOLUCION)));


            partes.add(parte);

        }

        return partes;
    }
}
