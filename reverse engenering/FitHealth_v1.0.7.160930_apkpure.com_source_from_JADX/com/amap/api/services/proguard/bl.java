package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Looper;
import com.amap.api.services.proguard.ba.C0369a;
import com.amap.api.services.proguard.cm.C0381a;
import com.amap.api.services.proguard.cm.C0382b;
import com.umeng.socialize.editorpage.ShareActivity;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

/* compiled from: LogProcessor */
public abstract class bl {
    private ba f1415a;
    private int f1416b;
    private cn f1417c;
    private cm f1418d;

    /* compiled from: LogProcessor */
    class C1610a implements cn {
        final /* synthetic */ bl f4359a;
        private bv f4360b;

        C1610a(bl blVar, bv bvVar) {
            this.f4359a = blVar;
            this.f4360b = bvVar;
        }

        public void mo1766a(String str) {
            try {
                this.f4360b.m1415b(str, bf.m1344a(this.f4359a.m1378b()));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    protected abstract String mo1763a(List<ba> list);

    protected abstract boolean mo1764a(Context context);

    protected bl(int i) {
        this.f1416b = i;
    }

    private void m1366d(Context context) {
        try {
            this.f1418d = m1359b(context, m1370a());
        } catch (Throwable th) {
            be.m1340a(th, "LogProcessor", "LogUpDateProcessor");
        }
    }

    void m1376a(ba baVar, Context context, Throwable th, String str, String str2, String str3) {
        m1375a(baVar);
        String d = m1364d();
        String a = av.m1226a(context, baVar);
        String a2 = as.m1209a(context);
        String c = m1363c(th);
        if (c != null && !"".equals(c)) {
            int b = m1378b();
            StringBuilder stringBuilder = new StringBuilder();
            if (str2 != null) {
                stringBuilder.append("class:").append(str2);
            }
            if (str3 != null) {
                stringBuilder.append(" method:").append(str3).append("$").append("<br/>");
            }
            stringBuilder.append(str);
            String a3 = mo1765a(str);
            String a4 = m1352a(a2, a, d, b, c, stringBuilder.toString());
            if (a4 != null && !"".equals(a4)) {
                String a5 = m1351a(context, a4);
                String a6 = m1370a();
                synchronized (Looper.getMainLooper()) {
                    bv bvVar = new bv(context);
                    m1355a(bvVar, baVar.m1308a(), a3, b, m1357a(context, a3, a6, a5, bvVar));
                }
            }
        }
    }

    void m1374a(Context context, Throwable th, String str, String str2) {
        List<ba> e = m1367e(context);
        if (e != null && e.size() != 0) {
            String a = m1372a(th);
            if (a != null && !"".equals(a)) {
                for (ba baVar : e) {
                    if (m1358a(baVar.m1311d(), a)) {
                        m1376a(baVar, context, th, a, str, str2);
                        return;
                    }
                }
                if (a.contains("com.amap.api.col")) {
                    try {
                        m1376a(new C0369a("collection", "1.0", "AMap_collection_1.0").m1302a(new String[]{"com.amap.api.collection"}).m1303a(), context, th, a, str, str2);
                    } catch (ar e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    void m1379b(Context context) {
        List e = m1367e(context);
        if (e != null && e.size() != 0) {
            String a = mo1763a(e);
            if (a != null && !"".equals(a)) {
                String d = m1364d();
                String a2 = av.m1226a(context, this.f1415a);
                int b = m1378b();
                String a3 = m1352a(as.m1209a(context), a2, d, b, "ANR", a);
                if (a3 != null && !"".equals(a3)) {
                    String a4 = mo1765a(a);
                    String a5 = m1351a(context, a3);
                    String a6 = m1370a();
                    synchronized (Looper.getMainLooper()) {
                        bv bvVar = new bv(context);
                        m1355a(bvVar, this.f1415a.m1308a(), a4, b, m1357a(context, a4, a6, a5, bvVar));
                    }
                }
            }
        }
    }

    protected void m1375a(ba baVar) {
        this.f1415a = baVar;
    }

    private List<ba> m1367e(Context context) {
        List<ba> a;
        Throwable th;
        Throwable th2;
        Throwable th3;
        List<ba> list = null;
        try {
            synchronized (Looper.getMainLooper()) {
                try {
                    a = new bx(context, false).m1426a();
                    try {
                    } catch (Throwable th22) {
                        th = th22;
                        list = a;
                        th3 = th;
                        try {
                            throw th3;
                        } catch (Throwable th32) {
                            th = th32;
                            a = list;
                            th22 = th;
                        }
                    }
                } catch (Throwable th4) {
                    th32 = th4;
                    throw th32;
                }
            }
        } catch (Throwable th322) {
            th = th322;
            a = null;
            th22 = th;
            th22.printStackTrace();
            return a;
        }
    }

    private void m1355a(bv bvVar, String str, String str2, int i, boolean z) {
        bw b = bf.m1348b(i);
        b.m1419a(0);
        b.m1423b(str);
        b.m1420a(str2);
        bvVar.m1412a(b);
    }

    protected String mo1765a(String str) {
        return ay.m1284c(str);
    }

    protected cn m1369a(bv bvVar) {
        try {
            if (this.f1417c == null) {
                this.f1417c = new C1610a(this, bvVar);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.f1417c;
    }

    private String m1352a(String str, String str2, String str3, int i, String str4, String str5) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str2).append(SeparatorConstants.SEPARATOR_ADS_ID).append("\"timestamp\":\"");
        stringBuffer.append(str3);
        stringBuffer.append("\",\"et\":\"");
        stringBuffer.append(i);
        stringBuffer.append("\",\"classname\":\"");
        stringBuffer.append(str4);
        stringBuffer.append("\",");
        stringBuffer.append("\"detail\":\"");
        stringBuffer.append(str5);
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }

    private String m1351a(Context context, String str) {
        String str2 = null;
        try {
            str2 = av.m1238e(context, bb.m1321a(str));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str2;
    }

    private String m1364d() {
        return bb.m1312a(new Date().getTime());
    }

    protected String m1372a(Throwable th) {
        String str = null;
        try {
            str = m1360b(th);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        return str;
    }

    private String m1363c(Throwable th) {
        return th.toString();
    }

    private boolean m1357a(Context context, String str, String str2, String str3, bv bvVar) {
        boolean z;
        Throwable th;
        Throwable th2;
        OutputStream outputStream = null;
        cm cmVar = null;
        C0382b c0382b = null;
        try {
            File file = new File(bf.m1345a(context, str2));
            if (file.exists() || file.mkdirs()) {
                cmVar = cm.m1512a(file, 1, 1, 20480);
                cmVar.m1533a(m1369a(bvVar));
                c0382b = cmVar.m1532a(str);
                if (c0382b != null) {
                    z = false;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th3) {
                            th3.printStackTrace();
                        }
                    }
                    if (c0382b != null) {
                        try {
                            c0382b.close();
                        } catch (Throwable th4) {
                            th4.printStackTrace();
                        }
                    }
                    if (cmVar == null || cmVar.m1534a()) {
                        return false;
                    }
                    try {
                        cmVar.close();
                        return false;
                    } catch (Throwable th5) {
                        th4 = th5;
                        th4.printStackTrace();
                        return z;
                    }
                }
                byte[] a = bb.m1321a(str3);
                C0381a b = cmVar.m1535b(str);
                outputStream = b.m1492a(0);
                outputStream.write(a);
                b.m1493a();
                cmVar.m1536b();
                z = true;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Throwable th32) {
                        th32.printStackTrace();
                    }
                }
                if (c0382b != null) {
                    try {
                        c0382b.close();
                    } catch (Throwable th42) {
                        th42.printStackTrace();
                    }
                }
                if (cmVar == null || cmVar.m1534a()) {
                    return true;
                }
                try {
                    cmVar.close();
                    return true;
                } catch (Throwable th6) {
                    th42 = th6;
                    th42.printStackTrace();
                    return z;
                }
            }
            z = false;
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable th322) {
                    th322.printStackTrace();
                }
            }
            if (c0382b != null) {
                try {
                    c0382b.close();
                } catch (Throwable th422) {
                    th422.printStackTrace();
                }
            }
            if (cmVar == null || cmVar.m1534a()) {
                return false;
            }
            try {
                cmVar.close();
                return false;
            } catch (Throwable th7) {
                th422 = th7;
            }
            return false;
            if (c0382b != null) {
                c0382b.close();
            }
            if (!(cmVar == null || cmVar.m1534a())) {
                cmVar.close();
            }
            return false;
            if (c0382b != null) {
                c0382b.close();
            }
            if (!(cmVar == null || cmVar.m1534a())) {
                cmVar.close();
            }
            return false;
            cmVar.close();
            return false;
            cmVar.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Throwable th8) {
            th2 = th8;
            th2.printStackTrace();
        }
    }

    public static boolean m1358a(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        try {
            String[] split = str.split("<br/>");
            for (CharSequence charSequence : strArr) {
                for (String str2 : split) {
                    if (str2.contains(ShareActivity.KEY_AT) && str2.contains(charSequence)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void m1381c(android.content.Context r6) {
        /*
        r5 = this;
        r5.m1366d(r6);	 Catch:{ Throwable -> 0x0035 }
        r0 = r5.mo1764a(r6);	 Catch:{ Throwable -> 0x0035 }
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        r1 = android.os.Looper.getMainLooper();	 Catch:{ Throwable -> 0x0035 }
        monitor-enter(r1);	 Catch:{ Throwable -> 0x0035 }
        r0 = new com.amap.api.services.proguard.bv;	 Catch:{ all -> 0x0032 }
        r0.<init>(r6);	 Catch:{ all -> 0x0032 }
        r2 = r5.m1378b();	 Catch:{ all -> 0x0032 }
        r5.m1354a(r0, r2);	 Catch:{ all -> 0x0032 }
        r2 = 0;
        r3 = r5.m1378b();	 Catch:{ all -> 0x0032 }
        r3 = com.amap.api.services.proguard.bf.m1344a(r3);	 Catch:{ all -> 0x0032 }
        r2 = r0.m1411a(r2, r3);	 Catch:{ all -> 0x0032 }
        if (r2 == 0) goto L_0x0030;
    L_0x002a:
        r3 = r2.size();	 Catch:{ all -> 0x0032 }
        if (r3 != 0) goto L_0x003e;
    L_0x0030:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x0009;
    L_0x0032:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        throw r0;	 Catch:{ Throwable -> 0x0035 }
    L_0x0035:
        r0 = move-exception;
        r1 = "LogProcessor";
        r2 = "processUpdateLog";
        com.amap.api.services.proguard.be.m1340a(r0, r1, r2);
        goto L_0x0009;
    L_0x003e:
        r3 = r5.m1353a(r2, r6);	 Catch:{ all -> 0x0032 }
        if (r3 != 0) goto L_0x0046;
    L_0x0044:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x0009;
    L_0x0046:
        r3 = r5.m1362c(r3);	 Catch:{ all -> 0x0032 }
        r4 = 1;
        if (r3 != r4) goto L_0x0054;
    L_0x004d:
        r3 = r5.m1378b();	 Catch:{ all -> 0x0032 }
        r5.m1356a(r2, r0, r3);	 Catch:{ all -> 0x0032 }
    L_0x0054:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.services.proguard.bl.c(android.content.Context):void");
    }

    private boolean m1361b(String str) {
        boolean z = false;
        if (this.f1418d != null) {
            try {
                z = this.f1418d.m1538c(str);
            } catch (Throwable th) {
                be.m1340a(th, "LogUpdateProcessor", "deleteLogData");
            }
        }
        return z;
    }

    protected String m1370a() {
        return bf.m1350c(this.f1416b);
    }

    protected int m1378b() {
        return this.f1416b;
    }

    private void m1354a(bv bvVar, int i) {
        try {
            m1356a(bvVar.m1411a(2, bf.m1344a(i)), bvVar, i);
        } catch (Throwable th) {
            be.m1340a(th, "LogProcessor", "processDeleteFail");
        }
    }

    private int m1362c(String str) {
        int i = 0;
        try {
            byte[] b = cq.m1545a().mo1777b(new bg(bb.m1325c(bb.m1321a(str))));
            if (b == null) {
                return 0;
            }
            try {
                JSONObject jSONObject = new JSONObject(bb.m1315a(b));
                String str2 = "code";
                if (jSONObject.has(str2)) {
                    return jSONObject.getInt(str2);
                }
                return 0;
            } catch (Throwable e) {
                be.m1340a(e, "LogProcessor", "processUpdate");
                return 1;
            }
        } catch (Throwable e2) {
            if (e2.m1208b() != 27) {
                i = 1;
            }
            be.m1340a(e2, "LogProcessor", "processUpdate");
            return i;
        }
    }

    private void m1356a(List<? extends bw> list, bv bvVar, int i) {
        if (list != null && list.size() > 0) {
            for (bw bwVar : list) {
                if (m1361b(bwVar.m1421b())) {
                    bvVar.m1413a(bwVar.m1421b(), bwVar.getClass());
                } else {
                    bwVar.m1419a(2);
                    bvVar.m1414b(bwVar);
                }
            }
        }
    }

    private String m1368f(Context context) {
        String str = null;
        try {
            String a = av.m1224a(context);
            if (!"".equals(a)) {
                str = av.m1234b(context, bb.m1321a(a));
            }
        } catch (Throwable th) {
            be.m1340a(th, "LogProcessor", "getPublicInfo");
        }
        return str;
    }

    private String m1353a(List<? extends bw> list, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"pinfo\":\"").append(m1368f(context)).append("\",\"els\":[");
        Object obj = 1;
        for (bw bwVar : list) {
            Object obj2;
            String d = m1365d(bwVar.m1421b());
            if (d != null) {
                if ("".equals(d)) {
                    obj2 = obj;
                    obj = obj2;
                } else {
                    String str = d + "||" + bwVar.m1424c();
                    if (obj != null) {
                        obj = null;
                    } else {
                        stringBuilder.append(SeparatorConstants.SEPARATOR_ADS_ID);
                    }
                    stringBuilder.append("{\"log\":\"").append(str).append("\"}");
                }
            }
            obj2 = obj;
            obj = obj2;
        }
        if (obj != null) {
            return null;
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    private String m1365d(String str) {
        Throwable e;
        String str2;
        String str3;
        InputStream a;
        Throwable th;
        Object obj;
        String str4 = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (this.f1418d == null) {
                if (str4 != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e2) {
                        be.m1340a(e2, "LogProcessor", "readLog1");
                    }
                }
                if (str4 != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        str2 = "LogProcessor";
                        str3 = "readLog2";
                        be.m1340a(e, str2, str3);
                        return str4;
                    }
                }
                return str4;
            }
            C0382b a2 = this.f1418d.m1532a(str);
            if (a2 == null) {
                if (str4 != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e22) {
                        be.m1340a(e22, "LogProcessor", "readLog1");
                    }
                }
                if (str4 != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e = e4;
                        str2 = "LogProcessor";
                        str3 = "readLog2";
                        be.m1340a(e, str2, str3);
                        return str4;
                    }
                }
                return str4;
            }
            a = a2.m1495a(0);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = a.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                    str4 = bb.m1315a(byteArrayOutputStream.toByteArray());
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable e5) {
                            be.m1340a(e5, "LogProcessor", "readLog1");
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (IOException e6) {
                            e5 = e6;
                            str2 = "LogProcessor";
                            str3 = "readLog2";
                            be.m1340a(e5, str2, str3);
                            return str4;
                        }
                    }
                } catch (Throwable th2) {
                    e5 = th2;
                    try {
                        be.m1340a(e5, "LogProcessor", "readLog");
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e52) {
                                be.m1340a(e52, "LogProcessor", "readLog1");
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (IOException e7) {
                                e52 = e7;
                                str2 = "LogProcessor";
                                str3 = "readLog2";
                                be.m1340a(e52, str2, str3);
                                return str4;
                            }
                        }
                        return str4;
                    } catch (Throwable th3) {
                        th = th3;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e522) {
                                be.m1340a(e522, "LogProcessor", "readLog1");
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (Throwable e5222) {
                                be.m1340a(e5222, "LogProcessor", "readLog2");
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable e52222) {
                obj = str4;
                th = e52222;
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (a != null) {
                    a.close();
                }
                throw th;
            }
            return str4;
        } catch (Throwable e522222) {
            byteArrayOutputStream = str4;
            a = str4;
            th = e522222;
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (a != null) {
                a.close();
            }
            throw th;
        }
    }

    void m1380c() {
        try {
            if (this.f1418d != null && !this.f1418d.m1534a()) {
                this.f1418d.close();
            }
        } catch (Throwable e) {
            be.m1340a(e, "LogProcessor", "closeDiskLru");
        } catch (Throwable e2) {
            be.m1340a(e2, "LogProcessor", "closeDiskLru");
        }
    }

    private cm m1359b(Context context, String str) {
        cm cmVar = null;
        try {
            File file = new File(bf.m1345a(context, str));
            if (file.exists() || file.mkdirs()) {
                cmVar = cm.m1512a(file, 1, 1, 20480);
            }
        } catch (Throwable e) {
            be.m1340a(e, "LogProcessor", "initDiskLru");
        } catch (Throwable e2) {
            be.m1340a(e2, "LogProcessor", "initDiskLru");
        }
        return cmVar;
    }

    public static String m1360b(Throwable th) {
        String a = bb.m1313a(th);
        if (a != null) {
            return a.replaceAll("\n", "<br/>");
        }
        return null;
    }
}
