package com.zhuoyi.system.config;

import android.text.TextUtils;
import android.util.Log;
import com.zhuoyi.system.promotion.listener.ZyPromSDK;
import com.zhuoyi.system.util.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ZySDKConfig {
    public static final String SDK_VERSION_NAME = "2.0.10";
    public static String[] SaleDelayAppIds = new String[]{"0000000000", "0000100002"};
    private static ZySDKConfig instance = null;
    private boolean isAutoTestIn = false;
    private boolean isDebugMode = false;
    private boolean isOpenLog = false;
    private String mLocAddress = null;
    private String mLocChannel = null;
    private Properties f3485p = null;

    public static ZySDKConfig getInstance() {
        if (instance == null) {
            instance = new ZySDKConfig();
        }
        return instance;
    }

    private ZySDKConfig() {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        if (this.f3485p == null) {
            File myFile = FileUtils.getDebugFile();
            if (myFile != null && myFile.exists()) {
                this.f3485p = new Properties();
                FileInputStream fis = null;
                try {
                    FileInputStream fis2 = new FileInputStream(myFile);
                    try {
                        this.f3485p.load(fis2);
                        if (fis2 != null) {
                            try {
                                fis2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e2 = e4;
                        fis = fis2;
                        try {
                            e2.printStackTrace();
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                            initDebugMode();
                            initLog();
                            initAutoTest();
                            initLocAddress();
                            initLocChannel();
                        } catch (Throwable th2) {
                            th = th2;
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e322) {
                                    e322.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e5) {
                        e322 = e5;
                        fis = fis2;
                        e322.printStackTrace();
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e3222) {
                                e3222.printStackTrace();
                            }
                        }
                        initDebugMode();
                        initLog();
                        initAutoTest();
                        initLocAddress();
                        initLocChannel();
                    } catch (Throwable th3) {
                        th = th3;
                        fis = fis2;
                        if (fis != null) {
                            fis.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e6) {
                    e2 = e6;
                    e2.printStackTrace();
                    if (fis != null) {
                        fis.close();
                    }
                    initDebugMode();
                    initLog();
                    initAutoTest();
                    initLocAddress();
                    initLocChannel();
                } catch (IOException e7) {
                    e3222 = e7;
                    e3222.printStackTrace();
                    if (fis != null) {
                        fis.close();
                    }
                    initDebugMode();
                    initLog();
                    initAutoTest();
                    initLocAddress();
                    initLocChannel();
                }
            }
        }
        initDebugMode();
        initLog();
        initAutoTest();
        initLocAddress();
        initLocChannel();
    }

    private void initLocAddress() {
        if (this.f3485p != null) {
            String address = this.f3485p.getProperty("loc.add");
            if (!TextUtils.isEmpty(address)) {
                this.mLocAddress = address;
            }
        }
        Log.d(ZyPromSDK.TAG, "initLocAddress = " + this.mLocAddress);
    }

    private void initLocChannel() {
        if (this.f3485p != null) {
            String channel = this.f3485p.getProperty("loc.cha");
            if (!TextUtils.isEmpty(channel)) {
                this.mLocChannel = channel;
            }
        }
        Log.d(ZyPromSDK.TAG, "initLocChannel = " + this.mLocChannel);
    }

    private void initDebugMode() {
        if (this.f3485p != null) {
            if ("b".equals(this.f3485p.getProperty("a"))) {
                this.isDebugMode = true;
            }
        }
        Log.d(ZyPromSDK.TAG, "initDebugMode = " + this.isDebugMode);
    }

    private void initLog() {
        if (this.f3485p != null) {
            if ("1".equals(this.f3485p.getProperty("b"))) {
                this.isOpenLog = true;
            }
        }
        Log.d(ZyPromSDK.TAG, "isOpenLog = " + this.isOpenLog);
    }

    private void initAutoTest() {
        if (this.f3485p != null) {
            if ("1".equals(this.f3485p.getProperty("c"))) {
                this.isAutoTestIn = true;
            }
        }
    }

    public boolean isAutoTestIn() {
        return this.isAutoTestIn;
    }

    public boolean isOpenLog() {
        return this.isOpenLog;
    }

    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    public String getLocAddress() {
        return this.mLocAddress;
    }

    public String getLocChannel() {
        return this.mLocChannel;
    }
}
