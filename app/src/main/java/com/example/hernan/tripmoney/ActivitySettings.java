package com.example.hernan.tripmoney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import static com.example.hernan.tripmoney.SQLite.NOMBRE_DB;

//**********************************************************************************************
// Clase de la actividad donde se cambian las configuraciones de la aplicacion
//**********************************************************************************************
public class ActivitySettings extends PreferenceActivity
{
    private DataBaseManager manejador_db;

    static public boolean Check_estado = false;

    //*****************************************************************************
    // Funcion Principal de la activity
    //*****************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        // Instancio una clase de la preferencia para borrar la base de datos
        final Preference prefs_usiarios = findPreference("Usuarios_BD");

        // Si presiono esta preferencia llamo a esta funcion para atender la accion
        prefs_usiarios.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                    // Genero un cuadro de alerta para preguntar si se quiere eliminar la base de datos
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySettings.this);
                    builder.setMessage("Confirma borrar la base de datos?");
                    builder.setTitle(NOMBRE_DB);

                    // En caso de presionar SI, entro a esta funcion
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Instancio un objeto para manejar la base de datos
                            manejador_db= new DataBaseManager(ActivitySettings.this);

                            // Elimino la base de datos
                            manejador_db.EliminarTablaBaseDatos_Usuarios();

                            // Cierro la base de datos
                            manejador_db.CerrarBaseDatos();

                            Toast.makeText(ActivitySettings.this,"Base de Datos borrada",Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            // Retorno al loguin ya que no tengo ningun usuario
                            finish();
                            Intent Activity_Main = new Intent(ActivitySettings.this, ActivityLoginUsuario.class);
                            startActivity(Activity_Main);

                        }
                    });

                    // En caso de presionar NO, entro a esta funcion
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();

                            finish();
                            Intent Activity_Main = new Intent(ActivitySettings.this, ActivityPrincipal.class);
                            startActivity(Activity_Main);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return Check_estado;
            }

        });
    }

    //**********************************************************************************************
    // Si presiono el boton atras finalizo esta actividad y vuelvo a la activity anterior
    //**********************************************************************************************
    @Override
    public void onBackPressed()
    {
        finish();
        Intent Activity_Main = new Intent(ActivitySettings.this, ActivityPrincipal.class);
        startActivity(Activity_Main);
        super.onBackPressed();
    }
}
