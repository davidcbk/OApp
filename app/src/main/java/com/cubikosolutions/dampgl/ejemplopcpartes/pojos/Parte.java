package com.cubikosolutions.dampgl.ejemplopcpartes.pojos;

import android.graphics.Bitmap;

import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;

/**
 * Created by Administrador on 01/11/2017.
 */

public class Parte {
    private int ID;
    private String fecha;
    private String cliente;
    private String motivo;
    private String resolucion;
    private Bitmap imagen;


    public Parte (){
        this.ID = G.SIN_VALOR_INT;
        this.fecha = G.SIN_VALOR_STRING;
        this.cliente = G.SIN_VALOR_STRING;
        this.motivo = G.SIN_VALOR_STRING;
        this.resolucion = G.SIN_VALOR_STRING;
        this.setImagen(null);
    }

    public Parte(int ID, String fecha, String cliente, String motivo, String resolucion) {
        this.ID = ID;
        this.fecha = fecha;
        this.cliente = cliente;
        this.motivo = motivo;
        this.resolucion = resolucion;
        this.imagen = imagen;
    }


    public Bitmap getImagen() {

        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getID() { return ID;}

    public void setID (int ID){ this.ID = ID;}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }
}
