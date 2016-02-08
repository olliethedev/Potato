package com.beastpotato.potato.compiler.models;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class RequestModel {
    private TypeElement typeElement;
    private List<RequestModelFieldDef> fields;
    private String modelName;

    public RequestModel(TypeElement typeElement) {
        this.modelName = typeElement.getSimpleName().toString();
        this.typeElement = typeElement;
        fields = new ArrayList<>();
    }

    public void addField(FieldType fieldType, String fieldSerializaleName, String fieldName, TypeMirror fieldClassType) {
        fields.add(new RequestModelFieldDef(fieldType, fieldSerializaleName, fieldName, fieldClassType));
    }

    public List<RequestModelFieldDef> getFields() {
        return fields;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getModelName() {
        return modelName;
    }

    public enum FieldType {Context, BaseUrl, RelativeUrl, HttpMethod, UrlPathParam, UrlParam, HeaderParam, Body}

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
