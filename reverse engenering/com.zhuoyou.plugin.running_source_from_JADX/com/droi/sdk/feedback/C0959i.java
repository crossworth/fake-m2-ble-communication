package com.droi.sdk.feedback;

import com.droi.sdk.core.DroiFile;
import com.droi.sdk.internal.DroiLog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class C0959i extends Thread {
    final /* synthetic */ List f3121a;
    final /* synthetic */ DroiFeedbackSendListener f3122b;
    final /* synthetic */ String f3123c;
    final /* synthetic */ String f3124d;

    C0959i(List list, DroiFeedbackSendListener droiFeedbackSendListener, String str, String str2) {
        this.f3121a = list;
        this.f3122b = droiFeedbackSendListener;
        this.f3123c = str;
        this.f3124d = str2;
    }

    public void run() {
        List arrayList = new ArrayList();
        if (this.f3121a != null && this.f3121a.size() != 0) {
            int size = this.f3121a.size();
            int i = 0;
            while (true) {
                if (i >= (size < 3 ? size : 3)) {
                    break;
                }
                File file = (File) this.f3121a.get(i);
                if (file != null && file.isFile()) {
                    DroiFile droiFile = new DroiFile(file);
                    if (droiFile.save().isOk()) {
                        arrayList.add(droiFile.getUri().getPath());
                    } else {
                        this.f3122b.onReturned(5);
                        return;
                    }
                }
                i++;
            }
        }
        DroiLog.m2871i("DroiFeedbackImpl", "upload image completed");
        boolean a = C0955e.m2825a(this.f3123c, this.f3124d, arrayList);
        DroiLog.m2871i("DroiFeedbackImpl", "result:" + a);
        if (this.f3122b != null) {
            DroiLog.m2871i("DroiFeedbackImpl", "feedbackListener!=null");
            if (a) {
                this.f3122b.onReturned(6);
            } else {
                this.f3122b.onReturned(0);
            }
        }
    }
}
