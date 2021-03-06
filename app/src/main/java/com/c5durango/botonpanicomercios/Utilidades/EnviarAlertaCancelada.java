package com.c5durango.botonpanicomercios.Utilidades;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.c5durango.botonpanicomercios.Constantes;
import com.c5durango.botonpanicomercios.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class EnviarAlertaCancelada
{

    static String TAG = "AlertaCancelada";

    public static void enviarAlertaCancelada(final Context context, int id_reporte_cancelar, int nuevo_estatus){

        // final Boolean seCancelo = false;

        StringRequest requestAlertaCancelada;
        String URL = Constantes.URL + "/activaciones/" + id_reporte_cancelar;

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("estatus", nuevo_estatus);
        }catch (JSONException e){
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
        final String requestBody = jsonObject.toString();
        requestAlertaCancelada = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "La respuesta al cancelar reporte es: " + response);
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorResp = "Error #9: " + R.string.error_desconocido;

                if (error instanceof TimeoutError) {
                    errorResp = "Error #9: " + R.string.error_tiempo_agotado;
                } else if (error instanceof NoConnectionError) {
                    errorResp = "Error #9: " + R.string.error_sin_conexion;
                } else if (error instanceof AuthFailureError) {
                    errorResp = "Error #9: " + R.string.error_fallo_autenticar;
                } else if (error instanceof ServerError) {
                    errorResp = "Error #9: " + R.string.error_servidor;
                } else if (error instanceof NetworkError) {
                    errorResp = "Error #9: " + R.string.error_red;
                } else if (error instanceof ParseError) {
                    errorResp = "Error #9: " + R.string.error_parseo;
                }

                // Toast.makeText(context, errorResp, Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorResp);
                requestQueue.stop();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Codificación no compatible al intentar obtener los bytes de% s usando %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        requestQueue.add(requestAlertaCancelada);
        //return seCancelo;
    }
}
