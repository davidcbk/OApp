package com.cubikosolutions.dampgl.ejemplopcpartes.constantes;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrador on 12/11/2017.
 */

public class Utilidades {
    public static void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img) throws FileNotFoundException{
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);
    }

    public static void storeImage (Bitmap image, Context contexto, String fileName) throws IOException{
        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }

    public static File crearCarpetaApp() {
        File carpetaAPP;
        carpetaAPP = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + "OUP");
        if (!carpetaAPP.exists()) {
            carpetaAPP.mkdir();
            return carpetaAPP;
        }

        return null;
    }

    public static  void permisoEscritura( Activity activity){
        int permissionCheck = ContextCompat.checkSelfPermission
                (activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        }
    }



}
