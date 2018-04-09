package com.zhuoyou.plugin.selfupdate;

import com.zhuoyou.plugin.running.BuildConfig;

public class Constants {
    public static final String APPID = (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR) ? "fit000" : "run000");
    public static final String CHNID = (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR) ? "fithealth01" : "running01");
    public static final String DownloadApkPath = "/Running/download/";
    public static final String DownloadFirmwarePath = "/Running/download/bin/";
    public static final String DownloadPath = "/Running/download/temp/";
}
