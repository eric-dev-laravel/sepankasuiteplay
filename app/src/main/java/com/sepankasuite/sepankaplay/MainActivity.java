package com.sepankasuite.sepankaplay;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionMenu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    String msgError;
    Button btn_resp_ult_preg;
    Button btn_all_questions;

    //Creamos las variables globales
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    FloatingActionMenu floatingActionMenu;

    TextView tv_pregunta_ultima;
    TextView tv_numero_pregunta;
    Cursor cursor;
    String pregunta = "";
    int id_ultima_pre = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        //Enlazar el textview con el objeto
        tv_pregunta_ultima = findViewById(R.id.tv_pregunta_ultima);
        tv_numero_pregunta = findViewById(R.id.tv_numero_pregunta);
        btn_resp_ult_preg = findViewById(R.id.btn_responder_ultima_pregunta);
        btn_all_questions = findViewById(R.id.btn_all_questions);
        btn_resp_ult_preg.setOnClickListener(this);
        btn_all_questions.setOnClickListener(this);

        //Enlazar el carrusel de imagenes
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Enlazar el layout donde van los indicadores de posicion
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        //Enlazamos la variable del mmenu flotante al objeto
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menuFlotante);
        //En caso de que el menu flotante este activo y se de clic fuera de el se cerrara automatico
        floatingActionMenu.setClosedOnTouchOutside(true);

        //Crear un nuevo adaptador de imagenes cada vez que se abra el activity
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        //Se asigna el adaptador al visor de imagenes
        viewPager.setAdapter(viewPagerAdapter);

        //Recuperamos el numero de imagenes que tiene el adaptador
        dotscount = viewPagerAdapter.getCount();
        //Asignamos el numero de imagenes como variable para ser usada como un contador
        dots = new ImageView[dotscount];
        //Creamos un ciclo pasa saber en cual posicion esta el slider de images
        for (int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            //Asignamos el recurso a la posicion actual activa
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            //Le damos estilo al Layout del tamaño completo del screen
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //Le damos estilo al layout para dejar lo mas centrado posible
            params.setMargins(8, 0, 8, 0);
            //Asignamos el elemento al slider
            sliderDotspanel.addView(dots[i], params);
        }
        //Asignamos el mismo recurso a todos los demas puntos que no estan activos
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        //Recuperamos en tipo real cuando el slider cambie el elemento interno
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Actualizamos el layout dots segun el cambio de posicion que tenga el elemento
                for (int i = 0; i < dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                //Cambiamos el icono de las demas posiciones menos la activa actual
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Creamos una variable para inicializar un loop en el carrusel
        Timer timer = new Timer();
        //Asignamos a nuestro timer el tiempo y la funcion que genera el cambio de imagen, el tiempo debe ser en milisegundos
        timer.scheduleAtFixedRate(new MyTimerTask(), 4000, 8000);

        new DownloadLastQuestion().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_responder_ultima_pregunta:
                //Toast.makeText(this, "ID: "+id_ultima_pre, Toast.LENGTH_LONG).show();
                //Creamos una instancia de la otra ventana
                Intent intent = new Intent(MainActivity.this, Questions_activity.class);
                //Mandamos paramentros a la siguiente ventana
                intent.putExtra("idPregunta", String.valueOf(id_ultima_pre));
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
            case R.id.btn_all_questions:
                //Creamos una instancia de la otra ventana
                Intent intent2 = new Intent(MainActivity.this, TodasPreguntas.class);
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
        }
    }

    //Metodo al hacer clic sobre el floating button de facebook
    public void facebookIntent(View view){
        //generalos la URL a donde se dirigira
        String url = "https://facebook.com/sepankasuite";
        //Creamos un nuevo intento
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Parseamos la url para que sea leida por el app que se seleccione al abrir
        intent.setData(Uri.parse(url));
        //Lanzamos el activity nuevo
        startActivity(intent);
    }

    //Proceso en segundo plano que lleva el tiempo transcurrido por elemento dentro del adaptador de imagenes
    public class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Se revisa cual es el elemento actual en el adaptador, si existe elementos siguientes se cambian
                    //si es el elemento final, regresa al principio, Nota: se puede mejorar para que no sea estatico
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4){
                        viewPager.setCurrentItem(5);
                    } else if (viewPager.getCurrentItem() == 5){
                        viewPager.setCurrentItem(6);
                    } else if (viewPager.getCurrentItem() == 6){
                        viewPager.setCurrentItem(7);
                    } else if (viewPager.getCurrentItem() == 7){
                        viewPager.setCurrentItem(8);
                    }  else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    //Proceso en segundo plano para descargar la ultima pregunta activa
    public class DownloadLastQuestion extends AsyncTask<Void, Void, Void>{

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
            cursor = manager.selectDataPregunta();

            if (cursor.moveToLast()){
                do{
                    id_ultima_pre = Integer.parseInt(cursor.getString(1));
                    pregunta = cursor.getString(2);
                } while (cursor.moveToNext());
            } else {
                pregunta = "No se actualizo la pregunta";
            }
            tv_numero_pregunta.setText("Pregunta "+id_ultima_pre);
            tv_pregunta_ultima.setText(pregunta);
        }
    }
}
