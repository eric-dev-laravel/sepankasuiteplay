package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signup_activity extends AppCompatActivity implements View.OnClickListener {

    //Generamos las variables globales
    private Button btn_acceder, btn_registrarse;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

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
                //Toast.makeText(this, "Datos de registro", Toast.LENGTH_LONG).show();
                showAlertDialog(R.layout.dialog_postive_layout);
                break;
        }
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
                alertDialog.dismiss();
            }
        });
    }
}
