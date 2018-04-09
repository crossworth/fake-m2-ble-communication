package com.tencent.stat;

public class StatAppMonitor implements Cloneable {
    public static final int FAILURE_RESULT_TYPE = 1;
    public static final int LOGIC_FAILURE_RESULT_TYPE = 2;
    public static final int SUCCESS_RESULT_TYPE = 0;
    private String f4279a = null;
    private long f4280b = 0;
    private long f4281c = 0;
    private int f4282d = 0;
    private long f4283e = 0;
    private int f4284f = 0;
    private int f4285g = 1;

    public StatAppMonitor(String str) {
        this.f4279a = str;
    }

    public StatAppMonitor(String str, int i, int i2, long j, long j2, long j3, int i3) {
        this.f4279a = str;
        this.f4280b = j;
        this.f4281c = j2;
        this.f4282d = i;
        this.f4283e = j3;
        this.f4284f = i2;
        this.f4285g = i3;
    }

    public StatAppMonitor clone() {
        try {
            return (StatAppMonitor) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getInterfaceName() {
        return this.f4279a;
    }

    public long getMillisecondsConsume() {
        return this.f4283e;
    }

    public long getReqSize() {
        return this.f4280b;
    }

    public long getRespSize() {
        return this.f4281c;
    }

    public int getResultType() {
        return this.f4282d;
    }

    public int getReturnCode() {
        return this.f4284f;
    }

    public int getSampling() {
        return this.f4285g;
    }

    public void setInterfaceName(String str) {
        this.f4279a = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f4283e = j;
    }

    public void setReqSize(long j) {
        this.f4280b = j;
    }

    public void setRespSize(long j) {
        this.f4281c = j;
    }

    public void setResultType(int i) {
        this.f4282d = i;
    }

    public void setReturnCode(int i) {
        this.f4284f = i;
    }

    public void setSampling(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.f4285g = i;
    }
}
