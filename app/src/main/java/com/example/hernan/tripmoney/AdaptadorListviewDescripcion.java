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

public class AdaptadorListviewDescripcion extends BaseAdapter
{
    Context contexto;
    List<DatosListViewDescripcion> ListaObjetosDescripcion;

    public AdaptadorListviewDescripcion(Context contexto, ArrayList<DatosListViewDescripcion> listaObjetos)
    {
        this.contexto = contexto;
        ListaObjetosDescripcion = listaObjetos;
    }

    @Override
    public int getCount()
    {
        return ListaObjetosDescripcion.size();
    }

    @Override
    public Object getItem(int Position)
    {
        return ListaObjetosDescripcion.get(Position);
    }

    @Override
    public long getItemId (int Position)
    {
        return ListaObjetosDescripcion.get(Position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.listitem_descripcion, null);

        TextView Descripcion_Desc = (TextView)item.findViewById(R.id.ListaDescripcion_Descripcion);
        TextView Descripcion_Gastado = (TextView)item.findViewById(R.id.ListaDescripcion_Gastado);

        Descripcion_Desc.setText(ListaObjetosDescripcion.get(position).getDescripcion().toString());
        Descripcion_Gastado.setText(String.valueOf(ListaObjetosDescripcion.get(position).getAFavor()));

        return(item);
    }
}
