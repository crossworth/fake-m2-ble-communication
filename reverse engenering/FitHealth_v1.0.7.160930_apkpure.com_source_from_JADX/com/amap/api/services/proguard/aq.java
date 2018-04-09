package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IWeatherSearch;
import com.amap.api.services.proguard.C0407q.C0405j;
import com.amap.api.services.proguard.C0407q.C0406k;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;

/* compiled from: WeatherSearchCore */
public class aq implements IWeatherSearch {
    private Context f4341a;
    private WeatherSearchQuery f4342b;
    private OnWeatherSearchListener f4343c;
    private LocalWeatherLiveResult f4344d;
    private LocalWeatherForecastResult f4345e;
    private Handler f4346f = null;

    /* compiled from: WeatherSearchCore */
    class C03601 implements Runnable {
        final /* synthetic */ aq f1314a;

        C03601(aq aqVar) {
            this.f1314a = aqVar;
        }

        public void run() {
            Message obtainMessage = C0407q.m1654a().obtainMessage();
            obtainMessage.arg1 = 13;
            Bundle bundle = new Bundle();
            if (this.f1314a.f4342b == null) {
                try {
                    throw new AMapException("无效的参数 - IllegalArgumentException");
                } catch (Throwable e) {
                    C0390i.m1594a(e, "WeatherSearch", "searchWeatherAsyn");
                }
            } else if (this.f1314a.f4342b.getType() == 1) {
                try {
                    this.f1314a.f4344d = this.f1314a.m4418a();
                    bundle.putInt("errorCode", 1000);
                } catch (Throwable e2) {
                    bundle.putInt("errorCode", e2.getErrorCode());
                    C0390i.m1594a(e2, "WeatherSearch", "searchWeatherAsyn");
                } catch (Throwable e22) {
                    C0390i.m1594a(e22, "WeatherSearch", "searchWeatherAnsyThrowable");
                } finally {
                    C0406k c0406k = new C0406k();
                    obtainMessage.what = 1301;
                    c0406k.f1571b = this.f1314a.f4343c;
                    c0406k.f1570a = this.f1314a.f4344d;
                    obtainMessage.obj = c0406k;
                    obtainMessage.setData(bundle);
                    this.f1314a.f4346f.sendMessage(obtainMessage);
                }
            } else if (this.f1314a.f4342b.getType() == 2) {
                try {
                    this.f1314a.f4345e = this.f1314a.m4421b();
                    bundle.putInt("errorCode", 1000);
                } catch (Throwable e222) {
                    bundle.putInt("errorCode", e222.getErrorCode());
                    C0390i.m1594a(e222, "WeatherSearch", "searchWeatherAsyn");
                } catch (Throwable e2222) {
                    C0390i.m1594a(e2222, "WeatherSearch", "searchWeatherAnsyThrowable");
                } finally {
                    C0405j c0405j = new C0405j();
                    obtainMessage.what = 1302;
                    c0405j.f1569b = this.f1314a.f4343c;
                    c0405j.f1568a = this.f1314a.f4345e;
                    obtainMessage.obj = c0405j;
                    obtainMessage.setData(bundle);
                    this.f1314a.f4346f.sendMessage(obtainMessage);
                }
            }
        }
    }

    public aq(Context context) {
        this.f4341a = context.getApplicationContext();
        this.f4346f = C0407q.m1654a();
    }

    public WeatherSearchQuery getQuery() {
        return this.f4342b;
    }

    public void setQuery(WeatherSearchQuery weatherSearchQuery) {
        this.f4342b = weatherSearchQuery;
    }

    public void searchWeatherAsyn() {
        new Thread(new C03601(this)).start();
    }

    private LocalWeatherLiveResult m4418a() throws AMapException {
        C0394o.m1652a(this.f4341a);
        if (this.f4342b == null) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        }
        ae aeVar = new ae(this.f4341a, this.f4342b);
        return LocalWeatherLiveResult.createPagedResult((WeatherSearchQuery) aeVar.m6212h(), (LocalWeatherLive) aeVar.m4358a());
    }

    private LocalWeatherForecastResult m4421b() throws AMapException {
        C0394o.m1652a(this.f4341a);
        if (this.f4342b == null) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        }
        ad adVar = new ad(this.f4341a, this.f4342b);
        return LocalWeatherForecastResult.createPagedResult((WeatherSearchQuery) adVar.m6212h(), (LocalWeatherForecast) adVar.m4358a());
    }

    public void setOnWeatherSearchListener(OnWeatherSearchListener onWeatherSearchListener) {
        this.f4343c = onWeatherSearchListener;
    }
}
