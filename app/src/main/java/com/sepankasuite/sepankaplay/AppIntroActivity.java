package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import static com.sepankasuite.sepankaplay.R.string.app_intro_description1;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Al usar este tipo de elemento no requiere hacer una ligadura del codigo java con la actividad
        //setContentView(R.layout.activity_app_intro);

        //Generamos el numero de elementos que tendra el intro cada uno debe cubrir background, title, description, image
        addSlide(AppIntroFragment.newInstance("BIENVENIDO", "",
                R.drawable.intro1, ContextCompat.getColor(getApplicationContext(), R.color.colorPurpleSepanka)));
        /*addSlide(AppIntroFragment.newInstance("INSTRUCCIONES", "",
                R.drawable.intro2, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("INSTRUCCIONES", "SEPANKAPLAY es un juego de pistas",
                R.drawable.intro3, ContextCompat.getColor(getApplicationContext(), R.color.colorPurpleSepanka)));*/
        addSlide(AppIntroFragment.newInstance("INSTRUCCIONES", "En esta pantalla irán apareciendo preguntas cuyas soluciones están dispersas en el sitio " +
                        "www.sepankasuite.com y en sus redes sociales",
                R.drawable.intro4, ContextCompat.getColor(getApplicationContext(), R.color.colorPurpleSepanka)));
        addSlide(AppIntroFragment.newInstance("INSTRUCCIONES", "Dá clic sobre el ícono de pistas que aparecerá en el margen superior derecho de tu dispositivo " +
                        "para encontrar las respuestas",
                R.drawable.intro5, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
    }

    //Metodo para cambiar de actividad cuando se presiona el boton de hecho
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //Creamos una instancia del la actividad siguiente
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //Cerramos la actividad actual para que no este en cola
        finish();
    }

    //Metodo para cambiar de actividad cuando se presiona el boton de skape
    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        //Creamos una instancia del la actividad siguiente
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //Cerramos la actividad actual para que no este en cola
        finish();
    }
}
