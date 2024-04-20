package com.trainingkaryawan.util;

import java.util.Date;

public class TimeUtil {
    public TimeUtil() {
    }

    public static Date calculateExpirationTime(long minute) {
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Add 3 minutes to the current time (3 minutes = 3 * 60 * 1000 milliseconds)
        long expirationTimeMillis = currentTimeMillis + (minute * 60 * 1000);

        // Create a Date object representing the expiration time
        return new Date(expirationTimeMillis);
    }

    public static boolean isExpired(Date expirationTime) {
        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();

        // Check if the expiration time is before the current time
        return expirationTime.getTime() < currentTimeMillis;
    }
}
