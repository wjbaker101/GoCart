package com.wjbaker.gocart.database.tables;

/**
 * Created by William on 29/04/2018.
 *
 * Stores the name of the table as well as the name of the keys that make up the table.
 */
public class TescoStoresTable
{
    public static final String TABLE_NAME = "UsersStore";

    public static final String KEY_NAME = "Name";

    public static final String KEY_LOCATION_LONGITUDE = "Longitude";

    public static final String KEY_LOCATION_LATITUDE = "Latitude";

    public static final String KEY_MONDAY_IS_OPEN = "MondayIsOpen";

    public static final String KEY_MONDAY_OPENING = "MondayOpeningTime";

    public static final String KEY_MONDAY_CLOSING = "MondayClosingTime";

    public static final String KEY_TUESDAY_IS_OPEN = "TuesdayIsOpen";

    public static final String KEY_TUESDAY_OPENING = "TuesdayOpeningTime";

    public static final String KEY_TUESDAY_CLOSING = "TuesdayClosingTime";

    public static final String KEY_WEDNESDAY_IS_OPEN = "WednesdayIsOpen";

    public static final String KEY_WEDNESDAY_OPENING = "WednesdayOpeningTime";

    public static final String KEY_WEDNESDAY_CLOSING = "WednesdayClosingTime";

    public static final String KEY_THURSDAY_IS_OPEN = "ThursdayIsOpen";

    public static final String KEY_THURSDAY_OPENING = "ThursdayOpeningTime";

    public static final String KEY_THURSDAY_CLOSING = "ThursdayClosingTime";

    public static final String KEY_FRIDAY_IS_OPEN = "FridayIsOpen";

    public static final String KEY_FRIDAY_OPENING = "FridayOpeningTime";

    public static final String KEY_FRIDAY_CLOSING = "FridayClosingTime";

    public static final String KEY_SATURDAY_IS_OPEN = "SaturdayIsOpen";

    public static final String KEY_SATURDAY_OPENING = "SaturdayOpeningTime";

    public static final String KEY_SATURDAY_CLOSING = "SaturdayClosingTime";

    public static final String KEY_SUNDAY_IS_OPEN = "SundayIsOpen";

    public static final String KEY_SUNDAY_OPENING = "SundayOpeningTime";

    public static final String KEY_SUNDAY_CLOSING = "SundayClosingTime";

    /**
     * Stores an array of all of the column names in the table.
     */
    public static final String[] COLUMNS =
    {
        KEY_NAME,

        KEY_LOCATION_LONGITUDE,
        KEY_LOCATION_LATITUDE,

        KEY_MONDAY_IS_OPEN,
        KEY_MONDAY_OPENING,
        KEY_MONDAY_CLOSING,

        KEY_TUESDAY_IS_OPEN,
        KEY_TUESDAY_OPENING,
        KEY_TUESDAY_CLOSING,

        KEY_WEDNESDAY_IS_OPEN,
        KEY_WEDNESDAY_OPENING,
        KEY_WEDNESDAY_CLOSING,

        KEY_THURSDAY_IS_OPEN,
        KEY_THURSDAY_OPENING,
        KEY_THURSDAY_CLOSING,

        KEY_FRIDAY_IS_OPEN,
        KEY_FRIDAY_OPENING,
        KEY_FRIDAY_CLOSING,

        KEY_SATURDAY_IS_OPEN,
        KEY_SATURDAY_OPENING,
        KEY_SATURDAY_CLOSING,

        KEY_SUNDAY_IS_OPEN,
        KEY_SUNDAY_OPENING,
        KEY_SUNDAY_CLOSING,
    };
}
