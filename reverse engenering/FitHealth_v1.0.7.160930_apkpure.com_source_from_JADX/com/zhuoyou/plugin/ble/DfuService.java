package com.zhuoyou.plugin.ble;

import android.app.Activity;
import com.zhuoyou.plugin.firmware.NotificationActivity;
import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuService extends DfuBaseService {
    protected Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }
}
