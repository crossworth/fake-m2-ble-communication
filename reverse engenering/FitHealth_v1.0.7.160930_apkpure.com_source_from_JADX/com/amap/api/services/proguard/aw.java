package com.amap.api.services.proguard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.File;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: DeviceInfo */
public class aw {
    private static String f1369a = "";
    private static boolean f1370b = false;
    private static String f1371c = "";
    private static String f1372d = "";
    private static String f1373e = "";
    private static String f1374f = "";

    /* compiled from: DeviceInfo */
    static class C0367a extends DefaultHandler {
        C0367a() {
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals("string") && "UTDID".equals(attributes.getValue("name"))) {
                aw.f1370b = true;
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (aw.f1370b) {
                aw.f1369a = new String(cArr, i, i2);
            }
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            aw.f1370b = false;
        }
    }

    static String m1239a(Context context) {
        try {
            return m1265u(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    static String m1245b(Context context) {
        String str = "";
        try {
            str = m1268x(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str;
    }

    static int m1247c(Context context) {
        int i = -1;
        try {
            i = m1269y(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return i;
    }

    static int m1248d(Context context) {
        int i = -1;
        try {
            i = m1266v(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return i;
    }

    static String m1249e(Context context) {
        try {
            return m1264t(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static void m1242a() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "setTraficTag");
        }
    }

    public static String m1250f(Context context) {
        try {
            if (f1369a != null && !"".equals(f1369a)) {
                return f1369a;
            }
            if (m1243a(context, "android.permission.WRITE_SETTINGS")) {
                f1369a = System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (!(f1369a == null || "".equals(f1369a))) {
                return f1369a;
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                    if (file.exists()) {
                        SAXParserFactory.newInstance().newSAXParser().parse(file, new C0367a());
                    }
                }
            } catch (Throwable th) {
                be.m1340a(th, "DeviceInfo", "getUTDID");
            }
            return f1369a;
        } catch (Throwable th2) {
            be.m1340a(th2, "DeviceInfo", "getUTDID");
        }
    }

    private static boolean m1243a(Context context, String str) {
        return context != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    static String m1251g(Context context) {
        if (context != null) {
            try {
                if (m1243a(context, "android.permission.ACCESS_WIFI_STATE")) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        return wifiManager.getConnectionInfo().getBSSID();
                    }
                    return null;
                }
            } catch (Throwable th) {
                be.m1340a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return null;
    }

    static String m1252h(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        if (context != null) {
            try {
                if (m1243a(context, "android.permission.ACCESS_WIFI_STATE")) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        List scanResults = wifiManager.getScanResults();
                        if (scanResults == null || scanResults.size() == 0) {
                            return stringBuilder.toString();
                        }
                        List a = m1241a(scanResults);
                        Object obj = 1;
                        int i = 0;
                        while (i < a.size() && i < 7) {
                            ScanResult scanResult = (ScanResult) a.get(i);
                            if (obj != null) {
                                obj = null;
                            } else {
                                stringBuilder.append(";");
                            }
                            stringBuilder.append(scanResult.BSSID);
                            i++;
                        }
                    }
                    return stringBuilder.toString();
                }
            } catch (Throwable th) {
                be.m1340a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return stringBuilder.toString();
    }

    public static String m1253i(Context context) {
        try {
            if (f1371c != null && !"".equals(f1371c)) {
                return f1371c;
            }
            if (!m1243a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return f1371c;
            }
            f1371c = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return f1371c;
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] m1254j(Context context) {
        try {
            if (m1243a(context, "android.permission.READ_PHONE_STATE") && m1243a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                CellLocation cellLocation = ((TelephonyManager) context.getSystemService("phone")).getCellLocation();
                int cid;
                int lac;
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    cid = gsmCellLocation.getCid();
                    lac = gsmCellLocation.getLac();
                    return new String[]{lac + "||" + cid, "gsm"};
                }
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                    cid = cdmaCellLocation.getSystemId();
                    lac = cdmaCellLocation.getNetworkId();
                    int baseStationId = cdmaCellLocation.getBaseStationId();
                    if (cid < 0 || lac < 0 || baseStationId < 0) {
                    }
                    return new String[]{cid + "||" + lac + "||" + baseStationId, "cdma"};
                }
                return new String[]{"", ""};
            }
            return new String[]{"", ""};
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "cellInfo");
        }
    }

    static String m1255k(Context context) {
        String str = "";
        try {
            if (m1243a(context, "android.permission.READ_PHONE_STATE")) {
                String networkOperator = m1270z(context).getNetworkOperator();
                if (!TextUtils.isEmpty(networkOperator) || networkOperator.length() >= 3) {
                    str = networkOperator.substring(3);
                }
            }
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getMNC");
        }
        return str;
    }

    public static int m1256l(Context context) {
        int i = -1;
        try {
            i = m1269y(context);
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getNetWorkType");
        }
        return i;
    }

    public static int m1257m(Context context) {
        int i = -1;
        try {
            i = m1266v(context);
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getActiveNetWorkType");
        }
        return i;
    }

    public static NetworkInfo m1258n(Context context) {
        if (!m1243a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return null;
        }
        ConnectivityManager w = m1267w(context);
        if (w != null) {
            return w.getActiveNetworkInfo();
        }
        return null;
    }

    static String m1259o(Context context) {
        String str = null;
        try {
            NetworkInfo n = m1258n(context);
            if (n != null) {
                str = n.getExtraInfo();
            }
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getNetworkExtraInfo");
        }
        return str;
    }

    static String m1260p(Context context) {
        try {
            if (f1372d != null && !"".equals(f1372d)) {
                return f1372d;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            f1372d = i2 > i ? i + "*" + i2 : i2 + "*" + i;
            return f1372d;
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getReslution");
        }
    }

    public static String m1261q(Context context) {
        try {
            if (f1373e != null && !"".equals(f1373e)) {
                return f1373e;
            }
            if (!m1243a(context, "android.permission.READ_PHONE_STATE")) {
                return f1373e;
            }
            f1373e = m1270z(context).getDeviceId();
            if (f1373e == null) {
                f1373e = "";
            }
            return f1373e;
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String m1262r(Context context) {
        String str = "";
        try {
            str = m1264t(context);
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getSubscriberId");
        }
        return str;
    }

    static String m1263s(Context context) {
        try {
            return m1265u(context);
        } catch (Throwable th) {
            be.m1340a(th, "DeviceInfo", "getNetworkOperatorName");
            return "";
        }
    }

    private static String m1264t(Context context) {
        if (f1374f != null && !"".equals(f1374f)) {
            return f1374f;
        }
        if (!m1243a(context, "android.permission.READ_PHONE_STATE")) {
            return f1374f;
        }
        f1374f = m1270z(context).getSubscriberId();
        if (f1374f == null) {
            f1374f = "";
        }
        return f1374f;
    }

    private static String m1265u(Context context) {
        if (!m1243a(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        String simOperatorName = m1270z(context).getSimOperatorName();
        if (TextUtils.isEmpty(simOperatorName)) {
            return m1270z(context).getNetworkOperatorName();
        }
        return simOperatorName;
    }

    private static int m1266v(Context context) {
        if (context == null || !m1243a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return -1;
        }
        ConnectivityManager w = m1267w(context);
        if (w == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = w.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }
        return -1;
    }

    private static ConnectivityManager m1267w(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    private static String m1268x(Context context) {
        String str = "";
        String r = m1262r(context);
        if (r == null || r.length() < 5) {
            return str;
        }
        return r.substring(3, 5);
    }

    private static int m1269y(Context context) {
        if (m1243a(context, "android.permission.READ_PHONE_STATE")) {
            return m1270z(context).getNetworkType();
        }
        return -1;
    }

    private static TelephonyManager m1270z(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    private static List<ScanResult> m1241a(List<ScanResult> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int i2 = 1; i2 < size - i; i2++) {
                if (((ScanResult) list.get(i2 - 1)).level > ((ScanResult) list.get(i2)).level) {
                    ScanResult scanResult = (ScanResult) list.get(i2 - 1);
                    list.set(i2 - 1, list.get(i2));
                    list.set(i2, scanResult);
                }
            }
        }
        return list;
    }
}
