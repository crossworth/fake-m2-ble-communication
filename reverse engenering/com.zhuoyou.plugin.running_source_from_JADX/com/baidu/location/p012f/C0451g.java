package com.baidu.location.p012f;

import android.net.wifi.ScanResult;
import android.text.TextUtils;
import com.baidu.location.p006h.C0468j;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class C0451g {
    public List<ScanResult> f802a = null;
    private long f803b = 0;
    private long f804c = 0;
    private boolean f805d = false;
    private boolean f806e;

    public C0451g(List<ScanResult> list, long j) {
        this.f803b = j;
        this.f802a = list;
        this.f804c = System.currentTimeMillis();
        m932i();
    }

    private boolean m931a(String str) {
        return TextUtils.isEmpty(str) ? false : Pattern.compile("wpa|wep", 2).matcher(str).find();
    }

    private void m932i() {
        if (m933a() >= 1) {
            Object obj = 1;
            for (int size = this.f802a.size() - 1; size >= 1 && r2 != null; size--) {
                int i = 0;
                obj = null;
                while (i < size) {
                    Object obj2;
                    if (((ScanResult) this.f802a.get(i)).level < ((ScanResult) this.f802a.get(i + 1)).level) {
                        ScanResult scanResult = (ScanResult) this.f802a.get(i + 1);
                        this.f802a.set(i + 1, this.f802a.get(i));
                        this.f802a.set(i, scanResult);
                        obj2 = 1;
                    } else {
                        obj2 = obj;
                    }
                    i++;
                    obj = obj2;
                }
            }
        }
    }

    public int m933a() {
        return this.f802a == null ? 0 : this.f802a.size();
    }

    public String m934a(int i) {
        return m935a(i, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    public java.lang.String m935a(int r26, boolean r27) {
        /*
        r25 = this;
        r2 = r25.m933a();
        r3 = 1;
        if (r2 >= r3) goto L_0x0009;
    L_0x0007:
        r2 = 0;
    L_0x0008:
        return r2;
    L_0x0009:
        r3 = 0;
        r19 = new java.util.Random;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r19.<init>();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r20 = new java.lang.StringBuffer;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        r0 = r20;
        r0.<init>(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r21 = new java.util.ArrayList;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r21.<init>();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = com.baidu.location.p012f.C0454h.m948a();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r6 = r2.m960i();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r5 = 0;
        r4 = 0;
        r2 = -1;
        if (r6 == 0) goto L_0x0350;
    L_0x002a:
        r7 = r6.getBSSID();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r7 == 0) goto L_0x0350;
    L_0x0030:
        r2 = r6.getBSSID();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = ":";
        r5 = "";
        r5 = r2.replace(r4, r5);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r6.getRssi();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = com.baidu.location.p012f.C0454h.m948a();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = r4.m962k();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 >= 0) goto L_0x0348;
    L_0x004a:
        r2 = -r2;
        r16 = r2;
        r17 = r4;
        r18 = r5;
    L_0x0051:
        r4 = 0;
        r8 = 0;
        r2 = 0;
        r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r7 = 17;
        if (r6 < r7) goto L_0x0345;
    L_0x005c:
        r4 = android.os.SystemClock.elapsedRealtimeNanos();	 Catch:{ Error -> 0x00aa, Exception -> 0x0337 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
    L_0x0063:
        r6 = 0;
        r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x0345;
    L_0x0069:
        r2 = 1;
        r14 = r4;
    L_0x006b:
        if (r2 == 0) goto L_0x0342;
    L_0x006d:
        if (r2 == 0) goto L_0x00ae;
    L_0x006f:
        if (r27 == 0) goto L_0x00ae;
    L_0x0071:
        r2 = 1;
    L_0x0072:
        r13 = r2;
    L_0x0073:
        r6 = 0;
        r5 = 0;
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.size();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = 1;
        r0 = r26;
        if (r2 <= r0) goto L_0x033e;
    L_0x0082:
        r2 = 0;
        r12 = r2;
    L_0x0084:
        r0 = r26;
        if (r12 >= r0) goto L_0x0211;
    L_0x0088:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.get(r12);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.level;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 != 0) goto L_0x00b0;
    L_0x0096:
        r2 = r4;
        r4 = r6;
        r6 = r8;
        r24 = r5;
        r5 = r3;
        r3 = r24;
    L_0x009e:
        r8 = r12 + 1;
        r12 = r8;
        r8 = r6;
        r6 = r4;
        r4 = r2;
        r24 = r3;
        r3 = r5;
        r5 = r24;
        goto L_0x0084;
    L_0x00aa:
        r4 = move-exception;
        r4 = 0;
        goto L_0x0063;
    L_0x00ae:
        r2 = 0;
        goto L_0x0072;
    L_0x00b0:
        if (r13 == 0) goto L_0x00d3;
    L_0x00b2:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x0195, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x0195, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x0195, Error -> 0x01a4 }
        r10 = r2.timestamp;	 Catch:{ Exception -> 0x0195, Error -> 0x01a4 }
        r10 = r14 - r10;
        r22 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r10 = r10 / r22;
    L_0x00c5:
        r2 = java.lang.Long.valueOf(r10);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r21;
        r0.add(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1));
        if (r2 <= 0) goto L_0x00d3;
    L_0x00d2:
        r8 = r10;
    L_0x00d3:
        if (r4 == 0) goto L_0x019a;
    L_0x00d5:
        r4 = 0;
        r2 = "&wf=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x00dd:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.get(r12);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.BSSID;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 == 0) goto L_0x0207;
    L_0x00eb:
        r7 = ":";
        r10 = "";
        r7 = r2.replace(r7, r10);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r7);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.get(r12);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.level;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 >= 0) goto L_0x0107;
    L_0x0106:
        r2 = -r2;
    L_0x0107:
        r10 = java.util.Locale.CHINA;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r11 = ";%d;";
        r22 = 1;
        r0 = r22;
        r0 = new java.lang.Object[r0];	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r22 = r0;
        r23 = 0;
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r22[r23] = r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r22;
        r2 = java.lang.String.format(r10, r11, r0);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r6 = r6 + 1;
        r2 = 0;
        if (r18 == 0) goto L_0x014b;
    L_0x012b:
        r0 = r18;
        r7 = r0.equals(r7);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r7 == 0) goto L_0x014b;
    L_0x0133:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.get(r12);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.capabilities;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r2 = r0.m931a(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r0.f806e = r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = 1;
        r5 = r6;
    L_0x014b:
        if (r2 != 0) goto L_0x01f6;
    L_0x014d:
        if (r3 != 0) goto L_0x01a8;
    L_0x014f:
        r2 = 10;
        r0 = r19;
        r2 = r0.nextInt(r2);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r7 = 2;
        if (r2 != r7) goto L_0x033b;
    L_0x015a:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        if (r2 == 0) goto L_0x033b;
    L_0x0168:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.length();	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r7 = 30;
        if (r2 >= r7) goto L_0x033b;
    L_0x017c:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = 1;
    L_0x018e:
        r3 = r5;
        r5 = r2;
        r2 = r4;
        r4 = r6;
        r6 = r8;
        goto L_0x009e;
    L_0x0195:
        r2 = move-exception;
        r10 = 0;
        goto L_0x00c5;
    L_0x019a:
        r2 = "|";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        goto L_0x00dd;
    L_0x01a4:
        r2 = move-exception;
        r2 = 0;
        goto L_0x0008;
    L_0x01a8:
        r2 = 1;
        if (r3 != r2) goto L_0x033b;
    L_0x01ab:
        r2 = 20;
        r0 = r19;
        r2 = r0.nextInt(r2);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r7 = 1;
        if (r2 != r7) goto L_0x033b;
    L_0x01b6:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        if (r2 == 0) goto L_0x033b;
    L_0x01c4:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.length();	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r7 = 30;
        if (r2 >= r7) goto L_0x033b;
    L_0x01d8:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.get(r12);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = r2.SSID;	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Exception -> 0x01eb, Error -> 0x01a4 }
        r2 = 2;
        goto L_0x018e;
    L_0x01eb:
        r2 = move-exception;
        r2 = r4;
        r4 = r6;
        r6 = r8;
        r24 = r5;
        r5 = r3;
        r3 = r24;
        goto L_0x009e;
    L_0x01f6:
        r0 = r25;
        r2 = r0.f802a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.get(r12);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (android.net.wifi.ScanResult) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.SSID;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x0207:
        r2 = r4;
        r4 = r6;
        r6 = r8;
        r24 = r5;
        r5 = r3;
        r3 = r24;
        goto L_0x009e;
    L_0x0211:
        if (r4 != 0) goto L_0x0334;
    L_0x0213:
        r2 = new java.lang.StringBuilder;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2.<init>();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = "&wf_n=";
        r2 = r2.append(r3);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.append(r5);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.toString();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r18 == 0) goto L_0x024c;
    L_0x022d:
        r2 = -1;
        r0 = r16;
        if (r0 == r2) goto L_0x024c;
    L_0x0232:
        r2 = new java.lang.StringBuilder;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2.<init>();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = "&wf_rs=";
        r2 = r2.append(r3);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r16;
        r2 = r2.append(r0);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.toString();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x024c:
        r2 = 10;
        r2 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x02d4;
    L_0x0252:
        r2 = r21.size();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 <= 0) goto L_0x02d4;
    L_0x0258:
        r2 = 0;
        r0 = r21;
        r2 = r0.get(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (java.lang.Long) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = r2.longValue();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r6 = 0;
        r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x02d4;
    L_0x026b:
        r6 = new java.lang.StringBuffer;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r6.<init>(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = "&wf_ut=";
        r6.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = 1;
        r2 = 0;
        r0 = r21;
        r2 = r0.get(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = (java.lang.Long) r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r7 = r21.iterator();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x0285:
        r3 = r7.hasNext();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r3 == 0) goto L_0x02cb;
    L_0x028b:
        r3 = r7.next();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = (java.lang.Long) r3;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r4 == 0) goto L_0x02a4;
    L_0x0293:
        r4 = 0;
        r8 = r3.longValue();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r6.append(r8);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = r4;
    L_0x029c:
        r4 = "|";
        r6.append(r4);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r4 = r3;
        goto L_0x0285;
    L_0x02a4:
        r8 = r3.longValue();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r10 = r2.longValue();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r8 = r8 - r10;
        r10 = 0;
        r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r3 == 0) goto L_0x02c9;
    L_0x02b3:
        r3 = new java.lang.StringBuilder;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3.<init>();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r10 = "";
        r3 = r3.append(r10);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = r3.append(r8);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r3 = r3.toString();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r6.append(r3);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x02c9:
        r3 = r4;
        goto L_0x029c;
    L_0x02cb:
        r2 = r6.toString();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x02d4:
        r2 = "&wf_st=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r2 = r0.f803b;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = "&wf_et=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r2 = r0.f804c;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = "&wf_vt=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = com.baidu.location.p012f.C0454h.f810a;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r5 <= 0) goto L_0x031c;
    L_0x0304:
        r2 = 1;
        r0 = r25;
        r0.f805d = r2;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r2 = "&wf_en=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r25;
        r2 = r0.f806e;	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        if (r2 == 0) goto L_0x0332;
    L_0x0316:
        r2 = 1;
    L_0x0317:
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x031c:
        if (r17 == 0) goto L_0x032c;
    L_0x031e:
        r2 = "&wf_gw=";
        r0 = r20;
        r0.append(r2);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        r0 = r20;
        r1 = r17;
        r0.append(r1);	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
    L_0x032c:
        r2 = r20.toString();	 Catch:{ Error -> 0x01a4, Exception -> 0x0337 }
        goto L_0x0008;
    L_0x0332:
        r2 = 0;
        goto L_0x0317;
    L_0x0334:
        r2 = 0;
        goto L_0x0008;
    L_0x0337:
        r2 = move-exception;
        r2 = 0;
        goto L_0x0008;
    L_0x033b:
        r2 = r3;
        goto L_0x018e;
    L_0x033e:
        r26 = r2;
        goto L_0x0082;
    L_0x0342:
        r13 = r2;
        goto L_0x0073;
    L_0x0345:
        r14 = r4;
        goto L_0x006b;
    L_0x0348:
        r16 = r2;
        r17 = r4;
        r18 = r5;
        goto L_0x0051;
    L_0x0350:
        r16 = r2;
        r17 = r4;
        r18 = r5;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.f.g.a(int, boolean):java.lang.String");
    }

    public boolean m936a(C0451g c0451g) {
        if (this.f802a == null || c0451g == null || c0451g.f802a == null) {
            return false;
        }
        int size = this.f802a.size() < c0451g.f802a.size() ? this.f802a.size() : c0451g.f802a.size();
        for (int i = 0; i < size; i++) {
            if (!((ScanResult) this.f802a.get(i)).BSSID.equals(((ScanResult) c0451g.f802a.get(i)).BSSID)) {
                return false;
            }
        }
        return true;
    }

    public String m937b() {
        try {
            return m935a(C0468j.f884L, true);
        } catch (Exception e) {
            return null;
        }
    }

    public String m938b(int i) {
        if (m933a() < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(512);
        int size = this.f802a.size();
        if (size <= i) {
            i = size;
        }
        int i2 = 0;
        int i3 = 1;
        while (i2 < i) {
            if (((ScanResult) this.f802a.get(i2)).level != 0) {
                if (((ScanResult) this.f802a.get(i2)).BSSID == null) {
                    size = i3;
                    i2++;
                    i3 = size;
                } else {
                    if (i3 != 0) {
                        i3 = 0;
                    } else {
                        stringBuffer.append("|");
                    }
                    stringBuffer.append(((ScanResult) this.f802a.get(i2)).BSSID.replace(":", ""));
                    size = ((ScanResult) this.f802a.get(i2)).level;
                    if (size < 0) {
                        size = -size;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(size)}));
                }
            }
            size = i3;
            i2++;
            i3 = size;
        }
        return i3 == 0 ? stringBuffer.toString() : null;
    }

    public boolean m939b(C0451g c0451g) {
        if (this.f802a == null || c0451g == null || c0451g.f802a == null) {
            return false;
        }
        int size = this.f802a.size() < c0451g.f802a.size() ? this.f802a.size() : c0451g.f802a.size();
        for (int i = 0; i < size; i++) {
            String str = ((ScanResult) this.f802a.get(i)).BSSID;
            int i2 = ((ScanResult) this.f802a.get(i)).level;
            String str2 = ((ScanResult) c0451g.f802a.get(i)).BSSID;
            int i3 = ((ScanResult) c0451g.f802a.get(i)).level;
            if (!str.equals(str2) || i2 != i3) {
                return false;
            }
        }
        return true;
    }

    public String m940c() {
        try {
            return m934a(15);
        } catch (Exception e) {
            return null;
        }
    }

    public String m941c(int i) {
        int i2 = 0;
        if (i == 0 || m933a() < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        int size = this.f802a.size();
        int i3 = size > C0468j.f884L ? C0468j.f884L : size;
        int i4 = 1;
        int i5 = 0;
        while (i5 < i3) {
            if ((i4 & i) == 0 || ((ScanResult) this.f802a.get(i5)).BSSID == null) {
                size = i2;
            } else {
                if (i2 == 0) {
                    stringBuffer.append("&ssid=");
                } else {
                    stringBuffer.append("|");
                }
                stringBuffer.append(((ScanResult) this.f802a.get(i5)).BSSID.replace(":", ""));
                stringBuffer.append(";");
                stringBuffer.append(((ScanResult) this.f802a.get(i5)).SSID);
                size = i2 + 1;
            }
            i4 <<= 1;
            i5++;
            i2 = size;
        }
        return stringBuffer.toString();
    }

    public boolean m942c(C0451g c0451g) {
        return C0454h.m951a(c0451g, this, C0468j.f887O);
    }

    public int m943d() {
        for (int i = 0; i < m933a(); i++) {
            int i2 = -((ScanResult) this.f802a.get(i)).level;
            if (i2 > 0) {
                return i2;
            }
        }
        return 0;
    }

    public boolean m944e() {
        return this.f805d;
    }

    public boolean m945f() {
        return System.currentTimeMillis() - this.f804c > 0 && System.currentTimeMillis() - this.f804c < 5000;
    }

    public boolean m946g() {
        return System.currentTimeMillis() - this.f804c > 0 && System.currentTimeMillis() - this.f804c < 5000;
    }

    public boolean m947h() {
        return System.currentTimeMillis() - this.f804c > 0 && System.currentTimeMillis() - this.f803b < 5000;
    }
}
