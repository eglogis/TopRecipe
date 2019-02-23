package com.example.toprecipe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;

public class receta {

    private int id;
    private String nombre;
    private String tiempo;
    private String dificultad;
    private byte[] imagen;
    private Bitmap imagenBitmap;
    private int id_usuario;
    private String categoria;
    private String pdf;
    private int defecto;

    public receta(int id, String nombre, String tiempo, String dificultad, byte[] imagen, int id_usuario, String categoria, String pdf, int defecto) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
        this.imagen = imagen;

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(this.imagen);
        this.imagenBitmap = BitmapFactory.decodeStream(arrayInputStream);

        this.id_usuario = id_usuario;
        this.categoria = categoria;
        this.pdf = pdf;
        this.defecto = defecto;
    }

    public receta(String nombre, String tiempo, String dificultad, byte[] imagen, int id_usuario, String categoria, String pdf) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
        this.imagen = imagen;
        this.id_usuario = id_usuario;
        this.categoria = categoria;
        this.pdf = pdf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Bitmap getImagenBitmap() {
        return imagenBitmap;
    }

    public void setImagenBitmap(Bitmap imagenBitmap) {
        this.imagenBitmap = imagenBitmap;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public int getDefecto() {
        return defecto;
    }

    public void setDefecto(int defecto) {
        this.defecto = defecto;
    }
}
