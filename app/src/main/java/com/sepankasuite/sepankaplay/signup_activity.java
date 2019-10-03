package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.regex.Pattern;

public class signup_activity extends AppCompatActivity implements View.OnClickListener {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;

    EditText et_name, et_idarioac, et_email, et_password1, et_password2;
    //Generamos las variables globales
    private Button btn_acceder, btn_registrarse;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_signup_activity);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        et_name = findViewById(R.id.et_name_user_resgister);
        et_idarioac = findViewById(R.id.et_id_arioac_resgister);
        et_email = findViewById(R.id.et_email_resgiter);
        et_password1 = findViewById(R.id.et_password1_resgister);
        et_password2 = findViewById(R.id.et_password2_register);

        //Enlazamos las variables con los objetos fisicos
        btn_acceder = findViewById(R.id.btn_signup_acceder);
        btn_registrarse = findViewById(R.id.btn_signup_registrarse);

        //Asignamos el evento alos botones al dar clic
        btn_acceder.setOnClickListener(this);
        btn_registrarse.setOnClickListener(this);


    }

    //Este metodo sirve para asignar funciones al dar clic sobre un boton
    @Override
    public void onClick(View v) {
        //Recuperamos el id del elemento al que fue dado clic
        switch (v.getId()){
            case R.id.btn_signup_acceder:
                //Creamos una instancia de la otra ventana
                Intent intent1 = new Intent(this, login_activity.class);
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                //Cerramos el activity actual para no dejarlo en cola
                finish();
                break;

            case R.id.btn_signup_registrarse:
                if (et_name.length() != 0) {
                    if (et_idarioac.length() != 0){
                        if (et_email.length() != 0){
                            if (!validarEmail(et_email.getText().toString())){
                                et_email.setError("Email no válido");
                            } else {
                                if (et_password1.length() != 0){
                                    if (et_password2.length() != 0) {
                                        if (et_password1.getText().toString().equals( et_password2.getText().toString())) {
                                            //Aqui va el codigo para dar se de alta
                                            //Toast.makeText(this, "parametros usuario y contrasela", Toast.LENGTH_LONG).show();
                                            //Creamos un nuevo cliente de conexion
                                            AsyncHttpClient client = new AsyncHttpClient();
                                            //Definimos la URL a la cual sera dirigidio y recuperamos los datos de las cajas de texto
                                            String url = manager.SERVER_URL + manager.SERVER_PATH_REGISTER
                                                    + et_email.getText().toString()
                                                    +"/" + et_password2.getText().toString()
                                                    +"/" + et_idarioac.getText().toString()
                                                    +"/" + et_name.getText().toString()
                                                    +"/";
                                            url = url.replaceAll(" ", "%20");
                                            url = url.replaceAll("  ", "%20");
                                            //Log.d("url_register", url);

                                            //Ejecutamos peticion POST para envio de parametros
                                            client.post(url, null, new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                                    //Recuperamos el codigo de la operacion 200 significa que respondio el server correctamente y si existe conexion
                                                    if(statusCode == 200){
                                                        //Recibimos la respuesta del servidor en formato JSON y la mandamos a la clase que obtiene los datos
                                                        //Asignamos el acceso si fue correcto regresara un true de lo contrario false
                                                        String[] values = manager.obtDatosJSONRegister(new String(responseBody), et_email.getText().toString(), et_password2.getText().toString());
                                                        boolean access = Boolean.parseBoolean(values[0]);
                                                        String msg = values[1];
                                                        int id_user = Integer.parseInt(values[2]);

                                                        if(access == true) {
                                                            showAlertDialog(R.layout.dialog_postive_layout);
                                                        } else {
                                                            //En caso de conectar con el servidor pero que el acceso sea incorrecto mandara el sig. mensaje
                                                            Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos. Verifique sus datos", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        //En caso de conectar con el server pero mandar un codigo distinto al 200
                                                        Toast.makeText(getApplicationContext(), "Ocurrio un detalle al intentar conectar. Code: "+statusCode, Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                    if (statusCode == 400){
                                                        Toast.makeText(getApplicationContext(), "El usuario ya existe verifique los datos. Code: "+statusCode, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        //En caso de no conectar con el servidor se muestra este msg
                                                        Toast.makeText(getApplicationContext(), "Imposible conectar con el servidor." + statusCode, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_LONG).show();
                                            et_password2.setError("No Coinciden");
                                        }
                                    } else {
                                        Toast.makeText(this, "Confirmar password no puede estar vacío", Toast.LENGTH_LONG).show();
                                        et_password2.setError("Vacío");
                                    }
                                } else {
                                    Toast.makeText(this, "El password no puede estar vacío", Toast.LENGTH_LONG).show();
                                    et_password1.setError("Vacío");
                                }
                            }
                        } else {
                            Toast.makeText(this, "El email no puede estar vacío", Toast.LENGTH_LONG).show();
                            et_email.setError("Vacío");
                        }
                    } else {
                        Toast.makeText(this, "El ID de Arioac no puede estar vacío", Toast.LENGTH_LONG).show();
                        et_idarioac.setError("Vacío");
                    }
                } else {
                    Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_LONG).show();
                    et_name.setError("Vacío");
                }
                break;
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void showAlertDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(signup_activity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialog);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos una instancia de la otra ventana
                Intent intent1 = new Intent(getApplicationContext(), AppIntroActivity.class);
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                //Cerramos la actividad actual para que no este en cola
                finish();
                alertDialog.dismiss();
            }
        });
    }
}
