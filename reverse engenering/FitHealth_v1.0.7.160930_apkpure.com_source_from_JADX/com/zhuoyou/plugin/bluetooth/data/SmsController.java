package com.zhuoyou.plugin.bluetooth.data;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.util.Xml;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.bluetooth.service.BTMapService;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlSerializer;

public class SmsController {
    private static final int ADDRESS_COLUMN = 3;
    private static final int BODY_COLUMN = 7;
    private static final int DATE_COLUMN = 2;
    private static final String[] DEFAULT_PROJECTION = new String[]{"_id", MapConstants.SUBJECT, "date", "address", "status", MapConstants.READ, MapConstants.PERSON, "body", MapConstants.THREAD_ID, "type", MapConstants.READ};
    private static final int ID_COLUMN = 0;
    private static final int INVALID_VALUE_ID = -1;
    public static final String MESSAGE_STATUS_SEND_ACTION = "com.mtk.map.SmsController.action.SEND_MESSAGE";
    private static final int READ_COLUMN = 5;
    private static final int STATUS_COLUMN = 4;
    private static final int TYPE_COLUMN = 9;
    public static String mAddress = null;
    public static String mPerson = null;
    private final String EXTRA_MESSAGE_ID = "com.mtk.map.SmsController.action.SENT_MESSAGE_ID";
    private final String MESSAGE_STATUS_DELIVERED_ACTION = "com.mtk.map.SmsController.action.DELIVERED_RESULT";
    private final String MESSAGE_STATUS_SENT_ACTION = "com.mtk.map.SmsController.action.SENT_RESULT";
    private final int MESSAGE_TYPE_DELETE = 100;
    private final int SMS_READ_STATUS = 1;
    private final int SMS_UNREAD_STATUS = 0;
    private final String TAG = "MAP-SmsController";
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final HashMap<Long, Integer> mDeleteFolder = new HashMap();

