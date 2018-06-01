package com.example.hernan.tripmoney;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Hernan on 28/3/2018.
 */

public class Cuentas
{
 //   public TextView GabyGasto;
 //   public TextView HerGasto;
 //   public TextView GerGasto;

    private DataBaseManager db;
    private Cursor cursor;
//    private Context contexto = ;

    // Constructor de la Clase
    public Cuentas()
    {
    }

    public void Calculo_Cuentas(String nombre, float Valor_Gastado)
    {
        float ValorGuardado_Debe;
        float ValorGuardado_AFavor;

        // Abro la base de datos
     //   db = new DataBaseManager(MainActivity.contexto_main);

        // Divido por 3 porque somos 3 personas
        Valor_Gastado /= 3;

        // Instanciamos la clase de la base de datos y automaticamente esta crea la tabla
     //   cursor = db.CargarCursorAPagar();

        if (cursor.getCount() > 0)
        {
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst())
            {
                switch(nombre)
                {
                    case "gaby":

                        ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                        ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));

                        // En el caso de que lo que pago sea menor a lo que debe
                        if(ValorGuardado_Debe > (Valor_Gastado*2))
                        {
                            ValorGuardado_Debe -= (Valor_Gastado*2);

                            // Guardo los valores
                        //    db.modificar("gaby", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta hernan
                            cursor.moveToNext();

                            ReCalculo_Tabla("hernan",Valor_Gastado);

                            // Muevo el Cursor hasta german
                            cursor.moveToNext();

                            ReCalculo_Tabla("ger",Valor_Gastado);

                        }
                        // En caso de que si pago quede con saldo a Favor
                        else
                        {
                            float Valor_Gastado_aux = Valor_Gastado;

                            Valor_Gastado = Valor_Gastado*2;

                            Valor_Gastado -= ValorGuardado_Debe;
                            ValorGuardado_AFavor += Valor_Gastado;
                            ValorGuardado_Debe=0;

                            // Guardo los valores
                      //      db.modificar("gaby", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta hernan
                            cursor.moveToNext();

                            ReCalculo_Tabla("hernan",Valor_Gastado_aux);

                            // Muevo el Cursor hasta german
                            cursor.moveToNext();

                            ReCalculo_Tabla("ger",Valor_Gastado_aux);
                        }

                        break;

                    case "hernan":

                        // Muevo el Cursor hasta hernan
                        cursor.moveToNext();

                        ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                        ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));

                        // En el caso de que lo que pago sea menor a lo que debe
                        if(ValorGuardado_Debe > (Valor_Gastado*2))
                        {
                            ValorGuardado_Debe -= (Valor_Gastado*2);

                            // Guardo los valores
                     //       db.modificar("hernan", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta german
                            cursor.moveToNext();

                            ReCalculo_Tabla("ger",Valor_Gastado);

                            // Muevo el Cursor hasta gaby
                            cursor.moveToFirst();

                            ReCalculo_Tabla("gaby",Valor_Gastado);

                        }
                        // En caso de que si pago quede con saldo a Favor
                        else
                        {
                            float Valor_Gastado_aux = Valor_Gastado;

                            Valor_Gastado = Valor_Gastado*2;

                            Valor_Gastado -= ValorGuardado_Debe;
                            ValorGuardado_AFavor += Valor_Gastado;
                            ValorGuardado_Debe=0;

                            // Guardo los valores
                    //        db.modificar("hernan", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta german
                            cursor.moveToNext();

                            ReCalculo_Tabla("ger",Valor_Gastado_aux);

                            // Muevo el Cursor hasta gaby
                            cursor.moveToFirst();

                            ReCalculo_Tabla("gaby",Valor_Gastado_aux);
                        }
                        break;

                    case "ger":

                        // Muevo el Cursor hasta german
                        cursor.moveToNext();
                        cursor.moveToNext();

                        ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                        ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));

                        // En el caso de que lo que pago sea menor a lo que debe
                        if(ValorGuardado_Debe > (Valor_Gastado*2))
                        {
                            ValorGuardado_Debe -= (Valor_Gastado*2);

                            // Guardo los valores
                   //         db.modificar("ger", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta gaby
                            cursor.moveToFirst();

                            ReCalculo_Tabla("gaby",Valor_Gastado);

                            // Muevo el Cursor hasta hernan
                            cursor.moveToNext();

                            ReCalculo_Tabla("hernan",Valor_Gastado);

                        }
                        // En caso de que si pago quede con saldo a Favor
                        else
                        {
                            float Valor_Gastado_aux = Valor_Gastado;

                            Valor_Gastado = Valor_Gastado*2;

                            Valor_Gastado -= ValorGuardado_Debe;
                            ValorGuardado_AFavor += Valor_Gastado;
                            ValorGuardado_Debe=0;

                            // Guardo los valores
                  //          db.modificar("ger", ValorGuardado_Debe,ValorGuardado_AFavor);

                            // Muevo el Cursor hasta gaby
                            cursor.moveToFirst();

                            ReCalculo_Tabla("gaby",Valor_Gastado_aux);

                            // Muevo el Cursor hasta gaby
                            cursor.moveToNext();

                            ReCalculo_Tabla("hernan",Valor_Gastado_aux);
                        }
                        break;


                }
            }
        }
        cursor.close();
        db.CerrarBaseDatos();

    }

    public void ReCalculo_Tabla(String nombre, float Valor_Gastado)
    {
        float ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
        float ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));

     //   float ValorGuardado_Debe = (float) (Math.round (ValorGuardado_Debe_Sin_Redondear * 100)/100.0);
       // float ValorGuardado_AFavor = (float) (Math.round (ValorGuardado_AFavor_Sin_Redondear * 100)/100.0);

        if(ValorGuardado_AFavor > Valor_Gastado)
        {
            ValorGuardado_AFavor -= Valor_Gastado;
        }
        else
        {
            Valor_Gastado -= ValorGuardado_AFavor;
            ValorGuardado_Debe += Valor_Gastado;
            ValorGuardado_AFavor=0;
        }

        switch (nombre)
        {
            case "gaby":

            //    db.modificar("gaby", ValorGuardado_Debe,ValorGuardado_AFavor);
                break;

            case "hernan":

             //   db.modificar("hernan", ValorGuardado_Debe,ValorGuardado_AFavor);
                break;

            case "ger":

             //   db.modificar("ger", ValorGuardado_Debe,ValorGuardado_AFavor);
                break;
        }

    }
}
