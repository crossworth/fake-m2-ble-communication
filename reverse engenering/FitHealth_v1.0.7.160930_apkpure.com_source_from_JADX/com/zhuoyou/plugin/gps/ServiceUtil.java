package com.zhuoyou.plugin.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.mcube.lib.ped.PedometerService;
import com.zhuoyou.plugin.database.DataBaseUtil;
import com.zhuoyou.plugin.running.Tools;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceUtil {
    public static boolean isgaodeSerAlive;
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    public boolean isstepsSerAlive;
    public Handler mAlivegpsHandler = new C12801();
    public Handler mAlivestepsHandler = new C12812();
    public BroadcastReceiver mBroadcastReceiver = new C12823();
    public Context mContext;
    public DataBaseUtil mDataBaseUtil;

    class C12801 extends Handler {
        C12801() {
        }

        public void handleMessage(Message msg) {
            if (!ServiceUtil.isgaodeSerAlive) {
                ServiceUtil.this.mContext.startService(new Intent(ServiceUtil.this.mContext, GaodeService.class));
                if (GaoDeMapActivity.is_line) {
                    ServiceUtil.this.mDataBaseUtil = new DataBaseUtil(ServiceUtil.this.mContext);
                    GaodeService.is_running = true;
                    OperationTimeModel mOperation = new OperationTimeModel();
                    mOperation.setOperatId(Tools.getPKL());
                    mOperation.setOperationtime(ServiceUtil.this.conversTime(System.currentTimeMillis()));
                    mOperation.setOperationSystime(System.currentTimeMillis());
                    GaodeService.point_state = 5;
                    mOperation.setOperationState(5);
                    mOperation.setSyncState(0);
                    ServiceUtil.this.mDataBaseUtil.insertOperation(mOperation);
                }
            }
        }
    }

    class C12812 extends Handler {
        C12812() {
        }

        public void handleMessage(Message msg) {
            if (!ServiceUtil.this.isstepsSerAlive) {
                ServiceUtil.this.mContext.startService(new Intent(ServiceUtil.this.mContext.getApplicationContext(), PedometerService.class));
            }
        }
    }

    class C12823 extends BroadcastReceiver {
        C12823() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.zhuoyou.gaode.activity.hi")) {
                ServiceUtil.isgaodeSerAlive = true;
            } else if (action.equals("com.zhuoyou.steps.activity.hi")) {
                ServiceUtil.this.isstepsSerAlive = true;
            }
        }
    }

    public ServiceUtil(Context mContext) {
        this.mContext = mContext;
        initFilter();
    }

    public boolean closeGaodeService() {
        boolean isclose = this.mContext.stopService(new Intent(this.mContext, GaodeService.class));
        if (isclose) {
            isgaodeSerAlive = false;
        }
        return isclose;
    }

    public boolean isServiceRunning() {
        isgaodeSerAlive = false;
        this.isstepsSerAlive = false;
        this.mContext.sendBroadcast(new Intent("com.zhuoyou.gaode.service.hello"));
        this.mContext.sendBroadcast(new Intent("com.zhuoyou.steps.service.hello"));
        this.mAlivegpsHandler.sendEmptyMessageDelayed(0, 3000);
        this.mAlivestepsHandler.sendEmptyMessageDelayed(0, 3000);
        return true;
    }

    public void initFilter() {
        IntentFilter mGATTFilter = new IntentFilter();
        mGATTFilter.addAction("com.zhuoyou.gaode.activity.hi");
        mGATTFilter.addAction("com.zhuoyou.steps.activity.hi");
        this.mContext.registerReceiver(this.mBroadcastReceiver, mGATTFilter);
    }

    public long conversTime(long systemTime) {
        return Long.valueOf(Long.parseLong(this.formatter.format(new Date(systemTime)))).longValue();
    }

    public void uninitFilter() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }
}
