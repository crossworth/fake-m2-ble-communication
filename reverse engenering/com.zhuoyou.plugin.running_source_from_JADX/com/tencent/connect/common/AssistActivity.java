package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.droi.btlib.connection.MessageObj;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.p037b.C1322d;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AssistActivity extends Activity {
    public static final String EXTRA_INTENT = "openSDK_LOG.AssistActivity.ExtraIntent";
    protected static final int FINISH_BY_TIMEOUT = 0;
    private static final String RESTART_FLAG = "RESTART_FLAG";
    private static final String TAG = "openSDK_LOG.AssistActivity";
    protected Handler handler = new C11721();
    private boolean isRestart = false;

    /* compiled from: ProGuard */
    class C11721 extends Handler {
        C11721() {
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (!AssistActivity.this.isFinishing()) {
                        C1314f.m3871d(AssistActivity.TAG, "-->finish by timeout");
                        AssistActivity.this.finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static Intent getAssistActivityIntent(Context context) {
        return new Intent(context, AssistActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        int i = 0;
        requestWindowFeature(1);
        super.onCreate(bundle);
        setRequestedOrientation(3);
        C1314f.m3867b(TAG, "--onCreate--");
        if (getIntent() == null) {
            C1314f.m3872e(TAG, "-->onCreate--getIntent() returns null");
            finish();
        }
        Intent intent = (Intent) getIntent().getParcelableExtra(EXTRA_INTENT);
        if (intent != null) {
            i = intent.getIntExtra(Constants.KEY_REQUEST_CODE, 0);
        }
        Bundle bundleExtra = getIntent().getBundleExtra(SystemUtils.H5_SHARE_DATA);
        if (bundle != null) {
            this.isRestart = bundle.getBoolean(RESTART_FLAG);
        }
        if (this.isRestart) {
            C1314f.m3867b(TAG, "is restart");
        } else if (bundleExtra != null) {
            C1314f.m3871d(TAG, "--onCreate--h5 bundle not null, will open browser");
            openBrowser(bundleExtra);
        } else if (intent != null) {
            C1314f.m3870c(TAG, "--onCreate--activityIntent not null, will start activity, reqcode = " + i);
            startActivityForResult(intent, i);
        } else {
            C1314f.m3872e(TAG, "--onCreate--activityIntent is null");
            finish();
        }
    }

    protected void onStart() {
        C1314f.m3867b(TAG, "-->onStart");
        super.onStart();
    }

    protected void onResume() {
        C1314f.m3867b(TAG, "-->onResume");
        super.onResume();
        Intent intent = getIntent();
        if (!intent.getBooleanExtra(SystemUtils.IS_LOGIN, false)) {
            if (!(intent.getBooleanExtra(SystemUtils.IS_QQ_MOBILE_SHARE, false) || !this.isRestart || isFinishing())) {
                finish();
            }
            this.handler.sendMessage(this.handler.obtainMessage(0));
        }
    }

    protected void onPause() {
        C1314f.m3867b(TAG, "-->onPause");
        this.handler.removeMessages(0);
        super.onPause();
    }

    protected void onStop() {
        C1314f.m3867b(TAG, "-->onStop");
        super.onStop();
    }

    protected void onDestroy() {
        C1314f.m3867b(TAG, "-->onDestroy");
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
        C1314f.m3870c(TAG, "--onNewIntent");
        super.onNewIntent(intent);
        intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_SHARE);
        setResult(-1, intent);
        if (!isFinishing()) {
            C1314f.m3870c(TAG, "--onNewIntent--activity not finished, finish now");
            finish();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        C1314f.m3867b(TAG, "--onSaveInstanceState--");
        bundle.putBoolean(RESTART_FLAG, true);
        super.onSaveInstanceState(bundle);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        C1314f.m3870c(TAG, "--onActivityResult--requestCode: " + i + " | resultCode: " + i2 + "data = null ? " + (intent == null));
        super.onActivityResult(i, i2, intent);
        if (i != 0) {
            if (intent != null) {
                intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            }
            setResultData(i2, intent);
            finish();
        }
    }

    public void setResultData(int i, Intent intent) {
        if (intent == null) {
            C1314f.m3871d(TAG, "--setResultData--intent is null, setResult ACTIVITY_CANCEL");
            setResult(0, intent);
            return;
        }
        try {
            Object stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
            C1314f.m3867b(TAG, "--setResultDataForLogin-- " + stringExtra);
            if (TextUtils.isEmpty(stringExtra)) {
                C1314f.m3871d(TAG, "--setResultData--response is empty, setResult ACTIVITY_OK");
                setResult(-1, intent);
                return;
            }
            JSONObject jSONObject = new JSONObject(stringExtra);
            CharSequence optString = jSONObject.optString("openid");
            CharSequence optString2 = jSONObject.optString("access_token");
            if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2)) {
                C1314f.m3871d(TAG, "--setResultData--openid or token is empty, setResult ACTIVITY_CANCEL");
                setResult(0, intent);
                return;
            }
            C1314f.m3870c(TAG, "--setResultData--openid and token not empty, setResult ACTIVITY_OK");
            setResult(-1, intent);
        } catch (Exception e) {
            C1314f.m3872e(TAG, "--setResultData--parse response failed");
            e.printStackTrace();
        }
    }

    private void openBrowser(Bundle bundle) {
        String string = bundle.getString("viaShareType");
        String string2 = bundle.getString("callbackAction");
        String string3 = bundle.getString("url");
        String string4 = bundle.getString("openId");
        String string5 = bundle.getString(MessageObj.APPID);
        String str = "";
        String str2 = "";
        if (SystemUtils.QQ_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QQ;
            str2 = Constants.VIA_REPORT_TYPE_SHARE_TO_QQ;
        } else if (SystemUtils.QZONE_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QZONE;
            str2 = Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE;
        }
        if (Util.openBrowser(this, string3)) {
            C1322d.m3896a().m3900a(string4, string5, str, str2, "3", "0", string, "0", "2", "0");
        } else {
            IUiListener listnerWithAction = UIListenerManager.getInstance().getListnerWithAction(string2);
            if (listnerWithAction != null) {
                listnerWithAction.onError(new UiError(-6, Constants.MSG_OPEN_BROWSER_ERROR, null));
            }
            C1322d.m3896a().m3900a(string4, string5, str, str2, "3", "1", string, "0", "2", "0");
            finish();
        }
        getIntent().removeExtra("shareH5");
    }
}
