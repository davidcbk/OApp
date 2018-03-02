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


public class IncidenciasFragment extends Fragment {
    EditText asunto;
    EditText observacion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_incidencias, container, false);
        Button btnIncidencia = (Button) v.findViewById(R.id.btnIncidencia);
        asunto = (EditText) v.findViewById(R.id.txtAsunto);
        observacion = (EditText) v.findViewById(R.id.txtObservacion);
        btnIncidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        return v;
    }

    private void validar(){
        asunto.setError(null);
        observacion.setError(null);

        String asunto1 = asunto.getText().toString();
        String observacion1 = observacion.getText().toString();

        if(TextUtils.isEmpty(asunto1)){
            asunto.setError(getString(R.string.Campo_obligatorio));
            asunto.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(observacion1)){
            observacion.setError(getString(R.string.Campo_obligatorio));
            observacion.requestFocus();
            return;
        }

        Toast.makeText(getActivity(),"Datos introducidos correctamente",Toast.LENGTH_LONG).show();
    }

}
