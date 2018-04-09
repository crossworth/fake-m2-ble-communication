package com.zhuoyou.plugin.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TestSendDataFromRemote extends BroadcastReceiver {
    private static final String ACTION_SEND_MSG = "com.tyd.plugin.receiver.sendmsg";
    private static final boolean DEBUG = false;

    public void onReceive(Context arg0, Intent arg1) {
    }
}
