package com.example.vyomkeshjha.CampApp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vyomkeshjha on 18/12/15.
 */
public class DBmanager extends SQLiteOpenHelper {
    public DBmanager()
    {
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
