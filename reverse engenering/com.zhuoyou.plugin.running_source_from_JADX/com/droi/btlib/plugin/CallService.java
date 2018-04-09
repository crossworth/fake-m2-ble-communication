package com.droi.btlib.plugin;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog.Calls;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.droi.btlib.C0687R;
import com.droi.btlib.connection.CallMessageBody;
import com.droi.btlib.connection.MapConstants;
import com.droi.btlib.connection.MessageHeader;
import com.droi.btlib.connection.MessageObj;
import com.droi.btlib.service.BtManagerService;
import com.droi.btlib.service.Util;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class CallService extends PhoneStateListener {
    private static final String LOG_TAG = "CallService";
    private Context mContext = null;
    private Handler mHandler;
    private String mIncomingNumber = null;
    private int mLastState = 0;
    private boolean mNeedWaiting = false;
    ReadContactname mReadContactname;
    private Timer mTimer = null;

    class C06891 extends TimerTask {
        C06891() {
        }

        public void run() {
            if (CallService.this.mTimer != null) {
                CallService.this.mTimer.cancel();
                CallService.this.mTimer = null;
            }
            CallService.this.sendCallMessage();
        }
    }

    public CallService(Context context, Handler handler) {
        Log.i(LOG_TAG, "CallService(), CallService created!");
        this.mContext = context;
        this.mReadContactname = new ReadContactname(context);
        this.mHandler = handler;
    }

    public void runMissedCallTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        TimerTask mTask = new C06891();
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        this.mTimer.schedule(mTask, 2000);
    }

    public void onCallStateChanged(int state, String incomingNumber) {
        Log.i(LOG_TAG, "onCallStateChanged(), state:incomingNumber" + state + "," + incomingNumber + "getCallRemind=" + BtManagerService.getCallRemind());
        if (BtManagerService.getCallRemind()) {
            if (state == 1) {
                noticeBleNewCall(incomingNumber);
            }
            if (state == 2) {
                noticeBleCallEnd();
            }
            if (this.mLastState == 1 && state == 0) {
                this.mIncomingNumber = incomingNumber;
                noticeBleCallEnd();
                if (Util.getCallRemind()) {
                    this.mNeedWaiting = true;
                    runMissedCallTimer();
                }
            }
            if (this.mLastState == 1 && state == 0 && Util.getCallRemind()) {
                noticeBleCallEnd();
            }
            this.mLastState = state;
        }
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
        Message msg = new Message();
        msg.what = BtManagerService.TASK_ADD;
        msg.arg1 = 4112;
        msg.obj = Util.genBytesFromObject(callMessageData);
        this.mHandler.sendMessage(msg);
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
        content.append(this.mContext.getText(C0687R.string.missed_call));
        content.append(": ");
        content.append(sender);
        content.append("\r\n");
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
        if (ContextCompat.checkSelfPermission(this.mContext, "android.permission.READ_CALL_LOG") != 0) {
            return 1;
        }
        Cursor cur = this.mContext.getContentResolver().query(Calls.CONTENT_URI, new String[]{MapConstants._ID}, queryStr.toString(), null, MapConstants.DEFAULT_SORT_ORDER);
        if (cur != null) {
            missedCallCount = cur.getCount();
            cur.close();
        }
        Log.i(LOG_TAG, "getMissedCallCount(), missed call count=" + missedCallCount);
        return missedCallCount;
    }

    private void noticeBleNewCall(String inPhoneNum) {
        Log.i(LOG_TAG, "noticeBleNewCall");
        String sendName = new String();
        try {
            String cur_contactname = this.mReadContactname.getContactNameFromPhoneBook(inPhoneNum);
            Message msg;
            if (!TextUtils.isEmpty(cur_contactname) && (Util.getVersion().startsWith("M2_3_") || Util.getName().equals("U3"))) {
                sendName = cur_contactname;
                Log.i("hphCall", "sendName=" + sendName);
                msg = new Message();
                msg.what = BtManagerService.TASK_ADD;
                msg.arg1 = 4114;
                msg.obj = sendName;
                this.mHandler.sendMessage(msg);
            } else if (TextUtils.isEmpty(cur_contactname) || !Util.getName().equals("A7")) {
                sendName = inPhoneNum;
                Log.i("hphCall", "sendName=" + sendName);
                msg = new Message();
                msg.what = BtManagerService.TASK_ADD;
                msg.arg1 = 4114;
                msg.obj = sendName;
                this.mHandler.sendMessage(msg);
            } else {
                if (Pattern.compile("^[a-zA-Z0-9]+$").matcher(cur_contactname).find()) {
                    sendName = cur_contactname;
                } else {
                    sendName = inPhoneNum;
                }
                Log.i("hphCall", "sendName=" + sendName);
                msg = new Message();
                msg.what = BtManagerService.TASK_ADD;
                msg.arg1 = 4114;
                msg.obj = sendName;
                this.mHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            Log.i("chenxin", "call phone e=" + e.getMessage());
            sendName = inPhoneNum;
        }
    }

    private void noticeBleCallEnd() {
        Message msg = new Message();
        msg.what = BtManagerService.TASK_ADD;
        msg.arg1 = 4113;
        this.mHandler.sendMessage(msg);
    }

    private void makeToast(String msg) {
        Toast.makeText(this.mContext, msg, 0).show();
    }
}
