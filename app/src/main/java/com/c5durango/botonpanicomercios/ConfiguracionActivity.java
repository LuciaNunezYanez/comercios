package com.c5durango.botonpanicomercios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.c5durango.botonpanicomercios.Servicios.ServicioNotificacion;
import com.c5durango.botonpanicomercios.Utilidades.PreferencesCiclo;
import com.c5durango.botonpanicomercios.Utilidades.Utilidades;

public class ConfiguracionActivity extends AppCompatActivity {

    private Switch switchServicioActivo;
    private Boolean isActive = false;
    private ImageButton btnSaveCiclo;
    private EditText txtNoCiclo;
    private String TAG = "Configuracion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        switchServicioActivo = findViewById(R.id.switchServicioActivo);
        btnSaveCiclo = findViewById(R.id.iBtnGuardarCiclo);
        txtNoCiclo = findViewById(R.id.txtNoCiclo);

        obtenerPreferenciasNotificacion();
        switchServicioActivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    iniciarServicioPersistente();
                } else {
                    detenerServicioPersistente();
                }
            }
        });

        obtenerPreferenciasCiclo();
        btnSaveCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (txtNoCiclo.getText()== null || Integer.parseInt(txtNoCiclo.getText().toString()) <= 0 ){
                        Toast.makeText(getApplication(), "¡Por favor ingrese un valor válido!" , Toast.LENGTH_SHORT).show();
                    } else if ( Integer.parseInt(txtNoCiclo.getText().toString()) > 3  ){
                        Toast.makeText(getApplication(), "¡Por favor ingrese un valor entre 1 y 3!" , Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(txtNoCiclo.getText().toString()) >= 1 && Integer.parseInt(txtNoCiclo.getText().toString()) <= 3){
                        guardarPreferenciasCiclo(Integer.parseInt(txtNoCiclo.getText().toString()));
                    }
                }catch (Exception e){
                    Toast.makeText(getApplication(), "¡Por favor ingrese un valor válido!" , Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void obtenerPreferenciasNotificacion(){
        SharedPreferences preferences = getApplication().getSharedPreferences("NotificacionPersistente", Context.MODE_PRIVATE);
        if (preferences.contains("notificacionActiva")){
            isActive = Utilidades.isMyServiceRunning(getApplication(), ServicioNotificacion.class);
            switchServicioActivo.setChecked(isActive);
        } else {
            isActive = Utilidades.isMyServiceRunning(getApplication(), ServicioNotificacion.class);
            switchServicioActivo.setChecked(isActive);
        }
    }

    private void obtenerPreferenciasCiclo(){
        PreferencesCiclo preferencesCiclo = new PreferencesCiclo();
        int ciclos = preferencesCiclo.obtenerCicloFotografias(getApplication());
        txtNoCiclo.setText(String.valueOf(ciclos));
    }

    private void guardarPreferenciasCiclo(int ciclo){
        PreferencesCiclo preferencesCiclo = new PreferencesCiclo();
        Boolean res = preferencesCiclo.guardarCicloFotografias(getApplication(), ciclo);
        if(res){
            Toast.makeText(getApplication(), "¡Número de ciclos guardados con éxito!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplication(), "¡Error al guardar el número de ciclos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarServicioPersistente(){
        Intent notificationIntent = new Intent(getApplication(), ServicioNotificacion.class);
        notificationIntent.putExtra("padre", "App");
        getApplication().startService(notificationIntent);
        isActive = Utilidades.isMyServiceRunning(getApplication(), ServicioNotificacion.class);
        actualizarPreferenciasNotificacion(isActive);
    }

    public void detenerServicioPersistente(){
        Intent notificationIntent = new Intent(getApplication(), ServicioNotificacion.class);
        getApplication().stopService(notificationIntent);
        isActive = Utilidades.isMyServiceRunning(getApplication(), ServicioNotificacion.class);
        actualizarPreferenciasNotificacion(isActive);
    }

    private void actualizarPreferenciasNotificacion(boolean nuevoValor){
        SharedPreferences preferences = getApplication().getSharedPreferences("NotificacionPersistente", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("notificacionActiva", nuevoValor);
        editor.commit();
    }


}