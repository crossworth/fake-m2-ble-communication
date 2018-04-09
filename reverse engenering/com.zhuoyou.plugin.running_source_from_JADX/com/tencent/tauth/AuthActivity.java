package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;

/* compiled from: ProGuard */
public class AuthActivity extends Activity {
    private static final String ACTION_ADD_TO_QQFAVORITES = "addToQQFavorites";
    public static final String ACTION_KEY = "action";
    private static final String ACTION_SEND_TO_MY_COMPUTER = "sendToMyComputer";
    public static final String ACTION_SHARE_PRIZE = "sharePrize";
    private static final String ACTION_SHARE_TO_QQ = "shareToQQ";
    private static final String ACTION_SHARE_TO_QZONE = "shareToQzone";
    private static final String ACTION_SHARE_TO_TROOP_BAR = "shareToTroopBar";
    private static final String SHARE_PRIZE_ACTIVITY_ID = "activityid";
    private static final String TAG = "openSDK_LOG.AuthActivity";
    private static int mShareQzoneBackTime = 0;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() == null) {
            C1314f.m3871d(TAG, "-->onCreate, getIntent() return null");
            finish();
            return;
        }
        Uri uri = null;
        try {
            uri = getIntent().getData();
        } catch (Exception e) {
            C1314f.m3872e(TAG, "-->onCreate, getIntent().getData() has exception! " + e.getMessage());
        }
        C1314f.m3864a(TAG, "-->onCreate, uri: " + uri);
        handleActionUri(uri);
    }

    private void handleActionUri(Uri uri) {
        C1314f.m3870c(TAG, "-->handleActionUri--start");
        if (uri == null || uri.toString() == null || uri.toString().equals("")) {
            C1314f.m3871d(TAG, "-->handleActionUri, uri invalid");
            finish();
            return;
        }
        String uri2 = uri.toString();
        Bundle decodeUrl = Util.decodeUrl(uri2.substring(uri2.indexOf("#") + 1));
        if (decodeUrl == null) {
            C1314f.m3871d(TAG, "-->handleActionUri, bundle is null");
            finish();
            return;
        }
        String string = decodeUrl.getString("action");
        C1314f.m3870c(TAG, "-->handleActionUri, action: " + string);
        if (string == null) {
            finish();
        } else if (string.equals("shareToQQ") || string.equals("shareToQzone") || string.equals("sendToMyComputer") || string.equals("shareToTroopBar")) {
            if (string.equals("shareToQzone") && SystemUtils.getAppVersionName(this, "com.tencent.mobileqq") != null && SystemUtils.compareQQVersion(this, SystemUtils.QQ_VERSION_NAME_5_2_0) < 0) {
                mShareQzoneBackTime++;
                if (mShareQzoneBackTime == 2) {
                    mShareQzoneBackTime = 0;
                    finish();
                    return;
                }
            }
            C1314f.m3870c(TAG, "-->handleActionUri, most share action, start assistactivity");
            Intent intent = new Intent(this, AssistActivity.class);
            intent.putExtras(decodeUrl);
            intent.setFlags(603979776);
            startActivity(intent);
            finish();
        } else if (string.equals("addToQQFavorites")) {
            r2 = getIntent();
            r2.putExtras(decodeUrl);
            r2.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_SHARE);
            IUiListener listnerWithAction = UIListenerManager.getInstance().getListnerWithAction(string);
            if (listnerWithAction != null) {
                UIListenerManager.getInstance().handleDataToListener(r2, listnerWithAction);
            }
            finish();
        } else if (string.equals(ACTION_SHARE_PRIZE)) {
            r2 = getPackageManager().getLaunchIntentForPackage(getPackageName());
            string = decodeUrl.getString("response");
            Object obj = "";
            try {
                obj = Util.parseJson(string).getString("activityid");
            } catch (Throwable e) {
                C1314f.m3868b(TAG, "sharePrize parseJson has exception.", e);
            }
            if (!TextUtils.isEmpty(obj)) {
                r2.putExtra(ACTION_SHARE_PRIZE, true);
                Bundle bundle = new Bundle();
                bundle.putString("activityid", obj);
                r2.putExtras(bundle);
            }
            startActivity(r2);
            finish();
        } else {
            finish();
        }
    }
}
