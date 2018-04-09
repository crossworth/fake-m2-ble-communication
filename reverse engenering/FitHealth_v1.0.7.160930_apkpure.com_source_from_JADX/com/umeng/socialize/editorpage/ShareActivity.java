package com.umeng.socialize.editorpage;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.internal.AnalyticsEvents;
import com.umeng.socialize.Config;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UMLocation;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.editorpage.location.C0959b;
import com.umeng.socialize.editorpage.location.C0961d;
import com.umeng.socialize.editorpage.location.C1811a;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.SocializeUtils;
import java.io.File;
import java.util.Set;

public class ShareActivity extends Activity implements OnClickListener {
    public static final int CANCLE_RESULTCODE = 1000;
    public static final String FOLLOW_FILE_NAME = "umeng_follow";
    public static final String KEY_AT = "at";
    public static final String KEY_FOLLOW = "follow_";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_PIC = "pic";
    public static final String KEY_PLATFORM = "media";
    public static final String KEY_TEXT = "txt";
    public static final String KEY_TITLE = "title";
    public static final int REQUEST_CODE = 1229;
    private static final String f3265c = "ShareActivity";
    private static int f3266d = com.weibo.net.ShareActivity.WEIBO_MAX_LENGTH;
    private SHARE_MEDIA f3267A;
    private C1811a f3268B;
    private UMLocation f3269C;
    private int f3270D;
    private boolean f3271E = false;
    private Dialog f3272F;
    private Set<String> f3273G = null;
    private C0959b f3274H = null;
    protected ImageView f3275a;
    TextWatcher f3276b = new C0954b(this);
    private String f3277e;
    private String f3278f;
    private String f3279g;
    private boolean f3280h;
    private boolean f3281i;
    private boolean f3282j;
    private ResContainer f3283k;
    private Button f3284l;
    private Button f3285m;
    private EditText f3286n;
    private ImageButton f3287o;
    private ImageButton f3288p;
    private View f3289q;
    private View f3290r;
    private View f3291s;
    private TextView f3292t;
    private RelativeLayout f3293u;
    private CheckBox f3294v;
    private KeyboardListenRelativeLayout f3295w;
    private ProgressBar f3296x;
    private Context f3297y;
    private boolean f3298z;

    protected void onCreate(Bundle bundle) {
        boolean z;
        this.f3283k = ResContainer.get(this);
        this.f3271E = SocializeUtils.isFloatWindowStyle(this);
        if (!this.f3271E) {
            setTheme(this.f3283k.style("Theme.UMDefault"));
        }
        super.onCreate(bundle);
        this.f3297y = this;
        setContentView(this.f3283k.layout("umeng_socialize_post_share"));
        LayoutParams attributes = getWindow().getAttributes();
        attributes.softInputMode = 16;
        if (this.f3271E) {
            int[] floatWindowSize = SocializeUtils.getFloatWindowSize(this.f3297y);
            attributes.width = floatWindowSize[0];
            attributes.height = floatWindowSize[1];
        }
        getWindow().setAttributes(attributes);
        this.f3295w = (KeyboardListenRelativeLayout) findViewById(this.f3283k.id("umeng_socialize_share_root"));
        this.f3295w.m3197a(new C1809a(this));
        Bundle extras = getIntent().getExtras();
        this.f3267A = m3199a(extras.getString(KEY_PLATFORM));
        if (this.f3267A == SHARE_MEDIA.RENREN) {
            f3266d = 120;
        } else {
            f3266d = com.weibo.net.ShareActivity.WEIBO_MAX_LENGTH;
        }
        this.f3277e = extras.getString("title");
        this.f3278f = extras.getString(KEY_TEXT);
        this.f3279g = extras.getString(KEY_PIC);
        this.f3280h = extras.getBoolean(KEY_FOLLOW, false);
        this.f3281i = extras.getBoolean(KEY_AT);
        this.f3281i = false;
        if (extras.getBoolean("location") && Config.ShareLocation) {
            z = true;
        } else {
            z = false;
        }
        this.f3282j = z;
        m3210c();
        if (this.f3282j) {
            m3207b();
        }
    }

    private SHARE_MEDIA m3199a(String str) {
        if (str.equals("TENCENT")) {
            return SHARE_MEDIA.TENCENT;
        }
        if (str.equals("RENREN")) {
            return SHARE_MEDIA.RENREN;
        }
        if (str.equals("DOUBAN")) {
            return SHARE_MEDIA.DOUBAN;
        }
        return SHARE_MEDIA.SINA;
    }

