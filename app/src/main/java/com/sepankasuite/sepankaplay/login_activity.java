package com.sepankasuite.sepankaplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_acceder, btn_registrarme;
    private EditText et_user, et_password;

    //Variable de instancia de clase
    DataBaseManager manager;

    private String SERVER_URL = "http://test.rally.sepankasuite.com/";
    private String SERVER_PATH_GENERIC_METHODS = "checklogin/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        //Creamos una nueva instancia de la clase para conectar con servidor
        manager = new DataBaseManager(this);

        btn_acceder = findViewById(R.id.btn_login_acceder);
        btn_registrarme = findViewById(R.id.btn_login_registrarme);

        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_acceder.setOnClickListener(this);
        btn_registrarme.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_acceder:
                //Toast.makeText(this, "parametros usuario y contrasela", Toast.LENGTH_LONG).show();
                //Creamos un nuevo cliente de conexion
                AsyncHttpClient client = new AsyncHttpClient();
                //Definimos la URL a la cual sera dirigidio
                //String url = SERVER_URL + SERVER_PATH_GENERIC_METHODS + "soporte@hallmg.com/" + "Rally2019$/";
                String url = SERVER_URL + SERVER_PATH_GENERIC_METHODS + et_user.getText().toString() +"/" + et_password.getText().toString() +"/";

                //En caso de no conectar con el servidor se muestra este msg
                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();

                //Creamos los parametros que mandaremos al php,json,java.etc.
                //RequestParams params1 = new RequestParams();
                //params1.put("u", et_user.getText());
                //params1.put("p", et_password.getText());
                //params1.put("/", "soporte@hallmg.com");
                //params1.put("/", "Rally2019$");

                //Log.d("parametros", url);

                //Ejecutamos peticion POST para envio de parametros
                client.post(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //Log.d("serverAnswer", new String(responseBody));
                        //Mostramos el msg en caso de que el Login no sea correcto
                        //Toast.makeText(getApplicationContext(), "Conexion establecida", Toast.LENGTH_LONG).show();
                        if(statusCode == 200){
                            boolean access = manager.obtDatosJSONLogin(new String(responseBody));
                            if(access == true) {
                                //Creamos una instancia de la otra ventana
                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                //Mandamos paramentros a la siguiente ventana
                                //Nos aseguramos de cerrar las ventanas activas o que no se
                                //repitan si es que ya esta abiertas
                                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            } else {
                                //En caso de no conectar con el servidor se muestra este msg
                                Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos. Verifique sus datos", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            //En caso de no conectar con el servidor se muestra este msg
                            Toast.makeText(getApplicationContext(), "Ocurrio un detalle al intentar conectar. Code: "+statusCode, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //En caso de no conectar con el servidor se muestra este msg
                        Toast.makeText(getApplicationContext(), "Imposible conectar con el servidor.", Toast.LENGTH_SHORT).show();
                        //Log.d("errorServer", String.valueOf(statusCode));
                        //Log.d("errorServer", String.valueOf(error));
                    }
                });
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
}
