package com.droi.sdk.selfupdate;

import android.content.Context;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1047b;
import com.droi.sdk.selfupdate.util.C1050d;
import java.io.Serializable;
import org.json.JSONObject;

public class DroiUpdateResponse implements Serializable {
    private int f3395a;
    private String f3396b;
    private String f3397c;
    private String f3398d;
    private int f3399e;
    private String f3400f;
    private String f3401g;
    private String f3402h;
    private String f3403i;
    private String f3404j;
    private int f3405k;
    private String f3406l;
    private String f3407m;
    private String f3408n;

    protected static DroiUpdateResponse m3192a(JSONObject jSONObject) {
        DroiUpdateResponse droiUpdateResponse = new DroiUpdateResponse();
        try {
            droiUpdateResponse.f3395a = jSONObject.getInt("errorCode");
            if (droiUpdateResponse.f3395a != 0) {
                return droiUpdateResponse;
            }
            droiUpdateResponse.f3399e = jSONObject.getInt("policy");
            if (droiUpdateResponse.f3399e == 0) {
                return droiUpdateResponse;
            }
            droiUpdateResponse.f3396b = jSONObject.getString("taskId");
            droiUpdateResponse.f3397c = "title";
            droiUpdateResponse.f3398d = jSONObject.getString("content");
            droiUpdateResponse.f3400f = jSONObject.getString("appVerName");
            droiUpdateResponse.f3401g = jSONObject.getString("appVer");
            droiUpdateResponse.f3404j = jSONObject.getString("fileUrl");
            droiUpdateResponse.f3402h = jSONObject.getString("md5");
            droiUpdateResponse.f3403i = jSONObject.getString("totalSize");
            droiUpdateResponse.f3405k = jSONObject.getInt("delta");
            if (droiUpdateResponse.f3405k != 1) {
                return droiUpdateResponse;
            }
            droiUpdateResponse.f3406l = jSONObject.getString("deltaUrl");
            droiUpdateResponse.f3407m = jSONObject.getString("deltaMd5");
            droiUpdateResponse.f3408n = jSONObject.getString("deltaSize");
            return droiUpdateResponse;
        } catch (Exception e) {
            DroiLog.m2869e("DroiUpdateResponse", e);
            return null;
        }
    }

    protected String m3194a(Context context, boolean z) {
        String string = context.getString(C1050d.m3292a(context).m3295c("droi_new_version"));
        String string2 = context.getString(C1050d.m3292a(context).m3295c("droi_patch_size"));
        String string3 = context.getString(C1050d.m3292a(context).m3295c("droi_new_size"));
        String string4 = context.getString(C1050d.m3292a(context).m3295c("droi_dialog_installapk"));
        if (z) {
            return String.format("%s %s\n%s\n", new Object[]{string, this.f3400f, string4});
        }
        if (this.f3405k == 1) {
            string2 = String.format("\n%s %s", new Object[]{string2, C1047b.m3260a(this.f3408n)});
        } else {
            string2 = "";
        }
        return String.format("%s %s\n%s %s%s\n", new Object[]{string, this.f3400f, string3, C1047b.m3260a(this.f3403i), string2});
    }

    public int getErrorCode() {
        return this.f3395a;
    }

    protected String m3193a() {
        return this.f3396b;
    }

    public String getTitle() {
        return this.f3397c;
    }

    public String getContent() {
        return this.f3398d;
    }

    public int getUpdateType() {
        return this.f3399e;
    }

    public String getVersionName() {
        return this.f3400f;
    }

    public String getVersionCode() {
        return this.f3401g;
    }

    public String getNewMd5() {
        return this.f3402h;
    }

    public String getNewSize() {
        return this.f3403i;
    }

    public String getFileUrl() {
        return this.f3404j;
    }

    public boolean isDeltaUpdate() {
        if (this.f3405k == 0) {
            return false;
        }
        return true;
    }

    public String getPatchUrl() {
        return this.f3406l;
    }

    public String getPatchMd5() {
        return this.f3407m;
    }

    public String getPatchSize() {
        return this.f3408n;
    }
}
