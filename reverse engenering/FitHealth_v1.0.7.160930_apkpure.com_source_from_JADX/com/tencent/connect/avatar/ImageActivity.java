package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.open.yyb.TitleBar;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: ProGuard */
public class ImageActivity extends Activity {
    RelativeLayout f2389a;
    private QQToken f2390b;
    private String f2391c;
    private Handler f2392d;
    private C0697b f2393e;
    private Button f2394f;
    private Button f2395g;
    private C0702g f2396h;
    private TextView f2397i;
    private ProgressBar f2398j;
    private int f2399k = 0;
    private boolean f2400l = false;
    private long f2401m = 0;
    private int f2402n = 0;
    private int f2403o = 640;
    private int f2404p = 640;
    private Rect f2405q = new Rect();
    private String f2406r;
    private Bitmap f2407s;
    private OnClickListener f2408t = new C0700d(this);
    private OnClickListener f2409u = new C0701f(this);
    private IUiListener f2410v = new C1717e(this);
    private IUiListener f2411w = new C1716a(this);

    /* compiled from: ProGuard */
    class C06922 implements OnGlobalLayoutListener {
        final /* synthetic */ ImageActivity f2387a;

        C06922(ImageActivity imageActivity) {
            this.f2387a = imageActivity;
        }

        public void onGlobalLayout() {
            this.f2387a.f2389a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            this.f2387a.f2405q = this.f2387a.f2396h.m2375a();
            this.f2387a.f2393e.m2372a(this.f2387a.f2405q);
        }
    }

    /* compiled from: ProGuard */
    class C0693a extends View {
        final /* synthetic */ ImageActivity f2388a;

        public C0693a(ImageActivity imageActivity, Context context) {
            this.f2388a = imageActivity;
            super(context);
        }

        public void m2328a(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.f2388a.m2342b("com.tencent.plus.blue_normal.png");
            Drawable a2 = this.f2388a.m2342b("com.tencent.plus.blue_down.png");
            Drawable a3 = this.f2388a.m2342b("com.tencent.plus.blue_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }

        public void m2329b(Button button) {
            Drawable stateListDrawable = new StateListDrawable();
            Drawable a = this.f2388a.m2342b("com.tencent.plus.gray_normal.png");
            Drawable a2 = this.f2388a.m2342b("com.tencent.plus.gray_down.png");
            Drawable a3 = this.f2388a.m2342b("com.tencent.plus.gray_disable.png");
            stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, a2);
            stateListDrawable.addState(View.ENABLED_FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.ENABLED_STATE_SET, a);
            stateListDrawable.addState(View.FOCUSED_STATE_SET, a);
            stateListDrawable.addState(View.EMPTY_STATE_SET, a3);
            button.setBackgroundDrawable(stateListDrawable);
        }
    }

    /* compiled from: ProGuard */
    private class QQAvatarImp extends BaseApi {
        final /* synthetic */ ImageActivity f4536a;

        public QQAvatarImp(ImageActivity imageActivity, Context context, QQToken qQToken) {
            this.f4536a = imageActivity;
            super(context, qQToken);
        }

        public void setAvator(Bitmap bitmap, IUiListener iUiListener) {
            Bundle composeCGIParams = composeCGIParams();
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 40, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            bitmap.recycle();
            IRequestListener tempRequestListener = new TempRequestListener(iUiListener);
            composeCGIParams.putByteArray("picture", toByteArray);
            HttpUtils.requestAsync(this.mToken, this.mContext, "user/set_user_face", composeCGIParams, "POST", tempRequestListener);
        }
    }

    private Bitmap m2330a(String str) throws IOException {
        int i = 1;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        Uri parse = Uri.parse(str);
        InputStream openInputStream = getContentResolver().openInputStream(parse);
        if (openInputStream == null) {
            return null;
        }
        BitmapFactory.decodeStream(openInputStream, null, options);
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
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(parse), null, options);
    }

