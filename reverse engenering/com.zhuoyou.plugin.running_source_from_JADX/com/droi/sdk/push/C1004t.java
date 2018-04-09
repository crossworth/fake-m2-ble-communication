package com.droi.sdk.push;

import com.droi.sdk.push.utils.C1008c;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class C1004t {
    public long f3327a;
    public int f3328b;
    public String f3329c;
    public long f3330d;
    public long f3331e;
    public long f3332f;
    public int f3333g;
    public String f3334h;
    public String f3335i;
    public boolean f3336j;
    public String f3337k;
    public String f3338l;
    private int f3339m;

    public C1004t(long j, String str, String str2, String str3, long j2, long j3, long j4) {
        JSONObject jSONObject;
        this.f3333g = -1;
        this.f3334h = null;
        this.f3335i = null;
        this.f3336j = false;
        this.f3338l = null;
        this.f3339m = 1;
        this.f3327a = j;
        this.f3329c = str;
        this.f3337k = str2;
        this.f3330d = j2;
        this.f3331e = j3;
        this.f3332f = j4;
        this.f3335i = C1008c.m3126a(str3);
        this.f3336j = true;
        if (j2 < 0) {
            this.f3336j = false;
        } else if (j2 > 0) {
            this.f3339m = 3;
        } else if (j2 == 0) {
            if (this.f3332f > this.f3331e) {
                this.f3339m = 2;
                if (System.currentTimeMillis() > this.f3332f) {
                    this.f3336j = false;
                }
            } else if (j4 < j3) {
                this.f3336j = false;
            } else {
                this.f3336j = j3 == 0;
            }
        }
        try {
            jSONObject = new JSONObject(this.f3335i);
        } catch (Exception e) {
            C1012g.m3140b("PushMsg: create json object failed - " + this.f3335i);
            C1012g.m3139b(e);
            jSONObject = null;
        }
        this.f3328b = C1004t.m3074b(jSONObject);
    }

    public C1004t(String str) {
        JSONObject jSONObject;
        boolean z = true;
        this.f3333g = -1;
        this.f3334h = null;
        this.f3335i = null;
        this.f3336j = false;
        this.f3338l = null;
        this.f3339m = 1;
        this.f3335i = str;
        this.f3336j = true;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            C1012g.m3140b("create 'PushMsg' object with string failed");
            jSONObject = null;
        }
        if (jSONObject != null) {
            this.f3327a = C1004t.m3073a(jSONObject);
            this.f3328b = C1004t.m3074b(jSONObject);
            this.f3329c = C1004t.m3075c(jSONObject);
            if (this.f3328b != 5) {
                this.f3330d = C1004t.m3081i(jSONObject) * 60000;
                long[] t = C1004t.m3092t(jSONObject);
                if (t.length == 2) {
                    this.f3331e = t[0];
                    this.f3332f = t[1];
                } else {
                    this.f3331e = 0;
                    this.f3332f = 0;
                }
                if (this.f3330d < 0) {
                    this.f3336j = false;
                } else if (this.f3330d > 0) {
                    this.f3339m = 3;
                } else if (this.f3330d == 0) {
                    if (this.f3332f > this.f3331e) {
                        this.f3339m = 2;
                        if (System.currentTimeMillis() > this.f3332f) {
                            this.f3336j = false;
                        }
                    } else if (this.f3332f < this.f3331e) {
                        this.f3336j = false;
                    } else {
                        if (this.f3331e != 0) {
                            z = false;
                        }
                        this.f3336j = z;
                    }
                }
            } else {
                this.f3333g = C1004t.m3090r(jSONObject);
                this.f3334h = C1004t.m3091s(jSONObject);
            }
            String str2 = "T";
            if (m3096c() || m3097d()) {
                str2 = "P";
            }
            this.f3337k = str2 + System.currentTimeMillis() + this.f3327a;
        }
    }

    public C1004t(String str, String str2) {
        this(str2);
        this.f3329c = str;
    }

    static long m3073a(JSONObject jSONObject) {
        long j = -1;
        if (jSONObject != null) {
            try {
                j = jSONObject.getLong("mi");
            } catch (JSONException e) {
            }
        }
        return j;
    }

    static int m3074b(JSONObject jSONObject) {
        int i = -1;
        if (jSONObject != null) {
            try {
                i = jSONObject.getInt("ms");
            } catch (JSONException e) {
            }
        }
        return i;
    }

    static String m3075c(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("mk");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3076d(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("mt");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3077e(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("mb");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3078f(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("r");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3079g(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("sc");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3080h(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("sb");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static long m3081i(JSONObject jSONObject) {
        if (jSONObject == null) {
            return 0;
        }
        try {
            long j = jSONObject.getLong("si");
            if (j <= 0) {
                j = 0;
            }
            return j;
        } catch (JSONException e) {
            return 0;
        }
    }

    static boolean m3082j(JSONObject jSONObject) {
        boolean z = true;
        if (jSONObject != null) {
            try {
                z = jSONObject.getBoolean("mf");
            } catch (JSONException e) {
            }
        }
        return z;
    }

    static boolean m3083k(JSONObject jSONObject) {
        boolean z = true;
        if (jSONObject != null) {
            try {
                z = jSONObject.getBoolean(DeviceInfo.TAG_MAC);
            } catch (JSONException e) {
            }
        }
        return z;
    }

    static boolean m3084l(JSONObject jSONObject) {
        boolean z = true;
        if (jSONObject != null) {
            try {
                z = jSONObject.getBoolean("mv");
            } catch (JSONException e) {
            }
        }
        return z;
    }

    static boolean m3085m(JSONObject jSONObject) {
        boolean z = true;
        if (jSONObject != null) {
            try {
                z = jSONObject.getBoolean("mr");
            } catch (JSONException e) {
            }
        }
        return z;
    }

    static int m3086n(JSONObject jSONObject) {
        int i = -1;
        if (jSONObject != null) {
            try {
                i = jSONObject.getInt(SocializeConstants.KEY_AT);
            } catch (JSONException e) {
            }
        }
        return i;
    }

    static String m3087o(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("ad");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3088p(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("ed");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static String m3089q(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("ai");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static int m3090r(JSONObject jSONObject) {
        int i = -1;
        if (jSONObject != null) {
            try {
                i = jSONObject.getInt(SocializeProtocolConstants.PROTOCOL_KEY_COMMENT_TEXT);
            } catch (JSONException e) {
            }
        }
        return i;
    }

    static String m3091s(JSONObject jSONObject) {
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString("cd");
            } catch (JSONException e) {
            }
        }
        return str;
    }

    static long[] m3092t(JSONObject jSONObject) {
        long[] jArr = new long[]{0, 0};
        String str = null;
        if (jSONObject != null) {
            try {
                str = jSONObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_ST);
            } catch (JSONException e) {
            }
        }
        if (C1015j.m3168d(str)) {
            int indexOf = str.indexOf("-");
            if (indexOf > 0) {
                String substring = str.substring(0, indexOf);
                str = str.substring(indexOf + 1, str.length());
                jArr[0] = C1015j.m3164c(substring);
                jArr[1] = C1015j.m3164c(str);
            }
        }
        return jArr;
    }

    public String m3093a() {
        return C1008c.m3127b(this.f3335i);
    }

    public void m3094a(String str) {
        this.f3338l = str;
    }

    public boolean m3095b() {
        return this.f3339m == 1;
    }

    public boolean m3096c() {
        return this.f3339m == 2;
    }

    public boolean m3097d() {
        return this.f3339m == 3;
    }

    public boolean m3098e() {
        return this.f3328b == 5;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean m3099f() {
        /*
        r8 = this;
        r0 = 1;
        r1 = 0;
        r2 = java.lang.System.currentTimeMillis();
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "msgTimingType: ";
        r4 = r4.append(r5);
        r5 = r8.f3339m;
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.droi.sdk.push.utils.C1012g.m3141c(r4);
        r4 = r8.f3339m;
        r5 = 2;
        if (r4 != r5) goto L_0x0074;
    L_0x0023:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4 = r4.append(r2);
        r5 = "-(";
        r4 = r4.append(r5);
        r6 = r8.f3331e;
        r4 = r4.append(r6);
        r5 = "-";
        r4 = r4.append(r5);
        r6 = r8.f3332f;
        r4 = r4.append(r6);
        r5 = ")";
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.droi.sdk.push.utils.C1012g.m3141c(r4);
        r4 = r8.f3331e;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 < 0) goto L_0x00b4;
    L_0x0057:
        r4 = r8.f3332f;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 > 0) goto L_0x00b4;
    L_0x005d:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "showNow: ";
        r1 = r1.append(r2);
        r1 = r1.append(r0);
        r1 = r1.toString();
        com.droi.sdk.push.utils.C1012g.m3141c(r1);
        return r0;
    L_0x0074:
        r4 = r8.f3339m;
        r5 = 3;
        if (r4 != r5) goto L_0x005d;
    L_0x0079:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = r8.f3330d;
        r4 = r4.append(r6);
        r5 = "-(";
        r4 = r4.append(r5);
        r4 = r4.append(r2);
        r5 = "-";
        r4 = r4.append(r5);
        r6 = r8.f3331e;
        r4 = r4.append(r6);
        r5 = ")";
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.droi.sdk.push.utils.C1012g.m3141c(r4);
        r4 = r8.f3331e;
        r2 = r2 - r4;
        r2 = java.lang.Math.abs(r2);
        r4 = r8.f3330d;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 >= 0) goto L_0x005d;
    L_0x00b4:
        r0 = r1;
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.push.t.f():boolean");
    }

    public boolean m3100g() {
        return this.f3336j;
    }

    public boolean m3101h() {
        return this.f3336j && m3097d() && Math.abs(System.currentTimeMillis() - this.f3331e) >= this.f3330d;
    }

    public boolean m3102i() {
        if (!this.f3336j || !m3096c()) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > this.f3331e && currentTimeMillis < this.f3332f) {
            return true;
        }
        if (currentTimeMillis <= this.f3332f) {
            return false;
        }
        this.f3336j = false;
        return false;
    }
}
