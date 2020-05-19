package com.chuchujie.core.json.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by wangjing on 2018/8/1.
 */
public class GsonUtils {

    public static String bean2Json(Object object) {
        return bean2Json(object, false);
    }

    public static String bean2Json(Object object, boolean excludeFields) {
        Gson gson;
        if (excludeFields) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        } else {
            gson = new GsonBuilder().create();
        }
        return gson.toJson(object);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> classOfT) {
        return json2Bean(jsonStr, classOfT, false);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> classOfT, boolean excludeFields) {
        Gson gson;
        if (excludeFields) {
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        } else {
            gson = new GsonBuilder().create();
        }
        return gson.fromJson(jsonStr, classOfT);
    }

}
