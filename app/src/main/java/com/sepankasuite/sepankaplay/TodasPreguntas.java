package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class TodasPreguntas extends AppCompatActivity {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    String msgError;
    Cursor cursor;
    Cursor cursor_respuesta_usuario;
    ProgressDialog progressDialog;
    Button btn_pregunta1,btn_pregunta2,btn_pregunta3,btn_pregunta4,btn_pregunta5,btn_pregunta6,btn_pregunta7,btn_pregunta8,btn_pregunta9;

    FloatingActionButton floatingActionButtonReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_todas_preguntas);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        btn_pregunta1 = findViewById(R.id.btn_pregunta1);
        btn_pregunta2 = findViewById(R.id.btn_pregunta2);
        btn_pregunta3 = findViewById(R.id.btn_pregunta3);
        btn_pregunta4 = findViewById(R.id.btn_pregunta4);
        btn_pregunta5 = findViewById(R.id.btn_pregunta5);
        btn_pregunta6 = findViewById(R.id.btn_pregunta6);
        btn_pregunta7 = findViewById(R.id.btn_pregunta7);
        btn_pregunta8 = findViewById(R.id.btn_pregunta8);
        btn_pregunta9 = findViewById(R.id.btn_pregunta9);
        floatingActionButtonReload = findViewById(R.id.fab_reloadQuestions);

        Drawable bgColorGreenActive = getResources().getDrawable(R.drawable.button_green_around);
        Drawable bgColorGreen = getResources().getDrawable(R.drawable.button_green_low_around);
        int colorTextSC = getResources().getColor(R.color.colorBackgroundInit);
        int colorTextPC = getResources().getColor(R.color.colorTextPreguntaContestada);

        btn_pregunta1.setBackground(bgColorGreenActive);
        btn_pregunta1.setTextColor(colorTextSC);
        btn_pregunta2.setBackground(bgColorGreenActive);
        btn_pregunta2.setTextColor(colorTextSC);
        btn_pregunta3.setBackground(bgColorGreenActive);
        btn_pregunta3.setTextColor(colorTextSC);
        btn_pregunta4.setBackground(bgColorGreenActive);
        btn_pregunta4.setTextColor(colorTextSC);
        btn_pregunta5.setBackground(bgColorGreenActive);
        btn_pregunta5.setTextColor(colorTextSC);
        btn_pregunta6.setBackground(bgColorGreenActive);
        btn_pregunta6.setTextColor(colorTextSC);
        btn_pregunta7.setBackground(bgColorGreenActive);
        btn_pregunta7.setTextColor(colorTextSC);
        btn_pregunta8.setBackground(bgColorGreenActive);
        btn_pregunta8.setTextColor(colorTextSC);
        btn_pregunta9.setBackground(bgColorGreenActive);
        btn_pregunta9.setTextColor(colorTextSC);

        cursor = manager.selectDataPregunta();
        cursor.moveToFirst();
        int number = 1;
        while (!cursor.isAfterLast()){
            final int idPregunta = Integer.parseInt(cursor.getString(1));
            if ( idPregunta == 1){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta1.setBackground(bgColorGreen);
                    btn_pregunta1.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta1.setVisibility(View.VISIBLE);
                btn_pregunta1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 2){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta2.setBackground(bgColorGreen);
                    btn_pregunta2.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta2.setVisibility(View.VISIBLE);
                btn_pregunta2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 3){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta3.setBackground(bgColorGreen);
                    btn_pregunta3.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta3.setVisibility(View.VISIBLE);
                btn_pregunta3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 4){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta4.setBackground(bgColorGreen);
                    btn_pregunta4.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta4.setVisibility(View.VISIBLE);
                btn_pregunta4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 5){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta5.setBackground(bgColorGreen);
                    btn_pregunta5.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta5.setVisibility(View.VISIBLE);
                btn_pregunta5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 6){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta6.setBackground(bgColorGreen);
                    btn_pregunta6.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta6.setVisibility(View.VISIBLE);
                btn_pregunta7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 7){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta7.setBackground(bgColorGreen);
                    btn_pregunta7.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta7.setVisibility(View.VISIBLE);
                btn_pregunta7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 8){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta8.setBackground(bgColorGreen);
                    btn_pregunta8.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta8.setVisibility(View.VISIBLE);
                btn_pregunta8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            } else if( idPregunta == 9){
                cursor_respuesta_usuario = manager.showUserAnswerForQuestion(idPregunta);
                if (cursor_respuesta_usuario.getCount() != 0){
                    btn_pregunta9.setBackground(bgColorGreen);
                    btn_pregunta9.setTextColor(colorTextPC);
                }
                //Desaparecer un boton
                btn_pregunta9.setVisibility(View.VISIBLE);
                btn_pregunta9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                        //Creamos una instancia de la otra ventana
                        Intent intent = new Intent(TodasPreguntas.this, Questions_activity.class);
                        //Mandamos paramentros a la siguiente ventana
                        intent.putExtra("idPregunta", String.valueOf(idPregunta));
                        //Nos aseguramos de cerrar las ventanas activas o que no se
                        //repitan si es que ya esta abiertas
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    }
                });
            }
            cursor.moveToNext();
            number++;
        }

        floatingActionButtonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TodasPreguntas.DownloadAllQuestions().execute();
            }
        });
    }

    //Proceso en segundo plano para descargar todas las preguntas
    public class DownloadAllQuestions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TodasPreguntas.this);
            progressDialog.setTitle("Actualizando Preguntas");
            progressDialog.setMessage("Actualizando, Por favor espere!");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

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
            refreshActivity();
            progressDialog.cancel();
        }
    }

    private void refreshActivity(){
        // Refresh main activity upon close of dialog box
        Intent refresh = new Intent(this, MainActivity.class);
        //Mandamos paramentros a la siguiente ventana
        startActivity(refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        this.finish();
    }

}
