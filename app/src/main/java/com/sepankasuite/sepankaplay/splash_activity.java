package com.sepankasuite.sepankaplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class splash_activity extends AppCompatActivity {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    String msgError;

    Animation animation;
    ImageView imageView;

    Cursor cursor;

    //Generamos la variable global del tiempo que estara abierto el splash screen en milisegundos
    private final int SPLASH_DURATION = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Asignamos que el activity abarque completamente el fondo de la pantalla
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Enlace de la clase java con el activity
        setContentView(R.layout.activity_splash_activity);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);
        cursor = manager.selectDataUsers();

        imageView = (ImageView) findViewById(R.id.iv_imageSplash);
        animation= AnimationUtils.loadAnimation(splash_activity.this,R.anim.pulse);
        startAnimation();

        FirebaseMessaging.getInstance().subscribeToTopic("NewQuestion").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "Suscripcion Completa";
                if (!task.isSuccessful()) {
                    msg = "Suscripcion Fallo";
                }
                //Log.d("suscription", msg);
                //Toast.makeText(splash_activity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        //Generamos la funcion que lleva el contador interno del tiempo y lo que se hara cuando termine el tiempo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cursor.getCount() > 0){
                    //Creamos una instancia de la otra ventana
                    Intent intent1 = new Intent(splash_activity.this, AppIntroActivity.class);
                    //Nos aseguramos de cerrar las ventanas activas o que no se
                    //repitan si es que ya esta abiertas
                    startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                } else {
                    //Generamos un intent de la actividad nueva
                    Intent intent = new Intent(splash_activity.this, init_activity.class);
                    //Lanzamos el activity
                    startActivity(intent);
                    //Cerramos el intent actual para que no se quede en cola
                    finish();
                }
            };
        }, SPLASH_DURATION);

        new splash_activity.DownloadAllQuestions().execute();
    }

    private void startAnimation(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.startAnimation(animation);
            }
        }, 1000);
    }

    //Proceso en segundo plano para descargar todas las preguntas
    public class DownloadAllQuestions extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    //Creamos un nuevo cliente de conexion
                    AsyncHttpClient client = new AsyncHttpClient();
                    //Definimos la URL a la cual sera dirigidio y recuperamos los datos de las cajas de texto
                    final String url = manager.SERVER_URL + manager.SERVER_PATH_ALL_QUESTIONS;

                    //Ejecutamos peticion POST para envio de parametros
                    client.post(url, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            //Log.d("preguntas", String.valueOf(statusCode));
                            //Recuperamos el codigo de la operacion 200 significa que respondio el server correctamente y si existe conexion
                            if(statusCode == 200){
                                //Recibimos la respuesta del servidor en formato JSON y la mandamos a la clase que obtiene los datos
                                //Asignamos el acceso si fue correcto regresara un true de lo contrario false
                                manager.obtDatosJSONAllQuestions(new String(responseBody));
                            } else {
                                //En caso de conectar con el server pero mandar un codigo distinto al 200
                                msgError = "Ocurrio un detalle al intentar conectar. Code: "+statusCode;
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            //En caso de no conectar con el servidor se muestra este msg
                            msgError ="Imposible conectar con el servidor.";
                        }
                    });
                }
            };
            handler.post(runnable);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Log.d("msg", msgError);
            //Toast.makeText(getApplicationContext(), "Ocurrio un error al actualizar: " + msgError, Toast.LENGTH_LONG).show();

        }
    }

    //Proceso en segundo plano para descargar la ultima pregunta activa
    public class DownloadLastQuestion extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    //Creamos un nuevo cliente de conexion
                    AsyncHttpClient client = new AsyncHttpClient();
                    //Definimos la URL a la cual sera dirigidio y recuperamos los datos de las cajas de texto
                    final String url = manager.SERVER_URL + manager.SERVER_PATH_LAST_QUESTION;

                    //Ejecutamos peticion POST para envio de parametros
                    client.post(url, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            //Log.d("preguntas", String.valueOf(statusCode));
                            //Recuperamos el codigo de la operacion 200 significa que respondio el server correctamente y si existe conexion
                            if(statusCode == 200){
                                //Recibimos la respuesta del servidor en formato JSON y la mandamos a la clase que obtiene los datos
                                //Asignamos el acceso si fue correcto regresara un true de lo contrario false
                                manager.obtDatosJSONLastQuestion(new String(responseBody));
                            } else {
                                //En caso de conectar con el server pero mandar un codigo distinto al 200
                                msgError = "Ocurrio un detalle al intentar conectar. Code: "+statusCode;
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            //En caso de no conectar con el servidor se muestra este msg
                            msgError ="Imposible conectar con el servidor.";
                        }
                    });
                }
            };
            handler.post(runnable);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Toast.makeText(getApplicationContext(), "Ocurrio un error al actualizar: " + msgError, Toast.LENGTH_LONG).show();

        }
    }
}
