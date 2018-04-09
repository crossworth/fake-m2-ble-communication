package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.PermissionsChecker;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.MyAlertDialog.OnClickListener;

public class PermissionActivity extends AppCompatActivity {
    private static final String EXTRA_PERMISSIONS = "com.droi.permission.extra_permission";
    private static final String PACKAGE_URL_SCHEME = "package:";
    public static final int PERMISSIONS_DENIED = 0;
    public static final int PERMISSIONS_GRANTED = -1;
    private static final int PERMISSION_REQUEST_CODE = 4098;
    private boolean isRequireCheck;
    private PermissionsChecker mChecker;

    class C17911 implements OnClickListener {
        C17911() {
        }

        public void onClick(int witch) {
            PermissionActivity.this.setResult(0);
            PermissionActivity.this.finish();
        }
    }

    class C17922 implements OnClickListener {
        C17922() {
        }

        public void onClick(int witch) {
            PermissionActivity.this.startAppSettings();
        }
    }

    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView((int) C1680R.layout.activity_permissions);
        this.mChecker = new PermissionsChecker(this);
        this.isRequireCheck = true;
    }

    protected void onResume() {
        super.onResume();
        if (this.isRequireCheck) {
            String[] permissions = getPermissions();
            if (this.mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions);
                return;
            } else {
                allPermissionsGranted();
                return;
            }
        }
        this.isRequireCheck = true;
    }

    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, 4098);
    }

    private void allPermissionsGranted() {
        setResult(-1);
        finish();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 4098 && hasAllPermissionsGranted(grantResults)) {
            this.isRequireCheck = true;
            allPermissionsGranted();
            return;
        }
        this.isRequireCheck = false;
        showMissingPermissionDialog();
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == -1) {
                return false;
            }
        }
        return true;
    }

    private void showMissingPermissionDialog() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this);
        myAlertDialog.setTitle((int) C1680R.string.help);
        myAlertDialog.setMessage((int) C1680R.string.string_help_text);
        myAlertDialog.setLeftButton((int) C1680R.string.quit, new C17911());
        myAlertDialog.setRightButton((int) C1680R.string.settings, new C17922());
        myAlertDialog.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
