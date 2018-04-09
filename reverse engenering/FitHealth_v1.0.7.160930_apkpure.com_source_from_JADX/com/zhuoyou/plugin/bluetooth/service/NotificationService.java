package com.zhuoyou.plugin.bluetooth.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.RemoteViews;
import com.tencent.connect.common.Constants;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.AppList;
import com.zhuoyou.plugin.bluetooth.data.IgnoreList;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.NoDataException;
import com.zhuoyou.plugin.bluetooth.data.NotificationMessageBody;
import com.zhuoyou.plugin.bluetooth.data.PreferenceData;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class NotificationService extends AccessibilityService {
    private static final long EVENT_NOTIFICATION_TIMEOUT_MILLIS = 0;
    private static final int NOTIFICATION_CONTENT_TYPE = 10;
    private static final int NOTIFICATION_TITLE_TYPE = 9;
    private Handler NotificationHandler;
    private AccessibilityEvent mAccessibilityEvent;
    private Notification mNotification;
    private SendNotficationThread mSNThread;

    private class SendNotficationThread extends Thread {
        public static final int MESSAGE_SEND_NOTIFICATION = 1;
        @SuppressLint({"HandlerLeak"})
        private Handler mHandler;
        ThreadNotfication mThreadNotfication;

        class C12101 extends Handler {
            C12101() {
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        SendNotficationThread.this.mThreadNotfication = (ThreadNotfication) msg.obj;
                        if (SendNotficationThread.this.mThreadNotfication != null) {
                            SendNotficationThread.this.sendNotfications(SendNotficationThread.this.mThreadNotfication);
                            SendNotficationThread.this.mThreadNotfication = null;
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        private SendNotficationThread() {
            this.mThreadNotfication = null;
        }

        public void run() {
            Log.i("hph", "SendNotficationThread");
            Looper.prepare();
            this.mHandler = new C12101();
            Looper.loop();
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        private MessageHeader createNotificationHeader() {
            MessageHeader header = new MessageHeader();
            header.setCategory(MessageObj.CATEGORY_NOTI);
            header.setSubType(MessageObj.SUBTYPE_NOTI);
            header.setMsgId(Util.genMessageId());
            header.setAction(MessageObj.ACTION_ADD);
            return header;
        }

        private NotificationMessageBody createNotificationBody(ThreadNotfication threadNotfication) {
            int timestamp;
            ApplicationInfo appinfo = Util.getAppInfo(NotificationService.this.getBaseContext(), threadNotfication.packageName);
            String appName = Util.getAppName(NotificationService.this.getBaseContext(), appinfo);
            Bitmap sendIcon = Util.getMessageIcon(NotificationService.this.getBaseContext(), appinfo);
            if (System.currentTimeMillis() - threadNotfication.when > 3600000) {
                timestamp = Util.getUtcTime(System.currentTimeMillis());
            } else {
                timestamp = Util.getUtcTime(threadNotfication.when);
            }
            Map<Object, Object> applist = AppList.getInstance().getAppList();
            if (!applist.containsValue(threadNotfication.packageName)) {
                int max = Integer.parseInt(applist.get(AppList.MAX_APP).toString());
                applist.remove(AppList.MAX_APP);
                max++;
                applist.put(AppList.MAX_APP, Integer.valueOf(max));
                applist.put(Integer.valueOf(max), threadNotfication.packageName);
                AppList.getInstance().saveAppList(applist);
            }
            String title = "";
            String content = "";
            String[] textList = threadNotfication.textList;
            if (textList != null) {
                if (textList.length > 0 && textList[0] != null) {
                    title = textList[0];
                }
                if (textList.length > 1 && textList[1] != null) {
                    content = textList[1];
                }
                if (title.length() > 128) {
                    title = title.substring(0, 128) + Util.TEXT_POSTFIX;
                }
                if (content.length() > 256) {
                    content = content.substring(0, 256) + Util.TEXT_POSTFIX;
                }
            }
            String tickerText = "";
            if (threadNotfication.tickerText != null && content.length() == 0) {
                tickerText = threadNotfication.tickerText.toString();
            }
            if (tickerText.length() > 128) {
                tickerText = tickerText.substring(0, 128) + Util.TEXT_POSTFIX;
            }
            if (tickerText.length() > 0) {
                tickerText = "[".concat(tickerText).concat("]");
            }
            applist = AppList.getInstance().getAppList();
            String appID = Util.getKeyFromValue(threadNotfication.packageName);
            NotificationMessageBody body = new NotificationMessageBody();
            body.setSender(appName);
            body.setAppID(appID);
            body.setTitle(title);
            body.setContent(content);
            body.setTickerText(tickerText);
            body.setTimestamp(timestamp);
            body.setIcon(sendIcon);
            return body;
        }

        public void sendNotfications(ThreadNotfication threadNotfication) {
            Log.i("hph", "sendNotfications000");
            try {
                MessageObj notificationMessage = new MessageObj();
                notificationMessage.setDataHeader(createNotificationHeader());
                notificationMessage.setDataBody(createNotificationBody(threadNotfication));
                Log.i("hph", "msgContent=" + notificationMessage.getDataBody().getContent());
                byte[] data = genBytesFromObject(notificationMessage);
                BluetoothService service = BluetoothService.getInstance();
                if (service != null) {
                    service.sendNotiMessageByData(data);
                }
                noticeBleNewWeChat();
            } catch (Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    Log.w("Exception during write", e);
                }
            }
        }

        public byte[] genBytesFromObject(MessageObj dataObj) {
            if (dataObj == null) {
                return null;
            }
            byte[] bArr = null;
            try {
                return dataObj.genXmlBuff();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
                return bArr;
            } catch (IllegalStateException e12) {
                e12.printStackTrace();
                return bArr;
            } catch (IOException e13) {
                e13.printStackTrace();
                return bArr;
            } catch (XmlPullParserException e14) {
                e14.printStackTrace();
                return bArr;
            } catch (NoDataException e) {
                e.printStackTrace();
                return bArr;
            }
        }

        private void noticeBleNewWeChat() {
            Log.i("NotificationService", "noticeBleNewWeChat");
            NotificationService.this.sendBroadcast(new Intent(BleManagerService.ACTION_NOTICE_NEW_WECHAT_MSG));
        }
    }

    private class ThreadNotfication {
        public String appID;
        public CharSequence packageName;
        public String[] textList;
        public CharSequence tickerText;
        public long when;

        private ThreadNotfication() {
        }
    }

    public NotificationService() {
        this.mAccessibilityEvent = null;
        this.mNotification = null;
        this.mSNThread = null;
        this.mSNThread = new SendNotficationThread();
        this.mSNThread.start();
        this.NotificationHandler = this.mSNThread.getHandler();
        Log.i("hph", "(NotificationService");
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("hph", "(onAccessibilityEvent");
        if (event.getEventType() == 64) {
            this.mAccessibilityEvent = event;
            this.mNotification = (Notification) this.mAccessibilityEvent.getParcelableData();
            if (this.mNotification != null) {
                boolean isServiceEnabled = PreferenceData.isNotificationServiceEnable();
                boolean needForward = PreferenceData.isNeedPush();
                Log.i("hph", "(isServiceEnabled");
                if (isServiceEnabled && needForward) {
                    HashSet<String> ignoreList = IgnoreList.getInstance().getIgnoreList();
                    HashSet<String> exclusionList = IgnoreList.getInstance().getExclusionList();
                    CharSequence packagenameString = event.getPackageName();
                    if ((!ignoreList.contains(event.getPackageName()) || exclusionList.contains(event.getPackageName())) && !packagenameString.equals(Constants.MOBILEQQ_PACKAGE_NAME)) {
                        Log.i("caixinxin", "Notice: This notification received!, package name=" + this.mAccessibilityEvent.getPackageName());
                        return;
                    }
                    Message message = new Message();
                    message.what = 1;
                    ThreadNotfication threadNotfication = new ThreadNotfication();
                    threadNotfication.textList = getNotificationText();
                    threadNotfication.packageName = this.mAccessibilityEvent.getPackageName();
                    threadNotfication.appID = Util.getKeyFromValue(threadNotfication.packageName);
                    threadNotfication.tickerText = this.mNotification.tickerText;
                    threadNotfication.when = this.mNotification.when;
                    message.obj = threadNotfication;
                    if (this.NotificationHandler == null) {
                        this.NotificationHandler = this.mSNThread.getHandler();
                    }
                    if (this.NotificationHandler != null) {
                        this.NotificationHandler.sendMessage(message);
                    }
                }
            }
        }
    }

    public void onInterrupt() {
    }

    public void onServiceConnected() {
        if (VERSION.SDK_INT < 14) {
            setAccessibilityServiceInfo();
        }
        BluetoothService.setNotificationService(this);
    }

    private void setAccessibilityServiceInfo() {
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = 64;
        accessibilityServiceInfo.feedbackType = 16;
        accessibilityServiceInfo.notificationTimeout = EVENT_NOTIFICATION_TIMEOUT_MILLIS;
        setServiceInfo(accessibilityServiceInfo);
    }

    public boolean onUnbind(Intent intent) {
        BluetoothService.clearNotificationService();
        return false;
    }

    @SuppressLint({"UseSparseArrays"})
    private String[] getNotificationText() {
        try {
            RemoteViews remoteViews = this.mNotification.contentView;
            Class<? extends RemoteViews> remoteViewsClass = remoteViews.getClass();
            HashMap<Integer, String> text = new HashMap();
            Field actionField = null;
            for (Field outerField : remoteViewsClass.getDeclaredFields()) {
                if (outerField.getName().equals("mActions")) {
                    actionField = outerField;
                    break;
                }
            }
            if (actionField == null) {
                return null;
            }
            actionField.setAccessible(true);
            int viewId = 0;
            Iterator it = ((ArrayList) actionField.get(remoteViews)).iterator();
            while (it.hasNext()) {
                Object action = it.next();
                Object value = null;
                Integer type = null;
                for (Field field : action.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.getName().equals("value")) {
                        value = field.get(action);
                    } else if (field.getName().equals("type")) {
                        type = Integer.valueOf(field.getInt(action));
                    } else if (field.getName().equals("methodName") && ((String) field.get(action)).equals("setProgress")) {
                        return null;
                    }
                }
                if (type != null && ((type.intValue() == 9 || type.intValue() == 10) && value != null)) {
                    viewId++;
                    text.put(Integer.valueOf(viewId), value.toString());
                    if (viewId == 2) {
                        break;
                    }
                }
            }
            return (String[]) text.values().toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
