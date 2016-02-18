package com.beastpotato.potato.example.Utils;

import com.beastpotato.potato.api.Validation;

/**
 * Created by Oleksiy on 2/17/2016.
 */
public class Utils {
    @Validation(fieldName = "category")
    public static boolean isValidCategory(String category) {
        return category != null;
    }

    @Validation(fieldName = "sort")
    public static boolean isValidSortType(String sortType) {
        return sortType != null;
    }

    @Validation(fieldName = "X-Mashape-Key")
    public static boolean isValidApiKey(String keyToCheck) {
        return keyToCheck != null && keyToCheck.length() > 4;
    }

    @Validation(fieldName = "Accept")
    public static boolean isValidContentType(String contentType) {
        return contentType != null && contentType.equals("application/json");
    }
}
