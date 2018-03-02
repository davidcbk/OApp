package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TecnicoFragment extends Fragment {
    EditText tecnico;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tecnico, container, false);
        Button btnTecnico = (Button) v.findViewById(R.id.btnTecnico);
        tecnico = (EditText) v.findViewById(R.id.txtTecnico);
        btnTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        return v;
    }

    private void validar(){
        tecnico.setError(null);
        String date = tecnico.getText().toString();
        if(TextUtils.isEmpty(date)){
            tecnico.setError(getString(R.string.Campo_obligatorio));
            tecnico.requestFocus();
            return;
        }

        Toast.makeText(getActivity(),"Tecnico asignado correctamente a la incidencia",Toast.LENGTH_LONG).show();
    }



}