package com.umeng.socialize.editorpage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.editorpage.KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener;
import com.umeng.socialize.media.WeiXinShareContent;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.io.File;

public class ShareActivity extends Activity implements OnClickListener {
    private static int MAX_WORD_NUM = 140;
    private static final String TAG = "ShareActivity";
    private ResContainer f4934R;
    private boolean isPad = false;
    private Dialog mAtDialog;
    private Button mCloseBtn;
    private Context mContext;
    private EditText mEditText;
    private String mImage;
    private int mKeyboardState;
    private SHARE_MEDIA mPlatform;
    protected ImageView mPreviewImageView;
    private View mPreviewRemBt;
    private KeyboardListenRelativeLayout mRoot;
    private Button mSendBtn;
    private String mText;
    private String mTitle;
    private TextView mWordNumTv;
    private boolean mWordsOverflow;
    TextWatcher watcher = new C15962();

    class C15951 implements IOnKeyboardStateChangedListener {
        C15951() {
        }

        public void onKeyboardStateChanged(int state) {
            ShareActivity.this.mKeyboardState = state;
            Log.m4546d(ShareActivity.TAG, "onKeyboardStateChanged  now state is " + state);
        }
    }

    class C15962 implements TextWatcher {
        C15962() {
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ShareActivity.this.mWordsOverflow = ShareActivity.this.checkWordsOverflow();
        }
    }

    class C15973 implements Runnable {
        C15973() {
        }

        public void run() {
            ShareActivity.this.finish();
        }
    }

    class C15984 implements Runnable {
        C15984() {
        }

        public void run() {
            ShareActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.f4934R = ResContainer.get(this);
        this.isPad = SocializeUtils.isFloatWindowStyle(this);
        if (!this.isPad) {
            setTheme(this.f4934R.style("Theme.UMDefault"));
        }
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(this.f4934R.layout("umeng_socialize_post_share"));
        LayoutParams lp = getWindow().getAttributes();
        lp.softInputMode = 16;
        if (this.isPad) {
            int[] windowSize = SocializeUtils.getFloatWindowSize(this.mContext);
            lp.width = windowSize[0];
            lp.height = windowSize[1];
        }
        getWindow().setAttributes(lp);
        this.mRoot = (KeyboardListenRelativeLayout) findViewById(this.f4934R.id("umeng_socialize_share_root"));
        this.mRoot.setOnKeyboardStateChangedListener(new C15951());
        Bundle bundle = getIntent().getExtras();
        this.mPlatform = StringtoMedia(bundle.getString(SocializeConstants.KEY_PLATFORM));
        if (this.mPlatform == SHARE_MEDIA.RENREN) {
            MAX_WORD_NUM = 120;
        } else {
            MAX_WORD_NUM = 140;
        }
        this.mTitle = bundle.getString("title");
        this.mText = bundle.getString(SocializeConstants.KEY_TEXT);
        this.mImage = bundle.getString(SocializeConstants.KEY_PIC);
        initView();
    }

    private SHARE_MEDIA StringtoMedia(String media) {
        if (media.equals("TENCENT")) {
            return SHARE_MEDIA.TENCENT;
        }
        if (media.equals("RENREN")) {
            return SHARE_MEDIA.RENREN;
        }
        if (media.equals("DOUBAN")) {
            return SHARE_MEDIA.DOUBAN;
        }
        return SHARE_MEDIA.SINA;
    }

    protected void onResume() {
        this.mEditText.requestFocus();
        super.onResume();
    }

