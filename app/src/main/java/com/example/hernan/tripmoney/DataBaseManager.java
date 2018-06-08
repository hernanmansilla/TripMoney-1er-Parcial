package com.example.hernan.tripmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hernan on 24/3/2018.
 */

public class DataBaseManager
{
    public static final String NOMBRE_TABLA_GASTOS = "gastos";
    public static final String NOMBRE_TABLA_USUARIOS = "usuarios";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_DESCRIPCION = "descripcion";
    public static final String COLUMNA_DEBE = "Debe";
    public static final String COLUMNA_AFAVOR = "AFavor";
    public static final String COLUMNA_PASSWORD = "password";
    public static final String COLUMNA_USUARIO_LOGUEADO = "uslogueado";

    public static final String TABLA_GASTOS = "create table " +NOMBRE_TABLA_GASTOS+ " ("
            + COLUMNA_ID + " integer primary key autoincrement,"
            + COLUMNA_NOMBRE + " text not null,"
            + COLUMNA_DESCRIPCION + " text not null,"
            + COLUMNA_DEBE + " float,"
            + COLUMNA_AFAVOR + " float);";

    public static final String TABLA_USUARIOS = "create table " +NOMBRE_TABLA_USUARIOS+ " ("
            + COLUMNA_ID + " integer primary key autoincrement,"
            + COLUMNA_NOMBRE + " text not null,"
            + COLUMNA_PASSWORD + " text not null,"
            + COLUMNA_USUARIO_LOGUEADO + " text not null);";

    private SQLiteDatabase db;
    private SQLite usuario;

    // Constructor de mi clase
    public DataBaseManager(Context context)
    {
        usuario = new SQLite(context);
        db = usuario.getWritableDatabase();
    }

    // Actualizar datos de la tabla USUARIOS
    public ContentValues generarContentValues_usuarios(String nombre, String pass, String logueado)
    {
        ContentValues valores = new ContentValues();

        valores.put(COLUMNA_NOMBRE,nombre);
        valores.put(COLUMNA_PASSWORD,pass);
        valores.put(COLUMNA_USUARIO_LOGUEADO,logueado);

        return valores;
    }

    // Actualizar datos de la tabla GASTOS
    public ContentValues generarContentValues_gastos(String nombre, String desc, float debe,float afavor)
    {
        ContentValues valores = new ContentValues();

        valores.put(COLUMNA_NOMBRE,nombre);
        valores.put(COLUMNA_DESCRIPCION,desc);
        valores.put(COLUMNA_DEBE,debe);
        valores.put(COLUMNA_AFAVOR,afavor);

        return valores;
    }

    // Inserto un dato en la tabla Usuarios
    public void insertar_usuarios(String nombre,String pass, String logueado)
    {
        db.insert(NOMBRE_TABLA_USUARIOS,null,generarContentValues_usuarios(nombre,pass,logueado));
    }

    // Inserto un dato en la tabla Gastos
    public void insertar_gastos(String nombre, String desc, float debe, float afavor)
    {
        db.insert(NOMBRE_TABLA_GASTOS,null,generarContentValues_gastos(nombre,desc,debe,afavor));
    }


    public void eliminar_Usuarios(int id)
    {
        db.delete(NOMBRE_TABLA_USUARIOS, COLUMNA_ID + "=" + id,null);
    }

    public void eliminar_Gastos(int id)
    {
        db.delete(NOMBRE_TABLA_GASTOS, COLUMNA_ID + "=" + id,null);
    }

    public void modificar_usuarios(String nombre,String contraseña, String logueado, int id)
    {
        //   float ValorGuardado_Debe = (float) (Math.round (NuevoDebe * 100)/100.0);
        //   float ValorGuardado_AFavor = (float) (Math.round (NuevoAFavor * 100)/100.0);

        db.update(NOMBRE_TABLA_USUARIOS, generarContentValues_usuarios(nombre,contraseña,logueado),COLUMNA_ID + "=" + id,null);
    }

    public void modificar_gastos(String nombre,String descripcion, float NuevoDebe, float NuevoAFavor, int id)
    {
     //   float ValorGuardado_Debe = (float) (Math.round (NuevoDebe * 100)/100.0);
     //   float ValorGuardado_AFavor = (float) (Math.round (NuevoAFavor * 100)/100.0);

        db.update(NOMBRE_TABLA_GASTOS, generarContentValues_gastos(nombre,descripcion,NuevoDebe,NuevoAFavor),COLUMNA_ID + "=" + id,null);
    }

    public Cursor CargarCursor_Usuarios()
    {
        String[] columnas = new String[]{COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_PASSWORD,COLUMNA_USUARIO_LOGUEADO};

        return db.query(NOMBRE_TABLA_USUARIOS,columnas,null,null,null,null,null);
    }

    public Cursor CargarCursor_Gastos()
    {
        String[] columnas = new String[]{COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_DESCRIPCION, COLUMNA_DEBE, COLUMNA_AFAVOR};

        return db.query(NOMBRE_TABLA_GASTOS,columnas,null,null,null,null,null);
    }

    public Cursor Query_Usuarios(String where, String argumento)
    {
        String[] campos_a_recuperar = new String[] {COLUMNA_ID,COLUMNA_NOMBRE,COLUMNA_PASSWORD,COLUMNA_USUARIO_LOGUEADO};
        String[] Where_argumento = new String[] {argumento};

        return db.query(NOMBRE_TABLA_USUARIOS, campos_a_recuperar, where,Where_argumento , null, null, null);
    }

    public Cursor Query_Gastos(String usuario)
    {
        String[] campos_a_recuperar = new String[] {COLUMNA_ID,COLUMNA_NOMBRE,COLUMNA_DESCRIPCION,COLUMNA_DEBE,COLUMNA_AFAVOR};
        String[] Where_usuarios = new String[] {usuario};

        return db.query(NOMBRE_TABLA_GASTOS, campos_a_recuperar, "nombre=?",Where_usuarios , null, null, null);
    }

    public void CerrarBaseDatos()
    {
        db.close();
    }

    public void EliminarTablaBaseDatos_Usuarios()
    {
        // Elimino la tabla Usuarios
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(DataBaseManager.TABLA_USUARIOS);

        // Tambien elimino la tabla gastos porque si no tengo usuarios no tengo gastos
        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL(DataBaseManager.TABLA_GASTOS);
     //   db.execSQL(DataBaseManager.NOMBRE_TABLA_USUARIOS);
    }

    public void EliminarTablaBaseDatos_Gastos()
    {
        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL(DataBaseManager.TABLA_GASTOS);
    //    db.execSQL(DataBaseManager.NOMBRE_TABLA_GASTOS);
    }

}
