package com.wjbaker.gocart.stores;

/**
 * Created by William on 28/04/2018.
 *
 * Stores information about a Tesco specific store.
 */
public class TescoStore
{
    /**
     * Name of the store.
     */
    private String name;

    /**
     * Longitude location of the store.
     */
    private double locationLongitude;

    /**
     * Latitude location of the store.
     */
    private double locationLatitude;

    /**
     * Array of the opening times for each day of the week.
     */
    private DayOpeningTime[] dayOpeningTimes;

    /**
     * Creates a Tesco-specific store with the necessary information needed to be displayed
     * to the user.
     *
     * @param name The name of the store.
     * @param locationLongitude The location of the store.
     * @param locationLatitude The location of the store.
     */
    public TescoStore(String name, double locationLongitude, double locationLatitude, DayOpeningTime[] dayOpeningTimes)
    {
        this.name = name;

        this.locationLongitude = locationLongitude;
        this.locationLatitude = locationLatitude;

        this.dayOpeningTimes = dayOpeningTimes;
    }

    /**
     * Gets the name of the store.
     *
     * @return Name of the store.
     */
    public String getName()
    {
        return this.name;
    }

    public double getLongtitude()
    {
        return this.locationLongitude;
    }

    public double getLatitude()
    {
        return this.locationLatitude;
    }

    public DayOpeningTime[] getDayOpeningTimes()
    {
        return this.dayOpeningTimes;
    }
}
