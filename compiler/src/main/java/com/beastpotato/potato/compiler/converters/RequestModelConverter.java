package com.beastpotato.potato.compiler.converters;

import com.beastpotato.potato.api.Constants;
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
                    .addField(makeMethodTypeFieldSpec(model))
                    .addMethod(makeConstructor(model))
                    .addMethod(makeSendMethod(model))
                    .superclass(ClassName.get(getElementUtils().getPackageOf(model.getTypeElement()).getQualifiedName().toString(), requestSuper.name));
            for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
                if (fieldDef.fieldType != RequestModel.FieldType.HttpMethod) {
                    FieldSpec fs = convertFieldDef(fieldDef);
                    classBuilder.addField(fs);
                    classBuilder.addMethod(makeGetter(fs));
                    classBuilder.addMethod(makeSetter(fs));
                }
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
                .initializer("" + getHttpMethod(model))
                .build();
    }

    private int getHttpMethod(RequestModel model) {
        int httpMethod = 0;//HTTP GET
        for (RequestModel.RequestModelFieldDef modelFieldDef : model.getFields()) {
            if (modelFieldDef.fieldType == RequestModel.FieldType.HttpMethod) {
                if (modelFieldDef.fieldSerializableName.equals(Constants.Http.GET.name())) {
                    httpMethod = 0;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.POST.name())) {
                    httpMethod = 1;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.PUT.name())) {
                    httpMethod = 2;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.DELETE.name())) {
                    httpMethod = 3;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.HEAD.name())) {
                    httpMethod = 4;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.OPTIONS.name())) {
                    httpMethod = 5;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.TRACE.name())) {
                    httpMethod = 6;
                } else if (modelFieldDef.fieldSerializableName.equals(Constants.Http.PATCH.name())) {
                    httpMethod = 7;
                }
            }
        }
        return httpMethod;
    }

    private MethodSpec makeConstructor(RequestModel model) {
        String relativeUrlValue = "";
        String relativeUrlFieldName = "";
        for (RequestModel.RequestModelFieldDef modelFieldDef : model.getFields()) {
            if (modelFieldDef.fieldType == RequestModel.FieldType.RelativeUrl) {
                relativeUrlValue = modelFieldDef.fieldSerializableName;
                relativeUrlFieldName = modelFieldDef.fieldName;
            }
        }
        return MethodSpec.constructorBuilder()
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

    private MethodSpec makeSendMethod(RequestModel model) {
        ClassName completionType = ClassName.get("com.beastpotato.potato.api.net.ApiRequest", "RequestCompletion");
        ClassName debugModelTypeVariableName = ClassName.get("com.beastpotato.potato.api.net", "DebugResponseModel");//todo parse json example
        ParameterizedTypeName parameterizedCompletionParam = ParameterizedTypeName.get(completionType, debugModelTypeVariableName);

        ParameterSpec completionParam = ParameterSpec.builder(parameterizedCompletionParam, "completion")
                .addModifiers(Modifier.FINAL)
                .build();

        CodeBlock.Builder fullUrlBlock = CodeBlock.builder()
                .addStatement("String fullUrl = this.baseUrl");
        for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
            if (fieldDef.fieldType == RequestModel.FieldType.RelativeUrl) {
                fullUrlBlock.addStatement("fullUrl += this." + fieldDef.fieldName);
            }
        }

        CodeBlock.Builder urlPathParamReplaceBlock = CodeBlock.builder();
        for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
            if (fieldDef.fieldType == RequestModel.FieldType.UrlPathParam) {
                fullUrlBlock.addStatement("fullUrl = fullUrl.replace(\"{" + fieldDef.fieldSerializableName + "}\"," + ConvertorUtils.fieldNameToGetterName(fieldDef.fieldName) + "())");
            }
        }

        CodeBlock.Builder urlParamsBlock = CodeBlock.builder();
        List<RequestModel.RequestModelFieldDef> urlParamsDefs = new ArrayList<>();
        for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
            if (fieldDef.fieldType == RequestModel.FieldType.UrlParam) {
                urlParamsDefs.add(fieldDef);
            }
        }
        for (int i = 0; i < urlParamsDefs.size(); i++) {
            RequestModel.RequestModelFieldDef fieldDef = urlParamsDefs.get(i);
            if (fieldDef.fieldType == RequestModel.FieldType.UrlParam) {
                if (i == 0) {
                    urlParamsBlock.addStatement("fullUrl += \"?\"");
                }
                urlParamsBlock.addStatement("fullUrl += \"" + fieldDef.fieldSerializableName + "=\"+ this." + fieldDef.fieldName);
                if (i != model.getFields().size() - 1) {
                    urlParamsBlock.addStatement("fullUrl += \"&\"");
                }
            }
        }

        CodeBlock.Builder headersBlock = CodeBlock.builder();
        for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
            if (fieldDef.fieldType == RequestModel.FieldType.HeaderParam) {
                headersBlock.addStatement("request.addHeader(\"" + fieldDef.fieldSerializableName + "\"," + ConvertorUtils.fieldNameToGetterName(fieldDef.fieldName) + "())");
            }
        }

        CodeBlock.Builder sendLogicBlock = CodeBlock.builder()
                .add(fullUrlBlock.build())
                .add(urlPathParamReplaceBlock.build())
                .add(urlParamsBlock.build())
                .addStatement("com.beastpotato.potato.api.net.ApiRequest<DebugResponseModel> request = new com.beastpotato.potato.api.net.ApiRequest<>(this.httpMethod, fullUrl, DebugResponseModel.class, completion)")
                .add(headersBlock.build())
                .addStatement("getRequestQueue().add(request)");

        return MethodSpec.methodBuilder("send")
                .addParameter(completionParam)
                .addCode(sendLogicBlock.build())
                .build();
    }
}
