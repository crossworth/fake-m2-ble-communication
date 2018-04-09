package com.zhuoyou.plugin.bluetooth.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.util.Log;
import com.fithealth.running.R;
import com.zhuoyou.plugin.bluetooth.data.AppList;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.NotificationMessageBody;
import com.zhuoyou.plugin.bluetooth.data.Util;

public class SystemNotificationService extends BroadcastReceiver {
    private static final String LOG_TAG = "SystemNotificationService";
    private static float mBettryCapacity = 0.0f;
    private static float mLastBettryCapacity = 0.0f;
    private Context mContext = null;

    public SystemNotificationService() {
        Log.i(LOG_TAG, "SystemNotificationService(), SystemNotificationService created!");
    }

    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        String intentAction = intent.getAction();
        if ("android.intent.action.BATTERY_LOW".equalsIgnoreCase(intentAction)) {
            if (mLastBettryCapacity == 0.0f) {
                Log.i(LOG_TAG, "mLastBettryCapacity = 0");
                sendLowBatteryMessage(String.valueOf(mBettryCapacity * 100.0f));
                mLastBettryCapacity = mBettryCapacity;
            } else if (mLastBettryCapacity != mBettryCapacity) {
                sendLowBatteryMessage(String.valueOf((int) (mBettryCapacity * 100.0f)));
                mLastBettryCapacity = mBettryCapacity;
            }
        } else if ("android.intent.action.BATTERY_CHANGED".equalsIgnoreCase(intentAction)) {
            mBettryCapacity = ((float) intent.getIntExtra(LogColumns.LEVEL, -1)) / ((float) intent.getIntExtra("scale", -1));
        } else if ("android.intent.action.ACTION_POWER_CONNECTED".equalsIgnoreCase(intentAction)) {
            mLastBettryCapacity = 0.0f;
        } else if (!SmsService.SMS_ACTION.equals(intentAction)) {
        } else {
            if (getResultCode() == -1) {
                sendSMSSuccessMessage();
            } else {
                sendSMSFailMessage();
            }
        }
    }

    private void sendLowBatteryMessage(String value) {
        String titile = this.mContext.getResources().getString(R.string.batterylow);
        String content = this.mContext.getResources().getString(R.string.pleaseconnectcharger) + ":" + value + "%";
        MessageObj smsMessageData = new MessageObj();
        smsMessageData.setDataHeader(createNotificationHeader());
        smsMessageData.setDataBody(createNotificationBody(titile, content));
        Log.i(LOG_TAG, "sendSmsMessage(), smsMessageData=" + smsMessageData);
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            service.sendSystemNotiMessage(smsMessageData);
        }
    }

    private MessageHeader createNotificationHeader() {
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType(MessageObj.SUBTYPE_NOTI);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        Log.i(LOG_TAG, "createSmsHeader(), header=" + header);
        return header;
    }

    private NotificationMessageBody createNotificationBody(String title, String content) {
        ApplicationInfo appinfo = this.mContext.getApplicationInfo();
        String appName = Util.getAppName(this.mContext, appinfo);
        Bitmap sendIcon = Util.getMessageIcon(this.mContext, appinfo);
        int timestamp = Util.getUtcTime(System.currentTimeMillis());
        String tickerText = "";
        NotificationMessageBody body = new NotificationMessageBody();
        if (title == this.mContext.getResources().getString(R.string.batterylow)) {
            body.setAppID(Util.getKeyFromValue(AppList.BETTRYLOW_APPID));
        } else if (title == this.mContext.getResources().getString(R.string.sms_send)) {
            body.setAppID(Util.getKeyFromValue(AppList.SMSRESULT_APPID));
        }
        body.setSender(appName);
        body.setTitle(title);
        body.setContent(content);
        body.setTickerText(tickerText);
        body.setTimestamp(timestamp);
        body.setIcon(sendIcon);
        Log.i(LOG_TAG, "createLowBatteryBody(), body=" + body.toString().substring(0, 20));
        return body;
    }

    private void sendSMSSuccessMessage() {
        String titile = this.mContext.getResources().getString(R.string.sms_send);
        String content = this.mContext.getResources().getString(R.string.sms_send_success);
        Log.i(LOG_TAG, "sendSMSSuccessMessage()" + titile + content);
        MessageObj sendSMSSuccessMessageData = new MessageObj();
        sendSMSSuccessMessageData.setDataHeader(createNotificationHeader());
        sendSMSSuccessMessageData.setDataBody(createNotificationBody(titile, content));
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            service.sendSystemNotiMessage(sendSMSSuccessMessageData);
        }
    }

    private void sendSMSFailMessage() {
        String titile = this.mContext.getResources().getString(R.string.sms_send);
        String content = this.mContext.getResources().getString(R.string.sms_send_fail);
        Log.i(LOG_TAG, "sendSMSFailMessage()" + titile + content);
        MessageObj sendSMSSuccessMessageData = new MessageObj();
        sendSMSSuccessMessageData.setDataHeader(createNotificationHeader());
        sendSMSSuccessMessageData.setDataBody(createNotificationBody(titile, content));
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            service.sendSystemNotiMessage(sendSMSSuccessMessageData);
        }
    }
}
