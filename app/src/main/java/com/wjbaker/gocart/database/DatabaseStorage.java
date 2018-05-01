package com.wjbaker.gocart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.text.TextUtils;

import com.wjbaker.gocart.database.tables.ShoppingListTable;
import com.wjbaker.gocart.database.tables.TescoStoresTable;
import com.wjbaker.gocart.shopping.Product;
import com.wjbaker.gocart.shopping.ShoppingList;
import com.wjbaker.gocart.stores.DayOpeningTime;
import com.wjbaker.gocart.stores.TescoStore;

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
        values.put(ShoppingListTable.KEY_CHECKED, product.isChecked());
        values.put(ShoppingListTable.KEY_AMOUNT, product.getAmount());

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
        values.put(ShoppingListTable.KEY_CHECKED, product.isChecked());
        values.put(ShoppingListTable.KEY_AMOUNT, product.getAmount());

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
        Boolean isChecked = Boolean.parseBoolean(query.getString(8));
        int amount = Integer.parseInt(query.getString(9));

        Product product = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

        product.setChecked(isChecked);
        product.setAmount(amount);

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
                Boolean isChecked = query.getString(8).equals("1");
                int amount = Integer.parseInt(query.getString(9));

                product = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

                product.setChecked(isChecked);
                product.setAmount(amount);

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

    /**
     * Deletes a Product from the database with the given TPNB.
     *
     * @param tpnb The TPNB of the Product to delete.
     */
    public void deleteProduct(int tpnb)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        // Sets up the WHERE condition of the SQL statement
        String condition = String.format(" %s = ?", ShoppingListTable.KEY_TPNB);

        // Stores the values of the WHERE condition
        String[] conditionArgs = { String.valueOf(tpnb) };

        db.delete(ShoppingListTable.TABLE_NAME, condition, conditionArgs);
    }

    /**
     * Add a Tesco store to the database.
     *
     * @param tescoStore The TescoStore to add to the database.
     */
    public void addTescoStore(TescoStore tescoStore)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TescoStoresTable.KEY_NAME, tescoStore.getName());

        values.put(TescoStoresTable.KEY_LOCATION_LONGITUDE, tescoStore.getLongtitude());
        values.put(TescoStoresTable.KEY_LOCATION_LATITUDE, tescoStore.getLatitude());

        values.put(TescoStoresTable.KEY_MONDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[0].isOpen());
        values.put(TescoStoresTable.KEY_MONDAY_OPENING, tescoStore.getDayOpeningTimes()[0].getOpenHour());
        values.put(TescoStoresTable.KEY_MONDAY_CLOSING, tescoStore.getDayOpeningTimes()[0].getCloseHour());

        values.put(TescoStoresTable.KEY_TUESDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[1].isOpen());
        values.put(TescoStoresTable.KEY_TUESDAY_OPENING, tescoStore.getDayOpeningTimes()[1].getOpenHour());
        values.put(TescoStoresTable.KEY_TUESDAY_CLOSING, tescoStore.getDayOpeningTimes()[1].getCloseHour());

        values.put(TescoStoresTable.KEY_WEDNESDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[2].isOpen());
        values.put(TescoStoresTable.KEY_WEDNESDAY_OPENING, tescoStore.getDayOpeningTimes()[2].getOpenHour());
        values.put(TescoStoresTable.KEY_WEDNESDAY_CLOSING, tescoStore.getDayOpeningTimes()[2].getCloseHour());

        values.put(TescoStoresTable.KEY_THURSDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[3].isOpen());
        values.put(TescoStoresTable.KEY_THURSDAY_OPENING, tescoStore.getDayOpeningTimes()[3].getOpenHour());
        values.put(TescoStoresTable.KEY_THURSDAY_CLOSING, tescoStore.getDayOpeningTimes()[3].getCloseHour());

        values.put(TescoStoresTable.KEY_FRIDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[4].isOpen());
        values.put(TescoStoresTable.KEY_FRIDAY_OPENING, tescoStore.getDayOpeningTimes()[4].getOpenHour());
        values.put(TescoStoresTable.KEY_FRIDAY_CLOSING, tescoStore.getDayOpeningTimes()[4].getCloseHour());

        values.put(TescoStoresTable.KEY_SATURDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[5].isOpen());
        values.put(TescoStoresTable.KEY_SATURDAY_OPENING, tescoStore.getDayOpeningTimes()[5].getOpenHour());
        values.put(TescoStoresTable.KEY_SATURDAY_CLOSING, tescoStore.getDayOpeningTimes()[5].getCloseHour());

        values.put(TescoStoresTable.KEY_SUNDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[6].isOpen());
        values.put(TescoStoresTable.KEY_SUNDAY_OPENING, tescoStore.getDayOpeningTimes()[6].getOpenHour());
        values.put(TescoStoresTable.KEY_SUNDAY_CLOSING, tescoStore.getDayOpeningTimes()[6].getCloseHour());

        db.insert(TescoStoresTable.TABLE_NAME, null, values);

        db.close();
    }

    /**
     * Gets the TescoStore stored in the database.
     *
     * @return TescoStore found from the database.
     */
    public TescoStore getTescoStore()
    {
        SQLiteDatabase db = this.database.getReadableDatabase();

        Cursor query = db.query(TescoStoresTable.TABLE_NAME, TescoStoresTable.COLUMNS, null, null, null, null, null, null);

        // Return null if no store currently exists
        if (query == null || !query.moveToFirst()) return null;

        // Stores the current column position in the cursor
        // Makes it easier to initialise the columns 1-by-1
        // Works as long as the columns are got in order
        int index = -1;

        String name = query.getString(++index);
        System.out.println(name + " === " + query.getString(index));

        double longitude = query.getDouble(++index);
        double latitude = query.getDouble(++index);

        boolean mondayIsOpen = query.getString(++index).equals("1");
        System.out.println(mondayIsOpen + " == " + query.getString(index));
        int mondayOpening = Integer.parseInt(query.getString(++index));
        int mondayClosing = Integer.parseInt(query.getString(++index));

        boolean tuesdayIsOpen = query.getString(++index).equals("1");
        int tuesdayOpening = Integer.parseInt(query.getString(++index));
        int tuesdayClosing = Integer.parseInt(query.getString(++index));

        boolean wednesdayIsOpen = query.getString(++index).equals("1");
        int wednesdayOpening = Integer.parseInt(query.getString(++index));
        int wednesdayClosing = Integer.parseInt(query.getString(++index));

        boolean thursdayIsOpen = query.getString(++index).equals("1");
        int thursdayOpening = Integer.parseInt(query.getString(++index));
        int thursdayClosing = Integer.parseInt(query.getString(++index));

        boolean fridayIsOpen = query.getString(++index).equals("1");
        int fridayOpening = Integer.parseInt(query.getString(++index));
        int fridayClosing = Integer.parseInt(query.getString(++index));

        boolean saturdayIsOpen = query.getString(++index).equals("1");
        int saturdayOpening = Integer.parseInt(query.getString(++index));
        int saturdayClosing = Integer.parseInt(query.getString(++index));

        boolean sundayIsOpen = query.getString(++index).equals("1");
        int sundayOpening = Integer.parseInt(query.getString(++index));
        int sundayClosing = Integer.parseInt(query.getString(++index));

        DayOpeningTime[] dayOpeningTimes = new DayOpeningTime[]
        {
            new DayOpeningTime(mondayIsOpen, mondayOpening, mondayClosing),
            new DayOpeningTime(tuesdayIsOpen, tuesdayOpening, tuesdayClosing),
            new DayOpeningTime(wednesdayIsOpen, wednesdayOpening, wednesdayClosing),
            new DayOpeningTime(thursdayIsOpen, thursdayOpening, thursdayClosing),
            new DayOpeningTime(fridayIsOpen, fridayOpening, fridayClosing),
            new DayOpeningTime(saturdayIsOpen, saturdayOpening, saturdayClosing),
            new DayOpeningTime(sundayIsOpen, sundayOpening, sundayClosing),
        };

        TescoStore store = new TescoStore(name, longitude, latitude, dayOpeningTimes);

        return store;
    }

    /**
     * Updates an existing Product in the database with the data in the given Product.
     *
     * @param product New Product to update in the database.
     */

    /**
     * Updates an existing store in the database with the data in the given store.
     *
     * @param tescoStore The store to update in the database.
     * @return The number of rows affected by the changes.
     */
    public int updateStore(TescoStore tescoStore)
    {
        SQLiteDatabase db = this.database.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TescoStoresTable.KEY_NAME, tescoStore.getName());

        values.put(TescoStoresTable.KEY_LOCATION_LONGITUDE, tescoStore.getLongtitude());
        values.put(TescoStoresTable.KEY_LOCATION_LATITUDE, tescoStore.getLatitude());

        values.put(TescoStoresTable.KEY_MONDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[0].isOpen());
        values.put(TescoStoresTable.KEY_MONDAY_OPENING, tescoStore.getDayOpeningTimes()[0].getOpenHour());
        values.put(TescoStoresTable.KEY_MONDAY_CLOSING, tescoStore.getDayOpeningTimes()[0].getCloseHour());

        values.put(TescoStoresTable.KEY_TUESDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[1].isOpen());
        values.put(TescoStoresTable.KEY_TUESDAY_OPENING, tescoStore.getDayOpeningTimes()[1].getOpenHour());
        values.put(TescoStoresTable.KEY_TUESDAY_CLOSING, tescoStore.getDayOpeningTimes()[1].getCloseHour());

        values.put(TescoStoresTable.KEY_WEDNESDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[2].isOpen());
        values.put(TescoStoresTable.KEY_WEDNESDAY_OPENING, tescoStore.getDayOpeningTimes()[2].getOpenHour());
        values.put(TescoStoresTable.KEY_WEDNESDAY_CLOSING, tescoStore.getDayOpeningTimes()[2].getCloseHour());

        values.put(TescoStoresTable.KEY_THURSDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[3].isOpen());
        values.put(TescoStoresTable.KEY_THURSDAY_OPENING, tescoStore.getDayOpeningTimes()[3].getOpenHour());
        values.put(TescoStoresTable.KEY_THURSDAY_CLOSING, tescoStore.getDayOpeningTimes()[3].getCloseHour());

        values.put(TescoStoresTable.KEY_FRIDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[4].isOpen());
        values.put(TescoStoresTable.KEY_FRIDAY_OPENING, tescoStore.getDayOpeningTimes()[4].getOpenHour());
        values.put(TescoStoresTable.KEY_FRIDAY_CLOSING, tescoStore.getDayOpeningTimes()[4].getCloseHour());

        values.put(TescoStoresTable.KEY_SATURDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[5].isOpen());
        values.put(TescoStoresTable.KEY_SATURDAY_OPENING, tescoStore.getDayOpeningTimes()[5].getOpenHour());
        values.put(TescoStoresTable.KEY_SATURDAY_CLOSING, tescoStore.getDayOpeningTimes()[5].getCloseHour());

        values.put(TescoStoresTable.KEY_SUNDAY_IS_OPEN, tescoStore.getDayOpeningTimes()[6].isOpen());
        values.put(TescoStoresTable.KEY_SUNDAY_OPENING, tescoStore.getDayOpeningTimes()[6].getOpenHour());
        values.put(TescoStoresTable.KEY_SUNDAY_CLOSING, tescoStore.getDayOpeningTimes()[6].getCloseHour());

        int rows = db.update(TescoStoresTable.TABLE_NAME, values, null, null);

        return rows;
    }
}
