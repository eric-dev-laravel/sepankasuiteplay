package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TodasPreguntas extends AppCompatActivity {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    Cursor cursor;

    Button btn_pregunta1,btn_pregunta2,btn_pregunta3,btn_pregunta4,btn_pregunta5,btn_pregunta6,btn_pregunta7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Drawable bgColorGreenActive = getResources().getDrawable(R.drawable.button_green_around);
        Drawable bgColorGreen = getResources().getDrawable(R.drawable.button_green_low_around);

        int colorTextPC = getResources().getColor(R.color.colorTextPreguntaContestada);

        btn_pregunta1.setBackground(bgColorGreen);
        btn_pregunta1.setTextColor(colorTextPC);

        btn_pregunta2.setBackground(bgColorGreenActive);

        cursor = manager.selectDataPregunta();
        cursor.moveToFirst();
        int number = 1;
        while (!cursor.isAfterLast()){
            final int idPregunta = Integer.parseInt(cursor.getString(1));
            //Log.d("idPreguntas", idPregunta);
            if ( idPregunta == 1){
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
            }
            cursor.moveToNext();
            number++;
        }
    }
}
