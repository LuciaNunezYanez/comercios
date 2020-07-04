package com.c5durango.botonpanicomercios;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.c5durango.botonpanicomercios.Utilidades.Utilidades;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;



public class RegistroActivity extends AppCompatActivity {

    String TAG = "Registro";

    private Button btnIniciarSesion;
    private EditText codigo;
    private Boolean tieneAcceso = false;

    int id_comercio = 0;
    int id_dir_comercio;
    int num_empleados;
    String nombre_comercio;
    String giro;
    String telefono_fijo;
    String folio_com;
    String razon_social;
    String calle;
    String numero;
    String colonia;
    int cp;
    String entre_calle_1;
    String entre_calle_2;
    String fachada;
    int id_localidad;
    String nombre_localidad;
    String nombre_municipio;
    String nombre_estado;

    int id_usuarios_app;
    String nombres_usuarios_app;
    String apell_pat;
    String apell_mat;
    String fecha_nacimiento;
    String sexo_app;
    String padecimientos;
    String tel_movil;
    String alergias;
    String tipo_sangre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        codigo = findViewById(R.id.userNickName);
        btnIniciarSesion = findViewById(R.id.setNickName);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BigInteger codigo_activacion = new BigInteger(codigo.getText().toString());
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                //1578347038755
                String URL = Constantes.URL + "/codigoactivacion";

                JSONObject jsonObjectBody = new JSONObject();
                try {
                    jsonObjectBody.put("codigo_activacion", codigo_activacion);
                    jsonObjectBody.put("fecha_apertura", Utilidades.obtenerFecha());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                }
                Log.d(TAG, URL);

                final String requestBody = jsonObjectBody.toString();
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                Log.d(TAG, "Respuesta:" + response.toString());

