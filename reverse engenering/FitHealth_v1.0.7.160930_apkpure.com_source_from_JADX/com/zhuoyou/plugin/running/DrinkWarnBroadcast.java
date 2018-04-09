package com.zhuoyou.plugin.running;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.util.List;

public class DrinkWarnBroadcast extends BroadcastReceiver {
    int[] ids = new int[]{R.string.drink_notify_body1, R.string.drink_notify_body2, R.string.drink_notify_body3, R.string.drink_notify_body4, R.string.drink_notify_body5, R.string.drink_notify_body6, R.string.drink_notify_body7, R.string.drink_notify_body8, R.string.default_notify_body};
    private SharedPreferences mSharedPres;
    Boolean phoneState = Boolean.valueOf(true);

    public void onReceive(Context context, Intent intent1) {
        this.mSharedPres = context.getSharedPreferences("TestResult", 2);
        Editor editor = this.mSharedPres.edit();
        int id = intent1.getIntExtra("key", 8);
        if (id == 7) {
            editor.putInt("water_num", 0);
            editor.putBoolean("warn_enable", false);
            editor.commit();
        }
        Log.i("DrinkWarnBroadcast", intent1.getAction());
        if (intent1.getAction().equals("Drink_Water_Warn")) {
            Log.v("renjing", "Drink_Water_Warn");
            long current = System.currentTimeMillis();
            if (iscurrentApp(context)) {
                Log.v("zhaojunhui", "is current running");
                Intent intent = new Intent(context, WarnDialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key", id);
                intent.putExtras(bundle);
                intent.addFlags(872415232);
                context.startActivity(intent);
                return;
            }
            send(context, id);
            Log.v("zhaojunhui", "is not current running and shoule send a notification");
        } else if (intent1.getAction().equals("android.intent.action.DATE_CHANGED")) {
            editor.putInt("water_num", 0);
            editor.commit();
        }
    }

    private boolean iscurrentApp(Context context) {
        String packageName = "com.zhuoyou.plugin.running";
        List<RunningTaskInfo> tasksInfo = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            System.out.println("---------------包名-----------" + ((RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName());
            if (packageName.equals(((RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public void send(Context context, int srcId) {
        NotificationManager nm = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
        Builder builder = new Builder(context);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setShowWhen(true);
        builder.setContentTitle(context.getResources().getString(R.string.drink_time));
        builder.setContentText(context.getString(R.string.firmwear_notiy_message));
        builder.setTicker(context.getString(R.string.firmwear_notiy_title));
        builder.setAutoCancel(true);
        builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.water_voice));
        builder.setAutoCancel(true);
        Intent intent = new Intent(context, WaterIntakeActivity.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(603979776);
        builder.setContentIntent(PendingIntent.getActivity(context, 100, intent, 0));
        nm.notify(100, builder.build());
    }
}
