package com.umeng.socialize.net.base;

import android.text.TextUtils;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.UResponse;
import com.umeng.socialize.utils.Log;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class SocializeReseponse extends UResponse {
    protected static final String TAG = "SocializeReseponse";
    protected JSONObject mJsonData;
    public String mMsg;
    public int mStCode = StatusCode.ST_CODE_SDK_NORESPONSE;

    public SocializeReseponse(JSONObject jSONObject) {
        super(jSONObject);
        this.mJsonData = parseStatus(jSONObject);
        parseJsonObject();
    }

    public boolean isOk() {
        boolean z;
        String str = "umeng_share_response";
        StringBuilder append = new StringBuilder().append("is http 200:");
        if (this.mStCode == 200) {
            z = true;
        } else {
            z = false;
        }
        Log.m3248d(str, append.append(z).toString());
        return this.mStCode == 200;
    }

    public JSONObject getJsonData() {
        return this.mJsonData;
    }

    private JSONObject parseStatus(JSONObject jSONObject) {
        if (jSONObject == null) {
            Log.m3251e(TAG, "failed requesting");
            return null;
        }
        try {
            this.mStCode = jSONObject.optInt(SocializeProtocolConstants.PROTOCOL_KEY_ST, SocializeConstants.SERVER_RETURN_PARAMS_ILLEGAL);
            if (this.mStCode == 0) {
                Log.m3251e(TAG, "no status code in response.");
                return null;
            }
            this.mMsg = jSONObject.optString("msg", "");
            String optString = jSONObject.optString("data", null);
            if (TextUtils.isEmpty(optString)) {
                return null;
            }
            if (this.mStCode != 200) {
                parseErrorMsg(optString);
            }
            return new JSONObject(optString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.m3251e(TAG, "Data body can`t convert to json ");
            return null;
        }
    }

    public void parseJsonObject() {
    }

    private void parseErrorMsg(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(str2);
                Object string = jSONObject2.getString("msg");
                if (TextUtils.isEmpty(string)) {
                    printLog(str2, jSONObject2.getJSONObject("data").getString(SocializeProtocolConstants.PROTOCOL_KEY_PLATFORM_ERROR));
                } else {
                    printLog(str2, string);
                }
            }
        } catch (Exception e) {
        }
    }

    private void printLog(String str, String str2) {
        Log.m3251e(TAG, "error message -> " + str + " : " + str2);
    }
}
