package com.umeng.socialize.net.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.umeng.socialize.Config;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SocializeNetUtils {
    private static final String TAG = "SocializeNetUtils";

    public static String generateGetURL(String str, Map<String, Object> map) {
        if (TextUtils.isEmpty(str) || map == null || map.size() == 0) {
            return str;
        }
        if (!str.endsWith("?")) {
            str = str + "?";
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = stringBuilder;
        for (String str2 : map.keySet()) {
            String str22;
            if (map.get(str22) != null) {
                stringBuilder2 = stringBuilder2.append(str22 + "=" + URLEncoder.encode(map.get(str22).toString()) + "&");
            }
        }
        StringBuilder stringBuilder3 = new StringBuilder(str);
        try {
            str22 = stringBuilder2.substring(0, stringBuilder2.length() - 1).toString();
            Log.m3248d(TAG, "##### 未加密参数 : " + str22);
            stringBuilder3.append("ud_get=" + AesHelper.encryptNoPadding(str22, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.m3248d(TAG, "#### 完整请求链接 : " + stringBuilder3.toString());
        return stringBuilder3.toString();
    }

    public static Map<String, Object> getBaseQuery(Context context) {
        Map<String, Object> hashMap = new HashMap();
        Object deviceId = DeviceConfig.getDeviceId(context);
        if (!TextUtils.isEmpty(deviceId)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, deviceId);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MD5IMEI, AesHelper.md5(deviceId));
        }
        CharSequence mac = DeviceConfig.getMac(context);
        if (TextUtils.isEmpty(mac)) {
            Log.m3260w(TAG, "Get MacAddress failed. Check permission android.permission.ACCESS_WIFI_STATE [" + DeviceConfig.checkPermission(context, "android.permission.ACCESS_WIFI_STATE") + "]");
        } else {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, mac);
        }
        if (!TextUtils.isEmpty(SocializeConstants.UID)) {
            hashMap.put("uid", SocializeConstants.UID);
        }
        try {
            hashMap.put("en", DeviceConfig.getNetworkAccessMode(context)[0]);
        } catch (Exception e) {
            hashMap.put("en", AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, SocializeConstants.SDK_VERSION);
        hashMap.put("os", "Android");
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DT, Long.valueOf(System.currentTimeMillis()));
        mac = SocializeUtils.getAppkey(context);
        if (TextUtils.isEmpty(mac)) {
            throw new SocializeException("No found appkey.");
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AK, mac);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_VERSION, SocializeConstants.PROTOCOL_VERSON);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
            Log.m3257v("10.13", "ek = " + Config.EntityKey);
        }
        if (!TextUtils.isEmpty(Config.SessionId)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_SID, Config.SessionId);
        }
        try {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, Integer.valueOf(0));
        } catch (Exception e2) {
        }
        return hashMap;
    }

    public static byte[] getNetData(String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        Object obj;
        Throwable th;
        InputStream inputStream2 = null;
        if (TextUtils.isEmpty(str)) {
            return inputStream2;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                InputStream inputStream3 = (InputStream) new URL(str).openConnection().getContent();
                try {
                    Log.m3248d("image", "getting image from url" + str);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream3.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    byte[] toByteArray = byteArrayOutputStream.toByteArray();
                    if (inputStream3 != null) {
                        try {
                            inputStream3.close();
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e) {
                                }
                            }
                        } catch (IOException e2) {
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e3) {
                                }
                            }
                        } catch (Throwable th2) {
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e4) {
                                }
                            }
                        }
                    }
                    return toByteArray;
                } catch (Exception e5) {
                    Exception exception = e5;
                    inputStream = inputStream3;
                    obj = exception;
                    try {
                        Log.m3250e("xxxxx e=" + obj);
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e6) {
                                    }
                                }
                            } catch (IOException e7) {
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e8) {
                                    }
                                }
                            } catch (Throwable th3) {
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e9) {
                                    }
                                }
                            }
                        }
                        return inputStream2;
                    } catch (Throwable th4) {
                        th = th4;
                        inputStream2 = inputStream;
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e10) {
                                    }
                                }
                            } catch (IOException e11) {
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e12) {
                                    }
                                }
                            } catch (Throwable th5) {
                                if (byteArrayOutputStream != null) {
                                    try {
                                        byteArrayOutputStream.close();
                                    } catch (IOException e13) {
                                    }
                                }
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th6) {
                    Throwable th7 = th6;
                    inputStream2 = inputStream3;
                    th = th7;
                    if (inputStream2 != null) {
                        inputStream2.close();
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                    }
                    throw th;
                }
            } catch (Exception e14) {
                obj = e14;
                inputStream = inputStream2;
                Log.m3250e("xxxxx e=" + obj);
                if (inputStream != null) {
                    inputStream.close();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
                return inputStream2;
            } catch (Throwable th8) {
                th = th8;
                if (inputStream2 != null) {
                    inputStream2.close();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
                throw th;
            }
        } catch (Exception e15) {
            obj = e15;
            inputStream = inputStream2;
            Object obj2 = inputStream2;
            Log.m3250e("xxxxx e=" + obj);
            if (inputStream != null) {
                inputStream.close();
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
            return inputStream2;
        } catch (Throwable th9) {
            th = th9;
            byteArrayOutputStream = inputStream2;
            if (inputStream2 != null) {
                inputStream2.close();
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
            throw th;
        }
    }

    public static boolean startWithHttp(String str) {
        return str.startsWith("http://") || str.startsWith("https://");
    }
}
