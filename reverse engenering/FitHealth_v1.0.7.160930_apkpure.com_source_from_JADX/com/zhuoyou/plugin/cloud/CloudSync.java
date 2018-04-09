package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.mainFrame.RankFragment;
import com.zhuoyou.plugin.running.HomePageFragment;
import com.zhuoyou.plugin.running.MainService;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.HashMap;

public class CloudSync {
    private static final int DB_STATE_CHANGE = 1008;
    public static int autoSyncType = 1;
    private static int getInfo = 2;
    public static Runnable getNetRankInfoRunnable = new C12166();
    public static Runnable getNetTodayRankInfoRunnable = new C12177();
    static Runnable getSportFromCloudRunnable = new C12155();
    static Handler handler = new C12133();
    public static boolean isSync = false;
    public static boolean isUpdateTodayRank = true;
    private static final Context mContext = RunningApp.getInstance().getApplicationContext();
    public static Runnable postGPSDataRunnable = new C12188();
    static Runnable postSportDataRunnable = new C12144();
    public static int syncType = 0;

    static class C12111 implements OnClickListener {
        C12111() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    static class C12122 implements OnClickListener {
        C12122() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            CloudSync.syncData(0);
        }
    }

    static class C12133 extends Handler {
        C12133() {
        }

        public void handleMessage(Message msg) {
            Message message;
            switch (msg.what) {
                case 200:
                    switch (msg.arg1) {
                        case NetMsgCode.postCustomInfo /*101001*/:
                            Log.d("txhlog", " start postCustomInfo");
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 4;
                                message.obj = "10%";
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            if (CloudSync.syncType == 3) {
                                CloudSync.setSyncEnd();
                                return;
                            } else {
                                new Thread(CloudSync.postSportDataRunnable).start();
                                return;
                            }
                        case NetMsgCode.getCustomInfo /*102001*/:
                            Log.d("txhlog", " start getSportInfo");
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 4;
                                message.obj = "60%";
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            new Thread(CloudSync.getSportFromCloudRunnable).start();
                            return;
                        case NetMsgCode.postSportInfo /*104001*/:
                            Log.d("txhlog", " start postSportInfo");
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 4;
                                message.obj = "50%";
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            Tools.clearFeedTable(DataBaseContants.TABLE_DELETE_NAME, CloudSync.mContext);
                            if (CloudSync.syncType == 0) {
                                CloudSync.downloadData();
                                return;
                            } else {
                                CloudSync.setSyncEnd();
                                return;
                            }
                        case NetMsgCode.getSportInfo /*105001*/:
                            Log.d("txhlog", "msg getSportInfo! ");
                            CloudSync.setSyncEnd();
                            if (HomePageFragment.mHandler != null) {
                                message = new Message();
                                message.what = 2;
                                message.arg1 = 2;
                                HomePageFragment.mHandler.sendMessage(message);
                            }
                            Toast.makeText(CloudSync.mContext, R.string.complete_sync, 0).show();
                            CloudSync.isUpdateTodayRank = true;
                            CloudSync.handler.sendEmptyMessageDelayed(1008, 1500);
                            Log.i("hph", "isUpdateTodayRank = true;");
                            return;
                        default:
                            return;
                    }
                case 404:
                    if (HomePageFragment.mHandler != null) {
                        message = new Message();
                        message.what = 3;
                        message.arg1 = 2;
                        HomePageFragment.mHandler.sendMessage(message);
                    }
                    if (CloudSync.getInfo != 2) {
                        CloudSync.isSync = false;
                        CloudSync.getInfo = 2;
                        return;
                    }
                    if (CloudSync.syncType == 0) {
                        Toast.makeText(CloudSync.mContext, R.string.network_link_failure, 0).show();
                    }
                    Log.i("hph", "network_link_failure222");
                    CloudSync.setSyncEnd();
                    return;
                case 1008:
                    Tools.dbStateChange = false;
                    return;
                case 110011:
                    if (HomePageFragment.mHandler != null) {
                        message = new Message();
                        message.what = 3;
                        message.arg1 = 2;
                        HomePageFragment.mHandler.sendMessage(message);
                    }
                    if (CloudSync.syncType == 0) {
                        Toast.makeText(CloudSync.mContext, R.string.update_failed, 0).show();
                    }
                    Log.i("hph", "syncType==0");
                    CloudSync.setSyncEnd();
                    return;
                default:
                    return;
            }
        }
    }

    static class C12144 implements Runnable {
        C12144() {
        }

        public void run() {
            new SportDataSync(CloudSync.mContext, CloudSync.getInfo).postSportData(CloudSync.handler);
        }
    }

