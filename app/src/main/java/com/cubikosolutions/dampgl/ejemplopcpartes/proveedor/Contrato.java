package com.cubikosolutions.dampgl.ejemplopcpartes.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contrato {

    public static final String AUTHORITY = "com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.ProveedorDeContenido";

    public static final class Parte implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Parte");

        // Table column
        public static final String FECHA = "Fecha";
        public static final String CLIENTE = "Cliente";
        public static final String MOTIVO = "Motivo";
        public static final String RESOLUCION = "Resolucion";
    }

    public static final class Bitacora implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Bitacora");

        // Table column
        public static final String ID_PARTE = "ID_Parte";
        public static final String OPERACION = "Operacion";
    }
}