    class C12001 extends BroadcastReceiver {
        C12001() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                int resultCode = getResultCode();
                if (action.equals("com.mtk.map.SmsController.action.SENT_RESULT")) {
                    SmsController.this.handleSentResult(intent, resultCode);
                } else if (action.equals("com.mtk.map.SmsController.action.DELIVERED_RESULT")) {
                    SmsController.this.handleDeliverResult(intent, resultCode);
                }
                if (action.equals(SmsController.MESSAGE_STATUS_SEND_ACTION)) {
                    SmsController.this.pushMessage(intent.getStringExtra("ADDRESS"), intent.getStringExtra("MESSAGE"));
                }
            }
        }
    }

    public SmsController(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.mtk.map.SmsController.action.SENT_RESULT");
        filter.addAction("com.mtk.map.SmsController.action.DELIVERED_RESULT");
        filter.addAction(MESSAGE_STATUS_SEND_ACTION);
        this.mContext.registerReceiver(new C12001(), filter);
        SmsContentObserver mSmsContentObserver = new SmsContentObserver(this);
        this.mContentResolver.registerContentObserver(Uri.parse(MapConstants.SMS_CONTENT_URI), false, mSmsContentObserver);
        this.mContentResolver.registerContentObserver(Uri.parse(MapConstants.CONVERSATION), false, mSmsContentObserver);
    }

    public void onStop() {
        clearDeletedMessage();
    }

    public MessageList getMessageList(int listSize, int maxSubjectLen, String folder) {
        Uri mailboxUri;
        int mailbox = convertMailboxType(folder);
        String orignator = null;
        String recipient = null;
        int index = 0;
        StringBuilder selection = new StringBuilder();
        ArrayList<String> selectionArgs = new ArrayList();
        String[] projection = DEFAULT_PROJECTION;
        String from = null;
        String to = null;
        if (mailbox != 100) {
            mailboxUri = getMailboxUri(mailbox);
            if (mailboxUri == null) {
                return null;
            }
        }
        mailboxUri = Uri.parse(MapConstants.SMS_CONTENT_URI);
        try {
            Cursor messageCursor = this.mContentResolver.query(mailboxUri, projection, selection.toString(), (String[]) selectionArgs.toArray(new String[selectionArgs.size()]), MapConstants.DEFAULT_SORT_ORDER);
            if (messageCursor == null) {
                return null;
            }
            MessageList list = new MessageList();
            boolean newMessageFlag = false;
            while (messageCursor.moveToNext() && (listSize == 0 || list.getCurrentSize() < listSize)) {
                if (messageCursor.getInt(5) == 1) {
                    newMessageFlag = true;
                }
                String address = messageCursor.getString(3);
                if (mailbox == 1) {
                    from = address;
                } else {
                    to = address;
                }
                if (recipient != null && recipient.length() > 0) {
                    if (doesPhoneNumberMatch(normalizeString(to), null, recipient)) {
                    }
                }
                if (orignator != null && orignator.length() > 0 && mailbox == 1) {
                    if (doesPhoneNumberMatch(normalizeString(from), null, orignator)) {
                    }
                }
                if (listSize > 0) {
                    list.addMessageItem(composeMessageItem(messageCursor, mailbox, maxSubjectLen));
                }
                index++;
                list.addSize(1);
            }
            messageCursor.close();
            if (newMessageFlag) {
                list.setNewMessage();
            }
            list.addSize(index);
            return list;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BMessage getMessage(long id) {
        Cursor messageCursor = this.mContentResolver.query(ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), id & MapConstants.MESSAGE_HANDLE_MASK), DEFAULT_PROJECTION, null, null, null);
        if (messageCursor == null || !messageCursor.moveToFirst()) {
            return null;
        }
        String text = messageCursor.getString(7);
        String address = messageCursor.getString(3);
        int mailbox = messageCursor.getInt(9);
        String orignator = new String();
        String recipient = new String();
        if (mailbox == 1) {
            orignator = address;
            normalizeString(orignator);
        } else {
            recipient = address;
            normalizeString(recipient);
        }
        BMessage bMessage = new BMessage();
        bMessage.reset();
        VCard vCard = new VCard();
        vCard.setTelephone(orignator);
        bMessage.setOrignator(vCard.toString());
        bMessage.setContent(text);
        vCard.reset();
        vCard.setTelephone(recipient);
        bMessage.addRecipient(vCard.toString());
        bMessage.setReadStatus(revertReadStatus(messageCursor.getInt(5)));
        messageCursor.close();
        return bMessage;
    }

    public boolean pushMessage(String telephone, String text) {
        try {
            Log.i("MAP-SmsController", "Start to Push message, the telephone is:" + telephone + " and the text is:" + text);
        } catch (Exception e) {
            String errorString = e.toString();
            if (errorString == null) {
                errorString = "push error";
            }
            Log.w("MAP-SmsController", errorString);
            BluetoothService.getInstance().sendMapResult(String.valueOf(6));
        }
        long messageId = -1;
        if (text != null) {
            if (!text.equals("\n")) {
                text = text.trim();
            }
        }
        String recipient = normalizeString(telephone);
        if (true) {
            ContentValues cv = new ContentValues();
            cv.put("type", Integer.valueOf(4));
            cv.put("date", Long.valueOf(System.currentTimeMillis()));
            cv.put("address", recipient);
            cv.put(MapConstants.READ, Integer.valueOf(1));
            cv.put("body", text);
            cv.put("status", Integer.valueOf(64));
            cv.put(MapConstants.SEEN, Integer.valueOf(0));
            Uri uri = this.mContentResolver.insert(Uri.parse(MapConstants.OUTBOX), cv);
            if (uri != null) {
                Cursor cs = this.mContentResolver.query(uri, new String[]{"_id"}, null, null, null);
                if (cs != null && cs.moveToFirst()) {
                    messageId = cs.getLong(0);
                    cs.close();
                }
            }
        } else {
            messageId = -1;
        }
        if (recipient != null) {
            SmsManager manager = SmsManager.getDefault();
            if (text == null) {
                return false;
            }
            ArrayList<String> messages = manager.divideMessage(text);
            ArrayList<PendingIntent> deliveryIntents = new ArrayList(messages.size());
            ArrayList<PendingIntent> sentIntents = new ArrayList(messages.size());
            for (int i = 0; i < messages.size(); i++) {
                Intent intent = new Intent("com.mtk.map.SmsController.action.SENT_RESULT");
                Intent deliveryIntent = new Intent("com.mtk.map.SmsController.action.DELIVERED_RESULT");
                intent.putExtra("com.mtk.map.SmsController.action.SENT_MESSAGE_ID", messageId);
                deliveryIntent.putExtra("com.mtk.map.SmsController.action.SENT_MESSAGE_ID", messageId);
                if (i == messages.size() - 1) {
                    String EXTRA_FINAL_MESSAGE = "com.mtk.map.SmsController.action.FINAL_MESSAGE";
                    intent.putExtra(EXTRA_FINAL_MESSAGE, true);
                    deliveryIntent.putExtra(EXTRA_FINAL_MESSAGE, true);
                }
                deliveryIntents.add(PendingIntent.getBroadcast(this.mContext, 0, deliveryIntent, 268435456));
                sentIntents.add(PendingIntent.getBroadcast(this.mContext, i, intent, 268435456));
            }
            manager.sendMultipartTextMessage(recipient, null, messages, sentIntents, deliveryIntents);
        }
        return true;
    }

    public boolean setMessageStatus(long id, int state) {
        BluetoothService service = BluetoothService.getInstance();
        Uri uri = ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), id);
        String[] projection = new String[]{MapConstants.READ};
        int newState = state;
        if (newState == -1) {
            return false;
        }
        Cursor cs = this.mContentResolver.query(uri, projection, null, null, null);
        if (cs == null || !cs.moveToFirst()) {
            service.sendMapResult(String.valueOf(-5));
        } else {
            if (cs.getInt(0) == newState) {
                service.sendMapResult(String.valueOf(-5));
            } else {
                ContentValues cv = new ContentValues();
                cv.put(MapConstants.READ, Integer.valueOf(newState));
                this.mContentResolver.update(uri, cv, null, null);
                service.sendMapResult(String.valueOf(5));
            }
            cs.close();
        }
        return true;
    }

    public boolean deleteMessage(long id) {
        BluetoothService service = BluetoothService.getInstance();
        Uri uri = ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), id);
        Cursor cs = this.mContentResolver.query(uri, new String[]{"type"}, null, null, null);
        if (cs == null || !cs.moveToFirst()) {
            service.sendMapResult(String.valueOf(-5));
            return false;
        }
        int mailbox = cs.getInt(0);
        if (mailbox == 100) {
            this.mContentResolver.delete(uri, null, null);
            this.mDeleteFolder.remove(Long.valueOf(id));
        } else {
            ContentValues cv = new ContentValues();
            cv.put("type", Integer.valueOf(100));
            this.mContentResolver.update(uri, cv, null, null);
            this.mDeleteFolder.put(Long.valueOf(id), Integer.valueOf(mailbox));
        }
        service.sendMapResult(String.valueOf(5));
        cs.close();
        return true;
    }

    public void clearDeletedMessage() {
        String[] projection = new String[]{"type"};
        Uri uri = Uri.parse(MapConstants.SMS_CONTENT_URI);
        for (Entry entry : this.mDeleteFolder.entrySet()) {
            uri = ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), ((Long) entry.getKey()).longValue());
            Cursor cs = this.mContentResolver.query(uri, projection, null, null, null);
            if (cs != null && cs.moveToFirst() && cs.getInt(0) == 100) {
                try {
                    this.mContentResolver.delete(uri, null, null);
                } catch (IllegalArgumentException e) {
                }
            }
            if (cs != null) {
                cs.close();
            }
        }
        this.mDeleteFolder.clear();
    }

    public void onMessageEvent(Long key, String oldFolder, int type) {
        if (MapConstants.EVENT_DELETE_S.equals(getEventType(type))) {
            BTMapService.mKeys.add(key);
        }
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            serializer.setOutput(stringWriter);
            serializer.startDocument("UTF-8", Boolean.valueOf(false));
            serializer.startTag(null, "MAP-event-report");
            serializer.attribute(null, "version", "1.0");
            serializer.startTag(null, "event");
            serializer.attribute(null, "type", getEventType(type));
            serializer.attribute(null, "handle", String.valueOf(key.longValue() | MapConstants.SMS_GSM_HANDLE_BASE));
            serializer.attribute(null, "folder", oldFolder);
            serializer.attribute(null, DataBaseContants.MSG_TYPE, MapConstants.MESSAGE_TYPE_SMS_GSM);
            serializer.endTag(null, "event");
            serializer.endTag(null, "MAP-event-report");
            serializer.endDocument();
            serializer.flush();
        } catch (Exception e) {
        }
        BluetoothService service = BluetoothService.getInstance();
        if (stringWriter != null) {
            try {
                byte[] dataOfEventReport = stringWriter.toString().getBytes("UTF-8");
                service.sendMapDResult(String.valueOf(7) + MapConstants.MAPD_WITH_XML + String.valueOf(dataOfEventReport.length) + " ");
                service.sendMapData(dataOfEventReport);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
    }

    private String getEventType(int type) {
        switch (type) {
            case 1:
                return MapConstants.EVENT_NEW_S;
            case 2:
                return MapConstants.EVENT_DELETE_S;
            case 3:
                return MapConstants.EVENT_SHIFT_S;
            default:
                return null;
        }
    }

    private Uri getMailboxUri(int mailbox) {
        switch (mailbox) {
            case 1:
                return Uri.parse(MapConstants.INBOX);
            case 2:
                return Uri.parse(MapConstants.SENT);
            case 3:
                return Uri.parse(MapConstants.DRAFT);
            case 4:
                return Uri.parse(MapConstants.OUTBOX);
            case 5:
                return Uri.parse(MapConstants.FAILED);
            default:
                return null;
        }
    }

    private String normalizeString(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        return text.replaceAll(" ", "").replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "");
    }

    private boolean doesPhoneNumberMatch(String[] targetArray, String[] templateArray) {
        if (targetArray == null || templateArray == null || targetArray.length == 0 || templateArray.length == 0) {
            return false;
        }
        for (String template : templateArray) {
            for (String target : targetArray) {
                if (target.indexOf(template) != 0 || template.contains(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean doesPhoneNumberMatch(String target, String template1, String template2) {
        boolean isTemplateEmpty;
        if (template1 == null && template2 == null) {
            isTemplateEmpty = true;
        } else {
            isTemplateEmpty = false;
        }
        if (target == null) {
            return false;
        }
        if (isTemplateEmpty) {
            return true;
        }
        if (template1 != null && doesPhoneNumberMatch(target.split(";"), template1.split(";"))) {
            return true;
        }
        if (template2 == null || !isPhoneNumber(template2)) {
            return false;
        }
        return target.contains(template2);
    }

    private boolean isPhoneNumber(String number) {
        int numDigits = 0;
        int len = number.length();
        for (int i = 0; i < len; i++) {
            char c = number.charAt(i);
            if (Character.isDigit(c)) {
                numDigits++;
            } else if (!(c == '*' || c == '#' || c == 'N' || c == '.' || c == ';' || c == '-' || c == '(' || c == ')' || c == ' ' || (c == '+' && numDigits == 0))) {
                return false;
            }
        }
        if (numDigits > 0) {
            return true;
        }
        return false;
    }

    private MessageListItem composeMessageItem(Cursor cs, int mailbox, int maxSubjextLen) {
        MessageListItem msg = new MessageListItem();
        int recipientStatus = revertLoadStatus(cs.getInt(4));
        if (recipientStatus == -1) {
            return null;
        }
        int readStatus = revertReadStatus(cs.getInt(5));
        if (readStatus == -1) {
            return null;
        }
        boolean isText = cs.getString(7) != null;
        String data = cs.getString(2);
        msg.setHandle(cs.getLong(0) | MapConstants.SMS_GSM_HANDLE_BASE);
        msg.setSubject(null);
        msg.setDatetime(Long.valueOf(data).longValue());
        String address = cs.getString(3);
        if (mAddress == null || !mAddress.equals(address)) {
            mAddress = address;
            mPerson = Util.getContactName(this.mContext, address);
        }
        if (mailbox == 1) {
            msg.setSenderAddr(address);
            msg.setSenderName(mPerson);
        } else {
            msg.setRecipientAddr(address);
            msg.setRecipientName(mPerson);
        }
        msg.setMsgType();
        if (cs.getString(7) != null) {
            msg.setSize(cs.getString(7).length());
        } else {
            msg.setSize(0);
        }
        msg.setText(isText);
        msg.setRecipientStatus(recipientStatus);
        msg.setAttachSize();
        msg.setReadStatus(readStatus);
        msg.setProtected();
        msg.setPriority();
        return msg;
    }

    private int revertReadStatus(int smsReadStatus) {
        switch (smsReadStatus) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return -1;
        }
    }

    private int convertMailboxType(String mapMailboxType) {
        if (mapMailboxType == null) {
            return -1;
        }
        if (mapMailboxType.equals(Mailbox.INBOX)) {
            return 1;
        }
        if (mapMailboxType.equals(Mailbox.OUTBOX)) {
            return 4;
        }
        if (mapMailboxType.equals(Mailbox.FAILED)) {
            return 5;
        }
        if (mapMailboxType.equals(Mailbox.SENT)) {
            return 2;
        }
        if (mapMailboxType.equals(Mailbox.DRAFT)) {
            return 3;
        }
        if (mapMailboxType.equals(Mailbox.DELETED)) {
            return 100;
        }
        return -1;
    }

    private int revertLoadStatus(int SmsStatus) {
        return 0;
    }

    private void moveMessageToFolder(Context context, Uri uri, int folder, int error, int status) {
        boolean markAsUnread = false;
        boolean markAsRead = false;
        switch (folder) {
            case 1:
            case 3:
                break;
            case 2:
            case 4:
                markAsRead = true;
                break;
            case 5:
            case 6:
                markAsUnread = true;
                break;
            default:
                return;
        }
        ContentValues values = new ContentValues(3);
        values.put("type", Integer.valueOf(folder));
        values.put("status", Integer.valueOf(status));
        if (markAsUnread) {
            values.put(MapConstants.READ, Integer.valueOf(0));
        } else if (markAsRead) {
            values.put(MapConstants.READ, Integer.valueOf(1));
        }
        this.mContentResolver.update(uri, values, null, null);
    }

    private void handleSentResult(Intent intent, int resultCode) {
        int error = intent.getIntExtra("errorCode", 0);
        String[] projection = new String[]{"type"};
        Uri uri = ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), intent.getLongExtra("com.mtk.map.SmsController.action.SENT_MESSAGE_ID", -1));
        Cursor cs = this.mContentResolver.query(uri, projection, null, null, null);
        if (cs != null) {
            if (cs.moveToFirst()) {
                if (resultCode != -1) {
                    moveMessageToFolder(this.mContext, uri, 5, error, 128);
                } else if (cs.getInt(0) == 4) {
                    moveMessageToFolder(this.mContext, uri, 2, error, -1);
                }
                cs.close();
                return;
            }
            cs.close();
        }
    }

    private void handleDeliverResult(Intent intent, int resultCode) {
        byte[] pdu = (byte[]) intent.getExtras().get("pdu");
        Uri uri = ContentUris.withAppendedId(Uri.parse(MapConstants.SMS_CONTENT_URI), intent.getLongExtra("com.mtk.map.SmsController.action.SENT_MESSAGE_ID", -1));
        String[] projection = new String[]{"_id"};
        if (pdu != null && resultCode == -1) {
            SmsMessage message = SmsMessage.createFromPdu(pdu);
            if (message != null) {
                Cursor cs = this.mContentResolver.query(uri, projection, null, null, null);
                if (cs != null && cs.moveToFirst()) {
                    ContentValues cv = new ContentValues();
                    try {
                        cv.put("status", Integer.valueOf(message.getStatus()));
                        this.mContentResolver.update(uri, cv, null, null);
                    } catch (Exception e) {
                        String errorString = e.toString();
                        if (errorString == null) {
                            errorString = "querry error";
                        }
                        Log.w("MAP-SmsController", errorString);
                    }
                }
                if (cs != null) {
                    cs.close();
                }
            }
        }
    }
}
