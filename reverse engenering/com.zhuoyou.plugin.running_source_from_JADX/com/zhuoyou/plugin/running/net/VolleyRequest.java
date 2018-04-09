package com.zhuoyou.plugin.running.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zhuoyou.plugin.running.app.TheApp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyRequest {
    public static final int MSG_REQUEST_FAIL = 4;
    public static final int MSG_REQUEST_SUCCESS = 3;
    public static final int MSG_SERVER_ERROR = 2;
    public static final int MSG_VOLLEY_ERROR = 1;
    public static final String TAG = "VolleyRequest";
    private static RequestQueue mQueue = TheApp.getRequestQueue();

    private static class MyRequest extends Request<String> {
        private byte[] content;
        private ResultListener listener;
        private Handler mHandler = new C19121();
        private HashMap<String, String> params;
        private int requestCode;
        private int resultCode;

        class C19121 extends Handler {
            C19121() {
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        MyRequest.this.listener.onServerError(MyRequest.this.requestCode, MyRequest.this.resultCode, MyRequest.this.getTag());
                        return;
                    case 3:
                        MyRequest.this.listener.onSuccess(MyRequest.this.requestCode, MyRequest.this.resultCode, msg.getData().getSerializable("result_data"), MyRequest.this.getTag());
                        return;
                    case 4:
                        MyRequest.this.listener.onFail(MyRequest.this.requestCode, MyRequest.this.resultCode, MyRequest.this.getTag());
                        return;
                    default:
                        return;
                }
            }
        }

        public MyRequest(int requestCode, ResultListener listener, HashMap<String, String> params, String url, ErrorListener errorListener) {
            super(1, url, errorListener);
            this.listener = listener;
            this.requestCode = requestCode;
            this.params = params;
            getContent();
        }

        private String buildHeadData() {
            String result = "";
            UUID uuid = UUID.randomUUID();
            Header header = new Header();
            header.setBasicVer((byte) 1);
            header.setLength(84);
            header.setType((byte) 1);
            header.setReserved((short) 0);
            header.setFirstTransaction(uuid.getMostSignificantBits());
            header.setSecondTransaction(uuid.getLeastSignificantBits());
            header.setMessageCode(this.requestCode);
            return header.toString();
        }

        private String buildBodyData() {
            JSONObject jsonObjBody = new JSONObject();
            try {
                if (this.params != null && this.params.size() > 0) {
                    for (String key : this.params.keySet()) {
                        jsonObjBody.put(key, this.params.get(key));
                    }
                }
                return jsonObjBody.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }

        private void getContent() {
            JSONObject jsObject = new JSONObject();
            try {
                jsObject.put("head", buildHeadData());
                jsObject.put("body", buildBodyData());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                this.content = DESUtil.encrypt(jsObject.toString().getBytes("utf-8"), NetCode.ENCODE_DECODE_KEY.getBytes());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> map = new HashMap();
            map.put("contentType", "utf-8");
            map.put("Content-Type", "application/x-www-form-urlencoded");
            map.put("Content-Length", "" + this.content.length);
            map.put("Connection", "close");
            return map;
        }

        protected void deliverResponse(final String result) {
            new Thread() {
                public void run() {
                    MyRequest.this.resultCode = VolleyRequest.getResult(result);
                    Log.i(VolleyRequest.TAG, "request finish : " + MyRequest.this.getTag());
                    if (TextUtils.isEmpty(result) || MyRequest.this.resultCode == -1) {
                        MyRequest.this.mHandler.sendEmptyMessage(2);
                    } else if (MyRequest.this.resultCode != 1000) {
                        Message msg = new Message();
                        msg.what = 3;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("result_data", JsonParser.parse(MyRequest.this.requestCode, result));
                        msg.setData(bundle);
                        MyRequest.this.mHandler.sendMessage(msg);
                    } else {
                        MyRequest.this.mHandler.sendEmptyMessage(4);
                    }
                }
            }.start();
        }

        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
                return Response.success(new String(DESUtil.decrypt(response.data, NetCode.ENCODE_DECODE_KEY.getBytes())), HttpHeaderParser.parseCacheHeaders(response));
            } catch (Throwable e) {
                e.printStackTrace();
                return Response.error(new ParseError(e));
            }
        }

        public byte[] getBody() throws AuthFailureError {
            return this.content;
        }
    }

    private VolleyRequest() {
    }

    public static void execute(final int msgCode, final ResultListener listener, HashMap<String, String> params, String url, final String tag) {
        MyRequest request = new MyRequest(msgCode, listener, params, url, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                listener.onVolleyError(msgCode, volleyError, tag);
            }
        });
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 0, 2.0f));
        mQueue.add(request);
    }

    private static int getResult(String result) {
        JSONObject bodyObject = JsonParser.getBodyJson(result);
        if (bodyObject == null) {
            return -1;
        }
        return bodyObject.optInt("result", -1);
    }
}