                                String json = response.toString();
                                JSONObject object = null;
                                JSONObject object_comercio = null;
                                try {
                                    object = new JSONObject(json);
                                    Boolean ok = object.getBoolean("ok");
                                    if (ok) {
                                        Log.d(TAG, object.toString());
                                        if (object.has("resultado")) {

                                            final String resultado = object.getString("resultado");
                                            if (resultado.equals("Código de activación abierto con éxito")) {
                                                Toast.makeText(getApplicationContext(), "¡BIENVENIDO! \n" + resultado, Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                                            }

                                        }

                                        String token = "S";
                                        if(object.has("token")){
                                            token = object.getString("token");
                                        }

                                        if (object.has("comercio") &&
                                                object.getString("resultado").equals("Código de activación abierto con éxito")) {
                                            // Obtener cada dato de comercio
                                            object_comercio = object.optJSONObject("comercio");

                                            id_comercio = object_comercio.getInt("id_comercio");
                                            id_dir_comercio = object_comercio.getInt("id_dir_comercio");
                                            num_empleados = object_comercio.getInt("num_empleados");
                                            nombre_comercio = object_comercio.getString("nombre_comercio");
                                            giro = object_comercio.getString("giro");
                                            telefono_fijo = object_comercio.getString("telefono_fijo");
                                            folio_com = object_comercio.getString("folio_comercio");
                                            razon_social = object_comercio.getString("razon_social");
                                            calle = object_comercio.getString("calle");
                                            numero = object_comercio.getString("numero");
                                            colonia = object_comercio.getString("colonia");
                                            cp = object_comercio.getInt("cp");
                                            entre_calle_1 = object_comercio.getString("entre_calle_1");
                                            entre_calle_2 = object_comercio.getString("entre_calle_2");
                                            fachada = object_comercio.getString("fachada");
                                            id_localidad = object_comercio.getInt("id_localidad");
                                            nombre_localidad = object_comercio.getString("nombre_localidad");
                                            nombre_municipio = object_comercio.getString("nombre_municipio");
                                            nombre_estado = object_comercio.getString("nombre_estado");

                                            id_usuarios_app = object_comercio.getInt("id_usuarios_app");
                                            nombres_usuarios_app = object_comercio.getString("nombres_usuarios_app");
                                            apell_pat = object_comercio.getString("apell_pat");
                                            apell_mat = object_comercio.getString("apell_mat");
                                            fecha_nacimiento = object_comercio.getString("fecha_nacimiento");
                                            sexo_app = object_comercio.getString("sexo_app");
                                            padecimientos = object_comercio.getString("padecimientos");
                                            tel_movil = object_comercio.getString("tel_movil");
                                            alergias = object_comercio.getString("alergias");
                                            tipo_sangre = object_comercio.getString("tipo_sangre");

                                            if (id_comercio != 0) {
                                                Boolean resp = com.c5durango.botonpanicomercios.Utilidades.PreferencesComercio.guardarDatosComercio(getApplicationContext(),
                                                        id_comercio,
                                                        id_dir_comercio,
                                                        num_empleados,
                                                        nombre_comercio,
                                                        giro,
                                                        telefono_fijo,
                                                        folio_com,
                                                        razon_social,
                                                        calle,
                                                        numero,
                                                        colonia,
                                                        cp,
                                                        entre_calle_1,
                                                        entre_calle_2,
                                                        fachada,
                                                        id_localidad,
                                                        nombre_localidad,
                                                        nombre_municipio,
                                                        nombre_estado,
                                                        id_usuarios_app,
                                                        nombres_usuarios_app,
                                                        apell_pat,
                                                        apell_mat,
                                                        fecha_nacimiento,
                                                        sexo_app,
                                                        padecimientos,
                                                        tel_movil,
                                                        alergias,
                                                        tipo_sangre,
                                                        token);

                                                Log.d(TAG, "La respuesta al guardar es: " + resp);
                                                // (Toast.makeText(getApplicationContext(), "La respuesta al guardar es: " + resp, Toast.LENGTH_SHORT)).show();
                                                if (resp) {
                                                    tieneAcceso = true;
                                                } else {
                                                    Log.d(TAG, "No se pudieron guardar los datos");
                                                    (Toast.makeText(getApplicationContext(), "Error al guardar la información", Toast.LENGTH_SHORT)).show();
                                                }
                                            } else {
                                                Log.d(TAG, "No se pudieron obtener los datos");
                                                (Toast.makeText(getApplicationContext(), "Error al obtener la información", Toast.LENGTH_SHORT)).show();
                                            }
                                        } else if (object.getString("resultado").equals("Código de activación abierto con éxito")) {
                                            Log.d(TAG, "NO viene comercio");
                                            (Toast.makeText(getApplicationContext(), "Información de comercio inválida", Toast.LENGTH_SHORT)).show();
                                        } else {
                                            // No viene comercio por que el codigo está mal
                                        }

                                    } else {
                                        Log.d(TAG, "Error al traer los datos" + object.optJSONObject("error").toString());
                                        (Toast.makeText(getApplicationContext(), "Error al traer los datos" + object.optJSONObject("error").toString(), Toast.LENGTH_SHORT)).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (tieneAcceso) {
                                    activarPermisoAlmacWrite();
                                } else {
                                    //(Toast.makeText(getApplicationContext(), "Acceso denegado #1", Toast.LENGTH_SHORT)).show();
                                }
                                requestQueue.stop();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d(TAG, );
                                Log.e(TAG, error.toString());

                                if (error instanceof TimeoutError) {
                                    (Toast.makeText(getApplicationContext(), "Timeout", Toast.LENGTH_SHORT)).show();
                                } else if (error instanceof NoConnectionError) {
                                    (Toast.makeText(getApplicationContext(), "Sin conexión", Toast.LENGTH_SHORT)).show();
                                } else if (error instanceof AuthFailureError) {
                                    (Toast.makeText(getApplicationContext(), "Falló al autenticar", Toast.LENGTH_SHORT)).show();
                                } else if (error instanceof ServerError) {
                                    (Toast.makeText(getApplicationContext(), "Error de servidor", Toast.LENGTH_SHORT)).show();
                                } else if (error instanceof NetworkError) {
                                    (Toast.makeText(getApplicationContext(), "Error de Red", Toast.LENGTH_SHORT)).show();
                                } else if (error instanceof ParseError) {
                                    (Toast.makeText(getApplicationContext(), "Error de parseo", Toast.LENGTH_SHORT)).show();
                                } else {
                                    (Toast.makeText(getApplicationContext(), "Acceso denegado #2", Toast.LENGTH_SHORT)).show();
                                }
                            }
                        }
                ) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Codificación no compatible al intentar obtener los bytes de% s usando %s", requestBody, "utf-8");
                            return null;
                        }
                    }
                };
                requestQueue.add(getRequest);
            }
        });
    }

    private void iniciarMain(){
        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void activarPermisoAlmacWrite(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.MY_PERMISSIONS_REQUEST_ALMAC_WRITE);
    }

    private void activarPermisoCam(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constantes.MY_PERMISSIONS_REQUEST_CAMERA);
    }

    private void activarPermisoMicrof(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, Constantes.MY_PERMISSIONS_REQUEST_MICROF);
    }

    private void activarPermisoUbic(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.MY_PERMISSIONS_REQUEST_UBICAC);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constantes.MY_PERMISSIONS_REQUEST_ALMAC_WRITE: {
                activarPermisoCam();
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_CAMERA: {
                activarPermisoMicrof();
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_MICROF: {
                activarPermisoUbic();
                return;
            }
            case Constantes.MY_PERMISSIONS_REQUEST_UBICAC: {
                iniciarMain();
                return;
            }
        }
    }
}
