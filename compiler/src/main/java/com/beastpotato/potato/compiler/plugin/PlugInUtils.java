package com.beastpotato.potato.compiler.plugin;

import com.beastpotato.potato.compiler.models.GeneratedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<List<GeneratedModel>> findMatchingGroups(List<GeneratedModel> models) {
        List<List<GeneratedModel>> outList = new ArrayList<>();
        Map<List<String>, List<GeneratedModel>> map = new HashMap<>();
        for (GeneratedModel model : models) {
            boolean isAdded = false;
            for (List<String> key : map.keySet()) {
                if (key.containsAll(model.getFieldElements()) && key.size() == model.getFieldElements().size()) {
                    map.get(key).add(model);
                    isAdded = true;
                }
            }
            if (!isAdded) {
                map.put(model.getFieldElements(), new ArrayList<GeneratedModel>());
            }
        }
        for (List<String> key : map.keySet()) {
            List<GeneratedModel> list = map.get(key);
            if (list.size() > 1) {
                outList.add(list);
            }
        }
        return outList;
    }
}
