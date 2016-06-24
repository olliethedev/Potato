package com.beastpotato.potato.compiler.json;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;

import org.jsonschema2pojo.GsonAnnotator;

import javax.tools.Diagnostic;

/**
 * JsonModelOrmLiteAnnotator
 * Description
 *
 * @author Oleksiy
 *         Created on 2016-06-24
 */

public class JsonModelOrmLiteAnnotator extends GsonAnnotator {
    private ProcessorLogger logger;

    public JsonModelOrmLiteAnnotator(ProcessorLogger messager) {
        this.logger = messager;
    }

    @Override
    public void additionalPropertiesField(JFieldVar field, JDefinedClass clazz, String propertyName) {
        super.additionalPropertiesField(field, clazz, propertyName);
    }

    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        super.propertyField(field, clazz, propertyName, propertyNode);
        logger.log(this, Diagnostic.Kind.WARNING, field.type().name());
        if (isFieldSimpleObject(field)) {
            field.annotate(DatabaseField.class).param("columnName", propertyName);
        } else if (field.name().startsWith("List<")) {
            field.annotate(ForeignCollectionField.class).param("eager", true);
        } else {
            field.annotate(DatabaseField.class).param("foreign", true);
        }
    }

    private boolean isFieldSimpleObject(JFieldVar field) {
        return field.type().name().equals("String") ||
                field.type().name().equals("Long") ||
                field.type().name().equals("Double") ||
                field.type().name().equals("Integer") ||
                field.type().name().equals("Boolean");
    }
}
