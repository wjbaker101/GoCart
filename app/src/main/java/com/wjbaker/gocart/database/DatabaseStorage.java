package com.wjbaker.gocart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.text.TextUtils;

import com.wjbaker.gocart.database.tables.ShoppingListTable;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 17/03/2018.
 *
 * Handles the creation of a connection to the database.
 */
public class DatabaseStorage
{
    /**
     * Stores an instance of this class, allowing it to act as a singleton.
     */
    private static DatabaseStorage instance;

    /**
     * Stores the database.
     */
    private DatabaseStorageHelper database;

    /**
     * Initialises the class.<br>
     * Creates the database helper.
     *
     * @param context Context to pass to the database helper.
     */
    public DatabaseStorage(Context context)
    {
        this.database = new DatabaseStorageHelper(context);
    }

    /**
     * Returns this singleton class.
     *
     * @param context Context to pass onto the database helper.
     * @return The instance of the singleton class.
     */
    public synchronized static DatabaseStorage query(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseStorage(context);
        }

        return instance;
    }

    /**
     * Adds the given Product to the database.
     *
     * @param product The new Product to add to the database.
     */
    public void addProduct(Product product)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ShoppingListTable.KEY_TPNB, product.getTPNB());
        values.put(ShoppingListTable.KEY_NAME, product.getName());
        values.put(ShoppingListTable.KEY_DESCRIPTION, product.getDescription());
        values.put(ShoppingListTable.KEY_COST, product.getCost());
        values.put(ShoppingListTable.KEY_QUANTITY, product.getQuantity());
        values.put(ShoppingListTable.KEY_SUPER_DEPARTMENT, product.getSuperDepartment());
        values.put(ShoppingListTable.KEY_DEPARTMENT, product.getDepartment());
        values.put(ShoppingListTable.KEY_IMAGE_URL, product.getImageURL());

        db.insert(ShoppingListTable.TABLE_NAME, null, values);

        db.close();
    }

    /**
     * Updates an existing Product in the database with the data in the given Product.
     *
     * @param product New Product to update in the database.
     */

    /**
     * Updates an existing Product in the database with the data in the given Product.
     *
     * @param product New Product to update in the database.
     * @return The number of rows affected by the changes.
     */
    public int updateProduct(Product product)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ShoppingListTable.KEY_TPNB, product.getTPNB());
        values.put(ShoppingListTable.KEY_NAME, product.getName());
        values.put(ShoppingListTable.KEY_DESCRIPTION, product.getDescription());
        values.put(ShoppingListTable.KEY_COST, product.getCost());
        values.put(ShoppingListTable.KEY_QUANTITY, product.getQuantity());
        values.put(ShoppingListTable.KEY_SUPER_DEPARTMENT, product.getSuperDepartment());
        values.put(ShoppingListTable.KEY_DEPARTMENT, product.getDepartment());
        values.put(ShoppingListTable.KEY_IMAGE_URL, product.getImageURL());

        // Sets up the WHERE condition of the SQL statement
        String condition = String.format(" %s = ?", ShoppingListTable.KEY_TPNB);

        // Stores the values of the WHERE condition
        String[] conditionArgs = { String.valueOf(product.getTPNB()) };

        int rows = db.update(ShoppingListTable.TABLE_NAME, values, condition, conditionArgs);

        return rows;
    }

    /**
     * Gets a Product from the database with the given TPNB.
     *
     * @param tpnb The TPNB of the Product to search for.
     */
    public Product getProduct(int tpnb)
    {
        SQLiteDatabase db = this.database.getReadableDatabase();

        // Sets up the WHERE condition of the SQL statement
        String condition = String.format(" %s = ?", ShoppingListTable.KEY_TPNB);

        // Stores the values of the WHERE condition
        String[] conditionArgs = { String.valueOf(tpnb) };

        Cursor query = db.query(ShoppingListTable.TABLE_NAME, ShoppingListTable.COLUMNS, condition, conditionArgs, null, null, null, null);

        // Return null if no Product exists with the given TPNB
        if (query == null) return null;

        query.moveToFirst();

        String name = query.getString(1);
        String description = query.getString(2);
        float cost = Float.parseFloat(query.getString(3));
        float quantity = Float.parseFloat(query.getString(4));
        String superDepartment = query.getString(5);
        String department = query.getString(6);
        String imageURL = query.getString(7);

        Product product = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

        return product;
    }

    /**
     * Gets all products found in the database.
     *
     * @return List of products.
     */
    public List<Product> getAllProducts()
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Creates a comma-separated string of all columns in the shopping list table
        String values = TextUtils.join(", ", ShoppingListTable.COLUMNS);

        // Constructs the SQL query
        String sql = String.format("SELECT %s FROM %s", values, ShoppingListTable.TABLE_NAME);

        Cursor query = db.rawQuery(sql, null);

        Product product = null;
        List<Product> products = new ArrayList<>();

        // Check whether there are any products
        if (query.moveToFirst())
        {
            do
            {
                int tpnb = Integer.parseInt(query.getString(0));
                String name = query.getString(1);
                String description = query.getString(2);
                float cost = Float.parseFloat(query.getString(3));
                float quantity = Float.parseFloat(query.getString(4));
                String superDepartment = query.getString(5);
                String department = query.getString(6);
                String imageURL = query.getString(7);

                product = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

                products.add(product);
            }
            while (query.moveToNext());
        }

        return products;
    }

    /**
     * Deletes the given product from the database.
     *
     * @param product The product to delete from the database.
     */
    public void deleteProduct(Product product)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Sets up the WHERE condition of the SQL statement
        String condition = String.format(" %s = ?", ShoppingListTable.KEY_TPNB);

        // Stores the values of the WHERE condition
        String[] conditionArgs = { String.valueOf(product.getTPNB()) };

        db.delete(ShoppingListTable.TABLE_NAME, condition, conditionArgs);
    }
}
