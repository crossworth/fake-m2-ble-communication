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
    private WeakReference<Context> f2437a;
    private Uri f2438b;
    private String f2439c;
    private String f2440d;
    private String f2441e;
    private String f2442f;
    private boolean f2443g = false;
    private int f2444h;

    public CallbackManager(Activity activity) {
        this.f2437a = new WeakReference(activity.getApplicationContext());
        Intent intent = activity.getIntent();
        if (intent != null) {
            this.f2438b = intent.getData();
            this.f2439c = intent.getStringExtra(Constants.SRC_PACKAGE_NAME);
            this.f2440d = intent.getStringExtra(Constants.SRC_ACTIVITY_CLASS_NAME);
            this.f2441e = intent.getStringExtra(Constants.SRC_ACTIVITY_ACTION);
            this.f2444h = intent.getIntExtra(Constants.REQUEST_TYPE, 0);
            this.f2442f = intent.getStringExtra(Constants.APPID);
            if (this.f2438b != null && this.f2440d != null) {
                this.f2443g = true;
            }
        }
    }

    public boolean isCallFromTencentApp() {
        return this.f2443g;
    }

    private int m2376a(Bundle bundle) {
        if (!this.f2443g) {
            return -2;
        }
        Intent intent = new Intent();
        intent.setClassName(this.f2439c, this.f2440d);
        intent.setAction(this.f2441e);
        bundle.putString(Constants.APPID, this.f2442f);
        intent.putExtras(bundle);
        intent.setFlags(268435456);
        ((Context) this.f2437a.get()).startActivity(intent);
        return 0;
    }

    public int getRequestDateTypeFlag() {
        return this.f2444h;
    }

    public boolean isSupportType(int i) {
        return (getRequestDateTypeFlag() & i) != 0;
    }

    public int sendTextAndImagePath(String str, String str2) {
        if (!isSupportType(1)) {
            return -1;
        }
        int a = m2377a(str2);
        if (a != 0) {
            return a;
        }
        Parcelable textAndMediaPath = new TextAndMediaPath(str, str2);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 1);
        bundle.putParcelable(Constants.CONTENT_DATA, textAndMediaPath);
        return m2376a(bundle);
    }

    public int sendTextAndVideoPath(String str, String str2) {
        if (!isSupportType(2)) {
            return -1;
        }
        int a = m2377a(str2);
        if (a != 0) {
            return a;
        }
        Parcelable textAndMediaPath = new TextAndMediaPath(str, str2);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 2);
        bundle.putParcelable(Constants.CONTENT_DATA, textAndMediaPath);
        return m2376a(bundle);
    }

    public int sendTextOnly(String str) {
        if (!isSupportType(4)) {
            return -1;
        }
        Parcelable textOnly = new TextOnly(str);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DATA_TYPE, 4);
        bundle.putParcelable(Constants.CONTENT_DATA, textOnly);
        return m2376a(bundle);
    }

    private int m2377a(String str) {
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
