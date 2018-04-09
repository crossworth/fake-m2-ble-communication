package com.tencent.stat;

public class StatAppMonitor implements Cloneable {
    public static final int FAILURE_RESULT_TYPE = 1;
    public static final int LOGIC_FAILURE_RESULT_TYPE = 2;
    public static final int SUCCESS_RESULT_TYPE = 0;
    private String f2777a = null;
    private long f2778b = 0;
    private long f2779c = 0;
    private int f2780d = 0;
    private long f2781e = 0;
    private int f2782f = 0;
    private int f2783g = 1;

    public StatAppMonitor(String str) {
        this.f2777a = str;
    }

    public StatAppMonitor(String str, int i, int i2, long j, long j2, long j3, int i3) {
        this.f2777a = str;
        this.f2778b = j;
        this.f2779c = j2;
        this.f2780d = i;
        this.f2781e = j3;
        this.f2782f = i2;
        this.f2783g = i3;
    }

    public StatAppMonitor clone() {
        try {
            return (StatAppMonitor) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getInterfaceName() {
        return this.f2777a;
    }

    public long getMillisecondsConsume() {
        return this.f2781e;
    }

    public long getReqSize() {
        return this.f2778b;
    }

    public long getRespSize() {
        return this.f2779c;
    }

    public int getResultType() {
        return this.f2780d;
    }

    public int getReturnCode() {
        return this.f2782f;
    }

    public int getSampling() {
        return this.f2783g;
    }

    public void setInterfaceName(String str) {
        this.f2777a = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f2781e = j;
    }

    public void setReqSize(long j) {
        this.f2778b = j;
    }

    public void setRespSize(long j) {
        this.f2779c = j;
    }

    public void setResultType(int i) {
        this.f2780d = i;
    }

    public void setReturnCode(int i) {
        this.f2782f = i;
    }

    public void setSampling(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.f2783g = i;
    }
}
