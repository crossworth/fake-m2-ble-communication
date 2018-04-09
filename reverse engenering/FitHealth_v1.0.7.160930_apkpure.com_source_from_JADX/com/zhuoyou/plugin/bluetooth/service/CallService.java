package com.zhuoyou.plugin.bluetooth.service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog.Calls;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import com.zhuoyou.plugin.bluetooth.data.CallMessageBody;
import com.zhuoyou.plugin.bluetooth.data.MapConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.custom.ReadContactname;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class CallService extends PhoneStateListener {
    private static final String LOG_TAG = "CallService";
    private Context mContext = null;
    private String mIncomingNumber = null;
    private int mLastState = 0;
    private boolean mNeedWaiting = false;
    ReadContactname mReadContactname;
    private Timer mTimer = null;

    class C12081 extends TimerTask {
        C12081() {
        }

        public void run() {
            if (CallService.this.mTimer != null) {
                CallService.this.mTimer.cancel();
                CallService.this.mTimer = null;
            }
            CallService.this.sendCallMessage();
        }
    }

    public CallService(Context context) {
        Log.i(LOG_TAG, "CallService(), CallService created!");
        this.mContext = context;
        this.mReadContactname = new ReadContactname(context);
    }

    public void runMissedCallTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        TimerTask mTask = new C12081();
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        this.mTimer.schedule(mTask, 2000);
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        boolean isServiceEnabled;
        Log.i(LOG_TAG, "onCallStateChanged(), state:incomingNumber" + state + SeparatorConstants.SEPARATOR_ADS_ID + incomingNumber);
        if (state == 1) {
            noticeBleNewCall(incomingNumber);
        }
        if (state == 2) {
            noticeBleCallEnd();
        }
        if (this.mLastState == 1 && state == 0) {
            this.mIncomingNumber = incomingNumber;
            noticeBleCallEnd();
            isServiceEnabled = PreferenceData.isCallServiceEnable();
            boolean needForward = PreferenceData.isNeedPush();
            if (isServiceEnabled && needForward) {
                this.mNeedWaiting = true;
                runMissedCallTimer();
            }
        }
        if (this.mLastState == 1 && state == 0) {
            isServiceEnabled = PreferenceData.isCallServiceEnable();
            needForward = PreferenceData.isNeedPush();
            if (isServiceEnabled && needForward) {
                noticeBleCallEnd();
            }
        }
        this.mLastState = state;
    }

    private void sendCallMessage() {
        if (getMissedCallCount() == 0 && this.mNeedWaiting) {
            Log.i(LOG_TAG, "sendCallMessage(), callnumber==0");
            this.mNeedWaiting = false;
            runMissedCallTimer();
            return;
        }
        MessageObj callMessageData = new MessageObj();
        callMessageData.setDataHeader(createCallHeader());
        callMessageData.setDataBody(createCallBody());
        Log.i(LOG_TAG, "sendCallMessage(), callMessageData=" + callMessageData);
        BluetoothService service = BluetoothService.getInstance();
        if (service != null) {
            service.sendCallMessage(callMessageData);
        }
        noticeBleMissCall();
    }

    private MessageHeader createCallHeader() {
        MessageHeader header = new MessageHeader();
        header.setCategory("call");
        header.setSubType(MessageObj.SUBTYPE_MISSED_CALL);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        Log.i(LOG_TAG, "createCallHeader(), header=" + header);
        return header;
    }

    private CallMessageBody createCallBody() {
        String phoneNum = this.mIncomingNumber;
        String sender = Util.getContactName(this.mContext, phoneNum);
        String content = getMessageContent(sender);
        int timestamp = Util.getUtcTime(System.currentTimeMillis());
        int missedCallCount = getMissedCallCount();
        CallMessageBody body = new CallMessageBody();
        body.setSender(sender);
        body.setNumber(phoneNum);
        body.setContent(content);
        body.setMissedCallCount(missedCallCount);
        body.setTimestamp(timestamp);
        Log.i(LOG_TAG, "createCallBody(), body=" + body);
        return body;
    }

    private String getMessageContent(String sender) {
        StringBuilder content = new StringBuilder();
        content.append(this.mContext.getText(R.string.missed_call));
        content.append(": ");
        content.append(sender);
        content.append(BMessage.CRLF);
        content.append("Missed Call Count:");
        content.append(getMissedCallCount());
        Log.i(LOG_TAG, "getMessageContent(), content=" + content);
        return content.toString();
    }

    private int getMissedCallCount() {
        StringBuilder queryStr = new StringBuilder("type = ");
        queryStr.append(3);
        queryStr.append(" AND new = 1");
        Log.i(LOG_TAG, "getMissedCallCount(), query string=" + queryStr);
        int missedCallCount = 0;
        Cursor cur = this.mContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{"_id"}, queryStr.toString(), null, MapConstants.DEFAULT_SORT_ORDER);
        if (cur != null) {
            missedCallCount = cur.getCount();
            cur.close();
        }
        Log.i(LOG_TAG, "getMissedCallCount(), missed call count=" + missedCallCount);
        return missedCallCount;
    }

    private void noticeBleNewCall(String inPhoneNum) {
        Log.i(LOG_TAG, "noticeBleNewCall");
        ArrayList<String> lists = this.mReadContactname.getPhoneContacts();
        String cur_contactname = this.mReadContactname.getContactNameFromPhoneBook(this.mContext, inPhoneNum);
        Intent intent = new Intent(BleManagerService.ACTION_NOTICE_NEW_CALL);
        Log.i("zhangweinan", "lists=" + lists);
        Log.i("zhangweinan", "cur_contactname111=" + cur_contactname);
        if (lists.size() == 0) {
            intent.putExtra("incomingNumber", inPhoneNum);
        } else {
            Iterator it = lists.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (cur_contactname.equals(str)) {
                    intent.putExtra("incomingNumber", cur_contactname);
                    Log.i("zhangweinan", "cur_contactname222=" + cur_contactname);
                    Log.i("zhangweinan", "str = " + str);
                    break;
                }
                intent.putExtra("incomingNumber", inPhoneNum);
            }
        }
        this.mContext.sendBroadcast(intent);
    }

    private void noticeBleCallEnd() {
        Log.i(LOG_TAG, "noticeBleCallEnd");
        this.mContext.sendBroadcast(new Intent(BleManagerService.ACTION_NOTICE_CALL_END));
    }

    private void noticeBleMissCall() {
        Log.i(LOG_TAG, "noticeBleMissCall");
        this.mContext.sendBroadcast(new Intent(BleManagerService.ACTION_NOTICE_MISS_CALL));
    }

    private void makeToast(String msg) {
        Toast.makeText(this.mContext, msg, 0).show();
    }
}
