package com.zhuoyou.plugin.bluetooth.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Xml;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import com.zhuoyou.plugin.bluetooth.data.MapConstants;
import com.zhuoyou.plugin.bluetooth.data.MessageList;
import com.zhuoyou.plugin.bluetooth.data.MessageListItem;
import com.zhuoyou.plugin.bluetooth.data.SmsController;
import com.zhuoyou.plugin.bluetooth.data.VCard;
import com.zhuoyou.plugin.running.RunningApp;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlSerializer;

public class BTMapService extends BroadcastReceiver {
    private static final String TAG = "BTMapService";
    private static final String TELECOM_MSG_INBOX = "telecom/msg/inbox";
    private static final Context mContext = RunningApp.getInstance().getApplicationContext();
    public static final ArrayList<Long> mKeys = new ArrayList();
    private static final SmsController mSmsController = new SmsController(mContext);
    private String mFolder = TELECOM_MSG_INBOX;
    private String mMapCommand = null;
    private String mMapDisconnect = null;

    public BTMapService() {
        Log.i(TAG, "BTMapReceiver(), BTMapReceiver created!");
    }

    public void onReceive(Context context, Intent intent) {
        if (MapConstants.BT_MAP_BROADCAST_ACTION.equals(intent.getAction())) {
            if (intent.hasExtra(MapConstants.DISCONNECT)) {
                this.mMapDisconnect = new String(intent.getStringExtra(MapConstants.DISCONNECT));
                if (this.mMapDisconnect.equals(MapConstants.DISCONNECT)) {
                    mSmsController.onStop();
                    return;
                }
            }
            try {
                this.mMapCommand = new String(intent.getByteArrayExtra("EXTRA_DATA"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            BluetoothService service = BluetoothService.getInstance();
            String[] commands = this.mMapCommand.split(" ");
            try {
                Log.i(TAG, "BTMapService onReceive(), commands :" + this.mMapCommand);
            } catch (Exception e2) {
                e2.getStackTrace();
            }
            switch (Integer.valueOf(commands[0]).intValue()) {
                case 1:
                    handleSetFolder(service, commands);
                    return;
                case 3:
                    handleGetList(service, commands);
                    return;
                case 4:
                    handleGetMsg(service, commands);
                    return;
                case 5:
                    handleSetStatus(service, commands);
                    return;
                case 6:
                    handlePushMsg(service, this.mMapCommand);
                    return;
                default:
                    return;
            }
        }
    }

    private void handleSetStatus(BluetoothService service, String[] commands) {
        if (commands.length >= 3) {
            int status = Integer.valueOf(commands[1]).intValue();
            long id = Long.valueOf(commands[2]).longValue() & MapConstants.MESSAGE_HANDLE_MASK;
            if (status == 1 || status == 0) {
                mSmsController.setMessageStatus(id, status);
            } else if (mKeys.contains(Long.valueOf(id))) {
                Log.i(TAG, "BTMapReceiver(), The message has been deleted!");
                service.sendMapResult(String.valueOf(5));
            } else {
                mSmsController.deleteMessage(id);
            }
        }
    }

    private void handlePushMsg(BluetoothService service, String commands) {
        String messageVcard = commands;
        String telephone = parse(messageVcard);
        String startIndex = "BEGIN:MSG\r\n";
        int startOfMsg = messageVcard.indexOf(startIndex) + startIndex.length();
        int endOfMsg = messageVcard.indexOf("\r\nEND:MSG");
        Log.i(TAG, "send msg result success");
        if (startOfMsg > endOfMsg) {
            service.sendMapResult(String.valueOf(-6));
            return;
        }
        String text = messageVcard.substring(startOfMsg, endOfMsg);
        if (text.equals("")) {
            text = "\n";
        }
        Log.i(TAG, "send msg result success");
        service.sendMapResult(String.valueOf(6));
        mSmsController.pushMessage(telephone, text);
    }

    private void handleGetMsg(BluetoothService service, String[] commands) {
        BMessage mBMessageObject = mSmsController.getMessage(Long.valueOf(commands[2]).longValue());
        if (mBMessageObject == null) {
            service.sendMapResult(String.valueOf(-4));
            return;
        }
        try {
            byte[] dataofMsg = mBMessageObject.toString().getBytes("UTF-8");
            service.sendMapDResult(String.valueOf(4) + MapConstants.MAPD_WITH_VCF + String.valueOf(dataofMsg.length) + " ");
            service.sendMapData(dataofMsg);
        } catch (UnsupportedEncodingException e) {
        }
    }

    private void handleGetList(BluetoothService service, String[] commands) {
        MessageList mMsgListRspCache;
        int listSize = Integer.valueOf(commands[2]).intValue();
        int maxSubjectLen = Integer.valueOf(commands[4]).intValue();
        if (this.mFolder.equals(Mailbox.OUTBOX)) {
            mMsgListRspCache = mSmsController.getMessageList(listSize, maxSubjectLen, Mailbox.FAILED);
        } else {
            mMsgListRspCache = mSmsController.getMessageList(listSize, maxSubjectLen, this.mFolder);
        }
        byte[] dataofList = genXmlBufferOfMsgList(mMsgListRspCache);
        service.sendMapDResult(String.valueOf(3) + MapConstants.MAPD_WITH_XML + String.valueOf(dataofList.length) + " ");
        service.sendMapData(dataofList);
    }

    private void handleSetFolder(BluetoothService service, String[] commands) {
        this.mFolder = commands[3];
        SmsController.mAddress = null;
        SmsController.mPerson = null;
        mKeys.clear();
        Log.i(TAG, "Set Folder the folder is :" + commands[3]);
        service.sendMapResult(String.valueOf(1));
    }

    private byte[] genXmlBufferOfMsgList(MessageList mMsgListRspCache) {
        XmlSerializer serializer = Xml.newSerializer();
        try {
            StringWriter stringWriter = new StringWriter();
            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", Boolean.valueOf(false));
            serializer.startTag(null, "MAP-msg-listing");
            serializer.attribute(null, "version", "1.0");
            for (MessageListItem mMessageItem : mMsgListRspCache.generateMessageItemArray()) {
                serializer.startTag(null, "msg");
                ArrayList<String> messageItemFields = mMessageItem.getMessageItem();
                for (int i = 0; i < messageItemFields.size(); i++) {
                    String value = (String) messageItemFields.get(i);
                    if (value == null) {
                        value = "";
                    }
                    serializer.attribute(null, (String) MapConstants.messageItemField.get(i), value);
                }
                serializer.endTag(null, "msg");
            }
            serializer.endTag(null, "MAP-msg-listing");
            serializer.endDocument();
            serializer.flush();
            return stringWriter.toString().getBytes("UTF-8");
        } catch (Exception e) {
            Log.e("Exception", "error occurred while creating xml file");
            return null;
        }
    }

    private String parse(String vcard) {
        for (String element : vcard.split(BMessage.CRLF)) {
            String[] item = element.split(":");
            if (item.length >= 2) {
                String key = item[0].trim();
                String value = item[1].trim();
                if (key.equals(VCard.TELEPHONE)) {
                    return value;
                }
            }
        }
        return null;
    }
}
