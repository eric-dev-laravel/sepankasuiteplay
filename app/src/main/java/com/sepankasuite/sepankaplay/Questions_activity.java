package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    int preguntaContestada=0;
    int idRespuestaUsuario;

    Cursor cursor;
    Cursor cursor_user;
    Cursor cursor_respuestas;
    Cursor cursor_respuesta_usuario;
    int idUser = 0;
    String pregunta = "";
    String respuesta = "";
    int idRespuesta = 0;

    String respuesta1 = "";
    String respuesta2 = "";
    String respuesta3 = "";
    String respuesta4 = "";
    int idRespuesta1 = 0;
    int idRespuesta2 = 0;
    int idRespuesta3 = 0;
    int idRespuesta4 = 0;

    Button btn_respuesta1,btn_respuesta2,btn_respuesta3,btn_respuesta4;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    FloatingActionButton floatingActionButtonPista;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asignamos que el activity solo se abra de tipo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        Drawable bgColorGreenActive = getResources().getDrawable(R.drawable.button_green_around);
        Drawable bgColorGreen = getResources().getDrawable(R.drawable.button_green_low_around);
        int colorTextSC = getResources().getColor(R.color.colorBackgroundInit);
        int colorTextPC = getResources().getColor(R.color.colorTextPreguntaContestada);

        btn_respuesta1.setBackground(bgColorGreenActive);
        btn_respuesta2.setBackground(bgColorGreenActive);
        btn_respuesta3.setBackground(bgColorGreenActive);
        btn_respuesta4.setBackground(bgColorGreenActive);

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
            case 8:
                drawable = getResources().getDrawable(R.drawable.pregunta8);
                break;
            case 9:
                drawable = getResources().getDrawable(R.drawable.pregunta8);
                break;
        }
        iv_imagen_pregunta.setImageDrawable(drawable);

        cursor = manager.checkIfExistDataQuestion(Integer.parseInt(idUltPregunta));
        if (cursor.moveToLast()){
            do{
                pregunta = cursor.getString(2);
                preguntaContestada = cursor.getInt(3);
            } while (cursor.moveToNext());
        } else {
            pregunta = "No se actualizo la pregunta";
        }
        tv_question.setText(pregunta);

        cursor_respuesta_usuario = manager.showUserAnswerForQuestion(Integer.parseInt(idUltPregunta));
        if (cursor_respuesta_usuario.moveToLast()){
            do{
                idRespuestaUsuario = cursor_respuesta_usuario.getInt(1);
            } while (cursor_respuesta_usuario.moveToNext());
        } else {
            idRespuestaUsuario = 0;
        }
        cursor_respuestas = manager.showAnswerForQuestion(Integer.parseInt(idUltPregunta));
        cursor_respuestas.moveToFirst();
        int number = 1;
        while (!cursor_respuestas.isAfterLast()){
            idRespuesta = cursor_respuestas.getInt(2);
            respuesta = cursor_respuestas.getString(3);
            if (number == 1){
                btn_respuesta1.setText(respuesta);
                idRespuesta1 = idRespuesta;
                respuesta1 = respuesta;
            } else if (number == 2){
                btn_respuesta2.setText(respuesta);
                idRespuesta2 = idRespuesta;
                respuesta2 = respuesta;
            } else if (number == 3){
                btn_respuesta3.setText(respuesta);
                idRespuesta3 = idRespuesta;
                respuesta3 = respuesta;
            } else if (number == 4){
                btn_respuesta4.setText(respuesta);
                idRespuesta4 = idRespuesta;
                respuesta4 = respuesta;
            }
            cursor_respuestas.moveToNext();
            number++;
        }

        if (preguntaContestada != 0){
            btn_respuesta1.setBackground(bgColorGreen);
            btn_respuesta1.setTextColor(colorTextPC);
            btn_respuesta2.setBackground(bgColorGreen);
            btn_respuesta2.setTextColor(colorTextPC);
            btn_respuesta3.setBackground(bgColorGreen);
            btn_respuesta3.setTextColor(colorTextPC);
            btn_respuesta4.setBackground(bgColorGreen);
            btn_respuesta4.setTextColor(colorTextPC);

            try {
                cursor_respuestas.moveToFirst();
                int number2 = 1;
                while (!cursor_respuestas.isAfterLast()){
                    idRespuesta = cursor_respuestas.getInt(2);
                    //Log.d("respuesta_usuario", String.valueOf(idRespuesta));
                    if (number2 == 1 && idRespuesta == idRespuestaUsuario){
                        btn_respuesta1.setBackground(bgColorGreenActive);
                        btn_respuesta1.setTextColor(colorTextSC);
                    } else if (number2 == 2 && idRespuesta == idRespuestaUsuario){
                        btn_respuesta2.setBackground(bgColorGreenActive);
                        btn_respuesta2.setTextColor(colorTextSC);
                    } else if (number2 == 3 && idRespuesta == idRespuestaUsuario){
                        btn_respuesta3.setBackground(bgColorGreenActive);
                        btn_respuesta3.setTextColor(colorTextSC);
                    } else if (number2 == 4 && idRespuesta == idRespuestaUsuario){
                        btn_respuesta4.setBackground(bgColorGreenActive);
                        btn_respuesta4.setTextColor(colorTextSC);
                    }
                    cursor_respuestas.moveToNext();
                    number2++;
                }
            } catch (Exception e) {
                Log.d("error_respuestas", String.valueOf(e));
            }
        } else {
            btn_respuesta1.setOnClickListener(this);
            btn_respuesta2.setOnClickListener(this);
            btn_respuesta3.setOnClickListener(this);
            btn_respuesta4.setOnClickListener(this);
        }
        floatingActionButtonPista.setOnClickListener(this);

        cursor_user = manager.selectDataUsers();
        if (cursor_user.moveToLast()){
            do{
                idUser = cursor_user.getInt(0);
            } while (cursor_user.moveToNext());
        } else {
            idUser = 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_respuesta1:
                showAlertDialog(R.layout.answer_layout, idRespuesta1, respuesta1);
                break;
            case R.id.btn_respuesta2:
                showAlertDialog(R.layout.answer_layout, idRespuesta2, respuesta2);
                break;
            case R.id.btn_respuesta3:
                showAlertDialog(R.layout.answer_layout, idRespuesta3, respuesta3);
                break;
            case R.id.btn_respuesta4:
                showAlertDialog(R.layout.answer_layout, idRespuesta4, respuesta4);
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

    private void showAlertDialog(int layout, final int idrespuesta, final String respuesta){
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
                try{
                    //Toast.makeText(Questions_activity.this, "Pregunta, Respuesta, id_user "+idUltPregunta+", "+idrespuesta+", "+idUser, Toast.LENGTH_LONG).show();
                    manager.InsertParamsAnswerUsers(Integer.parseInt(idUltPregunta), idrespuesta, idUser);
                    manager.updateDataQuestionState(Integer.parseInt(idUltPregunta));
                    alertDialog.dismiss();
                    refreshActivity();
                } catch (Exception e) {
                    Log.d("ErrorRespues", String.valueOf(e));
                }
            }
        });
    }

    private void refreshActivity(){
        // Refresh main activity upon close of dialog box
        Intent refresh = new Intent(this, Questions_activity.class);
        //Mandamos paramentros a la siguiente ventana
        refresh.putExtra("idPregunta", String.valueOf(idUltPregunta));
        startActivity(refresh);
        this.finish();
    }
}
