package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.ServiceSettings;
import java.util.Map;

/* compiled from: BasicHandler */
public abstract class C1608a<T, V> extends cw {
    protected T f4274a;
    protected int f4275b = 1;
    protected String f4276c = "";
    protected Context f4277d;
    private int f4278h = 1;

    protected abstract V mo3042a(String str) throws AMapException;

    public C1608a(Context context, T t) {
        m4355a(context, t);
    }

    private void m4355a(Context context, T t) {
        this.f4277d = context;
        this.f4274a = t;
        this.f4275b = 1;
        m1569b(ServiceSettings.getInstance().getSoTimeOut());
        m1566a(ServiceSettings.getInstance().getConnectionTimeOut());
    }

    protected V m4360a(byte[] bArr) throws AMapException {
        String str;
        try {
            str = new String(bArr, "utf-8");
        } catch (Throwable e) {
            C0390i.m1594a(e, "ProtocalHandler", "loadData");
            str = null;
        }
        if (str == null || str.equals("")) {
            return null;
        }
        C0390i.m1596b(str);
        return mo3042a(str);
    }

    public V m4358a() throws AMapException {
        if (this.f4274a != null) {
            return mo3048e();
        }
        return null;
    }

    private V mo3048e() throws AMapException {
        int i = 0;
        V v = null;
        while (i < this.f4275b) {
            try {
                int protocol = ServiceSettings.getInstance().getProtocol();
                cv a = cv.m4493a(false);
                m1567a(az.m1289a(this.f4277d));
                v = m4356b(mo3043a(protocol, a, this));
                i = this.f4275b;
            } catch (ar e) {
                i++;
                if (i < this.f4275b) {
                    try {
                        Thread.sleep((long) (this.f4278h * 1000));
                    } catch (InterruptedException e2) {
                        if ("http连接失败 - ConnectionException".equals(e.getMessage()) || "socket 连接异常 - SocketException".equals(e.getMessage()) || "服务器连接失败 - UnknownServiceException".equals(e.getMessage())) {
                            throw new AMapException(AMapException.AMAP_CLIENT_NETWORK_EXCEPTION);
                        }
                        throw new AMapException(e.m1207a());
                    }
                }
                mo3047d();
                if ("http连接失败 - ConnectionException".equals(e.getMessage()) || "socket 连接异常 - SocketException".equals(e.getMessage()) || "未知的错误".equals(e.m1207a()) || "服务器连接失败 - UnknownServiceException".equals(e.getMessage())) {
                    throw new AMapException(AMapException.AMAP_CLIENT_NETWORK_EXCEPTION);
                }
                throw new AMapException(e.m1207a());
            } catch (AMapException e3) {
                i++;
                if (i >= this.f4275b) {
                    throw new AMapException(e3.getErrorMessage());
                }
            } catch (Throwable th) {
                AMapException aMapException = new AMapException(AMapException.AMAP_CLIENT_UNKNOWN_ERROR);
            }
        }
        return v;
    }

    protected byte[] mo3043a(int i, cv cvVar, cw cwVar) throws ar {
        if (i == 1) {
            return cvVar.mo1777b(cwVar);
        }
        if (i == 2) {
            return cvVar.m1547a(cwVar);
        }
        return null;
    }

    public Map<String, String> mo1756b() {
        return null;
    }

    public Map<String, String> mo1757c() {
        return null;
    }

    private V m4356b(byte[] bArr) throws AMapException {
        return m4360a(bArr);
    }

    protected V mo3047d() {
        return null;
    }
}
