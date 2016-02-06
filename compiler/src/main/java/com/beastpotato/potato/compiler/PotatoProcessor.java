package com.beastpotato.potato.compiler;

import com.beastpotato.potato.compiler.plugin.AccessorPlugIn;
import com.beastpotato.potato.compiler.plugin.BasePlugIn;
import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
    private Map<BasePlugIn, Set<String>> plugInAnnotationMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();

        add(AccessorPlugIn.class);
    }

    private <T extends BasePlugIn> void add(Class<T> clazz) {
        try {
            plugIns.add(clazz.getConstructor(ProcessorLogger.class, Types.class, Elements.class, Filer.class).newInstance(this, typeUtils, elementUtils, filer));
        } catch (Exception e) {
            log(this, Diagnostic.Kind.ERROR, String.format("Could not instantiate %1s due to %2s", clazz.getSimpleName(), e.getMessage()));
        }
    }

    @Override
    public Set getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        for (BasePlugIn plugIn : plugIns) {
            Set<String> supportedAnnotations = plugIn.getSupportedAnnotationTypes();
            for (String annotationName : supportedAnnotations) {
                log(Diagnostic.Kind.NOTE, String.format("Registering annotation %1s for %2s", annotationName, plugIn.getClass().getSimpleName()));
                try {
                    if (!annotations.add(annotationName)) {
                        log(Diagnostic.Kind.ERROR, String.format("Could not register annotation:%1s from plug-in:%s2 because its used by another plugin.", annotationName, plugIn.getClass().getSimpleName()));
                    }
                } catch (Exception e) {
                    log(Diagnostic.Kind.ERROR, String.format("Could not register annotation:%1s from plug-in:%2s due to %3s", annotationName, plugIn.getClass().getSimpleName(), e.getMessage()));
                }
            }
            plugInAnnotationMap.put(plugIn, annotations);
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
            try {
                plugIn.process(plugInAnnotationMap.get(plugIn), roundEnv);
            } catch (Exception e) {
                log(Diagnostic.Kind.ERROR, String.format("Plug-in %1s failed to process annotations", plugIn.getClass().getSimpleName()));
            }
        }
        return true;
    }

    @Override
    public void log(Object caller, Diagnostic.Kind kind, String msg) {
        messager.printMessage(kind, String.format("Plug-In:%1s \nSays:%2s", caller.getClass().getSimpleName(), msg));
    }

    private void log(Diagnostic.Kind kind, String msg) {
        messager.printMessage(kind, String.format("%1s:\n%2s", getClass().getSimpleName(), msg));
    }
}