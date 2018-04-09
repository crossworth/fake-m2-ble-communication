package com.umeng.analytics.social;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.analytics.C0919a;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

public abstract class UMSocialService {

    private static class C0936a extends AsyncTask<Void, Void, C0941d> {
        String f3179a;
        String f3180b;
        C0937b f3181c;
        UMPlatformData[] f3182d;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m3131a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            m3132a((C0941d) obj);
        }

        public C0936a(String[] strArr, C0937b c0937b, UMPlatformData[] uMPlatformDataArr) {
            this.f3179a = strArr[0];
            this.f3180b = strArr[1];
            this.f3181c = c0937b;
            this.f3182d = uMPlatformDataArr;
        }

        protected void onPreExecute() {
            if (this.f3181c != null) {
                this.f3181c.m3133a();
            }
        }

        protected C0941d m3131a(Void... voidArr) {
            String a;
            if (TextUtils.isEmpty(this.f3180b)) {
                a = C0940c.m3148a(this.f3179a);
            } else {
                a = C0940c.m3149a(this.f3179a, this.f3180b);
            }
            try {
                JSONObject jSONObject = new JSONObject(a);
                int optInt = jSONObject.optInt(SocializeProtocolConstants.PROTOCOL_KEY_ST);
                C0941d c0941d = new C0941d(optInt == 0 ? C0942e.f3209t : optInt);
                String optString = jSONObject.optString("msg");
                if (!TextUtils.isEmpty(optString)) {
                    c0941d.m3152a(optString);
                }
                Object optString2 = jSONObject.optString("data");
                if (TextUtils.isEmpty(optString2)) {
                    return c0941d;
                }
                c0941d.m3154b(optString2);
                return c0941d;
            } catch (Exception e) {
                return new C0941d(-99, e);
            }
        }

        protected void m3132a(C0941d c0941d) {
            if (this.f3181c != null) {
                this.f3181c.m3134a(c0941d, this.f3182d);
            }
        }
    }

    public interface C0937b {
        void m3133a();

        void m3134a(C0941d c0941d, UMPlatformData... uMPlatformDataArr);
    }

    private static void m3135a(Context context, C0937b c0937b, String str, UMPlatformData... uMPlatformDataArr) {
        int i = 0;
        if (uMPlatformDataArr != null) {
            try {
                int length = uMPlatformDataArr.length;
                while (i < length) {
                    if (uMPlatformDataArr[i].isValid()) {
                        i++;
                    } else {
                        throw new C0938a("parameter is not valid.");
                    }
                }
            } catch (Throwable e) {
                Log.e(C0919a.f3107d, "unable send event.", e);
                return;
            } catch (Throwable e2) {
                Log.e(C0919a.f3107d, "", e2);
                return;
            }
        }
        new C0936a(C0943f.m3161a(context, str, uMPlatformDataArr), c0937b, uMPlatformDataArr).execute(new Void[0]);
    }

    public static void share(Context context, String str, UMPlatformData... uMPlatformDataArr) {
        m3135a(context, null, str, uMPlatformDataArr);
    }

    public static void share(Context context, UMPlatformData... uMPlatformDataArr) {
        m3135a(context, null, null, uMPlatformDataArr);
    }
}
