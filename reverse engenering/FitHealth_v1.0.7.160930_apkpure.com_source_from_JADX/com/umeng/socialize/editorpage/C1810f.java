package com.umeng.socialize.editorpage;

import android.location.Location;
import android.widget.Toast;
import com.umeng.socialize.bean.UMLocation;
import com.umeng.socialize.editorpage.location.C0959b;
import com.umeng.socialize.editorpage.location.C1811a;
import com.umeng.socialize.utils.Log;

/* compiled from: ShareActivity */
class C1810f extends C0959b {
    final /* synthetic */ ShareActivity f4823a;

    C1810f(ShareActivity shareActivity, C1811a c1811a) {
        this.f4823a = shareActivity;
        super(c1811a);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m4990a((Location) obj);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        this.f4823a.m3203a(true);
    }

    protected void m4990a(Location location) {
        super.onPostExecute(location);
        Log.m3251e("xxxxx", "result = " + location);
        this.f4823a.f3269C = UMLocation.build(location);
        this.f4823a.m3203a(false);
        if (location == null && !this.f4823a.isFinishing()) {
            Toast.makeText(this.f4823a.f3297y, "获取地理位置失败，请稍候重试.", 0).show();
        }
    }

    protected void onCancelled() {
        super.onCancelled();
        this.f4823a.m3203a(false);
    }
}
