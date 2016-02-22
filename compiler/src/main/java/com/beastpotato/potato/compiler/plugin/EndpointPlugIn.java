package com.beastpotato.potato.compiler.plugin;

import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;
import com.beastpotato.potato.api.Validation;
import com.beastpotato.potato.compiler.converters.BaseModelConverter;
import com.beastpotato.potato.compiler.converters.RequestModelConverter;
import com.beastpotato.potato.compiler.generators.BaseGenerator;
import com.beastpotato.potato.compiler.generators.RequestModelGenerator;
import com.beastpotato.potato.compiler.generators.ValidatorModelGenerator;
import com.beastpotato.potato.compiler.json.JsonCodeWriter;
import com.beastpotato.potato.compiler.json.JsonParser;
import com.beastpotato.potato.compiler.models.RequestModel;
import com.beastpotato.potato.compiler.models.ValidatorModel;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.sun.codemodel.JCodeModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class EndpointPlugIn extends BasePlugIn {
    private boolean isBaseGenerated = false;
    public EndpointPlugIn(ProcessorLogger logger, Types typeUtils, Elements elementUtils, Filer filer) {
        super(logger, typeUtils, elementUtils, filer);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Endpoint.class.getCanonicalName());
        annotations.add(UrlPathParam.class.getCanonicalName());
        annotations.add(UrlParam.class.getCanonicalName());
        annotations.add(HeaderParam.class.getCanonicalName());
        annotations.add(Validation.class.getCanonicalName());
        return annotations;
    }

    @Override
    public void process(Set annotations, RoundEnvironment roundEnv) {
        log(Diagnostic.Kind.NOTE, "Starting annotation processing");
        HashMap<String, ValidatorModel> validatorModelHashMap = new HashMap<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Validation.class)) {
            if (annotatedElement.getKind() != ElementKind.METHOD) {
                log(Diagnostic.Kind.ERROR, String.format("Only methods can be annotated with @%1s annotation", Validation.class.getSimpleName()));
            } else {
                ValidatorModelGenerator validatorModelGenerator = new ValidatorModelGenerator(annotatedElement, getLogger());
                try {
                    validatorModelGenerator.initialize();
                    ValidatorModel validatorModel = validatorModelGenerator.generate();
                    validatorModel.setPackageName(getElementUtils().getPackageOf(annotatedElement).toString());
                    validatorModelHashMap.put(validatorModel.getFiledName(), validatorModel);
                } catch (BaseGenerator.InitializationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to initialize %1s due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (BaseGenerator.GenerationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Code generator %1s failed due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                }
            }
        }
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Endpoint.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                log(Diagnostic.Kind.ERROR, String.format("Only Classes can be annotated with @%1s annotation", Endpoint.class.getSimpleName()));
            } else {
                TypeElement te = (TypeElement) annotatedElement;
                RequestModelGenerator requestModelGenerator = new RequestModelGenerator(te, getTypeUtils(), getElementUtils(), getLogger());
                JsonCodeWriter codeWriter = new JsonCodeWriter(getFiler(), getLogger());
                RequestModelConverter modelConverter = new RequestModelConverter(getLogger(), getTypeUtils(), getElementUtils(), validatorModelHashMap);
                try {
                    requestModelGenerator.initialize();
                    RequestModel model = requestModelGenerator.generate();
                    String packageName = getElementUtils().getPackageOf(te).toString();
                    String responsePackageName = getElementUtils().getPackageOf(te).toString() + "." + te.getSimpleName().toString().toLowerCase() + "response";
                    String responseClassName = te.getSimpleName() + "ApiResponse";
                    model.setPackageName(packageName);
                    model.setResponseClassName(responseClassName);
                    model.setResponsePackageName(responsePackageName);
                    JCodeModel responseCodeModel = JsonParser.parseJsonToModel(responsePackageName, responseClassName, model.getExampleJson(), getLogger());
                    log(Diagnostic.Kind.NOTE, "Writing JSON Java model to file...");
                    responseCodeModel.build(codeWriter);
                    log(Diagnostic.Kind.NOTE, "Writing JSON Java model to file done.");
                    List<TypeSpec> typeSpecList = modelConverter.convert(model);
                    log(Diagnostic.Kind.NOTE, "Writing Endpoint request objects to file...");
                    if (!isBaseGenerated) {
                        typeSpecList.add(RequestModelConverter.getRequestSuperClass());//must be generated once or Filer will throw
                        isBaseGenerated = true;
                    }
                    for (TypeSpec typeSpec : typeSpecList) {
                        log(Diagnostic.Kind.NOTE, "Writing " + typeSpec.name + "...");
                        JavaFile.builder(packageName, typeSpec).build().writeTo(getFiler());
                    }
                    log(Diagnostic.Kind.NOTE, "Writing request objects to file done.");
                    log(Diagnostic.Kind.NOTE, "==================Finished ==================");
                } catch (BaseGenerator.InitializationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to initialize %1s due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (BaseGenerator.GenerationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Code generator %1s failed due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (BaseModelConverter.ConversionException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to convert %1s due to %2s", RequestModelConverter.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (JsonParser.JsonParserException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to parse sample JSON"));
                    e.printStackTrace();
                } catch (IOException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to generate output file for %1s", RequestModelGenerator.class.getSimpleName()));
                    e.printStackTrace();
                }
            }
        }
    }
}