    protected void onResume() {
        if (this.f3282j) {
            m3213e();
        }
        this.f3286n.requestFocus();
        super.onResume();
    }

    private void m3207b() {
        this.f3268B = new C1811a();
        C0961d c0961d = new C0961d();
        c0961d.m3223a((Context) this);
        this.f3268B.m4995a(c0961d);
        this.f3268B.m4993a((Context) this);
    }

    private void m3210c() {
        ((TextView) findViewById(this.f3283k.id("umeng_socialize_title_bar_middleTv"))).setText(this.f3277e);
        this.f3284l = (Button) findViewById(this.f3283k.id("umeng_socialize_title_bar_leftBt"));
        this.f3285m = (Button) findViewById(this.f3283k.id("umeng_socialize_title_bar_rightBt"));
        this.f3284l.setOnClickListener(this);
        this.f3285m.setOnClickListener(this);
        this.f3286n = (EditText) findViewById(this.f3283k.id("umeng_socialize_share_edittext"));
        if (!TextUtils.isEmpty(this.f3278f)) {
            this.f3286n.setText(this.f3278f);
            this.f3286n.setSelection(this.f3278f.length());
        }
        this.f3286n.addTextChangedListener(this.f3276b);
        this.f3292t = (TextView) findViewById(this.f3283k.id("umeng_socialize_share_word_num"));
        this.f3298z = m3212d();
        if (this.f3282j) {
            findViewById(this.f3283k.id("umeng_socialize_share_location")).setVisibility(0);
            this.f3288p = (ImageButton) findViewById(this.f3283k.id("umeng_socialize_location_ic"));
            this.f3288p.setOnClickListener(this);
            this.f3288p.setVisibility(0);
            this.f3288p.setImageResource(this.f3283k.drawable("umeng_socialize_location_off"));
            this.f3289q = findViewById(this.f3283k.id("umeng_socialize_location_progressbar"));
        }
        if (this.f3281i) {
            this.f3287o = (ImageButton) findViewById(this.f3283k.id("umeng_socialize_share_at"));
            this.f3287o.setVisibility(0);
            this.f3287o.setOnClickListener(this);
        }
        if (this.f3279g != null) {
            findViewById(this.f3283k.id("umeng_socialize_share_image")).setVisibility(0);
            this.f3275a = (ImageView) findViewById(this.f3283k.id("umeng_socialize_share_previewImg"));
            this.f3290r = findViewById(this.f3283k.id("umeng_socialize_share_previewImg_remove"));
            this.f3290r.setOnClickListener(this);
            this.f3275a.setVisibility(0);
            if (this.f3279g.equals(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO)) {
                this.f3275a.setImageResource(ResContainer.getResourceId(this.f3297y, "drawable", "umeng_socialize_share_video"));
            } else if (this.f3279g.equals("music")) {
                this.f3275a.setImageResource(ResContainer.getResourceId(this.f3297y, "drawable", "umeng_socialize_share_music"));
            } else {
                this.f3275a.setImageURI(Uri.fromFile(new File(this.f3279g)));
            }
        }
        if (this.f3280h) {
            this.f3294v = (CheckBox) findViewById(this.f3283k.id("umeng_socialize_follow_check"));
            this.f3294v.setOnClickListener(this);
            this.f3294v.setVisibility(0);
        }
    }

