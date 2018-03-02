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
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Bitacora;

import java.io.IOException;
import java.util.ArrayList;


public class BitacoraProveedor {

   public static void insertRecord(ContentResolver resolver, Bitacora Bitacora){

        Uri uri = Contrato.Bitacora.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_PARTE, Bitacora.getID_Parte());
        values.put(Contrato.Bitacora.OPERACION, Bitacora.getOperacion());


        Uri uriResultado = resolver.insert(uri, values);
       String BitacoraId = uriResultado.getLastPathSegment();

    }



    static public void deleteRecord(ContentResolver resolver, int BitacoraId){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + BitacoraId);
        resolver.delete(uri, null, null);
    }

    static public void updateRecord(ContentResolver resolver, Bitacora Bitacora, Context contexto){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + Bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_PARTE, Bitacora.getID_Parte());
        values.put(Contrato.Bitacora.OPERACION, Bitacora.getOperacion());

        resolver.update(uri, values, null,null);
    }

    static public Bitacora readRecord(ContentResolver resolver,int BitacoraId){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + BitacoraId);
        String[] projection = {
                Contrato.Bitacora.ID_PARTE,
                Contrato.Bitacora.OPERACION,

        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            Bitacora Bitacora = new Bitacora();

            Bitacora.setID(BitacoraId);
            Bitacora.setID_Parte(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_PARTE)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));


            return Bitacora;

        }

        return null;
    }

    static public ArrayList<Bitacora> readAllRecord(ContentResolver resolver){
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        String[] projection = {
                Contrato.Bitacora._ID,
                Contrato.Bitacora.ID_PARTE,
                Contrato.Bitacora.OPERACION,

        };

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Bitacora> bitacoras = new ArrayList<>();
        Bitacora bitacora;

        while(cursor.moveToNext()){
            bitacora = new Bitacora();

            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora._ID)));
            bitacora.setID_Parte(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_PARTE)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));


            bitacoras.add(bitacora);

        }

        return bitacoras;
    }
}
