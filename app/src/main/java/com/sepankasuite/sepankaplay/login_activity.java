package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_acceder, btn_registrarme;

    private String SERVER_URL = "http://test.rally.sepankasuite.com/";
    private String SERVER_PATH_GENERIC_METHODS = "/user/metodos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        btn_acceder = findViewById(R.id.btn_login_acceder);
        btn_registrarme = findViewById(R.id.btn_login_registrarme);

        btn_acceder.setOnClickListener(this);
        btn_registrarme.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_acceder:
                Toast.makeText(this, "parametros usuario y contrasela", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_login_registrarme:
                //Creamos una instancia de la otra ventana
                Intent intent1 = new Intent(this, signup_activity.class);
                //Mandamos paramentros a la siguiente ventana
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
        }
    }

    //Clase para descargar datos desde el servidor
    public void ObjDatos(String UID){
        //Creamos un nuevo cliente de conexion
        AsyncHttpClient client = new AsyncHttpClient();

        //Definimos la URL a donde se conectara
        String url = SERVER_URL + SERVER_PATH_GENERIC_METHODS;

        //En caso de no conectar con el servidor se muestra este msg
        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), UID, Toast.LENGTH_SHORT).show();

        //Creamos los parametros que mandara al php, json, etc
        RequestParams params = new RequestParams();
        params.put("idNFC", UID);
        //Ejecutamos conexion tipo POST para envio de parametros
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //El codigo 200 significa que se genero correctamente
                if (statusCode == 200) {
                    //Log.d("tipoUser", String.valueOf(tipoUsuario));
                    //Mandamos los datos que regreso el server al metodo que los interpreta
                    /*manager.obtDatosJSON(new String(responseBody));
                    String name;
                    String apaterno;
                    String nCompleto;
                    if(tipoUsuario == 1){
                        cNameRunner = manager.selectDataNameRunner();
                        if (cNameRunner.moveToLast()){
                            do {
                                name = cNameRunner.getString(0);
                                apaterno = cNameRunner.getString(1);
                            } while (cNameRunner.moveToNext());
                        } else {
                            name = "sin";
                            apaterno = "datos";
                        }
                        nCompleto = name +" " + apaterno;
                        String saludo = cadena1+" "+nCompleto+", "+cadena2+" "+cadenaFecha2;

                        //Mandar al intent con los datos descargados
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(CuentaRegresivaActivity.this, DatosSencillosActivity.class);
                        //Mandamos paramentros a la siguiente ventana
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    } else if (tipoUsuario == 2) {
                        //Mandar al intent con los datos descargados
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(CuentaRegresivaActivity.this, DatosActivity.class);
                        //Mandamos paramentros a la siguiente ventana
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }*/
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //mostramos msg si no se pudo conectar
                Toast.makeText(getApplicationContext(), "Imposible conectar con el servidor.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
