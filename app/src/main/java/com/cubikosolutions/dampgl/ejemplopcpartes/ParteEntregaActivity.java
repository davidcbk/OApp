package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ParteEntregaActivity extends Activity implements OnClickListener {

    private Button btnGuardar;
    private EditText txtHora, txtFecha,txtCliente,txtMaterial;
    private CheckBox cbPrestamo,cbFacturable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_entrega);

        txtHora = (EditText) findViewById(R.id.txtHora);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtCliente = (EditText) findViewById(R.id.txtCliente);
        txtMaterial = (EditText) findViewById(R.id.txtMaterial);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        cbFacturable = (CheckBox) findViewById(R.id.cbFacturable);
        cbPrestamo = (CheckBox) findViewById(R.id.cbPrestamo);

        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String hora = txtHora.getText().toString();
        String fecha = txtFecha.getText().toString();
        String cliente = txtCliente.getText().toString();
        String material = txtMaterial.getText().toString();

        switch (v.getId()) {
            case R.id.btnGuardar:
                if (((hora != null) || (hora.length()!= 0) || (fecha != null) || (fecha.length()!= 0)  || (cliente != null) || (cliente.length()!= 0) || (material != null) || (material.length()!= 0)) && ((cbPrestamo.isChecked()) || (cbFacturable.isChecked()))){
                    Toast.makeText(this, "Parte completo", Toast.LENGTH_LONG).show();
                    Intent tipoparte = new Intent(this, com.cubikosolutions.dampgl.ejemplopcpartes.MainActivity.class);
                    startActivity(tipoparte);
                } else {
                    Toast.makeText(this, "Parte incompleto", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }

    }


}
