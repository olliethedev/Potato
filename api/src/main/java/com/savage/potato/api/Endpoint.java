package com.savage.potato.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Endpoint {
    Constants.Http httpMethod();

    String relativeUrl();

    String jsonExample();
}
