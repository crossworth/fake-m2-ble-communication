package com.zhuoyou.plugin.bluetooth.connection;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.zhuoyou.plugin.bluetooth.attach.PlugBean;
import com.zhuoyou.plugin.bluetooth.attach.PlugUtils;
import com.zhuoyou.plugin.bluetooth.attach.PluginManager;
import com.zhuoyou.plugin.bluetooth.data.MessageHeader;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import com.zhuoyou.plugin.bluetooth.data.SmsMessageBody;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;

public class CustomCmd {
    public static final String BT_BROADCAST_ACTION_DEVICE_NAME = "com.mtk.connection.BT_BROADCAST_ACTION_DEVICE_NAME";
    public static final int CMD_BASE = 32;
    public static final int CMD_GET_BATTERY = 3;
    public static final int CMD_GET_DEVICE_HARDWARE_VERSION = 97;
    public static final int CMD_GET_FIRMWARE_VERSION = 5;
    private static final Handler CMD_HANDLER = new C11971();
    public static final int CMD_INCOMING_NAME = 32;
    public static final int CMD_REMOTE_READ_SMS = 64;
    public static final int CMD_SYNC_PERSONAL_DATA = 116;
    public static final int CMD_SYNC_SLEEP_MSG = 129;
    public static final int CMD_SYNC_SPORT_DATA = 113;
    public static final int CMD_SYNC_TIME = 2;
    public static final int CMD_VISITING_CARD = 48;
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_TYPE = "DEVICE_TYPE";
    private static final String PLUG_PACKAGENAME_PREX = "com.zhuoyou.plugin.";
    private static final String TAG = "CustomCmd";
    public static final String TYD_NUMBER = "99999999999";
    private static Context mCtx = RunningApp.getInstance().getApplicationContext();
    private static PackageManager mPackageManager = mCtx.getPackageManager();

    static class C11971 extends Handler {
        C11971() {
        }

        public void handleMessage(Message msg) {
            int index;
            Context ctx = RunningApp.getInstance().getApplicationContext();
            char[] c_ucs2 = (char[]) msg.obj;
            char[] c_platform = new char[20];
            int i = 0;
            int index2 = 1;
            while (i < 20) {
                index = index2 + 1;
                c_platform[i] = c_ucs2[index2];
                i++;
                index2 = index;
            }
            char[] c_verno = new char[8];
            i = 0;
            while (i < 8) {
                index = index2 + 1;
                c_verno[i] = c_ucs2[index2];
                i++;
                index2 = index;
            }
            c_tag = new char[4];
            index = index2 + 1;
            c_tag[0] = c_ucs2[index2];
            index2 = index + 1;
            c_tag[1] = c_ucs2[index];
            index = index2 + 1;
            c_tag[2] = c_ucs2[index2];
            index2 = index + 1;
            c_tag[3] = c_ucs2[index];
            char[] c_msg = new char[(c_ucs2.length - index2)];
            System.arraycopy(c_ucs2, index2, c_msg, 0, c_ucs2.length - index2);
            String str = new String(c_msg);
            Intent intent;
            switch (msg.what) {
                case 2:
                    BluetoothService main = BluetoothService.getInstance();
                    if (main != null) {
                        Log.e(CustomCmd.TAG, "need sync time , send now");
                        main._sendSyncTime();
                        return;
                    }
                    Log.e(CustomCmd.TAG, "send custom sync time error");
                    return;
                case 3:
                    intent = new Intent("com.tyd.bt.device.battery");
                    intent.putExtra("tag", c_tag);
                    ctx.sendBroadcast(intent);
                    return;
                case 5:
                    intent = new Intent("com.tyd.bt.device.firmware");
                    intent.putExtra("content", str);
                    ctx.sendBroadcast(intent);
                    return;
                case 32:
                    Log.i(CustomCmd.TAG, "incoming number =" + str);
                    if (!str.equals("")) {
                        CustomCmd.sendCustomCmd(32, Util.getContactName(ctx, str), c_tag);
                        return;
                    }
                    return;
                case 48:
                    Log.i(CustomCmd.TAG, "spp connected device name =" + str);
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction(CustomCmd.BT_BROADCAST_ACTION_DEVICE_NAME);
                    broadcastIntent.putExtra(CustomCmd.DEVICE_NAME, str);
                    broadcastIntent.putExtra(CustomCmd.DEVICE_TYPE, c_tag[0]);
                    ctx.sendBroadcast(broadcastIntent);
                    return;
                case 64:
                    int sms_id = c_tag[0];
                    Log.i(CustomCmd.TAG, "read sms = id prev(int) = " + sms_id);
                    sms_id -= 32;
                    Log.i(CustomCmd.TAG, "read sms = id curr(int) = " + sms_id);
                    Util.setUnreadSmsToRead(ctx, sms_id);
                    return;
                case 97:
                    Tools.setHardwareVersion(String.valueOf(str));
                    Log.i("hph", "content HARDWARE_VERSION=" + str);
                    Log.i("hph", "getHardwareVersion=" + Tools.getHardwareVersion());
                    return;
                case CustomCmd.CMD_SYNC_SPORT_DATA /*113*/:
                    Log.i(DataBaseContants.TABLE_SLEEP, "running:c_tag:" + String.valueOf(c_tag) + "\t s_utf8:" + str);
                    BluetoothDevice device = BtProfileReceiver.getRemoteDevice();
                    String name = device.getName();
                    String address = device.getAddress();
                    String productName = Util.getProductName(name);
                    intent = new Intent("com.zhuoyou.plugin.running.get");
                    intent.putExtra("tag", c_tag);
                    intent.putExtra("content", str);
                    intent.putExtra("from", productName + "|" + address);
                    ctx.sendBroadcast(intent);
                    return;
                case CustomCmd.CMD_SYNC_PERSONAL_DATA /*116*/:
                    intent = new Intent("com.zhuoyou.plugin.running.sync.personal");
                    intent.putExtra("tag", c_tag);
                    intent.putExtra("content", str);
                    ctx.sendBroadcast(intent);
                    return;
                case CustomCmd.CMD_SYNC_SLEEP_MSG /*129*/:
                    Log.i(DataBaseContants.TABLE_SLEEP, "sleep:c_tag:" + String.valueOf(c_tag) + "\t s_utf8:" + str);
                    intent = new Intent("com.zhuoyou.plugin.running.sleep");
                    intent.putExtra("tag", c_tag);
                    intent.putExtra("content", str);
                    ctx.sendBroadcast(intent);
                    return;
                default:
                    if (!CustomCmd.praserInPlug(msg.what, c_tag, str)) {
                        Log.e(CustomCmd.TAG, "未知CMD =" + msg.what);
                        return;
                    }
                    return;
            }
        }
    }

