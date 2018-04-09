package com.zhuoyi.system.statistics.prom.util;

import android.content.Context;
import android.text.TextUtils;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.AdLogInfo;
import com.zhuoyi.system.network.object.ApkInfoNew;
import com.zhuoyi.system.network.object.FileDownloadResultExt;
import com.zhuoyi.system.network.object.FileVerInfo;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.network.protocol.DownloadLogExtensionReq;
import com.zhuoyi.system.network.protocol.GetAdsLogReq;
import com.zhuoyi.system.network.protocol.GetAdsLogResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.statistics.listener.ZyStatisticsSDK;
import com.zhuoyi.system.statistics.prom.data.StatsPromDBUtils;
import com.zhuoyi.system.statistics.prom.model.MyDownloadResult;
import com.zhuoyi.system.statistics.util.StatsConstants;
import com.zhuoyi.system.statistics.util.StatsUtils;
import com.zhuoyi.system.statistics.util.StatsUtils.TokenCallBack;
import com.zhuoyi.system.util.AppInfoUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import com.zhuoyi.system.util.model.MyPackageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StatsPromUtils {
    public static final String TAG = "PromStatsUtil";
    private static Context mContext = null;
    private static StatsPromUtils mInstance = null;
    private String timeStamp;

    class C18491 implements NetworkCallback {
        C18491() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue() && respMessage.head.code == AttributeUitl.getMessageCode(GetAdsLogResp.class)) {
                GetAdsLogResp resp = respMessage.message;
                Logger.m3372d(StatsPromUtils.TAG, "GetAdsLogResp:" + resp.toString());
                if (resp.getErrorCode() == 0) {
                    StatsPromDBUtils.getInstance(StatsPromUtils.mContext).deleteAllAdLogInfo();
                    StatsUtils.getInstance(StatsPromUtils.mContext).saveTimeStamp(StatsPromConstants.STATS_PROM_AD_TIME_STAMP, StatsConstants.TIME_STAMP_UPLOAD_SUCCESS);
                    return;
                }
                return;
            }
            StatsUtils.getInstance(StatsPromUtils.mContext).saveTimeStamp(StatsPromConstants.STATS_PROM_AD_TIME_STAMP, StatsPromUtils.this.timeStamp);
        }
    }

    class C18502 implements TokenCallBack {
        C18502() {
        }

        public void onTokenResponse(String token) {
            if (TextUtils.isEmpty(token)) {
                Logger.m3373e(StatsPromUtils.TAG, "get token error");
            } else {
                StatsPromUtils.this.sendAdLogInfoReqEx(token);
            }
        }
    }

    class C18513 implements NetworkCallback {
        C18513() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue()) {
                Logger.debug(StatsPromUtils.TAG, "sendDownloadResultEx  onResponse deleteAllDownloadResultInfo ");
                StatsPromDBUtils.getInstance(StatsPromUtils.mContext).deleteAllDownloadResultInfo();
                return;
            }
            Logger.error(StatsPromUtils.TAG, "sendDownloadLogExtensionReq  error");
        }
    }

    class C18524 implements TokenCallBack {
        C18524() {
        }

        public void onTokenResponse(String token) {
            if (TextUtils.isEmpty(token)) {
                Logger.m3373e(StatsPromUtils.TAG, "get token error");
            } else {
                StatsPromUtils.this.sendDownloadResultEx(token);
            }
        }
    }

    private StatsPromUtils() {
    }

    public static StatsPromUtils getInstance(Context context) {
        mContext = context;
        if (mInstance == null) {
            mInstance = new StatsPromUtils();
        }
        return mInstance;
    }

    private void sendAdLogInfoReqEx(String token1) {
        Logger.debug(TAG, "sendAdLogInfoReqEx");
        ArrayList<AdLogInfo> infos = StatsPromDBUtils.getInstance(mContext).queryAdLogInfoList();
        Logger.debug(TAG, "sendAdLogInfoReqEx size = " + infos.size());
        if (infos.size() != 0) {
            Iterator it = infos.iterator();
            while (it.hasNext()) {
                Logger.m3372d(TAG, ((AdLogInfo) it.next()).toString());
            }
            GetAdsLogReq req = new GetAdsLogReq();
            req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(mContext));
            req.setMac(TerminalInfoUtil.getLocalMacAddress(mContext));
            req.setAdLogInfoList(infos);
            this.timeStamp = StatsUtils.getInstance(mContext).getTimeStamp(StatsPromConstants.STATS_PROM_AD_TIME_STAMP);
            req.setUploadTime(this.timeStamp);
            req.setToken(token1);
            HTTPConnection.getInstance().sendRequest(Session.getInstance().getStatisNetworkAddr(), req, new C18491());
        }
    }

    public void sendAdLogInfoReq() {
        Logger.debug(TAG, "sendAdLogInfoReq");
        if (StatsPromDBUtils.getInstance(mContext).queryAdLogInfoList().size() != 0) {
            StatsUtils.getInstance(mContext).getStatsToken(new C18502());
        }
    }

    private void sendDownloadResultEx(String token2) {
        Logger.debug(TAG, "sendDownloadResultEx + token = " + token2);
        ArrayList<FileDownloadResultExt> results = new ArrayList();
        ArrayList<MyDownloadResult> resultlist = StatsPromDBUtils.getInstance(mContext).queryDownloadResultList();
        Logger.debug(TAG, "sendDownloadResultEx + resultlist size = " + resultlist.size());
        Iterator it = resultlist.iterator();
        while (it.hasNext()) {
            MyDownloadResult result = (MyDownloadResult) it.next();
            FileDownloadResultExt ext = new FileDownloadResultExt();
            ext.setFileSize(result.getTotalSize());
            ext.setOffset(result.getOffset());
            ext.setResult(result.getDownloadResult());
            ext.setTransportSize(result.getDownloadSize());
            ext.setSource1((byte) result.getSource1());
            ext.setSource2((byte) result.getSource2());
            FileVerInfo fileVerInfo = new FileVerInfo();
            fileVerInfo.setName(result.getPackageName());
            fileVerInfo.setVer(result.getVersionCode());
            ext.setFileVerInfo(fileVerInfo);
            results.add(ext);
        }
        DownloadLogExtensionReq req = new DownloadLogExtensionReq();
        req.setTermInfo(TerminalInfoUtil.getTerminalInfo(mContext));
        req.setNotifyInfos(results);
        req.setToken(token2);
        HTTPConnection.getInstance().sendRequest(Session.getInstance().getStatisNetworkAddr(), req, new C18513());
    }

    public synchronized void sendDownloadResult() {
        StatsUtils.getInstance(mContext).getStatsToken(new C18524());
    }

    public void addAppDownloadAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 5);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_DOWNLOAD, getStatMap(packageName, iconId, position, source, 5));
    }

    public void addAppInstallAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 1);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_INSTALL, getStatMap(packageName, iconId, position, source, 1));
    }

    public void addAppInstallSuccessAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 6);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_INSTALL_SUCCESS, getStatMap(packageName, iconId, position, source, 6));
    }

    public void addAppUninstallAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 7);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_UNINSTALL, getStatMap(packageName, iconId, position, source, 7));
    }

    public void addAppUninstallSuccessAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 8);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_UNINSTALL_SUCCESS, getStatMap(packageName, iconId, position, source, 8));
    }

    public void addAppLaunchAction(String packageName, int version, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, 2);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_APP_START, getStatMap(packageName, iconId, position, source, 2));
    }

    public void addAdDisplayAction(String packageName, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, 0, iconId, position, source, 3);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_AD_DIS, getStatMap(packageName, iconId, position, source, 3));
    }

    public void addAdClickAction(String packageName, int iconId, int position, int source) {
        addAdLogInfoToDB(packageName, 0, iconId, position, source, 4);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_AD_CLICK, getStatMap(packageName, iconId, position, source, 4));
    }

    public void addPromWapDisplayAction(String packageName, long stayTime, int position, int source) {
        addAdLogInfoToDB(packageName, 0, 1, position, source, 3, stayTime);
        ZyStatisticsSDK.onEventDuration(StatsPromConstants.PROM_LOTUSEED_EVENTID_WAP_DIS, getStatMap(packageName, 0, position, source, 3), stayTime);
    }

    public void addPromHomeDisplayAction(long stayTime, int position, int source) {
        addAdLogInfoToDB(mContext.getPackageName(), 0, 1, position, source, 3);
        ZyStatisticsSDK.onEventDuration(StatsPromConstants.PROM_LOTUSEED_EVENTID_PROM_HOME_DIS, getStatMap(mContext.getPackageName(), 0, position, source, 3), stayTime);
    }

    public void addPromDetailDisplayAction(long stayTime, int position, int source) {
        addAdLogInfoToDB(mContext.getPackageName(), 0, 1, position, source, 3, stayTime);
        ZyStatisticsSDK.onEventDuration(StatsPromConstants.PROM_LOTUSEED_EVENTID_PROM_DETAIL_DIS, getStatMap(mContext.getPackageName(), 0, position, source, 3), stayTime);
    }

    public void addNotifyDisplayAction(PromAppInfo appInfo) {
        addAdLogInfoToDB(appInfo.getPackageName(), appInfo.getVer(), appInfo.getIconId(), 3, 0, 3);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_NOTIFY_DIS, getStatMap(appInfo.getPackageName(), appInfo.getIconId(), 3, 0, 3));
    }

    public void addSilentNotifyAction(ApkInfoNew appInfo) {
        addAdLogInfoToDB(appInfo.getPackageName(), appInfo.getVerCode(), appInfo.getIconId(), 18, 0, 3);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_NOTIFY_DIS, getStatMap(appInfo.getPackageName(), appInfo.getIconId(), 18, 0, 3));
    }

    public void addSilentNotifyAction(PromAppInfo appInfo) {
        addAdLogInfoToDB(appInfo.getPackageName(), appInfo.getVer(), appInfo.getIconId(), 18, 0, 3);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_NOTIFY_DIS, getStatMap(appInfo.getPackageName(), appInfo.getIconId(), 18, 0, 3));
    }

    public void addPlugInfInitAction(MyPackageInfo info) {
        addAdLogInfoToDB(info.getPackageName(), info.getVersionCode(), 0, 20, 0, 2);
        ZyStatisticsSDK.onEvent(StatsPromConstants.PROM_LOTUSEED_EVENTID_INIT_PLUG_IN, getStatMap(info.getPackageName(), 0, 20, 0, 2));
    }

    private void addAdLogInfoToDB(String packageName, int version, int iconId, int position, int source, int action) {
        addAdLogInfoToDB(packageName, version, iconId, position, source, action, 0);
    }

    private void addAdLogInfoToDB(String packageName, int version, int iconId, int position, int source, int action, long stayTime) {
        AdLogInfo info = new AdLogInfo();
        info.setAction(action);
        info.setPackageName(packageName);
        info.setAppVer(version);
        info.setNum(1);
        info.setSdkVer(AppInfoUtils.getPackageVersionCode(mContext));
        info.setIconId(iconId);
        info.setSource1((short) position);
        info.setSource2((short) source);
        info.setStayTime(stayTime);
        StatsPromDBUtils.getInstance(mContext).addAdLogInfo(info);
    }

    private Map<String, String> getStatMap(String packageName, int iconId, int position, int source, int action) {
        Map<String, String> map = new HashMap();
        map.put("package_name", packageName);
        map.put("action", action);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_SOURCE1, position);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_SOURCE2, source);
        map.put(StatsPromConstants.PROM_LOTUSEED_KEY_ICON_ID, iconId);
        map.put("sdk_version", ZySDKConfig.SDK_VERSION_NAME);
        return map;
    }
}