    static class C12155 implements Runnable {
        C12155() {
        }

        public void run() {
            new SportDataSync(CloudSync.mContext, CloudSync.getInfo).getSportFromCloud(CloudSync.handler);
        }
    }

    static class C12166 implements Runnable {
        C12166() {
        }

        public void run() {
            RankInfoSync rankInfo = new RankInfoSync(CloudSync.mContext);
            if (RankFragment.handler != null) {
                rankInfo.getNetRankInfo(RankFragment.handler);
            }
        }
    }

    static class C12177 implements Runnable {
        C12177() {
        }

        public void run() {
            RankInfoSync rankInfo = new RankInfoSync(CloudSync.mContext);
            if (RankFragment.handler != null) {
                rankInfo.getTodayRankInfo(RankFragment.handler);
            }
        }
    }

    static class C12188 implements Runnable {
        C12188() {
        }

        public void run() {
            new GPSDataSync(CloudSync.mContext, CloudSync.getInfo).postSportData(CloudSync.handler);
        }
    }

    static class C12199 implements Runnable {
        C12199() {
        }

        public void run() {
            String openId;
            HashMap<String, String> params = new HashMap();
            if (Tools.getLogin(CloudSync.mContext)) {
                openId = Tools.getOpenId(CloudSync.mContext);
            } else {
                openId = "0";
            }
            params.put("openId", openId);
            new GetDataFromNet(NetMsgCode.ACTION_GET_MSG, ((RunningApp) CloudSync.mContext).GetAppHandler(), params, CloudSync.mContext).execute(new Object[]{NetMsgCode.URL});
        }
    }

    public static void prepareSync() {
        int net = NetUtils.getAPNType(mContext);
        if (!Tools.getLogin(mContext)) {
            Toast.makeText(mContext, R.string.login_before_sync, 0).show();
        } else if (net == -1) {
            Toast.makeText(mContext, R.string.check_network, 0).show();
        } else if (net == 1) {
            syncData(0);
            Log.i("hph", "syncData(0);");
        } else {
            Builder builder = new Builder(mContext);
            builder.setTitle((int) R.string.cloud_syncs);
            builder.setMessage((int) R.string.cloud_sync_message);
            builder.setPositiveButton((int) R.string.cancle, new C12111());
            builder.setNegativeButton((int) R.string.continueto, new C12122());
            builder.setCancelable(Boolean.valueOf(false));
            CustomAlertDialog dialog = builder.create();
            dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
            dialog.show();
        }
    }

    public static void startAutoSync(int netType) {
        if (netType == 1) {
            syncData(1);
        } else if (netType == 2) {
            syncData(2);
        } else if (netType == 4) {
            syncData(4);
        }
    }

    public static void startSyncInfo() {
        if (NetUtils.getAPNType(mContext) != -1 && Tools.getLogin(mContext)) {
            syncData(3);
        }
    }

    private static void syncData(int type) {
        if (!(!Tools.getLogin(mContext) || MainService.syncnow.booleanValue() || isSync)) {
            getInfo = 2;
            syncType = type;
            isSync = true;
            Log.i("hph", "syncType=" + syncType);
            switch (type) {
                case 0:
                    if (HomePageFragment.mHandler != null) {
                        Message message = new Message();
                        BleManagerService.getInstance().setTimeToRemote(0);
                        message.what = 1;
                        message.arg1 = 2;
                        HomePageFragment.mHandler.sendMessageDelayed(message, 500);
                        break;
                    }
                    break;
                case 1:
                case 2:
                    new SportDataSync(mContext, getInfo).postSportData(handler);
                    break;
                case 3:
                    break;
            }
            new CustomInfoSync(mContext, handler).postCustomInfo();
        }
        if (type == 4) {
            msgInfo();
        }
    }

    private static void setSyncEnd() {
        isSync = false;
    }

    public static void downloadData() {
        if (Tools.getLogin(mContext)) {
            new CustomInfoSync(mContext, handler).getCustomInfo();
        }
    }

    public static void syncAfterLogin(int type) {
        if (Tools.getLogin(mContext) && !MainService.syncnow.booleanValue()) {
            getInfo = type;
            syncType = 0;
            isSync = true;
            if (HomePageFragment.mHandler != null) {
                Message message = new Message();
                BleManagerService.getInstance().setTimeToRemote(0);
                message.what = 1;
                message.arg1 = 2;
                HomePageFragment.mHandler.sendMessageDelayed(message, 500);
            }
            new Thread(postSportDataRunnable).start();
        }
    }

    private static void msgInfo() {
        new Thread(new C12199()).start();
    }
}
