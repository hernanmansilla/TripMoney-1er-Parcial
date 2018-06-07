package com.example.hernan.tripmoney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class ActivitySettings extends PreferenceActivity
{
    private DataBaseManager manejador_db;

    static public boolean Check_estado = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        final Preference prefs_usiarios = (Preference) findPreference("Usuarios_BD");

        prefs_usiarios.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySettings.this);
                    builder.setMessage("Confirma borrar la base de datos de usuarios?");
                    builder.setTitle("Esto es el titulo");

                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Aca tengo que borrar la base de datos
                            manejador_db= new DataBaseManager(ActivitySettings.this);

                            manejador_db.EliminarTablaBaseDatos_Usuarios();

                            manejador_db.CerrarBaseDatos();

                            Toast.makeText(ActivitySettings.this,"Base de Datos borrada",Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            finish();
                            Intent Activity_Main = new Intent(ActivitySettings.this, ActivityLoginUsuario.class);
                            startActivity(Activity_Main);

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                    //        prefs.setSelectable(false);
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

    // Si toco el boton atras finalizo esta actividad
    @Override
    public void onBackPressed()
    {
        finish();
        Intent Activity_Main = new Intent(ActivitySettings.this, ActivityPrincipal.class);
        startActivity(Activity_Main);
        super.onBackPressed();
    }
}
