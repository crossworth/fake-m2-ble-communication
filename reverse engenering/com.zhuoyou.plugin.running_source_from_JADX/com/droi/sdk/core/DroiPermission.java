package com.droi.sdk.core;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class DroiPermission {
    public static final int f2599a = 2;
    public static final int f2600b = 1;
    private static DroiPermission f2601c;
    private int f2602d;
    private String f2603e;
    private Map<String, Integer> f2604f = new HashMap();
    private Map<String, Integer> f2605g = new HashMap();

    private void m2557a(boolean z, String str, int i, boolean z2) {
        Integer num = z ? (Integer) this.f2604f.get(str) : (Integer) this.f2605g.get(str);
        int intValue = num == null ? 0 : num.intValue();
        intValue = z2 ? intValue | i : intValue & (i ^ -1);
        if (z) {
            this.f2604f.put(str, Integer.valueOf(intValue));
        } else {
            this.f2605g.put(str, Integer.valueOf(intValue));
        }
    }

    public static DroiPermission fromJson(JSONObject jSONObject) {
        int i = 0;
        try {
            JSONArray jSONArray;
            int i2;
            DroiPermission droiPermission = new DroiPermission();
            if (jSONObject.has("creator")) {
                droiPermission.f2603e = jSONObject.getString("creator");
            }
            if (jSONObject.has("pr")) {
                droiPermission.setPublicReadPermission(jSONObject.getBoolean("pr"));
            }
            if (jSONObject.has("pw")) {
                droiPermission.setPublicWritePermission(jSONObject.getBoolean("pw"));
            }
            if (jSONObject.has("ur")) {
                jSONArray = jSONObject.getJSONArray("ur");
                for (i2 = 0; i2 < jSONArray.length(); i2++) {
                    droiPermission.setUserReadPermission(jSONArray.getString(i2), true);
                }
            }
            if (jSONObject.has("uw")) {
                jSONArray = jSONObject.getJSONArray("uw");
                for (i2 = 0; i2 < jSONArray.length(); i2++) {
                    droiPermission.setUserWritePermission(jSONArray.getString(i2), true);
                }
            }
            if (jSONObject.has("gr")) {
                jSONArray = jSONObject.getJSONArray("gr");
                for (i2 = 0; i2 < jSONArray.length(); i2++) {
                    droiPermission.setGroupReadPermission(jSONArray.getString(i2), true);
                }
            }
            if (!jSONObject.has("gw")) {
                return droiPermission;
            }
            JSONArray jSONArray2 = jSONObject.getJSONArray("gw");
            while (i < jSONArray2.length()) {
                droiPermission.setGroupWritePermission(jSONArray2.getString(i), true);
                i++;
            }
            return droiPermission;
        } catch (Exception e) {
            return null;
        }
    }

    public static DroiPermission getDefaultPermission() {
        return f2601c;
    }

    public static void setDefaultPermission(DroiPermission droiPermission) {
        f2601c = droiPermission;
    }

    String m2558a() {
        return this.f2603e;
    }

    void m2559a(String str) {
        this.f2603e = str;
    }

    public void setGroupReadPermission(String str, boolean z) {
        m2557a(false, str, 2, z);
    }

    public void setGroupWritePermission(String str, boolean z) {
        m2557a(false, str, 1, z);
    }

    public void setPublicReadPermission(boolean z) {
        if (z) {
            this.f2602d |= 2;
        } else {
            this.f2602d &= -3;
        }
    }

    public void setPublicWritePermission(boolean z) {
        if (z) {
            this.f2602d |= 1;
        } else {
            this.f2602d &= -2;
        }
    }

    public void setUserReadPermission(String str, boolean z) {
        m2557a(true, str, 2, z);
    }

    public void setUserWritePermission(String str, boolean z) {
        m2557a(true, str, 1, z);
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray;
            JSONArray jSONArray2;
            int intValue;
            if (this.f2603e != null) {
                jSONObject.put("creator", this.f2603e);
            }
            if (this.f2602d != 0) {
                if ((this.f2602d & 2) != 0) {
                    jSONObject.put("pr", true);
                }
                if ((this.f2602d & 1) != 0) {
                    jSONObject.put("pw", true);
                }
            }
            if (this.f2604f.size() > 0) {
                jSONArray = new JSONArray();
                jSONArray2 = new JSONArray();
                for (String str : this.f2604f.keySet()) {
                    intValue = ((Integer) this.f2604f.get(str)).intValue();
                    if ((intValue & 2) != 0) {
                        jSONArray.put(str);
                    }
                    if ((intValue & 1) != 0) {
                        jSONArray2.put(str);
                    }
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("ur", jSONArray);
                }
                if (jSONArray2.length() > 0) {
                    jSONObject.put("uw", jSONArray2);
                }
            }
            if (this.f2605g.size() > 0) {
                jSONArray = new JSONArray();
                jSONArray2 = new JSONArray();
                for (String str2 : this.f2605g.keySet()) {
                    intValue = ((Integer) this.f2605g.get(str2)).intValue();
                    if ((intValue & 2) != 0) {
                        jSONArray.put(str2);
                    }
                    if ((intValue & 1) != 0) {
                        jSONArray2.put(str2);
                    }
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("gr", jSONArray);
                }
                if (jSONArray2.length() > 0) {
                    jSONObject.put("gw", jSONArray2);
                }
            }
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
