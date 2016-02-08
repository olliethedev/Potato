package com.beastpotato.potato.compiler.plugin;

import com.beastpotato.potato.api.Body;
import com.beastpotato.potato.api.Endpoint;
import com.beastpotato.potato.api.HeaderParam;
import com.beastpotato.potato.api.UrlParam;
import com.beastpotato.potato.api.UrlPathParam;
import com.beastpotato.potato.compiler.converters.BaseModelConverter;
import com.beastpotato.potato.compiler.converters.RequestModelConverter;
import com.beastpotato.potato.compiler.generators.BaseGenerator;
import com.beastpotato.potato.compiler.generators.RequestModelGenerator;
import com.beastpotato.potato.compiler.models.RequestModel;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
        annotations.add(Body.class.getCanonicalName());
        return annotations;
    }

    @Override
    public void process(Set annotations, RoundEnvironment roundEnv) {
        log(Diagnostic.Kind.NOTE, "Starting annotation processing");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Endpoint.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                log(Diagnostic.Kind.ERROR, String.format("Only Classes can be annotated with @%1s annotation", Endpoint.class.getSimpleName()));
            } else {
                TypeElement te = (TypeElement) annotatedElement;
                RequestModelGenerator requestModelGenerator = new RequestModelGenerator(te, getTypeUtils(), getElementUtils(), getLogger());
                try {
                    log(Diagnostic.Kind.NOTE, "initializing...");
                    requestModelGenerator.initialize();
                    log(Diagnostic.Kind.NOTE, "initialization done.");
                    log(Diagnostic.Kind.NOTE, "generating model...");
                    RequestModel model = requestModelGenerator.generate();
                    log(Diagnostic.Kind.NOTE, "model generated...");
                    RequestModelConverter modelConverter = new RequestModelConverter(getLogger(), getTypeUtils(), getElementUtils());
                    List<TypeSpec> typeSpecList = modelConverter.convert(model);
                    typeSpecList.add(RequestModelConverter.getRequestSuperClass());//must be generated once or Filer will throw
                    for (TypeSpec typeSpec : typeSpecList) {
                        JavaFile.builder(getElementUtils().getPackageOf(te).toString(), typeSpec).build().writeTo(getFiler());
                    }
                } catch (BaseGenerator.InitializationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to initialize %1s due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (BaseGenerator.GenerationException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Code generator %1s failed due to %2s", RequestModelGenerator.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (BaseModelConverter.ConversionException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to convert %1s due to %2s", RequestModelConverter.class.getSimpleName(), e.getMessage()));
                    e.printStackTrace();
                } catch (IOException e) {
                    log(Diagnostic.Kind.ERROR, String.format("Failed to generate output file for %1s", RequestModelGenerator.class.getSimpleName()));
                    e.printStackTrace();
                }
            }
        }
    }
}