    public static boolean praser(String address, String message) {
        if (!address.equals(TYD_NUMBER)) {
            return false;
        }
        char[] c_ucs2 = message.toCharArray();
        char c_cmd = c_ucs2[0];
        if (c_cmd > ' ') {
            CMD_HANDLER.obtainMessage(c_cmd - 32, c_ucs2).sendToTarget();
        } else {
            Log.e(TAG, "小于0X20的命令暂不被支持CMD =" + c_cmd);
        }
        return true;
    }

    public static void sendCustomCmd(int cmd, String s) {
        sendCustomCmd(cmd, s, new char[]{'ÿ', 'ÿ', 'ÿ', 'ÿ'});
    }

    public static void sendCustomCmd(int cmd, String s, char[] tag) {
        String content = "";
        Log.i(TAG, "sendCustomCmd cmd= " + cmd + " ||s = " + s);
        switch (cmd) {
            case 32:
                content = buildMsgContent(cmd + 32, s, tag);
                break;
            case 64:
                content = buildMsgContent(cmd + 32, s, tag);
                break;
            default:
                content = buildMsgContent(cmd + 32, s, tag);
                break;
        }
        MessageHeader header = new MessageHeader();
        header.setCategory(MessageObj.CATEGORY_NOTI);
        header.setSubType(MessageObj.SUBTYPE_SMS);
        header.setMsgId(Util.genMessageId());
        header.setAction(MessageObj.ACTION_ADD);
        String phoneNum = TYD_NUMBER;
        int timestamp = Util.getUtcTime(Calendar.getInstance().getTimeInMillis());
        SmsMessageBody body = new SmsMessageBody();
        body.setSender("TYD");
        body.setNumber(phoneNum);
        body.setContent(content);
        body.setTimestamp(timestamp);
        MessageObj smsMessageData = new MessageObj();
        smsMessageData.setDataHeader(header);
        smsMessageData.setDataBody(body);
        BluetoothService.getInstance().sendSmsMessage(smsMessageData);
    }

