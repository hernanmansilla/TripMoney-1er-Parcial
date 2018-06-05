package com.example.hernan.tripmoney;

public class DatosListViewDescripcion
{
    private String Nombre;
    private String Descripcion;

    public DatosListViewDescripcion(String name,String desc)
    {
        Nombre = name;
        Descripcion = desc;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public String getDescripcion()
    {
        return Descripcion;
    }

}
