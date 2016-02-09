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
                    .addField(String.class, "baseUrl", Modifier.PRIVATE)
                    .addField(makeRelativeUrlFieldSpec(model))
                    .addField(makeMethodTypeFieldSpec(model))
                    .addMethod(makeConstructor(model))
                    .addMethod(makeSendMethod(model))
                    .addMethod(makeGetFullUrlMethod(model))
                    .superclass(ClassName.get(getElementUtils().getPackageOf(model.getTypeElement()).getQualifiedName().toString(), requestSuper.name));
            for (RequestModel.RequestModelFieldDef fieldDef : model.getAllFields()) {
                FieldSpec fs = convertFieldDef(fieldDef);
                classBuilder.addField(fs);
                classBuilder.addMethod(makeGetter(fs));
                classBuilder.addMethod(makeSetter(fs));
            }
            TypeSpec request = classBuilder.build();
            typeSpecs.add(request);
            return typeSpecs;
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert %1s to TypeSpec", model.getClass().getSimpleName()));
        }
    }

    private FieldSpec makeMethodTypeFieldSpec(RequestModel model) {
        return FieldSpec.builder(Integer.class, "httpMethod", Modifier.PRIVATE, Modifier.STATIC)
                .initializer("" + model.getMethod().getNumValue())
                .build();
    }

    private FieldSpec makeRelativeUrlFieldSpec(RequestModel model) {
        return FieldSpec.builder(String.class, "relativeUrl", Modifier.PRIVATE, Modifier.STATIC)
                .initializer("\"" + model.getRelativeUrl() + "\"")
                .build();
    }

    private MethodSpec makeConstructor(RequestModel model) {
        String relativeUrlValue = model.getRelativeUrl();
        String relativeUrlFieldName = "relativeUrl";
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "baseUrl")
                .addParameter(contextClass, "context")
                .addStatement("super(context)")
                .addStatement("this.baseUrl = baseUrl")
                .addStatement("this." + relativeUrlFieldName + "=\"" + relativeUrlValue + "\"")
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

    private MethodSpec makeSetter(FieldSpec fieldSpec) {
        return MethodSpec.methodBuilder(ConvertorUtils.fieldNameToSetterName(fieldSpec.name))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(fieldSpec.type, fieldSpec.name)
                .addStatement("this." + fieldSpec.name + "=" + fieldSpec.name)
                .build();
    }

    private MethodSpec makeGetFullUrlMethod(RequestModel model) {
        CodeBlock.Builder fullUrlBlock = CodeBlock.builder()
                .addStatement("String fullUrl = this.baseUrl");
        fullUrlBlock.addStatement("fullUrl += this." + "relativeUrl");

        CodeBlock.Builder urlPathParamReplaceBlock = CodeBlock.builder();
        for (RequestModel.RequestModelFieldDef fieldDef : model.getUrlPathParamFields()) {
            fullUrlBlock.addStatement("fullUrl = fullUrl.replace(\"{" + fieldDef.fieldSerializableName + "}\",this." + fieldDef.fieldName + ")");
        }

        CodeBlock.Builder urlParamsBlock = CodeBlock.builder();
        int i = 0;
        for (RequestModel.RequestModelFieldDef fieldDef : model.getUrlParamFields()) {
            if (i == 0) {
                urlParamsBlock.addStatement("fullUrl += \"?\"");
            }
            urlParamsBlock.addStatement("fullUrl += \"" + fieldDef.fieldSerializableName + "=\"+ this." + fieldDef.fieldName);
            if (i != model.getUrlParamFields().size() - 1) {
                urlParamsBlock.addStatement("fullUrl += \"&\"");
            }
            i++;
        }

        CodeBlock.Builder sendLogicBlock = CodeBlock.builder()
                .add(fullUrlBlock.build())
                .add(urlPathParamReplaceBlock.build())
                .add(urlParamsBlock.build())
                .addStatement("return fullUrl");

        return MethodSpec.methodBuilder("getFullUrl")
                .addCode(sendLogicBlock.build())
                .returns(String.class)
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    private MethodSpec makeSendMethod(RequestModel model) {
        ClassName completionType = ClassName.get("com.beastpotato.potato.api.net.ApiRequest", "RequestCompletion");
        ClassName responseTypeVariableName = ClassName.get(model.getPackageName(), model.getResponseClassName());
        ParameterizedTypeName parameterizedCompletionParam = ParameterizedTypeName.get(completionType, responseTypeVariableName);

        ParameterSpec completionParam = ParameterSpec.builder(parameterizedCompletionParam, "completion")
                .addModifiers(Modifier.FINAL)
                .build();

        CodeBlock.Builder headersBlock = CodeBlock.builder();
        for (RequestModel.RequestModelFieldDef fieldDef : model.getHeaderParamFields()) {
            headersBlock.addStatement("request.addHeader(\"" + fieldDef.fieldSerializableName + "\",this." + fieldDef.fieldName + ")");
        }

        CodeBlock.Builder sendLogicBlock = CodeBlock.builder()
                .addStatement("com.beastpotato.potato.api.net.ApiRequest<" + model.getResponseClassName() + "> request = new com.beastpotato.potato.api.net.ApiRequest<>(this.httpMethod, getFullUrl(), " + model.getResponseClassName() + ".class, completion)")
                .add(headersBlock.build())
                .addStatement("getRequestQueue().add(request)");

        return MethodSpec.methodBuilder("send")
                .addParameter(completionParam)
                .addCode(sendLogicBlock.build())
                .addModifiers(Modifier.PUBLIC)
                .build();
    }
}
