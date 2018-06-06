package com.example.hernan.tripmoney;

public class DatosListViewPrincipal
{
    private int Id;
    private String Nombre;
    private float Debe;
    private float Afavor;
    private int Imagen_titulo;

    public DatosListViewPrincipal(String name, float debe, float afavor,int imag)
    {
      //  Id=id;
        Nombre = name;
        Debe = debe;
        Afavor = afavor;
        Imagen_titulo = imag;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id=id;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public float getDebe()
    {
        return Debe;
    }

    public float getAfavor()
    {
        return Afavor;
    }

    public int getImagen()
    {
        return Imagen_titulo;
    }

}

