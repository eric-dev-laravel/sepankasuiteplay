package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class signup_activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_acceder, btn_registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        btn_acceder = findViewById(R.id.btn_signup_acceder);
        btn_registrarse = findViewById(R.id.btn_signup_registrarse);

        btn_acceder.setOnClickListener(this);
        btn_registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup_acceder:
                //Creamos una instancia de la otra ventana
                Intent intent1 = new Intent(this, login_activity.class);
                //Mandamos paramentros a la siguiente ventana
                //Nos aseguramos de cerrar las ventanas activas o que no se
                //repitan si es que ya esta abiertas
                startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                break;

            case R.id.btn_signup_registrarse:
                Toast.makeText(this, "Datos de registro", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
