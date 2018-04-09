package com.droi.sdk.analytics;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.os.SystemClock;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.tencent.stat.DeviceInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

class C0777m {
    private static Map<String, C0776l> m2386a(Context context) {
        List installedPackages = context.getPackageManager().getInstalledPackages(0);
        Map<String, C0776l> hashMap = new HashMap();
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                C0776l c0776l = new C0776l();
                int i2 = packageInfo.applicationInfo.uid;
                c0776l.f2340b = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                c0776l.f2341c = packageInfo.packageName;
                c0776l.f2342d = packageInfo.versionName;
                c0776l.f2339a = packageInfo.versionCode;
                c0776l.f2343e = TrafficStats.getUidTxBytes(i2);
                c0776l.f2344f = TrafficStats.getUidRxBytes(i2);
                hashMap.put(c0776l.f2341c, c0776l);
            }
        }
        return hashMap;
    }

    private static Map<String, C0776l> m2387a(Context context, Map<String, C0776l> map) {
        int i = 0;
        Map<String, C0776l> hashMap = new HashMap();
        long b = C0755c.m2329b() - SystemClock.elapsedRealtime();
        long c = C0777m.m2395c(context);
        C0777m.m2394b(context, b);
        int i2 = Math.abs(c - b) > 1000 ? 1 : 0;
        List installedPackages = context.getPackageManager().getInstalledPackages(0);
        while (i < installedPackages.size()) {
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                C0776l c0776l = new C0776l();
                int i3 = packageInfo.applicationInfo.uid;
                c0776l.f2340b = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                c0776l.f2341c = packageInfo.packageName;
                c0776l.f2342d = packageInfo.versionName;
                c0776l.f2339a = packageInfo.versionCode;
                C0776l c0776l2 = (C0776l) map.get(c0776l.f2341c);
                if (c0776l2 == null || i2 == 0) {
                    c0776l.f2343e = TrafficStats.getUidTxBytes(i3);
                    c0776l.f2344f = TrafficStats.getUidRxBytes(i3);
                } else {
                    c0776l.f2343e = TrafficStats.getUidTxBytes(i3) + c0776l2.f2343e;
                    c0776l.f2344f = TrafficStats.getUidRxBytes(i3) + c0776l2.f2344f;
                }
                hashMap.put(c0776l.f2341c, c0776l);
            }
            i++;
        }
        return hashMap;
    }

    protected static void m2388a() {
        long b = C0755c.m2329b();
        long b2 = C0777m.m2391b(C0770f.f2324a);
        Map b3 = C0777m.m2392b();
        b3 = b3 == null ? C0777m.m2386a(C0770f.f2324a) : C0777m.m2387a(C0770f.f2324a, b3);
        if (b3 != null) {
            if (b2 == 0 || Math.abs(b - b2) >= 604800000) {
                JSONArray b4 = C0777m.m2393b(b3);
                if (b4 != null) {
                    JSONObject jSONObject = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        b2 = C0755c.m2329b();
                        jSONObject2.put("t", b2);
                        String deviceId = AnalyticsCoreHelper.getDeviceId();
                        if (deviceId == null) {
                            C0777m.m2390a(b3);
                            return;
                        }
                        jSONObject2.put("did", deviceId);
                        jSONObject2.put("ai", b4);
                        jSONObject.put("mt", "m08");
                        jSONObject.put(DeviceInfo.TAG_MAC, jSONObject2);
                        C0770f.m2356b(new C0775k(1, 0, 4, "m08").toString(), jSONObject.toString());
                        C0777m.m2389a(C0770f.f2324a, b2);
                        C0777m.m2396c();
                        return;
                    } catch (Exception e) {
                        C0753a.m2311a("OtherAppsInfoManager", e);
                        return;
                    }
                }
                return;
            }
            C0777m.m2390a(b3);
        }
    }

    private static void m2389a(Context context, long j) {
        String str = "last_send_time";
        try {
            C0778n.m2397a("global_flag").m2403b(str, j);
        } catch (Exception e) {
        }
        new C0779o(context).m2404a(str, j);
    }

    private static void m2390a(Map<String, C0776l> map) {
        FileOutputStream fileOutputStream;
        Exception e;
        FileOutputStream fileOutputStream2;
        Throwable th;
        ObjectOutputStream objectOutputStream = null;
        ObjectOutputStream objectOutputStream2;
        try {
            File file = new File(C0774j.m2382b(), "OtherAppInfo.log");
            C0774j.m2385c(file.getAbsolutePath());
            fileOutputStream = new FileOutputStream(file);
            try {
                objectOutputStream2 = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream2.writeObject(map);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            C0753a.m2311a("OtherAppsInfoManager", e2);
                            return;
                        }
                    }
                    if (objectOutputStream2 != null) {
                        objectOutputStream2.close();
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    fileOutputStream2 = fileOutputStream;
                    try {
                        C0753a.m2311a("OtherAppsInfoManager", e2);
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (Exception e22) {
                                C0753a.m2311a("OtherAppsInfoManager", e22);
                                return;
                            }
                        }
                        if (objectOutputStream2 == null) {
                            objectOutputStream2.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        objectOutputStream = objectOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e4) {
                                C0753a.m2311a("OtherAppsInfoManager", e4);
                                throw th;
                            }
                        }
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    objectOutputStream = objectOutputStream2;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e22 = e5;
                objectOutputStream2 = null;
                fileOutputStream2 = fileOutputStream;
                C0753a.m2311a("OtherAppsInfoManager", e22);
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                if (objectOutputStream2 == null) {
                    objectOutputStream2.close();
                }
            } catch (Throwable th4) {
                th = th4;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e22 = e6;
            objectOutputStream2 = null;
            C0753a.m2311a("OtherAppsInfoManager", e22);
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (objectOutputStream2 == null) {
                objectOutputStream2.close();
            }
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            throw th;
        }
    }

    private static long m2391b(Context context) {
        long a;
        String str = "last_send_time";
        try {
            a = C0778n.m2397a("global_flag").m2402a(str, 0);
        } catch (Exception e) {
            a = 0;
        }
        return a == 0 ? new C0779o(context).m2406b(str, 0) : a;
    }

    private static Map<String, C0776l> m2392b() {
        try {
            String str = C0774j.m2380a() + File.separator + "OtherAppInfo.log";
            if (!C0774j.m2383b(str)) {
                return null;
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(str));
            HashMap hashMap = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            return hashMap;
        } catch (Exception e) {
            C0753a.m2311a("OtherAppsInfoManager", e);
            return null;
        } catch (Exception e2) {
            C0753a.m2311a("OtherAppsInfoManager", e2);
            return null;
        }
    }

    private static JSONArray m2393b(Map<String, C0776l> map) {
        JSONArray jSONArray = new JSONArray();
        for (Entry entry : map.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("pn", ((C0776l) entry.getValue()).f2341c);
                jSONObject.put("an", C0755c.m2332b(((C0776l) entry.getValue()).f2340b));
                jSONObject.put("vc", ((C0776l) entry.getValue()).f2339a);
                jSONObject.put("vn", ((C0776l) entry.getValue()).f2342d);
                jSONObject.put("tx", ((C0776l) entry.getValue()).f2343e);
                jSONObject.put("rx", ((C0776l) entry.getValue()).f2344f);
                if (jSONObject != null) {
                    jSONArray.put(jSONObject);
                }
            } catch (Exception e) {
                C0753a.m2311a("OtherAppsInfoManager", e);
                return null;
            }
        }
        return jSONArray;
    }

    private static void m2394b(Context context, long j) {
        String str = "last_boot_time";
        C0779o c0779o = new C0779o(context);
        try {
            C0778n.m2397a("global_flag").m2403b(str, j);
        } catch (Exception e) {
        }
        c0779o.m2404a(str, j);
    }

    private static long m2395c(Context context) {
        String str = "last_boot_time";
        long b = new C0779o(context).m2406b(str, 0);
        try {
            C0778n a = C0778n.m2397a("global_flag");
            if (b == 0) {
                b = a.m2402a(str, 0);
            }
        } catch (Exception e) {
        }
        return b;
    }

    private static void m2396c() {
        try {
            C0774j.m2385c(C0774j.m2380a() + File.separator + "OtherAppInfo.log");
        } catch (Exception e) {
            C0753a.m2311a("OtherAppsInfoManager", e);
        }
    }
}
