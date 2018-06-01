package com.example.hernan.tripmoney;

public class Datos
{
    private int Id;
    private String Nombre;
    private float Debe;
    private float Afavor;
    private int Imagen_titulo;

    public Datos(int id,String name, float debe, float afavor,int imag)
    {
        Id=id;
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

    public void setTitulo(String titulo)
    {
        titulo = titulo;
    }

    public void setSubtitulo(String subtitulo)
    {
        subtitulo = subtitulo;
    }

    public void setImagen(int imagen)
    {
        Imagen_titulo = imagen;
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
