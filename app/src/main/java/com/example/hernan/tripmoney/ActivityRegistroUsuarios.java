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

public class ActivityRegistroUsuarios extends AppCompatActivity
{
    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;
    private static Cursor cursor_gastos;
    private EditText Usuario_registrar;
    private EditText Password_registrar;
    private Button Boton_registrar;
    private Toolbar toolbar_RegistroUsuarios;
    private String Usuario_BD;
    private String Usuario_ingresado;
    private String Password_ingresado;
    private boolean Usuario_repetido;
    private int indice_buscador;
    public static String Tipo_Fuente;
    public static SharedPreferences pref;
    private TextView Usuario_registrar_text;
    private TextView Contraseña_registrar_text;

    //*****************************************************************************
    // Funcion Principal de la activity
    //*****************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        // Referencio los recursos del XML
        Usuario_registrar_text = findViewById(R.id.Usuario_registrar_text);
        Contraseña_registrar_text = findViewById(R.id.Contraseña_registrar_text);
        Usuario_registrar = findViewById(R.id.usuario_registrar);
        Password_registrar = findViewById(R.id.password_registrar);
        Boton_registrar = findViewById(R.id.registrar);
        toolbar_RegistroUsuarios = (Toolbar) findViewById(R.id.toolbar);

        // Seteo el Toolbar
        setSupportActionBar(toolbar_RegistroUsuarios);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_RegistroUsuarios.setSubtitle("Registrar Usuario");

        // Instancio una clase de las Shared Preferences
        pref = PreferenceManager.getDefaultSharedPreferences(ActivityRegistroUsuarios.this);

        // Obtengo el tipo de fuente
        Tipo_Fuente = pref.getString("Tipo_Fuente", "FUENTE1");

        // De acuerdo al tipo de fuente guardada, selecciono la fuente para las letras
        if (Tipo_Fuente.equals("FUENTE1")) {
            Usuario_registrar_text.setTypeface(Decalled);
            Contraseña_registrar_text.setTypeface(Decalled);
            Boton_registrar.setTypeface(Decalled);
        }

        if (Tipo_Fuente.equals("FUENTE2")) {
            Usuario_registrar_text.setTypeface(Delicious);
            Contraseña_registrar_text.setTypeface(Delicious);
            Boton_registrar.setTypeface(Delicious);
        }

        if (Tipo_Fuente.equals("FUENTE3")) {
            Usuario_registrar_text.setTypeface(The27Club);
            Contraseña_registrar_text.setTypeface(The27Club);
            Boton_registrar.setTypeface(The27Club);
        }

        // Seteo los textos con la fuente precargada
        Usuario_registrar_text.setText("USUARIO:");
        Contraseña_registrar_text.setText("CONTRASEÑA:");
        Boton_registrar.setText("REGISTRAR");

        //**********************************************************************************************
        // Boton REGISTRAR - Registra un nuevo usuario en la base de datos
        //**********************************************************************************************
        Boton_registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Tomo los datos ingresados
                Usuario_ingresado = Usuario_registrar.getText().toString();
                Password_ingresado = Password_registrar.getText().toString();

                // Me fijo que haya ingresado algo
                if(Usuario_ingresado.isEmpty() && Password_ingresado.isEmpty())
                {
                    Toast.makeText(ActivityRegistroUsuarios.this, "Ingrese datos validos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Instancio un objeto para manejar la base de datos
                    manejador_db = new DataBaseManager(ActivityRegistroUsuarios.this);

                    cursor_usuarios = manejador_db.Query_Usuarios("nombre=?",Usuario_ingresado);

                    // Si se cumple esta condicion quiere decir que no existe ese nombre en la base
                    if(cursor_usuarios.getCount() == 0)
                    {
                        // Recibo la indicacion si se viene del listview principal o del registro del loguin
                        Bundle extras = getIntent().getExtras();
                        assert extras != null;
                        int Reg_int = extras.getInt("Registro_interno");

                        // Cargo el cursor para recorrer la tabla usuarios
                        cursor_usuarios = manejador_db.CargarCursor_Usuarios();

                        // Cargo el cursor de la base para obtener los registros de Gastos
                        cursor_gastos = manejador_db.CargarCursor_Gastos();

                        cursor_usuarios.moveToFirst();
                        cursor_gastos.moveToFirst();

                        if ((cursor_usuarios != null) && (cursor_gastos != null))
                        {
                            // Voy hasta el ultimo registro para agregar el nuevo al final
                            cursor_usuarios.moveToLast();
                            cursor_gastos.moveToLast();

                            // Inserto los datos en la tabla uruarios
                            manejador_db.insertar_usuarios(Usuario_ingresado, Password_ingresado, "NO");
                            // Inserto los datos en la tabla gastos
                            manejador_db.insertar_gastos(Usuario_ingresado, "hola", 0, 0);

                            Toast.makeText(ActivityRegistroUsuarios.this, "Usuario Añadido", Toast.LENGTH_SHORT).show();

                            // Me fijo de donde vengo para saber a donde tengo que retornar
                            if (Reg_int == 1)
                            {
                                finish();
                                Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityPrincipal.class);
                                startActivity(ActivityAdd);
                            } else {
                                finish();
                                Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityLoginUsuario.class);
                                startActivity(ActivityAdd);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(ActivityRegistroUsuarios.this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
                    }
                    // Cierro las bases de datos y los cursores
                    manejador_db.CerrarBaseDatos();
                    cursor_usuarios.close();
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
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int Reg_int = extras.getInt("Registro_interno");

        finish();

        // De acuerdo al registro interno, vuelvo a la actividad principal porque la llame desde la aplicacion
        if (Reg_int == 1)
        {
            Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityPrincipal.class);
            startActivity(ActivityAdd);
        }
        super.onBackPressed();
    }
}
