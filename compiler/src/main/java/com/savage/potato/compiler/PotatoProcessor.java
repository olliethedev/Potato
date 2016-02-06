package com.savage.potato.compiler;

import com.google.auto.service.AutoService;
import com.savage.potato.compiler.plugin.AccessorPlugIn;
import com.savage.potato.compiler.plugin.BasePlugIn;
import com.savage.potato.compiler.plugin.ProcessorLogger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class PotatoProcessor extends AbstractProcessor implements ProcessorLogger {
    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private List<BasePlugIn> plugIns = new ArrayList<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();

        //Add processor plug-ins
        add(AccessorPlugIn.class);
    }

    private <T extends BasePlugIn> void add(Class<T> clazz) {
        try {
            plugIns.add(clazz.getConstructor(ProcessorLogger.class, Types.class, Elements.class, Filer.class).newInstance(this, typeUtils, elementUtils, filer));
        } catch (Exception e) {
            log(this, Diagnostic.Kind.ERROR, "Could not instantiate " + clazz.getSimpleName());
        }
    }

    @Override
    public Set getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        for (BasePlugIn plugIn : plugIns) {
            try {
                annotations.addAll(plugIn.getSupportedAnnotationTypes());
            } catch (Exception e) {
                log(this, Diagnostic.Kind.ERROR, "Could not add all annotations from " + plugIn.getClass().getSimpleName() + " due to " + e.getMessage());
            }
        }
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set annotations, RoundEnvironment roundEnv) {
        for (BasePlugIn plugIn : plugIns) {
            plugIn.process(annotations, roundEnv);
        }
        return true;
    }

    @Override
    public void log(Object caller, Diagnostic.Kind kind, String msg) {
        messager.printMessage(kind, String.format("Plug-In:%1s \nSays:%2s", caller.getClass().getSimpleName(), msg));
    }
}