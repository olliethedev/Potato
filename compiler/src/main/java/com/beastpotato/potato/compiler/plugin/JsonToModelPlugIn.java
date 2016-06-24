package com.beastpotato.potato.compiler.plugin;

import com.beastpotato.potato.api.Constants;
import com.beastpotato.potato.api.JsonToModel;
import com.beastpotato.potato.compiler.generators.RequestModelGenerator;
import com.beastpotato.potato.compiler.json.JsonCodeWriter;
import com.beastpotato.potato.compiler.json.JsonParser;
import com.sun.codemodel.JCodeModel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 6/19/2016.
 */

public class JsonToModelPlugIn extends BasePlugIn {
    public JsonToModelPlugIn(ProcessorLogger logger, Types typeUtils, Elements elementUtils, Filer filer) {
        super(logger, typeUtils, elementUtils, filer);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(JsonToModel.class.getCanonicalName());
        return annotations;
    }

    @Override
    public void process(Set annotations, RoundEnvironment roundEnv) {
        log(Diagnostic.Kind.NOTE, "Starting annotation processing");
        processJsonToModel(annotations, roundEnv);
    }

    private void processJsonToModel(Set annotations, RoundEnvironment roundEnv) {
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(JsonToModel.class)) {
            if (annotatedElement.getKind() != ElementKind.FIELD) {
                log(Diagnostic.Kind.ERROR, String.format("Only Classes can be annotated with @%1s annotation", JsonToModel.class.getSimpleName()));
            } else {
                VariableElement variableElement = (VariableElement) annotatedElement;
                JsonToModel annotation = variableElement.getAnnotation(JsonToModel.class);
                Constants.ModelType type = annotation.modelType();
                String jsonString = annotation.jsonString();
                String responsePackageName = getElementUtils().getPackageOf(variableElement).toString() + "." + variableElement.getSimpleName().toString().toLowerCase();
                String responseClassName = variableElement.getSimpleName().toString();
                JsonCodeWriter codeWriter = new JsonCodeWriter(getFiler(), getLogger());
                try {
                    JCodeModel responseCodeModel;
                    if (type == Constants.ModelType.GSON_AND_ORM_LITE_COMPAT) {
                        responseCodeModel = JsonParser.parseJsonToOrmLiteModel(responsePackageName, responseClassName, jsonString, getLogger());
                    } else {
                        responseCodeModel = JsonParser.parseJsonToModel(responsePackageName, responseClassName, jsonString, getLogger());
                    }
                    log(Diagnostic.Kind.NOTE, "Writing JSON Java model to file...");
                    responseCodeModel.build(codeWriter);
                    log(Diagnostic.Kind.NOTE, "Writing JSON Java model to file done.");
                    log(Diagnostic.Kind.NOTE, "==================Finished ==================");
                } catch (JsonParser.JsonParserException e) {
                    log(Diagnostic.Kind.ERROR, "Failed to parse sample JSON");
                    e.printStackTrace();
                } catch (IOException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to generate output file for %1s", RequestModelGenerator.class.getSimpleName()));
                    e.printStackTrace();
                }
            }
        }
    }
}
