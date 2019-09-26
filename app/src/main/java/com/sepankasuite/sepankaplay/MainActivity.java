package com.sepankasuite.sepankaplay;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionMenu;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Creamos las variables globales
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    FloatingActionMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
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
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
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
}
