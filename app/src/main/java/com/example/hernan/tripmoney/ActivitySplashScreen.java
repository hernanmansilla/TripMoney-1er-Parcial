package com.example.hernan.tripmoney;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import java.util.Timer;
import java.util.TimerTask;

//**********************************************************************************************
// Clase de la actividad donde se genera el SplashScreen
//**********************************************************************************************
public class ActivitySplashScreen extends AppCompatActivity
{
    private static final long SPLASH_SCREEN_DELAY = 3000;
    public static Typeface Decalled;
    public static Typeface Delicious;
    public static Typeface The27Club;
    public static String fuente1;
    public static String fuente2;
    public static String fuente3;

    //********************************************************************************
    // Funcion Principal de la activity
    //********************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Definimos la orientación de la SplashScreen como Portrait (vertical)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Escondemos el título de la app
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        //*****************************************************************************
        // Funcion que atiene el vencimiento del timer del splash
        //*****************************************************************************
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                // Start the next activity
                Intent mainIntent = new Intent().setClass(ActivitySplashScreen.this, ActivityLoginUsuario.class);
                startActivity(mainIntent);

                // Terminamos la activity para que el usuario no pueda volver para atrás con el botón de back
                finish();
            }
        };

        // Simulamos con un timer un tiempo de espera definido en una constante al comienzo
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

        // Mientras que corre el timer cargo las fuentes de la aplicacion
        fuente1 = "Fuentes/Decalled.ttf";
        this.Decalled = Typeface.createFromAsset(getAssets(),fuente1);
        fuente2 = "Fuentes/Delicious.ttf";
        this.Delicious = Typeface.createFromAsset(getAssets(),fuente2);
        fuente3 = "Fuentes/The27Club.otf";
        this.The27Club = Typeface.createFromAsset(getAssets(),fuente3);
    }
}
