package com.example.hernan.tripmoney;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hernan.tripmoney.LoginUsuario.cursor_usuarios;
import static com.example.hernan.tripmoney.LoginUsuario.cursor_gastos;
import static com.example.hernan.tripmoney.LoginUsuario.manejador_db_gastos;
import static com.example.hernan.tripmoney.LoginUsuario.manejador_db_usuarios;

public class RegistroUsuarios extends AppCompatActivity
{
    private EditText Usuario_registrar;
    private EditText Password_registrar;
    private Button Boton_registrar;
    private String user;
    private String pass;

    private static DataBaseManager manejador_db_register;
    private static Cursor cursor_register;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        Usuario_registrar = findViewById(R.id.usuario_registrar);
        Password_registrar = findViewById(R.id.password_registrar);
        Boton_registrar = findViewById(R.id.registrar);

        Boton_registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Abro la tabla de usuarios
                manejador_db_usuarios = new DataBaseManager(RegistroUsuarios.this);
                cursor_usuarios = manejador_db_usuarios.CargarCursor_Usuarios();

                // Abro la tabla de gastos
             //   manejador_db_gastos = new DataBaseManager(RegistroUsuarios.this);
                cursor_gastos = manejador_db_usuarios.CargarCursor_Gastos();

                cursor_usuarios.moveToFirst();
                cursor_gastos.moveToFirst();

                if((cursor_usuarios != null)&&(cursor_gastos != null))
                {
                    // Voy hasta el ultimo registro
                    cursor_usuarios.moveToLast();
                    cursor_gastos.moveToLast();

                    // Tomo los datos
                    user = Usuario_registrar.getText().toString();
                    pass = Password_registrar.getText().toString();

                    // Inserto los datos en la tabla uruarios
                    manejador_db_usuarios.insertar_usuarios(user, pass);
                    // Innserto los datos en la tabla gastos
                    manejador_db_usuarios.insertar_gastos(user,"hola",0,0);

                    // Cierro las bases de datos y los cursores
                    manejador_db_usuarios.CerrarBaseDatos();
               //     manejador_db_gastos.CerrarBaseDatos();
                    cursor_usuarios.close();
                    cursor_gastos.close();

                    Toast.makeText(RegistroUsuarios.this, "Usuario Añadido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(RegistroUsuarios.this, "Error al abrir la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
}
