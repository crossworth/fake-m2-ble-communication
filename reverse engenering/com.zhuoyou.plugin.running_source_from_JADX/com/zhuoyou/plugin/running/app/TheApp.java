package com.zhuoyou.plugin.running.app;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.service.BtManagerService;
import com.droi.greendao.bean.GpsPointBean;
import com.droi.greendao.bean.GpsSportBean;
import com.droi.greendao.bean.HeartBean;
import com.droi.greendao.bean.SleepBean;
import com.droi.greendao.bean.SportBean;
import com.droi.greendao.bean.WeightBean;
import com.droi.greendao.dao.DaoMaster;
import com.droi.greendao.dao.DaoSession;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiMessageHandler;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.SplashActivity;
import com.zhuoyou.plugin.running.baas.ActivityRank;
import com.zhuoyou.plugin.running.baas.CloudAPI;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentInfo;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentRequest;
import com.zhuoyou.plugin.running.baas.CommentCloud.CommentResponse;
import com.zhuoyou.plugin.running.baas.CommentData;
import com.zhuoyou.plugin.running.baas.CommentZan;
import com.zhuoyou.plugin.running.baas.DeleteGpsSport;
import com.zhuoyou.plugin.running.baas.FishGameScore;
import com.zhuoyou.plugin.running.baas.FishRank;
import com.zhuoyou.plugin.running.baas.Rank.RankInfo;
import com.zhuoyou.plugin.running.baas.Rank.Request;
import com.zhuoyou.plugin.running.baas.Rank.Response;
import com.zhuoyou.plugin.running.baas.RankZan;
import com.zhuoyou.plugin.running.baas.SportsReport;
import com.zhuoyou.plugin.running.baas.TopicData;
import com.zhuoyou.plugin.running.baas.UploadGpsSport;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.bean.Notice;
import com.zhuoyou.plugin.running.database.MyDevOpenHelper;
import com.zhuoyou.plugin.running.receiver.AlarmReceiver;
import com.zhuoyou.plugin.running.tools.JsonUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.WaterAlarmUtils;
import java.util.Calendar;
import java.util.List;

public class TheApp extends MultiDexApplication {
    public static final String ACTION_OPEN_ACTIVITY = "com.droi.droihealth.ACTION_OPEN_ACTIVITY";
    public static final String ACTION_OPEN_APP = "com.droi.droihealth.ACTION_OPEN_APP";
    public static final String ACTION_OPEN_WEBVIEW = "com.droi.droihealth.ACTION_OPEN_WEBVIEW";
    public static final String QQ_APPID = "1102927580";
    public static final String QQ_KEY = "DpaQCQr9RyBtCTN7";
    private static final String TAG = TheApp.class.getSimpleName();
    public static final String TWITTER_KEY = "6lZ2gGhbayw2vtsyftbyAjDjS";
    public static final String TWITTER_SECRET = "KE0WEudqpz2LORtgPehpcUEyqeqYO8bsvZQnDw1yVEJzfV6zeO";
    public static final String WEIBO_APPID = "1690816199";
    public static final String WEIBO_KEY = "8386535dc84a9760901a61cc23782fc7";
    public static final String WEIXIN_APPID = "wx9e68ecf43e6b8493";
    public static final String WEIXIN_KEY = "90aeacf25a411cc51265c0161803daa5";
    private static DaoSession daoSession;
    private static TheApp mInstance;
    private static RequestQueue mRequestQueue;
    private BroadcastReceiver mReceiver = new C18622();

    class C18611 extends DroiMessageHandler {
        C18611() {
        }

        public void onHandleCustomMessage(Context context, final String message) {
            new Handler(TheApp.this.getMainLooper()).post(new Runnable() {
                public void run() {
                    Log.i("zhuqichao", "Custom Msg:" + JsonUtils.formatJson(message));
                }
            });
        }
    }

