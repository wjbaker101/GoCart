package com.wjbaker.gocart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wjbaker.gocart.database.tables.ShoppingListTable;

/**
 * Created by William on 17/03/2018.
 *
 * Handles the creation, modification and connection of the database.
 */
public class DatabaseStorageHelper extends SQLiteOpenHelper
{
    /**
     * Stores the current version of the database.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Stores the name of the database.
     */
    public static final String DATABASE_NAME = "GoCart.db";

    public DatabaseStorageHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL("");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL(String.format("DROP TABLE IF EXISTS %s", ShoppingListTable.TABLE_NAME));
        this.onCreate(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        this.onUpgrade(database, oldVersion, newVersion);
    }
}
