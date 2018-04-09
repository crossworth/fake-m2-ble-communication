package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/* compiled from: ProGuard */
public class AssistActivity extends Activity {
    private static final String RESTART_FLAG = "RESTART_FLAG";
    private static final String TAG = "Donald";
    private static BaseApi sApiObject;
    private BaseApi mAPiObject;

    public static Intent getAssistActivityIntent(Context context) {
        return new Intent(context, AssistActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        requestWindowFeature(1);
        Log.d(TAG, "AssistActivity--onCreate--");
        if (sApiObject == null) {
            finish();
            return;
        }
        this.mAPiObject = sApiObject;
        sApiObject = null;
        int intExtra = this.mAPiObject.getActivityIntent().getIntExtra(Constants.KEY_REQUEST_CODE, 0);
        if (bundle != null) {
            i = bundle.getBoolean(RESTART_FLAG);
        }
        if (i == 0) {
            startActivityForResult(this.mAPiObject.getActivityIntent(), intExtra);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        Log.d(TAG, "AssistActivity--onSaveInstanceState--");
        bundle.putBoolean(RESTART_FLAG, true);
        super.onSaveInstanceState(bundle);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        Log.d(TAG, "AssistActivity--onActivityResult--");
        super.onActivityResult(i, i2, intent);
        if (this.mAPiObject != null) {
            this.mAPiObject.onActivityResult(i, i2, intent);
        }
        finish();
    }

    public static void setApiObject(BaseApi baseApi) {
        sApiObject = baseApi;
    }
}
