package com.umeng.analytics.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.C0921c;
import com.umeng.analytics.C0923f;
import com.umeng.analytics.C0924g;
import com.umeng.analytics.C1784d;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.game.C0927b.C0926a;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import p031u.aly.ap;
import p031u.aly.bl;

/* compiled from: InternalGameAgent */
class C1787c implements C0921c {
    private C1784d f4788a = MobclickAgent.getAgent();
    private C0927b f4789b = null;
    private final int f4790c = 100;
    private final int f4791d = 1;
    private final int f4792e = 0;
    private final int f4793f = -1;
    private final int f4794g = 1;
    private final String f4795h = LogColumns.LEVEL;
    private final String f4796i = "pay";
    private final String f4797j = "buy";
    private final String f4798k = "use";
    private final String f4799l = "bonus";
    private final String f4800m = "item";
    private final String f4801n = "cash";
    private final String f4802o = "coin";
    private final String f4803p = SocialConstants.PARAM_SOURCE;
    private final String f4804q = "amount";
    private final String f4805r = "user_level";
    private final String f4806s = "bonus_source";
    private final String f4807t = LogColumns.LEVEL;
    private final String f4808u = "status";
    private final String f4809v = "duration";
    private final String f4810w = "curtype";
    private final String f4811x = "orderid";
    private final String f4812y = "UMGameAgent.init(Context) should be called before any game api";
    private Context f4813z;

    public C1787c() {
        C0925a.f3136a = true;
    }

    void m4976a(Context context) {
        if (context == null) {
            bl.m3594e("Context is null, can't init GameAgent");
            return;
        }
        this.f4813z = context.getApplicationContext();
        this.f4788a.m4945a((C0921c) this);
        this.f4789b = new C0927b(this.f4813z);
        this.f4788a.m4936a(context, 1);
    }

    void m4980a(boolean z) {
        bl.m3576b(String.format("Trace sleep time : %b", new Object[]{Boolean.valueOf(z)}));
        C0925a.f3136a = z;
    }

    void m4977a(String str) {
        this.f4789b.f3142b = str;
        SharedPreferences a = ap.m3451a(this.f4813z);
        if (a != null) {
            Editor edit = a.edit();
            edit.putString("userlevel", str);
            edit.commit();
        }
    }

