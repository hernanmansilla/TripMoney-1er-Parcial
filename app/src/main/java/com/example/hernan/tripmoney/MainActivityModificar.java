package com.example.hernan.tripmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hernan.tripmoney.LoginUsuario.cursor_gastos;
import static com.example.hernan.tripmoney.LoginUsuario.manejador_db;

public class MainActivityModificar extends AppCompatActivity {


    public EditText Usuario_modificar;
    public EditText Contraseña_modificar;
    public Button Boton_Modificar;
    private String Descripcion_BD;
    private float Debe_BD;
    private float Afavor_BD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar);

        Usuario_modificar = (EditText) findViewById(R.id.Usuario_modificar);
        Contraseña_modificar = (EditText) findViewById(R.id.Constraseña_modificar);
        Boton_Modificar = (Button) findViewById(R.id.Boton_modificar);

        Boton_Modificar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Usuario_new = Usuario_modificar.getText().toString();

                String Contraseña_new = Contraseña_modificar.getText().toString();

                if((Usuario_new != null) && (Contraseña_new != null))
                {
                    manejador_db= new DataBaseManager(MainActivityModificar.this);

                    // Recibo la posicion del Listview que se presiono del MainActivity
                    Bundle extras = getIntent().getExtras();
                    assert extras != null;
                    int id_press = extras.getInt("ID_Press");

                    cursor_gastos = manejador_db.CargarCursor_Gastos();

                    if(cursor_gastos != null && cursor_gastos.getCount()>0)
                    {
                        cursor_gastos.move(id_press);

                        // Tomo los datos de la tabla de gastos para actualizar el Usuario. Debo mejorar esto
                    //    Id_BD = cursor_gastos.getInt(cursor_gastos.getColumnIndex("_id"));
                        Descripcion_BD = cursor_gastos.getString(cursor_gastos.getColumnIndex("descripcion"));
                        Debe_BD = cursor_gastos.getFloat(cursor_gastos.getColumnIndex("Debe"));
                        Afavor_BD = cursor_gastos.getFloat(cursor_gastos.getColumnIndex("AFavor"));

                        // Modifico el usuario
                        manejador_db.modificar_usuarios(Usuario_new,Contraseña_new,id_press);
                        manejador_db.modificar_gastos(Usuario_new,Descripcion_BD,Debe_BD,Afavor_BD,id_press);

                        manejador_db.CerrarBaseDatos();
                        cursor_gastos.close();

                        finish();
                        Intent Activity_Main_Modificar = new Intent(MainActivityModificar.this, MainActivity.class);
                        startActivity(Activity_Main_Modificar);
                    }
                    else
                    {
                        Toast.makeText(MainActivityModificar.this,"Error al modificar",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivityModificar.this,"Valores ingresados incorrectos",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
}
