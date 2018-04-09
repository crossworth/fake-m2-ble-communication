package com.zhuoyou.plugin.running;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.action.ActionWelcomeInfo;
import com.zhuoyou.plugin.action.CacheTool;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.cloud.NetMsgCode;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import p031u.aly.au;

public class RunningApp extends Application {
    private static final String TAG = RunningApp.class.getSimpleName();
    public static boolean isBLESupport = false;
    public static String mCurrentConnectDeviceType;
    private static String mDeviceName = null;
    private static Typeface mNumberTP;
    private static RunningApp sInstance = null;
    private Handler main_handler;
    private CacheTool mcachetool = new CacheTool(this);

    class C14021 extends Handler {

        class C14011 implements Runnable {
            C14011() {
            }

            public void run() {
                RunningApp.this.LoadwelcodeAd();
            }
        }

        C14021() {
        }

        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    switch (msg.arg1) {
                        case NetMsgCode.APP_RUN_ACTION_INIT /*100011*/:
                            RunningApp.this.mcachetool.SaveActionInitDate(msg.obj);
                            new Thread(new C14011()).start();
                            break;
                        case NetMsgCode.ACTION_GET_MSG /*100012*/:
                            RunningApp.this.mcachetool.SaveMsgList(msg.obj);
                            break;
                        default:
                            break;
                    }
                case 404:
                    Toast.makeText(RunningApp.this.getApplicationContext(), "net connect is error ,get data failed!", 0);
                    break;
            }
            super.dispatchMessage(msg);
        }
    }

    public RunningApp() {
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        this.main_handler = new C14021();
    }

    public static RunningApp getInstance() {
        return sInstance;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate(), RunningApp create!");
        super.onCreate();
        sInstance = this;
        MainService.getInstance();
        BluetoothService.getInstance();
        if (isSupportBle()) {
            BleManagerService.getInstance();
        }
        MobclickAgent.setScenarioType(getApplicationContext(), EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(true);
        getDeviceInfo(getApplicationContext());
    }

    public Handler GetAppHandler() {
        return this.main_handler;
    }

    public CacheTool GetCacheTool() {
        return this.mcachetool;
    }

    @SuppressLint({"NewApi"})
    private boolean isSupportBle() {
        isBLESupport = false;
        if (VERSION.SDK_INT >= 18) {
            isBLESupport = getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        } else {
            isBLESupport = false;
        }
        Log.d(TAG, "isSupportBle = " + isBLESupport);
        return isBLESupport;
    }

    public static Typeface getCustomChineseFont() {
        if (mNumberTP == null) {
            mNumberTP = Typeface.createFromAsset(sInstance.getApplicationContext().getAssets(), "font/fzlantingblackbold.ttf");
        }
        return mNumberTP;
    }

    public static Typeface getCustomNumberFont() {
        if (mNumberTP == null) {
            mNumberTP = Typeface.createFromAsset(sInstance.getApplicationContext().getAssets(), "font/cmtattoodragon.ttf");
        }
        return mNumberTP;
    }

    public static void setCurrentConnectDeviceType(String CurrentConnectDeviceType) {
        mCurrentConnectDeviceType = CurrentConnectDeviceType;
    }

    public static String getDeviceName() {
        return mDeviceName;
    }

    public String GetFileName(String url) {
        String filename = "";
        if (url == null) {
            return filename;
        }
        String tmp = url;
        String file_tmp = url;
        for (int i = 0; i < 5; i++) {
            tmp = tmp.substring(0, tmp.lastIndexOf("/"));
        }
        for (String aa : file_tmp.substring(tmp.length() + 1).split("/")) {
            filename = filename + aa;
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }

    private void LoadwelcodeAd() {
        FileInputStream fis;
        Drawable d;
        OutOfMemoryError e;
        FileOutputStream fileOutputStream;
        byte[] buffer;
        int byteread;
        Exception e2;
        Throwable th;
        String url = null;
        FileInputStream fis2 = null;
        InputStream i = null;
        ActionWelcomeInfo mwelcomedate = this.mcachetool.GetWelcomeDate();
        if (mwelcomedate != null) {
            url = mwelcomedate.GetImgUrl();
            try {
                url = url.substring(0, url.lastIndexOf("/") + 1) + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8").replace(SocializeConstants.OP_DIVIDER_PLUS, "%20");
            } catch (UnsupportedEncodingException e3) {
                e3.printStackTrace();
            }
        }
        if (url != null && !url.equals("")) {
            DataInputStream in;
            String fileName = GetFileName(url);
            String filePath = Tools.getSDPath() + "/Running/download/cache";
            File dirs = new File(filePath.toString());
            if (!dirs.exists()) {
                dirs.mkdirs();
            }
            File f = new File(filePath, fileName);
            if (f.exists()) {
                try {
                    fis = new FileInputStream(f);
                    try {
                        d = Drawable.createFromStream(fis, "src");
                        fis.close();
                    } catch (OutOfMemoryError e4) {
                        e = e4;
                        fis2 = fis;
                        e.printStackTrace();
                        System.gc();
                        fis = fis2;
                        i = (InputStream) new URL(url).getContent();
                        in = new DataInputStream(i);
                        fileOutputStream = new FileOutputStream(f);
                        buffer = new byte[1024];
                        while (true) {
                            byteread = in.read(buffer);
                            if (byteread != -1) {
                                break;
                            }
                            fileOutputStream.write(buffer, 0, byteread);
                        }
                        in.close();
                        fileOutputStream.close();
                        fis2 = new FileInputStream(f);
                        d = Drawable.createFromStream(i, "src");
                        fis2.close();
                        i.close();
                        if (i != null) {
                            try {
                                i.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                                return;
                            }
                        }
                    } catch (Exception e6) {
                        e2 = e6;
                        fis2 = fis;
                        e2.printStackTrace();
                        fis = fis2;
                        i = (InputStream) new URL(url).getContent();
                        in = new DataInputStream(i);
                        fileOutputStream = new FileOutputStream(f);
                        buffer = new byte[1024];
                        while (true) {
                            byteread = in.read(buffer);
                            if (byteread != -1) {
                                break;
                            }
                            fileOutputStream.write(buffer, 0, byteread);
                        }
                        in.close();
                        fileOutputStream.close();
                        fis2 = new FileInputStream(f);
                        d = Drawable.createFromStream(i, "src");
                        fis2.close();
                        i.close();
                        if (i != null) {
                            i.close();
                        }
                    }
                } catch (OutOfMemoryError e7) {
                    e = e7;
                    e.printStackTrace();
                    System.gc();
                    fis = fis2;
                    i = (InputStream) new URL(url).getContent();
                    in = new DataInputStream(i);
                    fileOutputStream = new FileOutputStream(f);
                    buffer = new byte[1024];
                    while (true) {
                        byteread = in.read(buffer);
                        if (byteread != -1) {
                            break;
                        }
                        fileOutputStream.write(buffer, 0, byteread);
                    }
                    in.close();
                    fileOutputStream.close();
                    fis2 = new FileInputStream(f);
                    d = Drawable.createFromStream(i, "src");
                    fis2.close();
                    i.close();
                    if (i != null) {
                        i.close();
                    }
                } catch (Exception e8) {
                    e2 = e8;
                    e2.printStackTrace();
                    fis = fis2;
                    i = (InputStream) new URL(url).getContent();
                    in = new DataInputStream(i);
                    fileOutputStream = new FileOutputStream(f);
                    buffer = new byte[1024];
                    while (true) {
                        byteread = in.read(buffer);
                        if (byteread != -1) {
                            break;
                        }
                        fileOutputStream.write(buffer, 0, byteread);
                    }
                    in.close();
                    fileOutputStream.close();
                    fis2 = new FileInputStream(f);
                    d = Drawable.createFromStream(i, "src");
                    fis2.close();
                    i.close();
                    if (i != null) {
                        i.close();
                    }
                }
                i = (InputStream) new URL(url).getContent();
                in = new DataInputStream(i);
                fileOutputStream = new FileOutputStream(f);
                buffer = new byte[1024];
                while (true) {
                    byteread = in.read(buffer);
                    if (byteread != -1) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, byteread);
                }
                in.close();
                fileOutputStream.close();
                fis2 = new FileInputStream(f);
                try {
                    d = Drawable.createFromStream(i, "src");
                    fis2.close();
                    i.close();
                    if (i != null) {
                        i.close();
                    }
                } catch (OutOfMemoryError e9) {
                    e = e9;
                    try {
                        e.printStackTrace();
                        System.gc();
                        if (i != null) {
                            try {
                                i.close();
                            } catch (IOException e52) {
                                e52.printStackTrace();
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (i != null) {
                            try {
                                i.close();
                            } catch (IOException e522) {
                                e522.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e10) {
                    e2 = e10;
                    e2.printStackTrace();
                    if (i != null) {
                        try {
                            i.close();
                        } catch (IOException e5222) {
                            e5222.printStackTrace();
                            return;
                        }
                    }
                }
            }
            fis = fis2;
            try {
                i = (InputStream) new URL(url).getContent();
                in = new DataInputStream(i);
                fileOutputStream = new FileOutputStream(f);
                buffer = new byte[1024];
                while (true) {
                    byteread = in.read(buffer);
                    if (byteread != -1) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, byteread);
                }
                in.close();
                fileOutputStream.close();
                fis2 = new FileInputStream(f);
                d = Drawable.createFromStream(i, "src");
                fis2.close();
                i.close();
                if (i != null) {
                    i.close();
                }
            } catch (OutOfMemoryError e11) {
                e = e11;
                fis2 = fis;
                e.printStackTrace();
                System.gc();
                if (i != null) {
                    i.close();
                }
            } catch (Exception e12) {
                e2 = e12;
                fis2 = fis;
                e2.printStackTrace();
                if (i != null) {
                    i.close();
                }
            } catch (Throwable th3) {
                th = th3;
                fis2 = fis;
                if (i != null) {
                    i.close();
                }
                throw th;
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static boolean checkPermission(Context context, String permission) {
        if (VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == 0) {
                return true;
            }
            return false;
        } else if (context.getPackageManager().checkPermission(permission, context.getPackageName()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDeviceInfo(Context context) {
        try {
            JSONObject json = new JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
            String device_id = null;
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                device_id = tm.getDeviceId();
            }
            String mac = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            json.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Secure.getString(context.getContentResolver(), "android_id");
            }
            json.put(au.f3592u, device_id);
            Log.i(TAG, "json.toString()=" + json.toString());
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
