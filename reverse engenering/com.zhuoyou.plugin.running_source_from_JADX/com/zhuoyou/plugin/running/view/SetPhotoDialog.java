package com.zhuoyou.plugin.running.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.CropPictureActivity;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.File;

public class SetPhotoDialog extends BaseDialog {
    public static final int CODE_CAMERA_REQUEST = 4098;
    public static final int CODE_CROP_REQUEST = 4099;
    public static final int CODE_GALLERY_REQUEST = 4097;
    public static String filePath = (Tools.getTempPath() + "photo_temp.jpg");
    private Activity mActivity;
    private Fragment mFragment;
    private OnClickListener onClickListener = new C19541();

    class C19541 implements OnClickListener {
        C19541() {
        }

        public void onClick(View v) {
            SetPhotoDialog.this.dialog.dismiss();
            switch (v.getId()) {
                case C1680R.id.btn_take_photo:
                    SetPhotoDialog.this.takePhoto();
                    return;
                case C1680R.id.btn_choose_photo:
                    SetPhotoDialog.this.choosePhoto();
                    return;
                default:
                    return;
            }
        }
    }

    public SetPhotoDialog(Activity activity) {
        this.mActivity = activity;
        initDialog(activity);
        initView();
    }

    public SetPhotoDialog(Fragment fragment) {
        this.mFragment = fragment;
        initDialog(fragment.getContext());
        initView();
    }

    private void initDialog(Context context) {
        this.dialog = new Dialog(context, C1680R.style.DefaultDialog);
        this.dialog.setContentView(C1680R.layout.layout_setphoto_dialog);
        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(C1680R.style.BottomDialogShowAnim);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        window.setAttributes(lp);
    }

    private void initView() {
        this.dialog.findViewById(C1680R.id.btn_take_photo).setOnClickListener(this.onClickListener);
        this.dialog.findViewById(C1680R.id.btn_choose_photo).setOnClickListener(this.onClickListener);
        this.dialog.findViewById(C1680R.id.btn_cancel).setOnClickListener(this.onClickListener);
        this.title = (TextView) this.dialog.findViewById(C1680R.id.tv_title);
    }

    private void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(new File(filePath)));
        if (this.mFragment != null) {
            this.mFragment.startActivityForResult(intent, 4098);
        } else if (this.mActivity != null) {
            this.mActivity.startActivityForResult(intent, 4098);
        }
    }

    private void choosePhoto() {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        if (this.mFragment != null) {
            this.mFragment.startActivityForResult(intent, 4097);
        } else if (this.mActivity != null) {
            this.mActivity.startActivityForResult(intent, 4097);
        }
    }

    public static void startPhotoZoom(Activity activity, Uri uri, int x, int y) {
        Intent intent = new Intent(activity, CropPictureActivity.class);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra(CropPictureActivity.KEY_IMG_WIDTH, x);
        intent.putExtra(CropPictureActivity.KEY_IMG_HEIGHT, y);
        activity.startActivityForResult(intent, 4099);
    }

    public static void startPhotoZoom(Fragment fragment, Uri uri, int x, int y) {
        Intent intent = new Intent(fragment.getContext(), CropPictureActivity.class);
        intent.setDataAndType(uri, "image/jpeg");
        intent.putExtra(CropPictureActivity.KEY_IMG_WIDTH, x);
        intent.putExtra(CropPictureActivity.KEY_IMG_HEIGHT, y);
        fragment.startActivityForResult(intent, 4099);
    }

    public void show(String title) {
        setTitle(title);
        show();
    }

    public void show(int title) {
        setTitle(title);
        show();
    }
}
