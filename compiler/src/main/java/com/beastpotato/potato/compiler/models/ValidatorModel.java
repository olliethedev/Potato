package com.beastpotato.potato.compiler.models;

import javax.lang.model.type.TypeMirror;

/**
 * Created by Oleksiy on 2/17/2016.
 */
public class ValidatorModel {
    private String filedName;
    private String methodName;
    private String packageName;
    private TypeMirror paramType;

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public TypeMirror getParamType() {
        return paramType;
    }

    public void setParamType(TypeMirror paramType) {
        this.paramType = paramType;
    }
}
