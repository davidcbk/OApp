package com.cubikosolutions.dampgl.ejemplopcpartes.constantes;

/**
 * Created by Administrador on 02/11/2017.
 */

public class G {

    public static final String RUTA_SERVIDOR = "http://192.168.1.90:8080/PartesOnline/webresources";
    public static final int SYNC_INTERVAL = 60; // no se puede poner menos de 60 segundos
    public static final boolean VERSION_ADMINISTRADOR = true; //true es admin y false solo consultas
    public static final int SIN_VALOR_INT = -1;
    public static final String SIN_VALOR_STRING = "";

    public static final int INSERTAR = 1;
    public static final int GUARDAR = 2;

    public static final int OPERACION_INSERTAR = 1;
    public static final int OPERACION_MODIFICAR = 2;
    public static final int OPERACION_BORRAR = 3;

}
