package com.droi.sdk.push.p019a;

import android.text.TextUtils;
import com.droi.sdk.push.data.ServerAddressBean;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1014i;
import com.droi.sdk.push.utils.C1015j;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public abstract class C0975b implements Runnable {
    private String[] f3201a = new String[]{"push_heartapi.droibaas.com:2100"};
    protected DatagramSocket f3202b;
    protected long f3203c = 0;
    protected long f3204d = 0;
    protected byte[] f3205e;
    protected ConcurrentLinkedQueue f3206f = new ConcurrentLinkedQueue();
    protected AtomicLong f3207g = new AtomicLong(0);
    protected AtomicLong f3208h = new AtomicLong(0);
    protected int f3209i = 1024;
    protected int f3210j = 100;
    protected byte[] f3211k;
    protected boolean f3212l = true;
    protected boolean f3213m = false;
    protected boolean f3214n = false;
    protected Thread f3215o;
    protected C0976c f3216p;
    protected Thread f3217q;

    public C0975b(byte[] bArr) {
        if (bArr == null || bArr.length != 16) {
            throw new IllegalArgumentException("uuid byte array must be not null and length of 16 bytes");
        }
        this.f3205e = bArr;
        m2940c();
    }

    private InetSocketAddress m2929a(String str) {
        InetSocketAddress inetSocketAddress;
        if (C1015j.m3168d(str)) {
            String[] split = str.split(":");
            if (split.length == 2) {
                Object obj = split[0];
                Object obj2 = split[1];
                if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2)) {
                    return null;
                }
                try {
                    inetSocketAddress = new InetSocketAddress(obj, Integer.parseInt(obj2));
                } catch (NumberFormatException e) {
                    inetSocketAddress = null;
                }
                return inetSocketAddress;
            }
        }
        inetSocketAddress = null;
        return inetSocketAddress;
    }

    private void m2930a(byte[] bArr) {
        if (bArr != null && this.f3202b != null) {
            DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
            datagramPacket.setSocketAddress(this.f3202b.getRemoteSocketAddress());
            this.f3202b.send(datagramPacket);
            this.f3203c = System.currentTimeMillis();
        }
    }

    private void m2931d(C0974a c0974a) {
        if (c0974a != null) {
            byte[] bArr = new byte[]{(byte) 68, (byte) 82};
            int d = c0974a.m2924d();
            byte[] bArr2 = new byte[30];
            ByteBuffer.wrap(bArr2).put(bArr).put((byte) 1).put((byte) d).put(this.f3205e).putLong(c0974a.m2928h()).putChar('\u0000');
            m2930a(bArr2);
        }
    }

    private synchronized void m2932h() {
        this.f3211k = new byte[this.f3209i];
    }

    private void m2933i() {
        if (Math.abs(System.currentTimeMillis() - this.f3203c) >= ((long) (this.f3210j * 1000))) {
            byte[] bArr = new byte[30];
            ByteBuffer.wrap(bArr).put(new byte[]{(byte) 68, (byte) 82}).put((byte) 1).put((byte) 0).put(this.f3205e).putLong(0).putChar('\u0000');
            C1012g.m3138a("Heartbeat: " + C1014i.m3148a(bArr));
            try {
                m2930a(bArr);
            } catch (Exception e) {
                C1012g.m3142d("send heartbeat exception!");
                throw e;
            }
        }
    }

    private void m2934j() {
        if (this.f3202b != null) {
            DatagramPacket datagramPacket = new DatagramPacket(this.f3211k, this.f3211k.length);
            this.f3202b.setSoTimeout(5000);
            this.f3202b.receive(datagramPacket);
            if (datagramPacket.getLength() > 0 && datagramPacket.getData() != null && datagramPacket.getData().length != 0) {
                Object obj = new byte[datagramPacket.getLength()];
                System.arraycopy(datagramPacket.getData(), 0, obj, 0, datagramPacket.getLength());
                C0974a c0974a = new C0974a(obj);
                C1012g.m3141c("receive push message: " + c0974a.m2928h());
                if (c0974a.m2926f()) {
                    this.f3204d = System.currentTimeMillis();
                    try {
                        m2931d(c0974a);
                        if (32 == c0974a.m2924d() && !mo1933b(c0974a)) {
                            C1012g.m3141c("receive message: id = " + c0974a.m2928h() + ", length = " + obj.length);
                            m2941c(c0974a);
                            this.f3216p.m2948b();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        C1012g.m3140b("ack message: " + c0974a.m2928h() + " failed!");
                        throw e;
                    }
                }
                C1012g.m3140b("attention: data format error!");
            }
        }
    }

    public abstract void mo1930a();

    public void m2936a(int i) {
        if (i > 0) {
            C1012g.m3141c("set heartbeat===" + i);
            this.f3210j = i;
        }
    }

    public abstract void mo1931a(C0974a c0974a);

    public abstract boolean mo1932b();

    public abstract boolean mo1933b(C0974a c0974a);

    public void m2940c() {
        try {
            ServerAddressBean serverAddressBean = (ServerAddressBean) C1015j.m3161b(C1015j.m3153a("droiServerIpList"));
            if (serverAddressBean != null) {
                Object address = serverAddressBean.getAddress(String.valueOf(1));
                if (address != null && address.length > 0) {
                    this.f3201a = new String[(address.length + 1)];
                    System.arraycopy(address, 0, this.f3201a, 0, address.length);
                    this.f3201a[address.length] = "push_heartapi.droibaas.com:2100";
                    return;
                }
            }
        } catch (Exception e) {
        }
        this.f3201a = new String[]{"push_heartapi.droibaas.com:2100"};
    }

    protected boolean m2941c(C0974a c0974a) {
        boolean add = this.f3206f.add(c0974a);
        if (add) {
            this.f3207g.addAndGet(1);
        }
        return add;
    }

    protected C0974a m2942d() {
        C0974a c0974a = (C0974a) this.f3206f.poll();
        if (c0974a != null) {
            this.f3208h.addAndGet(1);
        }
        return c0974a;
    }

    protected synchronized void m2943e() {
        int i = 0;
        synchronized (this) {
            if (this.f3212l) {
                if (this.f3202b != null) {
                    try {
                        this.f3202b.close();
                        this.f3202b = null;
                    } catch (Exception e) {
                    }
                }
                if (mo1932b()) {
                    this.f3202b = new DatagramSocket();
                    String[] strArr = this.f3201a;
                    int length = strArr.length;
                    while (i < length) {
                        try {
                            this.f3202b.connect(m2929a(strArr[i]));
                            this.f3212l = false;
                            break;
                        } catch (SocketException e2) {
                            if (this.f3202b != null) {
                                this.f3202b.disconnect();
                                this.f3202b = null;
                            }
                            i++;
                        }
                    }
                }
            }
        }
    }

    public synchronized void m2944f() {
        if (!this.f3213m) {
            m2932h();
            this.f3215o = new Thread(this, "udp-client-receiver");
            this.f3215o.setDaemon(true);
            synchronized (this.f3215o) {
                this.f3215o.start();
                this.f3215o.wait();
            }
            this.f3216p = new C0976c(this);
            this.f3217q = new Thread(this.f3216p, "udp-client-worker");
            this.f3217q.setDaemon(true);
            synchronized (this.f3217q) {
                this.f3217q.start();
                this.f3217q.wait();
            }
            this.f3213m = true;
        }
    }

    public void m2945g() {
        this.f3214n = true;
        if (this.f3202b != null) {
            try {
                this.f3202b.close();
            } catch (Exception e) {
            }
            this.f3202b = null;
        }
        if (this.f3215o != null) {
            try {
                this.f3215o.interrupt();
            } catch (Exception e2) {
            }
        }
        if (this.f3217q != null) {
            try {
                this.f3217q.interrupt();
            } catch (Exception e3) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r5 = this;
        r4 = 1;
        r1 = r5.f3215o;
        monitor-enter(r1);
        r0 = r5.f3215o;	 Catch:{ all -> 0x0041 }
        r0.notifyAll();	 Catch:{ all -> 0x0041 }
        monitor-exit(r1);	 Catch:{ all -> 0x0041 }
    L_0x000a:
        r0 = r5.f3214n;
        if (r0 != 0) goto L_0x0123;
    L_0x000e:
        r0 = r5.mo1932b();	 Catch:{ SocketTimeoutException -> 0x0072, Exception -> 0x009a, Throwable -> 0x00cc }
        if (r0 != 0) goto L_0x0044;
    L_0x0014:
        r5.mo1930a();	 Catch:{ Exception -> 0x0144, SocketTimeoutException -> 0x0072, Throwable -> 0x00cc }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0144, SocketTimeoutException -> 0x0072, Throwable -> 0x00cc }
    L_0x001c:
        r0 = r5.f3212l;
        if (r0 != r4) goto L_0x0028;
    L_0x0020:
        r5.mo1930a();	 Catch:{ Exception -> 0x0141 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0141 }
    L_0x0028:
        r0 = r5.f3206f;
        r0 = r0.isEmpty();
        if (r0 == r4) goto L_0x0036;
    L_0x0030:
        r0 = r5.mo1932b();
        if (r0 != 0) goto L_0x000a;
    L_0x0036:
        r5.mo1930a();	 Catch:{ Exception -> 0x003f }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x003f }
        goto L_0x000a;
    L_0x003f:
        r0 = move-exception;
        goto L_0x000a;
    L_0x0041:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0041 }
        throw r0;
    L_0x0044:
        r5.m2943e();	 Catch:{ SocketTimeoutException -> 0x0072, Exception -> 0x009a, Throwable -> 0x00cc }
        r5.m2933i();	 Catch:{ SocketTimeoutException -> 0x0072, Exception -> 0x009a, Throwable -> 0x00cc }
        r5.m2934j();	 Catch:{ SocketTimeoutException -> 0x0072, Exception -> 0x009a, Throwable -> 0x00cc }
        r0 = r5.f3212l;
        if (r0 != r4) goto L_0x0059;
    L_0x0051:
        r5.mo1930a();	 Catch:{ Exception -> 0x013e }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x013e }
    L_0x0059:
        r0 = r5.f3206f;
        r0 = r0.isEmpty();
        if (r0 == r4) goto L_0x0067;
    L_0x0061:
        r0 = r5.mo1932b();
        if (r0 != 0) goto L_0x000a;
    L_0x0067:
        r5.mo1930a();	 Catch:{ Exception -> 0x0070 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0070 }
        goto L_0x000a;
    L_0x0070:
        r0 = move-exception;
        goto L_0x000a;
    L_0x0072:
        r0 = move-exception;
        r0 = r5.f3212l;
        if (r0 != r4) goto L_0x007f;
    L_0x0077:
        r5.mo1930a();	 Catch:{ Exception -> 0x013b }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x013b }
    L_0x007f:
        r0 = r5.f3206f;
        r0 = r0.isEmpty();
        if (r0 == r4) goto L_0x008d;
    L_0x0087:
        r0 = r5.mo1932b();
        if (r0 != 0) goto L_0x000a;
    L_0x008d:
        r5.mo1930a();	 Catch:{ Exception -> 0x0097 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0097 }
        goto L_0x000a;
    L_0x0097:
        r0 = move-exception;
        goto L_0x000a;
    L_0x009a:
        r0 = move-exception;
        r1 = r0 instanceof java.net.SocketException;	 Catch:{ all -> 0x00ff }
        if (r1 != 0) goto L_0x00a2;
    L_0x009f:
        com.droi.sdk.push.utils.C1012g.m3137a(r0);	 Catch:{ all -> 0x00ff }
    L_0x00a2:
        r0 = 1;
        r5.f3212l = r0;	 Catch:{ all -> 0x00ff }
        r0 = r5.f3212l;
        if (r0 != r4) goto L_0x00b1;
    L_0x00a9:
        r5.mo1930a();	 Catch:{ Exception -> 0x0138 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0138 }
    L_0x00b1:
        r0 = r5.f3206f;
        r0 = r0.isEmpty();
        if (r0 == r4) goto L_0x00bf;
    L_0x00b9:
        r0 = r5.mo1932b();
        if (r0 != 0) goto L_0x000a;
    L_0x00bf:
        r5.mo1930a();	 Catch:{ Exception -> 0x00c9 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x00c9 }
        goto L_0x000a;
    L_0x00c9:
        r0 = move-exception;
        goto L_0x000a;
    L_0x00cc:
        r0 = move-exception;
        r1 = new java.lang.Exception;	 Catch:{ all -> 0x00ff }
        r1.<init>(r0);	 Catch:{ all -> 0x00ff }
        com.droi.sdk.push.utils.C1012g.m3137a(r1);	 Catch:{ all -> 0x00ff }
        r0 = 1;
        r5.f3212l = r0;	 Catch:{ all -> 0x00ff }
        r0 = r5.f3212l;
        if (r0 != r4) goto L_0x00e4;
    L_0x00dc:
        r5.mo1930a();	 Catch:{ Exception -> 0x0136 }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x0136 }
    L_0x00e4:
        r0 = r5.f3206f;
        r0 = r0.isEmpty();
        if (r0 == r4) goto L_0x00f2;
    L_0x00ec:
        r0 = r5.mo1932b();
        if (r0 != 0) goto L_0x000a;
    L_0x00f2:
        r5.mo1930a();	 Catch:{ Exception -> 0x00fc }
        r0 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r0);	 Catch:{ Exception -> 0x00fc }
        goto L_0x000a;
    L_0x00fc:
        r0 = move-exception;
        goto L_0x000a;
    L_0x00ff:
        r0 = move-exception;
        r1 = r5.f3212l;
        if (r1 != r4) goto L_0x010c;
    L_0x0104:
        r5.mo1930a();	 Catch:{ Exception -> 0x0134 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r2);	 Catch:{ Exception -> 0x0134 }
    L_0x010c:
        r1 = r5.f3206f;
        r1 = r1.isEmpty();
        if (r1 == r4) goto L_0x011a;
    L_0x0114:
        r1 = r5.mo1932b();
        if (r1 != 0) goto L_0x0122;
    L_0x011a:
        r5.mo1930a();	 Catch:{ Exception -> 0x0132 }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        java.lang.Thread.sleep(r2);	 Catch:{ Exception -> 0x0132 }
    L_0x0122:
        throw r0;
    L_0x0123:
        r0 = r5.f3202b;
        if (r0 == 0) goto L_0x012f;
    L_0x0127:
        r0 = r5.f3202b;	 Catch:{ Exception -> 0x0130 }
        r0.close();	 Catch:{ Exception -> 0x0130 }
    L_0x012c:
        r0 = 0;
        r5.f3202b = r0;
    L_0x012f:
        return;
    L_0x0130:
        r0 = move-exception;
        goto L_0x012c;
    L_0x0132:
        r1 = move-exception;
        goto L_0x0122;
    L_0x0134:
        r1 = move-exception;
        goto L_0x010c;
    L_0x0136:
        r0 = move-exception;
        goto L_0x00e4;
    L_0x0138:
        r0 = move-exception;
        goto L_0x00b1;
    L_0x013b:
        r0 = move-exception;
        goto L_0x007f;
    L_0x013e:
        r0 = move-exception;
        goto L_0x0059;
    L_0x0141:
        r0 = move-exception;
        goto L_0x0028;
    L_0x0144:
        r0 = move-exception;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.push.a.b.run():void");
    }
}
