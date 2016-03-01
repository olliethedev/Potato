package com.beastpotato.potato.compiler.json;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.sun.codemodel.JCodeModel;

import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.GsonAnnotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/8/2016.
 */
public class JsonParser {
    private static GenerationConfig config = new DefaultGenerationConfig() {
        @Override
        public boolean isGenerateBuilders() {
            return false;
        }

        @Override
        public boolean isIncludeAccessors() {
            return false;
        }

        @Override
        public AnnotationStyle getAnnotationStyle() {
            return AnnotationStyle.NONE;
        }

        @Override
        public SourceType getSourceType() {
            return SourceType.JSON;
        }


        @Override
        public boolean isIncludeAdditionalProperties() {
            return false;
        }

        @Override
        public boolean isUseDoubleNumbers() {
            return true;
        }

        @Override
        public boolean isRemoveOldOutput() {
            return true;
        }

        @Override
        public boolean isIncludeHashcodeAndEquals() {
            return false;
        }

        @Override
        public boolean isIncludeToString() {
            return false;
        }

        @Override
        public boolean isUseJodaDates() {
            return false;
        }

        @Override
        public boolean isUseJodaLocalDates() {
            return false;
        }

        @Override
        public boolean isUseJodaLocalTimes() {
            return false;
        }

        @Override
        public boolean isParcelable() {
            return true;
        }

        @Override
        public boolean isInitializeCollections() {
            return false;
        }

        @Override
        public boolean isUsePrimitives() {
            return false;
        }

        @Override
        public boolean isUseCommonsLang3() {
            return false;
        }

        @Override
        public boolean isIncludeDynamicAccessors() {
            return false;
        }

        @Override
        public boolean isUseLongIntegers() {
            return true;
        }


    };

    public static JCodeModel parseJsonToModel(String packageName, String className, String jsonStr, ProcessorLogger messager) throws JsonParserException {
        messager.log(JsonParser.class, Diagnostic.Kind.NOTE, "parsing JSON to JCodeModel...");
        JCodeModel codeModel = new JCodeModel();
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new GsonAnnotator(), new SchemaStore()), new SchemaGenerator());
        try {
            mapper.generate(codeModel, className, packageName, jsonStr);
            messager.log(JsonParser.class, Diagnostic.Kind.NOTE, "parsing JSON done.");
            return codeModel;
        } catch (Exception e) {
            throw new JsonParserException("JsonParser failed:" + e.getMessage(), e.getCause());
        }
    }

    public static class JsonParserException extends Exception {
        public JsonParserException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
