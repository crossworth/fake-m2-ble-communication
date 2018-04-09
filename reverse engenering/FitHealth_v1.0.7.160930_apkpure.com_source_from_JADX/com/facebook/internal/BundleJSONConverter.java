package com.facebook.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleJSONConverter {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    public interface Setter {
        void setOnBundle(Bundle bundle, String str, Object obj) throws JSONException;

        void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException;
    }

    static class C16451 implements Setter {
        C16451() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putBoolean(key, ((Boolean) value).booleanValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    static class C16462 implements Setter {
        C16462() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putInt(key, ((Integer) value).intValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    static class C16473 implements Setter {
        C16473() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putLong(key, ((Long) value).longValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    static class C16484 implements Setter {
        C16484() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putDouble(key, ((Double) value).doubleValue());
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    static class C16495 implements Setter {
        C16495() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putString(key, (String) value);
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            json.put(key, value);
        }
    }

    static class C16506 implements Setter {
        C16506() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("Unexpected type from JSON");
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            JSONArray jsonArray = new JSONArray();
            for (String stringValue : (String[]) value) {
                jsonArray.put(stringValue);
            }
            json.put(key, jsonArray);
        }
    }

    static class C16517 implements Setter {
        C16517() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            JSONArray jsonArray = (JSONArray) value;
            ArrayList<String> stringArrayList = new ArrayList();
            if (jsonArray.length() == 0) {
                bundle.putStringArrayList(key, stringArrayList);
                return;
            }
            int i = 0;
            while (i < jsonArray.length()) {
                Object current = jsonArray.get(i);
                if (current instanceof String) {
                    stringArrayList.add((String) current);
                    i++;
                } else {
                    throw new IllegalArgumentException("Unexpected type in an array: " + current.getClass());
                }
            }
            bundle.putStringArrayList(key, stringArrayList);
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
        }
    }

    static {
        SETTERS.put(Boolean.class, new C16451());
        SETTERS.put(Integer.class, new C16462());
        SETTERS.put(Long.class, new C16473());
        SETTERS.put(Double.class, new C16484());
        SETTERS.put(String.class, new C16495());
        SETTERS.put(String[].class, new C16506());
        SETTERS.put(JSONArray.class, new C16517());
    }

    public static JSONObject convertToJSON(Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject();
        for (String key : bundle.keySet()) {
            List<String> value = bundle.get(key);
            if (value != null) {
                if (value instanceof List) {
                    JSONArray jsonArray = new JSONArray();
                    for (String stringValue : value) {
                        jsonArray.put(stringValue);
                    }
                    json.put(key, jsonArray);
                } else if (value instanceof Bundle) {
                    json.put(key, convertToJSON((Bundle) value));
                } else {
                    Setter setter = (Setter) SETTERS.get(value.getClass());
                    if (setter == null) {
                        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
                    }
                    setter.setOnJSON(json, key, value);
                }
            }
        }
        return json;
    }

    public static Bundle convertToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> jsonIterator = jsonObject.keys();
        while (jsonIterator.hasNext()) {
            String key = (String) jsonIterator.next();
            Object value = jsonObject.get(key);
            if (!(value == null || value == JSONObject.NULL)) {
                if (value instanceof JSONObject) {
                    bundle.putBundle(key, convertToBundle((JSONObject) value));
                } else {
                    Setter setter = (Setter) SETTERS.get(value.getClass());
                    if (setter == null) {
                        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
                    }
                    setter.setOnBundle(bundle, key, value);
                }
            }
        }
        return bundle;
    }
}
