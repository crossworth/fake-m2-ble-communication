package com.amap.api.mapcore.util;

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
public class dq {
    private static String f506a = "";
    private static boolean f507b = false;
    private static String f508c = "";
    private static String f509d = "";
    private static String f510e = "";
    private static String f511f = "";

    /* compiled from: DeviceInfo */
    static class C0247a extends DefaultHandler {
        C0247a() {
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            if (str2.equals("string") && "UTDID".equals(attributes.getValue("name"))) {
                dq.f507b = true;
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            if (dq.f507b) {
                dq.f506a = new String(cArr, i, i2);
            }
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            dq.f507b = false;
        }
    }

    static String m638a(Context context) {
        try {
            return m664u(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    static String m644b(Context context) {
        String str = "";
        try {
            str = m667x(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return str;
    }

    static int m646c(Context context) {
        int i = -1;
        try {
            i = m668y(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return i;
    }

    static int m647d(Context context) {
        int i = -1;
        try {
            i = m665v(context);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return i;
    }

    static String m648e(Context context) {
        try {
            return m663t(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static void m641a() {
        try {
            if (VERSION.SDK_INT > 14) {
                TrafficStats.class.getDeclaredMethod("setThreadStatsTag", new Class[]{Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(40964)});
            }
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "setTraficTag");
        }
    }

    public static String m649f(Context context) {
        try {
            if (f506a != null && !"".equals(f506a)) {
                return f506a;
            }
            if (m642a(context, "android.permission.WRITE_SETTINGS")) {
                f506a = System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
            }
            if (!(f506a == null || "".equals(f506a))) {
                return f506a;
            }
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                    if (file.exists()) {
                        SAXParserFactory.newInstance().newSAXParser().parse(file, new C0247a());
                    }
                }
            } catch (Throwable th) {
                eb.m742a(th, "DeviceInfo", "getUTDID");
            }
            return f506a;
        } catch (Throwable th2) {
            eb.m742a(th2, "DeviceInfo", "getUTDID");
        }
    }

    private static boolean m642a(Context context, String str) {
        return context != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    static String m650g(Context context) {
        if (context != null) {
            try {
                if (m642a(context, "android.permission.ACCESS_WIFI_STATE")) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        return wifiManager.getConnectionInfo().getBSSID();
                    }
                    return null;
                }
            } catch (Throwable th) {
                eb.m742a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return null;
    }

    static String m651h(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        if (context != null) {
            try {
                if (m642a(context, "android.permission.ACCESS_WIFI_STATE")) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager.isWifiEnabled()) {
                        List scanResults = wifiManager.getScanResults();
                        if (scanResults == null || scanResults.size() == 0) {
                            return stringBuilder.toString();
                        }
                        List a = m640a(scanResults);
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
                eb.m742a(th, "DeviceInfo", "getWifiMacs");
            }
        }
        return stringBuilder.toString();
    }

    public static String m652i(Context context) {
        try {
            if (f508c != null && !"".equals(f508c)) {
                return f508c;
            }
            if (!m642a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return f508c;
            }
            f508c = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            return f508c;
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getDeviceMac");
        }
    }

    static String[] m653j(Context context) {
        try {
            if (m642a(context, "android.permission.READ_PHONE_STATE") && m642a(context, "android.permission.ACCESS_COARSE_LOCATION")) {
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
            eb.m742a(th, "DeviceInfo", "cellInfo");
        }
    }

    static String m654k(Context context) {
        String str = "";
        try {
            if (m642a(context, "android.permission.READ_PHONE_STATE")) {
                String networkOperator = m669z(context).getNetworkOperator();
                if (!TextUtils.isEmpty(networkOperator) || networkOperator.length() >= 3) {
                    str = networkOperator.substring(3);
                }
            }
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getMNC");
        }
        return str;
    }

    public static int m655l(Context context) {
        int i = -1;
        try {
            i = m668y(context);
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getNetWorkType");
        }
        return i;
    }

    public static int m656m(Context context) {
        int i = -1;
        try {
            i = m665v(context);
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getActiveNetWorkType");
        }
        return i;
    }

    public static NetworkInfo m657n(Context context) {
        if (!m642a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return null;
        }
        ConnectivityManager w = m666w(context);
        if (w != null) {
            return w.getActiveNetworkInfo();
        }
        return null;
    }

    static String m658o(Context context) {
        String str = null;
        try {
            NetworkInfo n = m657n(context);
            if (n != null) {
                str = n.getExtraInfo();
            }
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getNetworkExtraInfo");
        }
        return str;
    }

    static String m659p(Context context) {
        try {
            if (f509d != null && !"".equals(f509d)) {
                return f509d;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            f509d = i2 > i ? i + "*" + i2 : i2 + "*" + i;
            return f509d;
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getReslution");
        }
    }

    public static String m660q(Context context) {
        try {
            if (f510e != null && !"".equals(f510e)) {
                return f510e;
            }
            if (!m642a(context, "android.permission.READ_PHONE_STATE")) {
                return f510e;
            }
            f510e = m669z(context).getDeviceId();
            if (f510e == null) {
                f510e = "";
            }
            return f510e;
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getDeviceID");
        }
    }

    public static String m661r(Context context) {
        String str = "";
        try {
            str = m663t(context);
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getSubscriberId");
        }
        return str;
    }

    static String m662s(Context context) {
        try {
            return m664u(context);
        } catch (Throwable th) {
            eb.m742a(th, "DeviceInfo", "getNetworkOperatorName");
            return "";
        }
    }

    private static String m663t(Context context) {
        if (f511f != null && !"".equals(f511f)) {
            return f511f;
        }
        if (!m642a(context, "android.permission.READ_PHONE_STATE")) {
            return f511f;
        }
        f511f = m669z(context).getSubscriberId();
        if (f511f == null) {
            f511f = "";
        }
        return f511f;
    }

    private static String m664u(Context context) {
        if (!m642a(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        String simOperatorName = m669z(context).getSimOperatorName();
        if (TextUtils.isEmpty(simOperatorName)) {
            return m669z(context).getNetworkOperatorName();
        }
        return simOperatorName;
    }

    private static int m665v(Context context) {
        if (context == null || !m642a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            return -1;
        }
        ConnectivityManager w = m666w(context);
        if (w == null) {
            return -1;
        }
        NetworkInfo activeNetworkInfo = w.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.getType();
        }
        return -1;
    }

    private static ConnectivityManager m666w(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    private static String m667x(Context context) {
        String str = "";
        String r = m661r(context);
        if (r == null || r.length() < 5) {
            return str;
        }
        return r.substring(3, 5);
    }

    private static int m668y(Context context) {
        if (m642a(context, "android.permission.READ_PHONE_STATE")) {
            return m669z(context).getNetworkType();
        }
        return -1;
    }

    private static TelephonyManager m669z(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    private static List<ScanResult> m640a(List<ScanResult> list) {
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
