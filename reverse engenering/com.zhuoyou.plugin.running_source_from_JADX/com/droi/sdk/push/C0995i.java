package com.droi.sdk.push;

import android.os.Bundle;
import android.os.Message;
import com.droi.sdk.push.utils.C1012g;
import org.json.JSONException;
import org.json.JSONObject;

class C0995i implements C0994n {
    final /* synthetic */ String f3309a;
    final /* synthetic */ DroiPushService f3310b;

    C0995i(DroiPushService droiPushService, String str) {
        this.f3310b = droiPushService;
        this.f3309a = str;
    }

    public void mo1927a(boolean z, JSONObject jSONObject) {
        String string;
        if (z) {
            C1012g.m3141c("get short link message result: success!");
            try {
                string = jSONObject.getString("msgInfo");
                if (string != null) {
                    Message obtainMessage = this.f3310b.f3193i.obtainMessage(1);
                    Bundle bundle = new Bundle();
                    bundle.putString("app_id", this.f3309a);
                    bundle.putString("push_message_string", string);
                    obtainMessage.setData(bundle);
                    this.f3310b.f3193i.sendMessage(obtainMessage);
                    return;
                }
                return;
            } catch (JSONException e) {
                C1012g.m3140b("parse 'msgInfo' from json failed!");
                return;
            }
        }
        C1012g.m3141c("Get short link message result: failed!");
        string = null;
        try {
            string = jSONObject.getString("errorCode");
        } catch (JSONException e2) {
            C1012g.m3140b("parse 'errorCode' of short link message failed!");
        }
        if (string != null) {
            C1012g.m3140b("receive short link msg error, code = " + string);
        }
    }
}
