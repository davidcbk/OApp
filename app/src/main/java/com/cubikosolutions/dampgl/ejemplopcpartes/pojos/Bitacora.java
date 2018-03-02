package com.cubikosolutions.dampgl.ejemplopcpartes.pojos;

import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;

/**
 * Created by Administrador on 13/01/2018.
 */

public class Bitacora {
    int ID;
    int ID_Parte;
    int operacion;

    public Bitacora() {
        this.ID = G.SIN_VALOR_INT;
        this.ID_Parte = G.SIN_VALOR_INT;
        this.operacion = G.SIN_VALOR_INT;
    }

    public Bitacora(int ID, int ID_Parte, int operacion) {
        this.ID = ID;
        this.ID_Parte = ID_Parte;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Parte() {
        return ID_Parte;
    }

    public void setID_Parte(int ID_Parte) {
        this.ID_Parte = ID_Parte;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
