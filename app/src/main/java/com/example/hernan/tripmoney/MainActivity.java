package com.example.hernan.tripmoney;

//https://www.youtube.com/watch?v=3dBZHtw_J9E
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.hernan.tripmoney.LoginUsuario.manejador_db_usuarios;
import static com.example.hernan.tripmoney.LoginUsuario.cursor_usuarios;
import static com.example.hernan.tripmoney.LoginUsuario.manejador_db_gastos;
import static com.example.hernan.tripmoney.LoginUsuario.cursor_gastos;
import static com.example.hernan.tripmoney.R.layout.listitem_titular;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
{

    public ListView ListaPersonas;
    private Toolbar toolbar;
    private int Id_BD;
    private String Nombre_BD;
    private String Descripcion_BD;
    private float Debe_BD;
    private float Afavor_BD;

    public ListView ListaDatos;
    ArrayList<Datos> Lista;

    // Inicializo los datos de mi item seleccionado dentro de la lista
    //static public Titular[] Personas = new Titular[10];
    //{
         //   new Titular("HERNAN", "Subtítulo largo 1", R.mipmap.ic_launcher),
         //   new Titular("GERMAN", "Subtítulo largo 2",R.mipmap.ic_launcher),

    //};

    // Inflo el toolbar con los botones
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    // Analizo los botones del toolbar
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.Agregar:

                Intent ActivityAdd = new Intent(MainActivity.this, MainActivityAdd.class);
                startActivity(ActivityAdd);

                break;

            // Aca va la settings
            case R.id.Editar:

                Intent Activity2 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(Activity2);

                break;

        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater Inflater = getMenuInflater();

        switch(v.getId())
        {
            case R.id.ListaPersonas:
                Inflater.inflate(R.menu.menu_contextual_listview,menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
     {
         final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

      //   final int posicion = (int) ListaDatos.getItemAtPosition(info.position);

         final int posicion = info.position;

         switch (item.getItemId())
         {
            case R.id.Modificar:

                Toast.makeText(MainActivity.this, "Modificado", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Eliminar:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Confirma borrar la base de datos de usuarios?");
                builder.setTitle("Esto es el titulo");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this,"Registro eliminado",Toast.LENGTH_SHORT).show();
                        // Aca tengo que borrar la base de datos
                        manejador_db_usuarios= new DataBaseManager(MainActivity.this);

                        // Elimino el registro de esa tabla
                        manejador_db_usuarios.eliminar(posicion);

                        manejador_db_usuarios.CerrarBaseDatos();

                        Toast.makeText(MainActivity.this,"Base de Datos borrada",Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                        finish();
                        Intent Activity_Main = new Intent(MainActivity.this, LoginUsuario.class);
                        startActivity(Activity_Main);

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //        prefs.setSelectable(false);
                        dialog.cancel();
                        //       finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

              //  Toast.makeText(MainActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     //   cuentas = new Cuentas();

        ListaDatos = (ListView)findViewById(R.id.ListaPersonas);

        Lista = new ArrayList<Datos>();

        // Aca tengo que levantar de la base de datos los usuarios

        // Abro la base de datos y tomo el cursor para ver mis usuarios y cargarlos en el listview
        manejador_db_gastos = new DataBaseManager(MainActivity.this);
        cursor_gastos = manejador_db_gastos.CargarCursor_Gastos();

        cursor_gastos.moveToFirst();

        if(cursor_gastos != null && cursor_gastos.getCount()>0)
        {
            do
                {
                    // Tomo los datos de la tabla Gastos
                    Id_BD = cursor_gastos.getInt(cursor_gastos.getColumnIndex("_id"));
                    Nombre_BD = cursor_gastos.getString(cursor_gastos.getColumnIndex("nombre"));
                    Descripcion_BD = cursor_gastos.getString(cursor_gastos.getColumnIndex("descripcion"));
                    Debe_BD = cursor_gastos.getFloat(cursor_gastos.getColumnIndex("Debe"));
                    Afavor_BD = cursor_gastos.getFloat(cursor_gastos.getColumnIndex("AFavor"));

                    // Inserto en mi objeto para mostrar en el listview
                    Lista.add(new Datos(Id_BD,Nombre_BD,Debe_BD,Afavor_BD,R.mipmap.ic_launcher));

                    cursor_gastos.moveToNext();

                }while (Id_BD <cursor_gastos.getCount());

            manejador_db_gastos.CerrarBaseDatos();
            cursor_gastos.close();
        }

        Adaptador adaptador = new Adaptador(getApplicationContext(),Lista);

        ListaDatos.setAdapter(adaptador);

        // Registro los controles para el menu contextual, detecta la pulsacion prolongada
        registerForContextMenu(ListaDatos);

        // Analizo el click del listview
        ListaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {

                //Alternativa 1:
            //    String opcionSeleccionada = ((Titular)a.getItemAtPosition(position)).getTitulo();
                Datos obj = (Datos) a.getItemAtPosition(position);

                Intent Activity2 = new Intent(MainActivity.this, Main2Activity.class);
        //        Activity2.putExtra("Titulo",Personas[position].getTitulo());
                startActivity(Activity2);

            }
        });


/*
        // Boton que agrega valores a la tabla de Gaby
        BotonAgregar_Gabriel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Transformo el texto editable en float
                String number = TextoEditable_Gabriel.getText().toString();

                if (number.length() > 0)
                {
                    float ValorAinsertar = Float.parseFloat(number);

                    // Si es un valor positivo lo inserto en el registro de gaby
                    if (ValorAinsertar > 0)
                    {
                        TextoEditable_Gabriel.setText("");

                        cuentas.Calculo_Cuentas("gaby",ValorAinsertar);
                    }
                }
            }
        });
*/
    }
/*
    public class AdaptadorTitulares extends ArrayAdapter<Titular>
    {
        public AdaptadorTitulares(Context context, Titular[] datos)
        {
            super(context, R.layout.listitem_titular, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(listitem_titular, null);

            TextView Lista_Titulo = (TextView)item.findViewById(R.id.ListaTitulo);
            Lista_Titulo.setText(Personas[position].getTitulo());

            TextView Lista_Subtitulo = (TextView)item.findViewById(R.id.ListaSubTitulo);
            Lista_Subtitulo.setText(Personas[position].getSubtitulo());

            ImageView Imagen_Titulo = (ImageView)item.findViewById(R.id.imageView);
            Imagen_Titulo.setImageResource(Personas[position].getImagen());

            return(item);
        }
    }*/
}
