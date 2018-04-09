package com.zhuoyou.plugin.running.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;
import com.zhuoyou.plugin.running.tools.Tools;

public class ShareAppDialog extends BaseDialog {
    private ImageView imgFacebook;
    private ImageView imgInstagram;
    private ImageView imgMoments;
    private ImageView imgQQ;
    private ImageView imgQzone;
    private ImageView imgTwitter;
    private ImageView imgWechat;
    private ImageView imgWeibo;
    private ImageView imgWhatsApp;
    private Activity mActivity;
    private OnClickListener onClickListener = new C19551();
    private UMShareListener shareListener = new C19562();

    class C19551 implements OnClickListener {
        C19551() {
        }

        public void onClick(View v) {
            ShareAppDialog.this.dismiss();
            switch (v.getId()) {
                case C1680R.id.share_facebook:
                    ShareAppDialog.this.shareFacebook();
                    return;
                case C1680R.id.share_twitter:
                    ShareAppDialog.this.shareTwitter();
                    return;
                case C1680R.id.share_instagram:
                    ShareAppDialog.this.shareInstagram();
                    return;
                case C1680R.id.share_whatsapp:
                    ShareAppDialog.this.shareWhatsApp();
                    return;
                case C1680R.id.share_qq:
                    ShareAppDialog.this.qqShare();
                    return;
                case C1680R.id.share_qzone:
                    ShareAppDialog.this.qzoneShare();
                    return;
                case C1680R.id.share_weibo:
                    ShareAppDialog.this.weiboShare();
                    return;
                case C1680R.id.share_weixin:
                    ShareAppDialog.this.wechatShare();
                    return;
                case C1680R.id.share_friend_circle:
                    ShareAppDialog.this.wechatCircle();
                    return;
                default:
                    return;
            }
        }
    }

    class C19562 implements UMShareListener {
        C19562() {
        }

        public void onResult(SHARE_MEDIA platform) {
            Log.i("zhuqichao", "success:" + platform);
            if (platform == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share success!");
            }
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i("zhuqichao", "error:" + platform);
            if (platform == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share fail!");
            }
        }

        public void onCancel(SHARE_MEDIA platform) {
            Log.i("zhuqichao", "cancel:" + platform);
            if (platform == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share cancel!");
            }
        }
    }

    public ShareAppDialog(Activity activity) {
        this.mActivity = activity;
        this.dialog = new Dialog(activity, C1680R.style.DefaultDialog);
        this.dialog.setContentView(C1680R.layout.layout_share_app_dialog);
        this.dialog.setCanceledOnTouchOutside(true);
        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(C1680R.style.BottomDialogShowAnim);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(activity).x;
        window.setAttributes(lp);
        initView();
    }

    private void initView() {
        this.imgQQ = (ImageView) this.dialog.findViewById(C1680R.id.share_qq);
        this.imgQQ.setOnClickListener(this.onClickListener);
        this.imgQzone = (ImageView) this.dialog.findViewById(C1680R.id.share_qzone);
        this.imgQzone.setOnClickListener(this.onClickListener);
        this.imgWechat = (ImageView) this.dialog.findViewById(C1680R.id.share_weixin);
        this.imgWechat.setOnClickListener(this.onClickListener);
        this.imgWeibo = (ImageView) this.dialog.findViewById(C1680R.id.share_weibo);
        this.imgWeibo.setOnClickListener(this.onClickListener);
        this.imgMoments = (ImageView) this.dialog.findViewById(C1680R.id.share_friend_circle);
        this.imgMoments.setOnClickListener(this.onClickListener);
        this.imgFacebook = (ImageView) this.dialog.findViewById(C1680R.id.share_facebook);
        this.imgFacebook.setOnClickListener(this.onClickListener);
        this.imgTwitter = (ImageView) this.dialog.findViewById(C1680R.id.share_twitter);
        this.imgTwitter.setOnClickListener(this.onClickListener);
        this.imgInstagram = (ImageView) this.dialog.findViewById(C1680R.id.share_instagram);
        this.imgInstagram.setOnClickListener(this.onClickListener);
        this.imgWhatsApp = (ImageView) this.dialog.findViewById(C1680R.id.share_whatsapp);
        this.imgWhatsApp.setOnClickListener(this.onClickListener);
        initShareButton();
        umengShareDialogTitle();
    }

    private void initShareButton() {
        if (this.mActivity.getResources().getConfiguration().locale.getCountry().equals("CN")) {
            this.imgFacebook.setVisibility(8);
            this.imgTwitter.setVisibility(8);
            this.imgInstagram.setVisibility(8);
            this.imgWhatsApp.setVisibility(8);
            return;
        }
        this.imgQQ.setVisibility(8);
        this.imgQzone.setVisibility(8);
        this.imgWechat.setVisibility(8);
        this.imgMoments.setVisibility(8);
    }

    private void qqShare() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.QQ).setCallback(this.shareListener).withTitle(this.mActivity.getString(C1680R.string.app_name)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withText(this.mActivity.getString(C1680R.string.share_content)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void qzoneShare() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.QZONE).setCallback(this.shareListener).withTitle(this.mActivity.getString(C1680R.string.app_name)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withText(this.mActivity.getString(C1680R.string.share_content)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void weiboShare() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.SINA).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void wechatShare() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WEIXIN).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void wechatCircle() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareFacebook() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareTwitter() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.TWITTER).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content) + Tools.DOWNLOAD_SITE).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).share();
    }

    private void shareInstagram() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content)).withMedia(new UMImage(this.mActivity, (int) C1680R.drawable.share_img)).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareWhatsApp() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.mActivity.getString(C1680R.string.share_content) + Tools.DOWNLOAD_SITE).share();
    }

    private void umengShareDialogTitle() {
        ProgressDialog dialog = Tools.getProgressDialog(this.mActivity);
        dialog.setMessage(this.mActivity.getString(C1680R.string.start_share));
        Config.dialog = dialog;
    }
}
