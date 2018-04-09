package com.droi.sdk.feedback;

import java.util.List;

class C0966n implements DroiFeedbackReplyListener {
    final /* synthetic */ C0963k f3144a;

    C0966n(C0963k c0963k) {
        this.f3144a = c0963k;
    }

    public void onResult(int i, List<DroiFeedbackInfo> list) {
        switch (i) {
            case 0:
                this.f3144a.f3136a.sendMessage(this.f3144a.f3136a.obtainMessage(0, null));
                return;
            case 1:
                this.f3144a.f3136a.sendMessage(this.f3144a.f3136a.obtainMessage(1, list));
                return;
            case 2:
                this.f3144a.f3136a.sendMessage(this.f3144a.f3136a.obtainMessage(2, null));
                return;
            default:
                return;
        }
    }
}
