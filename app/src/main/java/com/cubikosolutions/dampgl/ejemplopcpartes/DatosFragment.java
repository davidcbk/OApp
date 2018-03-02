package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DatosFragment extends Fragment {
    EditText fecha;
    EditText urgencia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_datos, container, false);
        Button btnDatos = (Button) v.findViewById(R.id.btnDatos);
        fecha = (EditText) v.findViewById(R.id.txtFecha);
        urgencia = (EditText) v.findViewById(R.id.txtUrgencia);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        return v;
    }

    private void validar(){
        fecha.setError(null);
        urgencia.setError(null);

        String fecha1 = fecha.getText().toString();
        String urgencia1 = urgencia.getText().toString();

        if(TextUtils.isEmpty(fecha1)){
            fecha.setError(getString(R.string.Campo_obligatorio));
            fecha.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(urgencia1)){
            urgencia.setError(getString(R.string.Campo_obligatorio));
            urgencia.requestFocus();
            return;
        }

        Toast.makeText(getActivity(),"Datos incidencia introducido correctamente.",Toast.LENGTH_LONG).show();
    }

}

