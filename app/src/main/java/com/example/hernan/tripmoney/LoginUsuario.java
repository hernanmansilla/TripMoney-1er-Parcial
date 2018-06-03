package com.example.hernan.tripmoney;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUsuario extends AppCompatActivity
{
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
    private Toolbar toolbar_loguin;

    private static DataBaseManager manejador_db;
    private static Cursor cursor_usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        Usuario_login = findViewById(R.id.usuario_login);
        Password_login = findViewById(R.id.password_login);
        Aceptar_login = findViewById(R.id.aceptar_login);
        Registrar_login = findViewById(R.id.registrar_login);
        toolbar_loguin = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar_loguin);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_loguin.setSubtitle("Loguin Usuario");

        // Abri la base de datos
    //    manejador_db = new DataBaseManager(this);

    //    manejador_db.insertar_usuarios("hernan","messi10");
    //    manejador_db.insertar_usuarios("german","messi11");

        // Instanciamos la clase de la base de datos y automaticamente esta crea la tabla
    //    cursor = manejador_db.CargarCursor_Usuarios();

        Aceptar_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                manejador_db = new DataBaseManager(LoginUsuario.this);
                cursor_usuarios = manejador_db.CargarCursor_Usuarios();

                Usuario_ingresado = Usuario_login.getText().toString();
                Password_ingresado = Password_login.getText().toString();

                cursor_usuarios.moveToFirst();

                if(cursor_usuarios != null && cursor_usuarios.getCount()>0)
                {
                    // Busco la posicion para insertar el nuevo usuario
                    do {
                        // Leo el usuario
                        Usuario_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("nombre"));

                        // Leo el password
                        Password_BD = cursor_usuarios.getString(cursor_usuarios.getColumnIndex("password"));

                        // Leo el ID
                        Id_BD = cursor_usuarios.getInt(cursor_usuarios.getColumnIndex("_id"));

                        // Comparo si el usuario y contraseña es correcto
                        if (Usuario_ingresado.equals(Usuario_BD) && Password_ingresado.equals(Password_BD)) {
                            //       if ((Usuario_ingresado == Usuario_BD) && (Password_ingresado == Password_BD))
                            Usuario_OK = true;
                        }
                        else
                            {
                            indice_buscador++;
                            cursor_usuarios.moveToNext();
                        }

                    } while ((indice_buscador < cursor_usuarios.getCount()) && (Usuario_OK == false));
            //        while(cursor_usuarios != null);

                    cursor_usuarios.close();
                    manejador_db.CerrarBaseDatos();
                    indice_buscador = 0;

                    if (Usuario_OK == true) {
                        // Me voy a la actividad principal
                        finish();
                        Intent Activity_Main = new Intent(LoginUsuario.this, MainActivity.class);
                        startActivity(Activity_Main);
                    } else
                        Toast.makeText(LoginUsuario.this, "Usuario o Contraseña incorrectas", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Si entre aca es porque la tabla esta vacia. Le genero un registro por default
              //      manejador_db_loguin.insertar_usuarios("user","user");
                    Toast.makeText(LoginUsuario.this, "Base de datos vacia", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Voy a registrar un usuario nuevo
        Registrar_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent Activity2 = new Intent(LoginUsuario.this, RegistroUsuarios.class);
                Activity2.putExtra("Registro_interno",0);
                startActivity(Activity2);
            }
        });




    }
}
