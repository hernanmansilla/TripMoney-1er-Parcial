package com.example.hernan.tripmoney;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hernan on 18/3/2018.
 */

//**********************************************************************************************
// Clase creadora de la base de datos
//**********************************************************************************************
public class SQLite extends SQLiteOpenHelper
{
    public static final String NOMBRE_DB = "TripMoney8.basedatos";
    public static final int VERSION_DB=2;

    //**********************************************************************************************
    // Constructor de la clase
    //**********************************************************************************************
    public SQLite(Context context)
    {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    //**********************************************************************************************
    // Crea las tablas de la base de datos
    //**********************************************************************************************
    @Override
    public void onCreate (SQLiteDatabase db)
    {
        db.execSQL(DataBaseManager.TABLA_GASTOS);
        db.execSQL(DataBaseManager.TABLA_USUARIOS);
    }

    //**********************************************************************************************
    // Actualizo las tablas
    //**********************************************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior , int versionNueva)
    {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(DataBaseManager.NOMBRE_TABLA_USUARIOS);

        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL(DataBaseManager.NOMBRE_TABLA_GASTOS);
    }
}
