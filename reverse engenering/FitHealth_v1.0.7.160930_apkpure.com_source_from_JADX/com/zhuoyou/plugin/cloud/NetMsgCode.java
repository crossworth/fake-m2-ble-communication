package com.zhuoyou.plugin.cloud;

import com.zhuoyou.plugin.running.BuildConfig;

public class NetMsgCode {
    public static final int ACTION_GET_IDINFO = 100014;
    public static final int ACTION_GET_MSG = 100012;
    public static final int ACTION_GET_NEXTPAGE = 100015;
    public static final int ACTION_GET_REFRESHPAGE = 100016;
    public static final int ACTION_JOIN = 100013;
    public static final int APP_RUN_ACTION_INIT = 100011;
    public static final String DOWN_URL = (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR) ? "http://fithealthservice.dd351.com:8080/mars/services/FileService/fileDownLoad" : "http://service-erunning.yy845.com:8080/mars/services/FileService/fileDownLoad");
    public static final int FIRMWARE_GET_VERSION = 200101;
    public static final String GPS_DOWN_URL;
    public static final int The_network_link_failure = 404;
    public static final int The_network_link_success = 200;
    public static final String UP_URL = (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR) ? "http://fithealthservice.dd351.com:8080/mars/services/FileService/gpsFileUpLoad" : "http://service-erunning.yy845.com:8080/mars/services/FileService/gpsFileUpLoad");
    public static final String URL;
    public static final String deleteData = "deleteData";
    public static final int getCustomInfo = 102001;
    public static final int getGPSInfo = 10501;
    public static final int getNetRankInfo = 103001;
    public static final int getSportInfo = 105001;
    public static final int getTodayRankInfo = 301013;
    public static final String insertData = "insertData";
    public static final int postCustomInfo = 101001;
    public static final int postGPSInfo = 10401;
    public static final int postSportInfo = 104001;
    public static final int rank_state_request = 1111;
    public static final int rank_state_wait = 1110;
    public static final String updateData = "updateData";

    static {
        String str;
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            str = "http://fithealthservice.dd351.com:2761";
        } else {
            str = "http://service-erunning.yy845.com:2761";
        }
        URL = str;
        if (BuildConfig.FLAVOR.equals(BuildConfig.FLAVOR)) {
            str = "http://fithealthservice.dd351.com:8080/mars/services/FileService/gpsFileDownLoad";
        } else {
            str = "http://service-erunning.yy845.com:8080/mars/services/FileService/gpsFileDownLoad";
        }
        GPS_DOWN_URL = str;
    }
}
