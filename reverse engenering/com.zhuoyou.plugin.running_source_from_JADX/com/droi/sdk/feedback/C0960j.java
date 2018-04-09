package com.droi.sdk.feedback;

import android.util.Log;
import com.droi.sdk.internal.DroiLog;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class C0960j extends Thread {
    final /* synthetic */ DroiFeedbackReplyListener f3125a;

    C0960j(DroiFeedbackReplyListener droiFeedbackReplyListener) {
        this.f3125a = droiFeedbackReplyListener;
    }

    public void run() {
        JSONObject jSONObject;
        JSONArray jSONArray;
        int i;
        List b;
        try {
            String a = C0955e.m2823a();
            if (a != null) {
                Log.w("DroiFeedbackImpl", a);
                jSONObject = new JSONObject(a);
                if (jSONObject != null) {
                    try {
                        if (jSONObject.getInt("code") == 0) {
                            jSONArray = jSONObject.getJSONArray("result");
                            if (jSONArray != null || jSONArray.length() == 0) {
                                i = 1;
                                b = C0957g.m2828b();
                                if (this.f3125a == null) {
                                }
                                if (i == 0) {
                                    this.f3125a.onResult(2, null);
                                } else if (b != null || b.size() == 0) {
                                    this.f3125a.onResult(0, null);
                                    return;
                                } else {
                                    DroiLog.m2871i("DroiFeedbackImpl", "infos != null");
                                    this.f3125a.onResult(1, b);
                                    return;
                                }
                            }
                            List arrayList = new ArrayList();
                            for (i = 0; i < jSONArray.length(); i++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                DroiFeedbackInfo droiFeedbackInfo = new DroiFeedbackInfo();
                                droiFeedbackInfo.setContent(jSONObject2.getString("content"));
                                droiFeedbackInfo.setCreateTime(jSONObject2.getString("createTime"));
                                droiFeedbackInfo.setReply(jSONObject2.optString("reply"));
                                droiFeedbackInfo.setReplyTime(jSONObject2.optString("replyTime"));
                                arrayList.add(droiFeedbackInfo);
                            }
                            C0957g.m2829c();
                            C0957g.m2827a(arrayList);
                            if (this.f3125a != null) {
                                this.f3125a.onResult(1, arrayList);
                                return;
                            }
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        i = 1;
                    }
                }
                i = 0;
                b = C0957g.m2828b();
                if (this.f3125a == null) {
                    if (i == 0) {
                        if (b != null) {
                        }
                        this.f3125a.onResult(0, null);
                        return;
                    }
                    this.f3125a.onResult(2, null);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        jSONObject = null;
        if (jSONObject != null) {
            if (jSONObject.getInt("code") == 0) {
                jSONArray = jSONObject.getJSONArray("result");
                if (jSONArray != null) {
                }
                i = 1;
                b = C0957g.m2828b();
                if (this.f3125a == null) {
                }
                if (i == 0) {
                    this.f3125a.onResult(2, null);
                }
                if (b != null) {
                }
                this.f3125a.onResult(0, null);
                return;
            }
        }
        i = 0;
        b = C0957g.m2828b();
        if (this.f3125a == null) {
            if (i == 0) {
                if (b != null) {
                }
                this.f3125a.onResult(0, null);
                return;
            }
            this.f3125a.onResult(2, null);
        }
    }
}
