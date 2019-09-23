package com.sepankasuite.sepankaplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;

public class DataBaseManager {

    //Instancia de las clases
    private DBHelper helper;
    private SQLiteDatabase db;

    //Variables de configuracion de servicios
    public  static final String url = "http://ec2-52-91-17-128.compute-1.amazonaws.com/php_maraton/";
    //public  static final String url = "http://192.168.1.12/php_maraton/";
    public  static final String pathLogin = "GetDataLogin.php";
    public  static final String pathQuestions = "getDataQuestions.php";
    public  static final String pathSignup = "putDataSignup.php";

    /*Esta clase es la encargada de hacer movimientos en la DB*/

    /* *************************** ESTRUCTURA DE LA DB *********************************** */
    //Nombre de la tabla usuarios
    public static final String TABLE_USERS = "users";
    //Nombre d ela tabla de preguntas
    public static final String TABLE_QUESTIONS = "questions";
    //Nombre d ela tabla de respuestas de las preguntas
    public static final String TABLE_QUESTIONS_ANSWERS = "questions_answers";
    //Nombre d ela tabla de respuestas de los usuarios
    public static final String TABLE_ANSWERS = "user_answers";

    //Nombre de campos server
    public static final String CN_ID = "_id";
    //Nombre de campos usuarios
    public static final String CN_USER = "email";
    public static final String CN_PASSWORD = "password";
    public static final String CN_STATE = "state";

    /* ************* TABLAS ***************** */

    //Sentencia para crear la tabla usuarios
    public static final String CREATE_TABLE_USERS = "create table "+TABLE_USERS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_USER + " text not null,"
            + CN_PASSWORD + " text not null,"
            + CN_STATE + " text not null);";

    /* ****************** METODOS DE INSERTAR ************************** */

    //Metodo para insertar usuarios
    public void InsertParamsUsers(int id, String user, String password, String type) {
        //Instruccion para insertar en android
        db.insert(TABLE_USERS, null, generarContentValuesUsers(id, user, password, type));
    }

    /* ****************** FIN METODOS DE INSERTAR ************************** */

    /* ****************** METODOS DE BORRAR ************************** */

    //Borrar el contenido de la tabla usuarios
    public void clearDataUsers(){
        db.delete(TABLE_USERS, null, null);
    }

    /* ****************** FIN METODOS DE BORRAR ************************** */

    /* ****************** METODOS DE SELECCIONAR ************************** */

    public Cursor selectDataTipoUsuario() {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_STATE, CN_USER};

        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_USERS, columnas, null, null, null, null, null);
    }

    /* ****************** FIN METODOS DE SELECCIONAR ************************** */

    //#########################    CONTENEDORES   ###############################################


    //Metodo contenedor de valores USUARIOS
    private ContentValues generarContentValuesUsers(int id, String user, String password, String type) {
        ContentValues values = new ContentValues();
        values.put(CN_ID, id);
        values.put(CN_USER, user);
        values.put(CN_PASSWORD, password);
        values.put(CN_STATE, type);

        return values;
    }

    //#########################    FIN CONTENEDORES   ###############################################

    //########################### SERVER OPERATIONS ####################################################

    //Metodo para leer respuesta del login en el server
    public int obtDatosJSONLogin(String response) {
        int acceso = 0;
        String perfil = "";
        String correo = "";
        try {
            clearDataUsers();
            //resibimos el arreglo de tipo JSON en una variable JSON
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0;i<=jsonArray.length();i++) {
                // Insertar datos del tipo de usuario para saber que informacion mostrar
                acceso = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
                perfil = jsonArray.getJSONObject(i).getString("perfil");
                correo = jsonArray.getJSONObject(i).getString("correo");
                //Log.d("perfil", perfil);
                InsertParamsUsers(acceso, correo, "", perfil);
            }
        } catch (Exception e) {
            return acceso;
        }
        return acceso;
    }

    //Metodo para descargar clientes
    public String obtDatosJSON(String response) {
        String cadena = "";
        try {
            //clearDataDatos();
            //resibimos el arreglo de tipo JSON en una variable JSON
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0;i<=jsonArray.length();i++) {
                String valor="NO";
                String ganador = jsonArray.getJSONObject(i).getString("fecha_tag");
                if (!ganador.equals("null")){
                    valor = "SI";
                }
                /*InsertParamsDatos(jsonArray.getJSONObject(i).getString("NOMBRE"),
                        jsonArray.getJSONObject(i).getString("APELLIDO_PATERNO"),
                        jsonArray.getJSONObject(i).getString("APELLIDO_MATERNO"),
                        jsonArray.getJSONObject(i).getString("FECHA_NACIMIENTO"),
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("EDAD")),
                        jsonArray.getJSONObject(i).getString("SEXO"),
                        Integer.parseInt(jsonArray.getJSONObject(i).getString("NUMERO_COMPETIDOR")),
                        jsonArray.getJSONObject(i).getString("CATEGORIA"),
                        jsonArray.getJSONObject(i).getString("TIEMPO_ESTIMADO"),
                        jsonArray.getJSONObject(i).getString("EVENTO"),
                        jsonArray.getJSONObject(i).getString("ID_NFC"),
                        jsonArray.getJSONObject(i).getString("TIPO_SANGRE"),
                        jsonArray.getJSONObject(i).getString("ALERGIAS"),
                        jsonArray.getJSONObject(i).getString("PADECIMIENTO"),
                        jsonArray.getJSONObject(i).getString("STATUS_MEDICAMENTO"),
                        jsonArray.getJSONObject(i).getString("NOMBRE_MEDICAMENTO"),
                        jsonArray.getJSONObject(i).getString("ANTECEDENTES"),
                        jsonArray.getJSONObject(i).getString("COMENTARIOS"),
                        jsonArray.getJSONObject(i).getString("SEGURO"),
                        jsonArray.getJSONObject(i).getString("SEGURIDAD"),
                        jsonArray.getJSONObject(i).getString("CONTACTO_EMERGENCIA"),
                        jsonArray.getJSONObject(i).getString("TELEFONO_CONTACTO"),
                        jsonArray.getJSONObject(i).getString("DONADOR"),
                        jsonArray.getJSONObject(i).getString("CANCION_FAVORITA"),
                        valor
                );*/
            }

        } catch (Exception e) {
            return cadena;
        }
        return cadena;
    }

    //Metodo de Inicializacion
    public DataBaseManager(Context context) {
        helper = new DBHelper(context);

        db = helper.getWritableDatabase();
    }
}
