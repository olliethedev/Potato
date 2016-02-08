package com.beastpotato.potato.compiler.plugin;

import com.beastpotato.potato.api.Accessor;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/6/2016.
 */

public class AccessorPlugIn extends BasePlugIn {
    public AccessorPlugIn(ProcessorLogger logger, Types typeUtils, Elements elementUtils, Filer filer) {
        super(logger, typeUtils, elementUtils, filer);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Accessor.class.getCanonicalName());
        return annotations;
    }

    @Override
    public void process(Set annotations, RoundEnvironment roundEnv) {
        log(Diagnostic.Kind.NOTE, "Starting annotation processing");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Accessor.class)) {
            if (annotatedElement.getKind() != ElementKind.FIELD) {
                log(Diagnostic.Kind.ERROR, "Annotation can only be used with class fields");
            } else {
                String propertyName = annotatedElement.getSimpleName().toString();
                String propertyUpperCase = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                TypeElement te = PlugInUtils.findEnclosingTypeElement(annotatedElement);
                VariableElement ve = (VariableElement) annotatedElement;

                log(Diagnostic.Kind.NOTE, "Field Name:" + annotatedElement.getSimpleName());
                log(Diagnostic.Kind.NOTE, "Field Type:" + ve.asType());
                log(Diagnostic.Kind.NOTE, "Enclosing TypeElement:" + te.getSimpleName());

                MethodSpec getterMethod = MethodSpec.methodBuilder("get" + propertyUpperCase)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.get(ve.asType()))
                        .addStatement("return " + "(new " + te.getSimpleName() + "())." + annotatedElement.getSimpleName())
                        .build();
                TypeSpec accessorClass = TypeSpec.classBuilder(propertyUpperCase + "Accessor")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(getterMethod)
                        .build();
                try {
                    JavaFile.builder(getElementUtils().getPackageOf(te).toString(), accessorClass).build().writeTo(getFiler());
                } catch (IOException e) {
                    log(Diagnostic.Kind.ERROR, e.toString());
                }
            }
        }
    }
}
