package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Looper;
import com.amap.api.mapcore.util.dv.C0248a;
import com.amap.api.mapcore.util.fk.C0259a;
import com.amap.api.mapcore.util.fk.C0260b;
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
public abstract class ei {
    private dv f553a;
    private int f554b;
    private fn f555c;
    private fk f556d;

    /* compiled from: LogProcessor */
    class C1601a implements fn {
        final /* synthetic */ ei f4196a;
        private es f4197b;

        C1601a(ei eiVar, es esVar) {
            this.f4196a = eiVar;
            this.f4197b = esVar;
        }

        public void mo1649a(String str) {
            try {
                this.f4197b.m818b(str, ec.m746a(this.f4196a.m780b()));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    protected abstract String mo1646a(List<dv> list);

    protected abstract boolean mo1647a(Context context);

    protected ei(int i) {
        this.f554b = i;
    }

    private void m768d(Context context) {
        try {
            this.f556d = m761b(context, m772a());
        } catch (Throwable th) {
            eb.m742a(th, "LogProcessor", "LogUpDateProcessor");
        }
    }

    void m778a(dv dvVar, Context context, Throwable th, String str, String str2, String str3) {
        m777a(dvVar);
        String d = m766d();
        String a = dn.m619a(context, dvVar);
        String a2 = dl.m601a(context);
        String c = m765c(th);
        if (c != null && !"".equals(c)) {
            int b = m780b();
            StringBuilder stringBuilder = new StringBuilder();
            if (str2 != null) {
                stringBuilder.append("class:").append(str2);
            }
            if (str3 != null) {
                stringBuilder.append(" method:").append(str3).append("$").append("<br/>");
            }
            stringBuilder.append(str);
            String a3 = mo1648a(str);
            String a4 = m754a(a2, a, d, b, c, stringBuilder.toString());
            if (a4 != null && !"".equals(a4)) {
                String a5 = m753a(context, a4);
                String a6 = m772a();
                synchronized (Looper.getMainLooper()) {
                    es esVar = new es(context);
                    m757a(esVar, dvVar.m706a(), a3, b, m759a(context, a3, a6, a5, esVar));
                }
            }
        }
    }

    void m776a(Context context, Throwable th, String str, String str2) {
        List<dv> e = m769e(context);
        if (e != null && e.size() != 0) {
            String a = m774a(th);
            if (a != null && !"".equals(a)) {
                for (dv dvVar : e) {
                    if (m760a(dvVar.m711e(), a)) {
                        m778a(dvVar, context, th, a, str, str2);
                        return;
                    }
                }
                if (a.contains("com.amap.api.col")) {
                    try {
                        m778a(new C0248a("collection", "1.0", "AMap_collection_1.0").m700a(new String[]{"com.amap.api.collection"}).m701a(), context, th, a, str, str2);
                    } catch (dk e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    void m781b(Context context) {
        List e = m769e(context);
        if (e != null && e.size() != 0) {
            String a = mo1646a(e);
            if (a != null && !"".equals(a)) {
                String d = m766d();
                String a2 = dn.m619a(context, this.f553a);
                int b = m780b();
                String a3 = m754a(dl.m601a(context), a2, d, b, "ANR", a);
                if (a3 != null && !"".equals(a3)) {
                    String a4 = mo1648a(a);
                    String a5 = m753a(context, a3);
                    String a6 = m772a();
                    synchronized (Looper.getMainLooper()) {
                        es esVar = new es(context);
                        m757a(esVar, this.f553a.m706a(), a4, b, m759a(context, a4, a6, a5, esVar));
                    }
                }
            }
        }
    }

    protected void m777a(dv dvVar) {
        this.f553a = dvVar;
    }

    private List<dv> m769e(Context context) {
        List<dv> a;
        Throwable th;
        Throwable th2;
        Throwable th3;
        List<dv> list = null;
        try {
            synchronized (Looper.getMainLooper()) {
                try {
                    a = new eu(context, false).m829a();
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

    private void m757a(es esVar, String str, String str2, int i, boolean z) {
        et b = ec.m750b(i);
        b.m822a(0);
        b.m826b(str);
        b.m823a(str2);
        esVar.m815a(b);
    }

    protected String mo1648a(String str) {
        return ds.m683c(str);
    }

    protected fn m771a(es esVar) {
        try {
            if (this.f555c == null) {
                this.f555c = new C1601a(this, esVar);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.f555c;
    }

    private String m754a(String str, String str2, String str3, int i, String str4, String str5) {
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

    private String m753a(Context context, String str) {
        String str2 = null;
        try {
            str2 = dn.m629e(context, dx.m721a(str));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str2;
    }

    private String m766d() {
        return dx.m712a(new Date().getTime());
    }

    protected String m774a(Throwable th) {
        String str = null;
        try {
            str = m762b(th);
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        return str;
    }

    private String m765c(Throwable th) {
        return th.toString();
    }

    private boolean m759a(Context context, String str, String str2, String str3, es esVar) {
        boolean z;
        Throwable th;
        Throwable th2;
        OutputStream outputStream = null;
        fk fkVar = null;
        C0260b c0260b = null;
        try {
            File file = new File(ec.m747a(context, str2));
            if (file.exists() || file.mkdirs()) {
                fkVar = fk.m914a(file, 1, 1, 20480);
                fkVar.m935a(m771a(esVar));
                c0260b = fkVar.m934a(str);
                if (c0260b != null) {
                    z = false;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Throwable th3) {
                            th3.printStackTrace();
                        }
                    }
                    if (c0260b != null) {
                        try {
                            c0260b.close();
                        } catch (Throwable th4) {
                            th4.printStackTrace();
                        }
                    }
                    if (fkVar == null || fkVar.m936a()) {
                        return false;
                    }
                    try {
                        fkVar.close();
                        return false;
                    } catch (Throwable th5) {
                        th4 = th5;
                        th4.printStackTrace();
                        return z;
                    }
                }
                byte[] a = dx.m721a(str3);
                C0259a b = fkVar.m937b(str);
                outputStream = b.m894a(0);
                outputStream.write(a);
                b.m895a();
                fkVar.m938b();
                z = true;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Throwable th32) {
                        th32.printStackTrace();
                    }
                }
                if (c0260b != null) {
                    try {
                        c0260b.close();
                    } catch (Throwable th42) {
                        th42.printStackTrace();
                    }
                }
                if (fkVar == null || fkVar.m936a()) {
                    return true;
                }
                try {
                    fkVar.close();
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
            if (c0260b != null) {
                try {
                    c0260b.close();
                } catch (Throwable th422) {
                    th422.printStackTrace();
                }
            }
            if (fkVar == null || fkVar.m936a()) {
                return false;
            }
            try {
                fkVar.close();
                return false;
            } catch (Throwable th7) {
                th422 = th7;
            }
            return false;
            if (c0260b != null) {
                c0260b.close();
            }
            if (!(fkVar == null || fkVar.m936a())) {
                fkVar.close();
            }
            return false;
            if (c0260b != null) {
                c0260b.close();
            }
            if (!(fkVar == null || fkVar.m936a())) {
                fkVar.close();
            }
            return false;
            fkVar.close();
            return false;
            fkVar.close();
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

    public static boolean m760a(String[] strArr, String str) {
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
    void m783c(android.content.Context r6) {
        /*
        r5 = this;
        r5.m768d(r6);	 Catch:{ Throwable -> 0x0035 }
        r0 = r5.mo1647a(r6);	 Catch:{ Throwable -> 0x0035 }
        if (r0 != 0) goto L_0x000a;
    L_0x0009:
        return;
    L_0x000a:
        r1 = android.os.Looper.getMainLooper();	 Catch:{ Throwable -> 0x0035 }
        monitor-enter(r1);	 Catch:{ Throwable -> 0x0035 }
        r0 = new com.amap.api.mapcore.util.es;	 Catch:{ all -> 0x0032 }
        r0.<init>(r6);	 Catch:{ all -> 0x0032 }
        r2 = r5.m780b();	 Catch:{ all -> 0x0032 }
        r5.m756a(r0, r2);	 Catch:{ all -> 0x0032 }
        r2 = 0;
        r3 = r5.m780b();	 Catch:{ all -> 0x0032 }
        r3 = com.amap.api.mapcore.util.ec.m746a(r3);	 Catch:{ all -> 0x0032 }
        r2 = r0.m814a(r2, r3);	 Catch:{ all -> 0x0032 }
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
        com.amap.api.mapcore.util.eb.m742a(r0, r1, r2);
        goto L_0x0009;
    L_0x003e:
        r3 = r5.m755a(r2, r6);	 Catch:{ all -> 0x0032 }
        if (r3 != 0) goto L_0x0046;
    L_0x0044:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x0009;
    L_0x0046:
        r3 = r5.m764c(r3);	 Catch:{ all -> 0x0032 }
        r4 = 1;
        if (r3 != r4) goto L_0x0054;
    L_0x004d:
        r3 = r5.m780b();	 Catch:{ all -> 0x0032 }
        r5.m758a(r2, r0, r3);	 Catch:{ all -> 0x0032 }
    L_0x0054:
        monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        goto L_0x0009;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.ei.c(android.content.Context):void");
    }

    private boolean m763b(String str) {
        boolean z = false;
        if (this.f556d != null) {
            try {
                z = this.f556d.m940c(str);
            } catch (Throwable th) {
                eb.m742a(th, "LogUpdateProcessor", "deleteLogData");
            }
        }
        return z;
    }

    protected String m772a() {
        return ec.m752c(this.f554b);
    }

    protected int m780b() {
        return this.f554b;
    }

    private void m756a(es esVar, int i) {
        try {
            m758a(esVar.m814a(2, ec.m746a(i)), esVar, i);
        } catch (Throwable th) {
            eb.m742a(th, "LogProcessor", "processDeleteFail");
        }
    }

    private int m764c(String str) {
        int i = 0;
        try {
            byte[] b = fq.m948a().mo1651b(new ed(dx.m726c(dx.m721a(str))));
            if (b == null) {
                return 0;
            }
            try {
                JSONObject jSONObject = new JSONObject(dx.m715a(b));
                String str2 = "code";
                if (jSONObject.has(str2)) {
                    return jSONObject.getInt(str2);
                }
                return 0;
            } catch (Throwable e) {
                eb.m742a(e, "LogProcessor", "processUpdate");
                return 1;
            }
        } catch (Throwable e2) {
            if (e2.m600b() != 27) {
                i = 1;
            }
            eb.m742a(e2, "LogProcessor", "processUpdate");
            return i;
        }
    }

    private void m758a(List<? extends et> list, es esVar, int i) {
        if (list != null && list.size() > 0) {
            for (et etVar : list) {
                if (m763b(etVar.m824b())) {
                    esVar.m816a(etVar.m824b(), etVar.getClass());
                } else {
                    etVar.m822a(2);
                    esVar.m817b(etVar);
                }
            }
        }
    }

    private String m770f(Context context) {
        String str = null;
        try {
            String a = dn.m617a(context);
            if (!"".equals(a)) {
                str = dn.m625b(context, dx.m721a(a));
            }
        } catch (Throwable th) {
            eb.m742a(th, "LogProcessor", "getPublicInfo");
        }
        return str;
    }

    private String m755a(List<? extends et> list, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"pinfo\":\"").append(m770f(context)).append("\",\"els\":[");
        Object obj = 1;
        for (et etVar : list) {
            Object obj2;
            String d = m767d(etVar.m824b());
            if (d != null) {
                if ("".equals(d)) {
                    obj2 = obj;
                    obj = obj2;
                } else {
                    String str = d + "||" + etVar.m827c();
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

    private String m767d(String str) {
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
            if (this.f556d == null) {
                if (str4 != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e2) {
                        eb.m742a(e2, "LogProcessor", "readLog1");
                    }
                }
                if (str4 != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        str2 = "LogProcessor";
                        str3 = "readLog2";
                        eb.m742a(e, str2, str3);
                        return str4;
                    }
                }
                return str4;
            }
            C0260b a2 = this.f556d.m934a(str);
            if (a2 == null) {
                if (str4 != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable e22) {
                        eb.m742a(e22, "LogProcessor", "readLog1");
                    }
                }
                if (str4 != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e = e4;
                        str2 = "LogProcessor";
                        str3 = "readLog2";
                        eb.m742a(e, str2, str3);
                        return str4;
                    }
                }
                return str4;
            }
            a = a2.m897a(0);
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
                    str4 = dx.m715a(byteArrayOutputStream.toByteArray());
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable e5) {
                            eb.m742a(e5, "LogProcessor", "readLog1");
                        }
                    }
                    if (a != null) {
                        try {
                            a.close();
                        } catch (IOException e6) {
                            e5 = e6;
                            str2 = "LogProcessor";
                            str3 = "readLog2";
                            eb.m742a(e5, str2, str3);
                            return str4;
                        }
                    }
                } catch (Throwable th2) {
                    e5 = th2;
                    try {
                        eb.m742a(e5, "LogProcessor", "readLog");
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Throwable e52) {
                                eb.m742a(e52, "LogProcessor", "readLog1");
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (IOException e7) {
                                e52 = e7;
                                str2 = "LogProcessor";
                                str3 = "readLog2";
                                eb.m742a(e52, str2, str3);
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
                                eb.m742a(e522, "LogProcessor", "readLog1");
                            }
                        }
                        if (a != null) {
                            try {
                                a.close();
                            } catch (Throwable e5222) {
                                eb.m742a(e5222, "LogProcessor", "readLog2");
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

    void m782c() {
        try {
            if (this.f556d != null && !this.f556d.m936a()) {
                this.f556d.close();
            }
        } catch (Throwable e) {
            eb.m742a(e, "LogProcessor", "closeDiskLru");
        } catch (Throwable e2) {
            eb.m742a(e2, "LogProcessor", "closeDiskLru");
        }
    }

    private fk m761b(Context context, String str) {
        fk fkVar = null;
        try {
            File file = new File(ec.m747a(context, str));
            if (file.exists() || file.mkdirs()) {
                fkVar = fk.m914a(file, 1, 1, 20480);
            }
        } catch (Throwable e) {
            eb.m742a(e, "LogProcessor", "initDiskLru");
        } catch (Throwable e2) {
            eb.m742a(e2, "LogProcessor", "initDiskLru");
        }
        return fkVar;
    }

    public static String m762b(Throwable th) {
        String a = dx.m713a(th);
        if (a != null) {
            return a.replaceAll("\n", "<br/>");
        }
        return null;
    }
}
