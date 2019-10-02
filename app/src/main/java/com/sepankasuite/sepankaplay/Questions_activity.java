package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

public class Questions_activity extends AppCompatActivity implements View.OnClickListener {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    TextView pregunta_id, tv_question;
    String idUltPregunta = "";
    ImageView iv_imagen_pregunta;
    Drawable drawable;

    Cursor cursor;
    Cursor cursor_respuestas;
    String pregunta = "";
    String respuesta = "";
    String respuesta1 = "";
    String respuesta2 = "";
    String respuesta3 = "";
    String respuesta4 = "";
    Button btn_respuesta1,btn_respuesta2,btn_respuesta3,btn_respuesta4;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    FloatingActionButton floatingActionButtonPista;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_activity);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        idUltPregunta = bundle.getString("idPregunta");

        pregunta_id = findViewById(R.id.tv_pregunta_id);
        tv_question = findViewById(R.id.tv_question);
        btn_respuesta1 = findViewById(R.id.btn_respuesta1);
        btn_respuesta2 = findViewById(R.id.btn_respuesta2);
        btn_respuesta3 = findViewById(R.id.btn_respuesta3);
        btn_respuesta4 = findViewById(R.id.btn_respuesta4);
        floatingActionButtonPista = findViewById(R.id.fab_pista);

        btn_respuesta1.setOnClickListener(this);
        btn_respuesta2.setOnClickListener(this);
        btn_respuesta3.setOnClickListener(this);
        btn_respuesta4.setOnClickListener(this);

        pregunta_id.setText("Pregunta " + idUltPregunta);

        iv_imagen_pregunta = findViewById(R.id.iv_imagePregunta);
        switch (Integer.parseInt(idUltPregunta)){
            case 1:
                drawable = getResources().getDrawable(R.drawable.pregunta1);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.pregunta2);
                break;
            case 3:
                drawable = getResources().getDrawable(R.drawable.pregunta3);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.pregunta4);
                break;
            case 5:
                drawable = getResources().getDrawable(R.drawable.pregunta5);
                break;
            case 6:
                drawable = getResources().getDrawable(R.drawable.pregunta6);
                break;
            case 7:
                drawable = getResources().getDrawable(R.drawable.pregunta7);
                break;
        }
        iv_imagen_pregunta.setImageDrawable(drawable);

        cursor = manager.checkIfExistDataQuestion(Integer.parseInt(idUltPregunta));
        if (cursor.moveToLast()){
            do{
                pregunta = cursor.getString(2);
            } while (cursor.moveToNext());
        } else {
            pregunta = "No se actualizo la pregunta";
        }
        tv_question.setText(pregunta);

        cursor_respuestas = manager.showAnswerForQuestion(Integer.parseInt(idUltPregunta));
        cursor_respuestas.moveToFirst();
        int number = 1;
        while (!cursor_respuestas.isAfterLast()){
            respuesta = cursor_respuestas.getString(3);
            if (number == 1){
                btn_respuesta1.setText(respuesta);
                respuesta1 = respuesta;
            } else if (number == 2){
                btn_respuesta2.setText(respuesta);
                respuesta2 = respuesta;
            } else if (number == 3){
                btn_respuesta3.setText(respuesta);
                respuesta3 = respuesta;
            } else if (number == 4){
                btn_respuesta4.setText(respuesta);
                respuesta4 = respuesta;
            }
            cursor_respuestas.moveToNext();
            number++;
        }

        floatingActionButtonPista.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_respuesta1:
                showAlertDialog(R.layout.answer_layout, respuesta1);
                break;
            case R.id.btn_respuesta2:
                showAlertDialog(R.layout.answer_layout, respuesta2);
                break;
            case R.id.btn_respuesta3:
                showAlertDialog(R.layout.answer_layout, respuesta3);
                break;
            case R.id.btn_respuesta4:
                showAlertDialog(R.layout.answer_layout, respuesta4);
                break;
            case R.id.fab_pista:
                //generalos la URL a donde se dirigira
                url = "https://google.com";

                switch (Integer.parseInt(idUltPregunta)){
                    case 1:
                        //generalos la URL a donde se dirigira
                        url = "https://sepankasuite.com/compañia#mision_vision";
                        break;
                    case 2:
                        //generalos la URL a donde se dirigira
                        url = "https://sepankasuite.com/";
                        break;
                    case 3:
                        //generalos la URL a donde se dirigira
                        url = "https://sepankasuite.com/";
                        break;
                    case 4:
                        //generalos la URL a donde se dirigira
                        url = "http://arioac.com/app_test/";
                        break;
                    case 5:
                        //generalos la URL a donde se dirigira
                        url = "https://www.facebook.com/SepankaSuite/videos/2527358524166476/";
                        break;
                    case 6:
                        //generalos la URL a donde se dirigira
                        url = "https://www.facebook.com/SepankaSuite/videos/1490456411082913/";
                        break;
                    case 7:
                        //generalos la URL a donde se dirigira
                        url = "https://sepankasuite.com/";
                        break;
                }
                //Creamos un nuevo intento
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //Parseamos la url para que sea leida por el app que se seleccione al abrir
                intent.setData(Uri.parse(url));
                //Lanzamos el activity nuevo
                startActivity(intent);
                break;
        }
    }

    private void showAlertDialog(int layout, final String respuesta){
        dialogBuilder = new AlertDialog.Builder(Questions_activity.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDialogRegresar);
        TextView textViewRespuesta = layoutView.findViewById(R.id.tv_respuesta);
        Button dialogButtonAceptar = layoutView.findViewById(R.id.btnDialogAceptar);
        textViewRespuesta.setText(respuesta);
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

        dialogButtonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Questions_activity.this, "Pregunta, Respuesta, id_user "+idUltPregunta+", "+respuesta+", 1", Toast.LENGTH_LONG).show();
            }
        });
    }
}
