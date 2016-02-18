package com.beastpotato.potato.compiler.generators;

import com.beastpotato.potato.api.Validation;
import com.beastpotato.potato.compiler.models.ValidatorModel;
import com.beastpotato.potato.compiler.plugin.ProcessorLogger;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/17/2016.
 */
public class ValidatorModelGenerator extends BaseGenerator<ValidatorModel> {
    private Element rawElement;
    private ExecutableElement methodElement;

    public ValidatorModelGenerator(Element element, ProcessorLogger logger) {
        super(logger);
        this.rawElement = element;
    }

    @Override
    public void initialize() throws InitializationException {
        log(Diagnostic.Kind.NOTE, "initializing...");
        if (!isValidMethodElement(rawElement)) {
            throw new InitializationException(String.format("Element annotated with %1s does not return boolean or is not a static method", Validation.class.getCanonicalName()));
        } else {
            methodElement = (ExecutableElement) rawElement;
        }
        log(Diagnostic.Kind.NOTE, "initialization done.");
    }

    private boolean isValidMethodElement(Element methodElement) {
        if (methodElement.getModifiers().contains(Modifier.STATIC) && methodElement.getKind() == ElementKind.METHOD) {
            ExecutableElement executableElement = (ExecutableElement) methodElement;
            if (executableElement.getReturnType().getKind() == TypeKind.BOOLEAN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ValidatorModel generate() throws GenerationException {
        log(Diagnostic.Kind.NOTE, "generating model...");
        try {
            Validation annotation = methodElement.getAnnotation(Validation.class);
            ValidatorModel vm = new ValidatorModel();
            vm.setFiledName(annotation.fieldName());
            vm.setMethodName(methodElement.getEnclosingElement().getSimpleName() + "." + methodElement.getSimpleName().toString());
            vm.setParamType(methodElement.getReturnType());
            log(Diagnostic.Kind.NOTE, "model generated...");
            return vm;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenerationException(String.format("Generation failed due to %1s", e.getMessage()));
        }
    }
}
