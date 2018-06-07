package com.example.hernan.tripmoney;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import static com.example.hernan.tripmoney.MainActivity.Personas;

public class AdaptadorListViewPrincipal extends BaseAdapter
{
    Context contexto;
    List<DatosListViewPrincipal> ListaObjetosPrincipal;

    public AdaptadorListViewPrincipal(Context contexto, ArrayList<DatosListViewPrincipal> listaObjetos)
    {
        this.contexto = contexto;
        ListaObjetosPrincipal = listaObjetos;
    }

    @Override
    public int getCount()
    {
        return ListaObjetosPrincipal.size();
    }

    @Override
    public Object getItem(int Position)
    {
        return ListaObjetosPrincipal.get(Position);
    }

    @Override
    public long getItemId (int Position)
    {
        return ListaObjetosPrincipal.get(Position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View item = inflater.inflate(R.layout.listitem_titular, null);

        TextView Lista_Nombre = (TextView)item.findViewById(R.id.ListaNombre_usuario);
        TextView Lista_Debe = (TextView)item.findViewById(R.id.ListaDebe_usuario);
        TextView Lista_Afavor = (TextView)item.findViewById(R.id.ListaAfavor_usuario);
        ImageView Imagen_Titulo = (ImageView)item.findViewById(R.id.imageView);

        Lista_Nombre.setText(ListaObjetosPrincipal.get(position).getNombre().toString());
        Lista_Debe.setText(String.valueOf(ListaObjetosPrincipal.get(position).getDebe()));
        Lista_Afavor.setText(String.valueOf(ListaObjetosPrincipal.get(position).getAfavor()));
        Imagen_Titulo.setImageResource(ListaObjetosPrincipal.get(position).getImagen());

        return(item);
    }
}
