package com.sina.weibo.sdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.umeng.socialize.common.SocializeConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import p031u.aly.C1507j;

public class ResourceManager {
    private static final String DIALOG_BACKGROUND_IMAGE_NAME = "weibosdk_dialog_bg.9.png";
    public static final int DIALOG_BOTTOM_MARGIN = 10;
    private static final String DIALOG_CLOSE_BUTTON_IMAGE_NAME = "ic_com_sina_weibo_sdk_close.png";
    public static final int DIALOG_LEFT_MARGIN = 10;
    public static final int DIALOG_RIGHT_MARGIN = 10;
    public static final int DIALOG_TOP_MARGIN = 30;
    private static final String DRAWABLE = "drawable";
    private static final String DRAWABLE_HDPI = "drawable-hdpi";
    private static final String DRAWABLE_LDPI = "drawable-ldpi";
    private static final String DRAWABLE_MDPI = "drawable-mdpi";
    private static final String DRAWABLE_XHDPI = "drawable-xhdpi";
    private static final String DRAWABLE_XXHDPI = "drawable-xxhdpi";
    private static final String LOADING_EN = "Loading...";
    private static final String LOADING_ZH_CN = "加载中...";
    private static final String LOADING_ZH_TW = "載入中...";
    private static final String NETWORK_NOT_AVAILABLE_EN = "Network is not available";
    private static final String NETWORK_NOT_AVAILABLE_ZH_CN = "无法连接到网络，请检查网络配置";
    private static final String NETWORK_NOT_AVAILABLE_ZH_TW = "無法連接到網络，請檢查網络配置";
    private static final String[] PRE_INSTALL_DRAWBLE_PATHS = new String[]{DRAWABLE_XXHDPI, DRAWABLE_XHDPI, DRAWABLE_HDPI, DRAWABLE_MDPI, DRAWABLE_LDPI, DRAWABLE};
    private static final String TAG = ResourceManager.class.getName();
    public static final int dimen_dialog_bottom_margin = 4;
    public static final int dimen_dialog_left_margin = 1;
    public static final int dimen_dialog_right_margin = 3;
    public static final int dimen_dialog_top_margin = 2;
    public static final int drawable_dialog_background = 1;
    public static final int drawable_dialog_close_button = 2;
    private static final SparseArray<String> sDrawableMap = new SparseArray();
    private static final HashMap<Locale, SparseArray<String>> sLanguageMap = new HashMap();
    private static final SparseIntArray sLayoutMap = new SparseIntArray();
    public static final int string_loading = 1;
    public static final int string_network_not_available = 2;

    static {
        sLayoutMap.put(1, 10);
        sLayoutMap.put(2, 30);
        sLayoutMap.put(3, 10);
        sLayoutMap.put(4, 10);
        sDrawableMap.put(1, DIALOG_BACKGROUND_IMAGE_NAME);
        sDrawableMap.put(2, DIALOG_CLOSE_BUTTON_IMAGE_NAME);
        SparseArray<String> stringMap = new SparseArray();
        stringMap.put(1, LOADING_ZH_CN);
        stringMap.put(2, NETWORK_NOT_AVAILABLE_ZH_CN);
        sLanguageMap.put(Locale.SIMPLIFIED_CHINESE, stringMap);
        stringMap = new SparseArray();
        stringMap.put(1, LOADING_ZH_TW);
        stringMap.put(2, NETWORK_NOT_AVAILABLE_ZH_TW);
        sLanguageMap.put(Locale.TRADITIONAL_CHINESE, stringMap);
        stringMap = new SparseArray();
        stringMap.put(1, LOADING_EN);
        stringMap.put(2, NETWORK_NOT_AVAILABLE_EN);
        sLanguageMap.put(Locale.ENGLISH, stringMap);
    }

    public static String getString(Context context, int id) {
        return (String) ((SparseArray) sLanguageMap.get(getLanguage())).get(id, "");
    }

