package com.example.hernan.tripmoney;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import static com.example.hernan.tripmoney.MainActivity.toolbar;

public class RegistroUsuarios extends AppCompatActivity
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        Usuario_registrar = findViewById(R.id.usuario_registrar);
        Password_registrar = findViewById(R.id.password_registrar);
        Boton_registrar = findViewById(R.id.registrar);
        toolbar_RegistroUsuarios = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar_RegistroUsuarios);
        getSupportActionBar().setTitle("            T  R  I  P   M  O  N  E  Y");
        toolbar_RegistroUsuarios.setSubtitle("Modificar Usuario");

        Boton_registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Abro la tabla de usuarios
                manejador_db = new DataBaseManager(RegistroUsuarios.this);
                cursor_usuarios = manejador_db.CargarCursor_Usuarios();

                // Abro la tabla de gastos
                cursor_gastos = manejador_db.CargarCursor_Gastos();

                cursor_usuarios.moveToFirst();
                cursor_gastos.moveToFirst();

                if((cursor_usuarios != null)&&(cursor_gastos != null)) {
                    // Recibo la posicion del Listview que se presiono del MainActivity
                    Bundle extras = getIntent().getExtras();
                    assert extras != null;
                    int Reg_int = extras.getInt("Registro_interno");

                    // Voy hasta el ultimo registro
                    cursor_usuarios.moveToLast();
                    cursor_gastos.moveToLast();

                    // Tomo los datos
                    user = Usuario_registrar.getText().toString();
                    pass = Password_registrar.getText().toString();

                    // Inserto los datos en la tabla uruarios
                    manejador_db.insertar_usuarios(user, pass);
                    // Innserto los datos en la tabla gastos
                    manejador_db.insertar_gastos(user, "hola", 0, 0);

                    // Cierro las bases de datos y los cursores
                    manejador_db.CerrarBaseDatos();
                    //     manejador_db_gastos.CerrarBaseDatos();
                    cursor_usuarios.close();
                    cursor_gastos.close();

                    Toast.makeText(RegistroUsuarios.this, "Usuario Añadido", Toast.LENGTH_SHORT).show();

                    if (Reg_int == 1)
                    {
                        finish();
                        Intent ActivityAdd = new Intent(RegistroUsuarios.this, MainActivity.class);
                        startActivity(ActivityAdd);
                    }
                    else
                    {
                        finish();
                        Intent ActivityAdd = new Intent(RegistroUsuarios.this, LoginUsuario.class);
                        startActivity(ActivityAdd);
                    }
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
