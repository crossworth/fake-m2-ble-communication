package com.zhuoyi.system.promotion.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.util.FileUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Random;

public class NotiUitl {
    private static final String TAG = "NotiUitl";
    private static NotiUitl mInstance;
    private Context mContext;
    private Class<?> nClass = getNClass();
    private Random f3486r = new Random();

    class ShowPushTask extends AsyncTask<Object, Void, RemoteViews> {
        private PromAppInfo appInfo;
        private Intent clickIntent;

        ShowPushTask() {
        }

        protected RemoteViews doInBackground(Object... params) {
            Logger.debug(NotiUitl.TAG, "ShowPushTask doInBackground start");
            this.appInfo = (PromAppInfo) params[0];
            this.clickIntent = (Intent) params[1];
            if (this.appInfo.getAdType() == (byte) 1) {
                return NotiUitl.this.showImagePushNotify(this.appInfo);
            }
            if (this.appInfo.getAdType() == (byte) 3) {
                return NotiUitl.this.showImageAndTextPushNotify(this.appInfo);
            }
            return NotiUitl.this.showTextPushNotify(this.appInfo);
        }

        protected void onPostExecute(RemoteViews result) {
            super.onPostExecute(result);
            if (result == null) {
                Logger.debug(NotiUitl.TAG, "ShowPushTask onPostExecute result == null");
                return;
            }
            NotiUitl.this.cancelOldNoti(this.appInfo.getId());
            NotiUitl.this.createReflectNoti(this.appInfo, result, NotiUitl.this.getRandomIconId(), this.clickIntent);
        }
    }

    private NotiUitl(Context context) {
        this.mContext = context;
    }