    class C18622 extends BroadcastReceiver {
        C18622() {
        }

        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent().setFlags(335544320);
            Bundle bundle = intent.getBundleExtra("bundle");
            i.putExtra("bundle", bundle);
            String action = intent.getAction();
            Object obj = -1;
            switch (action.hashCode()) {
                case 202299596:
                    if (action.equals(TheApp.ACTION_OPEN_ACTIVITY)) {
                        obj = 1;
                        break;
                    }
                    break;
                case 730179196:
                    if (action.equals(TheApp.ACTION_OPEN_WEBVIEW)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 1894164004:
                    if (action.equals(TheApp.ACTION_OPEN_APP)) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    TheApp.this.startActivity(i.setClass(context, SplashActivity.class));
                    return;
                case 1:
                    String action2 = bundle.getString("action");
                    TheApp.this.startActivity(i.setClass(context, SplashActivity.class));
                    return;
                case 2:
                    String url = bundle.getString("url");
                    if (!TextUtils.isEmpty(url)) {
                        TheApp.this.startActivity(intent.setData(Uri.parse(url)).setAction("android.intent.action.VIEW"));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    static {
        PlatformConfig.setWeixin(WEIXIN_APPID, WEIXIN_KEY);
        PlatformConfig.setSinaWeibo(WEIBO_APPID, WEIBO_KEY);
        PlatformConfig.setQQZone(QQ_APPID, QQ_KEY);
        PlatformConfig.setTwitter(TWITTER_KEY, TWITTER_SECRET);
        Config.REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    }

    public void onCreate() {
        Log.i("zhuqichao", "TheApp.onCreate()");
        super.onCreate();
        mInstance = this;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        initDatabase();
        BtManagerService.init(this);
        initBaaS();
        initAlarm();
        WaterAlarmUtils.initWaterAlarm(SPUtils.isWaterAlarmOpen());
        initReceiver();
        UMShareAPI.get(this);
        SDKInitializer.initialize(this);
    }

    public void initBaaS() {
        Log.i("zhuqichao", "TheApp.initBaaS()");
        registDroiObject();
        DroiUser.setAutoAnonymousUser(false);
        Core.initialize(this);
        DroiFeedback.initialize(this);
        DroiUpdate.initialize(this);
        DroiAnalytics.initialize(this);
        DroiAnalytics.setCrashReport(true);
        initPush();
    }

    private void registDroiObject() {
        DroiObject.registerCustomClass(User.class);
        DroiObject.registerCustomClass(GpsSportBean.class);
        DroiObject.registerCustomClass(GpsPointBean.class);
        DroiObject.registerCustomClass(SportBean.class);
        DroiObject.registerCustomClass(SleepBean.class);
        DroiObject.registerCustomClass(WeightBean.class);
        DroiObject.registerCustomClass(HeartBean.class);
        DroiObject.registerCustomClass(RankInfo.class);
        DroiObject.registerCustomClass(Request.class);
        DroiObject.registerCustomClass(Response.class);
        DroiObject.registerCustomClass(FishRank.RankInfo.class);
        DroiObject.registerCustomClass(FishRank.Request.class);
        DroiObject.registerCustomClass(FishRank.Response.class);
        DroiObject.registerCustomClass(RankZan.class);
        DroiObject.registerCustomClass(DeleteGpsSport.Request.class);
        DroiObject.registerCustomClass(DeleteGpsSport.Response.class);
        DroiObject.registerCustomClass(UploadGpsSport.Request.class);
        DroiObject.registerCustomClass(UploadGpsSport.Response.class);
        DroiObject.registerCustomClass(FishGameScore.class);
        DroiObject.registerCustomClass(CommentData.class);
        DroiObject.registerCustomClass(SportsReport.Request.class);
        DroiObject.registerCustomClass(SportsReport.Response.class);
        DroiObject.registerCustomClass(ActivityRank.class);
        DroiObject.registerCustomClass(CommentInfo.class);
        DroiObject.registerCustomClass(CommentRequest.class);
        DroiObject.registerCustomClass(CommentResponse.class);
        DroiObject.registerCustomClass(CommentZan.class);
        DroiObject.registerCustomClass(CloudAPI.Request.class);
        DroiObject.registerCustomClass(CloudAPI.Response.class);
        DroiObject.registerCustomClass(TopicData.class);
    }

    private void initPush() {
        DroiPush.initialize(this);
        DroiPush.setMessageHandler(new C18611());
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_OPEN_APP);
        filter.addAction(ACTION_OPEN_ACTIVITY);
        filter.addAction(ACTION_OPEN_WEBVIEW);
        registerReceiver(this.mReceiver, filter);
    }

    public static TheApp getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (TheApp.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(mInstance);
                }
            }
        }
        return mRequestQueue;
    }

    private void initDatabase() {
        daoSession = new DaoMaster(new MyDevOpenHelper(this, "com.droi.droihealth", null).getWritableDatabase()).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private void initAlarm() {
        Intent intent = new Intent(AlarmReceiver.ACTION_RUN_TASK);
        intent.setClass(this, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.KEY_TASK, 4097);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        AlarmManager am = (AlarmManager) getSystemService("alarm");
        am.cancel(pi);
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 22);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        am.setRepeating(0, calendar.getTimeInMillis(), LogBuilder.MAX_INTERVAL, pi);
    }

    public static boolean isCurrentApp() {
        String packageName = getContext().getPackageName();
        List<RunningAppProcessInfo> appProcesses = ((ActivityManager) getContext().getSystemService("activity")).getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == 100) {
                return true;
            }
        }
        return false;
    }

    public static void buildCustomNotification(Context context, Notice notice) {
        if (notice != null) {
            boolean z;
            NotificationManager nm = (NotificationManager) context.getSystemService(MessageObj.CATEGORY_NOTI);
            Builder builder = new Builder(context);
            if (notice.isSound()) {
                builder.setDefaults(builder.build().defaults | 1);
            }
            if (notice.isLight()) {
                builder.setDefaults(builder.build().defaults | 4);
            }
            if (notice.isVibrate()) {
                builder.setDefaults(builder.build().defaults | 2);
            }
            builder.setSmallIcon(C1680R.mipmap.ic_launcher);
            builder.setShowWhen(true);
            builder.setContentTitle(notice.getTitle());
            builder.setContentText(notice.getContent());
            builder.setTicker(notice.getTicker());
            builder.setAutoCancel(notice.isCancel());
            if (notice.isClean()) {
                z = false;
            } else {
                z = true;
            }
            builder.setOngoing(z);
            builder.setPriority(2);
            Intent intent = new Intent().putExtra("bundle", notice.getBundle());
            if (notice.getAction() > 0) {
                if (notice.getAction() == 1) {
                    intent.setAction(ACTION_OPEN_APP);
                } else if (notice.getAction() == 2) {
                    intent.setAction(ACTION_OPEN_ACTIVITY);
                } else if (notice.getAction() == 3) {
                    intent.setAction(ACTION_OPEN_WEBVIEW);
                }
                builder.setContentIntent(PendingIntent.getBroadcast(mInstance, 0, intent, 0));
            }
            nm.notify(notice.getId(), builder.build());
        }
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
