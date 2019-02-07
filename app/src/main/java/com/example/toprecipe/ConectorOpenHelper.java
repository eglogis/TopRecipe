package com.example.toprecipe;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ConectorOpenHelper extends SQLiteAssetHelper{

    private static final String DATABASE_NAME = "recetas.db";
    private static final int DATABASE_VERSION = 1;

    public ConectorOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

