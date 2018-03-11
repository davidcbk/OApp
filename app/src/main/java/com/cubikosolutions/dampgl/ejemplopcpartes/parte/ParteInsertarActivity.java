package com.cubikosolutions.dampgl.ejemplopcpartes.parte;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
// import android.widget.Toolbar;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.Utilidades;

import com.cubikosolutions.dampgl.ejemplopcpartes.R;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;

import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Parte;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.ParteProveedor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import harmony.java.awt.Color;

public class ParteInsertarActivity extends AppCompatActivity {

    EditText editTextParteFecha;
    EditText editTextParteCliente;
    EditText editTextParteMotivo;
    EditText editTextParteResolucion;


    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;

    Bitmap foto = null;

    ImageView imageViewParte;

    private final static String NOMBRE_PDF_ENTREGA = "ParteAsistencia";
    private final static String ETIQUETA_ERROR = "ERROR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_detalle);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalle_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextParteFecha = (EditText) findViewById(R.id.editTextParteFecha);
        editTextParteCliente = (EditText) findViewById(R.id.editTextParteCliente);
        editTextParteMotivo = (EditText) findViewById(R.id.editTextParteMotivo);
        editTextParteResolucion = (EditText) findViewById(R.id.editTextParteResolucion);

        imageViewParte = (ImageView) findViewById(R.id.image_view_parte);


        ImageButton imageButtonCamara = (ImageButton) findViewById(R.id.image_button_camara);
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacarFoto();
            }
        });


        ImageButton imageButtonGaleria = (ImageButton) findViewById(R.id.image_button_galeria);
        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegirDeGaleria();
            }
        });
   // }

    ImageButton imageButtonPDF = (ImageButton) findViewById(R.id.image_button_pdf);
        imageButtonPDF.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            validar();
        }
    });

}


    //valida si tiene todos los campos rellenos
    private void validar(){

        String tiempo = editTextParteFecha.getText().toString();
        String cliente = editTextParteCliente.getText().toString();
        String motivo = editTextParteMotivo.getText().toString();
        String resolucion = editTextParteResolucion.getText().toString();

        if ((TextUtils.isEmpty(tiempo)) | (TextUtils.isEmpty(cliente))| (TextUtils.isEmpty(motivo))| (TextUtils.isEmpty(resolucion))){
            Toast.makeText(this,"Debe rellenar todos los campos para crear parte",Toast.LENGTH_LONG).show();
        }else{
            generarPDF();
        }
    }

//genera el pdf con los datos
     void generarPDF(){
        String tiempo = ("Tiempo empleado: " + editTextParteFecha.getText().toString() + " horas.");
        String cliente =("Cliente: " + editTextParteCliente.getText().toString());
        String motivo = ("Motivo incidencia: " + editTextParteMotivo .getText().toString());
        String resolucion = ("Resolucion incidencia: " + editTextParteResolucion .getText().toString());

        String titulo = "PARTE DE ASISTENCIA TÉCNICA";
        String textocompleto = "Fecha parte: " +Utilidades.obtenerfechacompleta() + "\n" + cliente + "\n" + motivo + "\n" + resolucion + "\n"+ tiempo;

        // Creamos el documento.
        Document documento = new Document();
        String nombrefichero = (NOMBRE_PDF_ENTREGA + cliente  + ".pdf");

        String rutacompleta= Environment.getExternalStorageDirectory() + "/" + nombrefichero;

        try {

            PdfWriter.getInstance(documento, new FileOutputStream(rutacompleta));

            // HEADER Y FOOTER
            HeaderFooter cabecera = new HeaderFooter(new Phrase(
                    "OUp! - Parte de asistencia técnica"), false);
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
            Utilidades.mostrarPdf(rutacompleta, this);
        }

    }

    void elegirDeGaleria (){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }


    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PETICION_SACAR_FOTO:
                if (resultCode == RESULT_OK) {
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewParte.setImageBitmap(foto);

                } else {
                    //El usuario hizo clic en cancelar.
                }
                break;
            case PETICION_GALERIA:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageViewParte.setImageURI(uri);
                    foto = ((BitmapDrawable) imageViewParte.getDrawable()).getBitmap();
                } else {
                    //El usuario hizo clic en cancelar.
                }
                break;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_action_guardar);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.GUARDAR:
                attemptGuardar ();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar() {
  //      EditText editTextParteFecha = (EditText) findViewById(R.id.editTextParteFecha);
  //      EditText editTextParteCliente = (EditText) findViewById(R.id.editTextParteCliente);
   //     EditText editTextParteMotivo = (EditText) findViewById(R.id.editTextParteMotivo);
    //    EditText editTextParteResolucion = (EditText) findViewById(R.id.editTextParteResolucion);

        editTextParteFecha.setError(null);
        editTextParteCliente.setError(null);
        editTextParteMotivo.setError(null);
        editTextParteResolucion.setError(null);

        String fecha =  String.valueOf(editTextParteFecha.getText());
        String cliente =  String.valueOf(editTextParteCliente.getText());
        String motivo =  String.valueOf(editTextParteMotivo.getText());
        String resolucion =  String.valueOf(editTextParteResolucion.getText());

        if (TextUtils.isEmpty(fecha)){
            editTextParteFecha.setError(getString(R.string.campo_requerido));
            editTextParteFecha.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cliente)){
            editTextParteCliente.setError(getString(R.string.campo_requerido));
            editTextParteCliente.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(motivo)){
            editTextParteMotivo.setError(getString(R.string.campo_requerido));
            editTextParteMotivo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(resolucion)){
            editTextParteResolucion.setError(getString(R.string.campo_requerido));
            editTextParteResolucion.requestFocus();
            return;
        }


        Parte parte = new Parte(G.SIN_VALOR_INT, fecha, cliente, motivo, resolucion);
        ParteProveedor.insertRecordConBitacora(getContentResolver(),parte);
        finish();

    }
}
