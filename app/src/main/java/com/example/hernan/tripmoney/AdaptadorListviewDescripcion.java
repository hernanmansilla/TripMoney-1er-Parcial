package com.example.hernan.tripmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

//**********************************************************************************************
// Clase Adaptador donde le paso el objeto del tipo arraylist para personalizarlo
//**********************************************************************************************
public class AdaptadorListviewDescripcion extends BaseAdapter
{
    Context contexto;
    List<DatosListViewDescripcion> ListaObjetosDescripcion;

    //**********************************************************************************************
    // Constructor de la clase
    //**********************************************************************************************
    public AdaptadorListviewDescripcion(Context contexto, ArrayList<DatosListViewDescripcion> listaObjetos)
    {
        this.contexto = contexto;
        ListaObjetosDescripcion = listaObjetos;
    }

    //**********************************************************************************************
    // Obtengo el tamaño
    //**********************************************************************************************
    @Override
    public int getCount()
    {
        return ListaObjetosDescripcion.size();
    }

    //**********************************************************************************************
    // Obtengo el item seleccionado
    //**********************************************************************************************
    @Override
    public Object getItem(int Position)
    {
        return ListaObjetosDescripcion.get(Position);
    }

    //**********************************************************************************************
    // Obtengo el id del item seleccionado del listview
    //**********************************************************************************************
    @Override
    public long getItemId (int Position)
    {
        return ListaObjetosDescripcion.get(Position).getId();
    }

    //**********************************************************************************************
    // Funcion donde se crea la vista de cada item del listview
    //**********************************************************************************************
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.listitem_descripcion, null);

        TextView Descripcion_Desc = item.findViewById(R.id.ListaDescripcion_Descripcion);
        TextView Descripcion_Gastado = item.findViewById(R.id.ListaDescripcion_Gastado);

        Descripcion_Desc.setText(ListaObjetosDescripcion.get(position).getDescripcion().toString());
        Descripcion_Gastado.setText(String.valueOf(ListaObjetosDescripcion.get(position).getAFavor()));

        return(item);
    }
}
