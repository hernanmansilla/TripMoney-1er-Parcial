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

public class ActivityModificar extends AppCompatActivity
{
    private static DataBaseManager manejador_db;
    private static Cursor cursor_gastos;

    private EditText Usuario_modificar;
    private EditText Contraseña_modificar;
    private Button Boton_Modificar;
    private String Descripcion_BD;
    private float Debe_BD;
    private float Afavor_BD;
    private Toolbar toolbar_MainActivityModificar;
    public static String Tipo_Fuente;
    public static SharedPreferences pref;
    private TextView Usuario_modificar_text;
    private TextView Contraseña_modificar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        Usuario_modificar_text = (TextView) findViewById(R.id.Usuario_modificar_text);
        Contraseña_modificar_text = (TextView) findViewById(R.id.Constraseña_modificar_text);
        Usuario_modificar = (EditText) findViewById(R.id.Usuario_modificar);
        Contraseña_modificar = (EditText) findViewById(R.id.Constraseña_modificar);
        Boton_Modificar = (Button) findViewById(R.id.Boton_modificar);
        toolbar_MainActivityModificar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar_MainActivityModificar);
        getSupportActionBar().setTitle("      T  R  I  P   M  O  N  E  Y");
        toolbar_MainActivityModificar.setSubtitle("Modificar Usuario");

        // Selecciono el tipo de fuente
        pref = PreferenceManager.getDefaultSharedPreferences(ActivityModificar.this);

        // Obtengo el tipo de moneda
        Tipo_Fuente = pref.getString("Tipo_Fuente","FUENTE1");

        if (Tipo_Fuente.equals("FUENTE1")) {
            Usuario_modificar_text.setTypeface(Decalled);
            Contraseña_modificar_text.setTypeface(Decalled);
            Boton_Modificar.setTypeface(Decalled);
        }

        if (Tipo_Fuente.equals("FUENTE2")) {
            Usuario_modificar_text.setTypeface(Delicious);
            Contraseña_modificar_text.setTypeface(Delicious);
            Boton_Modificar.setTypeface(Delicious);
        }

        if (Tipo_Fuente.equals("FUENTE3")) {
            Usuario_modificar_text.setTypeface(The27Club);
            Contraseña_modificar_text.setTypeface(The27Club);
            Boton_Modificar.setTypeface(The27Club);
        }

        Usuario_modificar_text.setText("USUARIO:");
        Contraseña_modificar_text.setText("CONTRASEÑA:");
        Boton_Modificar.setText("MODIFICAR USUARIO");

        Boton_Modificar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Usuario_new = Usuario_modificar.getText().toString();

                String Contraseña_new = Contraseña_modificar.getText().toString();

                if((Usuario_new != null) && (Contraseña_new != null))
                {
                    manejador_db= new DataBaseManager(ActivityModificar.this);

                    // Recibo la posicion del Listview que se presiono del MainActivity
                    Bundle extras = getIntent().getExtras();
                    assert extras != null;
                    int id_press = extras.getInt("ID_usuarios");

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
                        manejador_db.modificar_usuarios(Usuario_new,Contraseña_new,"NO",id_press);
                        manejador_db.modificar_gastos(Usuario_new,Descripcion_BD,Debe_BD,Afavor_BD,id_press);

                        manejador_db.CerrarBaseDatos();
                        cursor_gastos.close();

                        finish();
                        Intent Activity_Main_Modificar = new Intent(ActivityModificar.this, ActivityPrincipal.class);
                        startActivity(Activity_Main_Modificar);
                    }
                    else
                    {
                        Toast.makeText(ActivityModificar.this,"Error al modificar",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ActivityModificar.this,"Valores ingresados incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        finish();
        Intent ActivityAdd = new Intent(ActivityModificar.this, ActivityPrincipal.class);
        startActivity(ActivityAdd);
        super.onBackPressed();
    }
}
