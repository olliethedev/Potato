package com.beastpotato.potato.compiler.generators;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;

import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public abstract class BaseGenerator<T> {
    ProcessorLogger logger;

    public BaseGenerator(ProcessorLogger logger) {
        this.logger = logger;
    }

    public abstract void initialize() throws InitializationException;

    public abstract T generate() throws GenerationException;

    public void log(Diagnostic.Kind kind, String msg) {
        logger.log(this, kind, msg);
    }

    public static class InitializationException extends Exception {
        public InitializationException(String message) {
            super(message);
        }
    }

    public static class GenerationException extends Exception {
        public GenerationException(String message) {
            super(message);
        }
    }
}
