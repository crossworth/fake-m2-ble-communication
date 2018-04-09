package com.zhuoyou.plugin.running.tools;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.droi.btlib.connection.MessageObj;
import com.tencent.connect.common.Constants;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.WaterAlarmDialogActivity;
import com.zhuoyou.plugin.running.activity.WaterReminderActivity;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.receiver.AlarmReceiver;
import java.util.Calendar;
import java.util.Locale;

public class WaterAlarmUtils {
    private static final String[] TIMES = new String[]{"0700", "0840", Constants.DEFAULT_UIN, "1130", "1430", "1600", "1800", "2200"};

    public static void initWaterAlarm(boolean isOpen) {
        AlarmManager am = (AlarmManager) TheApp.getContext().getSystemService("alarm");
        Calendar today = Calendar.getInstance(Locale.CHINA);
        today.set(13, 0);
        today.set(14, 0);
        for (int i = 0; i < TIMES.length; i++) {
            Intent intent = new Intent(AlarmReceiver.ACTION_WATER_ALARM);
            intent.setClass(TheApp.getContext(), AlarmReceiver.class);
            intent.setData(Uri.parse("content://calendar/water_alarm/" + i));
            intent.putExtra(AlarmReceiver.KEY_WATER_ALARM_ID, i);
            PendingIntent pi = PendingIntent.getBroadcast(TheApp.getContext(), i, intent, 134217728);
            if (isOpen) {
                Calendar calendar = Calendar.getInstance(Locale.CHINA);
                calendar.set(11, Integer.parseInt(TIMES[i].substring(0, 2)));
                calendar.set(12, Integer.parseInt(TIMES[i].substring(2, 4)));
                calendar.set(13, 0);
                calendar.set(14, 0);
                if (calendar.before(today)) {
                    calendar.add(6, 1);
                }
                Log.i("zhuqichao", "set alarm=" + calendar.getTime() + ", " + calendar.getTimeInMillis());
                am.setRepeating(0, calendar.getTimeInMillis(), LogBuilder.MAX_INTERVAL, pi);
            } else {
                am.cancel(pi);
            }
        }
    }

    public static void showWaterAlarmDialog(Context context, int id) {
        Intent intent = new Intent(context, WaterAlarmDialogActivity.class);
        intent.putExtra(AlarmReceiver.KEY_WATER_ALARM_ID, id);
        intent.addFlags(872415232);
        context.startActivity(intent);
    }

    public static void buildWaterAlarmNotify(Context context, int id) {
        NotificationManager nm = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
        Builder builder = new Builder(context);
        builder.setDefaults(6);
        builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + C1680R.raw.water_voice));
        builder.setSmallIcon(C1680R.mipmap.ic_launcher);
        builder.setShowWhen(true);
        builder.setContentTitle(context.getString(C1680R.string.mine_water_reminder));
        builder.setContentText(context.getString(WaterAlarmDialogActivity.ids[id]));
        builder.setTicker(context.getString(C1680R.string.drink_notify_body_default));
        builder.setAutoCancel(true);
        builder.setPriority(2);
        Intent intent = new Intent(context, WaterReminderActivity.class);
        intent.setFlags(335544320);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
        BigTextStyle bigTextStyle = new BigTextStyle();
        bigTextStyle.setBigContentTitle(context.getString(C1680R.string.mine_water_reminder));
        bigTextStyle.bigText(context.getString(WaterAlarmDialogActivity.ids[id]));
        bigTextStyle.setBuilder(builder);
        builder.setStyle(bigTextStyle);
        nm.notify(100, builder.build());
    }
}
