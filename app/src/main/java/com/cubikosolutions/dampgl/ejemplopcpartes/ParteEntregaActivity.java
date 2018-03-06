package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import harmony.java.awt.Color;

public class ParteEntregaActivity extends Activity implements OnClickListener {


    private Button btnCrearPDFEntrega;
    private EditText  txtPresupuesto,txtCliente,txtMaterial;
    private CheckBox cbPrestamo,cbFacturable;
    private final static String NOMBRE_PDF_ENTREGA = "ParteEntrega";
    private final static String ETIQUETA_ERROR = "ERROR";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_entrega);

        // Permisos.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {
        }


        txtPresupuesto = (EditText) findViewById(R.id.txtPresupuesto);
        txtCliente = (EditText) findViewById(R.id.txtCliente);
        txtMaterial = (EditText) findViewById(R.id.txtMaterial);
        btnCrearPDFEntrega = (Button) findViewById(R.id.btnCrearPDFEntrega);
        cbFacturable = (CheckBox) findViewById(R.id.cbFacturable);
        cbPrestamo = (CheckBox) findViewById(R.id.cbPrestamo);

        btnCrearPDFEntrega.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        String presupuesto = txtPresupuesto.getText().toString();
        String cliente =("Cliente: " + txtCliente.getText().toString());
        String material = ("Entregado: " + txtMaterial.getText().toString());


        String seleccionado = null;
        if (cbFacturable.isChecked()) {
            seleccionado = "facturable";
        }else {
            if (cbPrestamo.isChecked()){
                seleccionado = "prestamo";
            }
        }
        String titulo = "PARTE DE ENTREGA";
        String textocompleto = "Fecha: " +obtenerfechacompleta() + "\n" + cliente + "\n" + material + "\n" + "El material es: " +seleccionado;

        // Creamos el documento.
        Document documento = new Document();
        String nombrefichero = (NOMBRE_PDF_ENTREGA + presupuesto + ".pdf");

        String rutacompleta=Environment.getExternalStorageDirectory() + "/" + nombrefichero;

        try {

            PdfWriter.getInstance(documento, new FileOutputStream(rutacompleta));

            // HEADER Y FOOTER
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "OUp! - Parte de entrega de material"), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    "OUp! (c) 2018"), false);
            documento.setHeader(cabecera);
            documento.setFooter(pie);
            // Abrimos el documento.
            documento.open();


            // A�adimos un t�tulo
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 24,
                    Font.BOLD, Color.black);
            Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 32,
                    Font.BOLD, Color.black);
            documento.add(new Paragraph(titulo, font1));
            documento.add(new Paragraph(textocompleto, font));



        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {

            // Cerramos el documento.
            documento.close();
            Toast.makeText(getApplicationContext(), "Parte creado correctamente", Toast.LENGTH_SHORT).show();
            mostrarPdf(rutacompleta, this);
        }
    }


    public static  String obtenerfechacompleta (){

        String obtenerfechacompleta = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(new Date());
        return obtenerfechacompleta;
    }

    public void mostrarPdf(String archivo, Context contexto) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            contexto.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No hay programa para mostrar PDF", Toast.LENGTH_SHORT).show();
        }


    }

}


