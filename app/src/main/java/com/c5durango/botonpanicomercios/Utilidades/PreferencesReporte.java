package com.c5durango.botonpanicomercios.Utilidades;

import android.content.Context;
import android.util.Log;

import com.c5durango.botonpanicomercios.Constantes;

public class PreferencesReporte {

    // ULTIMO REPORTE
    // Métodos para administrar la información del ultimo reporte generado

    private static String TAG = "PreferencesReporte";
    public static Boolean guardarReporteInicializado(Context context) {
        Boolean seGuardo;
        try{
            android.content.SharedPreferences preferences = context.getSharedPreferences("UltimoReporte", Context.MODE_PRIVATE);
            android.content.SharedPreferences.Editor editor = preferences.edit();

            editor.putLong("fechaUltimoReporte", System.currentTimeMillis());
            editor.putInt("ultimoReporte", 0);
            editor.putBoolean("estatusReporte", false);
            editor.commit();
            seGuardo = true;
        } catch (Exception e){
            seGuardo = false;
        }
        return seGuardo;
    }

    // Antes de generar un nuevo reporte es necesario validar
    // si existe uno pendiente para así no duplicar alertas.
    public static Boolean puedeEnviarReporte(Context context, Long fechaHoraActual){
        Boolean enviar;
        android.content.SharedPreferences preferences = context.getSharedPreferences("UltimoReporte", Context.MODE_PRIVATE);

        if (preferences.contains("estatusReporte")){
            Boolean estatusUltimo = preferences.getBoolean("estatusReporte", false);
            Long fechaUltReporte = preferences.getLong("fechaUltimoReporte", 0);
            Long diferenciaMilisegundos = fechaHoraActual - fechaUltReporte;

            // Obtener el ultimo reporte generado
            int ult_reporte_generado = preferences.getInt("ultimoReporte", 0);

            //Log.d(TAG, "El ultimo reporte es: " + preferences.getInt("", 0 ));
            //Log.d(TAG, "La diferencia es: " + diferenciaMilisegundos);
            //Log.d(TAG, "Estatus del ultimo: " + estatusUltimo);


            if( estatusUltimo ){
                if(diferenciaMilisegundos >= Constantes.DIFERENCIA_ENTRE_REPORTES){
                    enviar = true;
                } else {

                    enviar = false;
                    Log.d(TAG, "Supuestamente se genero incremento en el botonazo");

                    // La presión del botón se toma como el mismo reporte y solo se agrega un incremento
                    if(ult_reporte_generado >= 1){
                        EnviarBotonazo enviarBotonazo = new EnviarBotonazo();
                        enviarBotonazo.enviarBotonazo(context, ult_reporte_generado, Utilidades.obtenerFecha());
                    } else{
                        Log.d(TAG, "El ultimo reporte es <=1 por lo cuál no se puede generar incremento");
                    }

                }
            } else {
                // ¿El ultimo reporte que no se generó fue hace mas de 1 minuto?
                if(diferenciaMilisegundos >= Constantes.DIFERENCIA_ENTRE_REPORTES){
                    enviar = true;
                } else {
                    enviar = true;
                }
            }
        } else {
            enviar = true;
        }
        return enviar;
    }

    // Una vez que el reporte se haya generado es necesario
    // modificar el ultimo reporte como enviado.
    public static Boolean actualizarUltimoReporte(Context context, int reporteCreado){
        Boolean seActualizo;

        try{
            android.content.SharedPreferences preferences = context.getSharedPreferences("UltimoReporte", Context.MODE_PRIVATE);
            android.content.SharedPreferences.Editor editor = preferences.edit();

            if (reporteCreado != 0 ){
                editor.putLong("fechaUltimoReporte", System.currentTimeMillis());
                editor.putInt("ultimoReporte", reporteCreado);
                editor.putBoolean("estatusReporte", true);
                editor.commit();
                seActualizo = true;
            } else {
                seActualizo = false;
            }
        } catch (Exception e){
            seActualizo = false;
        }
        return seActualizo;
    }

    public static Boolean puedeCancelarAlerta(Context context, Long fechaHoraActual){
        android.content.SharedPreferences preferences = context.getSharedPreferences("UltimoReporte", Context.MODE_PRIVATE);
        if(preferences.contains("estatusReporte")){
            Boolean estatusUltimo = preferences.getBoolean("estatusReporte", false);
            Long fechaUltReporte = preferences.getLong("fechaUltimoReporte", 0);
            Long diferenciaMilisegundos = fechaHoraActual - fechaUltReporte;

            // Si ha pasado menos de X tiempo y el reporte fue enviado
            if (diferenciaMilisegundos <= Constantes.LAPSO_PARA_CANCELAR_REPORTE && estatusUltimo == true){
                //Puede cancelar
                //Log.d(TAG, "Puede cancelar");
                return true;
            } else {
                //Ya no puede cancelar
                //Log.d(TAG, "Ya no puede cancelar");
                return false;
            }
        } else {
            // No hay ningún reporte que cancelar
            //Log.d(TAG, "No hay ningún reporte que cancelar");
            return false;
        }

    }

}