    void m4982b(final String str) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        this.f4789b.f3141a = str;
        C0923f.m3076a(new C0924g(this) {
            final /* synthetic */ C1787c f4784b;

            public void mo2152a() {
                this.f4784b.f4789b.m3088a(str);
                HashMap hashMap = new HashMap();
                hashMap.put(LogColumns.LEVEL, str);
                hashMap.put("status", Integer.valueOf(0));
                if (this.f4784b.f4789b.f3142b != null) {
                    hashMap.put("user_level", this.f4784b.f4789b.f3142b);
                }
                this.f4784b.f4788a.m4940a(this.f4784b.f4813z, LogColumns.LEVEL, hashMap);
            }
        });
    }

    private void m4968a(final String str, final int i) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else {
            C0923f.m3076a(new C0924g(this) {
                final /* synthetic */ C1787c f4787c;

                public void mo2152a() {
                    C0926a b = this.f4787c.f4789b.m3090b(str);
                    if (b != null) {
                        long e = b.m3086e();
                        if (e <= 0) {
                            bl.m3576b("level duration is 0");
                            return;
                        }
                        HashMap hashMap = new HashMap();
                        hashMap.put(LogColumns.LEVEL, str);
                        hashMap.put("status", Integer.valueOf(i));
                        hashMap.put("duration", Long.valueOf(e));
                        if (this.f4787c.f4789b.f3142b != null) {
                            hashMap.put("user_level", this.f4787c.f4789b.f3142b);
                        }
                        this.f4787c.f4788a.m4940a(this.f4787c.f4813z, LogColumns.LEVEL, hashMap);
                        return;
                    }
                    bl.m3588d(String.format("finishLevel(or failLevel) called before startLevel", new Object[0]));
                }
            });
        }
    }

    void m4984c(String str) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else {
            m4968a(str, 1);
        }
    }

    void m4985d(String str) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else {
            m4968a(str, -1);
        }
    }

    void m4972a(double d, double d2, int i) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("cash", Long.valueOf((long) (d * 100.0d)));
        hashMap.put("coin", Long.valueOf((long) (d2 * 100.0d)));
        hashMap.put(SocialConstants.PARAM_SOURCE, Integer.valueOf(i));
        if (this.f4789b.f3142b != null) {
            hashMap.put("user_level", this.f4789b.f3142b);
        }
        if (this.f4789b.f3141a != null) {
            hashMap.put(LogColumns.LEVEL, this.f4789b.f3141a);
        }
        this.f4788a.m4940a(this.f4813z, "pay", hashMap);
    }

    void m4975a(double d, String str, int i, double d2, int i2) {
        m4972a(d, d2 * ((double) i), i2);
        m4978a(str, i, d2);
    }

    void m4978a(String str, int i, double d) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("item", str);
        hashMap.put("amount", Integer.valueOf(i));
        hashMap.put("coin", Long.valueOf((long) ((((double) i) * d) * 100.0d)));
        if (this.f4789b.f3142b != null) {
            hashMap.put("user_level", this.f4789b.f3142b);
        }
        if (this.f4789b.f3141a != null) {
            hashMap.put(LogColumns.LEVEL, this.f4789b.f3141a);
        }
        this.f4788a.m4940a(this.f4813z, "buy", hashMap);
    }

    void m4983b(String str, int i, double d) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("item", str);
        hashMap.put("amount", Integer.valueOf(i));
        hashMap.put("coin", Long.valueOf((long) ((((double) i) * d) * 100.0d)));
        if (this.f4789b.f3142b != null) {
            hashMap.put("user_level", this.f4789b.f3142b);
        }
        if (this.f4789b.f3141a != null) {
            hashMap.put(LogColumns.LEVEL, this.f4789b.f3141a);
        }
        this.f4788a.m4940a(this.f4813z, "use", hashMap);
    }

    void m4973a(double d, int i) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("coin", Long.valueOf((long) (100.0d * d)));
        hashMap.put("bonus_source", Integer.valueOf(i));
        if (this.f4789b.f3142b != null) {
            hashMap.put("user_level", this.f4789b.f3142b);
        }
        if (this.f4789b.f3141a != null) {
            hashMap.put(LogColumns.LEVEL, this.f4789b.f3141a);
        }
        this.f4788a.m4940a(this.f4813z, "bonus", hashMap);
    }

    void m4979a(String str, int i, double d, int i2) {
        m4973a(((double) i) * d, i2);
        m4978a(str, i, d);
    }

    public void mo2154a() {
        bl.m3576b("App resume from background");
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else if (C0925a.f3136a) {
            this.f4789b.m3091b();
        }
    }

    public void mo2155b() {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else if (C0925a.f3136a) {
            this.f4789b.m3089a();
        }
    }

    void m4974a(double d, String str, double d2, int i, String str2) {
        if (this.f4813z == null) {
            bl.m3594e("UMGameAgent.init(Context) should be called before any game api");
        } else if (d >= 0.0d && d2 >= 0.0d) {
            HashMap hashMap = new HashMap();
            if (!TextUtils.isEmpty(str) && str.length() > 0 && str.length() <= 3) {
                hashMap.put("curtype", str);
            }
            if (!TextUtils.isEmpty(str2)) {
                try {
                    int length = str2.getBytes("UTF-8").length;
                    if (length > 0 && length <= 1024) {
                        hashMap.put("orderid", str2);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            hashMap.put("cash", Long.valueOf((long) (d * 100.0d)));
            hashMap.put("coin", Long.valueOf((long) (d2 * 100.0d)));
            hashMap.put(SocialConstants.PARAM_SOURCE, Integer.valueOf(i));
            if (this.f4789b.f3142b != null) {
                hashMap.put("user_level", this.f4789b.f3142b);
            }
            if (this.f4789b.f3141a != null) {
                hashMap.put(LogColumns.LEVEL, this.f4789b.f3141a);
            }
            this.f4788a.m4940a(this.f4813z, "pay", hashMap);
        }
    }
}
