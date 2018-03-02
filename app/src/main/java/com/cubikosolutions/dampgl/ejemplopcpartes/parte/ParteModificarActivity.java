package com.cubikosolutions.dampgl.ejemplopcpartes.parte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cubikosolutions.dampgl.ejemplopcpartes.R;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.G;
import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.Utilidades;
import com.cubikosolutions.dampgl.ejemplopcpartes.pojos.Parte;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.Contrato;
import com.cubikosolutions.dampgl.ejemplopcpartes.proveedor.ParteProveedor;

import java.io.FileNotFoundException;

// import android.widget.Toolbar;

public class ParteModificarActivity extends AppCompatActivity {

    EditText editTextParteFecha;
    EditText editTextParteCliente;
    EditText editTextParteMotivo;
    EditText editTextParteResolucion;
    int parteId;

    ImageView imageViewParte;
    Bitmap foto = null;

    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;

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

        parteId = this.getIntent().getExtras().getInt(Contrato.Parte._ID);
        Parte parte = ParteProveedor.readRecord(getContentResolver(),parteId);

        editTextParteFecha.setText(parte.getFecha());
        editTextParteCliente.setText(parte.getCliente());
        editTextParteMotivo.setText(parte.getMotivo());
        editTextParteResolucion.setText(parte.getResolucion());

        imageViewParte = (ImageView) findViewById(R.id.image_view_parte);

        try {
            Utilidades.loadImageFromStorage(this, "img_" + parteId + ".jpg",imageViewParte );
            foto = ((BitmapDrawable)imageViewParte.getDrawable()).getBitmap();
        } catch (FileNotFoundException e) {
            // No existe imagen para este ciclo.
        }


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
        EditText editTextParteFecha = (EditText) findViewById(R.id.editTextParteFecha);
        EditText editTextParteCliente = (EditText) findViewById(R.id.editTextParteCliente);
        EditText editTextParteMotivo = (EditText) findViewById(R.id.editTextParteMotivo);
        EditText editTextParteResolucion = (EditText) findViewById(R.id.editTextParteResolucion);

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

        Parte parte = new Parte(parteId, fecha, cliente, motivo, resolucion);
        ParteProveedor.updateRecordConBitacora(getContentResolver(),parte);
        finish();

    }
}
