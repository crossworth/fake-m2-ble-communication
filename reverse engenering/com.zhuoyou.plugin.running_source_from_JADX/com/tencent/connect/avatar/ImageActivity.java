package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.p037b.C1322d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.Util;
import com.tencent.open.yyb.TitleBar;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class ImageActivity extends Activity {
    RelativeLayout f3603a;
    private QQToken f3604b;
    private String f3605c;
    private Handler f3606d;
    private C1171c f3607e;
    private Button f3608f;
    private Button f3609g;
    private C1168b f3610h;
    private TextView f3611i;
    private ProgressBar f3612j;
    private int f3613k = 0;
    private boolean f3614l = false;
    private long f3615m = 0;
    private int f3616n = 0;
    private final int f3617o = 640;
    private final int f3618p = 640;
    private Rect f3619q = new Rect();
    private String f3620r;
    private Bitmap f3621s;
    private final OnClickListener f3622t = new C11602(this);
    private final OnClickListener f3623u = new C11613(this);
    private final IUiListener f3624v = new C11635(this);
    private final IUiListener f3625w = new C11656(this);

    /* compiled from: ProGuard */
    class C11581 implements OnGlobalLayoutListener {
        final /* synthetic */ ImageActivity f3590a;

        C11581(ImageActivity imageActivity) {
            this.f3590a = imageActivity;
        }

        public void onGlobalLayout() {
            this.f3590a.f3603a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            this.f3590a.f3619q = this.f3590a.f3610h.m3443a();
            this.f3590a.f3607e.m3451a(this.f3590a.f3619q);
        }
    }

    /* compiled from: ProGuard */
    class C11602 implements OnClickListener {
        final /* synthetic */ ImageActivity f3592a;

        /* compiled from: ProGuard */
        class C11591 implements Runnable {
            final /* synthetic */ C11602 f3591a;

            C11591(C11602 c11602) {
                this.f3591a = c11602;
            }

            public void run() {
                this.f3591a.f3592a.m3420c();
            }
        }

        C11602(ImageActivity imageActivity) {
            this.f3592a = imageActivity;
        }

        public void onClick(View view) {
            this.f3592a.f3612j.setVisibility(0);
            this.f3592a.f3609g.setEnabled(false);
            this.f3592a.f3609g.setTextColor(Color.rgb(21, 21, 21));
            this.f3592a.f3608f.setEnabled(false);
            this.f3592a.f3608f.setTextColor(Color.rgb(36, 94, 134));
            new Thread(new C11591(this)).start();
            if (this.f3592a.f3614l) {
                this.f3592a.m3437a("10657", 0);
                return;
            }
            this.f3592a.m3437a("10655", System.currentTimeMillis() - this.f3592a.f3615m);
            if (this.f3592a.f3607e.f3632b) {
                this.f3592a.m3437a("10654", 0);
            }
        }
    }

    /* compiled from: ProGuard */
    class C11613 implements OnClickListener {
        final /* synthetic */ ImageActivity f3593a;

        C11613(ImageActivity imageActivity) {
            this.f3593a = imageActivity;
        }

        public void onClick(View view) {
            this.f3593a.m3437a("10656", System.currentTimeMillis() - this.f3593a.f3615m);
            this.f3593a.setResult(0);
            this.f3593a.m3424d();
        }
    }

    /* compiled from: ProGuard */
    class C11635 implements IUiListener {
        final /* synthetic */ ImageActivity f3597a;

        C11635(ImageActivity imageActivity) {
            this.f3597a = imageActivity;
        }

        public void onError(UiError uiError) {
            this.f3597a.f3609g.setEnabled(true);
            this.f3597a.f3609g.setTextColor(-1);
            this.f3597a.f3608f.setEnabled(true);
            this.f3597a.f3608f.setTextColor(-1);
            this.f3597a.f3608f.setText("重试");
            this.f3597a.f3612j.setVisibility(8);
            this.f3597a.f3614l = true;
            this.f3597a.m3411a(uiError.errorMessage, 1);
            this.f3597a.m3437a("10660", 0);
        }

        public void onComplete(Object obj) {
            int i;
            this.f3597a.f3609g.setEnabled(true);
            this.f3597a.f3609g.setTextColor(-1);
            this.f3597a.f3608f.setEnabled(true);
            this.f3597a.f3608f.setTextColor(-1);
            this.f3597a.f3612j.setVisibility(8);
            JSONObject jSONObject = (JSONObject) obj;
            try {
                i = jSONObject.getInt("ret");
            } catch (JSONException e) {
                e.printStackTrace();
                i = -1;
            }
            if (i == 0) {
                this.f3597a.m3411a("设置成功", 0);
                this.f3597a.m3437a("10658", 0);
                C1322d.m3896a().m3898a(this.f3597a.f3604b.getOpenId(), this.f3597a.f3604b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, "3", "0");
                Context context = this.f3597a;
                if (!(this.f3597a.f3605c == null || "".equals(this.f3597a.f3605c))) {
                    Intent intent = new Intent();
                    intent.setClassName(context, this.f3597a.f3605c);
                    if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                        context.startActivity(intent);
                    }
                }
                this.f3597a.m3407a(0, jSONObject.toString(), null, null);
                this.f3597a.m3424d();
                return;
            }
            this.f3597a.m3411a("设置出错了，请重新登录再尝试下呢：）", 1);
            C1322d.m3896a().m3898a(this.f3597a.f3604b.getOpenId(), this.f3597a.f3604b.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, Constants.VIA_ACT_TYPE_NINETEEN, "1");
        }

        public void onCancel() {
        }
    }

    /* compiled from: ProGuard */
    class C11656 implements IUiListener {
        final /* synthetic */ ImageActivity f3600a;

        C11656(ImageActivity imageActivity) {
            this.f3600a = imageActivity;
        }

        public void onError(UiError uiError) {
            m3399a(0);
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            int i = -1;
            try {
                i = jSONObject.getInt("ret");
                if (i == 0) {
                    final String string = jSONObject.getString("nickname");
                    this.f3600a.f3606d.post(new Runnable(this) {
                        final /* synthetic */ C11656 f3599b;

                        public void run() {
                            this.f3599b.f3600a.m3421c(string);
                        }
                    });
                    this.f3600a.m3437a("10659", 0);
                } else {
                    this.f3600a.m3437a("10661", 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (i != 0) {
                m3399a(i);
            }
        }

        public void onCancel() {
        }

        private void m3399a(int i) {
            if (this.f3600a.f3613k < 2) {
                this.f3600a.m3426e();
            }
        }
    }

    /* compiled from: ProGuard */
    private class QQAvatarImp extends BaseApi {
        final /* synthetic */ ImageActivity f3601a;

        public QQAvatarImp(ImageActivity imageActivity, QQToken qQToken) {
            this.f3601a = imageActivity;
            super(qQToken);
        }

        public void setAvator(Bitmap bitmap, IUiListener iUiListener) {
            Bundle composeCGIParams = composeCGIParams();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            bitmap.recycle();
            IRequestListener tempRequestListener = new TempRequestListener(iUiListener);
            composeCGIParams.putByteArray("picture", toByteArray);
            HttpUtils.requestAsync(this.mToken, Global.getContext(), "user/set_user_face", composeCGIParams, Constants.HTTP_POST, tempRequestListener);
            C1322d.m3896a().m3898a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR_SUCCEED, Constants.VIA_REPORT_TYPE_SET_AVATAR, Constants.VIA_ACT_TYPE_NINETEEN, "0");
        }
    }

    /* compiled from: ProGuard */
    class C1166a extends View {
        final /* synthetic */ ImageActivity f3602a;

        public C1166a(ImageActivity imageActivity, Context context) {
            this.f3602a = imageActivity;
            super(context);
        }

        public void m3400a(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.f3602a.m3414b("com.tencent.plus.blue_normal.png");
            Drawable a2 = this.f3602a.m3414b("com.tencent.plus.blue_down.png");
            Drawable a3 = this.f3602a.m3414b("com.tencent.plus.blue_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }

        public void m3401b(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.f3602a.m3414b("com.tencent.plus.gray_normal.png");
            Drawable a2 = this.f3602a.m3414b("com.tencent.plus.gray_down.png");
            Drawable a3 = this.f3602a.m3414b("com.tencent.plus.gray_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }
    }

    private Bitmap m3402a(String str) throws IOException {
        int i = 1;
        Bitmap bitmap = null;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Uri parse = Uri.parse(str);
        InputStream openInputStream = getContentResolver().openInputStream(parse);
        if (openInputStream != null) {
            try {
                BitmapFactory.decodeStream(openInputStream, null, options);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
            openInputStream.close();
            int i2 = options.outWidth;
            int i3 = options.outHeight;
            while (i2 * i3 > 4194304) {
                i2 /= 2;
                i3 /= 2;
                i *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = i;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(parse), null, options);
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }

    private Drawable m3414b(String str) {
        Drawable createFromStream;
        IOException e;
        try {
            InputStream open = getAssets().open(str);
            createFromStream = Drawable.createFromStream(open, str);
            try {
                open.close();
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return createFromStream;
            }
        } catch (IOException e3) {
            IOException iOException = e3;
            createFromStream = null;
            e = iOException;
            e.printStackTrace();
            return createFromStream;
        }
        return createFromStream;
    }

    private View m3405a() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        this.f3603a = new RelativeLayout(this);
        this.f3603a.setLayoutParams(layoutParams);
        this.f3603a.setBackgroundColor(-16777216);
        View relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(layoutParams3);
        this.f3603a.addView(relativeLayout);
        this.f3607e = new C1171c(this);
        this.f3607e.setLayoutParams(layoutParams2);
        this.f3607e.setScaleType(ScaleType.MATRIX);
        relativeLayout.addView(this.f3607e);
        this.f3610h = new C1168b(this);
        LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(layoutParams2);
        layoutParams4.addRule(14, -1);
        layoutParams4.addRule(15, -1);
        this.f3610h.setLayoutParams(layoutParams4);
        relativeLayout.addView(this.f3610h);
        relativeLayout = new LinearLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-2, C1167a.m3441a(this, 80.0f));
        layoutParams2.addRule(14, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setOrientation(0);
        relativeLayout.setGravity(17);
        this.f3603a.addView(relativeLayout);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(C1167a.m3441a(this, 24.0f), C1167a.m3441a(this, 24.0f)));
        imageView.setImageDrawable(m3414b("com.tencent.plus.logo.png"));
        relativeLayout.addView(imageView);
        this.f3611i = new TextView(this);
        layoutParams2 = new LinearLayout.LayoutParams(layoutParams3);
        layoutParams2.leftMargin = C1167a.m3441a(this, 7.0f);
        this.f3611i.setLayoutParams(layoutParams2);
        this.f3611i.setEllipsize(TruncateAt.END);
        this.f3611i.setSingleLine();
        this.f3611i.setTextColor(-1);
        this.f3611i.setTextSize(24.0f);
        this.f3611i.setVisibility(8);
        relativeLayout.addView(this.f3611i);
        relativeLayout = new RelativeLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, C1167a.m3441a(this, 60.0f));
        layoutParams2.addRule(12, -1);
        layoutParams2.addRule(9, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setBackgroundDrawable(m3414b("com.tencent.plus.bar.png"));
        int a = C1167a.m3441a(this, TitleBar.SHAREBTN_RIGHT_MARGIN);
        relativeLayout.setPadding(a, a, a, 0);
        this.f3603a.addView(relativeLayout);
        C1166a c1166a = new C1166a(this, this);
        int a2 = C1167a.m3441a(this, 14.0f);
        int a3 = C1167a.m3441a(this, 7.0f);
        this.f3609g = new Button(this);
        this.f3609g.setLayoutParams(new RelativeLayout.LayoutParams(C1167a.m3441a(this, 78.0f), C1167a.m3441a(this, 45.0f)));
        this.f3609g.setText("取消");
        this.f3609g.setTextColor(-1);
        this.f3609g.setTextSize(18.0f);
        this.f3609g.setPadding(a2, a3, a2, a3);
        c1166a.m3401b(this.f3609g);
        relativeLayout.addView(this.f3609g);
        this.f3608f = new Button(this);
        LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(C1167a.m3441a(this, 78.0f), C1167a.m3441a(this, 45.0f));
        layoutParams5.addRule(11, -1);
        this.f3608f.setLayoutParams(layoutParams5);
        this.f3608f.setTextColor(-1);
        this.f3608f.setTextSize(18.0f);
        this.f3608f.setPadding(a2, a3, a2, a3);
        this.f3608f.setText("选取");
        c1166a.m3400a(this.f3608f);
        relativeLayout.addView(this.f3608f);
        imageView = new TextView(this);
        layoutParams4 = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams4.addRule(13, -1);
        imageView.setLayoutParams(layoutParams4);
        imageView.setText("移动和缩放");
        imageView.setPadding(0, C1167a.m3441a(this, 3.0f), 0, 0);
        imageView.setTextSize(18.0f);
        imageView.setTextColor(-1);
        relativeLayout.addView(imageView);
        this.f3612j = new ProgressBar(this);
        layoutParams = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(15, -1);
        this.f3612j.setLayoutParams(layoutParams);
        this.f3612j.setVisibility(8);
        this.f3603a.addView(this.f3612j);
        return this.f3603a;
    }

    private void m3415b() {
        try {
            this.f3621s = m3402a(this.f3620r);
            if (this.f3621s == null) {
                throw new IOException("cannot read picture: '" + this.f3620r + "'!");
            }
            this.f3607e.setImageBitmap(this.f3621s);
            this.f3608f.setOnClickListener(this.f3622t);
            this.f3609g.setOnClickListener(this.f3623u);
            this.f3603a.getViewTreeObserver().addOnGlobalLayoutListener(new C11581(this));
        } catch (IOException e) {
            e.printStackTrace();
            String str = Constants.MSG_IMAGE_ERROR;
            m3411a(str, 1);
            m3407a(-5, null, str, e.getMessage());
            m3424d();
        }
    }

    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        setRequestedOrientation(1);
        setContentView(m3405a());
        this.f3606d = new Handler();
        Bundle bundleExtra = getIntent().getBundleExtra(Constants.KEY_PARAMS);
        this.f3620r = bundleExtra.getString("picture");
        this.f3605c = bundleExtra.getString("return_activity");
        String string = bundleExtra.getString("appid");
        String string2 = bundleExtra.getString("access_token");
        long j = bundleExtra.getLong("expires_in");
        String string3 = bundleExtra.getString("openid");
        this.f3616n = bundleExtra.getInt("exitAnim");
        this.f3604b = new QQToken(string);
        this.f3604b.setAccessToken(string2, ((j - System.currentTimeMillis()) / 1000) + "");
        this.f3604b.setOpenId(string3);
        m3415b();
        m3426e();
        this.f3615m = System.currentTimeMillis();
        m3437a("10653", 0);
    }

    public void onBackPressed() {
        setResult(0);
        m3424d();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.f3607e.setImageBitmap(null);
        if (this.f3621s != null && !this.f3621s.isRecycled()) {
            this.f3621s.recycle();
        }
    }

    private void m3420c() {
        float width = (float) this.f3619q.width();
        Matrix imageMatrix = this.f3607e.getImageMatrix();
        float[] fArr = new float[9];
        imageMatrix.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        float f3 = fArr[0];
        float f4 = 640.0f / width;
        int i = (int) ((((float) this.f3619q.left) - f) / f3);
        if (i < 0) {
            i = 0;
        }
        int i2 = (int) ((((float) this.f3619q.top) - f2) / f3);
        if (i2 < 0) {
            i2 = 0;
        }
        Matrix matrix = new Matrix();
        matrix.set(imageMatrix);
        matrix.postScale(f4, f4);
        int i3 = (int) (650.0f / f3);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(this.f3621s, i, i2, Math.min(this.f3621s.getWidth() - i, i3), Math.min(this.f3621s.getHeight() - i2, i3), matrix, true);
            Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, 640, 640);
            createBitmap.recycle();
            m3408a(createBitmap2);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String str = Constants.MSG_IMAGE_ERROR;
            m3411a(str, 1);
            m3407a(-5, null, str, e.getMessage());
            m3424d();
        }
    }

    private void m3408a(Bitmap bitmap) {
        new QQAvatarImp(this, this.f3604b).setAvator(bitmap, this.f3624v);
    }

    private void m3411a(final String str, final int i) {
        this.f3606d.post(new Runnable(this) {
            final /* synthetic */ ImageActivity f3596c;

            public void run() {
                this.f3596c.m3418b(str, i);
            }
        });
    }

    private void m3418b(String str, int i) {
        Toast makeText = Toast.makeText(this, str, 1);
        LinearLayout linearLayout = (LinearLayout) makeText.getView();
        ((TextView) linearLayout.getChildAt(0)).setPadding(8, 0, 0, 0);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(C1167a.m3441a(this, 16.0f), C1167a.m3441a(this, 16.0f)));
        if (i == 0) {
            imageView.setImageDrawable(m3414b("com.tencent.plus.ic_success.png"));
        } else {
            imageView.setImageDrawable(m3414b("com.tencent.plus.ic_error.png"));
        }
        linearLayout.addView(imageView, 0);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        makeText.setView(linearLayout);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    private void m3407a(int i, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_ERROR_CODE, i);
        intent.putExtra(Constants.KEY_ERROR_MSG, str2);
        intent.putExtra(Constants.KEY_ERROR_DETAIL, str3);
        intent.putExtra(Constants.KEY_RESPONSE, str);
        setResult(-1, intent);
    }

    private void m3424d() {
        finish();
        if (this.f3616n != 0) {
            overridePendingTransition(0, this.f3616n);
        }
    }

    private void m3426e() {
        this.f3613k++;
        new UserInfo(this, this.f3604b).getUserInfo(this.f3625w);
    }

    private void m3421c(String str) {
        CharSequence d = m3423d(str);
        if (!"".equals(d)) {
            this.f3611i.setText(d);
            this.f3611i.setVisibility(0);
        }
    }

    private String m3423d(String str) {
        return str.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&amp;", "&");
    }

    public void m3437a(String str, long j) {
        Util.reportBernoulli(this, str, j, this.f3604b.getAppId());
    }
}
