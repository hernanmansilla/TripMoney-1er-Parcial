package com.example.hernan.tripmoney;

public class DatosListViewDescripcion
{
    private int Id;
    private String Nombre;
    private String Descripcion;
    private float Afavor;

    public DatosListViewDescripcion(String name,String desc, float gastado)
    {
    //    Id = id;
        Nombre = name;
        Descripcion = desc;
        Afavor = gastado;
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

    public float getAFavor()
    {
        return Afavor;
    }

}
