package com.zhuoyi.system.promotion.listener;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.zhuoyi.system.config.ZySDKConfig;
import com.zhuoyi.system.promotion.data.PromDBUtils;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.TimeFormater;
import com.zhuoyi.system.statistics.sale.util.StatsSaleUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.CommConstants;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ZyPromSDK {
    public static final String TAG = "ZyPromSDK";
    private static Context context;
    private static ZyPromSDK mInstance = null;
    private boolean initPlugIn = true;
    private long lastInitTimeMills = 0;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        context = context;
    }

    private ZyPromSDK() {
    }

    public static ZyPromSDK getInstance() {
        if (mInstance == null) {
            mInstance = new ZyPromSDK();
        }
        return mInstance;
    }

    private String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while (true) {
                line = bufReader.readLine();
                if (line == null) {
                    inputReader.close();
                    bufReader.close();
                    return result;
                }
                result = new StringBuilder(String.valueOf(result)).append(line).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void init(Context context, boolean isInitWithKey) {
        if (isInitWithKey) {
            String str = "";
            String str2 = "";
            String appKey = "";
            try {
                Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
                str = bundle.getString(CommConstants.CPID_METADATA_KEY);
                str2 = bundle.getString("zy_appid");
                appKey = bundle.getString(CommConstants.LOTUSSED_ZY_METADATA_KEY);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            String channel = ZySDKConfig.getInstance().getLocChannel();
            if (TextUtils.isEmpty(channel)) {
                String channelId = getFromAssets(context, "td");
                if (TextUtils.isEmpty(channelId)) {
                    initWithKeys(context, "", "", "", "", false);
                    return;
                }
                initWithKeys(context, channelId, str, str2, appKey, true);
                return;
            }
            initWithKeys(context, channel, str, str2, appKey, true);
            return;
        }
        initWithKeys(context, "", "", "", "", false);
    }

    public void initWithPlugIn(Context context, boolean init) {
        this.initPlugIn = init;
        init(context, true);
    }

    public void initWithKeys(Context context, String channelId, String cpId, String appId, String appKey, boolean isInitWithKey) {
        setContext(context);
        String oldFrom = PromDBUtils.getInstance(context).queryCfgValueByKey(CommConstants.ZY_GET_DATA_PRIORITY);
        String newFrom = "1";
        if (isInitWithKey) {
            newFrom = "0";
        }
        if (!newFrom.equals(oldFrom)) {
            PromDBUtils.getInstance(context).insertCfg(CommConstants.ZY_GET_DATA_PRIORITY, newFrom);
        }
        if (TextUtils.isEmpty(channelId)) {
            channelId = TerminalInfoUtil.getChannelId(context);
        } else {
            PromDBUtils.getInstance(context).insertCfg("zy_channel_id", channelId);
        }
        if (TextUtils.isEmpty(appId)) {
            appId = TerminalInfoUtil.getAppId(context);
        } else {
            PromDBUtils.getInstance(context).insertCfg("zy_appid", appId);
        }
        if (TextUtils.isEmpty(cpId)) {
            cpId = TerminalInfoUtil.getCpId(context);
        } else {
            PromDBUtils.getInstance(context).insertCfg(CommConstants.CPID_METADATA_KEY, cpId);
        }
        if (TextUtils.isEmpty(channelId) || TextUtils.isEmpty(appId) || TextUtils.isEmpty(cpId)) {
            Log.e(TAG, "ZyPromSdk init failed.");
            return;
        }
        long timeMills = System.currentTimeMillis();
        Logger.m3373e(TAG, "init time=" + TimeFormater.formatTime(timeMills - this.lastInitTimeMills));
        if (timeMills - this.lastInitTimeMills > 300000) {
            this.lastInitTimeMills = timeMills;
            PromUtils.getInstance(context).startCommonReqTimer(TimeManager.UNIT_MINUTE + timeMills);
            StatsSaleUtils.getInstance(context).startCheckSaleStatsService(5000 + timeMills);
            if (this.initPlugIn) {
                PromUtils.getInstance(context).startPlugInService();
                return;
            }
            return;
        }
        Logger.debug(TAG, "Has been initialized");
    }

    public void initWithKeysHasExitAd(Context context, String channelId, String cpId, String appId, String appKey, boolean isInitWithKey) {
        initWithKeys(context, channelId, cpId, appId, appKey, isInitWithKey);
        PromUtils.getInstance(context).sendExitAdReq();
    }

    public void initWithExitAd(Context context) {
        initWithKeysHasExitAd(context, "", "", "", "", false);
    }

    public void showQuitDialog(Activity activity, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener) {
        PromUtils.getInstance(activity).showQuitDialog(activity, positiveBtnListener, negativeBtnListener);
    }
}
