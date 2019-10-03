package com.sepankasuite.sepankaplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataBaseManager {

    //Instancia de las clases
    private DBHelper helper;
    private SQLiteDatabase db;

    //Variables de configuracion de servicios
    public  static final String SERVER_URL = "http://test.rally.sepankasuite.com/";
    public  static final String SERVER_PATH_CHECKLOGIN = "checklogin/";
    public  static final String SERVER_PATH_LAST_QUESTION = "lastQuestionModif/";
    public  static final String SERVER_PATH_ALL_QUESTIONS = "activeQuestions/";
    public  static final String SERVER_PATH_REGISTER = "insertUser/";
    public  static final String SERVER_PATH_SAVE_USERANSWERS = "saveAnswers/";

    /*Esta clase es la encargada de hacer movimientos en la DB*/

    /* *************************** ESTRUCTURA DE LA DB *********************************** */
    //Nombre de la tabla usuarios
    public static final String TABLE_USERS = "users";
    //Nombre d ela tabla de preguntas
    public static final String TABLE_QUESTIONS = "questions";
    //Nombre d ela tabla de respuestas de las preguntas
    public static final String TABLE_ANSWERS_QUESTIONS = "answers_questions";
    //Nombre d ela tabla de respuestas de los usuarios
    public static final String TABLE_ANSWERS_USERS = "answers_users";

    //Nombre de campos server
    public static final String CN_ID = "_id";
    public static final String CN_SYNC = "sincronized";
    //Nombre de campos usuarios
    public static final String CN_ID_USER = "id_user";
    public static final String CN_USER = "email";
    public static final String CN_PASSWORD = "password";
    public static final String CN_STATE = "state";
    //Nombre de campos preguntas
    public static final String CN_ID_QUESTION = "_id_question";
    public static final String CN_TEXT_QUESTION = "question";
    public static final String CN_VALUE = "value";
    //Nombre de campos preguntas
    public static final String CN_ID_ANSWER = "_id_answer";
    public static final String CN_TEXT_ANSWER = "answer";

    /* ************* TABLAS ***************** */

    //Sentencia para crear la tabla usuarios
    public static final String CREATE_TABLE_USERS = "create table "+TABLE_USERS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_USER + " integer unique,"
            + CN_USER + " text not null,"
            + CN_PASSWORD + " text not null,"
            + CN_STATE + " text not null);";

    //Sentencia para crear la tabla preguntas
    public static final String CREATE_TABLE_QUESTIONS = "create table "+TABLE_QUESTIONS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_QUESTION + " integer not null unique,"
            + CN_TEXT_QUESTION + " text not null,"
            + CN_VALUE + " integer null,"
            + CN_STATE + " integer null,"
            + CN_SYNC + " integer not null);";

    //Sentencia para crear la tabla preguntas
    public static final String CREATE_TABLE_ANSWERS_QUESTIONS = "create table "+TABLE_ANSWERS_QUESTIONS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_QUESTION + " integer not null,"
            + CN_ID_ANSWER + " integer not null unique,"
            + CN_TEXT_ANSWER + " text not null,"
            + CN_VALUE + " integer null,"
            + CN_STATE + " integer null,"
            + CN_SYNC + " integer not null);";

    //Sentencia para crear la tabla respuestas por usuario
    public static final String CREATE_TABLE_ANSWERS_USERS = "create table "+TABLE_ANSWERS_USERS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_QUESTION + " integer not null,"
            + CN_ID_ANSWER + " integer not null unique,"
            + CN_ID_USER + " text not null,"
            + CN_SYNC + " integer not null);";

    public static final String UPDATE_TABLE_USERS = "alter table "+TABLE_USERS+" add column "
            + CN_ID_USER + " integer;";

    /* ****************** METODOS DE INSERTAR ************************** */

    //Metodo para insertar usuarios
    public void InsertParamsUsers(int id_user, String user, String password) {
        //Instruccion para insertar en android
        db.insert(TABLE_USERS, null, generarContentValuesUsers(id_user, user, password));
    }

    //Metodo para insertar preguntas
    public void InsertParamsQuestion(int id_question, String question) {
        //Instruccion para insertar en android
        db.insert(TABLE_QUESTIONS, null, generarContentValuesQuestions(id_question, question));
    }

    //Metodo para insertar respuestas de preguntas
    public void InsertParamsAnswer(int id_question, int id_answer, String answer, int value) {
        //Instruccion para insertar en android
        db.insert(TABLE_ANSWERS_QUESTIONS, null, generarContentValuesAnswerQuestions(id_question, id_answer, answer, value));
    }

    public void InsertParamsAnswerUsers(int id_question, int id_answer, int id_user) {
        //Instruccion para insertar en android
        db.insert(TABLE_ANSWERS_USERS, null, generarContentValuesAnswerUsers(id_question, id_answer, id_user));
    }
    /* ****************** FIN METODOS DE INSERTAR ************************** */

    /* ****************** METODOS DE BORRAR ************************** */

    //Borrar el contenido de la tabla usuarios
    public void clearDataUsers(){
        db.delete(TABLE_USERS, null, null);
    }

    /* ****************** FIN METODOS DE BORRAR ************************** */

    /* ****************** METODOS DE UPDATE ************************** */

    //Editar un campo en la tabla de Preguntas
    public void updateDataQuestionState(int idPregunta){
        ContentValues columnas = new ContentValues();
        columnas.put(CN_STATE, 1);

        String[] args = new String[] {String.valueOf(idPregunta)};

        db.update(TABLE_QUESTIONS, columnas, "_id_question=?", args);
    }

    //Editar un campo en la tabla de Preguntas
    public void updateDataUserAnswers(int idIntern){
        ContentValues columnas = new ContentValues();
        columnas.put(CN_SYNC, 1);

        String[] args = new String[] {String.valueOf(idIntern)};

        db.update(TABLE_ANSWERS_USERS, columnas, "_id=?", args);
    }

    /* ****************** FIN METODOS DE UPDATE ************************** */

    /* ****************** METODOS DE SELECCIONAR ************************** */
    public Cursor selectDataUsers() {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID_USER};

        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_USERS, columnas, null, null, null, null, null);
    }

    public Cursor selectDataPregunta() {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID, CN_ID_QUESTION, CN_TEXT_QUESTION, CN_STATE};

        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_QUESTIONS, columnas, null, null, null, null, CN_ID_QUESTION + " ASC");
    }

    public Cursor checkIfExistDataQuestion(int id) {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID, CN_ID_QUESTION, CN_TEXT_QUESTION, CN_STATE};
        String[] args = new String[] {String.valueOf(id)};
        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_QUESTIONS, columnas, "_id_question=?", args, null, null, null);
    }

    public Cursor showAnswerForQuestion(int id_question) {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID, CN_ID_QUESTION, CN_ID_ANSWER, CN_TEXT_ANSWER, CN_VALUE};
        String[] args = new String[] {String.valueOf(id_question)};
        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_ANSWERS_QUESTIONS, columnas, "_id_question=?", args, null, null, null);
    }

    public Cursor showUserAnswerForQuestion(int id_question) {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID_QUESTION, CN_ID_ANSWER};
        String[] args = new String[] {String.valueOf(id_question)};
        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_ANSWERS_USERS, columnas, "_id_question=?", args, null, null, null);
    }

    public Cursor showUserAnswers() {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_ID_USER, CN_ID_QUESTION, CN_ID_ANSWER, CN_ID};
        String[] args = new String[] {String.valueOf(0)};
        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_ANSWERS_USERS, columnas, "sincronized=?", args, null, null, null);
    }

    /* ****************** FIN METODOS DE SELECCIONAR ************************** */

    //#########################    CONTENEDORES   ###############################################

    //Metodo contenedor de valores USUARIOS
    private ContentValues generarContentValuesUsers(int id_user, String user, String password) {
        ContentValues values = new ContentValues();
        values.put(CN_ID_USER, id_user);
        values.put(CN_USER, user);
        values.put(CN_PASSWORD, password);
        values.put(CN_STATE, 0);
        return values;
    }

    //Metodo contenedor de valores Preguntas
    private ContentValues generarContentValuesQuestions(int id_question, String question) {
        ContentValues values = new ContentValues();
        values.put(CN_ID_QUESTION, id_question);
        values.put(CN_TEXT_QUESTION, question);
        values.put(CN_VALUE, 0);
        values.put(CN_STATE, 0);
        values.put(CN_SYNC, 0);
        //Log.d("valores", String.valueOf(values));
        return values;
    }

    //Metodo contenedor de valores Respuestas
    private ContentValues generarContentValuesAnswerQuestions(int id_question, int id_answer, String answer, int value) {
        ContentValues values = new ContentValues();
        values.put(CN_ID_QUESTION, id_question);
        values.put(CN_ID_ANSWER, id_answer);
        values.put(CN_TEXT_ANSWER, answer);
        values.put(CN_VALUE, value);
        values.put(CN_STATE, 0);
        values.put(CN_SYNC, 0);
        //Log.d("respuestas_v", String.valueOf(values));
        return values;
    }

    //Metodo contenedor de valores Respuestas
    private ContentValues generarContentValuesAnswerUsers(int id_question, int id_answer, int id_user) {
        ContentValues values = new ContentValues();
        values.put(CN_ID_QUESTION, id_question);
        values.put(CN_ID_ANSWER, id_answer);
        values.put(CN_ID_USER, id_user);
        values.put(CN_SYNC, 0);
        //Log.d("respuestas_v", String.valueOf(values));
        return values;
    }

    //#########################    FIN CONTENEDORES   ###############################################

    //########################### SERVER OPERATIONS ####################################################

    //Metodo para leer respuesta del login en el server
    public boolean obtDatosJSONLogin(String response, String user, String psw) {
        boolean acceso = false;
        int id_user = 0;
        try {
            //recibimos el arreglo de tipo JSON en una variable JSON
            JSONObject object = new JSONObject(response);
            JSONObject jsonArray = object.getJSONObject("success");
            acceso = jsonArray.getBoolean("existe");
            id_user = jsonArray.getInt("id_user");

            //Log.d("response_user1", String.valueOf(acceso));
            //Log.d("response_user2", String.valueOf(id_user));

            InsertParamsUsers(id_user, user, psw);
        } catch (Exception e) {
            Log.d("errorJson", String.valueOf(e));
            return acceso;
        }
        return acceso;
    }

    //Metodo para leer respuesta del login en el server
    public String[] obtDatosJSONRegister(String response, String user, String psw) {
        String[] values = new String[0];
        boolean access = false;
        String msg = "";
        int id_user = 0;
        try {
            //recibimos el arreglo de tipo JSON en una variable JSON
            JSONObject object = new JSONObject(response);
            //Log.d("response_register", String.valueOf(object));
            access = object.getBoolean("status");
            msg = object.getString("msg");
            id_user = object.getInt("id_user");

            values = new String[]{
                    String.valueOf(access),
                    msg,
                    String.valueOf(id_user)
            };

            InsertParamsUsers(id_user, user, psw);
        } catch (Exception e) {
            Log.d("errorJson", String.valueOf(e));
            return values;
        }
        return values;
    }

    //Metodo para descargar ultima pregunta
    public String obtDatosJSONAllQuestions(String response) {
        String pregunta = "";
        int id_pregunta_server = 0;
        try {
            //recibimos el arreglo de tipo JSON en una variable JSON
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("success");
            for (int a = 0; a < jsonArray.length(); a++){
                String stringPreguntas = jsonArray.getJSONObject(a).getString("pregunta_"+a);
                JSONObject jsonObjectPreguntas = new JSONObject(stringPreguntas);

                int idPregunta = Integer.parseInt(jsonObjectPreguntas.getString("id"));
                String stringPregunta = jsonObjectPreguntas.getString("pregunta");
                Cursor cursor = checkIfExistDataQuestion(idPregunta);
                if (cursor.getCount() <= 0){
                    InsertParamsQuestion(idPregunta, stringPregunta);
                }

                String stringRepuestas = jsonArray.getJSONObject(a).getString("respuestas");
                JSONObject jsonObjectRespuestas = new JSONObject(stringRepuestas);
                for (int b = 0; b < jsonObjectRespuestas.length(); b++){
                    JSONObject jsonObjectRespuestasInterno = jsonObjectRespuestas.getJSONObject("respuesta_"+b);

                    int idPreguntaInterna = Integer.parseInt(jsonObjectRespuestasInterno.getString("id_pregunta"));
                    int idRespuestaInterna = Integer.parseInt(jsonObjectRespuestasInterno.getString("id_respuesta"));
                    String opcionInterna = jsonObjectRespuestasInterno.getString("opcion");
                    int correctaInterna = Integer.parseInt(jsonObjectRespuestasInterno.getString("correcta"));

                    InsertParamsAnswer(idPreguntaInterna, idRespuestaInterna, opcionInterna, correctaInterna);
                    //Log.d("allQuestion",correctaInterna);
                }
            }
        } catch (Exception e) {
            Log.d("error", String.valueOf(e));
            return null;
        }
        return null;
    }

    //Metodo para descargar ultima pregunta
    public String obtDatosJSONLastQuestion(String response) {
        String pregunta = "";
        int id_pregunta_server = 0;
        try {
            //recibimos el arreglo de tipo JSON en una variable JSON
            JSONObject jsonArray = new JSONObject(response);
            JSONObject jsonObject = jsonArray.getJSONObject("success");

            id_pregunta_server = Integer.parseInt(jsonObject.getString("id_pregunta"));
            pregunta = jsonObject.getString("pregunta");

            Cursor cursor = checkIfExistDataQuestion(id_pregunta_server);
                if (cursor.getCount() <= 0){
                InsertParamsQuestion(id_pregunta_server, pregunta);

                for (int i = 0; i < 1; i++){
                    JSONObject jsonArray1 = new JSONObject(jsonObject.toString(i));
                    //Log.d("Numero", String.valueOf(jsonArray1));
                    JSONObject jsonObject1 = jsonArray1.getJSONObject("grupo");
                    for (int a = 0; a < jsonObject1.length(); a++){
                        JSONObject jsonArray2 = new JSONObject(jsonObject1.toString(a));
                        JSONObject jsonObject2 = jsonArray2.getJSONObject("respuesta"+a);

                        int id_respuesta = Integer.parseInt(jsonObject2.getString("id"));
                        String respuesta = jsonObject2.getString("texto");
                        int es_correcta = Integer.parseInt(jsonObject2.getString("correcta"));

                        InsertParamsAnswer(id_pregunta_server, id_respuesta, respuesta, es_correcta);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("error", String.valueOf(e));
            return null;
        }
        return null;
    }

    //Metodo para leer respuesta del login en el server
    public String[] obtDatosJSONSaveAnswersUser(String response) {
        String[] values = new String[0];
        boolean access = false;
        String msg = "";
        int id_interno = 0;
        try {
            //recibimos el arreglo de tipo JSON en una variable JSON
            JSONObject object = new JSONObject(response);
            //Log.d("response_register", String.valueOf(object));
            access = object.getBoolean("status");
            msg = object.getString("msg");
            id_interno = object.getInt("id_interno");

            values = new String[]{
                    String.valueOf(access),
                    msg,
                    String.valueOf(id_interno)
            };
            if (access == true){
                updateDataUserAnswers(id_interno);
            }
        } catch (Exception e) {
            Log.d("errorJson", String.valueOf(e));
            return values;
        }
        return values;
    }

    //Metodo de Inicializacion
    public DataBaseManager(Context context) {
        helper = new DBHelper(context);

        db = helper.getWritableDatabase();
    }
}
