package com.zhuoyou.plugin.running.receiver;

import android.content.BroadcastReceiver;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_RUN_TASK = "com.droi.droihealth.ACTION.RUN_TASK";
    public static final String ACTION_WATER_ALARM = "com.droi.droihealth.ACTION.WATER_ALARM";
    public static final String KEY_TASK = "key_task_type";
    public static final String KEY_WATER_ALARM_ID = "key_water_alarm_id";
    public static final int TASK_REFRESH_SPORT = 4098;
    public static final int TASK_UPLOAD_SPORT = 4097;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r10, android.content.Intent r11) {
        /*
        r9 = this;
        r8 = 7;
        r5 = -1;
        r4 = 0;
        r0 = r11.getAction();
        r3 = "zhuqichao";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "AlarmReceiver.Action=";
        r6 = r6.append(r7);
        r6 = r6.append(r0);
        r6 = r6.toString();
        android.util.Log.i(r3, r6);
        r3 = "zhuqichao";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "AlarmReceiver.data=";
        r6 = r6.append(r7);
        r7 = r11.getData();
        r6 = r6.append(r7);
        r6 = r6.toString();
        android.util.Log.i(r3, r6);
        r3 = r0.hashCode();
        switch(r3) {
            case -763666654: goto L_0x0053;
            case 505380757: goto L_0x0067;
            case 1041332296: goto L_0x005d;
            case 1614168896: goto L_0x0049;
            default: goto L_0x0044;
        };
    L_0x0044:
        r3 = r5;
    L_0x0045:
        switch(r3) {
            case 0: goto L_0x0071;
            case 1: goto L_0x00a3;
            case 2: goto L_0x00c1;
            case 3: goto L_0x00c4;
            default: goto L_0x0048;
        };
    L_0x0048:
        return;
    L_0x0049:
        r3 = "com.droi.droihealth.ACTION.RUN_TASK";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0044;
    L_0x0051:
        r3 = r4;
        goto L_0x0045;
    L_0x0053:
        r3 = "com.droi.droihealth.ACTION.WATER_ALARM";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0044;
    L_0x005b:
        r3 = 1;
        goto L_0x0045;
    L_0x005d:
        r3 = "android.intent.action.DATE_CHANGED";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0044;
    L_0x0065:
        r3 = 2;
        goto L_0x0045;
    L_0x0067:
        r3 = "android.intent.action.TIME_SET";
        r3 = r0.equals(r3);
        if (r3 == 0) goto L_0x0044;
    L_0x006f:
        r3 = 3;
        goto L_0x0045;
    L_0x0071:
        r3 = "key_task_type";
        r3 = r11.getIntExtra(r3, r5);
        switch(r3) {
            case 4097: goto L_0x007b;
            case 4098: goto L_0x008d;
            default: goto L_0x007a;
        };
    L_0x007a:
        goto L_0x0048;
    L_0x007b:
        com.zhuoyou.plugin.running.baas.BaasHelper.uploadSportInBackground();	 Catch:{ Exception -> 0x0088 }
    L_0x007e:
        r3 = "zhuqichao";
        r4 = "自动上传数据";
        android.util.Log.i(r3, r4);
        goto L_0x0048;
    L_0x0088:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x007e;
    L_0x008d:
        r3 = org.greenrobot.eventbus.EventBus.getDefault();
        r5 = new com.zhuoyou.plugin.running.bean.EventStep;
        r5.<init>(r4);
        r3.post(r5);
        r3 = "zhuqichao";
        r4 = "数据清零";
        android.util.Log.i(r3, r4);
        goto L_0x0048;
    L_0x00a3:
        r3 = "zhuqichao";
        r5 = "喝水提醒";
        android.util.Log.i(r3, r5);
        r3 = "key_water_alarm_id";
        r2 = r11.getIntExtra(r3, r8);
        if (r2 < r8) goto L_0x00b7;
    L_0x00b4:
        com.zhuoyou.plugin.running.tools.SPUtils.setWaterNumber(r4);
    L_0x00b7:
        r3 = com.zhuoyou.plugin.running.tools.SPUtils.isWaterAlarmOpen();
        if (r3 == 0) goto L_0x0048;
    L_0x00bd:
        com.zhuoyou.plugin.running.tools.WaterAlarmUtils.buildWaterAlarmNotify(r10, r2);
        goto L_0x0048;
    L_0x00c1:
        com.zhuoyou.plugin.running.tools.SPUtils.setWaterNumber(r4);
    L_0x00c4:
        r3 = org.greenrobot.eventbus.EventBus.getDefault();
        r5 = new com.zhuoyou.plugin.running.bean.EventStep;
        r5.<init>(r4);
        r3.post(r5);
        goto L_0x0048;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.running.receiver.AlarmReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
