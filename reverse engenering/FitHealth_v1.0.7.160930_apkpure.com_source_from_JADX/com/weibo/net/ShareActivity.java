package com.weibo.net;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.open.SocialConstants;
import com.weibo.android.R;
import com.weibo.net.AsyncWeiboRunner.RequestListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import p031u.aly.au;

public class ShareActivity extends Activity implements OnClickListener, RequestListener {
    public static final String EXTRA_ACCESS_TOKEN = "com.weibo.android.accesstoken";
    public static final String EXTRA_PIC_URI = "com.weibo.android.pic.uri";
    public static final String EXTRA_TOKEN_SECRET = "com.weibo.android.token.secret";
    public static final String EXTRA_WEIBO_CONTENT = "com.weibo.android.content";
    public static final int WEIBO_MAX_LENGTH = 140;
    private String mAccessToken = "";
    private String mContent = "";
    private EditText mEdit;
    private String mPicPath = "";
    private FrameLayout mPiclayout;
    private Button mSend;
    private TextView mTextNum;
    private String mTokenSecret = "";

    class C10111 implements TextWatcher {
        C10111() {
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int len = ShareActivity.this.mEdit.getText().toString().length();
            if (len <= ShareActivity.WEIBO_MAX_LENGTH) {
                len = 140 - len;
                ShareActivity.this.mTextNum.setTextColor(R.color.text_num_gray);
                if (!ShareActivity.this.mSend.isEnabled()) {
                    ShareActivity.this.mSend.setEnabled(true);
                }
            } else {
                len -= 140;
                ShareActivity.this.mTextNum.setTextColor(SupportMenu.CATEGORY_MASK);
                if (ShareActivity.this.mSend.isEnabled()) {
                    ShareActivity.this.mSend.setEnabled(false);
                }
            }
            ShareActivity.this.mTextNum.setText(String.valueOf(len));
        }
    }

    class C10122 extends Thread {
        C10122() {
        }

        public void run() {
            Weibo weibo = Weibo.getInstance();
            try {
                if (TextUtils.isEmpty(weibo.getAccessToken().getToken())) {
                    Toast.makeText(ShareActivity.this, ShareActivity.this.getString(R.string.please_login), 1);
                    return;
                }
                ShareActivity.this.mContent = ShareActivity.this.mEdit.getText().toString();
                if (TextUtils.isEmpty(ShareActivity.this.mPicPath)) {
                    ShareActivity.this.update(weibo, Weibo.getAppKey(), ShareActivity.this.mContent, "", "");
                } else {
                    ShareActivity.this.upload(weibo, Weibo.getAppKey(), ShareActivity.this.mPicPath, ShareActivity.this.mContent, "", "");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (WeiboException e3) {
                e3.printStackTrace();
            }
        }
    }

    class C10133 implements DialogInterface.OnClickListener {
        C10133() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ShareActivity.this.mEdit.setText("");
        }
    }

    class C10144 implements DialogInterface.OnClickListener {
        C10144() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ShareActivity.this.mPiclayout.setVisibility(8);
        }
    }

    class C10155 implements Runnable {
        C10155() {
        }

        public void run() {
            Toast.makeText(ShareActivity.this, R.string.send_sucess, 1).show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_mblog_view);
        Intent in = getIntent();
        this.mPicPath = in.getStringExtra(EXTRA_PIC_URI);
        this.mContent = in.getStringExtra(EXTRA_WEIBO_CONTENT);
        this.mAccessToken = in.getStringExtra(EXTRA_ACCESS_TOKEN);
        this.mTokenSecret = in.getStringExtra(EXTRA_TOKEN_SECRET);
        Weibo.getInstance().setAccessToken(new AccessToken(this.mAccessToken, this.mTokenSecret));
        ((Button) findViewById(R.id.btnClose)).setOnClickListener(this);
        this.mSend = (Button) findViewById(R.id.btnSend);
        this.mSend.setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.ll_text_limit_unit)).setOnClickListener(this);
        this.mTextNum = (TextView) findViewById(R.id.tv_text_limit);
        ((ImageView) findViewById(R.id.ivDelPic)).setOnClickListener(this);
        this.mEdit = (EditText) findViewById(R.id.etEdit);
        this.mEdit.addTextChangedListener(new C10111());
        this.mEdit.setText(this.mContent);
        this.mPiclayout = (FrameLayout) findViewById(R.id.flPic);
        if (TextUtils.isEmpty(this.mPicPath)) {
            this.mPiclayout.setVisibility(8);
            return;
        }
        this.mPiclayout.setVisibility(0);
        if (new File(this.mPicPath).exists()) {
            ((ImageView) findViewById(R.id.ivImage)).setImageBitmap(BitmapFactory.decodeFile(this.mPicPath));
            return;
        }
        this.mPiclayout.setVisibility(8);
    }

    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnClose) {
            finish();
        } else if (viewId == R.id.btnSend) {
            new C10122().start();
        } else if (viewId == R.id.ll_text_limit_unit) {
            new Builder(this).setTitle(R.string.attention).setMessage(R.string.delete_all).setPositiveButton(R.string.ok, new C10133()).setNegativeButton(R.string.cancel, null).create().show();
        } else if (viewId == R.id.ivDelPic) {
            new Builder(this).setTitle(R.string.attention).setMessage(R.string.del_pic).setPositiveButton(R.string.ok, new C10144()).setNegativeButton(R.string.cancel, null).create().show();
        }
    }

    private String upload(Weibo weibo, String source, String file, String status, String lon, String lat) throws WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add(SocialConstants.PARAM_SOURCE, source);
        bundle.add(com.umeng.socialize.editorpage.ShareActivity.KEY_PIC, file);
        bundle.add("status", status);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add(au.f3570Y, lat);
        }
        String rlt = "";
        new AsyncWeiboRunner(weibo).request(this, Weibo.SERVER + "statuses/upload.json", bundle, "POST", this);
        return rlt;
    }

    private String update(Weibo weibo, String source, String status, String lon, String lat) throws MalformedURLException, IOException, WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add(SocialConstants.PARAM_SOURCE, source);
        bundle.add("status", status);
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add(au.f3570Y, lat);
        }
        String rlt = "";
        new AsyncWeiboRunner(weibo).request(this, Weibo.SERVER + "statuses/update.json", bundle, "POST", this);
        return rlt;
    }

    private String upload_url_text(Weibo weibo, String source, String status, String pic_url, String lon, String lat) throws MalformedURLException, IOException, WeiboException {
        WeiboParameters bundle = new WeiboParameters();
        bundle.add(SocialConstants.PARAM_SOURCE, source);
        bundle.add("status", status);
        if (!TextUtils.isEmpty(pic_url)) {
            bundle.add("url", pic_url);
        }
        if (!TextUtils.isEmpty(lon)) {
            bundle.add("lon", lon);
        }
        if (!TextUtils.isEmpty(lat)) {
            bundle.add(au.f3570Y, lat);
        }
        String rlt = "";
        new AsyncWeiboRunner(weibo).request(this, Weibo.SERVER + "statuses/upload_url_text.json", bundle, "POST", this);
        return rlt;
    }

    public void onComplete(String response) {
        runOnUiThread(new C10155());
        finish();
    }

    public void onIOException(IOException e) {
    }

    public void onError(final WeiboException e) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ShareActivity.this, String.format(new StringBuilder(String.valueOf(ShareActivity.this.getString(R.string.send_failed))).append(":%s").toString(), new Object[]{e.getMessage()}), 1).show();
            }
        });
    }
}
