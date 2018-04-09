package com.tencent.connect.dataprovider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import com.tencent.connect.dataprovider.DataType.TextAndMediaPath;
import com.tencent.connect.dataprovider.DataType.TextOnly;
import java.io.File;
import java.lang.ref.WeakReference;

/* compiled from: ProGuard */
public final class CallbackManager {
    private WeakReference<Context> f3647a;
    private Uri f3648b;
    private String f3649c;
    private String f3650d;
    private String f3651e;
    private String f3652f;
    private boolean f3653g = false;
    private int f3654h;

    public CallbackManager(Activity activity) {
        this.f3647a = new WeakReference(activity.getApplicationContext());
        Intent intent = activity.getIntent();
        if (intent != null) {
            this.f3648b = intent.getData();
            this.f3649c = intent.getStringExtra(Constants.SRC_PACKAGE_NAME);
            this.f3650d = intent.getStringExtra(Constants.SRC_ACTIVITY_CLASS_NAME);
            this.f3651e = intent.getStringExtra(Constants.SRC_ACTIVITY_ACTION);
            this.f3654h = intent.getIntExtra(Constants.REQUEST_TYPE, 0);
            this.f3652f = intent.getStringExtra(Constants.APPID);
            if (this.f3648b != null && this.f3650d != null) {
                this.f3653g = true;
            }
        }
    }

    public boolean isCallFromTencentApp() {
        return this.f3653g;
    }

    private int m3452a(Bundle bundle) {
        if (!this.f3653g) {
            return -2;
        }
        if (this.f3647a == null) {
            return -3;
        }
        Intent intent = new Intent();
        intent.setClassName(this.f3649c, this.f3650d);
        intent.setAction(this.f3651e);
        bundle.putString(Constants.APPID, this.f3652f);
        intent.putExtras(bundle);
        intent.setFlags(268435456);
        ((Context) this.f3647a.get()).startActivity(intent);
        return 0;
    }

    public int getRequestDateTypeFlag() {
        return this.f3654h;
    }

    public boolean isSupportType(int i) {
        return (getRequestDateTypeFlag() & i) != 0;
    }

    public int sendTextAndImagePath(String str, String str2) {
        if (!isSupportType(1)) {
            return -1;
        }
        int a = m3453a(str2);
        if (a != 0) {
            return a;
        }
        Parcelable textAndMediaPath = new TextAndMediaPath(str, str2);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 1);
        bundle.putParcelable(Constants.CONTENT_DATA, textAndMediaPath);
        return m3452a(bundle);
    }

    public int sendTextAndVideoPath(String str, String str2) {
        if (!isSupportType(2)) {
            return -1;
        }
        int a = m3453a(str2);
        if (a != 0) {
            return a;
        }
        Parcelable textAndMediaPath = new TextAndMediaPath(str, str2);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 2);
        bundle.putParcelable(Constants.CONTENT_DATA, textAndMediaPath);
        return m3452a(bundle);
    }

    public int sendTextOnly(String str) {
        if (!isSupportType(4)) {
            return -1;
        }
        Parcelable textOnly = new TextOnly(str);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 4);
        bundle.putParcelable(Constants.CONTENT_DATA, textOnly);
        return m3452a(bundle);
    }

    private int m3453a(String str) {
        if (str == null) {
            return -7;
        }
        String toLowerCase = str.toLowerCase();
        if (toLowerCase.startsWith("http://")) {
            return 0;
        }
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return -10;
        }
        if (!toLowerCase.startsWith(Environment.getExternalStorageDirectory().toString().toLowerCase())) {
            return -5;
        }
        File file = new File(str);
        if (!file.exists() || file.isDirectory()) {
            return -8;
        }
        long length = file.length();
        if (length == 0) {
            return -9;
        }
        if (length > 1073741824) {
            return -6;
        }
        return 0;
    }
}
