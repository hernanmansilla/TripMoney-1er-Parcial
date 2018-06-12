package com.example.hernan.tripmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//**********************************************************************************************
// Clase Adaptador donde le paso el objeto del tipo arraylist para personalizarlo
//**********************************************************************************************
public class AdaptadorListViewPrincipal extends BaseAdapter
{
    Context contexto;
    List<DatosListViewPrincipal> ListaObjetosPrincipal;

    //**********************************************************************************************
    // Constructor de la clase
    //**********************************************************************************************
    public AdaptadorListViewPrincipal(Context contexto, ArrayList<DatosListViewPrincipal> listaObjetos)
    {
        this.contexto = contexto;
        ListaObjetosPrincipal = listaObjetos;
    }

    //**********************************************************************************************
    // Obtengo el tamaño
    //**********************************************************************************************
    @Override
    public int getCount()
    {
        return ListaObjetosPrincipal.size();
    }

    //**********************************************************************************************
    // Obtengo el item seleccionado
    //**********************************************************************************************
    @Override
    public Object getItem(int Position)
    {
        return ListaObjetosPrincipal.get(Position);
    }

    //**********************************************************************************************
    // Obtengo el id del item seleccionado del listview
    //**********************************************************************************************
    @Override
    public long getItemId (int Position)
    {
        return ListaObjetosPrincipal.get(Position).getId();
    }

    //**********************************************************************************************
    // Funcion donde se crea la vista de cada item del listview
    //**********************************************************************************************
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.listitem_titular, null);

        TextView Lista_Nombre = item.findViewById(R.id.ListaNombre_usuario);
        TextView Lista_Debe = item.findViewById(R.id.ListaDebe_usuario);
        TextView Lista_Afavor = item.findViewById(R.id.ListaAfavor_usuario);
        ImageView Imagen_Titulo = item.findViewById(R.id.imageView);

        Lista_Nombre.setText(ListaObjetosPrincipal.get(position).getNombre().toString());
        Lista_Debe.setText(String.valueOf(ListaObjetosPrincipal.get(position).getDebe()));
        Lista_Afavor.setText(String.valueOf(ListaObjetosPrincipal.get(position).getAfavor()));
        Imagen_Titulo.setImageResource(ListaObjetosPrincipal.get(position).getImagen());

        return(item);
    }
}
