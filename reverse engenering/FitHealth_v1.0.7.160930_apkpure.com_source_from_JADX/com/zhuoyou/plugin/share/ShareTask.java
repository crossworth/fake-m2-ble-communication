package com.zhuoyou.plugin.share;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.fithealth.running.R;
import com.tencent.open.SocialConstants;
import com.umeng.socialize.editorpage.ShareActivity;
import com.weibo.net.AsyncWeiboRunner;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import p031u.aly.au;

public class ShareTask implements Runnable {
    private Context mContext = null;
    private String mPicPath = null;
    private RequestListener mRequestListener = null;
    private String mStatus = null;

    public ShareTask(Context context, String picPath, String status, RequestListener listener) {
        this.mStatus = status;
        this.mRequestListener = listener;
        this.mContext = context;
        this.mPicPath = picPath;
    }

    public void run() {
        try {
            Weibo weibo = Weibo.getInstance();
            if (this.mStatus.equals("")) {
                this.mStatus = this.mContext.getResources().getString(R.string.share_photos);
            }
            if (TextUtils.isEmpty(this.mPicPath) || TextUtils.isEmpty(this.mStatus) || this.mPicPath.equals("") || this.mStatus.equals("")) {
                Toast.makeText(this.mContext, R.string.share_mistake, 0).show();
            } else {
                upload(weibo, Weibo.getAppKey(), this.mPicPath, this.mStatus, "", "");
            }
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }

    private String upload(Weibo weibo, String source, String file, String status, String lon, String lat) throws WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add(SocialConstants.PARAM_SOURCE, source);
        bundle.add(ShareActivity.KEY_PIC, file);
        bundle.add("status", status);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add(au.f3570Y, lat);
        }
        String rlt = "";
        new AsyncWeiboRunner(weibo).request(this.mContext, Weibo.SERVER + "statuses/upload.json", bundle, "POST", this.mRequestListener);
        return rlt;
    }
}
