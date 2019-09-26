package com.sepankasuite.sepankaplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Al usar este tipo de elemento no requiere hacer una ligadura del codigo java con la actividad
        //setContentView(R.layout.activity_app_intro);

        //Generamos el numero de elementos que tendra el intro cada uno debe cubrir background, title, description, image
        addSlide(AppIntroFragment.newInstance("First App Intro", "First App Intro Details",
                R.drawable.one, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Second App Intro", "Second App Intro Details",
                R.drawable.two, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Third App Intro", "Third App Intro Details",
                R.drawable.one, ContextCompat.getColor(getApplicationContext(), R.color.colorPurpleSepanka)));
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