    public static NotiUitl getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotiUitl(context);
        }
        return mInstance;
    }

    public void showNotification(PromAppInfo appInfo, Intent clickIntent) {
        new ShowPushTask().execute(new Object[]{appInfo, clickIntent});
    }

    public void showSimpleNotification(int notifyId, String title, String msg, PendingIntent pendingIntent) {
        Object nObject = getNotiObject(getRandomIconId(), "");
        try {
            setParams(nObject, "defaults", Integer.valueOf(-1));
            setParams(nObject, "flags", Integer.valueOf(18));
            setLatestEventInfo(nObject, title, msg, pendingIntent);
            createNoti(nObject, notifyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createReflectNoti(PromAppInfo appInfo, RemoteViews rv, int iconInt, Intent intent) {
        Object nObject = getNotiObject(iconInt, appInfo.getTitle());
        setParams(nObject, "defaults", Integer.valueOf(-1));
        createReflectNoti(appInfo.getId(), rv, nObject, clickPushNotifyListener(appInfo, intent), true);
    }

    public void createReflectNoti(int id, RemoteViews rv, Object nObject, PendingIntent pendingIntent, boolean cancelFlag) {
        try {
            Field flagsField = this.nClass.getDeclaredField("flags");
            Field numberField = this.nClass.getDeclaredField("number");
            Field contentIntentField = this.nClass.getDeclaredField("contentIntent");
            Field contentViewField = this.nClass.getDeclaredField("contentView");
            if (cancelFlag) {
                flagsField.set(nObject, Integer.valueOf(16));
            } else {
                flagsField.set(nObject, Integer.valueOf(34));
            }
            numberField.set(nObject, Integer.valueOf(1));
            contentIntentField.set(nObject, pendingIntent);
            contentViewField.set(nObject, rv);
            createNoti(nObject, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getNClass() {
        StringBuilder nSb = new StringBuilder("android.app.");
        nSb.append("N");
        nSb.append("o");
        nSb.append("t");
        nSb.append("i");
        nSb.append("f");
        nSb.append("i");
        nSb.append("c");
        nSb.append("a");
        nSb.append("t");
        nSb.append("i");
        nSb.append("o");
        nSb.append("n");
        Class<?> nClass = null;
        try {
            nClass = Class.forName(nSb.toString());
        } catch (Exception e) {
        }
        return nClass;
    }

    public void createNoti(Object nObject, int id) {
        try {
            getNmObject();
            Class<?> nmClass = Class.forName(getNmObject().getClass().getName());
            StringBuilder nSb = new StringBuilder();
            nSb.append("n");
            nSb.append("o");
            nSb.append("t");
            nSb.append("i");
            nSb.append("f");
            nSb.append("y");
            nmClass.getDeclaredMethod(nSb.toString(), new Class[]{Integer.TYPE, this.nClass}).invoke(getNmObject(), new Object[]{Integer.valueOf(id), nObject});
        } catch (Exception e) {
            Logger.m3373e(TAG, "create noti failed.");
        }
    }

    public void setLatestEventInfo(Object nObject, String title, String msg, PendingIntent pendingIntent) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("set");
            sb.append("Latest");
            sb.append("Event");
            sb.append("Info");
            Method m = this.nClass.getDeclaredMethod(sb.toString(), new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class});
            m.setAccessible(true);
            m.invoke(nObject, new Object[]{this.mContext, title, msg, pendingIntent});
        } catch (Exception e) {
            Logger.m3375p(e);
        }
    }

    public void cancelOldNoti(int id) {
        StringBuilder cSb = new StringBuilder();
        cSb.append("can");
        cSb.append("cel");
        try {
            Class.forName(getNmObject().getClass().getName()).getDeclaredMethod(cSb.toString(), new Class[]{Integer.TYPE}).invoke(getNmObject(), new Object[]{Integer.valueOf(id)});
        } catch (Exception e) {
            Logger.m3373e(TAG, "can-cel noti failed.");
        }
    }

    public void setParams(Object notiObject, String field, Object value) {
        try {
            Field icon = this.nClass.getDeclaredField(field);
            icon.setAccessible(true);
            icon.set(notiObject, value);
        } catch (Exception e) {
            Logger.m3373e(TAG, "set noti " + field + " error");
        }
    }

    public Object getNotiObject(int iconInt, String tickerText) {
        Object nObject = new Object();
        try {
            nObject = this.nClass.getConstructor(new Class[]{Integer.TYPE, CharSequence.class, Long.TYPE}).newInstance(new Object[]{Integer.valueOf(iconInt), tickerText, Long.valueOf(System.currentTimeMillis())});
        } catch (Exception e) {
        }
        return nObject;
    }

    public Object getNmObject() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m1 = Context.class.getDeclaredMethod("getSystemService", new Class[]{String.class});
        StringBuilder nSb = new StringBuilder();
        nSb.append("n");
        nSb.append("o");
        nSb.append("t");
        nSb.append("i");
        nSb.append("f");
        nSb.append("i");
        nSb.append("c");
        nSb.append("a");
        nSb.append("t");
        nSb.append("i");
        nSb.append("o");
        nSb.append("n");
        return m1.invoke(this.mContext, new Object[]{nSb.toString()});
    }

    private int getRandomIconId() {
        return new int[]{ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_prom_notify_icon_1"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_prom_notify_icon_2"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_prom_notify_icon_3"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_prom_notify_icon_4"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_prom_notify_icon_5")}[this.f3486r.nextInt(5)];
    }

    private RemoteViews showImagePushNotify(PromAppInfo appInfo) {
        RemoteViews rv = new RemoteViews(this.mContext.getPackageName(), ResourceIdUtils.getResourceId(this.mContext, "R.layout.zy_prom_notify_image_layout"));
        Bitmap bitmap = showBitmap(appInfo);
        if (bitmap != null) {
            Logger.debug(TAG, "ImagePushTask doInBackground bitmap download success");
            rv.setImageViewBitmap(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_iv_notify_icon"), bitmap);
            return rv;
        }
        Logger.debug(TAG, "ImagePushTask doInBackground bitmap == null");
        return null;
    }

    private RemoteViews showTextPushNotify(PromAppInfo appInfo) {
        return null;
    }

    private Bitmap showBitmap(PromAppInfo appInfo) {
        Bitmap bitmap = null;
        File imagePathFile = new File(PromConstants.PROM_APP_ICONS_PATH);
        if (!imagePathFile.exists()) {
            imagePathFile.mkdirs();
        }
        File f = null;
        if (appInfo.getAdType() == (byte) 3) {
            f = new File(imagePathFile, new StringBuilder(String.valueOf(appInfo.getIconId())).toString());
        } else if (appInfo.getAdType() == (byte) 1) {
            f = new File(imagePathFile, appInfo.getShowIconId());
        }
        if (f != null) {
            bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        }
        if (bitmap != null) {
            return bitmap;
        }
        InputStream is = null;
        try {
            if (appInfo.getAdType() == (byte) 3) {
                is = new URL(appInfo.getUrl()).openStream();
            } else if (appInfo.getAdType() == (byte) 1) {
                is = new URL(appInfo.getShowPicUrl()).openStream();
            }
            FileUtils.copyStream(is, new FileOutputStream(f));
            return BitmapFactory.decodeFile(f.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private RemoteViews showImageAndTextPushNotify(PromAppInfo appInfo) {
        RemoteViews rv;
        int[] colors = new int[]{ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_notify_color_1"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_notify_color_2"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_notify_color_3"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_notify_color_4"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_notify_color_5")};
        int temp = this.f3486r.nextInt(colors.length + 2);
        Logger.debug(TAG, "temp = " + temp);
        if (temp < colors.length) {
            rv = new RemoteViews(this.mContext.getPackageName(), ResourceIdUtils.getResourceId(this.mContext, "R.layout.zy_prom_notify_color_layout"));
            rv.setInt(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_ll"), "setBackgroundResource", colors[temp]);
            if (temp == 1) {
                rv.setTextColor(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_title"), this.mContext.getResources().getColor(ResourceIdUtils.getResourceId(this.mContext, "R.color.zy_white")));
                rv.setTextColor(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_msg"), this.mContext.getResources().getColor(ResourceIdUtils.getResourceId(this.mContext, "R.color.zy_notify_text_gray1")));
            } else {
                rv.setTextColor(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_msg"), this.mContext.getResources().getColor(ResourceIdUtils.getResourceId(this.mContext, "R.color.zy_black")));
                rv.setTextColor(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_title"), this.mContext.getResources().getColor(ResourceIdUtils.getResourceId(this.mContext, "R.color.zy_black")));
            }
        } else {
            rv = new RemoteViews(this.mContext.getPackageName(), ResourceIdUtils.getResourceId(this.mContext, "R.layout.zy_prom_notify_layout"));
        }
        rv.setTextViewText(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_title"), appInfo.getTitle());
        rv.setTextViewText(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_tv_notify_msg"), appInfo.getTip());
        Bitmap bitmap = showBitmap(appInfo);
        if (bitmap == null) {
            rv.setImageViewResource(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_iv_notify_icon"), ResourceIdUtils.getResourceId(this.mContext, "R.drawable.zy_default_app_icon"));
        } else {
            rv.setImageViewBitmap(ResourceIdUtils.getResourceId(this.mContext, "R.id.zy_iv_notify_icon"), bitmap);
        }
        return rv;
    }

    private PendingIntent clickPushNotifyListener(PromAppInfo appInfo, Intent intent) {
        Logger.debug(TAG, "PendingIntent----->clickPushNotifyListener");
        if (appInfo.getType() == (byte) 1 && appInfo.getActionType() == (byte) 1 && intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO) != null) {
            return PendingIntent.getService(this.mContext, appInfo.getId(), intent, 1073741824);
        }
        return PendingIntent.getActivity(this.mContext, appInfo.getId(), intent, 0);
    }
}
