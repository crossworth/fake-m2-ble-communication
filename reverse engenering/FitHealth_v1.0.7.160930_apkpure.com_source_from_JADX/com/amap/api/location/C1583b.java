package com.amap.api.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.api.location.core.AMapLocException;
import com.amap.api.location.core.C0186a;
import com.amap.api.location.core.C0188c;
import com.amap.api.location.core.C0189d;
import com.aps.C0456l;
import com.tencent.open.yyb.TitleBar;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AMapWeather */
public class C1583b implements AMapLocationListener {
    C0184a f3930a = null;
    AMapLocalWeatherListener f3931b;
    C0182a f3932c;
    private Context f3933d;
    private int f3934e;
    private AMapLocalWeatherListener f3935f;

    /* compiled from: AMapWeather */
    static class C0184a extends Handler {
        private WeakReference<C1583b> f75a;

        C0184a(C1583b c1583b, Looper looper) {
            super(looper);
            try {
                this.f75a = new WeakReference(c1583b);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                final C1583b c1583b = (C1583b) this.f75a.get();
                switch (message.what) {
                    case 1:
                        if (c1583b.f3931b != null) {
                            c1583b.f3931b.onWeatherLiveSearched((AMapLocalWeatherLive) message.obj);
                            return;
                        }
                        return;
                    case 2:
                        if (c1583b.f3931b != null) {
                            c1583b.f3931b.onWeatherForecaseSearched((AMapLocalWeatherForecast) message.obj);
                            return;
                        }
                        return;
                    case 3:
                        final AMapLocation aMapLocation = (AMapLocation) message.obj;
                        new Thread(this) {
                            final /* synthetic */ C0184a f74c;

                            public void run() {
                                try {
                                    if (c1583b.f3934e == 1) {
                                        c1583b.m3966a(aMapLocation, "base", c1583b.f3935f);
                                    }
                                    if (c1583b.f3934e == 2) {
                                        c1583b.m3966a(aMapLocation, "all", c1583b.f3935f);
                                    }
                                } catch (Throwable th) {
                                    th.printStackTrace();
                                }
                            }
                        }.start();
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            th.printStackTrace();
        }
    }

    public C1583b(C0182a c0182a, Context context) {
        this.f3933d = context;
        this.f3932c = c0182a;
        this.f3930a = new C0184a(this, context.getMainLooper());
    }

    void m3965a(int i, AMapLocalWeatherListener aMapLocalWeatherListener, AMapLocation aMapLocation) {
        try {
            this.f3934e = i;
            this.f3935f = aMapLocalWeatherListener;
            if (aMapLocation == null) {
                this.f3932c.m55a(-1, (float) TitleBar.SHAREBTN_RIGHT_MARGIN, (AMapLocationListener) this, LocationProviderProxy.AMapNetwork, true);
                return;
            }
            if (i == 1) {
                m3966a(aMapLocation, "base", aMapLocalWeatherListener);
            }
            if (i == 2) {
                m3966a(aMapLocation, "all", aMapLocalWeatherListener);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    void m3966a(AMapLocation aMapLocation, String str, AMapLocalWeatherListener aMapLocalWeatherListener) throws Exception {
        this.f3931b = aMapLocalWeatherListener;
        if (aMapLocation != null) {
            AMapLocException aMapLocException;
            byte[] a = m3961a(aMapLocation, str);
            String a2 = m3960a();
            AMapLocException aMapLocException2 = new AMapLocException();
            String str2 = null;
            try {
                str2 = C0456l.m1967a().m1974a(this.f3933d, a2, a, "sea");
            } catch (AMapLocException e) {
                aMapLocException2 = e;
            }
            if ("base".equals(str)) {
                AMapLocalWeatherLive a3;
                if (str2 != null) {
                    aMapLocException = aMapLocException2;
                    a3 = m3959a(str2, aMapLocation);
                } else {
                    a3 = new AMapLocalWeatherLive();
                    aMapLocException = new AMapLocException("http连接失败 - ConnectionException");
                }
                a3.m22a(aMapLocException);
                a3.setCity(aMapLocation.getCity());
                a3.setCityCode(aMapLocation.getCityCode());
                a3.setProvince(aMapLocation.getProvince());
                Message obtain = Message.obtain();
                obtain.what = 1;
                obtain.obj = a3;
                this.f3930a.sendMessage(obtain);
            } else {
                aMapLocException = aMapLocException2;
            }
            if ("all".equals(str)) {
                AMapLocalWeatherForecast b;
                if (str2 != null) {
                    b = m3962b(str2, aMapLocation);
                } else {
                    b = new AMapLocalWeatherForecast();
                    aMapLocException = new AMapLocException("http连接失败 - ConnectionException");
                }
                b.m19a(aMapLocException);
                Message obtain2 = Message.obtain();
                obtain2.what = 2;
                obtain2.obj = b;
                this.f3930a.sendMessage(obtain2);
            }
        }
    }

    private byte[] m3961a(AMapLocation aMapLocation, String str) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json&ec=1").append("&extensions=" + str).append("&city=").append(aMapLocation.getAdCode());
        stringBuffer.append("&key=" + C0188c.m85a());
        return C0186a.m80b(C0186a.m77a(stringBuffer.toString())).getBytes("utf-8");
    }

    private String m3960a() {
        return "http://restapi.amap.com/v3/weather/weatherInfo?";
    }

    private AMapLocalWeatherLive m3959a(String str, AMapLocation aMapLocation) throws JSONException {
        AMapLocalWeatherLive aMapLocalWeatherLive = new AMapLocalWeatherLive();
        try {
            C0189d.m107a(str);
        } catch (AMapLocException e) {
            aMapLocalWeatherLive.m22a(e);
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("lives");
            if (jSONArray != null && jSONArray.length() > 0) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(0);
                String a = m3964a(jSONObject, "weather");
                String a2 = m3964a(jSONObject, "temperature");
                String a3 = m3964a(jSONObject, "winddirection");
                String a4 = m3964a(jSONObject, "windpower");
                String a5 = m3964a(jSONObject, "humidity");
                String a6 = m3964a(jSONObject, "reporttime");
                aMapLocalWeatherLive.m23a(a);
                aMapLocalWeatherLive.m28f(a6);
                aMapLocalWeatherLive.m27e(a5);
                aMapLocalWeatherLive.m24b(a2);
                aMapLocalWeatherLive.m25c(a3);
                aMapLocalWeatherLive.m26d(a4);
                aMapLocalWeatherLive.setCity(aMapLocation.getCity());
                aMapLocalWeatherLive.setCityCode(aMapLocation.getCityCode());
                aMapLocalWeatherLive.setProvince(aMapLocation.getProvince());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return aMapLocalWeatherLive;
    }

    private AMapLocalWeatherForecast m3962b(String str, AMapLocation aMapLocation) throws JSONException {
        AMapLocalWeatherForecast aMapLocalWeatherForecast = new AMapLocalWeatherForecast();
        try {
            C0189d.m107a(str);
        } catch (AMapLocException e) {
            aMapLocalWeatherForecast.m19a(e);
            e.printStackTrace();
        }
        JSONArray jSONArray = new JSONObject(str).getJSONArray("forecasts");
        if (jSONArray != null && jSONArray.length() > 0) {
            JSONObject jSONObject = (JSONObject) jSONArray.get(0);
            aMapLocalWeatherForecast.m20a(m3964a(jSONObject, "reporttime"));
            JSONArray jSONArray2 = jSONObject.getJSONArray("casts");
            if (jSONArray2 != null && jSONArray2.length() > 0) {
                List arrayList = new ArrayList();
                for (int i = 0; i < jSONArray2.length(); i++) {
                    AMapLocalDayWeatherForecast aMapLocalDayWeatherForecast = new AMapLocalDayWeatherForecast();
                    jSONObject = (JSONObject) jSONArray2.get(i);
                    String a = m3964a(jSONObject, "date");
                    String a2 = m3964a(jSONObject, "week");
                    String a3 = m3964a(jSONObject, "dayweather");
                    String a4 = m3964a(jSONObject, "nightweather");
                    String a5 = m3964a(jSONObject, "daytemp");
                    String a6 = m3964a(jSONObject, "nighttemp");
                    String a7 = m3964a(jSONObject, "daywind");
                    String a8 = m3964a(jSONObject, "nightwind");
                    String a9 = m3964a(jSONObject, "daypower");
                    String a10 = m3964a(jSONObject, "nightpower");
                    aMapLocalDayWeatherForecast.m9a(a);
                    aMapLocalDayWeatherForecast.m10b(a2);
                    aMapLocalDayWeatherForecast.m11c(a3);
                    aMapLocalDayWeatherForecast.m12d(a4);
                    aMapLocalDayWeatherForecast.m13e(a5);
                    aMapLocalDayWeatherForecast.m14f(a6);
                    aMapLocalDayWeatherForecast.m15g(a7);
                    aMapLocalDayWeatherForecast.m16h(a8);
                    aMapLocalDayWeatherForecast.m17i(a9);
                    aMapLocalDayWeatherForecast.m18j(a10);
                    aMapLocalDayWeatherForecast.setCity(aMapLocation.getCity());
                    aMapLocalDayWeatherForecast.setCityCode(aMapLocation.getCityCode());
                    aMapLocalDayWeatherForecast.setProvince(aMapLocation.getProvince());
                    arrayList.add(aMapLocalDayWeatherForecast);
                }
                aMapLocalWeatherForecast.m21a(arrayList);
            }
        }
        return aMapLocalWeatherForecast;
    }

    protected String m3964a(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject == null) {
            return "";
        }
        String str2 = "";
        if (!jSONObject.has(str) || jSONObject.getString(str).equals("[]")) {
            return str2;
        }
        return jSONObject.optString(str);
    }

    public void onLocationChanged(Location location) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            try {
                if (aMapLocation.getAMapException() != null && aMapLocation.getAMapException().getErrorCode() == 0) {
                    this.f3932c.m57a((AMapLocationListener) this);
                    Message obtain = Message.obtain();
                    obtain.what = 3;
                    obtain.obj = aMapLocation;
                    this.f3930a.sendMessage(obtain);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
