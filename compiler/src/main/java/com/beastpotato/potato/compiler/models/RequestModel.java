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
    private TypeElement typeElement;
    private List<RequestModelFieldDef> urlPathParamFields, urlParamFields, headerParamFields;
    private String relativeUrl;
    private Constants.Http method;
    private String exampleJson;
    private String modelName;
    private String responseClassName;
    private String packageName;

    public RequestModel(TypeElement typeElement) {
        this.modelName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        urlPathParamFields = new ArrayList<>();
        urlParamFields = new ArrayList<>();
        headerParamFields = new ArrayList<>();
    }

    public void addField(FieldType fieldType, String fieldSerializaleName, String fieldName, TypeMirror fieldClassType) {
        switch (fieldType) {
            case UrlPathParam:
                urlPathParamFields.add(new RequestModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
            case UrlParam:
                urlParamFields.add(new RequestModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
            case HeaderParam:
                headerParamFields.add(new RequestModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
                break;
        }
    }

    public List<RequestModelFieldDef> getAllFields() {
        List<RequestModelFieldDef> out = new ArrayList<>();
        out.addAll(urlPathParamFields);
        out.addAll(urlParamFields);
        out.addAll(headerParamFields);
        return out;
    }

    public List<RequestModelFieldDef> getUrlPathParamFields() {
        return urlPathParamFields;
    }

    public List<RequestModelFieldDef> getUrlParamFields() {
        return urlParamFields;
    }

    public List<RequestModelFieldDef> getHeaderParamFields() {
        return headerParamFields;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public void setResponseClassName(String responseClassName) {
        this.responseClassName = responseClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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

    public enum FieldType {UrlPathParam, UrlParam, HeaderParam}

    public class RequestModelFieldDef {
        public FieldType fieldType;
        public String fieldSerializableName;
        public String fieldName;
        public TypeMirror fieldClassType;

        public RequestModelFieldDef(FieldType fieldType, String fieldSerializableName, String fieldName, TypeMirror fieldClassType) {
            this.fieldType = fieldType;
            this.fieldSerializableName = fieldSerializableName;
            this.fieldName = fieldName;
            this.fieldClassType = fieldClassType;
        }
    }
}
