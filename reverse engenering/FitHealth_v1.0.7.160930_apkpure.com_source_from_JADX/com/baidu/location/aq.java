package com.baidu.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.ai.C0503b;
import com.baidu.location.p000a.C0495a;
import com.baidu.location.p000a.C0496b;
import com.baidu.location.p001b.p002a.C0512a;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class aq implements C1619j {
    private static final String iB = "GeofenceStrategyService";
    public static final long iE = 21600000;
    private static final String iF = "1";
    private static final String iK = "com.baidu.locsdk.geofence.geofencestrategyservice";
    private static final int iL = 180000;
    private static final int iN = 60000;
    public static aq iP = null;
    private static final String iR = "0";
    public static final String iS = "http://loc.map.baidu.com/fence";
    private static final String iV = "&gf=1";
    private static final String iu = "GeofenceStrategyService";
    private static final int iv = 30000;
    private static final int iw = 360000;
    private static final int iy = 6;
    private boolean iA;
    private String iC;
    private C1617d iD;
    private Handler iG = new Handler();
    private String iH;
    private C0511c iI;
    private WakeLock iJ;
    private int iM;
    private HandlerThread iO;
    private Messenger iQ;
    private String iT;
    private C0503b iU;
    private List iW;
    private C0503b iX;
    private String ix;
    private boolean iz;

    public class C0511c extends BroadcastReceiver {
        final /* synthetic */ aq f2241a;

        public C0511c(aq aqVar) {
            this.f2241a = aqVar;
        }

        public void onReceive(Context context, Intent intent) {
            this.f2241a.m5904new(context);
            this.f2241a.iG.post(this.f2241a.iD);
        }
    }

    private class C1617d implements Runnable, C0497a {
        final /* synthetic */ aq iY;

        private C1617d(aq aqVar) {
            this.iY = aqVar;
        }

        public void run() {
            try {
                C0529a H = C1981n.m6008K().m6018H();
                String e = Jni.m5810e(String.format("%s|%s|%s|0", new Object[]{Integer.valueOf(H.f2271do), Integer.valueOf(H.f2273if), Integer.valueOf(H.f2272for)}));
                this.iY.ix = String.format("%s|%s|%s|0", new Object[]{Integer.valueOf(H.f2271do), Integer.valueOf(H.f2273if), Integer.valueOf(H.f2272for)});
                this.iY.iW = this.iY.m5909m(e);
                this.iY.bZ();
                this.iY.bY();
            } catch (Exception e2) {
                this.iY.m5907if(C1976f.getServiceContext(), (int) aq.iw);
            }
        }
    }

    private class C2059a extends C1982o {
        private static final String dp = "fence";
        private static final String dr = "bloc";
        private static final String ds = "ext";
        private static final String dt = "error";
        private static final String dv = "in";
        final /* synthetic */ aq dn;
        private ah dq;
        private final String du;

        public C2059a(aq aqVar, ah ahVar, String str) {
            this.dn = aqVar;
            this.dq = ahVar;
            this.du = str;
            this.cP = new ArrayList();
        }

        void mo3704V() {
            this.cL = aq.iS;
            DecimalFormat decimalFormat = new DecimalFormat("0.00000");
            String str = "&x=%s&y=%s&r=%s&coord=%s&type=%s&cu=%s&fence_type=%s&wf_on=%s";
            Object[] objArr = new Object[8];
            objArr[0] = decimalFormat.format(this.dq.m4578a());
            objArr[1] = decimalFormat.format(this.dq.m4582byte());
            objArr[2] = String.valueOf(this.dq.m4585do());
            objArr[3] = String.valueOf(this.dq.m4591int());
            objArr[4] = Integer.valueOf(al.m5863do(C1976f.getServiceContext()));
            objArr[5] = C0512a.m2169if(C1976f.getServiceContext());
            objArr[6] = Integer.valueOf(this.dq.m4583case());
            objArr[7] = ai.bb().a5() ? "1" : "0";
            this.cP.add(new BasicNameValuePair(dp, Jni.m5811f(String.format(str, objArr))));
            this.cP.add(new BasicNameValuePair(dr, this.du));
            List list = this.cP;
            r4 = new Object[2];
            ap.bD();
            r4[0] = ap.g9;
            ap.bD();
            r4[1] = ap.g8;
            list.add(new BasicNameValuePair("ext", Jni.m5811f(String.format("&ki=%s&sn=%s", r4))));
        }

        public void aa() {
            m6032R();
        }

        void mo3705if(boolean z) {
            boolean z2 = false;
            this.dn.iz = false;
            if (z && this.cM != null) {
                try {
                    JSONObject jSONObject = new JSONObject(EntityUtils.toString(this.cM, "UTF-8"));
                    if (jSONObject != null) {
                        int intValue = Integer.valueOf(jSONObject.getString("error")).intValue();
                        if (jSONObject.has(dv)) {
                            z2 = Integer.valueOf(jSONObject.getString(dv)).intValue();
                        }
                        if (intValue == 0 && r0) {
                            this.dn.iH = null;
                            this.dn.iU = null;
                            al.m5867for(C1976f.getServiceContext()).m5880if(this.dq);
                            al.m5867for(C1976f.getServiceContext()).bq();
                            if (this.dn.iQ != null) {
                                Message obtain = Message.obtain(null, an.f2211char);
                                Bundle bundle = new Bundle();
                                bundle.putString("geofence_id", this.dq.getGeofenceId());
                                obtain.setData(bundle);
                                this.dn.iQ.send(obtain);
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private class C2060b extends C1989z {
        final /* synthetic */ aq dR;

        private C2060b(aq aqVar) {
            this.dR = aqVar;
        }

        void ab() {
        }

        void mo3707byte(Message message) {
        }
    }

    private void b0() {
        this.iO = new HandlerThread("GeofenceStrategyService", 10);
        this.iO.start();
        this.iG = new Handler(this.iO.getLooper());
        this.iD = new C1617d();
    }

    private List b1() {
        C0529a H = C1981n.m6008K().m6018H();
        this.iX = ai.bb().a7();
        List arrayList = new ArrayList();
        this.iC = String.format("%s|%s|%s|%s", new Object[]{Integer.valueOf(H.f2271do), Integer.valueOf(H.f2273if), Integer.valueOf(H.f2272for), Integer.valueOf(H.f2276try)});
        arrayList.add(this.iC);
        if (this.iX != null) {
            List<ScanResult> list = this.iX.f2165for;
            if (list != null) {
                for (ScanResult scanResult : list) {
                    if (scanResult != null) {
                        arrayList.add(scanResult.BSSID.replace(":", ""));
                    }
                }
            }
        }
        return arrayList;
    }

    public static aq b2() {
        if (iP == null) {
            iP = new aq();
            iP.b0();
        }
        return iP;
    }

    private void bW() {
        if (this.iJ != null && this.iJ.isHeld()) {
            this.iJ.release();
            this.iJ = null;
        }
    }

    private boolean bX() {
        return this.iU == null ? true : this.iX == this.iU ? false : !this.iU.m2148a(this.iX);
    }

    private void bY() {
        if (this.iz) {
            m5907if(C1976f.getServiceContext(), 30000);
        } else if (this.iM > 0) {
            m5907if(C1976f.getServiceContext(), this.iM >= 6 ? iL : this.iM * 30000);
        } else if (this.iW == null || this.iW.size() <= 0) {
            m5907if(C1976f.getServiceContext(), (int) iw);
        } else {
            Object obj = null;
            for (ah ahVar : this.iW) {
                if (!(ahVar.m4590if() || ahVar.m4588for())) {
                    obj = 1;
                    m5894do(ahVar);
                }
                obj = obj;
            }
            if (obj != null) {
                m5907if(C1976f.getServiceContext(), 30000);
            } else {
                m5907if(C1976f.getServiceContext(), (int) iL);
            }
        }
    }

    private void bZ() {
        List<ah> list = m5906for(b1());
        if (list == null) {
            this.iz = false;
        } else if (!this.iC.equals(this.iH) || bX()) {
            for (ah ahVar : list) {
                if (ahVar != null) {
                    this.iz = true;
                    m5894do(ahVar);
                    this.iH = this.iC;
                    this.iU = this.iX;
                    this.iM = 0;
                } else {
                    this.iz = false;
                    this.iM++;
                    this.iM = this.iM == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED ? 1 : this.iM;
                }
            }
        }
    }

    private void m5894do(ah ahVar) {
        new C2059a(this, ahVar, Jni.m5811f(new C2060b().m6095void(iV).replace(BDGeofence.COORD_TYPE_GCJ, ahVar.m4591int()))).aa();
    }

    private void m5904new(Context context) {
        if (this.iJ == null) {
            this.iJ = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "GeofenceStrategyService");
            this.iJ.setReferenceCounted(false);
            this.iJ.acquire(TimeManager.UNIT_MINUTE);
        }
    }

    public void m5905byte(Context context) {
        this.iA = false;
        C0521e.m2185a(context, PendingIntent.getBroadcast(context, 0, new Intent(iK), 134217728));
        bW();
        if (this.iI != null) {
            try {
                context.unregisterReceiver(this.iI);
            } catch (Exception e) {
            }
        }
    }

    public List m5906for(List list) {
        Cursor rawQuery;
        Throwable th;
        SQLiteDatabase readableDatabase = C0526i.m2195a(C1976f.getServiceContext()).getReadableDatabase();
        List list2 = null;
        if (readableDatabase != null) {
            Cursor cursor = null;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                Cursor cursor2 = null;
                for (String e : list) {
                    try {
                        List list3;
                        rawQuery = readableDatabase.rawQuery(String.format("SELECT b.geofence_id, b.longitude, b.latitude, b.radius, b.coord_type, b.duration_millis, b.is_lac, b.is_cell, b.is_wifi, b.radius_type FROM %s AS a LEFT JOIN %s AS b WHERE (a.geofence_id = b.geofence_id) AND (a.ap = '%s' AND  (b.valid_date + b.duration_millis) >= %d) AND (b.next_active_time < %d)", new Object[]{C0496b.f2133a, C0495a.f2124else, Jni.m5810e(e), Long.valueOf(currentTimeMillis), Long.valueOf(currentTimeMillis)}), null);
                        if (rawQuery != null) {
                            try {
                                if (rawQuery.getCount() > 0) {
                                    List arrayList = new ArrayList();
                                    try {
                                        arrayList.clear();
                                        rawQuery.moveToFirst();
                                        int columnIndex = rawQuery.getColumnIndex("geofence_id");
                                        int columnIndex2 = rawQuery.getColumnIndex("longitude");
                                        int columnIndex3 = rawQuery.getColumnIndex("latitude");
                                        int columnIndex4 = rawQuery.getColumnIndex(C0495a.f2122char);
                                        int columnIndex5 = rawQuery.getColumnIndex(C0495a.f2128int);
                                        int columnIndex6 = rawQuery.getColumnIndex(C0495a.f2132void);
                                        int columnIndex7 = rawQuery.getColumnIndex(C0495a.f2120byte);
                                        int columnIndex8 = rawQuery.getColumnIndex(C0495a.f2129long);
                                        int columnIndex9 = rawQuery.getColumnIndex(C0495a.f2126goto);
                                        int columnIndex10 = rawQuery.getColumnIndex(C0495a.f2131try);
                                        do {
                                            this.iT = e;
                                            String string = rawQuery.getString(columnIndex);
                                            float floatValue = Float.valueOf(rawQuery.getString(columnIndex2)).floatValue();
                                            float floatValue2 = Float.valueOf(rawQuery.getString(columnIndex3)).floatValue();
                                            float floatValue3 = Float.valueOf(rawQuery.getString(columnIndex4)).floatValue();
                                            String string2 = rawQuery.getString(columnIndex5);
                                            long j = rawQuery.getLong(columnIndex6);
                                            boolean z = rawQuery.getInt(columnIndex7) != 0;
                                            boolean z2 = rawQuery.getInt(columnIndex8) != 0;
                                            boolean z3 = rawQuery.getInt(columnIndex9) != 0;
                                            ah ahVar = new ah(string, (double) floatValue, (double) floatValue2, rawQuery.getInt(columnIndex10), j, string2);
                                            if (ahVar != null) {
                                                ahVar.m4579a(floatValue3);
                                                ahVar.m4586do(z);
                                                ahVar.m4581a(z2);
                                                ahVar.m4589if(z3);
                                            }
                                            arrayList.add(ahVar);
                                        } while (rawQuery.moveToNext());
                                        list3 = arrayList;
                                        cursor2 = rawQuery;
                                        list2 = list3;
                                    } catch (Exception e2) {
                                        cursor = rawQuery;
                                        list2 = arrayList;
                                    } catch (Throwable th2) {
                                        th = th2;
                                    }
                                }
                            } catch (Exception e3) {
                                cursor = rawQuery;
                            } catch (Throwable th22) {
                                th = th22;
                            }
                        }
                        list3 = list2;
                        cursor2 = rawQuery;
                        list2 = list3;
                    } catch (Exception e4) {
                        cursor = cursor2;
                    } catch (Throwable th3) {
                        th = th3;
                        rawQuery = cursor2;
                    }
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
            } catch (Exception e5) {
                if (cursor != null) {
                    cursor.close();
                }
                readableDatabase.close();
                return list2;
            } catch (Throwable th4) {
                rawQuery = null;
                th = th4;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
            readableDatabase.close();
        }
        return list2;
    }

    public void m5907if(Context context, int i) {
        Intent intent = new Intent(iK);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
        if (i <= 0) {
            C0521e.m2185a(context, broadcast);
            context.sendBroadcast(intent);
            return;
        }
        C0521e.m2186a(context, broadcast, i);
    }

    public void m5908if(Context context, Message message) {
        if (!this.iA) {
            this.iQ = message.replyTo;
            this.iA = true;
            this.iI = new C0511c(this);
            context.registerReceiver(this.iI, new IntentFilter(iK));
            m5907if(context, 0);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List m5909m(java.lang.String r30) {
        /*
        r29 = this;
        r2 = com.baidu.location.C1976f.getServiceContext();
        r2 = com.baidu.location.C0526i.m2195a(r2);
        r17 = r2.getReadableDatabase();
        r2 = 0;
        if (r17 == 0) goto L_0x0117;
    L_0x000f:
        r3 = 0;
        r4 = java.lang.System.currentTimeMillis();
        r6 = "SELECT b.geofence_id, b.longitude, b.latitude, b.radius, b.coord_type, b.duration_millis, b.is_lac, b.is_cell, b.is_wifi, b.radius_type FROM %s AS a LEFT JOIN %s AS b WHERE (a.geofence_id = b.geofence_id) AND (a.ap = '%s' AND  (b.valid_date + b.duration_millis >= %d) AND b.next_active_time < %d)";
        r7 = 5;
        r7 = new java.lang.Object[r7];	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r8 = 0;
        r9 = "geofence_detail";
        r7[r8] = r9;	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r8 = 1;
        r9 = "geofence";
        r7[r8] = r9;	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r8 = 2;
        r7[r8] = r30;	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r8 = 3;
        r9 = java.lang.Long.valueOf(r4);	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r7[r8] = r9;	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r8 = 4;
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r7[r8] = r4;	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r4 = java.lang.String.format(r6, r7);	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        r5 = 0;
        r0 = r17;
        r12 = r0.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0122, all -> 0x0129 }
        if (r12 == 0) goto L_0x010f;
    L_0x0041:
        r3 = r12.getCount();	 Catch:{ Exception -> 0x0133, all -> 0x0131 }
        if (r3 <= 0) goto L_0x010f;
    L_0x0047:
        r13 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0133, all -> 0x0131 }
        r13.<init>();	 Catch:{ Exception -> 0x0133, all -> 0x0131 }
        r12.moveToFirst();	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "geofence_id";
        r18 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "longitude";
        r19 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "latitude";
        r20 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "radius";
        r21 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "coord_type";
        r22 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "duration_millis";
        r23 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "is_lac";
        r24 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "is_cell";
        r25 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "is_wifi";
        r26 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = "radius_type";
        r27 = r12.getColumnIndex(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
    L_0x008b:
        r0 = r18;
        r3 = r12.getString(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r19;
        r2 = r12.getString(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = java.lang.Float.valueOf(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r4 = r2.floatValue();	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r20;
        r2 = r12.getString(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = java.lang.Float.valueOf(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r6 = r2.floatValue();	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r21;
        r2 = r12.getString(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = java.lang.Float.valueOf(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r28 = r2.floatValue();	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r22;
        r11 = r12.getString(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r23;
        r9 = r12.getLong(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r24;
        r2 = r12.getInt(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        if (r2 == 0) goto L_0x0118;
    L_0x00cf:
        r2 = 1;
        r16 = r2;
    L_0x00d2:
        r0 = r25;
        r2 = r12.getInt(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        if (r2 == 0) goto L_0x011c;
    L_0x00da:
        r2 = 1;
        r15 = r2;
    L_0x00dc:
        r0 = r26;
        r2 = r12.getInt(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        if (r2 == 0) goto L_0x011f;
    L_0x00e4:
        r2 = 1;
        r14 = r2;
    L_0x00e6:
        r0 = r27;
        r8 = r12.getInt(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = new com.baidu.location.ah;	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r4 = (double) r4;	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r6 = (double) r6;	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2.<init>(r3, r4, r6, r8, r9, r11);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        if (r2 == 0) goto L_0x0105;
    L_0x00f5:
        r0 = r28;
        r2.m4579a(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r0 = r16;
        r2.m4586do(r0);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2.m4581a(r15);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2.m4589if(r14);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
    L_0x0105:
        r13.add(r2);	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        r2 = r12.moveToNext();	 Catch:{ Exception -> 0x0136, all -> 0x0131 }
        if (r2 != 0) goto L_0x008b;
    L_0x010e:
        r2 = r13;
    L_0x010f:
        if (r12 == 0) goto L_0x0114;
    L_0x0111:
        r12.close();
    L_0x0114:
        r17.close();
    L_0x0117:
        return r2;
    L_0x0118:
        r2 = 0;
        r16 = r2;
        goto L_0x00d2;
    L_0x011c:
        r2 = 0;
        r15 = r2;
        goto L_0x00dc;
    L_0x011f:
        r2 = 0;
        r14 = r2;
        goto L_0x00e6;
    L_0x0122:
        r4 = move-exception;
    L_0x0123:
        if (r3 == 0) goto L_0x0114;
    L_0x0125:
        r3.close();
        goto L_0x0114;
    L_0x0129:
        r2 = move-exception;
        r12 = r3;
    L_0x012b:
        if (r12 == 0) goto L_0x0130;
    L_0x012d:
        r12.close();
    L_0x0130:
        throw r2;
    L_0x0131:
        r2 = move-exception;
        goto L_0x012b;
    L_0x0133:
        r3 = move-exception;
        r3 = r12;
        goto L_0x0123;
    L_0x0136:
        r2 = move-exception;
        r3 = r12;
        r2 = r13;
        goto L_0x0123;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.aq.m(java.lang.String):java.util.List");
    }

    public void m5910try(Context context) {
        m5908if(context, null);
    }
}
