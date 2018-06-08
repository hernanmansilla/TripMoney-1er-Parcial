package com.example.hernan.tripmoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Hernan on 18/3/2018.
 */

public class SQLite extends SQLiteOpenHelper
{
    public static final String NOMBRE_DB = "TripMoney8.basedatos";
    public static final int VERSION_DB=2;

   // String Tabla_Usuarios = "CREATE TABLE Usuarios ()"

    // Contructor de la clase
    public SQLite(Context context)
    {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    // Crea la tabla de la base de datos si no existe
    @Override
    public void onCreate (SQLiteDatabase db)
    {
 //       db.execSQL("DROP TABLE IF EXISTS " +DataBaseManager.NOMBRE_TABLA);
        db.execSQL(DataBaseManager.TABLA_GASTOS);
        db.execSQL(DataBaseManager.TABLA_USUARIOS);
    }

    // Metodo para actualizar las tablas cuando cambia de version la base de datos
    // La eliminamos y creamos una nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior , int versionNueva)
    {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(DataBaseManager.NOMBRE_TABLA_USUARIOS);

        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL(DataBaseManager.NOMBRE_TABLA_GASTOS);
    }

    // Agregamos Valor a la tabla
/*    public void agregarValor(float valor)
    {
        SQLiteDatabase bd = getWritableDatabase();

        // Preguntamos si se abrio correctamente la base de datos
        if(bd!=null)
        {
            // Insertamos valor
            bd.execSQL("INSERT INTO APAGAR VALUES('"+valor+"')");
            // Cerramos conexion
            bd.close();
        }


    }*/


}
