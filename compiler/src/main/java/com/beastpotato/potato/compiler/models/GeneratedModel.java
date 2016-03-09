package com.beastpotato.potato.compiler.models;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by Oleksiy on 3/7/2016.
 */
public class GeneratedModel {
    private TypeElement classElement;
    private List<String> serializedNames = new ArrayList<>();
    private List<ModelFieldDef> fields = new ArrayList<>();

    public GeneratedModel(TypeElement classElement) {
        this.classElement = classElement;
    }


    public List<String> getFieldElements() {
        return serializedNames;
    }

    public TypeElement getClassElement() {
        return classElement;
    }

    public void setClassElement(TypeElement classElement) {
        this.classElement = classElement;
    }

    public boolean isMatching(GeneratedModel model) {
        if (model.getFieldElements().size() == serializedNames.size()) {
            return model.getFieldElements().containsAll(serializedNames);
        }
        return false;
    }

    public List<ModelFieldDef> getFields() {
        return fields;
    }

    public void addField(String fieldSerializaleName, String fieldName, TypeMirror fieldClassType) {
        this.serializedNames.add(fieldSerializaleName);
        fields.add(new ModelFieldDef(fieldSerializaleName, fieldName, fieldClassType));
    }
}
