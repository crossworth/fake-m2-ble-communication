package com.tencent.open.yyb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.Util;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ProGuard */
public class C0820b {

    /* compiled from: ProGuard */
    public static class C0818a {
        public String f2758a;
        public String f2759b;
        public long f2760c;
    }

    /* compiled from: ProGuard */
    private static class C0819b extends AsyncTask<Bundle, Void, Void> {
        private C0819b() {
        }

        protected /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return m2601a((Bundle[]) objArr);
        }

        protected Void m2601a(Bundle... bundleArr) {
            if (bundleArr != null) {
                try {
                    C1711d.m4638b("openSDK_LOG", "-->(ViaAsyncTask)doInBackground : ret = " + Util.parseJson(HttpUtils.openUrl2(null, "http://analy.qq.com/cgi-bin/mapp_apptrace", "GET", bundleArr[0]).response).getInt("ret"));
                } catch (Exception e) {
                    C1711d.m4638b("openSDK_LOG", "-->(ViaAsyncTask)doInBackground : Exception = " + e.toString());
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static void m2604a(Context context, String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str)) {
            CookieSyncManager.createInstance(context);
            CookieManager instance = CookieManager.getInstance();
            instance.setAcceptCookie(true);
            String str5 = null;
            if (Uri.parse(str).getHost().toLowerCase().endsWith(".qq.com")) {
                str5 = ".qq.com";
            }
            instance.setCookie(str, C0820b.m2606b("logintype", "MOBILEQ", str5));
            instance.setCookie(str, C0820b.m2606b("qopenid", str2, str5));
            instance.setCookie(str, C0820b.m2606b("qaccesstoken", str3, str5));
            instance.setCookie(str, C0820b.m2606b("openappid", str4, str5));
            CookieSyncManager.getInstance().sync();
        }
    }

    private static String m2606b(String str, String str2, String str3) {
        String str4 = str + "=" + str2;
        if (str3 == null) {
            return str4;
        }
        return (str4 + "; path=/") + "; domain=" + str3;
    }

    public static Drawable m2602a(String str, Context context) {
        return C0820b.m2603a(str, context, new Rect(0, 0, 0, 0));
    }

    public static Drawable m2603a(String str, Context context, Rect rect) {
        InputStream open;
        IOException e;
        InputStream inputStream;
        Throwable th;
        Context applicationContext = context.getApplicationContext();
        Drawable ninePatchDrawable;
        try {
            open = applicationContext.getAssets().open(str);
            if (open != null) {
                try {
                    if (str.endsWith(".9.png")) {
                        Bitmap decodeStream = BitmapFactory.decodeStream(open);
                        if (decodeStream != null) {
                            ninePatchDrawable = new NinePatchDrawable(applicationContext.getResources(), decodeStream, decodeStream.getNinePatchChunk(), rect, null);
                        } else if (open == null) {
                            return null;
                        } else {
                            try {
                                open.close();
                                return null;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return null;
                            }
                        }
                    }
                    ninePatchDrawable = Drawable.createFromStream(open, str);
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (IOException e4) {
                    e2 = e4;
                    inputStream = open;
                    try {
                        e2.printStackTrace();
                        C1711d.m4638b("openSDK_LOG", "-->(AppbarUtil)getDrawable : IOException");
                        if (inputStream == null) {
                            ninePatchDrawable = null;
                        } else {
                            try {
                                inputStream.close();
                                ninePatchDrawable = null;
                            } catch (IOException e22) {
                                e22.printStackTrace();
                                ninePatchDrawable = null;
                            }
                        }
                        return ninePatchDrawable;
                    } catch (Throwable th2) {
                        th = th2;
                        open = inputStream;
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (open != null) {
                        open.close();
                    }
                    throw th;
                }
                return ninePatchDrawable;
            } else if (open == null) {
                return null;
            } else {
                try {
                    open.close();
                    return null;
                } catch (IOException e222) {
                    e222.printStackTrace();
                    return null;
                }
            }
        } catch (IOException e5) {
            e222 = e5;
            inputStream = null;
            e222.printStackTrace();
            C1711d.m4638b("openSDK_LOG", "-->(AppbarUtil)getDrawable : IOException");
            if (inputStream == null) {
                inputStream.close();
                ninePatchDrawable = null;
            } else {
                ninePatchDrawable = null;
            }
            return ninePatchDrawable;
        } catch (Throwable th4) {
            th = th4;
            open = null;
            if (open != null) {
                open.close();
            }
            throw th;
        }
    }

    public static void m2605a(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("uin", "1000");
        bundle.putString("action", str2);
        bundle.putString("appid", str);
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERIFY_MEDIA, str3);
        new C0819b().execute(new Bundle[]{bundle});
    }
}
