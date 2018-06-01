package com.example.hernan.tripmoney;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity
{
    private DataBaseManager db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Recibo los datos del MainActivity
    //    Bundle extras = getIntent().getExtras();
    //    String Title = extras.getString("Titulo");

        // Genero las tabs
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("USUARIO", res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("DESCRIPCIONES", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        // Abro la base de datos
      //  db = new DataBaseManager(this);

     //   cursor = db.CargarCursorAPagar();
/*
        if (cursor.getCount() > 0)
        {
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst())
            {
                float ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                float ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                Gaby_Debe.setText(String.valueOf(ValorGuardado_Debe));
                Gaby_AFavor.setText(String.valueOf(ValorGuardado_AFavor));

                cursor.moveToNext();

                ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                Hernan_Debe.setText(String.valueOf(ValorGuardado_Debe));
                Hernan_AFavor.setText(String.valueOf(ValorGuardado_AFavor));

                cursor.moveToNext();

                ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                German_Debe.setText(String.valueOf(ValorGuardado_Debe));
                German_AFavor.setText(String.valueOf(ValorGuardado_AFavor));

                // Cierro la base de datos
                cursor.close();
                db.CerrarBaseDatos();
            }
        }
*/
/*
        BotonAtras.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Vuelvo a la pantalla anterior
                onBackPressed();
            }
        });
*/
  /*      BotonBorrarBD.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.contexto_main.deleteDatabase("TripMoneybd");

                db = new DataBaseManager(MainActivity.contexto_main);

                cursor = db.CargarCursorAPagar();

                cursor.moveToFirst();

                db.insertar("gaby", (float) 0, (float) 0);
                cursor.moveToNext();
                db.insertar("hernan", (float) 0, (float) 0);
                cursor.moveToNext();
                db.insertar("ger", (float) 0, (float) 0);

      /*          cursor.moveToFirst();

                float ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                float ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                Gaby_Debe.setText(String.valueOf(ValorGuardado_Debe));
                Gaby_AFavor.setText(String.valueOf(ValorGuardado_AFavor));

                cursor.moveToNext();

                ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                Hernan_Debe.setText(String.valueOf(ValorGuardado_Debe));
                Hernan_AFavor.setText(String.valueOf(ValorGuardado_AFavor));

                cursor.moveToNext();

                ValorGuardado_Debe = cursor.getFloat(cursor.getColumnIndex("Debe"));
                ValorGuardado_AFavor = cursor.getFloat(cursor.getColumnIndex("AFavor"));
                German_Debe.setText(String.valueOf(ValorGuardado_Debe));
                German_AFavor.setText(String.valueOf(ValorGuardado_AFavor));


                // Cierro la base de datos
                cursor.close();
                db.CerrarBaseDatos();
            }
        });*/

    }




}
