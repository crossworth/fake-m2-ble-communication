package com.tencent.wxop.stat;

import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONException;
import org.json.JSONObject;

public class StatAccount {
    public static final int CUSTOM_TYPE = 7;
    public static final int DEFAULT_TYPE = 0;
    public static final int EMAIL_TYPE = 6;
    public static final int PHONE_NUM_TYPE = 5;
    public static final int QQ_NUM_TYPE = 1;
    public static final int QQ_OPENID_TYPE = 3;
    public static final int WECHAT_ID_TYPE = 2;
    public static final int WECHAT_OPENID_TYPE = 4;
    private String f4504a = "";
    private int f4505b = 0;
    private String f4506c = "";
    private String f4507d = "";

    public StatAccount(String str) {
        this.f4504a = str;
    }

    public StatAccount(String str, int i) {
        this.f4504a = str;
        this.f4505b = i;
    }

    public String getAccount() {
        return this.f4504a;
    }

    public int getAccountType() {
        return this.f4505b;
    }

    public String getExt() {
        return this.f4506c;
    }

    public String getExt1() {
        return this.f4507d;
    }

    public void setAccount(String str) {
        this.f4504a = str;
    }

    public void setAccountType(int i) {
        this.f4505b = i;
    }

    public void setExt(String str) {
        this.f4506c = str;
    }

    public void setExt1(String str) {
        this.f4507d = str;
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        if (C1442k.m4420c(this.f4504a)) {
            try {
                C1448q.m4464a(jSONObject, "a", this.f4504a);
                jSONObject.put("t", this.f4505b);
                C1448q.m4464a(jSONObject, "e", this.f4506c);
                C1448q.m4464a(jSONObject, "e1", this.f4507d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    public String toString() {
        return "StatAccount [account=" + this.f4504a + ", accountType=" + this.f4505b + ", ext=" + this.f4506c + ", ext1=" + this.f4507d + "]";
    }
}
