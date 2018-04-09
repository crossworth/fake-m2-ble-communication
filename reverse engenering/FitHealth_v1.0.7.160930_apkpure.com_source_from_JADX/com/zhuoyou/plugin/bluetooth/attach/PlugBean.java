package com.zhuoyou.plugin.bluetooth.attach;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.internal.AnalyticsEvents;
import com.fithealth.running.R;
import java.lang.reflect.Constructor;
import java.util.Map;

public class PlugBean {
    public boolean isInstalled = false;
    public boolean isPreInstall = false;
    public boolean isSystem = false;
    public Class<?> mClasses;
    public Constructor<?> mConstructor;
    public Context mCtx;
    public Object mInstance;
    public Drawable mLogoBitmap = null;
    public int mLogoResId = 0;
    public String mMethod_Entry = "";
    public String mPackageName = "";
    public String mSupportCmd = "";
    public String mTitle = "";
    public Map<String, String> mWorkMethod;

    public void setBitmapId(ImageView iv) {
        iv.setImageResource(this.mLogoResId);
    }

    public void setBitmap(ImageView iv) {
        if (this.mLogoBitmap != null) {
            iv.setImageDrawable(this.mLogoBitmap);
        } else {
            iv.setImageResource(R.drawable.ic_launcher);
        }
    }

    public void setTitle(TextView tv) {
        if (this.mTitle != null) {
            tv.setText(this.mTitle);
        } else {
            tv.setText(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);
        }
    }
}
