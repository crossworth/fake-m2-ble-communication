package com.umeng.socialize.net;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareMultiResponse extends SocializeReseponse {
    public Map<SHARE_MEDIA, Integer> mInfoMap;
    public String mWeiboId;
    public SHARE_MEDIA platform;

    public ShareMultiResponse(JSONObject jSONObject) {
        super(jSONObject);
    }

    public void parseJsonObject() {
        super.parseJsonObject();
        this.mInfoMap = new HashMap();
        SHARE_MEDIA[] defaultPlatform = SHARE_MEDIA.getDefaultPlatform();
        if (defaultPlatform != null) {
            for (SHARE_MEDIA share_media : defaultPlatform) {
                String share_media2 = share_media.toString();
                if (this.mJsonData.has(share_media2)) {
                    try {
                        JSONObject jSONObject = this.mJsonData.getJSONObject(share_media2);
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        if (jSONObject2 != null && jSONObject2.has(SocializeConstants.JSON_SEND_RESULT)) {
                            JSONObject jSONObject3 = null;
                            try {
                                jSONObject3 = jSONObject2.getJSONObject(SocializeConstants.JSON_SEND_RESULT);
                            } catch (Exception e) {
                            }
                            if (jSONObject3 != null) {
                                this.mWeiboId = jSONObject3.optString("id", "");
                                this.platform = share_media;
                            }
                        }
                        this.mInfoMap.put(share_media, Integer.valueOf(jSONObject.optInt(SocializeProtocolConstants.PROTOCOL_KEY_ST)));
                    } catch (JSONException e2) {
                    }
                }
            }
        }
    }

    public String toString() {
        return "ShareMultiResponse [mInfoMap=" + this.mInfoMap + ", mWeiboId=" + this.mWeiboId + ", mMsg=" + this.mMsg + ", mStCode=" + this.mStCode + "]";
    }
}
