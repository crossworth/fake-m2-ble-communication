package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.api.maps.model.WeightedLatLng;
import com.fithealth.running.R;
import com.zhuoyou.plugin.album.BitmapUtils;
import com.zhuoyou.plugin.running.Tools;
import java.io.ByteArrayOutputStream;

public class EditPictureActivity extends Activity implements OnClickListener, OnTouchListener {
    private static final int DRAG = 1;
    static final float MAX_SCALE = 10.0f;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private int angleInt = 0;
    private Bitmap bitmap;
    private int bottomBarHeight = 0;
    private LinearLayout cancel_btn;
    private ClipView clipview;
    DisplayMetrics dm;
    private int height;
    private LinearLayout left_rotate_btn;
    Matrix matrix = new Matrix();
    PointF mid = new PointF();
    float minScaleR;
    private int mode = 0;
    private int f3490n = 0;
    private LinearLayout ok_btn;
    double oldDist = WeightedLatLng.DEFAULT_INTENSITY;
    PointF prev = new PointF();
    private Rect rectIV;
    private LinearLayout right_rotate_btn;
    Matrix savedMatrix = new Matrix();
    private ImageView screenshot_imageView;
    private int statusBarHeight = 0;
    private int titleBarHeight = 0;
    private int width;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_picture);
        setUpViews();
        setUpListeners();
        Uri uri = getIntent().getData();
        if (uri != null) {
            this.bitmap = getBitmapFromUri(uri);
            if (this.bitmap != null) {
                this.screenshot_imageView.setImageBitmap(this.bitmap);
            }
        }
        if (this.screenshot_imageView.getDrawable() == null) {
            Tools.makeToast(getString(R.string.choose_correct_picture));
            return;
        }
        this.rectIV = this.screenshot_imageView.getDrawable().getBounds();
        this.bottomBarHeight = Tools.dip2px(this, 115.0f);
        getStatusBarHeight();
        minZoom();
        center();
        this.screenshot_imageView.setImageMatrix(this.matrix);
    }

    private void setUpViews() {
        this.screenshot_imageView = (ImageView) findViewById(R.id.imageView);
        this.cancel_btn = (LinearLayout) findViewById(R.id.cancel_btn);
        this.ok_btn = (LinearLayout) findViewById(R.id.ok_btn);
        this.left_rotate_btn = (LinearLayout) findViewById(R.id.left_rotate_btn);
        this.right_rotate_btn = (LinearLayout) findViewById(R.id.right_rotate_btn);
        this.clipview = (ClipView) findViewById(R.id.clipview);
        this.dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.dm);
        this.width = this.dm.widthPixels;
        this.height = this.dm.heightPixels;
        this.screenshot_imageView.setImageMatrix(this.matrix);
    }

    private void setUpListeners() {
        this.cancel_btn.setOnClickListener(this);
        this.ok_btn.setOnClickListener(this);
        this.left_rotate_btn.setOnClickListener(this);
        this.right_rotate_btn.setOnClickListener(this);
        this.screenshot_imageView.setOnTouchListener(this);
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
            int picWidth = options.outWidth;
            int picHeight = options.outHeight;
            Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();
            int screenHeight = display.getHeight();
            options.inSampleSize = 1;
            if (picWidth > picHeight) {
                if (picWidth > screenWidth) {
                    options.inSampleSize = picWidth / screenWidth;
                }
            } else if (picHeight > screenHeight) {
                options.inSampleSize = picHeight / screenHeight;
            }
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            this.statusBarHeight = getResources().getDimensionPixelSize(Integer.parseInt(c.getField("status_bar_height").get(c.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void minZoom() {
        this.minScaleR = Math.min((((float) this.dm.widthPixels) / ((float) this.bitmap.getWidth())) / 2.0f, (((float) this.dm.widthPixels) / ((float) this.bitmap.getHeight())) / 2.0f);
        if (((double) this.minScaleR) < 0.5d) {
            float scale = Math.max(((float) this.dm.widthPixels) / ((float) this.bitmap.getWidth()), ((float) this.dm.widthPixels) / ((float) this.bitmap.getHeight()));
            this.matrix.postScale(scale, scale);
            return;
        }
        this.minScaleR = 1.0f;
    }

    private void center() {
        center(true, true);
    }

    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(this.matrix);
        RectF rect = new RectF(0.0f, 0.0f, (float) this.bitmap.getWidth(), (float) this.bitmap.getHeight());
        m.mapRect(rect);
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        if (vertical) {
            int screenHeight = this.dm.heightPixels;
            if (height < ((float) screenHeight)) {
                deltaY = ((((((float) screenHeight) - height) - ((float) this.statusBarHeight)) - ((float) this.bottomBarHeight)) / 2.0f) - rect.top;
            } else if (rect.top > 0.0f) {
                deltaY = -rect.top;
            } else if (rect.bottom < ((float) screenHeight)) {
                deltaY = ((float) this.screenshot_imageView.getHeight()) - rect.bottom;
            }
        }
        if (horizontal) {
            int screenWidth = this.dm.widthPixels;
            if (width < ((float) screenWidth)) {
                deltaX = ((((float) screenWidth) - width) / 2.0f) - rect.left;
            } else if (rect.left > 0.0f) {
                deltaX = -rect.left;
            } else if (rect.right > ((float) screenWidth)) {
                deltaX = ((((float) screenWidth) - width) / 2.0f) - rect.left;
            }
        }
        this.matrix.postTranslate(deltaX, deltaY);
        if (this.f3490n % 4 != 0) {
            this.matrix.postRotate((float) ((this.f3490n % 4) * -90), (float) (this.screenshot_imageView.getWidth() / 2), (float) (this.screenshot_imageView.getHeight() / 2));
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & 255) {
            case 0:
                this.savedMatrix.set(this.matrix);
                this.prev.set(event.getX(), event.getY());
                if (!isOnCP(event.getX(), event.getY())) {
                    this.mode = 0;
                    break;
                }
                this.mode = 1;
                break;
            case 1:
            case 6:
                this.mode = 0;
                break;
            case 2:
                if (this.mode != 1) {
                    if (this.mode == 2) {
                        double newDist = spacing(event);
                        if (newDist > 10.0d) {
                            this.matrix.set(this.savedMatrix);
                            double scale = newDist / this.oldDist;
                            this.matrix.postScale((float) scale, (float) scale, this.mid.x, this.mid.y);
                            break;
                        }
                    }
                }
                this.matrix.set(this.savedMatrix);
                this.matrix.postTranslate(event.getX() - this.prev.x, event.getY() - this.prev.y);
                break;
                break;
            case 5:
                this.oldDist = spacing(event);
                boolean isonpic = isOnCP(event.getX(), event.getY());
                if (this.oldDist > 10.0d && isonpic) {
                    this.savedMatrix.set(this.matrix);
                    midPoint(this.mid, event);
                    this.mode = 2;
                    break;
                }
        }
        view.setImageMatrix(this.matrix);
        CheckView();
        return true;
    }

    private boolean isOnCP(float evx, float evy) {
        float[] p = new float[9];
        this.matrix.getValues(p);
        float scale = Math.max(Math.abs(p[0]), Math.abs(p[1]));
        RectF rectf = null;
        switch (this.f3490n) {
            case 0:
                rectf = new RectF(p[2], p[5], p[2] + (((float) this.rectIV.width()) * scale), p[5] + (((float) this.rectIV.height()) * scale));
                break;
            case 1:
                rectf = new RectF(p[2], p[5] - (((float) this.rectIV.width()) * scale), p[2] + (((float) this.rectIV.height()) * scale), p[5]);
                break;
            case 2:
                rectf = new RectF(p[2] - (((float) this.rectIV.width()) * scale), p[5] - (((float) this.rectIV.height()) * scale), p[2], p[5]);
                break;
            case 3:
                rectf = new RectF(p[2] - (((float) this.rectIV.height()) * scale), p[5], p[2], p[5] + (((float) this.rectIV.width()) * scale));
                break;
        }
        if (rectf == null || !rectf.contains(evx, evy)) {
            return false;
        }
        return true;
    }

    private double spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void midPoint(PointF point, MotionEvent event) {
        point.set((event.getX(0) + event.getX(1)) / 2.0f, (event.getY(0) + event.getY(1)) / 2.0f);
    }

    private void CheckView() {
        float[] p = new float[9];
        this.matrix.getValues(p);
        float scale = Math.max(Math.abs(p[0]), Math.abs(p[1]));
        if (this.mode == 2) {
            if (scale < this.minScaleR) {
                this.matrix.setScale(this.minScaleR, this.minScaleR);
                center();
            }
            if (scale > 10.0f) {
                this.matrix.set(this.savedMatrix);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                return;
            case R.id.left_rotate_btn:
                int i = this.angleInt + 1;
                this.angleInt = i;
                this.f3490n = i % 4;
                this.matrix.postRotate(-90.0f, (float) (this.screenshot_imageView.getWidth() / 2), (float) (this.screenshot_imageView.getHeight() / 2));
                this.savedMatrix.postRotate(-90.0f);
                this.screenshot_imageView.setImageMatrix(this.matrix);
                return;
            case R.id.right_rotate_btn:
                this.f3490n = (this.angleInt + 3) % 4;
                this.matrix.postRotate(90.0f, (float) (this.screenshot_imageView.getWidth() / 2), (float) (this.screenshot_imageView.getHeight() / 2));
                this.savedMatrix.postRotate(90.0f);
                this.screenshot_imageView.setImageMatrix(this.matrix);
                return;
            case R.id.ok_btn:
                Intent intent = new Intent();
                Bitmap fianBitmap = getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                fianBitmap.compress(CompressFormat.PNG, 100, baos);
                intent.putExtra("bitmap", baos.toByteArray());
                setResult(-1, intent);
                finish();
                return;
            default:
                return;
        }
    }

    private Bitmap getBitmap() {
        getBarHeight();
        Bitmap screenShoot = takeScreenShot();
        int SX = this.width;
        return BitmapUtils.getCroppedRoundBitmap(Bitmap.createBitmap(screenShoot, (this.width - SX) / 2, ((((this.height - SX) + this.statusBarHeight) + this.titleBarHeight) - this.bottomBarHeight) / 2, SX, SX), 102);
    }

    private void getBarHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        this.statusBarHeight = frame.top;
        this.titleBarHeight = getWindow().findViewById(16908290).getTop() - this.statusBarHeight;
    }

    private Bitmap takeScreenShot() {
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bitmap != null) {
            this.bitmap.recycle();
            this.bitmap = null;
            System.gc();
        }
    }
}
