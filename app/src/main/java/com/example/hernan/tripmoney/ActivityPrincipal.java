package com.example.hernan.tripmoney;

//https://www.youtube.com/watch?v=3dBZHtw_J9E

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ActivityPrincipal extends AppCompatActivity
{
    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;
    private static Cursor cursor_gastos;

    public ListView ListaPersonas;
    private Toolbar toolbar_MainActivity;
    private int Id_BD;
    private String Nombre_BD;
    private String Descripcion_BD;
    private float Debe_BD;
    private float Afavor_BD;
    private int  indice_buscador_usuarios=0;
    private int  indice_buscador_gastos=0;
    private int  AFavor_Total=0;

    private ListView ListaDatos;
    ArrayList<DatosListViewPrincipal> Lista;
    public static String Tipo_Fuente;
    public static SharedPreferences pref;

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

                finish();
                Intent ActivityAdd = new Intent(ActivityPrincipal.this, ActivityRegistroUsuarios.class);
                ActivityAdd.putExtra("Registro_interno",1);
                startActivity(ActivityAdd);

                break;

            // Aca va la settings
            case R.id.Editar:

                finish();
                Intent Activity2 = new Intent(ActivityPrincipal.this, ActivitySettings.class);
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

         switch (item.getItemId())
         {
            case R.id.Modificar:

                finish();
                Intent Activity_Main_Modificar = new Intent(ActivityPrincipal.this, ActivityModificar.class);
                // Le paso a traves de un intent el id del item que toque para modificarlo
                Activity_Main_Modificar.putExtra("ID_usuarios",Lista.get(info.position).getId());
                startActivity(Activity_Main_Modificar);

                return true;

            case R.id.Eliminar:

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
                builder.setMessage("Confirma borrar la base de datos de usuarios?");
                builder.setTitle("Esto es el titulo");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(ActivityPrincipal.this,"Registro eliminado",Toast.LENGTH_SHORT).show();
                        // Aca tengo que borrar la base de datos
                        manejador_db= new DataBaseManager(ActivityPrincipal.this);

                        // Elimino el registro de esa tabla
                        manejador_db.eliminar(Lista.get(info.position).getId());

                        manejador_db.CerrarBaseDatos();

                        Toast.makeText(ActivityPrincipal.this,"Base de Datos borrada",Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                        finish();
                        Intent Activity_Main = new Intent(ActivityPrincipal.this, ActivityLoginUsuario.class);
                        startActivity(Activity_Main);

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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

        toolbar_MainActivity = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_MainActivity);

        getSupportActionBar().setTitle("       T  R  I  P   M  O  N  E  Y");

     //  cuentas = new Cuentas();

        ListaDatos = (ListView)findViewById(R.id.ListaPersonas);

        Lista = new ArrayList<DatosListViewPrincipal>();

        // Aca tengo que levantar de la base de datos los usuarios
        // Abro la base de datos y tomo el cursor para ver mis usuarios y cargarlos en el listview
        manejador_db = new DataBaseManager(ActivityPrincipal.this);

        // Tomo el cursor de los gastos de cada persona
        cursor_usuarios = manejador_db.CargarCursor_Usuarios();

        cursor_usuarios.moveToFirst();

        if(cursor_usuarios != null && cursor_usuarios.getCount()>0)
        {
            indice_buscador_usuarios=0;
            do
                {
                    // Tomo el nombre para direccionarlo a la otra tabla y traerme los gastos
                    Nombre_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));

                    // Hago una query del usuario para traerme todos los datos y obtener el gasto total
                    cursor_gastos = manejador_db.Query_Gastos(Nombre_BD);

                    cursor_gastos.moveToFirst();

                    if(cursor_gastos != null && cursor_gastos.getCount()>0)
                    {
                        indice_buscador_gastos = 0;

                        AFavor_Total = 0;

                        do {
                            // Sumo el gasto total de ese usuario
                            AFavor_Total += cursor_gastos.getFloat(cursor_gastos.getColumnIndex("AFavor"));
                            indice_buscador_gastos++;
                            cursor_gastos.moveToNext();

                        } while (indice_buscador_gastos < cursor_gastos.getCount());

                        // Inserto en mi objeto para mostrar en el listview
                        Lista.add(new DatosListViewPrincipal(Nombre_BD, Debe_BD, AFavor_Total, R.mipmap.ic_launcher));
                    }

                    indice_buscador_usuarios++;
                    cursor_usuarios.moveToNext();

                }while (indice_buscador_usuarios <cursor_usuarios.getCount());

            manejador_db.CerrarBaseDatos();
            cursor_usuarios.close();
            cursor_gastos.close();
        }

        AdaptadorListViewPrincipal adaptador = new AdaptadorListViewPrincipal(getApplicationContext(),Lista);

        ListaDatos.setAdapter(adaptador);

        // Registro los controles para el menu contextual, detecta la pulsacion prolongada
        registerForContextMenu(ListaDatos);

        // Analizo el click del listview
        ListaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
                // Le paso el estado de este usuario si es que esta logueado para habilitarle el boton de agregar un gasto nuevo
                manejador_db = new DataBaseManager(ActivityPrincipal.this);

                // Hago una query con este usuario para saber si esta logueado
                cursor_usuarios = manejador_db.Query_Usuarios("nombre=?",Lista.get(position).getNombre());

                cursor_usuarios.moveToFirst();

                if(cursor_usuarios != null && cursor_usuarios.getCount()>0)
                {
                    String estado_logueado = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("uslogueado"));

                    finish();
                    Intent Activity2 = new Intent(ActivityPrincipal.this, ActivityTabs.class);
                    //     Activity2.putExtra("ID_gastos",Lista.get(position).getId());
                    Activity2.putExtra("Nombre_usu", Lista.get(position).getNombre());
                    Activity2.putExtra("Estado_logueo_usu", estado_logueado);
                    startActivity(Activity2);
                }
                else
                {
                    Toast.makeText(ActivityPrincipal.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
    //    builder.setMessage("Salir de la aplicacion?");
        builder.setTitle("Salir de la aplicacion?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Aca tengo que borrar la base de datos
                // Le paso el estado de este usuario si es que esta logueado para habilitarle el boton de agregar un gasto nuevo
                manejador_db = new DataBaseManager(ActivityPrincipal.this);

                // Hago una query con este usuario para saber que usuario esta loqueado
                cursor_usuarios = manejador_db.Query_Usuarios("uslogueado=?","SI");

                cursor_usuarios.moveToFirst();

                if(cursor_usuarios != null && cursor_usuarios.getCount()>0)
                {
                    int Id_BD = cursor_usuarios.getInt(cursor_usuarios.getColumnIndex("_id"));

                    String Usuario_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));

                    String Password_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("password"));

                    // Lo dejo como deslogueado al usuario
                    manejador_db.modificar_usuarios(Usuario_BD,Password_BD,"NO",Id_BD);

                    manejador_db.CerrarBaseDatos();
                    cursor_usuarios.close();
                }
                else
                {
                    Toast.makeText(ActivityPrincipal.this,"Error",Toast.LENGTH_SHORT).show();
                }

                dialog.cancel();

                finish();
                onBackPressed();

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //        prefs.setSelectable(false);
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
