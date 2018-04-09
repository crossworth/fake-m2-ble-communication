package com.zhuoyou.plugin.appWidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import com.amap.api.maps.model.WeightedLatLng;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.FirstActivity;
import com.zhuoyou.plugin.running.PersonalGoal;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.text.NumberFormat;

public class RunningAppWidget extends AppWidgetProvider {
    private static int PHONE_CALORIES = 0;
    private static int PHONE_STEP = 0;
    public static int Tag;
    public int current;
    private Context mCtx = RunningApp.getInstance();
    public int max;
    private float num = 0.0f;
    private PersonalGoal personal;
    private Context sContext = RunningApp.getInstance().getApplicationContext();

    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    private void readPhoneStep(String day) {
        Cursor c = this.mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.CALORIES}, "date  = ? AND statistics = ?", new String[]{day, "1"}, null);
        c.moveToFirst();
        PHONE_STEP = 0;
        PHONE_CALORIES = 0;
        if (c.getCount() > 0) {
            for (int y = 0; y < c.getCount(); y++) {
                PHONE_STEP = c.getInt(c.getColumnIndex("steps"));
                PHONE_CALORIES = c.getInt(c.getColumnIndex(DataBaseContants.CALORIES));
                c.moveToNext();
            }
        }
        c.close();
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        readPhoneStep(Tools.getDate(0));
        this.personal = Tools.getPersonalGoal();
        this.num = 0.0f;
        if (PHONE_STEP != 0) {
            this.num = ((float) PHONE_STEP) / ((float) this.personal.mGoalSteps);
        }
        ComponentName thisWidget = new ComponentName(context, RunningAppWidget.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.running_app_widget);
        views.setTextViewText(R.id.tv_steps, String.valueOf(PHONE_STEP));
        views.setTextViewText(R.id.tv_cal, String.valueOf(PHONE_CALORIES));
        views.setTextViewText(R.id.tv_completion, String.valueOf((int) ((this.num * 100.0f) / 1.0f)));
        views.setImageViewBitmap(R.id.im_arc_percent, updateNotificationRemoteViews(this.sContext, this.num));
        views.setOnClickPendingIntent(R.id.running_app_widget, PendingIntent.getActivity(context, 0, new Intent(context, FirstActivity.class), 0));
        Log.i("111", "qqqq");
        String action = intent.getAction();
        Bundle extras;
        if ("android.appwidget.action.APPWIDGET_UPDATE".equals(action)) {
            extras = intent.getExtras();
            if (extras != null) {
                int[] appWidgetIds = extras.getIntArray("appWidgetIds");
                if (appWidgetIds != null && appWidgetIds.length > 0) {
                    onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
                }
            }
        } else if ("android.appwidget.action.APPWIDGET_DELETED".equals(action)) {
            extras = intent.getExtras();
            if (extras != null && extras.containsKey("appWidgetId")) {
                int appWidgetId = extras.getInt("appWidgetId");
                onDeleted(context, new int[]{appWidgetId});
            }
        } else if ("android.appwidget.action.APPWIDGET_ENABLED".equals(action)) {
            onEnabled(context);
        } else if ("android.appwidget.action.APPWIDGET_DISABLED".equals(action)) {
            onDisabled(context);
        }
        AppWidgetManager.getInstance(context).updateAppWidget(thisWidget, views);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.running_app_widget);
            ((AlarmManager) context.getSystemService("alarm")).setRepeating(1, SystemClock.elapsedRealtime() + 5000, (long) 10000, PendingIntent.getBroadcast(context, 0, new Intent("android.appwidget.action.APPWIDGET_UPDATE"), 134217728));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d("app", "update---->id" + appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, new RemoteViews(context.getPackageName(), R.layout.running_app_widget));
    }

    public Bitmap updateNotificationRemoteViews(Context paramContext, float num) {
        double d = (double) num;
        if (num > 0.0f) {
            if (((double) num) < 0.01d) {
                d = 0.01d;
            }
            if (((double) num) >= WeightedLatLng.DEFAULT_INTENSITY) {
                d = WeightedLatLng.DEFAULT_INTENSITY;
            }
        }
        NumberFormat.getPercentInstance().setMinimumFractionDigits(0);
        Bitmap localBitmap = Bitmap.createBitmap(Util.dip2pixel(paramContext, 53.0f), Util.dip2pixel(paramContext, 53.0f), Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setColor(Color.rgb(236, 122, 35));
        localPaint.setStyle(Style.STROKE);
        localPaint.setStrokeWidth((float) Util.dip2pixel(paramContext, 6.0f));
        localCanvas.drawArc(new RectF((float) Util.dip2pixel(paramContext, 3.0f), (float) Util.dip2pixel(paramContext, 3.0f), (float) Util.dip2pixel(paramContext, 49.0f), (float) Util.dip2pixel(paramContext, 49.0f)), -90.0f, (float) ((int) (360.0d * d)), false, localPaint);
        return localBitmap;
    }
}
