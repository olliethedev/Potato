package com.savage.potato.compiler;

import com.google.auto.service.AutoService;
import com.savage.potato.api.Accessor;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


@AutoService(Processor.class)
public class PotatoProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    public static TypeElement findEnclosingTypeElement(Element e) {
        while (e != null && !(e instanceof TypeElement)) {
            e = e.getEnclosingElement();
        }
        return TypeElement.class.cast(e);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(Accessor.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Starting annotation processing");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Accessor.class)) {
            if (annotatedElement.getKind() != ElementKind.FIELD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Annotation can only be used with class fields");
            } else {
                String propertyName = annotatedElement.getSimpleName().toString();
                String propertyUpperCase = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                TypeElement te = findEnclosingTypeElement(annotatedElement);
                VariableElement ve = (VariableElement) annotatedElement;
                messager.printMessage(Diagnostic.Kind.NOTE, "Field Name:" + annotatedElement.getSimpleName());
                messager.printMessage(Diagnostic.Kind.NOTE, "Field Type:" + ve.asType());
                messager.printMessage(Diagnostic.Kind.NOTE, "Enclosing TypeElement:" + te.getSimpleName());

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
                    JavaFile.builder("com.savage.potato.example", accessorClass).build().writeTo(filer);
                } catch (IOException e) {
                    messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
                }
            }
        }
        return true;
    }


}