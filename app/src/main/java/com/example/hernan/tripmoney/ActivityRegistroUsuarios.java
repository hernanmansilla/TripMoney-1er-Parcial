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

//import static com.example.hernan.tripmoney.ActivityPrincipal.Decalled;
//import static com.example.hernan.tripmoney.ActivityPrincipal.Delicious;
//import static com.example.hernan.tripmoney.ActivityPrincipal.The27Club;
import static com.example.hernan.tripmoney.SplashScreen.Decalled;
import static com.example.hernan.tripmoney.SplashScreen.Delicious;
import static com.example.hernan.tripmoney.SplashScreen.The27Club;

//import static com.example.hernan.tripmoney.MainActivity.toolbar;

public class ActivityRegistroUsuarios extends AppCompatActivity
{
    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;
    private static Cursor cursor_gastos;
    private EditText Usuario_registrar;
    private EditText Password_registrar;
    private Button Boton_registrar;
    private String user;
    private String pass;
    private Toolbar toolbar_RegistroUsuarios;
    private String Usuario_BD;
    private String Password_BD;
    private int Id_BD;
    private String Usuario_ingresado;
    private String Password_ingresado;
    private boolean Usuario_repetido;
    private int indice_buscador;
    public static String Tipo_Fuente;
    public static SharedPreferences pref;
    private TextView Usuario_registrar_text;
    private TextView Contraseña_registrar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);


        Usuario_registrar_text = findViewById(R.id.Usuario_registrar_text);
        Contraseña_registrar_text = findViewById(R.id.Contraseña_registrar_text);
        Usuario_registrar = findViewById(R.id.usuario_registrar);
        Password_registrar = findViewById(R.id.password_registrar);
        Boton_registrar = findViewById(R.id.registrar);
        toolbar_RegistroUsuarios = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar_RegistroUsuarios);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_RegistroUsuarios.setSubtitle("Modificar Usuario");

        pref = PreferenceManager.getDefaultSharedPreferences(ActivityRegistroUsuarios.this);

        // Obtengo el tipo de moneda
        Tipo_Fuente = pref.getString("Tipo_Fuente", "FUENTE1");

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

        Usuario_registrar_text.setText("USUARIO:");
        Contraseña_registrar_text.setText("CONTRASEÑA:");
        Boton_registrar.setText("REGISTRAR");

        Boton_registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Tomo los datos
                Usuario_ingresado = Usuario_registrar.getText().toString();
                Password_ingresado = Password_registrar.getText().toString();

                if(Usuario_ingresado.isEmpty() && Password_ingresado.isEmpty())
                {
                    Toast.makeText(ActivityRegistroUsuarios.this, "Ingrese datos validos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Abro la tabla de usuarios
                    manejador_db = new DataBaseManager(ActivityRegistroUsuarios.this);
                    cursor_usuarios = manejador_db.CargarCursor_Usuarios();

                    // Abro la tabla de gastos
                    cursor_gastos = manejador_db.CargarCursor_Gastos();

                    cursor_usuarios.moveToFirst();
                    cursor_gastos.moveToFirst();

                    if ((cursor_usuarios != null) && (cursor_usuarios.getCount()>0))
                    {
                        indice_buscador = 0;
                        Usuario_repetido = false;

                        // Chequeo ya si el usuario esta ingresado
                        // Busco la posicion para insertar el nuevo usuario
                        do {
                            Usuario_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));

                            // Comparo si el usuario y contraseña es correcto
                            if (Usuario_ingresado.equals(Usuario_BD))
                            {
                                Usuario_repetido = true;
                            }
                            else
                                {
                                indice_buscador++;
                                cursor_usuarios.moveToNext();
                            }
                        }
                        while ((indice_buscador < cursor_usuarios.getCount()) && (Usuario_repetido == false));
                    }
                    else
                    {
                        if(cursor_usuarios.getCount()==0)
                        {
                            // Si entro aca la base de datos esta vacia, no comparo a ver si esta repetido el usuario
                            Usuario_repetido=false;
                        }
                    }

                    if (Usuario_repetido == false)
                    {
                        // Recibo la posicion del Listview que se presiono del MainActivity
                        Bundle extras = getIntent().getExtras();
                        assert extras != null;
                        int Reg_int = extras.getInt("Registro_interno");

                        // Voy hasta el ultimo registro
                        cursor_usuarios.moveToLast();
                        cursor_gastos.moveToLast();

                        // Inserto los datos en la tabla uruarios
                        manejador_db.insertar_usuarios(Usuario_ingresado, Password_ingresado,"NO");
                        // Innserto los datos en la tabla gastos
                        manejador_db.insertar_gastos(Usuario_ingresado, "hola", 0, 0);

                        Toast.makeText(ActivityRegistroUsuarios.this, "Usuario Añadido", Toast.LENGTH_SHORT).show();

                        if (Reg_int == 1) {
                            finish();
                            Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityPrincipal.class);
                            startActivity(ActivityAdd);
                        } else {
                            finish();
                            Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityLoginUsuario.class);
                            startActivity(ActivityAdd);
                        }
                    }
                    else
                    {
                        Toast.makeText(ActivityRegistroUsuarios.this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
                    }
                    // Cierro las bases de datos y los cursores
                    manejador_db.CerrarBaseDatos();
                    cursor_usuarios.close();
                    cursor_gastos.close();
                }
            }
        });

    }

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        int Reg_int = extras.getInt("Registro_interno");

        finish();

        if (Reg_int == 1)
        {
            Intent ActivityAdd = new Intent(ActivityRegistroUsuarios.this, ActivityPrincipal.class);
            startActivity(ActivityAdd);
        }
        super.onBackPressed();
    }
}
