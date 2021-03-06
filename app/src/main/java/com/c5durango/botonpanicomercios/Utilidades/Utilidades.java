package com.c5durango.botonpanicomercios.Utilidades;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utilidades {

    static String TAG = "Utilidades";

    public static String obtenerFecha(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date()); // Salida:  2019-10-28 15:24:55
    }

    public static String convertirImgString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);


        return imagenString;
    }

    public static String convertirAudioString(String pathAudio) {

        String audioString = "";
        byte[] audioBytes;
        try {
            // Log.d(TAG, "El peso del archivo es: " + new File(pathAudio).length());
            // 25 KB aprox para 15 segundos

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(pathAudio));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
            audioBytes = baos.toByteArray();

            audioString = Base64.encodeToString(audioBytes, Base64.DEFAULT);

        } catch (Exception e) {
            Log.d(TAG, "Ocurrió un error al codificar audio a Base64");
        }
        return audioString;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
