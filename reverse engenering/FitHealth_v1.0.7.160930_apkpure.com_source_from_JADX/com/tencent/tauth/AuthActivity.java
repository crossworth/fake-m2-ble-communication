package com.tencent.tauth;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.AuthMap;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.utils.TemporaryStorage;
import com.tencent.utils.Util;
import com.zhuoyou.plugin.database.DataBaseContants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthActivity extends Activity {
    private static final String ACTION_KEY = "action";
    private static final String ACTION_SHARE_TO_QQ = "shareToQQ";
    private static final String ACTION_SHARE_TO_QZONE = "shareToQzone";

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handleActionUri(getIntent().getData());
    }

    private void handleActionUri(Uri uri) {
        if (uri == null || uri.toString().equals("")) {
            finish();
            return;
        }
        String uri2 = uri.toString();
        Bundle decodeUrl = Util.decodeUrl(uri2.substring(uri2.indexOf("#") + 1));
        String string = decodeUrl.getString("action");
        if (string == null) {
            execAuthCallback(decodeUrl, uri2);
        } else if (string.equals("shareToQQ") || string.equals("shareToQzone")) {
            execShareToQQCallback(decodeUrl);
        } else {
            execAuthCallback(decodeUrl, uri2);
        }
    }

    private void execAuthCallback(Bundle bundle, String str) {
        AuthMap instance = AuthMap.getInstance();
        String string = bundle.getString("serial");
        Auth auth = instance.get(string);
        if (auth != null) {
            if (str.indexOf("://cancel") != -1) {
                auth.listener.onCancel();
                auth.dialog.dismiss();
            } else {
                String string2 = bundle.getString("access_token");
                if (string2 != null) {
                    bundle.putString("access_token", instance.decode(string2, auth.key));
                }
                JSONObject decodeUrlToJson = Util.decodeUrlToJson(new JSONObject(), Util.encodeUrl(bundle));
                String optString = decodeUrlToJson.optString("cb");
                if ("".equals(optString)) {
                    auth.listener.onComplete(decodeUrlToJson);
                    auth.dialog.dismiss();
                } else {
                    auth.dialog.callJs(optString, decodeUrlToJson.toString());
                }
            }
            instance.remove(string);
        }
        finish();
    }

    private void execShareToQQCallback(Bundle bundle) {
        Object obj = TemporaryStorage.get(bundle.getString("action"));
        if (obj == null) {
            finish();
            return;
        }
        IUiListener iUiListener = (IUiListener) obj;
        String string = bundle.getString("result");
        String string2 = bundle.getString("response");
        if (string.equals("cancel")) {
            iUiListener.onCancel();
        } else if (string.equals("error")) {
            iUiListener.onError(new UiError(-6, "unknown error", string2 + ""));
        } else if (string.equals(DataBaseContants.COMPLETE)) {
            if (string2 == null) {
                string = "{\"ret\": 0}";
            } else {
                string = string2;
            }
            try {
                iUiListener.onComplete(new JSONObject(string));
            } catch (JSONException e) {
                e.printStackTrace();
                iUiListener.onError(new UiError(-4, "json error", string + ""));
            }
        }
        finish();
    }
}
