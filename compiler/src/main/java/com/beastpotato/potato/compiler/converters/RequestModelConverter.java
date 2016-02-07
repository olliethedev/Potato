package com.beastpotato.potato.compiler.converters;

import com.beastpotato.potato.compiler.models.RequestModel;
import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class RequestModelConverter extends BaseModelConverter<TypeSpec, RequestModel> {
    private static TypeSpec superTypeSpec;
    private static ClassName contextClass = ClassName.get("android.content", "Context"),
            requestQueueClass = ClassName.get("com.android.volley", "RequestQueue");

    public RequestModelConverter(ProcessorLogger logger, Types typeUtils, Elements elementUtils) {
        super(logger, typeUtils, elementUtils);
    }

    public static TypeSpec getRequestSuperClass() {
        if (superTypeSpec == null) {

            FieldSpec requestQueue = FieldSpec.builder(requestQueueClass, "requestQueue")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();
            FieldSpec context = FieldSpec.builder(contextClass, "context")
                    .addModifiers(Modifier.PRIVATE)
                    .build();
            CodeBlock constructorBlock = CodeBlock.builder()
                    .beginControlFlow("if(requestQueue==null)")
                    .addStatement("requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(this.context,1024 * 1024 * 2);")
                    .endControlFlow()
                    .build();
            MethodSpec constructor = MethodSpec.constructorBuilder()
                    .addParameter(contextClass, "context")
                    .addStatement("this.context = context")
                    .addCode(constructorBlock)
                    .build();
            MethodSpec getRequestQueue = MethodSpec.methodBuilder("getRequestQueue")
                    .returns(requestQueueClass)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return this.requestQueue")
                    .build();
            superTypeSpec = TypeSpec.classBuilder("RequestBase")
                    .addModifiers(Modifier.ABSTRACT)
                    .addField(requestQueue)
                    .addField(context)
                    .addMethod(getRequestQueue)
                    .addMethod(constructor)
                    .build();
        }
        return superTypeSpec;
    }

    @Override
    public List<TypeSpec> convert(RequestModel model) throws ConversionException {
        try {
            List<TypeSpec> typeSpecs = new ArrayList<>();
            TypeSpec requestSuper = getRequestSuperClass();
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(model.getModelName() + "ApiRequest")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(makeConstructor())
                    .addMethod(makeSendMethod())
                    .superclass(ClassName.get(getElementUtils().getPackageOf(model.getTypeElement()).getQualifiedName().toString(), requestSuper.name));
            for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
                FieldSpec fs = convertFieldDef(fieldDef);
                classBuilder.addField(fs);
                classBuilder.addMethod(makeGetter(fs));
            }
            TypeSpec request = classBuilder.build();
            typeSpecs.add(request);
            return typeSpecs;
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert %1s to TypeSpec", model.getClass().getSimpleName()));
        }
    }

    private MethodSpec makeConstructor() {
        return MethodSpec.constructorBuilder()
                .addParameter(contextClass, "context")
                .addStatement("super(context)")
                .build();
    }

    private FieldSpec convertFieldDef(RequestModel.RequestModelFieldDef fieldDef) {
        return FieldSpec.builder(TypeName.get(fieldDef.fieldClassType), fieldDef.fieldName)
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    private MethodSpec makeGetter(FieldSpec fieldSpec) {
        return MethodSpec.methodBuilder(ConvertorUtils.fieldNameToGetterName(fieldSpec.name))
                .addModifiers(Modifier.PUBLIC)
                .returns(fieldSpec.type)
                .addStatement("return " + fieldSpec.name)
                .build();
    }

    private MethodSpec makeSendMethod() {
        ClassName completionType = ClassName.get("com.beastpotato.potato.api.net.ApiRequest", "RequestCompletion");
        //TypeVariableName completionTypeVariableName = TypeVariableName.get("T",completionType);
        ClassName debugModelTypeVariableName = ClassName.get("com.beastpotato.potato.api.net", "DebugResponseModel");
        ParameterizedTypeName parameterizedCompletionParam = ParameterizedTypeName.get(completionType, debugModelTypeVariableName);

        ParameterSpec completionParam = ParameterSpec.builder(parameterizedCompletionParam, "completion")
                .addModifiers(Modifier.FINAL)
                .build();

        CodeBlock codeBlock = CodeBlock.builder()
                .addStatement("com.beastpotato.potato.api.net.ApiRequest<DebugResponseModel> request = new com.beastpotato.potato.api.net.ApiRequest<>(\"http://www.9gag.com\", DebugResponseModel.class, completion)")
                .addStatement("getRequestQueue().add(request)")
                .build();
        return MethodSpec.methodBuilder("send")
                .addParameter(completionParam)
                .addCode(codeBlock)
                .build();
    }
}
