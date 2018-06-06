package com.example.hernan.tripmoney;

public class DatosListViewDescripcion
{
    private int Id;
    private String Nombre;
    private String Descripcion;

    public DatosListViewDescripcion(String name,String desc)
    {
    //    Id = id;
        Nombre = name;
        Descripcion = desc;
    }

    public int getId()
    {
        return Id;
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
