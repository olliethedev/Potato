package com.beastpotato.potato.compiler.converters;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public abstract class BaseModelConverter<T, U> {
    public abstract T convert(U model) throws ConversionException;

    public static class ConversionException extends Exception {
        public ConversionException(String message) {
            super(message);
        }
    }
}
