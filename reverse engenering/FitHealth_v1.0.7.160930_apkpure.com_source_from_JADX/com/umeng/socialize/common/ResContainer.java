package com.umeng.socialize.common;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ResContainer {
    private static ResContainer f3257R = null;
    private static String mPackageName = "";
    private Context context = null;
    private Map<String, SocializeResource> mResources;
    private Map<String, Integer> map = new HashMap();

    public static class SocializeResource {
        public int mId;
        public boolean mIsCompleted = false;
        public String mName;
        public String mType;

        public SocializeResource(String str, String str2) {
            this.mType = str;
            this.mName = str2;
        }
    }

    private ResContainer(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized ResContainer get(Context context) {
        ResContainer resContainer;
        synchronized (ResContainer.class) {
            if (f3257R == null) {
                f3257R = new ResContainer(context);
            }
            resContainer = f3257R;
        }
        return resContainer;
    }

    public int layout(String str) {
        return getResourceId(this.context, "layout", str);
    }

    public int id(String str) {
        return getResourceId(this.context, "id", str);
    }

    public int drawable(String str) {
        return getResourceId(this.context, "drawable", str);
    }

    public int style(String str) {
        return getResourceId(this.context, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, str);
    }

    public int string(String str) {
        return getResourceId(this.context, "string", str);
    }

    public int color(String str) {
        return getResourceId(this.context, "color", str);
    }

    public int dimen(String str) {
        return getResourceId(this.context, "dimen", str);
    }

    public int raw(String str) {
        return getResourceId(this.context, "raw", str);
    }

    public int anim(String str) {
        return getResourceId(this.context, "anim", str);
    }

    public int styleable(String str) {
        return getResourceId(this.context, "styleable", str);
    }

    public ResContainer(Context context, Map<String, SocializeResource> map) {
        this.mResources = map;
        this.context = context;
    }

    public static int getResourceId(Context context, String str, String str2) {
        Resources resources = context.getResources();
        if (TextUtils.isEmpty(mPackageName)) {
            mPackageName = context.getPackageName();
        }
        int identifier = resources.getIdentifier(str2, str, mPackageName);
        if (identifier > 0) {
            return identifier;
        }
        throw new RuntimeException("获取资源ID失败:(packageName=" + mPackageName + " type=" + str + " name=" + str2);
    }

    public static String getString(Context context, String str) {
        return context.getString(getResourceId(context, "string", str));
    }

    public synchronized Map<String, SocializeResource> batch() {
        Map<String, SocializeResource> map;
        if (this.mResources == null) {
            map = this.mResources;
        } else {
            for (String str : this.mResources.keySet()) {
                SocializeResource socializeResource = (SocializeResource) this.mResources.get(str);
                socializeResource.mId = getResourceId(this.context, socializeResource.mType, socializeResource.mName);
                socializeResource.mIsCompleted = true;
            }
            map = this.mResources;
        }
        return map;
    }

    public static int[] getStyleableArrts(Context context, String str) {
        return getResourceDeclareStyleableIntArray(context, str);
    }

    private static final int[] getResourceDeclareStyleableIntArray(Context context, String str) {
        try {
            for (Field field : Class.forName(context.getPackageName() + ".R$styleable").getFields()) {
                if (field.getName().equals(str)) {
                    return (int[]) field.get(null);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }
}
