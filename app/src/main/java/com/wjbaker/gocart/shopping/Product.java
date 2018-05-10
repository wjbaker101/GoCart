package com.wjbaker.gocart.shopping;

/**
 * Created by William on 14/03/2018.
 *
 * Stores the various information about a specific Tesco product.
 */
public class Product
{
    /**
     * The unique ID of the Tesco Product, set by Tesco.
     */
    private final int tpnb;

    /**
     * The name of the Product.
     */
    private final String name;

    /**
     * Words or phrases describing the Product.
     */
    private final String description;

    /**
     * The cost of the Product (in GBP).
     */
    private final float cost;

    /**
     * The pack size or weight of the contents of the Product.
     */
    private final float quantity;

    /**
     * The department of the Product.
     */
    private final String superDepartment;

    /**
     * A more specific department of the Product.
     */
    private final String department;

    /**
     * URL of the image containing the Product.
     */
    private final String imageURL;

    /**
     * Whether the user has checked or unchecked this Product.
     */
    private boolean isChecked;

    /**
     * The number of Product the user wishes to purchase.
     */
    private int amount;

    /**
     * The position of the Product in the shopping list.<br>
     * So that the Product is in the same order each time, which the user can change.
     */
    private int position;

    /**
     * The score given by Tesco about how healthy the food product is.
     */
    private int healthScore;

    /**
     * Create the Product with the given immutable properties
     * and set default mutable properties.
     *
     * @param tpnb The unique ID of the Tesco Product, set by Tesco.
     * @param name The name of the Product.
     * @param description Words or phrases describing the Product.
     * @param cost The cost of the Product (in GBP).
     * @param quantity The pack size or weight of the contents of the Product.
     * @param superDepartment The department of the Product.
     * @param department A more specific department of the Product.
     * @param imageURL URL of the image containing the Product.
     */
    public Product(int tpnb, String name, String description, float cost, float quantity, String superDepartment, String department, String imageURL)
    {
        this.tpnb = tpnb;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.superDepartment = superDepartment;
        this.department = department;
        this.imageURL = imageURL;

        this.isChecked = false;
        this.amount = 1;
        this.position = 1;
        this.healthScore = 0;
    }

    /*
     * Getters for all properties of the Product.
     */

    public int getTPNB()
    {
        return tpnb;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public float getCost()
    {
        return cost;
    }

    public float getQuantity()
    {
        return quantity;
    }

    public String getSuperDepartment()
    {
        return superDepartment;
    }

    public String getDepartment()
    {
        return department;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public boolean isChecked()
    {
        return this.isChecked;
    }

    public int getAmount()
    {
        return this.amount;
    }

    public int getPosition()
    {
        return this.position;
    }

    public int getHealthScore()
    {
        return this.healthScore;
    }

    /*
     * Setters for mutable properties of the Product.
     */

    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public void setHealthScore(int healthScore)
    {
        this.healthScore = healthScore;
    }
}
