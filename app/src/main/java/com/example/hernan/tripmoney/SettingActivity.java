package com.example.hernan.tripmoney;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import static com.example.hernan.tripmoney.LoginUsuario.manejador_db_usuarios;

public class SettingActivity extends PreferenceActivity
{
    boolean borrarbd;
    static public boolean Check_estado = false;

    private static DataBaseManager manejador_db_settings;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        // Analizo las preferencias del menu Settings
      //  SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

      /*  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
             borrarbd = prefs.getBoolean("opcion1", false);

        if( borrarbd == true)
        {
            Toast.makeText(this,"Borre la Base de Datos",Toast.LENGTH_SHORT).show();
        }*/

        final Preference prefs_usiarios = (Preference) findPreference("Usuarios_BD");

        prefs_usiarios.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            public boolean onPreferenceClick(Preference preference)
            {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    builder.setMessage("Confirma borrar la base de datos de usuarios?");
                    builder.setTitle("Esto es el titulo");

                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Aca tengo que borrar la base de datos
                            manejador_db_usuarios= new DataBaseManager(SettingActivity.this);

                            manejador_db_usuarios.EliminarTablaBaseDatos_Usuarios();

                            manejador_db_usuarios.CerrarBaseDatos();

                            Toast.makeText(SettingActivity.this,"Base de Datos borrada",Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                            finish();
                            Intent Activity_Main = new Intent(SettingActivity.this, LoginUsuario.class);
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
                            //       finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return Check_estado;
            }

        });

        final Preference prefs_gastos = (Preference) findPreference("Gastos_BD");

        prefs_gastos.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
        {
            @Override
            public boolean onPreferenceClick(Preference preference)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Confirma borrar la base de datos de gastos?");
                builder.setTitle("Esto es el titulo");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Aca tengo que borrar la base de datos
                        manejador_db_settings = new DataBaseManager(SettingActivity.this);

                        manejador_db_settings.EliminarTablaBaseDatos_Gastos();

                        Toast.makeText(SettingActivity.this,"Borre la Base de Datos",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        //        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //        prefs.setSelectable(false);
                        dialog.cancel();
                        //       finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }

        });
    }
}
