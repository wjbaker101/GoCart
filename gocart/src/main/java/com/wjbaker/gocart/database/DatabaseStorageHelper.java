package com.wjbaker.gocart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

import com.wjbaker.gocart.database.tables.ShoppingListTable;
import com.wjbaker.gocart.database.tables.TescoStoresTable;
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
    public static final int DATABASE_VERSION = 5;

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
        this.createProductsTable(database);
        this.createStoreTable(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        database.execSQL(String.format("DROP TABLE IF EXISTS %s", ShoppingListTable.TABLE_NAME));
        database.execSQL(String.format("DROP TABLE IF EXISTS %s", TescoStoresTable.TABLE_NAME));
        this.onCreate(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        this.onUpgrade(database, oldVersion, newVersion);
    }

    /**
     * Executes an SQL query to create the products table.
     *
     * @param database The database to query.
     */
    private void createProductsTable(SQLiteDatabase database)
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
            String.format("%s INTEGER", ShoppingListTable.KEY_POSITION),
        };

        String columns = TextUtils.join(", ", columnInformation);

        database.execSQL(String.format("CREATE TABLE %s (%s)", ShoppingListTable.TABLE_NAME, columns));
    }

    /**
     * Executes an SQL query to create the user's store table.
     *
     * @param database The database to query.
     */
    private void createStoreTable(SQLiteDatabase database)
    {
        String[] columnInformation =
        {
            String.format("%s TEXT", TescoStoresTable.KEY_NAME),

            String.format("%s REAL", TescoStoresTable.KEY_LOCATION_LONGITUDE),
            String.format("%s REAL", TescoStoresTable.KEY_LOCATION_LATITUDE),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_MONDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_MONDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_MONDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_TUESDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_TUESDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_TUESDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_WEDNESDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_WEDNESDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_WEDNESDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_THURSDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_THURSDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_THURSDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_FRIDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_FRIDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_FRIDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_SATURDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_SATURDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_SATURDAY_CLOSING),

            String.format("%s BOOLEAN", TescoStoresTable.KEY_SUNDAY_IS_OPEN),
            String.format("%s INTEGER", TescoStoresTable.KEY_SUNDAY_OPENING),
            String.format("%s INTEGER", TescoStoresTable.KEY_SUNDAY_CLOSING),
        };

        String columns = TextUtils.join(", ", columnInformation);

        database.execSQL(String.format("CREATE TABLE %s (%s)", TescoStoresTable.TABLE_NAME, columns));
    }

    public Cursor getProducts(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        builder.setTables(ShoppingListTable.TABLE_NAME);

        if (id != null) builder.appendWhere(String.format("%s=%s", ShoppingListTable.KEY_TPNB, id));

        if (sortOrder == null || sortOrder == "") sortOrder = ShoppingListTable.KEY_TPNB;

        Cursor cursor = builder.query(this.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public long addProduct(ContentValues values) throws SQLException
    {
        long id = this.getWritableDatabase().insert(ShoppingListTable.TABLE_NAME, "", values);

        if (id <= 0) throw new SQLException("Unabled to add a new Product.");

        return id;
    }

    public int deleteProduct(String id)
    {
        if (id == null)
        {
            return this.getWritableDatabase().delete(ShoppingListTable.TABLE_NAME, null, null);
        }
        else
        {
            String whereClause = String.format("%s=?", ShoppingListTable.KEY_TPNB);
            String[] whereArgs = { id };

            return this.getWritableDatabase().delete(ShoppingListTable.TABLE_NAME, whereClause, whereArgs);
        }
    }

    public int updateProduct(String id, ContentValues values)
    {
        if (id == null)
        {
            return this.getWritableDatabase().update(ShoppingListTable.TABLE_NAME, values, null, null);
        }
        else
        {
            String whereClause = String.format("%s=?", ShoppingListTable.KEY_TPNB);
            String[] whereArgs = { id };

            return this.getWritableDatabase().update(ShoppingListTable.TABLE_NAME, values, whereClause, whereArgs);
        }
    }
}
