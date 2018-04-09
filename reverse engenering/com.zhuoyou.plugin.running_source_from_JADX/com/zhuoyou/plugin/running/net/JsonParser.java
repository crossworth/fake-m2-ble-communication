package com.zhuoyou.plugin.running.net;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static Serializable parse(int code, String result) {
        return result;
    }

    public static JSONObject getBodyJson(String jsonStr) {
        try {
            return new JSONObject(new JSONObject(jsonStr).optString("body"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
