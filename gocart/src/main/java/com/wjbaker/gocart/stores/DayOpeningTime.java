package com.wjbaker.gocart.stores;

/**
 * Created by William on 29/04/2018.
 */
public class DayOpeningTime
{
    /**
     * Whether the store is open at some point during the day.
     */
    private boolean isOpen;

    /**
     * The opening time of the store.<br>
     * Where 0000 is midnight and 2400 is midnight 24 hours later.
     */
    private int openHour;

    /**
     * The closing time of the store.<br>
     * Where 0000 is midnight and 2400 is midnight 24 hours later.
     */
    private int closeHour;

    public DayOpeningTime(boolean isOpen)
    {
        this(isOpen, 0000);
    }

    public DayOpeningTime(boolean isOpen, int openHour)
    {
        this(isOpen, openHour, 2400);
    }

    public DayOpeningTime(boolean isOpen, int openHour, int closeHour)
    {
        this.isOpen = isOpen;

        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public boolean isOpen()
    {
        return this.isOpen;
    }

    public int getOpenHour()
    {
        return this.openHour;
    }

    public int getCloseHour()
    {
        return this.closeHour;
    }

    /**
     * Formats the opening time.
     *
     * @return Formatted opening time of the store.
     */
    public String getFormattedOpeningTime()
    {
        return this.getFormattedTime(this.getOpenHour());
    }

    /**
     * Formats the closing time.
     *
     * @return Formatted closing time of the store.
     */
    public String getFormattedClosingTime()
    {
        return this.getFormattedTime(this.getCloseHour());
    }

    /**
     * Formats an integer value into a formatted time, in the format:<br>
     * 1500 => 3pm
     * 0930 => 9:30am
     *
     * @param time Time to be formatted.
     * @return Formatted string.
     */
    private String getFormattedTime(int time)
    {
        // Gets the individual values for the hour and minutes
        int hours = time / 100;
        int minutes = time - (hours * 100);

        // Creates a template for how the time will be displayed
        String template = "%d:%d%s";

        // Removes the minutes if equal to 0
        if (minutes == 0) template = "%d%s%s";

        // Sets the correct state if the day, am or pm
        String state = "am";
        if (hours > 12)
        {
            state = "pm";
            hours -= 12;
        }

        return String.format(template, hours, (minutes == 0 ? "" : minutes), state);
    }
}
