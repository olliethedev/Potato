package com.beastpotato.potato.compiler.models;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Oleksiy on 3/8/2016.
 */
public class ModelFieldDef {
    public FieldType fieldType;
    public String fieldSerializableName;
    public String fieldName;
    public TypeMirror fieldClassType;

    public ModelFieldDef(FieldType fieldType, String fieldSerializableName, String fieldName, TypeMirror fieldClassType) {
        this.fieldType = fieldType;
        this.fieldSerializableName = fieldSerializableName;
        this.fieldName = fieldName;
        this.fieldClassType = fieldClassType;
    }

    public ModelFieldDef(String fieldSerializableName, String fieldName, TypeMirror fieldClassType) {
        this.fieldSerializableName = fieldSerializableName;
        this.fieldName = fieldName;
        this.fieldClassType = fieldClassType;
    }

    public enum FieldType {UrlPathParam, UrlParam, HeaderParam, Body}
}
