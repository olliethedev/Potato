package com.beastpotato.potato.compiler.json;

import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

/**
 * Created by Oleksiy on 2/8/2016.
 */
public class JsonCodeWriter extends CodeWriter {
    private Filer filer;

    public JsonCodeWriter(Filer mFiler) {
        this.filer = mFiler;
    }

    @Override
    public Writer openSource(JPackage jPackage, String className) throws IOException {
        String fullQualifiedName = jPackage.name().isEmpty() ? className : (jPackage.name() + "." + className);
        JavaFileObject javaFileObject = null;
        javaFileObject = filer.createSourceFile(fullQualifiedName);

        if (javaFileObject == null) {
            throw new IOException("Unable to create JavaFileObject");
        }
        OutputStream out = javaFileObject.openOutputStream();
        return new OutputStreamWriter(out);
    }

    @Override
    public OutputStream openBinary(JPackage jPackage, String className) throws IOException {
        String fullQualifiedName = jPackage.name().isEmpty() ? className : (jPackage.name() + "." + className);
        JavaFileObject javaFileObject = null;
        javaFileObject = filer.createSourceFile(fullQualifiedName);

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
