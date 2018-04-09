package com.zhuoyou.plugin.firmware;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.services.core.AMapException;
import com.facebook.GraphResponse;
import com.fithealth.running.R;
import com.tencent.stat.DeviceInfo;
import com.zhuoyi.system.statistics.prom.util.StatsPromConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.cloud.GetDataFromNet;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import com.zhuoyou.plugin.cloud.NetUtils;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.download.Util_update.FileManage;
import com.zhuoyou.plugin.download.Util_update.FileManage.FileHolder;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

public class FirmwareService extends Service {
    public static final String ACTION_RECEIVER_DEVICE_INFO = "com.zhouyou.device.receiver.deviceinfo";
    private static final String tag = "FirmwareService";
    private String datFileName = "sample.dat";
    private String device_name = "";
    private DownloadManager downloadManager;
    private boolean downloading = false;
    private long fileId;
    private String fileName = "image.hex";
    private boolean isBleDevice = false;
    private boolean isHexDownloaded = false;
    private IntentFilter mBlueToothFilter;
    private BroadcastReceiver mBlueToothStateReceiver = new C12354();
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private IntentFilter mDeviceFilter;
    private IntentFilter mDownLoadFilter;
    private BroadcastReceiver mDownloadReceiver = new C12365();
    private BroadcastReceiver mGetFirmwareReceiver = new C12343();
    private ThreadGetDownRate mThreadRate;
    private SharedPreferences prefs;
    private GetDataFromNet task;
    private int temp;
    private Firmware ware;

