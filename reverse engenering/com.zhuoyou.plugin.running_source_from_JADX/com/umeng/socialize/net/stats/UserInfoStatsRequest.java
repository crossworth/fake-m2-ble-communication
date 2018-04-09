package com.umeng.socialize.net.stats;

import android.content.Context;
import com.umeng.socialize.net.base.SocializeReseponse;

public class UserInfoStatsRequest extends StatsRequest {

    protected enum GetUserInfoLifecycle {
        START("getinfostart"),
        END("getinfoend");
        
        private String value;

        private GetUserInfoLifecycle(String str) {
            this.value = str;
        }

        public String toString() {
            return this.value;
        }
    }

    public UserInfoStatsRequest(Context context, Class<? extends SocializeReseponse> cls) {
        super(context, "", cls, 0, RequestMethod.GET);
    }

    protected String getPath() {
        return this.mRequestPath;
    }

    protected void setRequestPath(GetUserInfoLifecycle getUserInfoLifecycle) {
        addStringParams(PARAMS_STATS_TYPE, getUserInfoLifecycle.toString());
    }
}
