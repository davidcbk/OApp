package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity  {

    private Button btnLogin;
    private EditText txtEmail, txtContrasena;
    JSONArray ja;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContrasena = (EditText) findViewById(R.id.txtContrasena);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConsultaContrasena("http://192.168.1.91:8082/oup/consulta.php?email=" + txtEmail.getText().toString());

            }
        });

    }

    private void ConsultaContrasena(String URL) {

        Log.i("url",""+URL);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    ja = new JSONArray(response);
                    String contrasena = ja.getString(0);
                    if(contrasena.equals(txtContrasena.getText().toString())){

                        Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), com.cubikosolutions.dampgl.ejemplopcpartes.MainActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getApplicationContext(),"Verifique su contraseña",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(),"El usuario no existe en la base de datos",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);



    }
}


    /*

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
}*/