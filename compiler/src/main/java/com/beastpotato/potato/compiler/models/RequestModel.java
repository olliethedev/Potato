package com.beastpotato.potato.compiler.models;

import com.beastpotato.potato.api.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class RequestModel {
    private static String packageName;
    private TypeElement typeElement;
    private List<ModelFieldDef> urlPathParamFields, urlParamFields, headerParamFields;
    private ModelFieldDef bodyField;
    private String relativeUrl;
    private Constants.Http method;
    private String exampleJson;
    private String modelName;
    private String responseClassName;
    private String responsePackageName;

    public RequestModel(TypeElement typeElement) {
        this.modelName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        urlPathParamFields = new ArrayList<>();
        urlParamFields = new ArrayList<>();
        headerParamFields = new ArrayList<>();
    }

    public void addField(ModelFieldDef.FieldType fieldType, String fieldSerializaleName, String fieldName, TypeMirror fieldClassType) {
        switch (fieldType) {
            case UrlPathParam:
                urlPathParamFields.add(new ModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
            case UrlParam:
                urlParamFields.add(new ModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
            case HeaderParam:
                headerParamFields.add(new ModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
            case Body:
                bodyField = new ModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType);
                break;
        }
    }

    public List<ModelFieldDef> getAllFields() {
        List<ModelFieldDef> out = new ArrayList<>();
        out.addAll(urlPathParamFields);
        out.addAll(urlParamFields);
        out.addAll(headerParamFields);
        if (bodyField != null)
            out.add(bodyField);
        return out;
    }

    public List<ModelFieldDef> getUrlPathParamFields() {
        return urlPathParamFields;
    }

    public List<ModelFieldDef> getUrlParamFields() {
        return urlParamFields;
    }

    public List<ModelFieldDef> getHeaderParamFields() {
        return headerParamFields;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public void setResponseClassName(String responseClassName) {
        this.responseClassName = responseClassName;
    }

    public String getResponsePackageName() {
        return responsePackageName;
    }

    public void setResponsePackageName(String responsePackageName) {
        this.responsePackageName = responsePackageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        if (RequestModel.packageName == null)
            RequestModel.packageName = packageName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public Constants.Http getMethod() {
        return method;
    }

    public void setMethod(Constants.Http method) {
        this.method = method;
    }

    public String getExampleJson() {
        return exampleJson;
    }

    public void setExampleJson(String exampleJson) {
        this.exampleJson = exampleJson;
    }

    public ModelFieldDef getBodyField() {
        return bodyField;
    }
}
