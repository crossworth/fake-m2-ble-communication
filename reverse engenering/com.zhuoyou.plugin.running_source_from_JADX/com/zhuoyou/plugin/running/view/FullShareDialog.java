package com.zhuoyou.plugin.running.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;
import com.zhuoyou.plugin.running.tools.Tools;

public class FullShareDialog extends BaseDialog {
    private ImageView imgFacebook;
    private ImageView imgInstagram;
    private ImageView imgMoments;
    private ImageView imgQQ;
    private ImageView imgQzone;
    private ImageView imgTwitter;
    private ImageView imgWechat;
    private ImageView imgWeibo;
    private ImageView imgWhatsApp;
    private LinearLayout layoutBootom;
    private LinearLayout layoutContent;
    private Activity mActivity;
    private String message;
    private OnClickListener onClickListener = new C19341();
    private UMShareListener shareListener = new C19352();
    private TextView tvBack;

    class C19341 implements OnClickListener {
        C19341() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case C1680R.id.share_facebook:
                    FullShareDialog.this.shareFacebook();
                    return;
                case C1680R.id.share_twitter:
                    FullShareDialog.this.shareTwitter();
                    return;
                case C1680R.id.share_instagram:
                    FullShareDialog.this.shareInstagram();
                    return;
                case C1680R.id.share_whatsapp:
                    FullShareDialog.this.shareWhatsApp();
                    return;
                case C1680R.id.share_qq:
                    FullShareDialog.this.shareQQ();
                    return;
                case C1680R.id.share_qzone:
                    FullShareDialog.this.shareQzone();
                    return;
                case C1680R.id.share_wechat:
                    FullShareDialog.this.shareWeiChat();
                    return;
                case C1680R.id.share_moments:
                    FullShareDialog.this.shareMoments();
                    return;
                case C1680R.id.share_weibo:
                    FullShareDialog.this.shareWeibo();
                    return;
                case C1680R.id.btn_back:
                    FullShareDialog.this.dismiss();
                    return;
                default:
                    return;
            }
        }
    }

    class C19352 implements UMShareListener {
        C19352() {
        }

        public void onResult(SHARE_MEDIA share_media) {
            Log.i("zhuqichao", "success:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share success!");
            }
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.i("zhuqichao", "error:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share fail!");
            }
        }

        public void onCancel(SHARE_MEDIA share_media) {
            Log.i("zhuqichao", "cancel:" + share_media);
            if (share_media == SHARE_MEDIA.TWITTER) {
                Tools.makeToast("Share cancel!");
            }
        }
    }

    public FullShareDialog(Activity activity) {
        this.mActivity = activity;
        this.dialog = new Dialog(activity, C1680R.style.FullScreenDialog);
        this.dialog.setContentView(C1680R.layout.layout_full_share_dialog);
        this.dialog.setCanceledOnTouchOutside(true);
        Window window = this.dialog.getWindow();
        window.setGravity(17);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(activity).x;
        lp.height = DisplayUtils.getScreenMetrics(activity).y;
        window.setAttributes(lp);
        initView();
    }

    private void initView() {
        this.imgQQ = (ImageView) this.dialog.findViewById(C1680R.id.share_qq);
        this.imgQQ.setOnClickListener(this.onClickListener);
        this.imgQzone = (ImageView) this.dialog.findViewById(C1680R.id.share_qzone);
        this.imgQzone.setOnClickListener(this.onClickListener);
        this.imgWechat = (ImageView) this.dialog.findViewById(C1680R.id.share_wechat);
        this.imgWechat.setOnClickListener(this.onClickListener);
        this.imgMoments = (ImageView) this.dialog.findViewById(C1680R.id.share_moments);
        this.imgMoments.setOnClickListener(this.onClickListener);
        this.imgWeibo = (ImageView) this.dialog.findViewById(C1680R.id.share_weibo);
        this.imgWeibo.setOnClickListener(this.onClickListener);
        this.imgFacebook = (ImageView) this.dialog.findViewById(C1680R.id.share_facebook);
        this.imgFacebook.setOnClickListener(this.onClickListener);
        this.imgTwitter = (ImageView) this.dialog.findViewById(C1680R.id.share_twitter);
        this.imgTwitter.setOnClickListener(this.onClickListener);
        this.imgInstagram = (ImageView) this.dialog.findViewById(C1680R.id.share_instagram);
        this.imgInstagram.setOnClickListener(this.onClickListener);
        this.imgWhatsApp = (ImageView) this.dialog.findViewById(C1680R.id.share_whatsapp);
        this.imgWhatsApp.setOnClickListener(this.onClickListener);
        initShareButton();
        this.tvBack = (TextView) this.dialog.findViewById(C1680R.id.btn_back);
        this.tvBack.setOnClickListener(this.onClickListener);
        this.layoutContent = (LinearLayout) this.dialog.findViewById(C1680R.id.layout_share_content);
        this.layoutBootom = (LinearLayout) this.dialog.findViewById(C1680R.id.layout_bootom);
        this.layoutContent.setDrawingCacheEnabled(true);
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

    private void shareQQ() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.QQ).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareQzone() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.QZONE).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareWeiChat() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WEIXIN).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareMoments() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareWeibo() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.SINA).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareFacebook() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.FACEBOOK).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareTwitter() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.TWITTER).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message + Tools.DOWNLOAD_SITE).withMedia(new UMImage(this.mActivity, getSharePicture())).share();
    }

    private void shareInstagram() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.INSTAGRAM).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message).withMedia(new UMImage(this.mActivity, getSharePicture())).withTargetUrl(Tools.DOWNLOAD_SITE).share();
    }

    private void shareWhatsApp() {
        new ShareAction(this.mActivity).setPlatform(SHARE_MEDIA.WHATSAPP).withTitle(this.mActivity.getString(C1680R.string.app_name)).setCallback(this.shareListener).withText(this.message + Tools.DOWNLOAD_SITE).share();
    }

    private Bitmap getSharePicture() {
        Bitmap src = this.layoutContent.getDrawingCache();
        int[] location = new int[2];
        this.tvBack.getLocationOnScreen(location);
        int y1 = location[1] + this.tvBack.getMeasuredHeight();
        this.layoutBootom.getLocationOnScreen(location);
        return Bitmap.createBitmap(src, 0, y1, DisplayUtils.getScreenMetrics(this.mActivity).x, location[1] - y1);
    }

    public void setContentView(View view) {
        this.layoutContent.addView(view);
    }

    public void setShareListener(UMShareListener listener) {
        this.shareListener = listener;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void umengShareDialogTitle() {
        ProgressDialog dialog = Tools.getProgressDialog(this.mActivity);
        dialog.setMessage(this.mActivity.getString(C1680R.string.start_share));
        Config.dialog = dialog;
    }
}