    private Drawable m2342b(String str) {
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

    private View m2333a() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        LayoutParams layoutParams3 = new LayoutParams(-2, -2);
        this.f2389a = new RelativeLayout(this);
        this.f2389a.setLayoutParams(layoutParams);
        this.f2389a.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        View relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(layoutParams3);
        this.f2389a.addView(relativeLayout);
        this.f2393e = new C0697b(this);
        this.f2393e.setLayoutParams(layoutParams2);
        this.f2393e.setScaleType(ScaleType.MATRIX);
        relativeLayout.addView(this.f2393e);
        this.f2396h = new C0702g(this);
        LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(layoutParams2);
        layoutParams4.addRule(14, -1);
        layoutParams4.addRule(15, -1);
        this.f2396h.setLayoutParams(layoutParams4);
        relativeLayout.addView(this.f2396h);
        relativeLayout = new LinearLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-2, C0698c.m2373a(this, 80.0f));
        layoutParams2.addRule(14, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setOrientation(0);
        relativeLayout.setGravity(17);
        this.f2389a.addView(relativeLayout);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(C0698c.m2373a(this, 24.0f), C0698c.m2373a(this, 24.0f)));
        imageView.setImageDrawable(m2342b("com.tencent.plus.logo.png"));
        relativeLayout.addView(imageView);
        this.f2397i = new TextView(this);
        layoutParams2 = new LinearLayout.LayoutParams(layoutParams3);
        layoutParams2.leftMargin = C0698c.m2373a(this, 7.0f);
        this.f2397i.setLayoutParams(layoutParams2);
        this.f2397i.setEllipsize(TruncateAt.END);
        this.f2397i.setSingleLine();
        this.f2397i.setTextColor(-1);
        this.f2397i.setTextSize(24.0f);
        this.f2397i.setVisibility(8);
        relativeLayout.addView(this.f2397i);
        relativeLayout = new RelativeLayout(this);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, C0698c.m2373a(this, BitmapDescriptorFactory.HUE_YELLOW));
        layoutParams2.addRule(12, -1);
        layoutParams2.addRule(9, -1);
        relativeLayout.setLayoutParams(layoutParams2);
        relativeLayout.setBackgroundDrawable(m2342b("com.tencent.plus.bar.png"));
        int a = C0698c.m2373a(this, TitleBar.SHAREBTN_RIGHT_MARGIN);
        relativeLayout.setPadding(a, a, a, 0);
        this.f2389a.addView(relativeLayout);
        C0693a c0693a = new C0693a(this, this);
        int a2 = C0698c.m2373a(this, 14.0f);
        int a3 = C0698c.m2373a(this, 7.0f);
        this.f2395g = new Button(this);
        this.f2395g.setLayoutParams(new RelativeLayout.LayoutParams(C0698c.m2373a(this, 78.0f), C0698c.m2373a(this, 45.0f)));
        this.f2395g.setText("取消");
        this.f2395g.setTextColor(-1);
        this.f2395g.setTextSize(18.0f);
        this.f2395g.setPadding(a2, a3, a2, a3);
        c0693a.m2329b(this.f2395g);
        relativeLayout.addView(this.f2395g);
        this.f2394f = new Button(this);
        LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(C0698c.m2373a(this, 78.0f), C0698c.m2373a(this, 45.0f));
        layoutParams5.addRule(11, -1);
        this.f2394f.setLayoutParams(layoutParams5);
        this.f2394f.setTextColor(-1);
        this.f2394f.setTextSize(18.0f);
        this.f2394f.setPadding(a2, a3, a2, a3);
        this.f2394f.setText("选取");
        c0693a.m2328a(this.f2394f);
        relativeLayout.addView(this.f2394f);
        imageView = new TextView(this);
        layoutParams4 = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams4.addRule(13, -1);
        imageView.setLayoutParams(layoutParams4);
        imageView.setText("移动和缩放");
        imageView.setPadding(0, C0698c.m2373a(this, 3.0f), 0, 0);
        imageView.setTextSize(18.0f);
        imageView.setTextColor(-1);
        relativeLayout.addView(imageView);
        this.f2398j = new ProgressBar(this);
        layoutParams = new RelativeLayout.LayoutParams(layoutParams3);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(15, -1);
        this.f2398j.setLayoutParams(layoutParams);
        this.f2398j.setVisibility(8);
        this.f2389a.addView(this.f2398j);
        return this.f2389a;
    }

    private void m2343b() {
        try {
            this.f2407s = m2330a(this.f2406r);
            if (this.f2407s == null) {
                throw new IOException("cannot read picture: '" + this.f2406r + "'!");
            }
            this.f2393e.setImageBitmap(this.f2407s);
            this.f2394f.setOnClickListener(this.f2408t);
            this.f2395g.setOnClickListener(this.f2409u);
            this.f2389a.getViewTreeObserver().addOnGlobalLayoutListener(new C06922(this));
        } catch (IOException e) {
            e.printStackTrace();
            String str = Constants.MSG_IMAGE_ERROR;
            m2346b(str, 1);
            m2335a(-5, null, str, e.getMessage());
            m2352d();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setRequestedOrientation(1);
        setContentView(m2333a());
        this.f2392d = new Handler();
        Bundle bundleExtra = getIntent().getBundleExtra(Constants.KEY_PARAMS);
        this.f2406r = bundleExtra.getString("picture");
        this.f2391c = bundleExtra.getString("return_activity");
        String string = bundleExtra.getString("appid");
        String string2 = bundleExtra.getString("access_token");
        long j = bundleExtra.getLong("expires_in");
        String string3 = bundleExtra.getString("openid");
        this.f2402n = bundleExtra.getInt("exitAnim");
        this.f2390b = new QQToken(string);
        this.f2390b.setAccessToken(string2, ((j - System.currentTimeMillis()) / 1000) + "");
        this.f2390b.setOpenId(string3);
        m2343b();
        m2354e();
        this.f2401m = System.currentTimeMillis();
        m2364a("10653", 0);
    }

    public void onBackPressed() {
        setResult(0);
        m2352d();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.f2393e.setImageBitmap(null);
        if (this.f2407s != null && !this.f2407s.isRecycled()) {
            this.f2407s.recycle();
        }
    }

    private void m2348c() {
        float width = (float) this.f2405q.width();
        Matrix imageMatrix = this.f2393e.getImageMatrix();
        float[] fArr = new float[9];
        imageMatrix.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        float f3 = fArr[0];
        width = ((float) this.f2403o) / width;
        int i = (int) ((((float) this.f2405q.left) - f) / f3);
        int i2 = (int) ((((float) this.f2405q.top) - f2) / f3);
        Matrix matrix = new Matrix();
        matrix.set(imageMatrix);
        matrix.postScale(width, width);
        int i3 = (int) (650.0f / f3);
        Bitmap createBitmap = Bitmap.createBitmap(this.f2407s, i, i2, Math.min(this.f2407s.getWidth() - i, i3), Math.min(this.f2407s.getHeight() - i2, i3), matrix, true);
        Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, this.f2403o, this.f2404p);
        createBitmap.recycle();
        m2336a(createBitmap2);
    }

    private void m2336a(Bitmap bitmap) {
        new QQAvatarImp(this, this, this.f2390b).setAvator(bitmap, this.f2410v);
    }

    private void m2339a(final String str, final int i) {
        this.f2392d.post(new Runnable(this) {
            final /* synthetic */ ImageActivity f2386c;

            public void run() {
                this.f2386c.m2346b(str, i);
            }
        });
    }

    private void m2346b(String str, int i) {
        Toast makeText = Toast.makeText(this, str, 1);
        LinearLayout linearLayout = (LinearLayout) makeText.getView();
        ((TextView) linearLayout.getChildAt(0)).setPadding(8, 0, 0, 0);
        View imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(C0698c.m2373a(this, 16.0f), C0698c.m2373a(this, 16.0f)));
        if (i == 0) {
            imageView.setImageDrawable(m2342b("com.tencent.plus.ic_success.png"));
        } else {
            imageView.setImageDrawable(m2342b("com.tencent.plus.ic_error.png"));
        }
        linearLayout.addView(imageView, 0);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        makeText.setView(linearLayout);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    private void m2335a(int i, String str, String str2, String str3) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_ERROR_CODE, i);
        intent.putExtra(Constants.KEY_ERROR_MSG, str2);
        intent.putExtra(Constants.KEY_ERROR_DETAIL, str3);
        intent.putExtra(Constants.KEY_RESPONSE, str);
        setResult(-1, intent);
    }

    private void m2352d() {
        finish();
        if (this.f2402n != 0) {
            overridePendingTransition(0, this.f2402n);
        }
    }

    private void m2354e() {
        this.f2399k++;
        new UserInfo(this, this.f2390b).getUserInfo(this.f2411w);
    }

    private void m2349c(String str) {
        CharSequence d = m2351d(str);
        if (!"".equals(d)) {
            this.f2397i.setText(d);
            this.f2397i.setVisibility(0);
        }
    }

    private String m2351d(String str) {
        return str.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&amp;", "&");
    }

    public void m2364a(String str, long j) {
        Util.reportBernoulli(this, str, j, this.f2390b.getAppId());
    }
}
