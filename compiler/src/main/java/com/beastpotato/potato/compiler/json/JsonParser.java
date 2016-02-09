package com.beastpotato.potato.compiler.json;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.writer.SingleStreamCodeWriter;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Oleksiy on 2/8/2016.
 */
public class JsonParser {
    public static String parseJsonToClassSource(String packageName, String className, String jsonStr, ProcessorLogger messager) throws JsonParserException {
        JCodeModel codeModel = new JCodeModel();
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public boolean isIncludeAccessors() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(), new SchemaStore()), new SchemaGenerator());
        try {
            mapper.generate(codeModel, className, packageName, jsonStr);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            CodeWriter codeWriter = new SingleStreamCodeWriter(baos);
            codeModel.build(codeWriter);
            return baos.toString();
        } catch (IOException e) {
            throw new JsonParserException("JsonParser failed:" + e.getMessage(), e.getCause());
        }

    }

    public static JCodeModel parseJsonToModel(String packageName, String className, String jsonStr, ProcessorLogger messager) throws JsonParserException {
        JCodeModel codeModel = new JCodeModel();
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public boolean isIncludeAccessors() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(), new SchemaStore()), new SchemaGenerator());
        try {
            mapper.generate(codeModel, className, packageName, jsonStr);
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
