package com.wjbaker.gocart.util;

import java.text.DecimalFormat;
import java.util.Locale;

public abstract class NumberUtils {

    private static final DecimalFormat TWO_DECIMAL_PLACES = new DecimalFormat(".##");

    private NumberUtils() {}

    public static String formatNumber(final double number) {
        return String.format(Locale.UK, "%.2f", number);
    }
}
