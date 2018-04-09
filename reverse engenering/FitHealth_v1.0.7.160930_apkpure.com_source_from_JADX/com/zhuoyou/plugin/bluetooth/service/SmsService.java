package com.zhuoyou.plugin.bluetooth.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.data.SmsMessageBody;
import com.zhuoyou.plugin.bluetooth.data.Util;

public class SmsService extends BroadcastReceiver {
    private static final String LOG_TAG = "SmsService";
    public static final String SMS_ACTION = "SenderSMSFromeFP";
    private static final String SMS_RECEIVED = "com.mtk.btnotification.SMS_RECEIVED";
    private static String preID = null;
    private Context mContext = null;

    void sendSms() {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r8 = 0;
        r0 = r10.mContext;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = "content://sms/inbox";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = android.net.Uri.parse(r1);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r2 = 0;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r3 = 0;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r4 = 0;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r5 = "_id desc";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r8 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        if (r8 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
    L_0x0018:
        r0 = r8.moveToNext();	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        if (r0 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
    L_0x001e:
        r0 = "body";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r9 = r8.getString(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = "address";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r7 = r8.getString(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = "_id";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r6 = r8.getString(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = preID;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = r6.equals(r0);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        if (r0 == 0) goto L_0x004a;
    L_0x0044:
        if (r8 == 0) goto L_0x0049;
    L_0x0046:
        r8.close();
    L_0x0049:
        return;
    L_0x004a:
        preID = r6;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r0 = "gchk";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1.<init>();	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r2 = "新短信 preID = ";	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r2 = preID;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        android.util.Log.i(r0, r1);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        if (r9 == 0) goto L_0x0018;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
    L_0x0068:
        if (r7 == 0) goto L_0x0018;	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
    L_0x006a:
        r10.sendSmsMessage(r9, r7);	 Catch:{ Exception -> 0x006e, all -> 0x0075 }
        goto L_0x0044;
    L_0x006e:
        r0 = move-exception;
        if (r8 == 0) goto L_0x0049;
    L_0x0071:
        r8.close();
        goto L_0x0049;
    L_0x0075:
        r0 = move-exception;
        if (r8 == 0) goto L_0x007b;
    L_0x0078:
        r8.close();
    L_0x007b:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.bluetooth.service.SmsService.sendSms():void");
    }

    public SmsService() {
        Log.i(LOG_TAG, "SmsReceiver(), SmsReceiver created!");
    }

    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_TAG, "onReceive()");
        boolean isServiceEnabled = PreferenceData.isSmsServiceEnable();
        boolean needForward = PreferenceData.isNeedPush();
        if (isServiceEnabled && needForward) {
            this.mContext = context;
            if (intent.getAction().equals(SMS_RECEIVED)) {
                sendSms();
                noticeBleNewSMS();
            } else if (intent.getAction().equals("com.tyd.btsecretary.SMS_UNREAD_TO_READ")) {
                if (intent.getLongExtra("read_id", -1) == -1) {
                    Log.i("gchk", "ID获取不对.发送已读指令失败");
                } else {
                    Log.i("gchk", "开始发送");
                    CustomCmd.sendCustomCmd(64, "Hi~Sao nian!", new char[]{(char) ((int) (32 + key)), 'ÿ', 'ÿ', 'ÿ'});
                }
                noticeBleSMSReaded();
            }
        }
    }

    private void sendSmsMessage(String msgbody, String address) {
        MessageObj smsMessageData = new MessageObj();
        smsMessageData.setDataHeader(createSmsHeader());
        smsMessageData.setDataBody(createSmsBody(address, msgbody));
        Log.i(LOG_TAG, "sendSmsMessage(), smsMessageData=" + smsMessageData);
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            service.sendSmsMessage(smsMessageData);
        }
    }

    private void noticeBleNewSMS() {
        Log.i(LOG_TAG, "noticeBleNewSMS");
        this.mContext.sendBroadcast(new Intent(BleManagerService.ACTION_NOTICE_NEW_SMS));
    }

    private void noticeBleSMSReaded() {
        Log.i(LOG_TAG, "noticeBleSMSReaded");
        this.mContext.sendBroadcast(new Intent(BleManagerService.ACTION_NOTICE_READ_SMS));
    }

    private MessageHeader createSmsHeader() {
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType(MessageObj.SUBTYPE_SMS);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        Log.i(LOG_TAG, "createSmsHeader(), header=" + header);
        return header;
    }

    private SmsMessageBody createSmsBody(String address, String msgbody) {
        String phoneNum = address;
        String sender = Util.getContactName(this.mContext, phoneNum);
        String content = msgbody;
        int timestamp = Util.getUtcTime(System.currentTimeMillis());
        SmsMessageBody body = new SmsMessageBody();
        body.setSender(sender);
        body.setNumber(phoneNum);
        body.setContent(content);
        body.setTimestamp(timestamp);
        body.setID(preID);
        Log.i(LOG_TAG, "createSmsBody(), body=" + body);
        return body;
    }
}