    private void m3202a(View view) {
        String obj = this.f3286n.getText().toString();
        if (TextUtils.isEmpty(obj.trim())) {
            Toast.makeText(this, "输入内容为空...", 0).show();
        } else if (SocializeUtils.countContentLength(obj) > f3266d) {
            Toast.makeText(this, "输入内容超过140个字.", 0).show();
        } else if (this.f3298z) {
            Toast.makeText(this.f3297y, "超出最大字数限制....", 0).show();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TEXT, obj);
            bundle.putString(KEY_PIC, this.f3279g);
            bundle.putBoolean(KEY_FOLLOW, this.f3280h);
            bundle.putSerializable("location", this.f3269C);
            intent.putExtras(bundle);
            setResult(-1, intent);
            m3215a();
        }
    }

    public void onCancel(View view) {
        setResult(1000);
        m3215a();
    }

    private void m3208b(View view) {
        this.f3279g = null;
        findViewById(this.f3283k.id("umeng_socialize_share_image")).setVisibility(8);
    }

    public void onAtFriends(View view) {
        if (this.f3272F == null) {
            this.f3272F = m3214f();
        }
        if (!this.f3272F.isShowing()) {
            this.f3272F.show();
        }
    }

    public void onFollowStatChanged(View view) {
        this.f3280h = this.f3294v.isChecked();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == this.f3283k.id("umeng_socialize_share_previewImg_remove")) {
            m3208b(view);
        } else if (id == this.f3283k.id("umeng_socialize_title_bar_rightBt")) {
            m3202a(view);
        } else if (id == this.f3283k.id("umeng_socialize_title_bar_leftBt")) {
            onCancel(view);
        } else if (id == this.f3283k.id("umeng_socialize_share_at")) {
            onAtFriends(view);
        } else if (id == this.f3283k.id("umeng_socialize_location_ic")) {
            m3211c(view);
        } else if (id == this.f3283k.id("umeng_socialize_follow_check")) {
            onFollowStatChanged(view);
        }
    }

    private void m3201a(int i, Bitmap bitmap) {
        try {
            this.f3275a.setImageBitmap(bitmap);
        } catch (Exception e) {
            this.f3275a.setImageResource(i);
        }
        this.f3275a.setVisibility(0);
        this.f3290r.setVisibility(0);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0) {
            setResult(1000);
        }
        return super.onKeyDown(i, keyEvent);
    }

    protected void m3215a() {
        if (this.f3270D == -3) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().peekDecorView().getWindowToken(), 0);
            new Handler().postDelayed(new C0955c(this), 500);
            return;
        }
        finish();
    }

    private boolean m3212d() {
        int countContentLength = f3266d - SocializeUtils.countContentLength(this.f3286n.getText().toString());
        Log.m3248d(f3265c, "onTextChanged " + countContentLength + "   " + SocializeUtils.countContentLength(this.f3286n.getText().toString()));
        this.f3292t.setText("" + countContentLength);
        if (countContentLength >= 0) {
            return false;
        }
        return true;
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        if (this.f3268B != null) {
            this.f3268B.m4992a();
        }
        if (this.f3274H != null) {
            this.f3274H.cancel(true);
        }
        super.onDestroy();
    }

    private void m3211c(View view) {
        if (this.f3269C != null) {
            new Builder(this).setMessage("是否删除位置信息？").setCancelable(false).setPositiveButton("是", new C0957e(this)).setNegativeButton("否", new C0956d(this)).create().show();
        } else {
            m3213e();
        }
    }

    private void m3213e() {
        if (this.f3268B == null) {
            m3207b();
        }
        if (!(this.f3274H == null || this.f3274H.getStatus() == Status.FINISHED)) {
            this.f3274H.cancel(true);
        }
        this.f3274H = new C1810f(this, this.f3268B);
        this.f3274H.execute(new Void[0]);
    }

    private void m3203a(boolean z) {
        if (z) {
            this.f3288p.setVisibility(8);
            this.f3289q.setVisibility(0);
        } else if (this.f3269C == null) {
            this.f3288p.setImageResource(this.f3283k.drawable("umeng_socialize_location_off"));
            this.f3288p.setVisibility(0);
            this.f3289q.setVisibility(8);
        } else {
            this.f3288p.setImageResource(this.f3283k.drawable("umeng_socialize_location_on"));
            this.f3288p.setVisibility(0);
            this.f3289q.setVisibility(8);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!SocializeConstants.BACKKEY_COMPLETE_CLOSE || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        new Handler().postDelayed(new C0958g(this), 400);
        return true;
    }

    public void inputAt(SpannableString spannableString) {
        this.f3286n.getText().insert(this.f3286n.getSelectionStart(), spannableString);
    }

    private Dialog m3214f() {
        try {
            return (Dialog) Class.forName("com.umeng.socialize.view.ShareAtDialogV2").getConstructor(new Class[]{ShareActivity.class, SHARE_MEDIA.class, String.class}).newInstance(new Object[]{this, this.f3267A, Config.UID});
        } catch (Exception e) {
            Log.m3261w(f3265c, "如果需要使用‘@好友’功能，请添加相应的jar文件；否则忽略此信息", e);
            return null;
        }
    }
}
