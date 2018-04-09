package com.droi.sdk.selfupdate;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1047b;

class C1033h extends Handler {
    final /* synthetic */ Context f3429a;
    final /* synthetic */ C1032g f3430b;

    C1033h(C1032g c1032g, Looper looper, Context context) {
        this.f3430b = c1032g;
        this.f3429a = context;
        super(looper);
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        DroiLog.m2871i("DroiUpdateImpl", "handleMessage:" + message.what);
        DroiUpdateResponse droiUpdateResponse = (DroiUpdateResponse) message.obj;
        if (message.what == 1) {
            C1047b.m3265b(this.f3429a, droiUpdateResponse.m3193a());
            C1047b.m3267c(this.f3429a, droiUpdateResponse.getVersionCode());
            C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3450a, System.currentTimeMillis());
            if ((droiUpdateResponse.getUpdateType() != 1 && !C1042p.f3467c) || C1042p.f3466b || this.f3430b.f3427e == null) {
                DroiLog.m2871i("DroiUpdateImpl", "UIStyle:" + C1042p.f3468d);
                this.f3430b.m3211a(C1032g.f3424b, droiUpdateResponse, C1042p.f3468d);
            } else {
                C1041o.m3235a("m01", droiUpdateResponse.m3193a(), C1041o.f3453d, System.currentTimeMillis());
                this.f3430b.f3428f = false;
                this.f3430b.f3427e.onUpdateReturned(message.what, droiUpdateResponse);
            }
        } else {
            this.f3430b.f3428f = false;
            if (C1042p.f3466b || this.f3430b.f3427e == null) {
                if (C1042p.f3467c) {
                    switch (message.what) {
                        case 0:
                            Toast.makeText(C1032g.f3423a, "已经是最新版本", 0).show();
                            break;
                        case 2:
                            Toast.makeText(C1032g.f3423a, "查询更新发生错误", 0).show();
                            break;
                        case 3:
                            Toast.makeText(C1032g.f3423a, "查询更新超时", 0).show();
                            break;
                        case 5:
                            Toast.makeText(C1032g.f3423a, "正在更新中", 0).show();
                            break;
                        default:
                            break;
                    }
                }
            }
            this.f3430b.f3427e.onUpdateReturned(message.what, droiUpdateResponse);
        }
        C1032g.f3424b = null;
    }
}
