package com.chuchujie.core.json;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangjing on 2018/4/26.
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private final static String CHARSET = "UTF-8";

    /**
     * javabean转换为json字符串
     *
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj) {
        if (null == obj) {
            return "";
        }
        return JSON.toJSONString(obj);
    }

    /**
     * json字符串转换为javabean
     *
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return JSON.parseObject(jsonStr, objClass);
        } catch (Exception e) {
            Log.e(TAG, "json 2 bean. Exception:" + e);
            return null;
        }
    }

    /**
     * json字符串转换为javabean, 不捕获异常
     *
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> T json2BeanWithException(String jsonStr, Class<T> objClass) throws Exception {
        return JSON.parseObject(jsonStr, objClass);
    }

    /**
     * json转换为JsonObject
     *
     * @param jsonText
     * @return
     */
    public static JSONObject json2JsonObject(String jsonText) {
        try {
            return JSON.parseObject(jsonText);
        } catch (Exception e) {
            Log.e(TAG, "json to JsonObject exception:" + e.getMessage());
            return null;
        }
    }

    /**
     * json数组直接解析为List
     *
     * @param jsonText
     * @param objClass javabean的class
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String jsonText, Class<T> objClass) {
        try {
            return JSON.parseArray(jsonText, objClass);
        } catch (Exception e) {
            Log.e(TAG, "json to list exception:" + e.getMessage());
            return null;
        }
    }

    /**
     * 将一个Bean缓存到文件存储
     */
    public static void bean2File(Object obj, String filePath) {
        try {
            byte[] saveBytes = URLEncoder.encode((bean2Json(obj)), CHARSET).getBytes();
            FileUtils.save(saveBytes, filePath);
        } catch (IOException e) {
            Log.e(TAG, "bean2File Exception:" + e.getMessage());
        }
    }



    /**
     * 文件缓存转换到Bean
     */
    public static <T> T file2Bean(String filePath, Class<T> objClass) {
        try {
            return json2Bean(URLDecoder.decode(FileUtils.readFileContent(filePath),CHARSET),objClass);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 解析json单个关键字
     *
     * @param @param jsonStr 待解析Json字符串
     * @param @param key 待解析关键字
     * @param @param objClass 对象类型
     * @return T : 具体对象
     */
    public static <T> T jsonParseKey(String jsonStr, String key, Class<T> objClass) {
        JSONObject json = null;
        try {
            json = JSON.parseObject(jsonStr);
            if (json != null) {
                T result = (T) json.get(key);
                return result;
            }
        } catch (Exception e) {
            Log.i(TAG, "jsonParseKey:" + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * list转化为json数组
     *
     * @param list
     * @return
     */
    public static String arrayToJsonArray(List<String> list) {
        if (list == null || list.size() <= 0)
            return "";
        JSONArray jsonArray = new JSONArray();
        for (String item : list) {
            String s = JsonUtils.bean2Json(item);
            jsonArray.add(s);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 将map中的key-value映射为一个json数组,数组中只有一个对象
     * 如
     * <pre>
     * [{"a":"b","c":"d"}]
     * </pre>
     *
     * @param map
     * @return
     */
    public static String map2SingleJSONArray(Map<String, String> map) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = map2JSONObject(map);
        if (jsonObject.size() > 0) {
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 将map转化为JSONObject
     *
     * @param map
     * @return
     */
    public static JSONObject map2JSONObject(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        if (map != null) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (entry == null)
                    continue;
                try {
                    jsonObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    /**
     * json 转 map
     */
    public static Map<String, String> jsonToMap(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr) || jsonStr.length() == 0) return null;
        HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        Iterator it = entrySet.iterator();
        try {
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>) it.next();

                //            String key = String.valueOf(it.next());
                //            String value = (String) jsonObject.get(key);
                data.put(mapEntry.getKey(), (String) mapEntry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
