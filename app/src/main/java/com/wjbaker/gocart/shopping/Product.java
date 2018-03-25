package com.wjbaker.gocart.shopping;

/**
 * Created by William on 14/03/2018.
 *
 * Stores the various information about a specific Tesco product.
 */
public class Product
{
    private int tpnb;

    private String name;

    private String description;

    private float cost;

    private float quantity;

    private String superDepartment;

    private String department;

    private String imageURL;

    private boolean isChecked;

    public Product(int tpnb, String name, String description, float cost, float quantity, String superDepartment, String department, String imageURL)
    {
        this(tpnb, name, description, cost, quantity, superDepartment, department, imageURL, false);
    }

    public Product(int tpnb, String name, String description, float cost, float quantity, String superDepartment, String department, String imageURL, boolean isChecked)
    {
        this.tpnb = tpnb;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.superDepartment = superDepartment;
        this.department = department;
        this.imageURL = imageURL;

        this.isChecked = isChecked;
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

    public Product setChecked(boolean isChecked)
    {
        return new Product(this.getTPNB(), this.getName(), this.getDescription(), this.getCost(), this.getQuantity(), this.getSuperDepartment(), this.getDepartment(), this.getImageURL(), isChecked);
    }
}
