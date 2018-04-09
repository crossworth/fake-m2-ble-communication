package com.tencent.open;

import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class C1730g implements IRequestListener {
    private IUiListener f4678a;

    public C1730g(IUiListener iUiListener) {
        this.f4678a = iUiListener;
    }

    public void onComplete(JSONObject jSONObject) {
        if (this.f4678a != null) {
            this.f4678a.onComplete(jSONObject);
        }
    }

    public void onIOException(IOException iOException) {
        m4847a(iOException);
    }

    public void onMalformedURLException(MalformedURLException malformedURLException) {
        m4847a(malformedURLException);
    }

    public void onJSONException(JSONException jSONException) {
        m4847a(jSONException);
    }

    public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
        m4847a(connectTimeoutException);
    }

    public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
        m4847a(socketTimeoutException);
    }

    public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
        m4847a(networkUnavailableException);
    }

    public void onHttpStatusException(HttpStatusException httpStatusException) {
        m4847a(httpStatusException);
    }

    public void onUnknowException(Exception exception) {
        m4847a(exception);
    }

    private void m4847a(Exception exception) {
        if (this.f4678a != null) {
            this.f4678a.onError(new UiError(100, exception.getMessage(), null));
        }
    }
}
