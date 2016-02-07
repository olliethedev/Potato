package com.beastpotato.potato.compiler.converters;

/**
 * Created by Oleksiy on 2/7/2016.
 */
public class ConvertorUtils {
    public static String fieldNameToGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String fieldNameToSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
