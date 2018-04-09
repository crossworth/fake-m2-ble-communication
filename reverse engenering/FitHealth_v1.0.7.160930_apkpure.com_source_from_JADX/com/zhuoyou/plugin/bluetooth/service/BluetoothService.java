package com.zhuoyou.plugin.bluetooth.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog.Calls;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import com.amap.api.maps.model.WeightedLatLng;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.mcube.lib.ped.PedometerService;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.attach.PluginManager;
import com.zhuoyou.plugin.bluetooth.connection.BluetoothManager;
import com.zhuoyou.plugin.bluetooth.connection.BtProfileReceiver;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import com.zhuoyou.plugin.bluetooth.data.AppList;
import com.zhuoyou.plugin.bluetooth.data.CallMessageBody;
import com.zhuoyou.plugin.bluetooth.data.IgnoreList;
import com.zhuoyou.plugin.bluetooth.data.MapConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.NoDataException;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.data.SmsController;
import com.zhuoyou.plugin.bluetooth.data.SmsMessageBody;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.product.ProductManager;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.firmware.FirmwareService;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.weather.WeatherTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class BluetoothService extends Service {
    private static final String LOG_TAG = "BluetoothService";
    private static final String[] SELECT_LIST = new String[]{WXApp.WXAPP_PACKAGE_NAME};
    public static final boolean mIsNeedStartBTMapService = true;
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private static BluetoothService sInstance = null;
    private static NotificationService sNotificationService = null;
    private BluetoothAdapter btAdapt;
    int caloriesAddSport = 0;
    private final BroadcastReceiver mBTManagerReceiver = new C12063();
    private BTMapService mBTMapService = null;
    private final BluetoothManager mBluetoothManager = new BluetoothManager(sContext);
    private final ContentObserver mCallLogObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean selfChange) {
            Log.i("hello", "call phone changed = " + selfChange);
            Log.i("hello", "call phone changed = " + BluetoothService.this.getMissedCallCount());
            if (BluetoothService.this.getMissedCallCount() == 0) {
                BluetoothService.this.sendReadMissedCallData();
                BluetoothService.this.noticeBleReadCall();
            }
        }
    };
    private CallService mCallService = null;
    private int mCalories;
    private final BroadcastReceiver mInstallBroadcast = new C12074();
    private boolean mIsCallServiceActive = false;
    private boolean mIsConnectionStatusIconShow = false;
    private boolean mIsMainServiceActive = false;
    private boolean mIsSmsServiceActive = false;
    private String mOldType = null;
    private final BroadcastReceiver mReceiver = new C12032();
    private List<RunningItem> mRunningDays = new ArrayList();
    private SmsService mSmsService = null;
    private int mSteps;
    private SystemNotificationService mSystemNotificationService = null;
    private float mTargetStep;

    class C12032 extends BroadcastReceiver {
        C12032() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BtProfileReceiver.NEED_CONNECT_ACTION_STRING) && !BluetoothService.this.isConnected()) {
                BluetoothService.this.mBluetoothManager.connectToRemoteDevice();
            }
        }
    }

    class C12063 extends BroadcastReceiver {

        class C12041 implements OnClickListener {
            C12041() {
            }

            public void onClick(DialogInterface dialog, int which) {
                BluetoothService.this.stopService(new Intent(BluetoothService.this.getApplicationContext(), PedometerService.class));
                Tools.setPhonePedState(false);
                dialog.dismiss();
            }
        }

        class C12052 implements OnClickListener {
            C12052() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Tools.setOpenDialogState(true);
                dialog.dismiss();
            }
        }

        C12063() {
        }

        public void onReceive(Context context, Intent intent) {
            if (BluetoothManager.BT_BROADCAST_ACTION.equals(intent.getAction())) {
                int extraType = intent.getIntExtra(BluetoothManager.EXTRA_TYPE, 0);
                byte[] mIncomingMessageBuffer = intent.getByteArrayExtra("EXTRA_DATA");
                switch (extraType) {
                    case 1:
                        Intent connectIntent = new Intent();
                        connectIntent.setAction("com.zhuoyou.running.connect.success");
                        BluetoothService.sContext.sendBroadcast(connectIntent);
                        BluetoothService.this.queryData();
                        int targetStep = Tools.getPersonalGoal().getStep();
                        BluetoothService.this.updateConnectionStatus(false, ((RunningItem) BluetoothService.this.mRunningDays.get(BluetoothService.this.mRunningDays.size() - 1)).getSteps(), ((RunningItem) BluetoothService.this.mRunningDays.get(BluetoothService.this.mRunningDays.size() - 1)).getCalories(), targetStep);
                        if (BluetoothService.this.mBluetoothManager.isBTConnected()) {
                            String name = Util.getProductName(BtProfileReceiver.getRemoteDevice().getName());
                            BluetoothService.this.loadInstalledPlugIn(name);
                            if (name.equals("TJ01")) {
                                WeatherTools.newInstance().getCurrWeather();
                            }
                        }
                        Util.setLatestDeviceType(context, false);
                        Util.updateLatestConnectDeviceAddress(context, BtProfileReceiver.getRemoteDevice().getAddress());
                        String BLEaddress = Util.getLLatsetConnectDeviceAddress(BluetoothService.sContext);
                        if (!BLEaddress.equals("")) {
                            Intent DisconnectBLEDevicesIntent = new Intent(BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE);
                            DisconnectBLEDevicesIntent.putExtra("BINDED_DEVICE_ADDRESS", BLEaddress);
                            BluetoothService.this.sendBroadcast(DisconnectBLEDevicesIntent);
                            Log.i("666", "BLEaddress=" + BLEaddress);
                        }
                        BluetoothService.this.startService(new Intent(context, FirmwareService.class));
                        Intent intent2 = new Intent("com.tyd.plugin.receiver.sendmsg");
                        intent2.putExtra("plugin_cmd", 5);
                        intent2.putExtra("plugin_content", "0");
                        BluetoothService.this.sendBroadcast(intent2);
                        if (true == Tools.getPhonePedState() && !Tools.getOpenDialogState()) {
                            Builder builder = new Builder(BluetoothService.this);
                            builder.setTitle(R.string.alert_title);
                            builder.setMessage(R.string.close_phone_steps);
                            builder.setPositiveButton(R.string.ok, (OnClickListener) new C12041());
                            builder.setNegativeButton(R.string.cancle, (OnClickListener) new C12052());
                            builder.setCancelable(Boolean.valueOf(false));
                            CustomAlertDialog dialog = builder.create();
                            dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                            dialog.show();
                            return;
                        }
                        return;
                    case 2:
                        Intent lostConnectIntent = new Intent();
                        lostConnectIntent.setAction("com.zhuoyou.running.connect.failed");
                        BluetoothService.sContext.sendBroadcast(lostConnectIntent);
                        BluetoothService.this.queryData();
                        int targetSteps = Tools.getPersonalGoal().getStep();
                        BluetoothService.this.updateConnectionStatus(false, ((RunningItem) BluetoothService.this.mRunningDays.get(BluetoothService.this.mRunningDays.size() - 1)).getSteps(), ((RunningItem) BluetoothService.this.mRunningDays.get(BluetoothService.this.mRunningDays.size() - 1)).getCalories(), targetSteps);
                        return;
                    case 4:
                        try {
                            BluetoothService.this.parseReadBuffer(mIncomingMessageBuffer);
                            return;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    case 5:
                        Log.i(BluetoothService.LOG_TAG, "MAP REQUEST ARRIVE");
                        try {
                            if (Integer.valueOf(new String(intent.getByteArrayExtra("EXTRA_DATA"), "UTF-8").split(" ")[0]).intValue() == 8) {
                                BluetoothService.this.sendMapResult(String.valueOf(1));
                                if (BluetoothService.this.mBTMapService == null) {
                                    BluetoothService.this.startMapService();
                                    return;
                                }
                                return;
                            }
                            return;
                        } catch (UnsupportedEncodingException e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 6:
                        Intent disconnectIntent = new Intent();
                        disconnectIntent.setAction("com.zhuoyou.running.connect.failed");
                        BluetoothService.sContext.sendBroadcast(disconnectIntent);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    class C12074 extends BroadcastReceiver {
        C12074() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                String packageName = intent.getData().getSchemeSpecificPart();
                String sdcard = Environment.getExternalStorageDirectory().getPath();
                if (Environment.getExternalStorageState().equals("mounted")) {
                    sdcard = Environment.getExternalStorageDirectory().getPath();
                } else {
                    sdcard = Environment.getDataDirectory().getPath();
                }
                new File(sdcard + "/" + packageName + ".apk").delete();
                PluginManager.getInstance().updatePlugList(packageName, true);
            } else if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                PluginManager.getInstance().updatePlugList(intent.getData().getSchemeSpecificPart(), false);
            }
            BluetoothService.this.sendBroadcast(new Intent("com.tyd.plugin.PLUGIN_LIST_REFRESH"));
        }
    }

    private class InitTask extends AsyncTask<String, Void, Boolean> {
        private InitTask() {
        }

        protected Boolean doInBackground(String... params) {
            ProductManager.getInstance();
            PluginManager.getInstance();
            return Boolean.valueOf(true);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        int i = 0;
        super.onCreate();
        Log.i(LOG_TAG, "onCreate()");
        updateConnectionStatus(true, 0, 0, 0);
        sInstance = this;
        this.mIsMainServiceActive = true;
        this.btAdapt = BluetoothAdapter.getDefaultAdapter();
        Map<Object, Object> applist = AppList.getInstance().getAppList();
        if (applist.size() == 0) {
            applist.put(AppList.MAX_APP, Integer.valueOf(3));
            applist.put(Integer.valueOf(3), AppList.BETTRYLOW_APPID);
            applist.put(Integer.valueOf(3), AppList.SMSRESULT_APPID);
            AppList.getInstance().saveAppList(applist);
        }
        if (!applist.containsValue(AppList.BETTRYLOW_APPID)) {
            int max = Integer.parseInt(applist.get(AppList.MAX_APP).toString());
            applist.remove(AppList.MAX_APP);
            max++;
            applist.put(AppList.MAX_APP, Integer.valueOf(max));
            applist.put(Integer.valueOf(max), AppList.BETTRYLOW_APPID);
            AppList.getInstance().saveAppList(applist);
        }
        if (!applist.containsValue(AppList.SMSRESULT_APPID)) {
            max = Integer.parseInt(applist.get(AppList.MAX_APP).toString());
            applist.remove(AppList.MAX_APP);
            max++;
            applist.put(AppList.MAX_APP, Integer.valueOf(max));
            applist.put(Integer.valueOf(max), AppList.SMSRESULT_APPID);
            AppList.getInstance().saveAppList(applist);
        }
        initBluetoothManager();
        registerService();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentfilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentfilter.addDataScheme("package");
        registerReceiver(this.mInstallBroadcast, intentfilter);
        new InitTask().execute(new String[0]);
        HashSet<String> selectList = new HashSet();
        HashSet<String> selectedList = IgnoreList.getInstance().getIgnoreList();
        String[] strArr = SELECT_LIST;
        int length = strArr.length;
        while (i < length) {
            String selectPackage = strArr[i];
            if (!selectedList.contains(selectPackage)) {
                selectList.add(selectPackage);
            }
            i++;
        }
        if (Tools.checkIsFirstEntry(sContext)) {
            IgnoreList.getInstance().saveIgnoreList(selectList);
        }
    }

    public static BluetoothService getInstance() {
        if (sInstance == null) {
            Log.i(LOG_TAG, "getInstance(), BluetoothService is null.");
            startMainService();
        }
        return sInstance;
    }

    private static void startMainService() {
        Log.i(LOG_TAG, "BluetoothService()");
        sContext.startService(new Intent(sContext, BluetoothService.class));
    }

    public void onDestroy() {
        this.mIsMainServiceActive = false;
        unregisterReceiver(this.mReceiver);
        unregisterReceiver(this.mSystemNotificationService);
        this.mSystemNotificationService = null;
        getContentResolver().unregisterContentObserver(this.mCallLogObserver);
        stopMapService();
        destoryBluetoothManager();
        unregisterReceiver(this.mInstallBroadcast);
        startMainService();
    }

    public BluetoothManager getBluetoothManager() {
        return this.mBluetoothManager;
    }

    public void connectToRemoteDevice(BluetoothDevice remoteDevice) {
        this.mBluetoothManager.connectToAppointedDevice(remoteDevice);
    }

    public void disconnectRemoteDevice() {
        this.mBluetoothManager.disconnectRemoteDevice();
    }

    public boolean isConnected() {
        return this.mBluetoothManager.isBTConnected();
    }

    public void updateConnectionStatus(boolean isCrash, int notificationStep, int notificationCalories, int targetStep) {
        boolean isShowNotification;
        if (PreferenceData.isShowConnectionStatus() && this.mBluetoothManager.isBTConnected()) {
            isShowNotification = true;
        } else {
            isShowNotification = false;
        }
        Log.i(LOG_TAG, "showConnectionStatus(), showNotification=" + isShowNotification);
        NotificationManager manager = (NotificationManager) sContext.getSystemService(MessageObj.CATEGORY_NOTI);
        if (isCrash) {
            manager.cancel(R.string.app_name);
            this.mIsConnectionStatusIconShow = false;
        } else if (isShowNotification) {
            Notification notification = new Notification();
            notification.icon = R.drawable.ic_connected_status;
            notification.tickerText = sContext.getText(R.string.notification_ticker_text);
            Intent localIntent = new Intent();
            localIntent.setClass(sContext, Main.class);
            notification.flags = 2;
            notification.contentView = new RemoteViews(getPackageName(), R.layout.notification_template_base);
            notification.contentIntent = PendingIntent.getActivity(sContext, 0, localIntent, 268435456);
            this.mTargetStep = 0.0f;
            this.mSteps = 0;
            this.mCalories = 0;
            this.mSteps = notificationStep;
            this.mCalories = notificationCalories;
            this.mTargetStep = (float) targetStep;
            updateNotificationRemoteViews(sContext, notification.contentView);
            manager.notify(R.string.app_name, notification);
            this.mIsConnectionStatusIconShow = true;
        } else if (this.mIsConnectionStatusIconShow) {
            manager.cancel(R.string.app_name);
            this.mIsConnectionStatusIconShow = false;
            Log.i(LOG_TAG, "updateConnectionStatus(),  cancel notification id=2131165268");
        }
    }

    public RemoteViews updateNotificationRemoteViews(Context paramContext, RemoteViews paramRemoteViews) {
        Calendar calender = Calendar.getInstance();
        paramRemoteViews.setImageViewResource(R.id.icon, R.drawable.logo);
        if (this.mSteps == 0) {
            paramRemoteViews.setViewVisibility(R.id.currentStep, 8);
            paramRemoteViews.setViewVisibility(R.id.currentCalorie, 8);
            paramRemoteViews.setViewVisibility(R.id.layoutCalorie, 8);
            paramRemoteViews.setViewVisibility(R.id.showStpe, 8);
            paramRemoteViews.setViewVisibility(R.id.showCalorie, 8);
            paramRemoteViews.setViewVisibility(R.id.tv_widget_goal_percent, 8);
            paramRemoteViews.setViewVisibility(R.id.percent_arc_iv, 8);
            paramRemoteViews.setViewVisibility(R.id.percent_color_iv, 8);
            paramRemoteViews.setViewVisibility(R.id.showNo, 0);
        } else {
            paramRemoteViews.setViewVisibility(R.id.showNo, 8);
            paramRemoteViews.setViewVisibility(R.id.currentStep, 0);
            paramRemoteViews.setViewVisibility(R.id.currentCalorie, 0);
            paramRemoteViews.setViewVisibility(R.id.layoutCalorie, 0);
            paramRemoteViews.setViewVisibility(R.id.showStpe, 0);
            paramRemoteViews.setViewVisibility(R.id.showCalorie, 0);
            paramRemoteViews.setViewVisibility(R.id.tv_widget_goal_percent, 0);
            paramRemoteViews.setViewVisibility(R.id.percent_arc_iv, 0);
            paramRemoteViews.setViewVisibility(R.id.percent_color_iv, 0);
            double d = (double) (((float) this.mSteps) / this.mTargetStep);
            if (d < 0.01d) {
                d = 0.01d;
            }
            if (d >= WeightedLatLng.DEFAULT_INTENSITY) {
                d = WeightedLatLng.DEFAULT_INTENSITY;
            }
            NumberFormat.getPercentInstance().setMinimumFractionDigits(0);
            paramRemoteViews.setTextViewText(R.id.currentStep, this.mSteps + "");
            StringBuilder localStringBuilder = new StringBuilder();
            RemoteViews remoteViews = paramRemoteViews;
            remoteViews.setTextViewText(R.id.currentCalorie, String.format("%.0f", new Object[]{Double.valueOf((double) this.mCalories)}));
            remoteViews = paramRemoteViews;
            remoteViews.setTextViewText(R.id.tv_widget_goal_percent, String.format("%.0f", new Object[]{Double.valueOf(100.0d * d)}) + "%");
            Bitmap localBitmap = Bitmap.createBitmap(Util.dip2pixel(paramContext, 47.0f), Util.dip2pixel(paramContext, 47.0f), Config.ARGB_8888);
            Canvas localCanvas = new Canvas(localBitmap);
            Paint localPaint = new Paint();
            localPaint.setAntiAlias(true);
            localPaint.setColor(Color.rgb(244, CustomCmd.CMD_SYNC_PERSONAL_DATA, 36));
            localPaint.setStyle(Style.STROKE);
            localPaint.setStrokeWidth((float) Util.dip2pixel(paramContext, 3.0f));
            localCanvas.drawArc(new RectF((float) Util.dip2pixel(paramContext, 2.0f), (float) Util.dip2pixel(paramContext, 2.0f), (float) Util.dip2pixel(paramContext, 45.0f), (float) Util.dip2pixel(paramContext, 45.0f)), -90.0f, (float) ((int) (360.0d * d)), false, localPaint);
            paramRemoteViews.setImageViewBitmap(R.id.percent_arc_iv, localBitmap);
        }
        return paramRemoteViews;
    }

    String formatTime(int t) {
        return t >= 10 ? "" + t : "0" + t;
    }

    private void initBluetoothManager() {
        this.mBluetoothManager.setupConnection();
        sContext.registerReceiver(this.mBTManagerReceiver, new IntentFilter(BluetoothManager.BT_BROADCAST_ACTION));
    }

    private void destoryBluetoothManager() {
        this.mBluetoothManager.saveData();
        this.mBluetoothManager.removeConnection();
        sContext.unregisterReceiver(this.mBTManagerReceiver);
    }

    private void registerService() {
        Log.i(LOG_TAG, "registerService()");
        IntentFilter filter = new IntentFilter();
        filter.addAction(BtProfileReceiver.NEED_CONNECT_ACTION_STRING);
        registerReceiver(this.mReceiver, filter);
        startSystemNotificationService();
        startMapService();
        if (PreferenceData.isSmsServiceEnable()) {
            startSmsService();
        }
        if (PreferenceData.isCallServiceEnable()) {
            getContentResolver().registerContentObserver(Calls.CONTENT_URI, true, this.mCallLogObserver);
            startCallService();
        }
    }

    public void startSystemNotificationService() {
        this.mSystemNotificationService = new SystemNotificationService();
        registerReceiver(this.mSystemNotificationService, new IntentFilter("android.intent.action.BATTERY_LOW"));
        registerReceiver(this.mSystemNotificationService, new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED"));
        registerReceiver(this.mSystemNotificationService, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        String action = SmsService.SMS_ACTION;
        IntentFilter filtersms = new IntentFilter();
        filtersms.addAction(action);
        registerReceiver(this.mSystemNotificationService, filtersms);
    }

    void startMapService() {
        Log.i(LOG_TAG, "startMapService()");
        if (!this.mIsMainServiceActive) {
            startMainService();
        }
        if (this.mBTMapService == null) {
            this.mBTMapService = new BTMapService();
            registerReceiver(this.mBTMapService, new IntentFilter(MapConstants.BT_MAP_BROADCAST_ACTION));
        }
    }

    void stopMapService() {
        Log.i(LOG_TAG, "stopMapService()");
        if (this.mBTMapService != null) {
            new SmsController(sContext).clearDeletedMessage();
            unregisterReceiver(this.mBTMapService);
            this.mBTMapService = null;
        }
    }

    public void startSmsService() {
        Log.i(LOG_TAG, "startSmsService()");
        if (!this.mIsMainServiceActive) {
            startMainService();
        }
        if (this.mSmsService == null) {
            this.mSmsService = new SmsService();
        }
        IntentFilter filter = new IntentFilter("com.mtk.btnotification.SMS_RECEIVED");
        filter.addAction("com.tyd.btsecretary.SMS_UNREAD_TO_READ");
        registerReceiver(this.mSmsService, filter);
        this.mIsSmsServiceActive = true;
    }

    public void stopSmsService() {
        Log.i(LOG_TAG, "stopSmsService()");
        if (this.mSmsService != null) {
            unregisterReceiver(this.mSmsService);
            this.mSmsService = null;
        }
        this.mIsSmsServiceActive = false;
    }

    public void startCallService() {
        Log.i(LOG_TAG, "startCallService()");
        if (!this.mIsMainServiceActive) {
            startMainService();
        }
        if (this.mCallService == null) {
            this.mCallService = new CallService(sContext);
        }
        ((TelephonyManager) sContext.getSystemService("phone")).listen(this.mCallService, 32);
        this.mIsCallServiceActive = true;
    }

    public void stopCallService() {
        Log.i(LOG_TAG, "stopCallService()");
        if (this.mCallService != null) {
            ((TelephonyManager) sContext.getSystemService("phone")).listen(this.mCallService, 0);
            this.mCallService = null;
        }
        this.mIsCallServiceActive = false;
    }

    public void startNotificationService() {
        Log.i(LOG_TAG, "startNotifiService()");
        if (!this.mIsMainServiceActive) {
            startMainService();
        }
    }

    public void stopNotificationService() {
        Log.i(LOG_TAG, "stopNotifiService()");
    }

    public static void setNotificationService(NotificationService notificationService) {
        sNotificationService = notificationService;
    }

    public static void clearNotificationService() {
        sNotificationService = null;
    }

    public static boolean isNotificationServiceActived() {
        return sNotificationService != null;
    }

    private int getMissedCallCount() {
        StringBuilder queryStr = new StringBuilder("type = ");
        queryStr.append(3);
        queryStr.append(" AND new = 1");
        Log.i(LOG_TAG, "getMissedCallCount(), query string=" + queryStr);
        Cursor cur = sContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{"_id"}, queryStr.toString(), null, MapConstants.DEFAULT_SORT_ORDER);
        if (cur == null) {
            return 0;
        }
        int missedCallCount = cur.getCount();
        cur.close();
        return missedCallCount;
    }

    private void sendReadMissedCallData() {
        MessageHeader header = new MessageHeader();
        header.setCategory("call");
        header.setSubType(MessageObj.SUBTYPE_MISSED_CALL);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        int timestamp = Util.getUtcTime(Calendar.getInstance().getTimeInMillis());
        CallMessageBody body = new CallMessageBody();
        body.setSender("");
        body.setNumber("");
        body.setContent("");
        body.setMissedCallCount(0);
        body.setTimestamp(timestamp);
        MessageObj callMessageData = new MessageObj();
        callMessageData.setDataHeader(header);
        callMessageData.setDataBody(body);
        getInstance().sendCallMessage(callMessageData);
    }

    private void noticeBleReadCall() {
        Log.i(LOG_TAG, "noticeBleReadCall");
        sendBroadcast(new Intent(BleManagerService.ACTION_BATTERY_READ));
    }

    public void sendNotiMessage(MessageObj notiMessage) {
        Log.i(LOG_TAG, "sendNotiMessage(),  notiMessageId=" + notiMessage.getDataHeader().getMsgId());
        sendData(notiMessage);
    }

    public void sendNotiMessageByData(byte[] data) {
        Log.i(LOG_TAG, "sendNotiMessageByData(),  data=" + data.toString());
        this.mBluetoothManager.sendData(data);
    }

    public void sendSystemNotiMessage(MessageObj notiMessage) {
        Log.i(LOG_TAG, "sendOtherNotiMessage(),  OtherNotiMessageID=" + notiMessage.getDataHeader().getMsgId());
        sendData(notiMessage);
    }

    public void sendSmsMessage(MessageObj smsMessage) {
        Log.i(LOG_TAG, "sendSmsMessage(),  smsMessageId=" + smsMessage.getDataHeader().getMsgId());
        sendData(smsMessage);
    }

    public void sendCallMessage(MessageObj callMessage) {
        Log.i(LOG_TAG, "sendSmsMessage(),  smsMessageId=" + callMessage.getDataHeader().getMsgId());
        sendData(callMessage);
    }

    public void sendMapResult(String result) {
        this.mBluetoothManager.sendMapResult(result);
    }

    public void sendMapDResult(String result) {
        this.mBluetoothManager.sendMapDResult(result);
    }

    public void sendMapData(byte[] data) {
        this.mBluetoothManager.sendMAPData(data);
    }

    private void sendData(MessageObj dataObj) {
        byte[] data = genBytesFromObject(dataObj);
        if (data != null) {
            this.mBluetoothManager.sendData(data);
        }
    }

    public static byte[] genBytesFromObject(MessageObj dataObj) {
        Log.i(LOG_TAG, "genBytesFromObject(), dataObj=" + dataObj);
        if (dataObj == null) {
            return null;
        }
        byte[] bArr = null;
        try {
            return dataObj.genXmlBuff();
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
            return bArr;
        } catch (IllegalStateException e12) {
            e12.printStackTrace();
            return bArr;
        } catch (IOException e13) {
            e13.printStackTrace();
            return bArr;
        } catch (XmlPullParserException e14) {
            e14.printStackTrace();
            return bArr;
        } catch (NoDataException e) {
            e.printStackTrace();
            return bArr;
        }
    }

    public String GetRemoteDeviceName() {
        return this.mBluetoothManager.GetRemoteDeviceName();
    }

    private void parseReadBuffer(byte[] mIncomingMessageBuffer) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "ReadData"), true);
        fos.write(mIncomingMessageBuffer);
        fos.close();
        MessageObj mIncomingMessage = new MessageObj();
        MessageHeader mIncomingMessageHeader = new MessageHeader();
        try {
            mIncomingMessage = mIncomingMessage.parseXml(mIncomingMessageBuffer);
            String messageSubType = mIncomingMessage.getDataHeader().getSubType();
            if (messageSubType.equals(MessageObj.SUBTYPE_SMS)) {
                sendSMS(mIncomingMessage);
            } else if (messageSubType.equals(MessageObj.SUBTYPE_MISSED_CALL)) {
                updateMissedCallCountToZero();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    void sendSMS(MessageObj smsMessage) {
        Log.i(LOG_TAG, "sendSmsMessage(),  notiMessageId=" + smsMessage.getDataHeader().getMsgId());
        String address = ((SmsMessageBody) smsMessage.getDataBody()).getNumber();
        String message = smsMessage.getDataBody().getContent();
        if (message == null) {
            message = "\n";
        }
        if (message.equals("")) {
            message = "\n";
        }
        if (!CustomCmd.praser(address, message)) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(SmsController.MESSAGE_STATUS_SEND_ACTION);
            sendIntent.putExtra("ADDRESS", address);
            sendIntent.putExtra("MESSAGE", message);
            sContext.sendBroadcast(sendIntent);
        }
    }

    @SuppressLint({"InlinedApi"})
    private void updateMissedCallCountToZero() {
        ContentValues values = new ContentValues();
        values.put("new", Integer.valueOf(0));
        if (VERSION.SDK_INT >= 14) {
            values.put("is_read", Integer.valueOf(1));
        }
        StringBuilder where = new StringBuilder();
        where.append("new");
        where.append(" = 1 AND ");
        where.append("type");
        where.append(" = ?");
        sContext.getContentResolver().update(Calls.CONTENT_URI, values, where.toString(), new String[]{Integer.toString(3)});
    }

    public void _sendSyncTime() {
        if (this.mBluetoothManager != null) {
            this.mBluetoothManager._sendSyncTime();
        } else {
            Log.e("gchk", "need sync time , mBluetoothManager is null");
        }
    }

    private void loadInstalledPlugIn(String nickname) {
        if (this.mOldType != nickname) {
            PluginManager.release();
        }
        this.mOldType = nickname;
        PluginManager.getInstance().processPlugList(nickname);
    }

    public void queryData() {
        initAddSport(Tools.getDate(0));
        RunningItem runningdate = new RunningItem();
        Cursor c = sContext.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.CALORIES}, "date  = ? AND statistics = ? ", new String[]{today, "1"}, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            runningdate.setCalories(c.getInt(c.getColumnIndex(DataBaseContants.CALORIES)) + this.caloriesAddSport);
            runningdate.setSteps(c.getInt(c.getColumnIndex("steps")));
        } else {
            runningdate.setCalories(0);
            runningdate.setSteps(0);
        }
        c.close();
        this.mRunningDays.add(runningdate);
    }

    void initAddSport(String day) {
        this.caloriesAddSport = 0;
        int calories = 0;
        Cursor cAddSport = sContext.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", DataBaseContants.CALORIES, DataBaseContants.SPORTS_TYPE, "type"}, "date  = ? AND statistics = ?", new String[]{day, "0"}, null);
        cAddSport.moveToFirst();
        if (cAddSport.getCount() > 0) {
            for (int y = 0; y < cAddSport.getCount(); y++) {
                if (cAddSport.getInt(cAddSport.getColumnIndex("type")) == 2 && cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.SPORTS_TYPE)) != 0) {
                    calories += cAddSport.getInt(cAddSport.getColumnIndex(DataBaseContants.CALORIES));
                    this.caloriesAddSport = calories;
                }
                cAddSport.moveToNext();
            }
        }
        cAddSport.close();
    }
}
