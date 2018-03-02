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

public class ParteAsistenciaActivity extends Activity implements OnClickListener {

    private Button btnGuardar;
    private EditText txtHora, txtFecha,txtCliente,txtIncidencia;
    private CheckBox cbMantenimiento,cbFacturable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte_asistencia);

        txtHora = (EditText) findViewById(R.id.txtHora);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtCliente = (EditText) findViewById(R.id.txtCliente);
        txtIncidencia = (EditText) findViewById(R.id.txtIncidencia);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        cbFacturable = (CheckBox) findViewById(R.id.cbFacturable);
        cbMantenimiento = (CheckBox) findViewById(R.id.cbMantenimiento);

        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String hora = txtHora.getText().toString();
        String fecha = txtFecha.getText().toString();
        String cliente = txtCliente.getText().toString();
        String incidencia = txtIncidencia.getText().toString();

        switch (v.getId()) {
            case R.id.btnGuardar:
                if (((hora != null) || (hora.length()!= 0) || (fecha != null) || (fecha.length()!= 0)  || (cliente != null) || (cliente.length()!= 0) || (incidencia != null) || (incidencia.length()!= 0)) && ((cbMantenimiento.isChecked()) || (cbFacturable.isChecked()))){
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
