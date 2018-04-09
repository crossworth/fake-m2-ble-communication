package com.zhuoyou.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AlarmChangeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharepreference = context.getSharedPreferences("gaode_location_info", 0);
        Editor edit = sharepreference.edit();
        edit.putLong("change_time", System.currentTimeMillis() - sharepreference.getLong("onPause_time", System.currentTimeMillis()));
        edit.commit();
    }
}
