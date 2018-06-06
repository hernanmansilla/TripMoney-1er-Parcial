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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;


public class ActivityTabs extends AppCompatActivity
{
    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;
    private static Cursor cursor_gastos;
    private float Gasto_nuevo;
    private Button Boton_agregar_gasto;
    private EditText Descripcion_gasto;
    private EditText Gasto_nuevo_string;
    private TextView Descripcion_gasto_text;
    private String Nombre_BD;
    private String Descripcion;
    private String Descripcion_BD;
    private float Debe_BD;
    private float Afavor_BD;
    private Toolbar toolbar_Main2Activity;
    private ListView ListaDescripciones;
    ArrayList<DatosListViewDescripcion> ListaDesc;
    private int indice_buscador_descripciones=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Gasto_nuevo_string = (EditText) findViewById(R.id.Gasto_nuevo);
        Descripcion_gasto = (EditText) findViewById(R.id.Descripcion);
        Boton_agregar_gasto = (Button) findViewById(R.id.Boton_agregar_gasto);
      //  Descripcion_gasto_text = (TextView) findViewById(R.id.Descripcion_text_tab2);
        toolbar_Main2Activity = (Toolbar) findViewById(R.id.toolbar);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        String Usuario_Activo = extras.getString("Nombre_usu");

        setSupportActionBar(toolbar_Main2Activity);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_Main2Activity.setSubtitle(Usuario_Activo);

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
        spec.setIndicator("DETALLES GASTOS", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        Bundle extras1 = getIntent().getExtras();
        assert extras1 != null;
        String estado_logueo = extras1.getString("Estado_logueo_usu");

        // Esto quiere decir que el usuario no es el logueado, no le doy permiso de agregar un gasto nuevo
        if(estado_logueo.equals("NO"))
            Boton_agregar_gasto.setEnabled(false);
        else
            Boton_agregar_gasto.setEnabled(true);

        Boton_agregar_gasto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Gasto = Gasto_nuevo_string.getText().toString();
                Gasto_nuevo = Float.parseFloat(Gasto);

                Descripcion = Descripcion_gasto.getText().toString();

                if((Gasto_nuevo !=0) && (Descripcion != null) && !Descripcion.isEmpty())
                {
                    // Recibo los datos del MainActivity
                    Bundle extras1 = getIntent().getExtras();
                    assert extras1 != null;
                    String nombre_usuario = extras1.getString("Nombre_usu");

                    manejador_db = new DataBaseManager(ActivityTabs.this);
                    cursor_gastos = manejador_db.CargarCursor_Gastos();

                    if (cursor_gastos != null && cursor_gastos.getCount() > 0)
                    {
                        // Agrego el nuevo registro al final de la tabla
                        cursor_gastos.moveToLast();

                        manejador_db.insertar_gastos(nombre_usuario, Descripcion, Debe_BD,  Gasto_nuevo);
                    }
                    manejador_db.CerrarBaseDatos();
                    cursor_gastos.close();

                    finish();
                    Intent Activity_Main_Modificar = new Intent(ActivityTabs.this, ActivityPrincipal.class);
                    startActivity(Activity_Main_Modificar);

                    Toast.makeText(ActivityTabs.this, "Gasto añadido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabID)
            {
               if(tabID == "mitab2")
               {
                   ListaDescripciones = (ListView)findViewById(R.id.ListaDescripciones);

                   ListaDesc = new ArrayList<DatosListViewDescripcion>();

                   manejador_db = new DataBaseManager(ActivityTabs.this);

                   Bundle extras = getIntent().getExtras();
                   assert extras != null;
                   String Nombre_usu = extras.getString("Nombre_usu");

                   // Hago una query del usuario para traerme la descripcion
                   cursor_gastos = manejador_db.Query_Gastos(Nombre_usu);

                   cursor_gastos.moveToFirst();

                   // Lo comparo con 1 para saltearme el hola
                   if (cursor_gastos != null && cursor_gastos.getCount() > 1)
                   {
                       indice_buscador_descripciones = 0;

                       // Para saltearme el primer registro que es el "hola"
                       cursor_gastos.moveToNext();
                       indice_buscador_descripciones++;

                       do {
                           // Tomo los datos de la tabla Gastos
                           Descripcion_BD = cursor_gastos.getString(cursor_gastos.getColumnIndex("descripcion"));

                           Afavor_BD = cursor_gastos.getFloat(cursor_gastos.getColumnIndex("AFavor"));

                           // Inserto en mi objeto para mostrar en el listview
                           ListaDesc.add(new DatosListViewDescripcion(Nombre_usu, Descripcion_BD, Afavor_BD));

                           indice_buscador_descripciones++;

                           cursor_gastos.moveToNext();

                       }while(indice_buscador_descripciones < cursor_gastos.getCount());
                   }

                   AdaptadorListviewDescripcion adaptador1 = new AdaptadorListviewDescripcion(getApplicationContext(),ListaDesc);

                   ListaDescripciones.setAdapter(adaptador1);

                   // Registro los controles para el menu contextual, detecta la pulsacion prolongada
                   registerForContextMenu(ListaDescripciones);

                   manejador_db.CerrarBaseDatos();
                   cursor_gastos.close();
               }
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

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        finish();
        Intent Activity2 = new Intent(ActivityTabs.this, ActivityPrincipal.class);
        startActivity(Activity2);
        super.onBackPressed();
    }
}
