package com.cubikosolutions.dampgl.ejemplopcpartes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cubikosolutions.dampgl.ejemplopcpartes.constantes.Utilidades;
import com.cubikosolutions.dampgl.ejemplopcpartes.parte.ParteActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;
    ImageView imageViewImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Permisos.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1000);
        } else {
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Button btnCrear = (Button) findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent selcrear = new Intent (getApplicationContext(), Deslizante1.class); VA A SLICE
                Intent selcrear = new Intent (getApplicationContext(), ParteEntregaActivity.class);
                startActivity(selcrear);
            }
        });


        Button btnRellenar = (Button) findViewById(R.id.btnRellenar);
        btnRellenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selrellenar = new Intent (getApplicationContext(), ParteActivity.class);
                startActivity(selrellenar);
            }
        });



    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
     //   getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ayuda) {
            Intent intent = new Intent(getApplicationContext(),Ayuda.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //      finish();

        } else if (id == R.id.nav_info) {
            Intent intentInfo = new Intent (getApplicationContext(), Acercade.class);
            startActivity (intentInfo);

        } else if (id == R.id.nav_partes){

            Intent intentPartes = new Intent (getApplicationContext(), ParteActivity.class );
            startActivity (intentPartes);

          //  Toast.makeText(getApplicationContext(), "NO IMPLEMENTADO", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {

            final Button boton_salida = (Button)findViewById(R.id.nav_logout);
            boton_salida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();

            //        Intent intent = new Intent(Intent.ACTION_MAIN);
            //        intent.addCategory(Intent.CATEGORY_HOME);
            //        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //        startActivity(intent);

                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
    }

    void elegirDeGaleria (){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PETICION_SACAR_FOTO:
            if (resultCode==RESULT_OK){
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                imageViewImagen.setImageBitmap(foto);
                try {
                    Utilidades.storeImage (foto, this, "imagen.jpg");
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error: No se pudo guardar la imagen",Toast.LENGTH_LONG).show();
                }


            }else{
                //El usuario hizo clic en cancelar.
            }
            break;
            case PETICION_GALERIA:
                if (resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    imageViewImagen.setImageURI(uri);
                }else{
                    //El usuario hizo clic en cancelar.
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
