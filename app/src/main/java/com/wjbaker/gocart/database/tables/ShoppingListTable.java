package com.wjbaker.gocart.database.tables;

/**
 * Created by William on 18/03/2018.
 *
 * Stores the name and keys of the ShoppingList table in the database.
 */
public class ShoppingListTable
{
    /**
     * Stores the name of the table.<br>
     * Table stores the items the user has selected.
     */
    public static final String TABLE_NAME = "ShoppingList";

    /**
     * Stores the "TPNB" column of the table.<br>
     * Stores the unique ID of the Tesco product.
     */
    public static final String KEY_TPNB = "TPNB";

    /**
     * Stores the "Name" column of the table.<br>
     * Stores the name of the product.
     */
    public static final String KEY_NAME = "Name";

    /**
     * Stores the "Description" column of the table.<br>
     * Stores the description about the product.
     */
    public static final String KEY_DESCRIPTION = "Description";

    /**
     * Stores the "Cost" column of the table.<br>
     * Stores the cost of the product (in GBP).
     */
    public static final String KEY_COST = "Cost";

    /**
     * Stores the "Quantity" column of the table.<br>
     * Stores the quantity of the product.
     */
    public static final String KEY_QUANTITY = "Quantity";

    /**
     * Stores the "SuperDepartment" column of the table.<br>
     * Stores the super department of the product.
     */
    public static final String KEY_SUPER_DEPARTMENT = "SuperDepartment";

    /**
     * Stores the "Department" column of the table.<br>
     * Stores the department of the product.
     */
    public static final String KEY_DEPARTMENT = "Department";

    /**
     * Stores the "ImageURL" column of the table.<br>
     * Stores the URL of the image of the product.
     */
    public static final String KEY_IMAGE_URL = "ImageURL";

    /**
     * Stores the "IsChecked" column of the table.<br>
     * Stores whether the Product has been checked by the user.
     */
    public static final String KEY_CHECKED = "IsChecked";

    /**
     * Stores the "Amount" column of the table.<br>
     * Store the number of the Product the user wishes to purchase.
     */
    public static final String KEY_AMOUNT = "Amount";

    /**
     * Stores an array of all of the column names in the table.
     */
    public static final String[] COLUMNS =
    {
        KEY_TPNB,
        KEY_NAME,
        KEY_DESCRIPTION,
        KEY_COST,
        KEY_QUANTITY,
        KEY_SUPER_DEPARTMENT,
        KEY_DEPARTMENT,
        KEY_IMAGE_URL,
        KEY_CHECKED,
        KEY_AMOUNT,
    };
}
