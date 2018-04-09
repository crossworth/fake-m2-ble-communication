package com.zhuoyou.plugin.gps;

import java.io.Serializable;

public class OperationTimeModel implements Serializable {
    public static final int BEGIN_GPS_GUIDE = 1;
    public static final int COMPLETE_GPS_GUIDE = 4;
    public static final int CONTINUE_GPS_GUIDE = 3;
    public static final int NO_LAOCTION_SINGAL = 6;
    public static final int OTHER_REASON = 7;
    public static final int SERVICE_IS_STOP = 5;
    public static final int STOP_GPS_GUIDE = 2;
    public static final long serialVersionUID = -1912165521494046890L;
    public long operatId;
    public int operationState;
    public long operationSystime;
    public long operationtime;
    public int syncState;

    public long getOperationtime() {
        return this.operationtime;
    }

    public void setOperationtime(long operationtime) {
        this.operationtime = operationtime;
    }

    public int getOperationState() {
        return this.operationState;
    }

    public void setOperationState(int operationState) {
        this.operationState = operationState;
    }

    public long getOperationSystime() {
        return this.operationSystime;
    }

    public void setOperationSystime(long operationSystime) {
        this.operationSystime = operationSystime;
    }

    public int getSyncState() {
        return this.syncState;
    }

    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }

    public long getOperatId() {
        return this.operatId;
    }

    public void setOperatId(long operatId) {
        this.operatId = operatId;
    }
}
