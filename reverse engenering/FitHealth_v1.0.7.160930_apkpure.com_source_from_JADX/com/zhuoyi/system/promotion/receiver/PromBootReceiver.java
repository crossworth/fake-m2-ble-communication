package com.zhuoyi.system.promotion.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyi.system.util.Logger;

public class PromBootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Logger.debug("ZyPromReceiver", "boot completed and init the SDK.");
            ZyPromSDK.getInstance().initWithPlugIn(context, false);
        }
    }
}
