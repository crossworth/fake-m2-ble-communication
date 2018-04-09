package com.droi.sdk.selfupdate;

import java.io.Serializable;
import org.json.JSONObject;

public class DroiInappUpdateResponse implements Serializable {
    private int f3372a;
    private String f3373b;
    private String f3374c;
    private String f3375d;
    private int f3376e;
    private int f3377f;
    private String f3378g;
    private String f3379h;
    private String f3380i;
    private String f3381j;

    protected static DroiInappUpdateResponse m3181a(JSONObject jSONObject) {
        DroiInappUpdateResponse droiInappUpdateResponse = new DroiInappUpdateResponse();
        try {
            droiInappUpdateResponse.f3372a = jSONObject.getInt("errorCode");
            if (droiInappUpdateResponse.f3372a != 0) {
                return droiInappUpdateResponse;
            }
            droiInappUpdateResponse.f3376e = jSONObject.getInt("policy");
            if (droiInappUpdateResponse.f3376e != 1) {
                return droiInappUpdateResponse;
            }
            droiInappUpdateResponse.f3373b = jSONObject.getString("taskId");
            droiInappUpdateResponse.f3374c = "title";
            droiInappUpdateResponse.f3375d = jSONObject.getString("content");
            droiInappUpdateResponse.f3377f = jSONObject.getInt("appVer");
            droiInappUpdateResponse.f3378g = jSONObject.optString("appVerName");
            droiInappUpdateResponse.f3381j = jSONObject.getString("fileUrl");
            droiInappUpdateResponse.f3379h = jSONObject.getString("md5");
            droiInappUpdateResponse.f3380i = jSONObject.getString("totalSize");
            return droiInappUpdateResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected int m3182a() {
        return this.f3372a;
    }

    public String getContent() {
        return this.f3375d;
    }

    protected boolean m3183b() {
        if (this.f3376e == 0) {
            return false;
        }
        return true;
    }

    public int getFileVersion() {
        return this.f3377f;
    }

    public String getFileVersionName() {
        return this.f3378g;
    }

    public String getFileMd5() {
        return this.f3379h;
    }

    public String getFileSize() {
        return this.f3380i;
    }

    public String getFileUrl() {
        return this.f3381j;
    }
}
