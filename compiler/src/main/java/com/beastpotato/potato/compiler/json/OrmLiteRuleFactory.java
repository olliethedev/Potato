package com.beastpotato.potato.compiler.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;

import org.jsonschema2pojo.Annotator;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Schema;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.rules.ArrayRule;
import org.jsonschema2pojo.rules.ObjectRule;
import org.jsonschema2pojo.rules.Rule;
import org.jsonschema2pojo.rules.RuleFactory;
import org.jsonschema2pojo.util.Inflector;
import org.jsonschema2pojo.util.ParcelableHelper;

import java.util.Set;

/**
 * OrmLiteRuleFactory
 * Description
 *
 * @author Oleksiy
 *         Created on 2016-06-24
 */

public class OrmLiteRuleFactory extends RuleFactory {
    public OrmLiteRuleFactory(GenerationConfig generationConfig, Annotator annotator, SchemaStore schemaStore) {
        super(generationConfig, annotator, schemaStore);
    }

    @Override
    public Rule<JPackage, JType> getObjectRule() {
        return new OrmLiteObjectRule(this, new ParcelableHelper());
    }

    @Override
    public Rule<JPackage, JClass> getArrayRule() {
        return new OrmLiteArrayRule(this);
    }

    public static class OrmLiteArrayRule extends ArrayRule {

        private RuleFactory ruleFactory;

        protected OrmLiteArrayRule(RuleFactory ruleFactory) {
            super(ruleFactory);
            this.ruleFactory = ruleFactory;
        }

        @Override
        public JClass apply(String nodeName, JsonNode node, JPackage jpackage, Schema schema) {
            boolean uniqueItems = node.has("uniqueItems") && node.get("uniqueItems").asBoolean();
            boolean rootSchemaIsArray = !schema.isGenerated();

            JType itemType;
            if (node.has("items")) {
                itemType = ruleFactory.getSchemaRule().apply(makeSingular(nodeName), node.get("items"), jpackage, schema);
            } else {
                itemType = jpackage.owner().ref(Object.class);
            }

            JClass arrayType;
            if (uniqueItems) {
                arrayType = jpackage.owner().ref(Set.class).narrow(itemType);
            } else {
                arrayType = jpackage.owner().ref(ForeignCollection.class).narrow(itemType);
            }

            if (rootSchemaIsArray) {
                schema.setJavaType(arrayType);
            }

            return arrayType;
        }

        private String makeSingular(String nodeName) {
            return Inflector.getInstance().singularize(nodeName);
        }
    }

    public static class OrmLiteObjectRule extends ObjectRule {

        protected OrmLiteObjectRule(RuleFactory ruleFactory, ParcelableHelper parcelableHelper) {
            super(ruleFactory, parcelableHelper);
        }

        @Override
        public JType apply(String nodeName, JsonNode node, JPackage _package, Schema schema) {
            JDefinedClass jclass = (JDefinedClass) super.apply(nodeName, node, _package, schema);
            applyDatabaseTableAnnotation(jclass);
            return jclass;
        }

        private void applyDatabaseTableAnnotation(JDefinedClass jclass) {
            jclass.annotate(DatabaseTable.class);
        }

    }
}
