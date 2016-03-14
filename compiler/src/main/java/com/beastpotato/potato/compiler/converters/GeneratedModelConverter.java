package com.beastpotato.potato.compiler.converters;

import com.beastpotato.potato.compiler.models.GeneratedModel;
import com.beastpotato.potato.compiler.models.ModelFieldDef;
import com.beastpotato.potato.compiler.plugin.Pair;
import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.google.gson.annotations.SerializedName;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 3/10/2016.
 */
public class GeneratedModelConverter extends BaseModelConverter<Pair<TypeSpec, GeneratedModel>, List<GeneratedModel>> {
    private List<List<GeneratedModel>> groups;

    public GeneratedModelConverter(ProcessorLogger logger, Types typeUtils, Elements elementUtils, List<List<GeneratedModel>> groups) {
        super(logger, typeUtils, elementUtils);
        this.groups = groups;
    }

    @Override
    public List<Pair<TypeSpec, GeneratedModel>> convert(List<GeneratedModel> models) throws ConversionException {
        getLogger().log(this, Diagnostic.Kind.NOTE, "Converting Endpoint annotation data model to java object...");
        try {
            List<Pair<TypeSpec, GeneratedModel>> typeSpecs = new ArrayList<>();
            for (GeneratedModel model : models) {
                if (!belongsToGroup(model)) {
                    typeSpecs.add(new Pair<>(getClass(model), model));
                }
            }
            for (List<GeneratedModel> group : groups) {
                typeSpecs.add(new Pair<>(getMergedClass(group), group.get(0)));
            }
            return typeSpecs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConversionException(String.format("Could not convert %1s to TypeSpec because %2s", models.getClass().getSimpleName(), e.getMessage()));
        }
    }

    private TypeSpec getMergedClass(List<GeneratedModel> group) {
        String name = "";
        for (GeneratedModel model : group) {
            name += model.getClassElement().getSimpleName().toString();
        }
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(name + "Merged")//todo remove test and change original class name
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(getClassAnnotationSpec());
        for (ModelFieldDef fieldDef : group.get(0).getFields()) {
            classBuilder.addField(getFieldSpec(fieldDef));
        }
        return classBuilder.build();
    }

    private TypeSpec getClass(GeneratedModel model) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(model.getClassElement().getSimpleName().toString() + "Test")//todo remove test and change original class name
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(getClassAnnotationSpec());
        for (ModelFieldDef fieldDef : model.getFields()) {
            classBuilder.addField(getFieldSpec(fieldDef));
        }
        return classBuilder.build();
    }

    private AnnotationSpec getClassAnnotationSpec() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "\"com.beastpotato.potato.compiler\"")
                .build();
    }

    private FieldSpec getFieldSpec(ModelFieldDef fieldDef) {
        return FieldSpec.builder(TypeName.get(fieldDef.fieldClassType), fieldDef.fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(getFieldAnnotationSpec(fieldDef))
                .build();
    }

    private AnnotationSpec getFieldAnnotationSpec(ModelFieldDef fieldDef) {
        return AnnotationSpec.builder(SerializedName.class)
                .addMember("value", "\"" + fieldDef.fieldSerializableName + "\"")
                .build();
    }

    private boolean belongsToGroup(GeneratedModel model) {
        for (List<GeneratedModel> group : groups) {
            for (GeneratedModel gm : group) {
                if (gm.equals(model)) {
                    return true;
                }
            }
        }
        return false;
    }
}
