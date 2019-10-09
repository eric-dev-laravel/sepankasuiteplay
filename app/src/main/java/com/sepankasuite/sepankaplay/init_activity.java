package com.sepankasuite.sepankaplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

public class init_activity extends AppCompatActivity implements View.OnClickListener {

    //Creamos las variables globales
    private Button btn_acceder, btn_registrarse;

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_init_activity);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        //Enlazamos las variables a los objetos fisicos
        btn_acceder = findViewById(R.id.btn_init_acceder);
        btn_registrarse = findViewById(R.id.btn_init_registrarme);

        //Generamos el evento clic de cada boton
        btn_acceder.setOnClickListener(this);
        btn_registrarse.setOnClickListener(this);

        cursor = manager.selectDataUsers();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        String TAG = "Token: ";
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = TAG + token;
                        Log.d(TAG, msg);
                        //Toast.makeText(init_activity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Metodo que recupera que elemento fue dado clic
    @Override
    public void onClick(View v) {
        //Recuperamos el id del elemento al que fue dado clic
        switch (v.getId()){
            case R.id.btn_init_acceder:
                if (cursor.getCount() > 0){
                    //Creamos una instancia de la otra ventana
                    Intent intent1 = new Intent(this, AppIntroActivity.class);
                    //Nos aseguramos de cerrar las ventanas activas o que no se
                    //repitan si es que ya esta abiertas
                    startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                } else {
                    //Creamos una instancia de la otra ventana
                    Intent intent1 = new Intent(this, login_activity.class);
                    //Nos aseguramos de cerrar las ventanas activas o que no se
                    //repitan si es que ya esta abiertas
                    startActivity(intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
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
