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
import com.umeng.socialize.utils.Log;

public class UMInstagramHandler extends UMSSOHandler {
    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
    }

    public boolean share(ShareContent content, UMShareListener listener) {
        if (isInstall(getConfig())) {
            return shareto(new InstagramShareContent(content), listener);
        }
        listener.onError(getConfig().getName(), new Throwable("no client"));
        return false;
    }

    private boolean isInstall(Platform p) {
        if (DeviceConfig.isAppInstalled("com.instagram.android", getContext())) {
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

    protected boolean shareto(InstagramShareContent shareContent, UMShareListener listener) {
        UMImage img = shareContent.getImage();
        if (img != null) {
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
                if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                    ((Activity) this.mWeakAct.get()).startActivity(shareIntent);
                }
            } catch (Exception e) {
                listener.onError(SHARE_MEDIA.INSTAGRAM, e);
            }
        } else if (Config.IsToastTip) {
            Toast.makeText(getContext(), "INSTAGRAM 只支持图文 和纯图分享", 1).show();
        }
        return false;
    }
}
