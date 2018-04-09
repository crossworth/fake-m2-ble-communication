package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.WhatsAppShareContent;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Dummy;
import com.umeng.socialize.utils.Log;
import java.util.List;

public class UMWhatsAppHandler extends UMSSOHandler {
    private static final String PACKAGE_NAME = "com.whatsapp";
    private static final String TAG = UMWhatsAppHandler.class.getSimpleName();

    public boolean share(Activity activity, ShareContent content, UMShareListener listener) {
        UMShareListener safelistener = (UMShareListener) Dummy.get(UMShareListener.class, listener);
        if (isInstall(activity, getConfig())) {
            return shareTo(activity, new WhatsAppShareContent(content), safelistener);
        }
        safelistener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    private boolean isInstall(Context mContext, Platform p) {
        if (DeviceConfig.isAppInstalled(PACKAGE_NAME, mContext)) {
            return true;
        }
        StringBuilder msb = new StringBuilder();
        msb.append("请安装");
        msb.append(ResContainer.getString(mContext, p.getName().toSnsPlatform().mShowWord));
        msb.append("客户端");
        Log.m3256v(msb.toString());
        if (Config.IsToastTip) {
            Toast.makeText(mContext, msb, 1).show();
        }
        return false;
    }

    public boolean isInstall(Context mContext) {
        return isInstall(mContext, getConfig());
    }

    public boolean shareTo(Activity activity, WhatsAppShareContent shareContent, UMShareListener listener) {
        boolean found = false;
        if (!checkData(shareContent)) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        UMImage img = shareContent.getImage();
        if (img != null) {
            intent.setType("image/*");
        } else {
            intent.setType("text/*");
        }
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo != null) {
            for (ResolveInfo info : resInfo) {
                if (!info.activityInfo.packageName.toLowerCase().contains(PACKAGE_NAME)) {
                    if (info.activityInfo.name.toLowerCase().contains(PACKAGE_NAME)) {
                    }
                }
                intent.putExtra("android.intent.extra.SUBJECT", shareContent.getTitle());
                if (img != null) {
                    intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(img.asFileImage()));
                } else {
                    intent.putExtra("android.intent.extra.TEXT", shareContent.getText());
                }
                intent.setPackage(info.activityInfo.packageName);
                found = true;
                if (found) {
                    return false;
                }
                intent = Intent.createChooser(intent, "choose WhatsApp app");
                intent.setFlags(270532608);
                try {
                    activity.startActivity(intent);
                    listener.onResult(SHARE_MEDIA.WHATSAPP);
                } catch (Exception e) {
                    listener.onError(SHARE_MEDIA.WHATSAPP, e);
                }
                return true;
            }
            if (found) {
                return false;
            }
            intent = Intent.createChooser(intent, "choose WhatsApp app");
            intent.setFlags(270532608);
            activity.startActivity(intent);
            listener.onResult(SHARE_MEDIA.WHATSAPP);
            return true;
        }
        Log.m3260w(TAG, "don't scan package name...");
        return false;
    }

    private boolean checkData(WhatsAppShareContent shareContent) {
        if (TextUtils.isEmpty(shareContent.getText()) && shareContent.getImage() == null) {
            return false;
        }
        return true;
    }
}
