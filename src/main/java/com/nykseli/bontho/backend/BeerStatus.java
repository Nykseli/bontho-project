package com.nykseli.bontho.backend;

public class BeerStatus {

    public static final Integer[] statusInts = {0, 1, 2, 3};

    public static final String[] statusText = {
        "Bought",
        "Chilled",
        "Opened",
        "Drank"
    };

    public static Integer getStatusIndex(String status) {

        // Loop through statusText array and return the matching index
        for (int i = 0; i < statusInts.length; i++) {
            if (statusText[i].equals(status)) {
                return i;
            }
        }

        // If status doesn't mach anything, return -1
        return -1;
    }
}
