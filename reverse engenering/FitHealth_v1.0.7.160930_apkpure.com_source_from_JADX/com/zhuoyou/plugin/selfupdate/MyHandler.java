package com.zhuoyou.plugin.selfupdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.stat.DeviceInfo;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.download.DownloadService;
import com.zhuoyou.plugin.download.Util_update.AppInfoManager;
import com.zhuoyou.plugin.download.Util_update.FileManage;
import com.zhuoyou.plugin.download.Util_update.FileManage.FileHolder;
import com.zhuoyou.plugin.running.Main;
import java.io.File;
import java.util.HashMap;

public class MyHandler extends Handler {
    public static final int MSG_UPDATE_START = 1000;
    public static final int MSG_UPDATE_VIEW = 1001;
    private int count = 0;
    private boolean isHand = false;
    private Context mCtx = null;

    class C14302 implements OnClickListener {
        C14302() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Main.mHandler.sendEmptyMessage(1);
        }
    }

    class C14324 implements OnClickListener {
        C14324() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public MyHandler(Context ctx) {
        this.mCtx = ctx;
    }

    public MyHandler(Context ctx, boolean isHand) {
        this.mCtx = ctx;
        this.isHand = isHand;
    }

    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1001:
                HashMap<String, Object> map = msg.obj;
                if (map != null) {
                    int policy = ((Integer) map.get("policy")).intValue();
                    String content = (String) map.get("content");
                    String version = (String) map.get(DeviceInfo.TAG_VERSION);
                    String data = ((String) map.get("fileUrl")) + "&" + version;
                    Log.i("gchk", "data = " + data);
                    if (policy != 3) {
                        Log.i("gchk", "need update policy =" + policy + " ||content = " + content + " ||");
                        String sdcardPath = FileManage.getSDPath();
                        Log.i("gchk", "sdcardPath  = " + sdcardPath);
                        if (sdcardPath == null) {
                            Toast.makeText(this.mCtx, R.string.please_insert, 0).show();
                            return;
                        }
                        FileHolder fileHolder = FileManage.readSDCardSpace();
                        if (fileHolder == null || fileHolder.availSpace >= 10240) {
                            File f = new File(FileManage.getSDPath() + Constants.DownloadApkPath + this.mCtx.getResources().getString(R.string.app_name) + ".apk");
                            if (f.exists()) {
                                f.delete();
                            }
                            showAlertDialog(policy, content, sdcardPath, data, false);
                            return;
                        }
                        Toast.makeText(this.mCtx, R.string.space_not_enough, 0).show();
                        return;
                    } else if (this.isHand) {
                        Toast.makeText(this.mCtx, this.mCtx.getResources().getString(R.string.islasted_version), 1).show();
                        return;
                    } else {
                        return;
                    }
                } else if (this.isHand) {
                    Toast.makeText(this.mCtx, this.mCtx.getResources().getString(R.string.islasted_version), 1).show();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void showAlertDialog(int policy, String content, final String sdcardPath, final String data, final boolean exsit) {
        int yes_id;
        int no_id;
        if (exsit) {
            yes_id = R.string.alert_btn_install;
            no_id = R.string.alert_btn_no_install;
        } else {
            yes_id = R.string.alert_btn_yes;
            no_id = R.string.alert_btn_no;
        }
        Builder builder = new Builder(this.mCtx);
        builder.setTitle((int) R.string.alert_title);
        if (TextUtils.isEmpty(content)) {
            builder.setMessage((int) R.string.alert_msg);
        } else {
            builder.setMessage(content);
        }
        if (policy == 1) {
            builder.setPositiveButton(yes_id, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (exsit) {
                        AppInfoManager.AppInstall(sdcardPath, MyHandler.this.mCtx);
                    } else {
                        Intent downloadServiceIntent = new Intent(MyHandler.this.mCtx, DownloadService.class);
                        downloadServiceIntent.putExtra("sdPath", sdcardPath);
                        downloadServiceIntent.putExtra("url", data);
                        downloadServiceIntent.putExtra("appName", MyHandler.this.mCtx.getResources().getString(R.string.app_name));
                        Log.i("gchk", "start download thread end name=" + MyHandler.this.mCtx.startService(downloadServiceIntent).getClassName());
                    }
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(no_id, new C14302());
            builder.setCancelable(Boolean.valueOf(false));
            builder.create().show();
        } else if (policy == 2) {
            builder.setPositiveButton(yes_id, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (exsit) {
                        AppInfoManager.AppInstall(sdcardPath, MyHandler.this.mCtx);
                    } else {
                        Intent downloadServiceIntent = new Intent(MyHandler.this.mCtx, DownloadService.class);
                        downloadServiceIntent.putExtra("sdPath", sdcardPath);
                        downloadServiceIntent.putExtra("url", data);
                        downloadServiceIntent.putExtra("appName", MyHandler.this.mCtx.getResources().getString(R.string.app_name));
                        ComponentName name = MyHandler.this.mCtx.startService(downloadServiceIntent);
                        SelfUpdateMain.isDownloading = true;
                    }
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(no_id, new C14324());
            builder.setCancelable(Boolean.valueOf(false));
            builder.create().show();
        } else if (policy == 4) {
            Intent downloadServiceIntent = new Intent(this.mCtx, DownloadService.class);
            downloadServiceIntent.putExtra("sdPath", sdcardPath);
            downloadServiceIntent.putExtra("url", data);
            downloadServiceIntent.putExtra("appName", this.mCtx.getResources().getString(R.string.app_name));
            Log.i("gchk", "start download thread end name=" + this.mCtx.startService(downloadServiceIntent).getClassName());
        }
    }
}
