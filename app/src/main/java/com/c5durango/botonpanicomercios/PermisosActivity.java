package com.c5durango.botonpanicomercios;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class PermisosActivity extends AppCompatActivity {

    ImageButton btnAlmacWrite, btnCam, btnMicrof, btnUbic;
    String TAG = "Permisos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);
        btnAlmacWrite = findViewById(R.id.btnAlmacenamientoWrite);
        btnCam = findViewById(R.id.btnCamara);
        btnMicrof = findViewById(R.id.btnMicrofono);
        btnUbic = findViewById(R.id.btnUbicacion);

        // Detectar permisos
        permisoAlmacWrite();
        permisoCam();
        permisoMicrof();
        permisoUbic();
    }


    private void permisoAlmacWrite(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            btnAlmacWrite.setBackgroundColor(Color.rgb(65, 174, 71));
        } else {
            btnAlmacWrite.setBackgroundColor(Color.rgb(213, 34, 37));
            Log.e(TAG, "No tiene permisos Write");
        }
    }

    private void permisoCam(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            btnCam.setBackgroundColor(Color.rgb(65, 174, 71));
        } else {
            btnCam.setBackgroundColor(Color.rgb(213, 34, 37));
            Log.e(TAG, "No tiene permisos camara");
        }
    }

    private void permisoMicrof(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            btnMicrof.setBackgroundColor(Color.rgb(65, 174, 71));
        } else {
            btnMicrof.setBackgroundColor(Color.rgb(213, 34, 37));
            Log.e(TAG, "No tiene permisos microfono");
        }
    }

    private void permisoUbic(){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            btnUbic.setBackgroundColor(Color.rgb(65, 174, 71));
        } else {
            btnUbic.setBackgroundColor(Color.rgb(213, 34, 37));
            Log.e(TAG, "No tiene permisos ubicacion");
        }
    }

    public void activarPermisoAlmacWrite(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.MY_PERMISSIONS_REQUEST_ALMAC_WRITE);
    }

    public void activarPermisoCam(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constantes.MY_PERMISSIONS_REQUEST_CAMERA);
    }

    public void activarPermisoMicrof(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, Constantes.MY_PERMISSIONS_REQUEST_MICROF);
    }

    public void activarPermisoUbic(View view){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.MY_PERMISSIONS_REQUEST_UBICAC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constantes.MY_PERMISSIONS_REQUEST_ALMAC_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "PERMISO DE ESCRITURA ACEPTADO");
                    permisoAlmacWrite();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Permiso de escritura denegado!", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "PERMISO DE CAMARA ACEPTADO");
                    permisoCam();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Permiso de cámara denegado!", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_MICROF: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "PERMISO DE MICROFONO ACEPTADO");
                    permisoMicrof();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Permiso de micrófono denegado!", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_UBICAC: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "PERMISO DE UBICACION ACEPTADO");
                    permisoUbic();
                } else {
                    Toast.makeText(getApplicationContext(), "¡Permiso de ubicación denegado!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