    public static String buildMsgContent(int cmd, String s, char[] c_tag) {
        String s_utf8 = "";
        char[] c_cmd = new char[]{(char) cmd};
        char[] c_msg = s.toCharArray();
        char[] c_ucs2 = new char[((c_cmd.length + c_tag.length) + c_msg.length)];
        System.arraycopy(c_cmd, 0, c_ucs2, 0, c_cmd.length);
        System.arraycopy(c_tag, 0, c_ucs2, c_cmd.length, c_tag.length);
        System.arraycopy(c_msg, 0, c_ucs2, c_cmd.length + c_tag.length, c_msg.length);
        return new String(c_ucs2);
    }

    public static boolean praserInPlug(int cmd, char[] tag, String content) {
        if (PluginManager.getInstance() == null) {
            return false;
        }
        List<PlugBean> beans = PluginManager.getInstance().getPlugBeans();
        if (beans == null) {
            return false;
        }
        String mPackageName = packageNameByCmd(cmd);
        Intent intent;
        if (cheackPlugs(mPackageName).booleanValue()) {
            if (mPackageName.equals("com.zhuoyou.plugin.antilost")) {
                intent = new Intent("com.tyd.plugin.receiver.sendmsg");
                intent.putExtra("plugin_cmd", 23);
                intent.putExtra("plugin_content", "antilost");
                mCtx.sendBroadcast(intent);
            }
        } else if (mPackageName.equals("com.zhuoyou.plugin.antilost")) {
            intent = new Intent("com.tyd.plugin.receiver.sendmsg");
            intent.putExtra("plugin_cmd", 23);
            intent.putExtra("plugin_content", "antilost");
            intent.putExtra("plugin_tag", getTag1());
            mCtx.sendBroadcast(intent);
        } else if (mPackageName.equals("com.zhuoyou.plugin.autocamera")) {
            intent = new Intent("com.tyd.plugin.receiver.sendmsg");
            intent.putExtra("plugin_cmd", 83);
            intent.putExtra("plugin_content", "autocamera");
            intent.putExtra("plugin_tag", getTag2());
            mCtx.sendBroadcast(intent);
        }
        for (int i = 0; i < beans.size(); i++) {
            PlugBean bean = (PlugBean) beans.get(i);
            if (!bean.isSystem && bean.isInstalled) {
                String[] cmds = bean.mSupportCmd.split("\\|");
                for (int n = 0; n < cmds.length; n++) {
                    if (!cmds[n].equals("")) {
                        int s_cmd = Integer.parseInt(cmds[n], 16);
                        if (s_cmd == cmd && bean.mWorkMethod != null) {
                            for (Entry entry : bean.mWorkMethod.entrySet()) {
                                String key = (String) entry.getKey();
                                String value = (String) entry.getValue();
                                if (Integer.parseInt(key, 16) == s_cmd) {
                                    PlugUtils.invoke_method(bean, value, tag, content);
                                    Log.i("gchk", key + "   " + value);
                                    return true;
                                }
                            }
                            continue;
                        }
                    }
                }
                continue;
            }
        }
        return false;
    }

    private static Boolean cheackPlugs(String name) {
        List<String> mInstalledPlugs = new ArrayList();
        for (PackageInfo pkg : mPackageManager.getInstalledPackages(8192)) {
            if (pkg.packageName.startsWith(PLUG_PACKAGENAME_PREX)) {
                mInstalledPlugs.add(pkg.packageName);
            }
        }
        if (mInstalledPlugs != null && mInstalledPlugs.size() > 0) {
            for (int i = 0; i < mInstalledPlugs.size(); i++) {
                if (((String) mInstalledPlugs.get(i)).equals(name)) {
                    return Boolean.valueOf(true);
                }
            }
        }
        return Boolean.valueOf(false);
    }

    private static String packageNameByCmd(int cmd) {
        String name = "";
        int[] command = new int[]{16, 17, 18, 19, 20, 21, 23, 80, 82, 84};
        for (int i = 0; i < command.length; i++) {
            if (command[i] == cmd) {
                if (i < 7) {
                    name = "com.zhuoyou.plugin.antilost";
                } else {
                    name = "com.zhuoyou.plugin.autocamera";
                }
            }
        }
        return name;
    }

    private static char[] getTag1() {
        return new char[]{'!', 'ÿ', 'ÿ', 'ÿ'};
    }

    private static char[] getTag2() {
        return new char[]{'\"', 'ÿ', 'ÿ', 'ÿ'};
    }
}