    public static Drawable getDrawable(Context context, int id) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, (String) sDrawableMap.get(id, "")), false);
    }

    public static Drawable getNinePatchDrawable(Context context, int id) {
        return getDrawableFromAssert(context, getAppropriatePathOfDrawable(context, (String) sDrawableMap.get(id, "")), true);
    }

    public static int getDimensionPixelSize(int id) {
        return sLayoutMap.get(id, 0);
    }

    public static Locale getLanguage() {
        Locale locale = Locale.getDefault();
        return (Locale.SIMPLIFIED_CHINESE.equals(locale) || Locale.TRADITIONAL_CHINESE.equals(locale)) ? locale : Locale.ENGLISH;
    }

    public static String getAppropriatePathOfDrawable(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            LogUtil.m2215e(TAG, "id is NOT correct!");
            return null;
        }
        String pathPrefix = getCurrentDpiFolder(context);
        String path = new StringBuilder(String.valueOf(pathPrefix)).append("/").append(fileName).toString();
        LogUtil.m2216i(TAG, "Maybe the appropriate path: " + path);
        if (isFileExisted(context, path)) {
            return path;
        }
        LogUtil.m2214d(TAG, "Not the correct path, we need to find one...");
        boolean bFound = false;
        for (int ix = 0; ix < PRE_INSTALL_DRAWBLE_PATHS.length; ix++) {
            if (bFound) {
                path = new StringBuilder(String.valueOf(PRE_INSTALL_DRAWBLE_PATHS[ix])).append("/").append(fileName).toString();
                if (isFileExisted(context, path)) {
                    return path;
                }
            } else if (pathPrefix.equals(PRE_INSTALL_DRAWBLE_PATHS[ix])) {
                bFound = true;
                LogUtil.m2216i(TAG, "Have Find index: " + ix + ", " + PRE_INSTALL_DRAWBLE_PATHS[ix]);
            }
        }
        LogUtil.m2215e(TAG, "Not find the appropriate path for drawable");
        return null;
    }

    public static Drawable getDrawableFromAssert(Context context, String relativePath, boolean isNinePatch) {
        InputStream is = null;
        try {
            Drawable rtDrawable;
            is = context.getAssets().open(relativePath);
            if (is != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                if (isNinePatch) {
                    rtDrawable = new NinePatchDrawable(new Resources(context.getAssets(), metrics, context.getResources().getConfiguration()), bitmap, bitmap.getNinePatchChunk(), new Rect(0, 0, 0, 0), null);
                } else {
                    bitmap.setDensity(metrics.densityDpi);
                    rtDrawable = new BitmapDrawable(context.getResources(), bitmap);
                }
            } else {
                rtDrawable = null;
            }
            if (is == null) {
                return rtDrawable;
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rtDrawable;
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is == null) {
                return null;
            }
            try {
                is.close();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
            return null;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
    }

    private static boolean isFileExisted(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)) {
            return false;
        }
        InputStream is = null;
        try {
            is = context.getAssets().open(filePath);
            LogUtil.m2214d(TAG, "file [" + filePath + "] existed");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        } catch (IOException e2) {
            LogUtil.m2214d(TAG, "file [" + filePath + "] NOT existed");
            if (is == null) {
                return false;
            }
            try {
                is.close();
                return false;
            } catch (IOException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
    }

    private static String getCurrentDpiFolder(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        if (density <= 120) {
            return DRAWABLE_LDPI;
        }
        if (density > 120 && density <= C1507j.f3829b) {
            return DRAWABLE_MDPI;
        }
        if (density > C1507j.f3829b && density <= SocializeConstants.MASK_USER_CENTER_HIDE_AREA) {
            return DRAWABLE_HDPI;
        }
        if (density <= SocializeConstants.MASK_USER_CENTER_HIDE_AREA || density > 320) {
            return DRAWABLE_XXHDPI;
        }
        return DRAWABLE_XHDPI;
    }

    private static View extractView(Context context, String fileName, ViewGroup root) throws Exception {
        return ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(context.getAssets().openXmlResourceParser(fileName), root);
    }

    private static Drawable extractDrawable(Context context, String fileName) throws Exception {
        InputStream inputStream = context.getAssets().open(fileName);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TypedValue value = new TypedValue();
        value.density = dm.densityDpi;
        Drawable drawable = Drawable.createFromResourceStream(context.getResources(), value, inputStream, fileName);
        inputStream.close();
        return drawable;
    }
}
