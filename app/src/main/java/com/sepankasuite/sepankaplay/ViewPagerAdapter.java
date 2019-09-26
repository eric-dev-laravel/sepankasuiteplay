package com.sepankasuite.sepankaplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {

    //Generamos las variables globales
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    //Generamos el contructor para recuperar el contexto donde nos encontramo
    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    //Metodo para recuperar el numero de imagenes que estaran dentro del adaptador
    @Override
    public int getCount() {
        return images.length;
    }

    //Regresamos la vista como un objeto
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //Creamos el adaptador de las imagenes
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //Asignamos la variable a los atributos y funciones necesarias
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Creamos una nueva vista a la cual le asignamos el layout que contendra las imagenes
        View view = layoutInflater.inflate(R.layout.custom_layout_viewpager, null);
        //Creamos la variable de imageview y enlazamos al objeto que esta en el activity principal
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPager);
        //Asignamos las imagenes al contenedor
        imageView.setImageResource(images[position]);

        //Metodo para dar clic a los elementos del imageview
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Segun la posicion podemos hacer varias opciones
                if (position == 0){
                    Toast.makeText(context, "Slide1 Clicked", Toast.LENGTH_LONG).show();
                } else if (position == 1){
                    Toast.makeText(context, "Slide2 Clicked", Toast.LENGTH_LONG).show();
                } else {
                    //Creamos un nuevo intent de tipo externo para abir el navegador y asignamos la url que abrira
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sepankasuite.com/comunicacion-interna"));
                    //Lanzamos la peticion del nuevo activity externo
                    context.startActivity(browserIntent);
                }
            }
        });

        //Creamos una nueva variable que contendra el objeto creado
        ViewPager vp = (ViewPager) container;
        //asignamos la vista a la variable es para optimizar los procesos internos, android se encarga del monitoreo
        vp.addView(view, 0);
        //Devolvemos la vista
        return view;
    }

    //Metodo para remover la imagen actual y colocar la siguiente o la anterior segun sea la direccion del uso
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
