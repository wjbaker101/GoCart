package com.wjbaker.gocart.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by William on 14/03/2018.
 */
public class ShoppingList
{
    private HashMap<Integer, Product> items;

    public ShoppingList()
    {
        this.items = new HashMap<>();
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
     * @param product The Tesco product to add.
     */
    public void addItem(Product product)
    {
        this.items.put(product.getTPNB(), product);
    }

    /**
     * Removes a product from the shopping list with the given id.
     *
     * @param id TPNB number of the Tesco product.
     */
    public void removeItem(int id)
    {
        this.items.remove(id);
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
     * Stores the ShoppingList object.
     */
    private static ShoppingList instance;

    /**
     * Allows the class to act as a singleton.<br>
     * Returns the current ShoppingList object, or creates a new one if none exists.
     *
     * @return The ShoppingList object.
     */
    public static synchronized ShoppingList getInstance()
    {
        if (instance == null)
        {
            instance = new ShoppingList();
        }

        return instance;
    }
}
