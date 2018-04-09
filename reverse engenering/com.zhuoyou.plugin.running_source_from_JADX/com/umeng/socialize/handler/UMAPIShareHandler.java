package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.editorpage.IEditor;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.SharePostRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.utils.Log;
import java.util.Map;
import java.util.Stack;

public abstract class UMAPIShareHandler extends UMSSOHandler implements IEditor {
    private Stack<StatHolder> mStatStack = new Stack();

    static class StatHolder {
        public ShareContent Content;
        public UMShareListener Listener;

        StatHolder() {
        }
    }

    public abstract void authorizeCallBack(int i, int i2, Intent intent);

    public abstract SHARE_MEDIA getPlatform();

    public abstract String getUID();

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getRequestCode()) {
            StatHolder holder;
            if (resultCode == 1000) {
                holder = (StatHolder) this.mStatStack.pop();
                if (holder != null) {
                    holder.Listener.onCancel(getPlatform());
                }
            }
            if (data == null || !data.hasExtra(SocializeConstants.KEY_TEXT)) {
                authorizeCallBack(requestCode, resultCode, data);
            } else if (!this.mStatStack.empty()) {
                holder = (StatHolder) this.mStatStack.pop();
                final Bundle extras = data == null ? null : data.getExtras();
                if (resultCode == -1) {
                    QueuedWork.runInBack(new Runnable() {
                        public void run() {
                            UMAPIShareHandler.this.sendShareRequest(UMAPIShareHandler.this.getResult(holder.Content, extras), holder.Listener);
                            Log.m4546d(SocialConstants.PARAM_ACT, "sent share request");
                        }
                    });
                } else if (holder.Listener != null) {
                    holder.Listener.onCancel(getPlatform());
                }
            }
        }
    }

    public boolean share(final ShareContent content, final UMShareListener listener) {
        if (isAuthorize()) {
            doShare(content, listener);
        } else {
            authorize(new UMAuthListener() {

                class C16141 implements Runnable {
                    C16141() {
                    }

                    public void run() {
                        UMAPIShareHandler.this.doShare(content, listener);
                    }
                }

                public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                    QueuedWork.runInBack(new C16141());
                }

                public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                    listener.onError(platform, t);
                }

                public void onCancel(SHARE_MEDIA platform, int action) {
                    listener.onCancel(platform);
                }
            });
        }
        return false;
    }

    private void doShare(ShareContent shareContent, UMShareListener listener) {
        if (Config.OpenEditor) {
            StatHolder holder = new StatHolder();
            holder.Content = shareContent;
            holder.Listener = listener;
            this.mStatStack.push(holder);
            if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
                try {
                    Intent in = new Intent((Context) this.mWeakAct.get(), Class.forName("com.umeng.socialize.editorpage.ShareActivity"));
                    in.putExtras(getEditable(shareContent));
                    ((Activity) this.mWeakAct.get()).startActivityForResult(in, getRequestCode());
                    return;
                } catch (ClassNotFoundException e) {
                    sendShareRequest(shareContent, listener);
                    Log.m4548e("没有加入界面jar");
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        sendShareRequest(shareContent, listener);
    }

    private void sendShareRequest(ShareContent shareContent, final UMShareListener listener) {
        final SHARE_MEDIA media = getPlatform();
        SharePostRequest request = new SharePostRequest(getContext(), media.toString().toLowerCase(), getUID(), shareContent);
        request.setReqType(0);
        SocializeReseponse resp = RestAPI.doShare(request);
        final SocializeReseponse finalResp = resp;
        if (resp == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    listener.onError(media, new SocializeException("response is null"));
                }
            });
        } else if (resp.isOk()) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    listener.onResult(media);
                }
            });
        } else {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    listener.onError(media, new SocializeException(finalResp.mStCode, finalResp.mMsg));
                }
            });
        }
        if (shareContent.mFollow != null) {
        }
    }
}
