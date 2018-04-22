package com.wjbaker.gocart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.wjbaker.gocart.database.tables.ShoppingListTable;
import com.wjbaker.gocart.shopping.ShoppingList;

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
    public static final int DATABASE_VERSION = 3;

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
        String[] columnInformation =
        {
            String.format("%s INTEGER PRIMARY KEY", ShoppingListTable.KEY_TPNB),
            String.format("%s TEXT", ShoppingListTable.KEY_NAME),
            String.format("%s TEXT", ShoppingListTable.KEY_DESCRIPTION),
            String.format("%s REAL", ShoppingListTable.KEY_COST),
            String.format("%s REAL", ShoppingListTable.KEY_QUANTITY),
            String.format("%s TEXT", ShoppingListTable.KEY_SUPER_DEPARTMENT),
            String.format("%s TEXT", ShoppingListTable.KEY_DEPARTMENT),
            String.format("%s TEXT", ShoppingListTable.KEY_IMAGE_URL),
            String.format("%s BOOLEAN", ShoppingListTable.KEY_CHECKED),
            String.format("%s INTEGER", ShoppingListTable.KEY_AMOUNT),
        };

        String columns = TextUtils.join(", ", columnInformation);

        database.execSQL(String.format("CREATE TABLE %s (%s)", ShoppingListTable.TABLE_NAME, columns));
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
