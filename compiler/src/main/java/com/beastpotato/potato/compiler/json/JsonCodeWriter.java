package com.beastpotato.potato.compiler.json;

import com.beastpotato.potato.compiler.plugin.ProcessorLogger;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by Oleksiy on 2/8/2016.
 */
public class JsonCodeWriter extends CodeWriter {
    private Filer filer;
    private ProcessorLogger logger;

    public JsonCodeWriter(Filer mFiler, ProcessorLogger logger) {
        this.filer = mFiler;
        this.logger = logger;
    }

    @Override
    public Writer openSource(JPackage jPackage, String className) throws IOException {
        logger.log(this, Diagnostic.Kind.NOTE, "writing package " + jPackage.name() + " .... class name " + className);
        String packageName = jPackage.name();
        if (className.endsWith(".java")) {
            className = className.substring(0, className.length() - ".java".length());
        }
        String fullQualifiedName = packageName.isEmpty() ? className : (jPackage.name() + "." + className);
        JavaFileObject javaFileObject = filer.createSourceFile(fullQualifiedName);

        if (javaFileObject == null) {
            throw new IOException("Unable to create JavaFileObject");
        }
        OutputStream out = javaFileObject.openOutputStream();
        return new OutputStreamWriter(out);
    }

    @Override
    public OutputStream openBinary(JPackage jPackage, String className) throws IOException {
        String fullQualifiedName = jPackage.name().isEmpty() ? className : (jPackage.name() + "." + className);
        JavaFileObject javaFileObject = filer.createSourceFile(fullQualifiedName);

        if (javaFileObject == null) {
            throw new IOException("Unable to create JavaFileObject");
        }
        OutputStream out = javaFileObject.openOutputStream();
        return out;
    }

    @Override
    public void close() throws IOException {
    }

}
