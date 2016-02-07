package com.beastpotato.potato.compiler.converters;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;

import java.util.List;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public abstract class BaseModelConverter<T, U> {
    private ProcessorLogger logger;
    private Types typeUtils;
    private Elements elementUtils;

    public BaseModelConverter(ProcessorLogger logger, Types typeUtils, Elements elementUtils) {
        this.logger = logger;
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
    }

    public ProcessorLogger getLogger() {
        return logger;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public abstract List<T> convert(U model) throws ConversionException;

    public static class ConversionException extends Exception {
        public ConversionException(String message) {
            super(message);
        }
    }
}
