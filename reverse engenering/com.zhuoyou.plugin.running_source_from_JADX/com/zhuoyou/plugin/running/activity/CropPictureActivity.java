package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.CropImageView.RotateDegrees;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.File;

public class CropPictureActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!CropPictureActivity.class.desiredAssertionStatus());
    public static final String KEY_IMG_HEIGHT = "img_height";
    public static final String KEY_IMG_WIDTH = "img_width";
    public static final String KEY_RESULT = "crop_result";
    private ProgressDialog dialog;
    private int imgHeight = 200;
    private int imgWidth = 200;
    private final CropCallback mCropCallback = new C17062();
    private CropImageView mCropView;
    private final LoadCallback mLoadCallback = new C17051();
    private final SaveCallback mSaveCallback = new C17073();

    class C17051 implements LoadCallback {
        C17051() {
        }

        public void onSuccess() {
        }

        public void onError() {
            Tools.hideProgressDialog(CropPictureActivity.this.dialog);
            Tools.makeToast("加载图片失败，请更换图片重试！");
            CropPictureActivity.this.finish();
        }
    }

    class C17062 implements CropCallback {
        C17062() {
        }

        public void onSuccess(Bitmap cropped) {
        }

        public void onError() {
            Tools.hideProgressDialog(CropPictureActivity.this.dialog);
            Tools.makeToast("裁剪图片失败，请更换图片重试！");
            CropPictureActivity.this.finish();
        }
    }

    class C17073 implements SaveCallback {
        C17073() {
        }

        public void onSuccess(Uri outputUri) {
            Tools.hideProgressDialog(CropPictureActivity.this.dialog);
            CropPictureActivity.this.setResult(-1, new Intent().putExtra(CropPictureActivity.KEY_RESULT, outputUri));
            CropPictureActivity.this.finish();
        }

        public void onError() {
            Tools.hideProgressDialog(CropPictureActivity.this.dialog);
            Tools.makeToast("裁剪失败，无法保存文件！");
            CropPictureActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_crop_picture);
        this.dialog = Tools.getProgressDialog(this);
        this.imgWidth = getIntent().getIntExtra(KEY_IMG_WIDTH, this.imgWidth);
        this.imgHeight = getIntent().getIntExtra(KEY_IMG_HEIGHT, this.imgHeight);
        setUpViews();
        this.mCropView.startLoad(getIntent().getData(), this.mLoadCallback);
    }

    private void setUpViews() {
        this.mCropView = (CropImageView) findViewById(C1680R.id.cropImageView);
        if ($assertionsDisabled || this.mCropView != null) {
            this.mCropView.setCustomRatio(this.imgWidth, this.imgHeight);
            this.mCropView.setOutputMaxSize(this.imgWidth, this.imgHeight);
            return;
        }
        throw new AssertionError();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.cancel_btn:
                finish();
                return;
            case C1680R.id.left_rotate_btn:
                this.mCropView.rotateImage(RotateDegrees.ROTATE_M90D);
                return;
            case C1680R.id.right_rotate_btn:
                this.mCropView.rotateImage(RotateDegrees.ROTATE_90D);
                return;
            case C1680R.id.ok_btn:
                Tools.showProgressDialog(this.dialog, "正在处理图片……");
                this.mCropView.startCrop(Uri.fromFile(new File(Tools.getTempPath() + System.currentTimeMillis())), this.mCropCallback, this.mSaveCallback);
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
