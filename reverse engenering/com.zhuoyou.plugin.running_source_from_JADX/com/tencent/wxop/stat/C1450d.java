package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;
import java.util.Timer;
import java.util.TimerTask;

public class C1450d {
    private static volatile C1450d f4813b = null;
    private Timer f4814a = null;
    private Context f4815c = null;

    private C1450d(Context context) {
        this.f4815c = context.getApplicationContext();
        this.f4814a = new Timer(false);
    }

    public static C1450d m4474a(Context context) {
        if (f4813b == null) {
            synchronized (C1450d.class) {
                if (f4813b == null) {
                    f4813b = new C1450d(context);
                }
            }
        }
        return f4813b;
    }

    public void m4475a() {
        if (StatConfig.getStatSendStrategy() == StatReportStrategy.PERIOD) {
            long sendPeriodMinutes = (long) ((StatConfig.getSendPeriodMinutes() * 60) * 1000);
            if (StatConfig.isDebugEnable()) {
                C1442k.m4416b().m4376i("setupPeriodTimer delay:" + sendPeriodMinutes);
            }
            m4476a(new C1451e(this), sendPeriodMinutes);
        }
    }

    public void m4476a(TimerTask timerTask, long j) {
        if (this.f4814a != null) {
            if (StatConfig.isDebugEnable()) {
                C1442k.m4416b().m4376i("setupPeriodTimer schedule delay:" + j);
            }
            this.f4814a.schedule(timerTask, j);
        } else if (StatConfig.isDebugEnable()) {
            C1442k.m4416b().m4378w("setupPeriodTimer schedule timer == null");
        }
    }
}
