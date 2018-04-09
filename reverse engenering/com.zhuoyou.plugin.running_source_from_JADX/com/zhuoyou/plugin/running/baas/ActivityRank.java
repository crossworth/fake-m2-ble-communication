package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

public class ActivityRank extends DroiObject {
    @DroiExpose
    public String accountId;
    @DroiExpose
    public String activityCode;
    @DroiExpose
    public String date;
    @DroiExpose
    public String headUrl;
    @DroiExpose
    public String nickName;
    @DroiExpose
    public int rank;
    @DroiExpose
    public int step;

    public boolean equals(Object o) {
        if (o instanceof ActivityRank) {
            return this.accountId.equals(((ActivityRank) o).accountId);
        }
        return super.equals(o);
    }
}
