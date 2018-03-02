package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends Activity implements OnClickListener {

    private Button btnRegistrar;
    private EditText txtEmail, txtContrasena,txtUsuario;
    private TextView vwLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        vwLogin = (TextView) findViewById(R.id.vwLogin);

        vwLogin.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = txtEmail.getText().toString();
        String contrasena = txtContrasena.getText().toString();
        String usuario = txtUsuario.getText().toString();

        switch (v.getId()) {
            case R.id.btnRegistrar:
                if (email == null || email.length()== 0 || contrasena == null || contrasena.length()== 0  || usuario == null || usuario.length()== 0   ){
                    Toast.makeText(this, "Registro incorrecto", Toast.LENGTH_LONG).show();
                    Intent registro = new Intent(this,RegistroActivity.class);
                    startActivity(registro);
                } else {
                    Toast.makeText(this, "Registro correcto", Toast.LENGTH_LONG).show();
                    Intent login = new Intent(this,LoginActivity.class);
                    startActivity(login);
                }
                break;
            case R.id.vwLogin:
                Toast.makeText(this, "Ir a login", Toast.LENGTH_LONG).show();
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                break;

            default:
                break;
        }


    }


}