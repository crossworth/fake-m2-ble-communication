package com.zhuoyou.plugin.share;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.weibo.net.AccessToken;
import com.weibo.net.DialogError;
import com.weibo.net.Oauth2AccessTokenHeader;
import com.weibo.net.Utility;
import com.weibo.net.Weibo;
import com.weibo.net.WeiboDialogListener;
import com.weibo.net.WeiboException;
import com.weibo.net.WeiboParameters;
import com.weibo.net.WeiboWebView;
import com.zhuoyou.plugin.running.ShareActivity;
import com.zhuoyou.plugin.running.SharePopupWindow;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthorizeActivity extends Activity {
    private static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
    private WebView mWebView = null;
    private Weibo weibo = Weibo.getInstance();

    class C14331 implements OnClickListener {
        C14331() {
        }

        public void onClick(View v) {
            AuthorizeActivity.this.finish();
        }
    }

    private class FetchTokenAsync extends AsyncTask<String, Object, Integer> {
        private FetchTokenAsync() {
        }

        protected void onPreExecute() {
        }

        protected Integer doInBackground(String... arg0) {
            String code = arg0[0];
            WeiboParameters parameters = new WeiboParameters();
            parameters.add("client_id", WeiboConstant.CONSUMER_KEY);
            parameters.add("client_secret", WeiboConstant.CONSUMER_SECRET);
            parameters.add("grant_type", "authorization_code");
            parameters.add("code", code);
            parameters.add("redirect_uri", "https://api.weibo.com/oauth2/default.html");
            String result = "";
            try {
                result = EntityUtils.toString(new DefaultHttpClient().execute(new HttpPost("https://open.weibo.cn/oauth2/access_token?" + Utility.encodeUrl(parameters))).getEntity());
                Log.i("caixinxin", result);
                try {
                    JSONObject obj = new JSONObject(result);
                    String access_token = obj.getString("access_token");
                    String expires_in = obj.getString("expires_in");
                    Log.i("gchk", "access_token= " + access_token);
                    Log.i("gchk", "expires_in= " + expires_in);
                    AccessToken accessToken = new AccessToken(access_token, WeiboConstant.CONSUMER_SECRET);
                    accessToken.setExpiresIn(expires_in);
                    AuthorizeActivity.this.weibo.setAccessToken(accessToken);
                    AccessTokenKeeper.writeAccessToken(AuthorizeActivity.this, accessToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (AuthorizeActivity.this.weibo.isSessionValid()) {
                    return Integer.valueOf(0);
                }
                return Integer.valueOf(-1);
            } catch (ClientProtocolException e2) {
                e2.printStackTrace();
                return Integer.valueOf(-1);
            } catch (IOException e3) {
                e3.printStackTrace();
                return Integer.valueOf(-1);
            }
        }

        protected void onPostExecute(Integer result) {
            if (result.intValue() == -1) {
                Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_auth_failed, 0).show();
            } else if (result.intValue() == 0) {
                Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_auth_success, 0).show();
                SharePopupWindow.mInstance.getWeiboView().setImageResource(R.drawable.share_wb_select);
                Message msg = new Message();
                msg.what = 1;
                ShareActivity.mHandler.sendMessage(msg);
            }
            AuthorizeActivity.this.setResult(-1);
            AuthorizeActivity.this.finish();
        }
    }

    class C19032 implements WeiboDialogListener {
        C19032() {
        }

        public void onCancel() {
            Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_auth_canceled, 0).show();
            AuthorizeActivity.this.setResult(-1);
            AuthorizeActivity.this.finish();
        }

        public void onComplete(Bundle values) {
            CookieSyncManager.getInstance().sync();
            Log.i("caixinxin", "weibo onComplete");
            if (values == null) {
                Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_obtain_code_failed, 0).show();
                return;
            }
            String code = values.getString("code");
            if (TextUtils.isEmpty(code)) {
                Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_obtain_code_failed, 0).show();
                return;
            }
            Log.i("caixinxin", code);
            new FetchTokenAsync().execute(new String[]{code});
        }

        public void onError(DialogError arg0) {
            Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_auth_failed, 0).show();
            AuthorizeActivity.this.setResult(-1);
            AuthorizeActivity.this.finish();
        }

        public void onWeiboException(WeiboException arg0) {
            Toast.makeText(AuthorizeActivity.this, R.string.weibosdk_demo_toast_auth_failed, 0).show();
            AuthorizeActivity.this.setResult(-1);
            AuthorizeActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        ((TextView) findViewById(R.id.title)).setText(R.string.weibo_login);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C14331());
        this.mWebView = (WebView) findViewById(R.id.webview);
        weiBoAuth();
    }

    public void weiBoAuth() {
        this.weibo.setupConsumerConfig(WeiboConstant.CONSUMER_KEY, WeiboConstant.CONSUMER_SECRET);
        this.weibo.setRedirectUrl("https://api.weibo.com/oauth2/default.html");
        authorize(this, this.mWebView);
    }

    private void authorize(Activity activity, WebView webview) {
        Utility.setAuthorization(new Oauth2AccessTokenHeader());
        startWebViewAuth(activity, webview);
    }

    private void startWebViewAuth(Activity activity, WebView webview) {
        WeiboParameters params = new WeiboParameters();
        CookieSyncManager.createInstance(activity);
        webview(activity, webview, params, new C19032());
    }

    public void webview(Context context, WebView webview, WeiboParameters parameters, WeiboDialogListener listener) {
        parameters.add("client_id", WeiboConstant.CONSUMER_KEY);
        parameters.add("response_type", "code");
        parameters.add("redirect_uri", "https://api.weibo.com/oauth2/default.html");
        parameters.add("display", "mobile");
        if (this.weibo.isSessionValid()) {
            parameters.add("access_token", this.weibo.getAccessToken().getToken());
        }
        String url = Weibo.URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(parameters);
        if (context.checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            Utility.showAlert(context, "Error", "Application requires permission to access the Internet");
        } else {
            WeiboWebView weiboWebView = new WeiboWebView(this.weibo, webview, context, url, listener);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        finish();
        return true;
    }
}
