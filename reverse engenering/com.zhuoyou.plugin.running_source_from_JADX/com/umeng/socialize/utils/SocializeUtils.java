package com.umeng.socialize.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
            Log.m4552i("com.umeng.socialize", "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.");
            return str;
        } catch (Exception e) {
            Log.m4553i("com.umeng.socialize", "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", e);
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
                Log.m4550e(TAG, "dialog dismiss error", e);
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
                Log.m4550e(TAG, "dialog show error", e);
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
                Log.m4550e("com.umeng.socialize", "", e);
            } catch (Exception e2) {
                Log.m4550e("com.umeng.socialize", "", e2);
            }
        }
        return uri;
    }

    public static int dip2Px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
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
            Log.m4549e("social", "jsontomap fail=" + e);
        }
        return hashMap;
    }

    public static byte[] File2byte(File file) {
        byte[] bArr = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr2);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr2, 0, read);
            }
            fileInputStream.close();
            byteArrayOutputStream.close();
            bArr = byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bArr;
    }

    public static Map<String, String> bundleTomap(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return null;
        }
        Set<String> keySet = bundle.keySet();
        Map<String, String> hashMap = new HashMap();
        for (String str : keySet) {
            if (str.equals("com.sina.weibo.intent.extra.USER_ICON")) {
                hashMap.put("icon_url", bundle.getString(str));
            }
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }
}
