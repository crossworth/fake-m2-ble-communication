package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.media.InstagramShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.Dummy;
import com.umeng.socialize.utils.Log;

public class UMInstagramHandler extends UMSSOHandler {
    public boolean share(Activity activity, ShareContent content, UMShareListener listener) {
        UMShareListener safelistener = (UMShareListener) Dummy.get(UMShareListener.class, listener);
        if (isInstall(activity, getConfig())) {
            return shareto(activity, new InstagramShareContent(content), safelistener);
        }
        safelistener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    private boolean isInstall(Context mContext, Platform p) {
        if (DeviceConfig.isAppInstalled("com.instagram.android", mContext)) {
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

    protected boolean shareto(Activity activity, InstagramShareContent shareContent, UMShareListener listener) {
        UMImage img = shareContent.getImage();
        if (img == null) {
            Log.m3250e(" xxxxxx INSTAGRAM 只支持图文 和纯图分享");
            if (Config.IsToastTip) {
                Toast.makeText(activity, "INSTAGRAM 只支持图文 和纯图分享", 1).show();
            }
        } else {
            Intent shareIntent = new Intent();
            shareIntent.addFlags(268435456);
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.instagram.android");
            shareIntent.setType("image/*");
            if (img != null) {
                shareIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(img.asFileImage()));
            }
            try {
                listener.onResult(SHARE_MEDIA.INSTAGRAM);
                activity.startActivity(shareIntent);
            } catch (Exception e) {
                listener.onError(SHARE_MEDIA.INSTAGRAM, e);
            }
        }
        return false;
    }
}
