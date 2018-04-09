package com.zhuoyou.plugin.running.net;

import android.util.Log;
import com.android.volley.VolleyError;
import com.zhuoyou.plugin.running.tools.Tools;
import java.io.Serializable;

public class ResultListener {
    void onVolleyError(int resultCode, VolleyError volleyError, Object tag) {
        Log.i(VolleyRequest.TAG, "request error: " + volleyError);
    }

    void onServerError(int resultCode, int requestCode, Object tag) {
        Tools.makeToast("访问服务器出错");
    }

    protected void onSuccess(int resultCode, int requestCode, Serializable resultData, Object tag) {
    }

    void onFail(int resultCode, int requestCode, Object tag) {
    }
}
