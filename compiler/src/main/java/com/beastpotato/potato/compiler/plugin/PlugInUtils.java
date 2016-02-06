package com.beastpotato.potato.compiler.plugin;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class PlugInUtils {
    public static TypeElement findEnclosingTypeElement(Element e) {
        while (e != null && !(e instanceof TypeElement)) {
            e = e.getEnclosingElement();
        }
        return TypeElement.class.cast(e);
    }
}
