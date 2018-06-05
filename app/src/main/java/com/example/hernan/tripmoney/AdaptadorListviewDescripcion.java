package com.example.hernan.tripmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListviewDescripcion
{
    Context contexto;
    List<DatosListViewDescripcion> ListaObjetosDescripcion;

    public AdaptadorListviewDescripcion(Context contexto, ArrayList<DatosListViewDescripcion> listaObjetos)
    {
        this.contexto = contexto;
        ListaObjetosDescripcion = listaObjetos;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.listitem_descripcion, null);

      //  TextView Lista_Nombre = (TextView)item.findViewById(R.id.ListaDescripcion_Gastos);
        TextView Descripcion_gasto = (TextView)item.findViewById(R.id.ListaDescripcion_Gastos);

    //    Lista_Nombre.setText(ListaObjetosDescripcion.get(position).getNombre().toString());
        Descripcion_gasto.setText(String.valueOf(ListaObjetosDescripcion.get(position).getDescripcion()));

        return(item);
    }
}
