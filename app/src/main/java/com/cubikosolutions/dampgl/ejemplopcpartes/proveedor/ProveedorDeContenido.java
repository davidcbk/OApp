package com.cubikosolutions.dampgl.ejemplopcpartes.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;

public class ProveedorDeContenido extends ContentProvider {


    private static final int PARTE_ONE_REG = 1;
    private static final int PARTE_ALL_REGS = 2;

    private static final int BITACORA_ONE_REG = 3;
    private static final int BITACORA_ALL_REGS = 4;


    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "PartesOnline.db";
    private static final int DATABASE_VERSION = 310;

    private static final String PARTE_TABLE_NAME = "Parte"; // nombre de la tabla
    private static final String BITACORA_TABLE_NAME = "Bitacora"; // nombre de la tabla



    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                PARTE_TABLE_NAME,
                PARTE_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                PARTE_TABLE_NAME + "/#",
                PARTE_ONE_REG);


        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                BITACORA_TABLE_NAME,
                BITACORA_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                BITACORA_TABLE_NAME + "/#",
                BITACORA_ONE_REG);


        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                PARTE_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + PARTE_TABLE_NAME);
        sMimeTypes.put(
                PARTE_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + PARTE_TABLE_NAME);

        sMimeTypes.put(
                BITACORA_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + BITACORA_TABLE_NAME);
        sMimeTypes.put(
                BITACORA_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + BITACORA_TABLE_NAME);



    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial

           // db.execSQL("PRAGMA foreign_keys=ON;");
           // }
        }
        // Añadimos esto para resolver el problema de la integridad referencial ya que a partir de la API 16 el método ha cambiado
        @Override
        public void onConfigure(SQLiteDatabase db){
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                            + PARTE_TABLE_NAME
                            + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                            + Contrato.Parte.FECHA + " TEXT , "
                            + Contrato.Parte.CLIENTE + " TEXT , "
                            + Contrato.Parte.MOTIVO + " TEXT , "
                            + Contrato.Parte.RESOLUCION + " TEXT ); "
            );


            db.execSQL("Create table "
                    + BITACORA_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.Bitacora.ID_PARTE + " INTEGER , "
                    + Contrato.Bitacora.OPERACION + " INTEGER ); "
            );


           // inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

                db.execSQL("INSERT INTO " + PARTE_TABLE_NAME + " (" +  Contrato.Parte._ID + "," + Contrato.Parte.FECHA + "," + Contrato.Parte.CLIENTE+ "," + Contrato.Parte.MOTIVO + "," + Contrato.Parte.RESOLUCION + ") " +
                    "VALUES (1,'01/11/17','Cubiko Solutions','Implementacion Servidor','Actualizaciones')");
                db.execSQL("INSERT INTO " + PARTE_TABLE_NAME + " (" +  Contrato.Parte._ID + "," + Contrato.Parte.FECHA + "," + Contrato.Parte.CLIENTE+ "," + Contrato.Parte.MOTIVO + "," + Contrato.Parte.RESOLUCION + ") " +
                    "VALUES (2,'02/11/17','Cubiko Solutions','Implementacion Servidor 2','Creacion dominio')");
                db.execSQL("INSERT INTO " + PARTE_TABLE_NAME + " (" +  Contrato.Parte._ID + "," + Contrato.Parte.FECHA + "," + Contrato.Parte.CLIENTE+ "," + Contrato.Parte.MOTIVO + "," + Contrato.Parte.RESOLUCION + ") " +
                    "VALUES (3,'01/11/17','VM Consulting','Impresora no imprime','Reinstalacion')");
                db.execSQL("INSERT INTO " + PARTE_TABLE_NAME + " (" +  Contrato.Parte._ID + "," + Contrato.Parte.FECHA + "," + Contrato.Parte.CLIENTE+ "," + Contrato.Parte.MOTIVO + "," + Contrato.Parte.RESOLUCION + ") " +
                    "VALUES (4,'04/11/17','L7VE Magazine','Actualizacion BD','Actualiza BD')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + PARTE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + BITACORA_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenido() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case PARTE_ALL_REGS:
                table = PARTE_TABLE_NAME;
                break;

            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Fallo al insertar fila en " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // UsuarioProveedor record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case PARTE_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Parte._ID + " = "
                        + uri.getLastPathSegment();
                table = PARTE_TABLE_NAME;
                break;
            case PARTE_ALL_REGS:
                table = PARTE_TABLE_NAME;
                break;

            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TABLE_NAME;
                break;
            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;

        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Fallo al insertar fila en " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case PARTE_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Parte._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(PARTE_TABLE_NAME);
                break;
            case PARTE_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Parte._ID + " ASC";
                qb.setTables(PARTE_TABLE_NAME);
                break;

            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(BITACORA_TABLE_NAME);
                break;
            case BITACORA_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Bitacora._ID + " ASC";
                qb.setTables(BITACORA_TABLE_NAME);
                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                        sortOrder);
       // c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // UsuarioProveedor record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case PARTE_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Parte._ID + " = "
                        + uri.getLastPathSegment();
                table = PARTE_TABLE_NAME;
                break;
            case PARTE_ALL_REGS:
                table = PARTE_TABLE_NAME;
                break;

            case BITACORA_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Bitacora._ID + " = "
                        + uri.getLastPathSegment();
                table = BITACORA_TABLE_NAME;
                break;
            case BITACORA_ALL_REGS:
                table = BITACORA_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to updateRecord row into " + uri);
    }
}