    private void initView() {
        ((TextView) findViewById(this.f4934R.id("umeng_socialize_title_bar_middleTv"))).setText(this.mTitle);
        this.mCloseBtn = (Button) findViewById(this.f4934R.id("umeng_socialize_title_bar_leftBt"));
        this.mSendBtn = (Button) findViewById(this.f4934R.id("umeng_socialize_title_bar_rightBt"));
        this.mCloseBtn.setOnClickListener(this);
        this.mSendBtn.setOnClickListener(this);
        this.mEditText = (EditText) findViewById(this.f4934R.id("umeng_socialize_share_edittext"));
        if (!TextUtils.isEmpty(this.mText)) {
            this.mEditText.setText(this.mText);
            this.mEditText.setSelection(this.mText.length());
        }
        this.mEditText.addTextChangedListener(this.watcher);
        this.mWordNumTv = (TextView) findViewById(this.f4934R.id("umeng_socialize_share_word_num"));
        this.mWordsOverflow = checkWordsOverflow();
        if (this.mImage != null) {
            findViewById(this.f4934R.id("umeng_socialize_share_image")).setVisibility(0);
            this.mPreviewImageView = (ImageView) findViewById(this.f4934R.id("umeng_socialize_share_previewImg"));
            this.mPreviewRemBt = findViewById(this.f4934R.id("umeng_socialize_share_previewImg_remove"));
            this.mPreviewRemBt.setOnClickListener(this);
            this.mPreviewImageView.setVisibility(0);
            if (this.mImage.equals("video")) {
                this.mPreviewImageView.setImageResource(ResContainer.getResourceId(this.mContext, "drawable", "umeng_socialize_share_video"));
            } else if (this.mImage.equals(WeiXinShareContent.TYPE_MUSIC)) {
                this.mPreviewImageView.setImageResource(ResContainer.getResourceId(this.mContext, "drawable", "umeng_socialize_share_music"));
            } else {
                this.mPreviewImageView.setImageURI(Uri.fromFile(new File(this.mImage)));
            }
        }
    }

    private void onSend(View view) {
        String content = this.mEditText.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            Toast.makeText(this, "输入内容为空...", 0).show();
        } else if (SocializeUtils.countContentLength(content) > MAX_WORD_NUM) {
            Toast.makeText(this, "输入内容超过140个字.", 0).show();
        } else if (this.mWordsOverflow) {
            Toast.makeText(this.mContext, "超出最大字数限制....", 0).show();
        } else {
            Intent in = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(SocializeConstants.KEY_TEXT, content);
            bundle.putString(SocializeConstants.KEY_PIC, this.mImage);
            in.putExtras(bundle);
            setResult(-1, in);
            safeClose();
        }
    }

    public void onCancel(View view) {
        setResult(1000);
        safeClose();
    }

    private void onRemoveImage(View view) {
        this.mImage = null;
        findViewById(this.f4934R.id("umeng_socialize_share_image")).setVisibility(8);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == this.f4934R.id("umeng_socialize_share_previewImg_remove")) {
            onRemoveImage(view);
        } else if (id == this.f4934R.id("umeng_socialize_title_bar_rightBt")) {
            onSend(view);
        } else if (id == this.f4934R.id("umeng_socialize_title_bar_leftBt")) {
            onCancel(view);
        }
    }

    private void setPreview(int resId, Bitmap bitmap) {
        try {
            this.mPreviewImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            this.mPreviewImageView.setImageResource(resId);
        }
        this.mPreviewImageView.setVisibility(0);
        this.mPreviewRemBt.setVisibility(0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            setResult(1000);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void safeClose() {
        if (this.mKeyboardState == -3) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
            new Handler().postDelayed(new C15973(), 500);
            return;
        }
        finish();
    }

    private boolean checkWordsOverflow() {
        int tmp = MAX_WORD_NUM - SocializeUtils.countContentLength(this.mEditText.getText().toString());
        Log.m4546d(TAG, "onTextChanged " + tmp + "   " + SocializeUtils.countContentLength(this.mEditText.getText().toString()));
        this.mWordNumTv.setText("" + tmp);
        if (tmp >= 0) {
            return false;
        }
        return true;
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!SocializeConstants.BACKKEY_COMPLETE_CLOSE || event.getKeyCode() != 4) {
            return super.dispatchKeyEvent(event);
        }
        new Handler().postDelayed(new C15984(), 400);
        return true;
    }
}
