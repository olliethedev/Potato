package com.beastpotato.potato.compiler.converters;

import com.beastpotato.potato.compiler.models.RequestModel;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class RequestModelConverter extends BaseModelConverter<TypeSpec, RequestModel> {
    @Override
    public TypeSpec convert(RequestModel model) throws ConversionException {
        try {
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(model.getModelName() + "Request")
                    .addModifiers(Modifier.PUBLIC);
            for (RequestModel.RequestModelFieldDef fieldDef : model.getFields()) {
                classBuilder.addField(convertFieldDef(fieldDef));
            }
            return classBuilder.build();
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert %1s to TypeSpec", model.getClass().getSimpleName()));
        }
    }

    private FieldSpec convertFieldDef(RequestModel.RequestModelFieldDef fieldDef) {
        return FieldSpec.builder(TypeName.get(fieldDef.fieldClassType), fieldDef.fieldName).build();
    }
}
