package com.umeng.socialize.net;

import android.text.TextUtils;
import com.umeng.socialize.net.base.SocializeReseponse;
import org.json.JSONException;
import org.json.JSONObject;

public class PlatformTokenUploadResponse extends SocializeReseponse {
    public String mExpiresIn;
    public String mTencentUid;

    public PlatformTokenUploadResponse(JSONObject jSONObject) {
        super(jSONObject);
    }

    public void parseJsonObject() {
        super.parseJsonObject();
        m6115a();
        m6116b();
    }

    private void m6115a() {
        if (this.mJsonData != null) {
            try {
                JSONObject jSONObject = this.mJsonData.getJSONObject("tencent");
                if (jSONObject != null) {
                    Object optString = jSONObject.optString("user_id");
                    if (!TextUtils.isEmpty(optString)) {
                        this.mTencentUid = optString;
                    }
                }
            } catch (JSONException e) {
            }
        }
    }

    private void m6116b() {
    }
}
