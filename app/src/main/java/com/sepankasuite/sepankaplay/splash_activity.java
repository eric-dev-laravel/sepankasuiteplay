package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash_activity extends AppCompatActivity {

    //Generamos la variable global del tiempo que estara abierto el splash screen en milisegundos
    private final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Asignamos que el activity abarque completamente el fondo de la pantalla
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Enlace de la clase java con el activity
        setContentView(R.layout.activity_splash_activity);

        //Generamos la funcion que lleva el contador interno del tiempo y lo que se hara cuando termine el tiempo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Generamos un intent de la actividad nueva
                Intent intent = new Intent(splash_activity.this, init_activity.class);
                //Lanzamos el activity
                startActivity(intent);
                //Cerramos el intent actual para que no se quede en cola
                finish();
            };
        }, SPLASH_DURATION);
    }
}
