package com.droi.sdk.feedback;

import android.util.Log;
import com.droi.sdk.feedback.p018a.C0949a;
import java.util.List;

public class DroiFeedbackInfo {
    private String f3104a;
    private String f3105b;
    private String f3106c;
    private String f3107d;
    private String f3108e;
    private List<String> f3109f;

    public String getCreateTime() {
        Log.i("chenpei", "creat" + this.f3104a);
        return C0949a.m2817c(this.f3104a);
    }

    public String getUTCCreateTime() {
        return this.f3104a;
    }

    public void setCreateTime(String str) {
        this.f3104a = str;
    }

    public String getContact() {
        return this.f3105b;
    }

    public void setContact(String str) {
        this.f3105b = str;
    }

    public String getContent() {
        return this.f3106c;
    }

    public void setContent(String str) {
        this.f3106c = str;
    }

    public String getReply() {
        return this.f3107d;
    }

    public void setReply(String str) {
        this.f3107d = str;
    }

    public String getReplyTime() {
        return C0949a.m2817c(this.f3108e);
    }

    public String getUTCReplyTime() {
        return this.f3108e;
    }

    public void setReplyTime(String str) {
        this.f3108e = str;
    }

    public List<String> getImageList() {
        return this.f3109f;
    }

    public void setImageList(List<String> list) {
        this.f3109f = list;
    }
}
