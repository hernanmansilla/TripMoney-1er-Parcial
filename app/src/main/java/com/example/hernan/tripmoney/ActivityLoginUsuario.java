package com.example.hernan.tripmoney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hernan.tripmoney.ActivitySplashScreen.Decalled;
import static com.example.hernan.tripmoney.ActivitySplashScreen.Delicious;
import static com.example.hernan.tripmoney.ActivitySplashScreen.The27Club;

//**********************************************************************************************
// Clase de la actividad donde se loguea un nuevo usuario
//**********************************************************************************************
public class ActivityLoginUsuario extends AppCompatActivity
{
    public TextView Usuario_text;
    public TextView Contraseña_text;
    public EditText Usuario_login;
    public EditText Password_login;
    public Button Aceptar_login;
    public Button Registrar_login;
    public String Usuario_ingresado;
    public String Password_ingresado;
    public String Usuario_BD;
    public String Password_BD;
    public int Id_BD;
    private int  indice_buscador=0;
    public boolean Usuario_OK=false;
    public Toolbar toolbar_loguin;
    public static String Tipo_Fuente;
    public static SharedPreferences pref;
    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;
    public TextView Registrate_text;

    //*****************************************************************************
    // Inflo el toolbar con los botones
    //*****************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        // Referencio los recursos del XML
        Registrate_text = findViewById(R.id.Registrate_text);
        Usuario_text = findViewById(R.id.Usuario_text);
        Contraseña_text = findViewById(R.id.Contraseña_text);
        Usuario_login = findViewById(R.id.usuario_login);
        Password_login = findViewById(R.id.password_login);
        Aceptar_login = findViewById(R.id.aceptar_login);
        Registrar_login = findViewById(R.id.registrar_login);
        toolbar_loguin =  findViewById(R.id.toolbar);

         // Seteo el Toolbar
        setSupportActionBar(toolbar_loguin);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_loguin.setSubtitle("Loguin Usuario");

        // Selecciono el tipo de fuente
        pref = PreferenceManager.getDefaultSharedPreferences(ActivityLoginUsuario.this);

        // Obtengo el tipo de fuente y dejo por defecto la 1 por si ocurre un error
        Tipo_Fuente = pref.getString("Tipo_Fuente","FUENTE1");

        // De acuerdo al tipo de fuente guardada, selecciono la fuente para las letras
        if (Tipo_Fuente.equals("FUENTE1"))
        {
            Usuario_text.setTypeface(Decalled);
            Contraseña_text.setTypeface(Decalled);
            Aceptar_login.setTypeface(Decalled);
            Registrar_login.setTypeface(Decalled);
            Registrate_text.setTypeface(Decalled);
        }

        if (Tipo_Fuente.equals("FUENTE2")) {
            Usuario_text.setTypeface(Delicious);
            Contraseña_text.setTypeface(Delicious);
            Aceptar_login.setTypeface(Delicious);
            Registrar_login.setTypeface(Delicious);
            Registrate_text.setTypeface(Delicious);
        }

        if (Tipo_Fuente.equals("FUENTE3")) {
            Usuario_text.setTypeface(The27Club);
            Contraseña_text.setTypeface(The27Club);
            Aceptar_login.setTypeface(The27Club);
            Registrar_login.setTypeface(The27Club);
            Registrate_text.setTypeface(The27Club);
        }

        // Seteo los textos con la fuente precargada
        Usuario_text.setText("USUARIO:");
        Contraseña_text.setText("CONTRASEÑA:");
        Aceptar_login.setText("LOGIN");
        Registrar_login.setText("REGISTRAR");
        Registrate_text.setText("Si no posee usuario, registrese");

        //**********************************************************************************************
        // Boton LOGIN - Compara el usuario ingresado con los usuarios almacenados en la base de datos
        //**********************************************************************************************
        Aceptar_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Tomo los datos ingresados
                Usuario_ingresado = Usuario_login.getText().toString();
                Password_ingresado = Password_login.getText().toString();

                // Me fijo si ingreso datos invalidos
                if(Usuario_ingresado.isEmpty() && Password_ingresado.isEmpty())
                {
                    Toast.makeText(ActivityLoginUsuario.this, "Ingrese datos validos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Instancio un objeto para manejar la base de datos
                    manejador_db = new DataBaseManager(ActivityLoginUsuario.this);
                    // Cargo el cursor de la base para obtener los registrosde Usuario
                    cursor_usuarios = manejador_db.CargarCursor_Usuarios();

                    cursor_usuarios.moveToFirst();

                    if (cursor_usuarios != null && cursor_usuarios.getCount() > 0)
                    {
                        indice_buscador=0;

                        // Busco la posicion para insertar el nuevo usuario
                        do {
                            // De acuerdo a la posicion del cursor tomo los registros de esa fila
                            Usuario_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));
                            Password_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("password"));
                            Id_BD = cursor_usuarios.getInt(cursor_usuarios.getColumnIndex("_id"));

                            // Comparo si el usuario y contraseña es correcto
                            if (Usuario_ingresado.equals(Usuario_BD) && Password_ingresado.equals(Password_BD))
                            {
                                // Seteo como logueado a este usuario
                                manejador_db.modificar_usuarios(Usuario_BD,Password_BD,"SI",Id_BD);
                                Usuario_OK = true;
                            }
                            else
                                {
                                // Si no es igual aumento el cursor para saltar a la proxima fila
                                indice_buscador++;
                                cursor_usuarios.moveToNext();
                            }
                        }
                        while ((indice_buscador < cursor_usuarios.getCount()) && (Usuario_OK == false));

                        // Cierro la base de datos y el cursor
                        cursor_usuarios.close();
                        manejador_db.CerrarBaseDatos();
                        indice_buscador = 0;

                        // Compruebo si existe el usuario y la contraseña es correcta
                        if (Usuario_OK == true)
                        {
                            // Me voy a la actividad principal
                            finish();
                            Intent Activity_Main = new Intent(ActivityLoginUsuario.this, ActivityPrincipal.class);
                            startActivity(Activity_Main);
                        } else
                            Toast.makeText(ActivityLoginUsuario.this, "Usuario o Contraseña incorrectas", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // Si entre aca es porque la tabla esta vacia. Le genero un registro por default
                        if(cursor_usuarios.getCount() ==0)
                        {
                            Toast.makeText(ActivityLoginUsuario.this, "Base de datos vacia", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //**************************************************************************
        // BOTON REGISTRAR - Voy a la activity para registrar un usuario nuevo
        //**************************************************************************
        Registrar_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
                // Le paso la indicacion de que no quiero un registro dentro de la aplicacion
                Intent Activity2 = new Intent(ActivityLoginUsuario.this, ActivityRegistroUsuarios.class);
                Activity2.putExtra("Registro_interno",0);
                startActivity(Activity2);
            }
        });
    }
}
