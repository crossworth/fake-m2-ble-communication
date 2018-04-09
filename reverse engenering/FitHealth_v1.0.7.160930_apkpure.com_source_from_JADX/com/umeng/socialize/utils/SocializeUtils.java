package com.umeng.socialize.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.WindowManager;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class SocializeUtils {
    protected static final String TAG = "SocializeUtils";
    public static Set<Uri> deleteUris = new HashSet();
    private static Pattern mDoubleByte_Pattern = null;
    private static int smDip = 0;

    public static String getAppkey(Context context) {
        String str = SocializeConstants.APPKEY;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return str;
            }
            Object obj = applicationInfo.metaData.get("UMENG_APPKEY");
            if (obj != null) {
                return obj.toString();
            }
            Log.m3254i(SocializeConstants.COMMON_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
            return str;
        } catch (Exception e) {
            Log.m3255i(SocializeConstants.COMMON_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", e);
            return str;
        }
    }

    public static void safeCloseDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (dialog.isShowing()) {
                    Activity ownerActivity = dialog.getOwnerActivity();
                    if (ownerActivity != null && !ownerActivity.isFinishing()) {
                        dialog.dismiss();
                    }
                }
            } catch (Exception e) {
                Log.m3252e(TAG, "dialog dismiss error", e);
            }
        }
    }

    public static void safeShowDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (!dialog.isShowing()) {
                    Activity ownerActivity = dialog.getOwnerActivity();
                    if (ownerActivity != null && !ownerActivity.isFinishing()) {
                        dialog.show();
                    }
                }
            } catch (Exception e) {
                Log.m3252e(TAG, "dialog show error", e);
            }
        }
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str);
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                bundle.putString(URLDecoder.decode(split2[0]), URLDecoder.decode(split2[1]));
            }
        }
        return bundle;
    }

    public static int countContentLength(String str) {
        Object trim = str.trim();
        int i = 0;
        while (getDoubleBytePattern().matcher(trim).find()) {
            i++;
        }
        int length = trim.length() - i;
        if (length % 2 != 0) {
            return i + ((length + 1) / 2);
        }
        return i + (length / 2);
    }

    private static Pattern getDoubleBytePattern() {
        if (mDoubleByte_Pattern == null) {
            mDoubleByte_Pattern = Pattern.compile("[^\\x00-\\xff]");
        }
        return mDoubleByte_Pattern;
    }

    public static Object[] readSIMCard(Context context) {
        try {
            Object[] objArr = new Object[3];
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            objArr[0] = Boolean.valueOf(false);
            switch (telephonyManager.getSimState()) {
                case 0:
                    objArr[0] = Boolean.valueOf(true);
                    objArr[1] = "未知状态";
                    break;
                case 1:
                    objArr[1] = "无卡";
                    break;
                case 2:
                    objArr[1] = "需要PIN解锁";
                    break;
                case 3:
                    objArr[1] = "需要PUK解锁";
                    break;
                case 4:
                    objArr[1] = "需要NetworkPIN解锁";
                    break;
                case 5:
                    objArr[0] = Boolean.valueOf(true);
                    objArr[1] = "良好";
                    break;
            }
            return objArr;
        } catch (Exception e) {
            Log.m3251e(TAG, "cannot read SIM card. [" + e.toString() + "]");
            return null;
        }
    }

    public static boolean isGoogleMapExist() {
        try {
            Class.forName("com.google.android.maps.MapActivity");
            return true;
        } catch (Exception e) {
            Log.m3260w(TAG, "The device has no google map lib!");
            return false;
        }
    }

    public static int[] getFloatWindowSize(Context context) {
        ResContainer resContainer = ResContainer.get(context);
        Resources resources = context.getResources();
        return new int[]{(int) resources.getDimension(resContainer.dimen("umeng_socialize_pad_window_width")), (int) resources.getDimension(resContainer.dimen("umeng_socialize_pad_window_height"))};
    }

    public static boolean isFloatWindowStyle(Context context) {
        if (SocializeConstants.SUPPORT_PAD) {
            if (smDip == 0) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                Display defaultDisplay = windowManager.getDefaultDisplay();
                int width = defaultDisplay.getWidth();
                int height = defaultDisplay.getHeight();
                if (width <= height) {
                    height = width;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                smDip = (int) ((((float) height) / displayMetrics.density) + 0.5f);
            }
            if ((context.getResources().getConfiguration().screenLayout & 15) >= 3 && smDip >= 550) {
                return true;
            }
        }
        return false;
    }

    public static Uri insertImage(Context context, String str) {
        Uri uri = null;
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            try {
                Object insertImage = Media.insertImage(context.getContentResolver(), str, "umeng_social_shareimg", null);
                if (!TextUtils.isEmpty(insertImage)) {
                    uri = Uri.parse(insertImage);
                }
            } catch (Exception e) {
                Log.m3252e(SocializeConstants.COMMON_TAG, "", e);
            } catch (Exception e2) {
                Log.m3252e(SocializeConstants.COMMON_TAG, "", e2);
            }
        }
        return uri;
    }

    public static void deleteUriImage(Context context, Set<Uri> set) {
        Set<String> set2 = (Set) getObject(BitmapUtils.PATH + SocializeConstants.FILE_URI_NAME);
        if (set2 != null && set2.size() > 0) {
            for (String parse : set2) {
                set.add(Uri.parse(parse));
            }
        }
        if (set != null && set.size() > 0) {
            for (Uri delete : set) {
                context.getContentResolver().delete(delete, null, null);
            }
            set.clear();
        } else if (set == null) {
            HashSet hashSet = new HashSet();
        }
    }

    public static void saveObject(Object obj, String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(String str) {
        Exception e;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        ObjectInputStream objectInputStream2;
        try {
            File file = new File(str);
            if (file.exists()) {
                objectInputStream2 = new ObjectInputStream(new FileInputStream(file));
                try {
                    T readObject = objectInputStream2.readObject();
                    if (readObject != null) {
                        if (objectInputStream2 != null) {
                            try {
                                objectInputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return readObject;
                    } else if (objectInputStream2 == null) {
                        return null;
                    } else {
                        try {
                            objectInputStream2.close();
                            return null;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return null;
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    try {
                        e.printStackTrace();
                        if (objectInputStream2 != null) {
                            return null;
                        }
                        try {
                            objectInputStream2.close();
                            return null;
                        } catch (IOException e32) {
                            e32.printStackTrace();
                            return null;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (objectInputStream2 != null) {
                            try {
                                objectInputStream2.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } else if (null == null) {
                return null;
            } else {
                try {
                    objectInputStream.close();
                    return null;
                } catch (IOException e3222) {
                    e3222.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e5) {
            e = e5;
            objectInputStream2 = null;
            e.printStackTrace();
            if (objectInputStream2 != null) {
                return null;
            }
            objectInputStream2.close();
            return null;
        } catch (Throwable th3) {
            objectInputStream2 = null;
            th = th3;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            throw th;
        }
    }

    public static int dip2Px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static String reverse(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        char[] toCharArray = str.toCharArray();
        for (int length = toCharArray.length - 1; length >= 0; length--) {
            stringBuilder.append(toCharArray[length]);
        }
        return stringBuilder.toString();
    }

    public static Pair<String, String> resolveActivity(Context context, String str, String str2) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!queryIntentActivities.isEmpty()) {
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                boolean contains = resolveInfo.activityInfo.packageName.toLowerCase().contains(str);
                boolean contains2 = resolveInfo.activityInfo.name.toLowerCase().contains(str2);
                if (!contains) {
                    if (contains2) {
                    }
                }
                return new Pair(resolveInfo.activityInfo.parentActivityName, resolveInfo.activityInfo.name);
            }
        }
        return null;
    }

    public static Map<String, String> jsonToMap(String str) {
        Map<String, String> hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                hashMap.put(str2, jSONObject.get(str2) + "");
            }
        } catch (Exception e) {
            Log.m3251e("weixin", "jsontomap fail=" + e);
        }
        return hashMap;
    }
}
