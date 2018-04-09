package com.zhuoyou.plugin.running.net;

import android.os.Handler;
import java.util.HashMap;

public class AppRequest {
    private ResultListener listener = new C19101();
    private Handler mHandler;

    class C19101 extends ResultListener {
        C19101() {
        }
    }

    public AppRequest(ResultListener listener) {
        this.listener = listener;
    }

    public void testRequest(int id) {
        HashMap<String, String> map = new HashMap();
        map.put("noticeId", id + "");
        VolleyRequest.execute(101020, this.listener, map, NetCode.URL, "testRequest");
    }
}
