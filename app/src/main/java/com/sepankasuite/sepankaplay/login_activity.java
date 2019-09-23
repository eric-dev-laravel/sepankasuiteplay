package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import static com.sepankasuite.sepankaplay.DataBaseManager.pathLogin;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_acceder, btn_registrarme;
    private EditText et_user, et_password;

    private String SERVER_URL = "http://test.rally.sepankasuite.com/";
    private String SERVER_PATH_GENERIC_METHODS = "checklogin/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

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
                String url = SERVER_URL + SERVER_PATH_GENERIC_METHODS;

                //En caso de no conectar con el servidor se muestra este msg
                //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

                //Creamos los parametros que mandaremos al php,json,java.etc.
                RequestParams params1 = new RequestParams();
                params1.put("user", et_user.getText());
                params1.put("password", et_password.getText());
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
