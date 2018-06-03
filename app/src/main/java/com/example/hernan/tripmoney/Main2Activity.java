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
    private float Gasto_nuevo;
    private Button Boton_agregar_gasto;
    private EditText Descripcion_gasto;
    private EditText Gasto_nuevo_string;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Gasto_nuevo_string = (EditText) findViewById(R.id.Gasto_nuevo);
        Descripcion_gasto = (EditText) findViewById(R.id.Descripcion);
        Boton_agregar_gasto = (Button) findViewById(R.id.Boton_agregar_gasto);

        // Recibo los datos del MainActivity
    //    Bundle extras = getIntent().getExtras();
    //    String Title = extras.getString("Titulo");

        // Genero las tabs
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("NUEVO GASTO", res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("DESCRIPCIONES", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        Boton_agregar_gasto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Gasto_nuevo = 0;
            }
        });

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s)
            {
                Gasto_nuevo = 1;
            }
        });

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
