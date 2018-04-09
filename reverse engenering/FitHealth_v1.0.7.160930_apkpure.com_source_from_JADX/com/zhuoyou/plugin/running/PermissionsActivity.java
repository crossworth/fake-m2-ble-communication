package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.fithealth.running.R;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;

public class PermissionsActivity extends AppCompatActivity {
    private static final String EXTRA_PERMISSIONS = "me.chunyu.clwang.permission.extra_permission";
    private static final String PACKAGE_URL_SCHEME = "package:";
    public static final int PERMISSIONS_DENIED = 1;
    public static final int PERMISSIONS_GRANTED = 0;
    private static final int PERMISSION_REQUEST_CODE = 0;
    private boolean isRequireCheck;
    private PermissionsChecker mChecker;

    class C13981 implements OnClickListener {
        C13981() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PermissionsActivity.this.startAppSettings();
            dialog.dismiss();
        }
    }

    class C13992 implements OnClickListener {
        C13992() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PermissionsActivity.this.setResult(1);
            PermissionsActivity.this.finish();
        }
    }

    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView((int) R.layout.activity_permissions);
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
        ActivityCompat.requestPermissions(this, permissions, 0);
    }

    private void allPermissionsGranted() {
        setResult(0);
        finish();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 && hasAllPermissionsGranted(grantResults)) {
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
        Builder builder = new Builder(this);
        builder.setTitle((int) R.string.help);
        builder.setMessage((int) R.string.string_help_text);
        builder.setPositiveButton((int) R.string.settings, new C13981());
        builder.setNegativeButton((int) R.string.quit, new C13992());
        builder.setCancelable(Boolean.valueOf(false));
        builder.create().show();
    }

    private void startAppSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