    class C12321 implements OnClickListener {
        C12321() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(FirmwareService.this.mContext, FwUpdateActivity.class);
            intent.putExtra("device_name", FirmwareService.this.device_name);
            intent.putExtra("isBleDevice", FirmwareService.this.isBleDevice);
            intent.setFlags(268435456);
            FirmwareService.this.startActivity(intent);
            dialog.dismiss();
            FirmwareService.this.downloadHex();
        }
    }

    class C12332 implements OnClickListener {
        C12332() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            FirmwareService.this.stopSelf();
        }
    }

    class C12343 extends BroadcastReceiver {
        C12343() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.tyd.bt.device.firmware")) {
                FirmwareService.this.isBleDevice = false;
                String content = intent.getStringExtra("content");
                Log.i("caixinxin", "content = " + content);
                String[] s = content.split("\\|");
                String device_code = s[0];
                String version_code = s[1];
                FirmwareService.this.device_name = device_code;
                int type = Integer.parseInt(s[2]);
                if (NetUtils.getAPNType(context) == -1) {
                    if (type == 1) {
                        Toast.makeText(context, R.string.check_network, 0).show();
                    }
                    FirmwareService.this.stopSelf();
                    return;
                }
                FirmwareHandler mHandler = new FirmwareHandler(type);
                HashMap<String, String> params = new HashMap();
                params.put("dev", device_code);
                params.put(DeviceInfo.TAG_VERSION, version_code);
                if (FirmwareService.this.task == null || FirmwareService.this.task.getStatus() == Status.FINISHED) {
                    FirmwareService.this.task = new GetDataFromNet(NetMsgCode.FIRMWARE_GET_VERSION, mHandler, params, context);
                    FirmwareService.this.task.execute(new Object[]{NetMsgCode.URL});
                }
            } else if (action.equals("com.zhuoyou.running.cancel.firmwear")) {
                FirmwareService.this.downloadManager.remove(new long[]{FirmwareService.this.fileId});
                Editor editor = FirmwareService.this.prefs.edit();
                if (FirmwareService.this.prefs.contains(FirmwareService.this.ware.getCurrentVer())) {
                    editor.remove(FirmwareService.this.ware.getCurrentVer());
                }
                if (FirmwareService.this.prefs.contains(ClientCookie.PATH_ATTR)) {
                    editor.remove(ClientCookie.PATH_ATTR);
                }
                editor.commit();
                FirmwareService.this.stopSelf();
            } else if (action.equals("com.zhuoyou.running.notification.firmwear")) {
                FirmwareService.this.showUpgradeDialog();
            }
        }
    }

    class C12354 extends BroadcastReceiver {
        C12354() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FirmwareService.ACTION_RECEIVER_DEVICE_INFO)) {
                Log.i(FirmwareService.tag, "receiver ACTION_RECEIVER_DEVICE_INFO");
                FirmwareService.this.isBleDevice = true;
                String version = intent.getStringExtra("device_version");
                Log.i("BluetoothLeService", "device.version:" + version);
                int slitIndex = version.lastIndexOf("_");
                if (slitIndex > 1 && slitIndex < version.length()) {
                    FirmwareService.this.device_name = version.substring(0, slitIndex);
                    String deviceVer = version.substring(slitIndex + 1);
                    if (NetUtils.getAPNType(context) == -1) {
                        if (1 == 1) {
                            Toast.makeText(context, R.string.check_network, 0).show();
                        }
                        FirmwareService.this.stopSelf();
                        return;
                    }
                    FirmwareHandler mHandler = new FirmwareHandler(1);
                    HashMap<String, String> params = new HashMap();
                    params.put("dev", FirmwareService.this.device_name);
                    params.put(DeviceInfo.TAG_VERSION, deviceVer);
                    if (FirmwareService.this.task == null || FirmwareService.this.task.getStatus() == Status.FINISHED) {
                        FirmwareService.this.task = new GetDataFromNet(NetMsgCode.FIRMWARE_GET_VERSION, mHandler, params, context);
                        Log.i(FirmwareService.tag, "device_name:" + FirmwareService.this.device_name + ",ver:" + deviceVer);
                        FirmwareService.this.task.execute(new Object[]{NetMsgCode.URL});
                    }
                }
            }
        }
    }

    class C12365 extends BroadcastReceiver {
        C12365() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.DOWNLOAD_COMPLETE")) {
                Log.i("caixinxin", "mDownloadReceiver download complete");
                long downId = intent.getLongExtra("extra_download_id", -1);
                FirmwareService.this.downloading = false;
                try {
                    FirmwareService.this.queryDownloadStatus();
                } catch (Exception e) {
                }
                if (downId == FirmwareService.this.prefs.getLong(FirmwareService.this.ware.getCurrentVer() + 1, -1)) {
                    Log.i("caixinxin", "download successful");
                    try {
                        FirmwareService.this.prefs.edit().putString("path_dat", new File(new URI(FirmwareService.this.downloadManager.getUriForDownloadedFile(downId).toString())).getAbsolutePath()).commit();
                    } catch (URISyntaxException e2) {
                        e2.printStackTrace();
                    }
                    FirmwareService.this.sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                    FirmwareService.this.stopSelf();
                }
                if (downId == FirmwareService.this.prefs.getLong(FirmwareService.this.ware.getCurrentVer(), -1) && TextUtils.isEmpty(FirmwareService.this.ware.getDatFileUrl())) {
                    FirmwareService.this.prefs.edit().putString("path_dat", "").commit();
                    Log.i("chenxin", "download successful no dat:" + FirmwareService.this.prefs.getString(ClientCookie.PATH_ATTR, ""));
                    FirmwareService.this.sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                    FirmwareService.this.stopSelf();
                }
            } else if (!action.equals("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED") && action.equals("extra_click_download_ids")) {
            }
        }
    }

    class C12376 implements Runnable {
        C12376() {
        }

        public void run() {
            FirmwareService.this.downLoadDataFile(FirmwareService.this.ware.getDatFileUrl());
        }
    }

    private class FirmwareHandler extends Handler {
        private int mType;

        public FirmwareHandler(int type) {
            this.mType = type;
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    if (FirmwareService.this.isBleDevice) {
                        FirmwareService.this.fileName = "image.hex";
                    }
                    Tools.setFirmwear(true);
                    if (MineFragment.mHandler != null) {
                        MineFragment.mHandler.sendEmptyMessage(MineFragment.UPDATA_RED_POINT);
                    }
                    FirmwareService.this.ware = (Firmware) msg.obj;
                    String sdcardPath = FileManage.getSDPath();
                    FileHolder fileHolder = FileManage.readSDCardSpace();
                    if (sdcardPath == null) {
                        if (this.mType == 1) {
                            Toast.makeText(FirmwareService.this.mContext, R.string.please_insert, 0).show();
                        }
                        FirmwareService.this.stopSelf();
                        return;
                    } else if (fileHolder != null && fileHolder.availSpace < 10240) {
                        if (this.mType == 1) {
                            Toast.makeText(FirmwareService.this.mContext, R.string.space_not_enough, 0).show();
                        }
                        FirmwareService.this.stopSelf();
                        return;
                    } else if (FirmwareService.this.isTopActivity()) {
                        FirmwareService.this.showUpgradeDialog();
                        return;
                    } else {
                        FirmwareService.this.showNotification();
                        return;
                    }
                case 404:
                    Tools.setFirmwear(false);
                    if (this.mType == 1) {
                        Tools.setFirmwear(false);
                        Tools.makeToast(FirmwareService.this.getResources().getString(R.string.firmware_upgrade_none));
                    }
                    FirmwareService.this.stopSelf();
                    return;
                default:
                    return;
            }
        }
    }

    class ThreadGetDownRate extends Thread {
        ThreadGetDownRate() {
        }

        public void run() {
            while (FirmwareService.this.downloading) {
                try {
                    sleep(500);
                    int i = 0;
                    try {
                        i = FirmwareService.this.queryDownloadStatus();
                        Log.i("hph", "rate===" + i);
                    } catch (Exception e) {
                        Log.i("hph", "ThreadGetDownRate catch Exception e");
                    }
                    FirmwareService.this.sendBroadCast(i);
                } catch (InterruptedException e2) {
                    Log.i("hph", "ThreadGetDownRate catch InterruptedException e");
                }
            }
        }
    }

    private boolean isTopActivity() {
        List<RunningTaskInfo> tasksInfo = ((ActivityManager) getSystemService("activity")).getRunningTasks(1);
        String packageName = this.mContext.getPackageName();
        if (tasksInfo.size() <= 0 || !packageName.equals(((RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName())) {
            return false;
        }
        return true;
    }

    private void showUpgradeDialog() {
        if (this.ware.getFileUrl().contains(".bin") || this.ware.getFileUrl().contains(".BIN")) {
            this.fileName = "image.bin";
        } else {
            this.fileName = "image.hex";
        }
        String path_bin = FileManage.getSDPath() + Constants.DownloadFirmwarePath + this.fileName;
        String path_dat = FileManage.getSDPath() + Constants.DownloadFirmwarePath + this.datFileName;
        Log.i("hph", "path_bin=" + path_bin);
        Log.i("hph", "path_dat=" + path_dat);
        File f = new File(path_bin);
        File f_dat = new File(path_dat);
        Log.i("hph", "f.exists()==" + f.exists());
        if (f.exists()) {
            f.delete();
        }
        Log.i("hph", "f_dat.exists()==" + f_dat.exists());
        if (f_dat.exists()) {
            f_dat.delete();
        }
        Editor editor = this.prefs.edit();
        if (this.prefs.contains(this.ware.getCurrentVer())) {
            editor.remove(this.ware.getCurrentVer());
        }
        if (this.prefs.contains(ClientCookie.PATH_ATTR)) {
            editor.remove(ClientCookie.PATH_ATTR);
        }
        editor.commit();
        Builder builder = new Builder(this.mContext);
        builder.setTitle((int) R.string.alert_title);
        builder.setMessage((int) R.string.upgrade_alert);
        builder.setPositiveButton((int) R.string.upgrade, new C12321());
        builder.setNegativeButton((int) R.string.upgrade_none, new C12332());
        builder.setCancelable(Boolean.valueOf(false));
        CustomAlertDialog dialog = builder.create();
        dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
        dialog.show();
    }

    private void showNotification() {
        NotificationManager NCmanager = (NotificationManager) getSystemService(MessageObj.CATEGORY_NOTI);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setShowWhen(true);
        builder.setContentTitle(getString(R.string.firmwear_notiy_title));
        builder.setContentText(getString(R.string.firmwear_notiy_message));
        builder.setTicker(getString(R.string.firmwear_notiy_title));
        builder.setAutoCancel(true);
        Intent intent = new Intent(this.mContext, Main.class);
        intent.putExtra("firmwear", true);
        intent.setFlags(335544320);
        builder.setContentIntent(PendingIntent.getActivity(this.mContext, 0, intent, 268435456));
        builder.setDefaults(-1);
        NCmanager.notify(0, builder.build());
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        initValue();
        initIntentFilter();
        registerBc();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        this.downloading = false;
        this.mThreadRate = null;
        unRegisterBc();
    }

    private void initValue() {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.downloadManager = (DownloadManager) getSystemService("download");
    }

    private void initIntentFilter() {
        this.mDeviceFilter = new IntentFilter();
        this.mDeviceFilter.addAction("com.tyd.bt.device.firmware");
        this.mDeviceFilter.addAction("com.zhuoyou.running.cancel.firmwear");
        this.mDeviceFilter.addAction("com.zhuoyou.running.notification.firmwear");
        this.mDownLoadFilter = new IntentFilter();
        this.mDownLoadFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
        this.mDownLoadFilter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
        this.mDownLoadFilter.addAction("extra_click_download_ids");
        this.mBlueToothFilter = new IntentFilter();
        this.mBlueToothFilter.addAction(ACTION_RECEIVER_DEVICE_INFO);
    }

    private void registerBc() {
        registerReceiver(this.mGetFirmwareReceiver, this.mDeviceFilter);
        registerReceiver(this.mDownloadReceiver, this.mDownLoadFilter);
        registerReceiver(this.mBlueToothStateReceiver, this.mBlueToothFilter);
    }

    private void unRegisterBc() {
        unregisterReceiver(this.mGetFirmwareReceiver);
        unregisterReceiver(this.mDownloadReceiver);
        unregisterReceiver(this.mBlueToothStateReceiver);
    }

    private void downloadHex() {
        Log.i("caixinxin", "downloadBin");
        if (this.prefs.contains(this.ware.getCurrentVer())) {
            Log.i("caixinxin", "downloadBin2");
            try {
                queryDownloadStatus();
                return;
            } catch (Exception e) {
                Log.i("hph", "catch ");
                e.printStackTrace();
                return;
            }
        }
        if (this.ware.getFileUrl().contains(".bin") || this.ware.getFileUrl().contains(".BIN")) {
            this.fileName = "image.bin";
        } else {
            this.fileName = "image.hex";
        }
        Log.i("chenxin", "fileUrl:" + this.ware.getFileUrl());
        Request request = new Request(Uri.parse(this.ware.getFileUrl()));
        request.setAllowedNetworkTypes(3);
        request.setAllowedOverRoaming(false);
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(2);
        request.setDestinationInExternalPublicDir(Constants.DownloadFirmwarePath, this.fileName);
        this.fileId = this.downloadManager.enqueue(request);
        Log.i("hph", "bin fileId==" + this.fileId);
        this.prefs.edit().putLong(this.ware.getCurrentVer(), this.fileId).commit();
        this.downloading = true;
        this.mThreadRate = new ThreadGetDownRate();
        this.mThreadRate.start();
    }

    private void downloadDat() {
        String url = this.ware.getDatFileUrl();
        Log.i("hph", "data url=" + url);
        if (!TextUtils.isEmpty(url)) {
            Request request = new Request(Uri.parse(url));
            request.setAllowedNetworkTypes(3);
            request.setAllowedOverRoaming(false);
            request.setVisibleInDownloadsUi(false);
            request.setNotificationVisibility(2);
            request.setDestinationInExternalPublicDir(Constants.DownloadFirmwarePath, this.datFileName);
            this.fileId = this.downloadManager.enqueue(request);
            this.prefs.edit().putLong(this.ware.getCurrentVer() + 1, this.fileId).commit();
            Log.i("hph", "fileId=" + this.fileId);
        }
    }

    private int queryDownloadStatus() throws Exception {
        Log.i("caixinxin", "queryDownloadStatus");
        Log.i("hph", "ware.getCurrentVer()=" + this.ware.getCurrentVer());
        long id = this.prefs.getLong(this.ware.getCurrentVer(), -1);
        Log.i("hph", "id=" + id);
        if (id == -1) {
            return 0;
        }
        int rate = 0;
        Query query = new Query();
        query.setFilterById(new long[]{id});
        Cursor c = this.downloadManager.query(query);
        Log.i("hph", "downloadManager.query");
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex("status"));
            int reasonIdx = c.getColumnIndex("reason");
            int fileSizeIdx = c.getColumnIndex(StatsPromConstants.PROM_LOTUSEED_KEY_TOTAL_SIZE);
            int bytesDLIdx = c.getColumnIndex("bytes_so_far");
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);
            switch (c.getInt(reasonIdx)) {
                case 2:
                    Log.i("caixinxin", "Waiting for connectivity");
                    sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOAD_PAUSED));
                    break;
            }
            if (fileSize != 0 && bytesDL >= 0 && bytesDL <= fileSize) {
                rate = (bytesDL * 100) / fileSize;
                Log.i("caixinxin", "Downloaded rate = " + rate);
            }
            switch (status) {
                case 8:
                    Log.i("hph", "DownloadManager.STATUS_SUCCESSFUL");
                    Log.i("hph", "isHexDownloaded.=" + this.isHexDownloaded);
                    if (!this.isHexDownloaded) {
                        this.isHexDownloaded = true;
                        String path_bin = FileManage.getSDPath() + Constants.DownloadFirmwarePath + this.fileName;
                        this.prefs.edit().putString(ClientCookie.PATH_ATTR, path_bin).commit();
                        Log.i("hph", "uri getUriForDownloadedFile=");
                        new Thread(new C12376()).start();
                        break;
                    }
                    break;
                case 16:
                    this.downloadManager.remove(new long[]{this.fileId});
                    Editor editor = this.prefs.edit();
                    if (this.prefs.contains(this.ware.getCurrentVer())) {
                        editor.remove(this.ware.getCurrentVer());
                    }
                    if (this.prefs.contains(ClientCookie.PATH_ATTR)) {
                        editor.remove(ClientCookie.PATH_ATTR);
                    }
                    editor.commit();
                    break;
            }
        }
        if (c == null) {
            return rate;
        }
        c.close();
        return rate;
    }

    private void sendBroadCast(int rate) {
        if (this.temp != rate) {
            this.temp = rate;
            Intent intent = new Intent(FwUpdateActivity.ACTION_DOWNLOADING);
            intent.putExtra("rate", rate);
            Log.i("caixinxin", "rate:" + rate);
            sendBroadcast(intent);
        }
    }

    private void downLoadDataFile(String dataFileUrl) {
        MalformedURLException e;
        IOException e2;
        Throwable th;
        FileNotFoundException e3;
        OutputStream output = null;
        File file = null;
        try {
            InputStream input;
            OutputStream output2;
            HttpURLConnection conn = (HttpURLConnection) new URL(dataFileUrl).openConnection();
            String dataPathName = (FileManage.getSDPath() + Constants.DownloadFirmwarePath) + this.datFileName;
            Log.i("wangchao", "pathName downLoadFile=" + dataPathName);
            File file2 = new File(dataPathName);
            try {
                input = conn.getInputStream();
                file2.createNewFile();
                output2 = new FileOutputStream(file2);
            } catch (MalformedURLException e4) {
                e = e4;
                file = file2;
                try {
                    e.printStackTrace();
                    try {
                        output.close();
                        this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                        Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                        sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                        stopSelf();
                    } catch (IOException e22) {
                        Log.i("wangchao", "fail");
                        e22.printStackTrace();
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        output.close();
                        this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                        Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                        sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                        stopSelf();
                    } catch (IOException e222) {
                        Log.i("wangchao", "fail");
                        e222.printStackTrace();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e3 = e5;
                file = file2;
                e3.printStackTrace();
                try {
                    output.close();
                    this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                    Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                    sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                    stopSelf();
                } catch (IOException e2222) {
                    Log.i("wangchao", "fail");
                    e2222.printStackTrace();
                    return;
                }
            } catch (IOException e6) {
                e2222 = e6;
                file = file2;
                e2222.printStackTrace();
                try {
                    output.close();
                    this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                    Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                    sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                    stopSelf();
                } catch (IOException e22222) {
                    Log.i("wangchao", "fail");
                    e22222.printStackTrace();
                    return;
                }
            } catch (Throwable th3) {
                th = th3;
                file = file2;
                output.close();
                this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                stopSelf();
                throw th;
            }
            try {
                byte[] buffer = new byte[14];
                while (input.read(buffer) != -1) {
                    output2.write(buffer);
                }
                output2.flush();
                try {
                    output2.close();
                    this.prefs.edit().putString("path_dat", file2.getAbsolutePath()).commit();
                    Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                    sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                    stopSelf();
                    file = file2;
                    output = output2;
                } catch (IOException e222222) {
                    Log.i("wangchao", "fail");
                    e222222.printStackTrace();
                    file = file2;
                    output = output2;
                }
            } catch (MalformedURLException e7) {
                e = e7;
                file = file2;
                output = output2;
                e.printStackTrace();
                output.close();
                this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                stopSelf();
            } catch (FileNotFoundException e8) {
                e3 = e8;
                file = file2;
                output = output2;
                e3.printStackTrace();
                output.close();
                this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                stopSelf();
            } catch (IOException e9) {
                e222222 = e9;
                file = file2;
                output = output2;
                e222222.printStackTrace();
                output.close();
                this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                stopSelf();
            } catch (Throwable th4) {
                th = th4;
                file = file2;
                output = output2;
                output.close();
                this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
                Log.i("wangchao", GraphResponse.SUCCESS_KEY);
                sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
                stopSelf();
                throw th;
            }
        } catch (MalformedURLException e10) {
            e = e10;
            e.printStackTrace();
            output.close();
            this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
            Log.i("wangchao", GraphResponse.SUCCESS_KEY);
            sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
            stopSelf();
        } catch (FileNotFoundException e11) {
            e3 = e11;
            e3.printStackTrace();
            output.close();
            this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
            Log.i("wangchao", GraphResponse.SUCCESS_KEY);
            sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
            stopSelf();
        } catch (IOException e12) {
            e222222 = e12;
            e222222.printStackTrace();
            output.close();
            this.prefs.edit().putString("path_dat", file.getAbsolutePath()).commit();
            Log.i("wangchao", GraphResponse.SUCCESS_KEY);
            sendBroadcast(new Intent(FwUpdateActivity.ACTION_DOWNLOADED));
            stopSelf();
        }
    }
}
