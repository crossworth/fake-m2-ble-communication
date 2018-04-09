package com.tencent.wxop.stat;

public class StatAppMonitor implements Cloneable {
    public static final int FAILURE_RESULT_TYPE = 1;
    public static final int LOGIC_FAILURE_RESULT_TYPE = 2;
    public static final int SUCCESS_RESULT_TYPE = 0;
    private String f4508a = null;
    private long f4509b = 0;
    private long f4510c = 0;
    private int f4511d = 0;
    private long f4512e = 0;
    private int f4513f = 0;
    private int f4514g = 1;

    public StatAppMonitor(String str) {
        this.f4508a = str;
    }

    public StatAppMonitor(String str, int i, int i2, long j, long j2, long j3, int i3) {
        this.f4508a = str;
        this.f4509b = j;
        this.f4510c = j2;
        this.f4511d = i;
        this.f4512e = j3;
        this.f4513f = i2;
        this.f4514g = i3;
    }

    public StatAppMonitor clone() {
        try {
            return (StatAppMonitor) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getInterfaceName() {
        return this.f4508a;
    }

    public long getMillisecondsConsume() {
        return this.f4512e;
    }

    public long getReqSize() {
        return this.f4509b;
    }

    public long getRespSize() {
        return this.f4510c;
    }

    public int getResultType() {
        return this.f4511d;
    }

    public int getReturnCode() {
        return this.f4513f;
    }

    public int getSampling() {
        return this.f4514g;
    }

    public void setInterfaceName(String str) {
        this.f4508a = str;
    }

    public void setMillisecondsConsume(long j) {
        this.f4512e = j;
    }

    public void setReqSize(long j) {
        this.f4509b = j;
    }

    public void setRespSize(long j) {
        this.f4510c = j;
    }

    public void setResultType(int i) {
        this.f4511d = i;
    }

    public void setReturnCode(int i) {
        this.f4513f = i;
    }

    public void setSampling(int i) {
        if (i <= 0) {
            i = 1;
        }
        this.f4514g = i;
    }
}
