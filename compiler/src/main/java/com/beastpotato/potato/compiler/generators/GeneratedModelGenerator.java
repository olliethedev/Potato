package com.beastpotato.potato.compiler.generators;

import com.beastpotato.potato.compiler.models.GeneratedModel;
import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 3/7/2016.
 */
public class GeneratedModelGenerator extends BaseGenerator<GeneratedModel> {
    private final Elements elementUtils;
    private TypeElement classElement;
    private List<VariableElement> serializedNameFields = new ArrayList<>();

    public GeneratedModelGenerator(TypeElement classElement, ProcessorLogger logger, Elements elementUtils) {
        super(logger);
        this.classElement = classElement;
        this.elementUtils = elementUtils;
    }

    @Override
    public void initialize() throws InitializationException {
        log(Diagnostic.Kind.NOTE, "initializing...");
        try {
            for (Element element : classElement.getEnclosedElements()) {
                if (isVariableElementAnnotated(element, SerializedName.class)) {
                    serializedNameFields.add((VariableElement) element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InitializationException(e.getMessage());
        }
        log(Diagnostic.Kind.NOTE, "initialization done.");
    }

    @Override
    public GeneratedModel generate() throws GenerationException {
        try {
            log(Diagnostic.Kind.NOTE, "generating model...");
            GeneratedModel model = new GeneratedModel(classElement);
            for (VariableElement variableElement : serializedNameFields) {
                model.addField(elementUtils.getPackageOf(variableElement), variableElement.getAnnotation(SerializedName.class).value(), variableElement.getSimpleName().toString(), variableElement.asType());
            }
            String packageName = elementUtils.getPackageOf(classElement).toString();
            model.setPackageName(packageName);
            log(Diagnostic.Kind.NOTE, "model generated...");
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenerationException(String.format("Generation failed due to %1s", e.getMessage()));
        }
    }

    private <A extends Annotation> boolean isVariableElementAnnotated(Element element, Class<A> annotation) throws InitializationException {
        if (element.getKind() != ElementKind.FIELD && element.getAnnotation(annotation) != null) {
            throw new InitializationException(String.format("Only fields can be annotated with @%1s", annotation.getSimpleName()));
        } else
            return element.getKind() == ElementKind.FIELD && element.getAnnotation(annotation) != null;
    }
}
