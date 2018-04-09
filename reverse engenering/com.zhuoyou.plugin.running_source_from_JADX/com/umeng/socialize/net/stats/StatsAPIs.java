package com.umeng.socialize.net.stats;

import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.net.base.StatsClient;
import com.umeng.socialize.net.stats.cache.C1662b;
import com.umeng.socialize.net.stats.cache.C1665c.C1664a;
import com.umeng.socialize.net.stats.cache.UMCacheListener;
import com.umeng.socialize.utils.Log;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;

public class StatsAPIs {
    private static StatsClient mClient = new StatsClient();

    static class C16551 implements UMCacheListener {
        C16551() {
        }

        public void onResult(boolean z, C1664a c1664a) {
            if (c1664a != null) {
                JSONArray jSONArray = new JSONArray();
                List b = c1664a.m4527b();
                for (int i = 0; i < b.size(); i++) {
                    jSONArray.put(b.get(i));
                }
                StatsAPIs.sendStatsCache(c1664a.m4524a(), jSONArray);
            }
        }
    }

    public static SocializeReseponse shareStatsStart(ShareStatsRequest shareStatsRequest) {
        shareStatsRequest.setRequestPath(ShareLifecycle.START);
        SocializeReseponse execute = mClient.execute(shareStatsRequest);
        handleResponseForCache(false, shareStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse shareStatsEnd(ShareStatsRequest shareStatsRequest) {
        shareStatsRequest.setRequestPath(ShareLifecycle.END);
        SocializeReseponse execute = mClient.execute(shareStatsRequest);
        handleResponseForCache(false, shareStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse authStatsStart(AuthStatsRequest authStatsRequest) {
        authStatsRequest.setRequestPath(AuthLifecycle.START);
        SocializeReseponse execute = mClient.execute(authStatsRequest);
        handleResponseForCache(false, authStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse authStatsEnd(AuthStatsRequest authStatsRequest) {
        authStatsRequest.setRequestPath(AuthLifecycle.END);
        SocializeReseponse execute = mClient.execute(authStatsRequest);
        handleResponseForCache(false, authStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse userInfoStatsStart(UserInfoStatsRequest userInfoStatsRequest) {
        userInfoStatsRequest.setRequestPath(GetUserInfoLifecycle.START);
        SocializeReseponse execute = mClient.execute(userInfoStatsRequest);
        handleResponseForCache(false, userInfoStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse userInfoStatsEnd(UserInfoStatsRequest userInfoStatsRequest) {
        userInfoStatsRequest.setRequestPath(GetUserInfoLifecycle.END);
        SocializeReseponse execute = mClient.execute(userInfoStatsRequest);
        handleResponseForCache(false, userInfoStatsRequest, execute);
        return execute;
    }

    public static SocializeReseponse dauStats(DauStatsRequest dauStatsRequest) {
        SocializeReseponse execute = mClient.execute(dauStatsRequest);
        handleResponseForCache(true, dauStatsRequest, execute);
        return execute;
    }

    private static void handleResponseForCache(boolean z, SocializeRequest socializeRequest, SocializeReseponse socializeReseponse) {
        if (socializeReseponse == null || !socializeReseponse.isHttpOK()) {
            try {
                String query = new URL(socializeRequest.toGetUrl()).getQuery();
                Log.m4546d("StatsAPIs", "save stats log");
                C1662b.m4517a().m4521a(query, null);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (z) {
            Log.m4546d("StatsAPIs", "read stats log");
            C1662b.m4517a().m4520a(new C16551());
        }
    }

    private static void sendStatsCache(final String str, JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() > 0) {
            Log.m4546d("StatsAPIs", "send stats log:" + jSONArray.toString());
            final StatsLogRequest statsLogRequest = new StatsLogRequest(SocializeReseponse.class);
            statsLogRequest.addStringParams("data", jSONArray.toString());
            QueuedWork.runInBack(new Runnable() {
                public void run() {
                    SocializeReseponse execute = StatsAPIs.mClient.execute(statsLogRequest);
                    if (execute != null && execute.isHttpOK()) {
                        C1662b.m4517a().m4522b(str, null);
                        Log.m4546d("StatsAPIs", "delete stats log" + str);
                    }
                }
            });
        }
    }
}
