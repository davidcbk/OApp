package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cubikosolutions.dampgl.ejemplopcpartes.parte.ParteActivity;

public class TipoParteActivity extends Activity implements OnClickListener {

    private Button btnRellenar;
    private RadioButton rdbEntrega, rdbTrabajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_parte);

        rdbEntrega = (RadioButton) findViewById(R.id.rdbEntrega);
        rdbTrabajo = (RadioButton) findViewById(R.id.rdbTrabajo);
        btnRellenar = (Button) findViewById(R.id.btnRellenar);

        btnRellenar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRellenar:
                if (rdbEntrega.isChecked()){
                    Toast.makeText(this, "Ir a parte de entrega", Toast.LENGTH_LONG).show();
                    Intent entrega = new Intent(this,ParteActivity.class);
                    startActivity(entrega);
                } else {
                    if (rdbTrabajo.isChecked()){
                        Toast.makeText(this, "Ir a parte de asistencia tecnica", Toast.LENGTH_LONG).show();
                        Intent asistencia = new Intent(this,ParteActivity.class);
                        startActivity(asistencia);
                    }

                }
                break;

            default:
                break;
        }

    }


}
