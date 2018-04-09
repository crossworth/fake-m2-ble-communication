package com.tencent.connect.avatar;

import android.content.Context;
import android.content.Intent;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
class C1717e implements IUiListener {
    final /* synthetic */ ImageActivity f4539a;

    C1717e(ImageActivity imageActivity) {
        this.f4539a = imageActivity;
    }

    public void onError(UiError uiError) {
        this.f4539a.f2395g.setEnabled(true);
        this.f4539a.f2395g.setTextColor(-1);
        this.f4539a.f2394f.setEnabled(true);
        this.f4539a.f2394f.setTextColor(-1);
        this.f4539a.f2394f.setText("重试");
        this.f4539a.f2398j.setVisibility(8);
        this.f4539a.f2400l = true;
        this.f4539a.m2339a(uiError.errorMessage, 1);
        this.f4539a.m2364a("10660", 0);
    }

    public void onComplete(Object obj) {
        int i;
        this.f4539a.f2395g.setEnabled(true);
        this.f4539a.f2395g.setTextColor(-1);
        this.f4539a.f2394f.setEnabled(true);
        this.f4539a.f2394f.setTextColor(-1);
        this.f4539a.f2398j.setVisibility(8);
        JSONObject jSONObject = (JSONObject) obj;
        try {
            i = jSONObject.getInt("ret");
        } catch (JSONException e) {
            e.printStackTrace();
            i = -1;
        }
        if (i == 0) {
            this.f4539a.m2339a("设置成功", 0);
            this.f4539a.m2364a("10658", 0);
            Context context = this.f4539a;
            if (!(this.f4539a.f2391c == null || "".equals(this.f4539a.f2391c))) {
                Intent intent = new Intent();
                intent.setClassName(context, this.f4539a.f2391c);
                if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                    context.startActivity(intent);
                }
            }
            this.f4539a.m2335a(0, jSONObject.toString(), null, null);
            this.f4539a.m2352d();
            return;
        }
        this.f4539a.m2339a("设置出错了，请重新登录再尝试下呢：）", 1);
    }

    public void onCancel() {
    }
}
