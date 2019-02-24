/*
 * Realizado por: Samuel Bautista Sanchez
 * DNI: 20227866X
 * Asignatura: Desarrollo de Aplicaciones Multiplataforma
 * */

package com.example.toprecipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ConectorBaseDeDatos {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static ConectorBaseDeDatos instance;

    private ConectorBaseDeDatos(Context context) {
        this.openHelper = new ConectorOpenHelper(context);
    }

    public static ConectorBaseDeDatos getInstance(Context context) {
        if (instance == null) {
            instance = new ConectorBaseDeDatos(context);
        }
        return instance;
    }

    public void AbrirConexion() {
        this.database = openHelper.getWritableDatabase();
    }

    public void CerrarConexcion() {
        if (database != null) {
            this.database.close();
        }
    }

    ArrayList<receta> todas_las_recetas(){

        Cursor c;

        ArrayList<receta> arrayRecetas = new ArrayList<receta>();

        c= database.rawQuery(" SELECT * FROM receta", null);

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                arrayRecetas.add(new receta(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4), c.getInt(5), c.getString(6), c.getString(7), c.getInt(8)));

            } while(c.moveToNext());
        }
        c.close();

        return arrayRecetas;
    }

    ArrayList<receta> recetasPorCategoria(String categoria){

        Cursor c;

        ArrayList<receta> arrayListCategoria = new ArrayList<>();

        c = database.rawQuery("Select * from receta where receta.categoria = " + "'" +categoria + "'", null);

        if(c.moveToFirst()){

            do{

                arrayListCategoria.add(new receta(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4), c.getInt(5), c.getString(6), c.getString(7), c.getInt(8)));


            }while(c.moveToNext());

        }
        c.close();
        return arrayListCategoria;
    }

    ArrayList<receta> recetasPorUsuario(int id){

        Cursor c;

        ArrayList<receta> arrayListCategoria = new ArrayList<>();

        c = database.rawQuery("Select * from receta where receta.id_usuario = " + id, null);

        if(c.moveToFirst()){

            do{

                arrayListCategoria.add(new receta(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4), c.getInt(5), c.getString(6), c.getString(7), c.getInt(8)));


            }while(c.moveToNext());

        }
        c.close();
        return arrayListCategoria;
    }

    public void insertarReceta(String nombre, String tiempo, String dificultad, byte[] fotoReceta, int id_usuario, String categoria, String pdf){

        ContentValues valores = new ContentValues();

        valores.put("nombre", nombre);
        valores.put("tiempo", tiempo);
        valores.put("dificultad", dificultad);
        valores.put("foto", fotoReceta);
        valores.put("id_usuario", id_usuario);
        valores.put("categoria", categoria);
        valores.put("pdf", pdf);
        valores.put("defecto", 0);

        database.insert("receta", null, valores);
    }



    public void borrarReceta(int id){


        database.delete("receta", "id=" + id,null);


    }
}
