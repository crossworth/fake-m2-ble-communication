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
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.io.File;
import java.util.List;

public class UMWhatsAppHandler extends UMSSOHandler {
    private static final String PACKAGE_NAME = "com.whatsapp";
    private static final String TAG = UMWhatsAppHandler.class.getSimpleName();

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        if (isInstall(getConfig())) {
            return shareTo(new WhatsAppShareContent(content), listener);
        }
        listener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    private boolean isInstall(Platform p) {
        if (DeviceConfig.isAppInstalled(PACKAGE_NAME, getContext())) {
            return true;
        }
        StringBuilder msb = new StringBuilder();
        msb.append("请安装");
        msb.append(ResContainer.getString(getContext(), p.getName().toSnsPlatform().mShowWord));
        msb.append("客户端");
        Log.m4554v(msb.toString());
        if (Config.IsToastTip) {
            Toast.makeText(getContext(), msb, 1).show();
        }
        return false;
    }

    public boolean isInstall() {
        return isInstall(getConfig());
    }

    public boolean shareTo(WhatsAppShareContent shareContent, UMShareListener listener) {
        boolean found = false;
        if (!checkData(shareContent)) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", shareContent.getTitle());
        UMImage img = shareContent.getImage();
        if (img != null) {
            intent.setType("image/*");
            File file = img.asFileImage();
            if (file != null) {
                Uri imgUri = SocializeUtils.insertImage(getContext(), file.toString());
                if (imgUri != null) {
                    intent.putExtra("android.intent.extra.STREAM", imgUri);
                    SocializeUtils.deleteUris.add(imgUri);
                }
            }
        } else {
            intent.setType("text/*");
            intent.putExtra("android.intent.extra.TEXT", shareContent.getText());
        }
        List<ResolveInfo> resInfo = getContext().getPackageManager().queryIntentActivities(intent, 0);
        if (resInfo != null) {
            for (ResolveInfo info : resInfo) {
                if (!info.activityInfo.packageName.toLowerCase().contains(PACKAGE_NAME)) {
                    if (info.activityInfo.name.toLowerCase().contains(PACKAGE_NAME)) {
                    }
                }
                intent.setPackage(info.activityInfo.packageName);
                found = true;
            }
            if (!found) {
                return false;
            }
            try {
                Activity activity = (Activity) this.mWeakAct.get();
                if (!(activity == null || activity.isFinishing())) {
                    intent.setFlags(270532608);
                    activity.startActivity(intent);
                }
                listener.onResult(SHARE_MEDIA.WHATSAPP);
            } catch (Exception e) {
                listener.onError(SHARE_MEDIA.WHATSAPP, e);
            }
            return true;
        }
        Log.m4558w(TAG, "don't scan package name...");
        return false;
    }

    private boolean checkData(WhatsAppShareContent shareContent) {
        if (TextUtils.isEmpty(shareContent.getText()) && shareContent.getImage() == null) {
            return false;
        }
        return true;
    }
}
