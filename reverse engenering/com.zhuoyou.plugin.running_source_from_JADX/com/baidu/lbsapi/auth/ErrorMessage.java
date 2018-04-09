package com.baidu.lbsapi.auth;

import com.umeng.facebook.share.internal.ShareConstants;
import org.json.JSONException;
import org.json.JSONObject;

class ErrorMessage {
    ErrorMessage() {
    }

    static String m102a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", -1);
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, str);
        } catch (JSONException e) {
        }
        return jSONObject.toString();
    }
}
