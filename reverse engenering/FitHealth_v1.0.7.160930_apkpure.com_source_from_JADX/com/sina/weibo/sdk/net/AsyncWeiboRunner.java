package com.sina.weibo.sdk.net;

import android.os.AsyncTask;
import com.sina.weibo.sdk.exception.WeiboException;

public class AsyncWeiboRunner {

    class C06641 extends Thread {
        private final /* synthetic */ String val$httpMethod;
        private final /* synthetic */ RequestListener val$listener;
        private final /* synthetic */ WeiboParameters val$params;
        private final /* synthetic */ String val$url;

        C06641(String str, String str2, WeiboParameters weiboParameters, RequestListener requestListener) {
            this.val$url = str;
            this.val$httpMethod = str2;
            this.val$params = weiboParameters;
            this.val$listener = requestListener;
        }

        public void run() {
            try {
                String resp = HttpManager.openUrl(this.val$url, this.val$httpMethod, this.val$params);
                if (this.val$listener != null) {
                    this.val$listener.onComplete(resp);
                }
            } catch (WeiboException e) {
                if (this.val$listener != null) {
                    this.val$listener.onWeiboException(e);
                }
            }
        }
    }

    private static class AsyncTaskResult<T> {
        private WeiboException error;
        private T result;

        public T getResult() {
            return this.result;
        }

        public WeiboException getError() {
            return this.error;
        }

        public AsyncTaskResult(T result) {
            this.result = result;
        }

        public AsyncTaskResult(WeiboException error) {
            this.error = error;
        }
    }

    private static class RequestRunner extends AsyncTask<Void, Void, AsyncTaskResult<String>> {
        private final String mHttpMethod;
        private final RequestListener mListener;
        private final WeiboParameters mParams;
        private final String mUrl;

        public RequestRunner(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
            this.mUrl = url;
            this.mParams = params;
            this.mHttpMethod = httpMethod;
            this.mListener = listener;
        }

        protected AsyncTaskResult<String> doInBackground(Void... params) {
            try {
                return new AsyncTaskResult(HttpManager.openUrl(this.mUrl, this.mHttpMethod, this.mParams));
            } catch (WeiboException e) {
                return new AsyncTaskResult(e);
            }
        }

        protected void onPreExecute() {
        }

        protected void onPostExecute(AsyncTaskResult<String> result) {
            WeiboException exception = result.getError();
            if (exception != null) {
                this.mListener.onWeiboException(exception);
            } else {
                this.mListener.onComplete((String) result.getResult());
            }
        }
    }

    @Deprecated
    public static void requestByThread(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        new C06641(url, httpMethod, params, listener).start();
    }

    public static String request(String url, WeiboParameters params, String httpMethod) throws WeiboException {
        return HttpManager.openUrl(url, httpMethod, params);
    }

    public static void requestAsync(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        new RequestRunner(url, params, httpMethod, listener).execute(new Void[1]);
    }
}
