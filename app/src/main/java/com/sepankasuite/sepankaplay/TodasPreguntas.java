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
    Cursor cursor_respuesta_usuario;

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
            }
            cursor.moveToNext();
            number++;
        }
    }
}
