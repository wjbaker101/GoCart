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
    }

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

    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
