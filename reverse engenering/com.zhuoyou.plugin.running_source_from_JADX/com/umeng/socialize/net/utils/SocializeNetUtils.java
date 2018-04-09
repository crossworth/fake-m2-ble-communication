package com.umeng.socialize.net.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.facebook.internal.AnalyticsEvents;
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

    public static String generateEncryptGetURL(String str, Map<String, Object> map) {
        return buildGetUrl(str, map, true);
    }

    public static String generateGetURL(String str, Map<String, Object> map) {
        return buildGetUrl(str, map, false);
    }

    private static String buildGetUrl(String str, Map<String, Object> map, boolean z) {
        if (TextUtils.isEmpty(str) || map == null || map.size() == 0) {
            return str;
        }
        if (!str.endsWith("?")) {
            str = str + "?";
        }
        String buildGetParams = buildGetParams(map);
        if (z) {
            try {
                buildGetParams = "ud_get=" + AesHelper.encryptNoPadding(buildGetParams, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.append(buildGetParams);
        Log.m4546d(TAG, "#### 完整请求链接 : " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private static String buildGetParams(Map<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = stringBuilder;
        for (String str : map.keySet()) {
            if (map.get(str) != null) {
                stringBuilder2 = stringBuilder2.append(str + "=" + URLEncoder.encode(map.get(str).toString()) + "&");
            }
        }
        return stringBuilder2.substring(0, stringBuilder2.length() - 1).toString();
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
            Log.m4558w(TAG, "Get MacAddress failed. Check permission android.permission.ACCESS_WIFI_STATE [" + DeviceConfig.checkPermission(context, "android.permission.ACCESS_WIFI_STATE") + "]");
        } else {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, mac);
        }
        if (!TextUtils.isEmpty(SocializeConstants.UID)) {
            hashMap.put("uid", SocializeConstants.UID);
        }
        try {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EN, DeviceConfig.getNetworkAccessMode(context)[0]);
        } catch (Exception e) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_EN, AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, SocializeConstants.SDK_VERSION);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_OS, SocializeConstants.OS);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID, DeviceConfig.getAndroidID(context));
        hashMap.put("sn", DeviceConfig.getDeviceSN());
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_OS_VERSION, DeviceConfig.getOsVersion());
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DT, Long.valueOf(System.currentTimeMillis()));
        mac = SocializeUtils.getAppkey(context);
        if (TextUtils.isEmpty(mac)) {
            throw new SocializeException("No found appkey.");
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AK, mac);
        hashMap.put(SocializeProtocolConstants.PROTOCOL_VERSION, SocializeConstants.PROTOCOL_VERSON);
        hashMap.put(SocializeConstants.USHARETYPE, Config.shareType);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
            Log.m4555v("10.13", "ek = " + Config.EntityKey);
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
        Throwable th;
        InputStream inputStream2 = null;
        if (TextUtils.isEmpty(str)) {
            return inputStream2;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                inputStream = (InputStream) new URL(str).openConnection().getContent();
                try {
                    Log.m4546d("image", "getting image from url" + str);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    byte[] toByteArray = byteArrayOutputStream.toByteArray();
                    if (inputStream != null) {
                        try {
                            inputStream.close();
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
                    Throwable th5 = th4;
                    inputStream2 = inputStream;
                    th = th5;
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
                        } catch (Throwable th6) {
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
            } catch (Exception e14) {
                inputStream = inputStream2;
                if (inputStream != null) {
                    inputStream.close();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
                return inputStream2;
            } catch (Throwable th7) {
                th = th7;
                if (inputStream2 != null) {
                    inputStream2.close();
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                }
                throw th;
            }
        } catch (Exception e15) {
            inputStream = inputStream2;
            Object obj = inputStream2;
            if (inputStream != null) {
                inputStream.close();
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            }
            return inputStream2;
        } catch (Throwable th8) {
            th = th8;
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
