package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class signup_activity extends AppCompatActivity implements View.OnClickListener {

    //Generamos las variables globales
    private Button btn_acceder, btn_registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

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
                //Aqui va el codigo para dar se de alta
                Toast.makeText(this, "Datos de registro", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
