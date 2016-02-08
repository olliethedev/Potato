package com.beastpotato.potato.compiler.plugin;

import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public abstract class BasePlugIn {
    private ProcessorLogger logger;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    public BasePlugIn(ProcessorLogger logger, Types typeUtils, Elements elementUtils, Filer filer) {
        this.logger = logger;
        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    public abstract Set<String> getSupportedAnnotationTypes();

    public void log(Diagnostic.Kind kind, String msg) {
        logger.log(this, kind, msg);
    }

    public abstract void process(Set annotations, RoundEnvironment roundEnv);

    public Filer getFiler() {
        return filer;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public ProcessorLogger getLogger() {
        return logger;
    }
}
