package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btnLogin;
    private EditText txtEmail, txtContrasena;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        String email = txtEmail.getText().toString();
        String contrasena = txtContrasena.getText().toString();

        switch (v.getId()) {
            case R.id.btnLogin:
                if (email.equals("dmedina") && contrasena.equals("Prueba")) {
                    Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show();
                    Intent selpartes = new Intent(this, com.cubikosolutions.dampgl.ejemplopcpartes.MainActivity.class);
                    startActivity(selpartes);
                } else {
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_LONG).show();
                    Intent errorlogin = new Intent(this, LoginActivity.class);
                    startActivity(errorlogin);
                }
                break;

            default:
                break;
        }

    }
}