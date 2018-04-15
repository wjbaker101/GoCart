package com.wjbaker.gocart.shopping;

import android.content.Context;

import com.wjbaker.gocart.database.DatabaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by William on 14/03/2018.
 */
public class ShoppingList
{
    /**
     * Stores the Products the user has currently selected in their shopping list.
     */
    private HashMap<Integer, Product> items;

    /**
     * Stores the context.<br>
     * Allows it to be passed into the DatabaseStorage class.
     */
    private Context context;

    /**
     * Stores the ShoppingList object.
     */
    private static ShoppingList instance;

    /**
     * First time the class is created, initialises the HashMap.<br>
     * Adds database data into the HashMap.
     *
     * @param context Context to pass into the class.
     */
    public ShoppingList(Context context)
    {
        this.items = new HashMap<>();

        this.context = context;

        this.addInitialItems();
    }

    /**
     * Adds the products found in the database when the HashMap is first created.
     */
    private void addInitialItems()
    {
        List<Product> productsFromDatabase = DatabaseStorage.query(this.context).getAllProducts();

        // Loops through all products found in the database
        // Adds the product to the shopping list
        for (Product product : productsFromDatabase)
        {
            this.items.put(product.getTPNB(), product);
        }
    }

    /**
     * Gets a Product from the shopping list.
     *
     * @param tpnb Unique ID of the Tesco product.
     * @return The Product.
     */
    public Product get(int tpnb)
    {
        return this.items.get(tpnb);
    }

    /**
     * Adds a product to the shopping list, specifying its if.
     *
     * @param product The Tesco Product to add.
     */
    public void addItem(Product product)
    {
        // Checks whether the new Product is already inside the HashMap
        // Adds or updates the Product in the database
        if (this.items.containsKey(product.getTPNB()))
        {
            DatabaseStorage.query(this.context).updateProduct(product);
        }
        else
        {
            DatabaseStorage.query(this.context).addProduct(product);
        }

        this.items.put(product.getTPNB(), product);
    }

    /**
     * Removes a product from the shopping list with the given id.
     *
     * @param tpnb TPNB number of the Tesco product.
     */
    public void removeItem(int tpnb)
    {
        DatabaseStorage.query(this.context).deleteProduct(tpnb);

        this.items.remove(tpnb);
    }

    /**
     * Gets the number of items in the shopping list.
     *
     * @return Number of items.
     */
    public int size()
    {
        return this.items.keySet().size();
    }

    /**
     * Gets a product at the given index.
     *
     * @param index Position of the element in the HashMap's keyset.
     * @return The product at the given index.
     */
    public Product getProduct(int index)
    {
        return this.items.get(this.items.keySet().toArray()[index]);
    }

    /**
     * Gets the HashMap of products in the shopping list.
     *
     * @return HashMap containing the products.
     */
    public HashMap<Integer, Product> getProducts()
    {
        return this.items;
    }

    /**
     * Updates a Product on whether it has been checked by the user.<br>
     * Then updates the database with the data of the Product.
     *
     * @param tpnb ID of the Product to update.
     * @param isChecked Whether the Product is to be checked or not.
     */
    public void setProductChecked(int tpnb, boolean isChecked)
    {
        // Makes sure the Product exists
        // Then overrides the current Product with a new Product
        if (this.items.containsKey(tpnb))
        {
            this.items.get(tpnb).setChecked(isChecked);

            DatabaseStorage.query(this.context).updateProduct(this.items.get(tpnb));
        }
    }

    /**
     * Allows the class to act as a singleton.<br>
     * Returns the current ShoppingList object, or creates a new one if none exists.
     *
     * @return The ShoppingList object.
     */
    public static synchronized ShoppingList getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new ShoppingList(context);
        }

        return instance;
    }
}
