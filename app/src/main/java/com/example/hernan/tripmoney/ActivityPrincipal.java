package com.example.hernan.tripmoney;

//https://www.youtube.com/watch?v=3dBZHtw_J9E

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    private int  indice_buscador_usuarios=0;
    private int  indice_buscador_gastos=0;
    private int  AFavor_Total=0;
    private ListView ListaUsuarios;
    private int indice_eliminar_gastos;
    private String Usuario_Logueado_BD;

    ArrayList<DatosListViewPrincipal> ListaDatos;

    //*****************************************************************************
    // Inflo el toolbar con los botones
    //*****************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    //*****************************************************************************
    // Analizo los botones del toolbar
    //*****************************************************************************
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            // Boton para agregar un nuevo Usuario
            case R.id.Agregar:

                // Salto a otra activity para agregar un nuevo Usuario
                finish();
                Intent ActivityAdd = new Intent(ActivityPrincipal.this, ActivityRegistroUsuarios.class);
                ActivityAdd.putExtra("Registro_interno",1);
                startActivity(ActivityAdd);

                break;

            // Boton para acceder a la activity para la configuracion de la aplicacion
            case R.id.Settings:

                // Salto a otra activity para modificar la configuracion de la aplicacion
                finish();
                Intent Activity2 = new Intent(ActivityPrincipal.this, ActivitySettings.class);
                startActivity(Activity2);

                break;
        }
        return true;
    }

    //*****************************************************************************
    // Inflo el menu contextual cuando se mantiene presionado un item del Listview
    //*****************************************************************************
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

    //********************************************************************************
    // Funcion que atiene la seleccion de algun item del menu contextual
    //********************************************************************************
    @Override
    public boolean onContextItemSelected(MenuItem item)
     {
         // Obtengo la informacion del item seleccionado
         final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

         // Instancio un objeto para manejar la base de datos
         manejador_db = new DataBaseManager(ActivityPrincipal.this);

         // Hago una consulta a la base de datos con el nombre que corresponde al item que seleccione
         cursor_usuarios = manejador_db.Query_Usuarios("nombre=?",ListaDatos.get(info.position).getNombre());

         cursor_usuarios.moveToFirst();

         if((cursor_usuarios != null && cursor_usuarios.getCount()>0))
         {
             // Consulto si el usuario seleccionado fue logueado para que te permita hacer modificaciones sobre ese usuario
             Usuario_Logueado_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("uslogueado"));

             // Si es el logueado le permito hacer acciones sobre el menu contextual
             if(Usuario_Logueado_BD.equals("SI"))
             {
                 switch (item.getItemId())
                 {
                     case R.id.Modificar:

                         finish();
                         Intent Activity_Main_Modificar = new Intent(ActivityPrincipal.this, ActivityModificarUsuario.class);
                         // Le paso a traves de un intent el usuario del item que toque para modificarlo
                         Activity_Main_Modificar.putExtra("Usuario_press",ListaDatos.get(info.position).getNombre());
                         startActivity(Activity_Main_Modificar);

                         return true;

                     case R.id.Eliminar:

                         // Genero un cuadro de alerta para preguntar si se quiere eliminar el usuario
                         AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
                         builder.setMessage("Confirma borrar este usuario?");
                         builder.setTitle(ListaDatos.get(info.position).getNombre());

                         // En caso de presionar SI, entro a esta funcion
                         builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                         {
                             @Override
                             public void onClick(DialogInterface dialog, int which)
                             {
                                 // Obtengo el id del nombre
                                 Id_BD = cursor_usuarios.getInt(cursor_usuarios.getColumnIndex("_id"));

                                 // Elimino el registro de la tabla de usuarios
                                 manejador_db.eliminar_Usuarios(Id_BD);

                                 // Hago una query para traerme todos los id que corresponden al usuario
                                 cursor_gastos = manejador_db.Query_Gastos(ListaDatos.get(info.position).getNombre());

                                 if((cursor_gastos != null && cursor_gastos.getCount()>0))
                                 {
                                     indice_eliminar_gastos=0;

                                     cursor_gastos.moveToFirst();

                                     do {
                                         // Consulto todos los id del nombre
                                         Id_BD = cursor_gastos.getInt(cursor_gastos.getColumnIndex("_id"));

                                         // Elimino de la tabla el registro del gasto
                                         manejador_db.eliminar_Gastos(Id_BD);

                                         // Incremento el indice
                                         cursor_gastos.moveToNext();
                                         indice_eliminar_gastos++;

                                     }while(indice_eliminar_gastos < cursor_gastos.getCount());
                                 }

                                 // Cierro la base de datos y el cursor
                                 manejador_db.CerrarBaseDatos();
                                 cursor_usuarios.close();
                                 cursor_gastos.close();

                                 Toast.makeText(ActivityPrincipal.this, "Registro eliminado", Toast.LENGTH_SHORT).show();

                                 dialog.cancel();

                                 finish();
                                 Intent Activity_Main = new Intent(ActivityPrincipal.this, ActivityLoginUsuario.class);
                                 startActivity(Activity_Main);

                             }
                         });

                         // En caso de presionar NO, entro a esta funcion
                         builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                         {
                             @Override
                             public void onClick(DialogInterface dialog, int which)
                             {
                                 // Cierro el aviso
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
         }
         manejador_db.CerrarBaseDatos();
         cursor_usuarios.close();
         return super.onContextItemSelected(item);
    }

    //*****************************************************************************
    // Funcion Principal de la activity
    //*****************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Referencio los recursos del XML
        toolbar_MainActivity = findViewById(R.id.toolbar);
        ListaUsuarios = findViewById(R.id.ListaPersonas);

        // Genero el toolbar
        setSupportActionBar(toolbar_MainActivity);
        getSupportActionBar().setTitle("       T  R  I  P   M  O  N  E  Y");

        // Genero una instancia del ArrayList
        ListaDatos = new ArrayList<DatosListViewPrincipal>();

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
                        ListaDatos.add(new DatosListViewPrincipal(Nombre_BD, 0, AFavor_Total, R.mipmap.ic_launcher));
                    }

                    // Aumento el indice
                    indice_buscador_usuarios++;
                    cursor_usuarios.moveToNext();

                }while (indice_buscador_usuarios <cursor_usuarios.getCount());

            // Cierro la base de dato y los cursores
            manejador_db.CerrarBaseDatos();
            cursor_usuarios.close();
            cursor_gastos.close();
        }

        // Instancio mi clase creada adaptador con los datos ya precargados
        AdaptadorListViewPrincipal adaptador = new AdaptadorListViewPrincipal(getApplicationContext(),ListaDatos);

        // Referencio el adaptador con la lista del XML
        ListaUsuarios.setAdapter(adaptador);

        // Registro los controles para el menu contextual, detecta la pulsacion prolongada
        registerForContextMenu(ListaUsuarios);

        //*****************************************************************************
        // Funcion que atiende el click de un item del listview
        //*****************************************************************************
        ListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {
                // Le paso el estado de este usuario si es que esta logueado para habilitarle el boton de agregar un gasto nuevo
                manejador_db = new DataBaseManager(ActivityPrincipal.this);

                // Hago una query con este usuario para saber si esta logueado
                cursor_usuarios = manejador_db.Query_Usuarios("nombre=?",ListaDatos.get(position).getNombre());

                cursor_usuarios.moveToFirst();

                if(cursor_usuarios != null && cursor_usuarios.getCount()>0)
                {
                    // Me fijo si el usuario del item seleccionado esta logueado para darle permiso de modificar sus datos
                    String estado_logueado = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("uslogueado"));

                    finish();
                    Intent Activity2 = new Intent(ActivityPrincipal.this, ActivityTabs.class);

                    // Le paso el nombre y el estado del logueo del usuario correspondiente al item selecionado
                    Activity2.putExtra("Nombre_usu", ListaDatos.get(position).getNombre());
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

    //**********************************************************************************************
    // Si presiono el boton atras finalizo esta actividad y vuelvo a la activity anterior
    //**********************************************************************************************
    @Override
    public void onBackPressed()
    {
        // Creo un alerta para preguntar si deseo salir de la aplicacion
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPrincipal.this);
        builder.setTitle("Salir de la aplicacion?");

        // En caso de presionar SI, entro a esta funcion
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
                    // Obtengo los datos del usuario
                    int Id_BD = cursor_usuarios.getInt(cursor_usuarios.getColumnIndex("_id"));
                    String Usuario_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));
                    String Password_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("password"));

                    // Lo dejo como deslogueado al usuario
                    manejador_db.modificar_usuarios(Usuario_BD,Password_BD,"NO",Id_BD);

                    // Cierro la base de datos y el cursor
                    manejador_db.CerrarBaseDatos();
                    cursor_usuarios.close();
                }
                else
                {
                    Toast.makeText(ActivityPrincipal.this,"Error",Toast.LENGTH_SHORT).show();
                }

                // Finalizo la alerta
                dialog.cancel();

                // Finalizo la activity cerrando la aplicacion
                finish();
            }
        });

        // En caso de presionar NO, entro a esta funcion
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
