package com.zhuoyou.plugin.download;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.fithealth.running.R;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.download.Util_update.AppInfoManager;
import com.zhuoyou.plugin.download.Util_update.FileManage;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.Constants;
import com.zhuoyou.plugin.selfupdate.SelfUpdateMain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DownloadService extends Service {
    private static NotificationManager NCmanager = null;
    public static final String Tag = "zhuqichao";
    public static DownloadService downloadServiceInstance;
    public static Map<String, Downloader> downloaders = new HashMap();
    private Builder builder;
    private String mApkPath;
    private Handler mHandler = new C12311();
    private String sdPathString;

    class C12311 extends Handler {
        C12311() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    String[] temp_str = msg.obj.split(SeparatorConstants.SEPARATOR_ADS_ID);
                    String url1 = temp_str[0];
                    long Surplus_size = Long.parseLong(temp_str[1]);
                    if (DownloadService.downloaders.get(url1) != null) {
                        int notifaction_flag = msg.arg1;
                        int rate = msg.arg2;
                        DownloadService.this.builder.setContentTitle(DownloadService.this.getString(R.string.update_downloading));
                        DownloadService.this.builder.setContentText(DownloadService.this.getString(R.string.Surplus) + DownloadService.humanReadableByteCount(Surplus_size, true));
                        DownloadService.this.builder.setTicker(DownloadService.this.getString(R.string.has_new_download));
                        DownloadService.this.builder.setOngoing(true);
                        DownloadService.NCmanager.notify(notifaction_flag, DownloadService.this.builder.build());
                        if (rate >= 100) {
                            DownloadService.NCmanager.cancel(notifaction_flag);
                            SelfUpdateMain.isDownloading = false;
                            return;
                        }
                        return;
                    }
                    return;
                case 3:
                    String url = msg.obj;
                    String localPath = ((Downloader) DownloadService.downloaders.get(url)).getLocalfile();
                    String mappName = ((Downloader) DownloadService.downloaders.get(url)).getAppName();
                    DownloadService.downloaders.remove(url);
                    if (DownloadService.downloaders != null && DownloadService.downloaders.size() == 0) {
                        DownloadService.this.stopSelf();
                    }
                    DownloadService.this.copyFile(localPath, DownloadService.this.mApkPath);
                    File old = new File(localPath);
                    if (old.exists()) {
                        old.delete();
                    }
                    Intent i = new Intent();
                    i.setFlags(268435456);
                    i.setAction("android.intent.action.VIEW");
                    i.setDataAndType(Uri.fromFile(new File(DownloadService.this.mApkPath)), "application/vnd.android.package-archive");
                    PendingIntent contentIntent = PendingIntent.getActivity(DownloadService.this.getApplicationContext(), (int) System.currentTimeMillis(), i, 1073741824);
                    DownloadService.this.builder.setContentTitle(mappName);
                    DownloadService.this.builder.setContentText(DownloadService.this.getString(R.string.download_notification));
                    DownloadService.this.builder.setTicker(DownloadService.this.getString(R.string.download_complete));
                    DownloadService.this.builder.setAutoCancel(true);
                    DownloadService.this.builder.setOngoing(false);
                    DownloadService.this.builder.setContentIntent(contentIntent);
                    DownloadService.NCmanager.notify(1, DownloadService.this.builder.build());
                    AppInfoManager.AppInstall(DownloadService.this.mApkPath, DownloadService.this);
                    return;
                case 4:
                    Tools.makeToast(DownloadService.this.getResources().getString(R.string.canot_getsize));
                    return;
                default:
                    return;
            }
        }
    }

    public void onCreate() {
        Log.e(Tag, "download service onCreate");
        NCmanager = (NotificationManager) getSystemService(MessageObj.CATEGORY_NOTI);
        downloadServiceInstance = this;
        this.builder = new Builder(this);
        this.builder.setSmallIcon(R.drawable.plug_download_m_icon);
        this.builder.setShowWhen(true);
        this.sdPathString = FileManage.getSDPath();
        if (this.sdPathString != null) {
            FileManage.newFolder(this.sdPathString + Constants.DownloadPath);
        }
        super.onCreate();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(Tag, "**************start download service onStartCommand");
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String urlstr = intent.getStringExtra("url").split("&")[0];
        String appName = intent.getStringExtra("appName");
        byte[] bitmap = intent.getByteArrayExtra("bitmap");
        String version = intent.getStringExtra("version");
        Log.e("version", "version + service " + version);
        String size = intent.getStringExtra("size");
        String mTempPath = this.sdPathString + Constants.DownloadPath + appName + ".tmp";
        this.mApkPath = this.sdPathString + Constants.DownloadApkPath + appName + ".apk";
        File f = new File(this.sdPathString + Constants.DownloadPath);
        if (!f.exists()) {
            f.mkdir();
        }
        File file = new File(mTempPath);
        if (file.exists()) {
            file.delete();
        }
        int notifaction_flag = (int) System.currentTimeMillis();
        Downloader downloader = new Downloader(this, urlstr, mTempPath, this.mHandler, notifaction_flag, appName, bitmap, size, version);
        Log.i(Tag, "urlstr:" + urlstr + " mTempPath:" + mTempPath + " notifaction_flag:" + notifaction_flag + " appName:" + appName + " size:" + size + " version:" + version);
        downloaders.put(urlstr, downloader);
        Log.e(Tag, "-----init");
        new DownloaderThread(downloader, this).start();
        Log.e(Tag, "**************end download service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        SelfUpdateMain.isDownloading = false;
    }

    public void copyFile(String oldPath, String newPath) {
        int bytesum = 0;
        try {
            if (new File(oldPath).exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread != -1) {
                        bytesum += byteread;
                        System.out.println(bytesum);
                        fs.write(buffer, 0, byteread);
                    } else {
                        inStream.close();
                        fs.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < ((long) unit)) {
            return bytes + " B";
        }
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(((int) (Math.log((double) bytes) / Math.log((double) unit))) - 1) + (si ? "" : "i");
        return String.format(Locale.CHINA, "%.2f %sB", new Object[]{Double.valueOf(((double) bytes) / Math.pow((double) unit, (double) ((int) (Math.log((double) bytes) / Math.log((double) unit))))), pre});
    }
}
