package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class init_activity extends AppCompatActivity implements View.OnClickListener {

    //Creamos las variables globales
    private Button btn_acceder, btn_registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_activity);

        //Enlazamos las variables a los objetos fisicos
        btn_acceder = findViewById(R.id.btn_init_acceder);
        btn_registrarse = findViewById(R.id.btn_init_registrarme);

        //Generamos el evento clic de cada boton
        btn_acceder.setOnClickListener(this);
        btn_registrarse.setOnClickListener(this);
    }

    //Metodo que recupera que elemento fue dado clic
    @Override
    public void onClick(View v) {
        //Recuperamos el id del elemento al que fue dado clic
        switch (v.getId()){
            case R.id.btn_init_acceder:
                //Creamos una instancia de la otra ventana
                Intent intent1 = new Intent(this, login_activity.class);
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;

            case R.id.btn_init_registrarme:
                //Creamos una instancia de la otra ventana
                Intent intent2 = new Intent(this, signup_activity.class);
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
        }

    }
}
