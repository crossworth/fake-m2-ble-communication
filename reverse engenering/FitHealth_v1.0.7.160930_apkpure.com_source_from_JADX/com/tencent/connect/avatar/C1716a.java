package com.tencent.connect.avatar;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
class C1716a implements IUiListener {
    final /* synthetic */ ImageActivity f4538a;

    C1716a(ImageActivity imageActivity) {
        this.f4538a = imageActivity;
    }

    public void onError(UiError uiError) {
        m4669a(0);
    }

    public void onComplete(Object obj) {
        JSONObject jSONObject = (JSONObject) obj;
        int i = -1;
        try {
            i = jSONObject.getInt("ret");
            if (i == 0) {
                final String string = jSONObject.getString("nickname");
                this.f4538a.f2392d.post(new Runnable(this) {
                    final /* synthetic */ C1716a f2413b;

                    public void run() {
                        this.f2413b.f4538a.m2349c(string);
                    }
                });
                this.f4538a.m2364a("10659", 0);
            } else {
                this.f4538a.m2364a("10661", 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (i != 0) {
            m4669a(i);
        }
    }

    public void onCancel() {
    }

    private void m4669a(int i) {
        if (this.f4538a.f2399k < 2) {
            this.f4538a.m2354e();
        }
    }
}
