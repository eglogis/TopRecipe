package com.example.toprecipe;

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

                arrayRecetas.add(new receta(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getBlob(4), c.getInt(5), c.getString(6), c.getString(7)));

            } while(c.moveToNext());
        }
        c.close();

        return arrayRecetas;
    }
}
