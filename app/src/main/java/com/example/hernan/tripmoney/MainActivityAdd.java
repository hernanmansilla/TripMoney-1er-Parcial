package com.example.hernan.tripmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import static com.example.hernan.tripmoney.MainActivity.Personas;

public class MainActivityAdd extends AppCompatActivity
{
    public Button Boton_Cancelar;
    public Button Boton_Agregar;
    public EditText Nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);
/*
        Boton_Cancelar = findViewById(R.id.Boton_Cancelar);

        Boton_Cancelar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent ActivityMain = new Intent(MainActivityAdd.this, MainActivity.class);
                startActivity(ActivityMain);
            }
        });

        Boton_Agregar.setOnClickListener(new View.OnClickListener()
        {
            int i;

            @Override
            public void onClick(View v)
            {
                String Nombre_add = Nombre.getText().toString();

                // Busco la ultima posicion en el array
                for(i=0;i<20;i++)
                {
                    if("".equals(Personas[i].getTitulo()))
                    {
                        // Agrego el nuevo item al array
                        Personas[i].setTitulo(Nombre_add);

                    }
                }

            }
        });*/


    }
}
