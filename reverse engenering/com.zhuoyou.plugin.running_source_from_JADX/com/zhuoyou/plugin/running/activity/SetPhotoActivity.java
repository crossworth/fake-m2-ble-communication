package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventUserFetch;
import com.zhuoyou.plugin.running.tools.FileUtils;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.ProgressWheel;
import com.zhuoyou.plugin.running.view.SetPhotoDialog;
import java.io.File;
import org.greenrobot.eventbus.EventBus;

public class SetPhotoActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!SetPhotoActivity.class.desiredAssertionStatus());
    private ImageView imgPhoto;
    private DroiFile oldPhoto;
    private DroiFile photo;
    private User user = ((User) DroiUser.getCurrentUser(User.class));
    private ProgressWheel wheel;

    class C18051 implements OnClickListener {
        C18051() {
        }

        public void onClick(View v) {
            new SetPhotoDialog(SetPhotoActivity.this).show((int) C1680R.string.userinfo_set_photo);
        }
    }

    class C18072 implements DroiCallback<Boolean> {

        class C18061 implements DroiCallback<Boolean> {
            C18061() {
            }

            public void result(Boolean aBoolean, DroiError droiError) {
                SetPhotoActivity.this.wheel.setVisibility(8);
                if (droiError.isOk()) {
                    Tools.makeToast((int) C1680R.string.userinfo_photo_save_success);
                    EventBus.getDefault().post(new EventUserFetch());
                    return;
                }
                Tools.makeToast(Tools.getDroiError(droiError));
                SetPhotoActivity.this.user.setHead(SetPhotoActivity.this.oldPhoto);
                Tools.displayFace(SetPhotoActivity.this.imgPhoto, SetPhotoActivity.this.user.getHead());
            }
        }

        C18072() {
        }

        public void result(Boolean aBoolean, DroiError droiError) {
            if (droiError.isOk()) {
                SetPhotoActivity.this.user.setHead(SetPhotoActivity.this.photo);
                SetPhotoActivity.this.user.saveInBackground(new C18061());
                return;
            }
            Tools.makeToast(Tools.getDroiError(droiError));
            SetPhotoActivity.this.wheel.setVisibility(8);
            SetPhotoActivity.this.user.setHead(SetPhotoActivity.this.oldPhoto);
            Tools.displayFace(SetPhotoActivity.this.imgPhoto, SetPhotoActivity.this.user.getHead());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this, true);
        setContentView((int) C1680R.layout.activity_set_photo);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        this.imgPhoto = (ImageView) findViewById(C1680R.id.img_photo);
        this.wheel = (ProgressWheel) findViewById(C1680R.id.progress_wheel);
        Tools.displayFace(this.imgPhoto, this.user.getHead());
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightTitleClickListener(new C18051());
            return;
        }
        throw new AssertionError();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 4097:
                    SetPhotoDialog.startPhotoZoom((Activity) this, data.getData(), 300, 300);
                    return;
                case 4098:
                    if (FileUtils.isSDAvailable()) {
                        SetPhotoDialog.startPhotoZoom((Activity) this, Uri.fromFile(new File(SetPhotoDialog.filePath)), 300, 300);
                        return;
                    } else {
                        Tools.makeToast((int) C1680R.string.string_sd_not_exist);
                        return;
                    }
                case 4099:
                    Tools.makeToast((int) C1680R.string.userinfo_photo_saving);
                    Uri uri = (Uri) data.getParcelableExtra(CropPictureActivity.KEY_RESULT);
                    this.oldPhoto = this.user.getHead();
                    this.photo = new DroiFile(new File(uri.getPath()));
                    Tools.displayImage(this.imgPhoto, uri);
                    savePhoto();
                    return;
                default:
                    return;
            }
        }
    }

    private void savePhoto() {
        this.wheel.setVisibility(0);
        this.photo.saveInBackground(new C18072());
    }
}
