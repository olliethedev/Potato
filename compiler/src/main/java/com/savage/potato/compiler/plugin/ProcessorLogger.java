package com.savage.potato.compiler.plugin;

import javax.tools.Diagnostic;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public interface ProcessorLogger {
    void log(Object caller, Diagnostic.Kind kind, String msg);
}
