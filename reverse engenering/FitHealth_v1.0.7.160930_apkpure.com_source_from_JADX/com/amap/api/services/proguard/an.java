package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IPoiSearch;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.amap.api.services.proguard.C0407q.C0402g;
import com.amap.api.services.proguard.C0407q.C0403h;
import java.util.HashMap;

/* compiled from: PoiSearchCore */
public class an implements IPoiSearch {
    private static HashMap<Integer, PoiResult> f4321i;
    private SearchBound f4322a;
    private Query f4323b;
    private Context f4324c;
    private OnPoiSearchListener f4325d;
    private String f4326e = "zh-CN";
    private Query f4327f;
    private SearchBound f4328g;
    private int f4329h;
    private Handler f4330j = null;

    /* compiled from: PoiSearchCore */
    class C03491 extends Thread {
        final /* synthetic */ an f1293a;

        C03491(an anVar) {
            this.f1293a = anVar;
        }

        public void run() {
            Message obtainMessage = this.f1293a.f4330j.obtainMessage();
            obtainMessage.arg1 = 6;
            obtainMessage.what = 600;
            Bundle bundle = new Bundle();
            PoiResult poiResult = null;
            try {
                poiResult = this.f1293a.searchPOI();
                bundle.putInt("errorCode", 1000);
            } catch (AMapException e) {
                bundle.putInt("errorCode", e.getErrorCode());
            } finally {
                C0403h c0403h = new C0403h();
                c0403h.f1565b = this.f1293a.f4325d;
                c0403h.f1564a = poiResult;
                obtainMessage.obj = c0403h;
                obtainMessage.setData(bundle);
                this.f1293a.f4330j.sendMessage(obtainMessage);
            }
        }
    }

    public an(Context context, Query query) {
        this.f4324c = context.getApplicationContext();
        setQuery(query);
        this.f4330j = C0407q.m1654a();
    }

    public void setOnPoiSearchListener(OnPoiSearchListener onPoiSearchListener) {
        this.f4325d = onPoiSearchListener;
    }

    public void setLanguage(String str) {
        if ("en".equals(str)) {
            this.f4326e = "en";
        } else {
            this.f4326e = "zh-CN";
        }
    }

    public String getLanguage() {
        return this.f4326e;
    }

    private boolean m4408a() {
        if (this.f4323b == null) {
            return false;
        }
        if (C0390i.m1595a(this.f4323b.getQueryString()) && C0390i.m1595a(this.f4323b.getCategory())) {
            return false;
        }
        return true;
    }

    private boolean m4410b() {
        SearchBound bound = getBound();
        if (bound != null && bound.getShape().equals("Bound")) {
            return true;
        }
        return false;
    }

    public PoiResult searchPOI() throws AMapException {
        try {
            C0394o.m1652a(this.f4324c);
            if (m4410b() || m4408a()) {
                SearchBound clone;
                this.f4323b.setQueryLanguage(this.f4326e);
                if ((!this.f4323b.queryEquals(this.f4327f) && this.f4322a == null) || !(this.f4323b.queryEquals(this.f4327f) || this.f4322a.equals(this.f4328g))) {
                    this.f4329h = 0;
                    this.f4327f = this.f4323b.clone();
                    if (this.f4322a != null) {
                        this.f4328g = this.f4322a.clone();
                    }
                    if (f4321i != null) {
                        f4321i.clear();
                    }
                }
                if (this.f4322a != null) {
                    clone = this.f4322a.clone();
                } else {
                    clone = null;
                }
                PoiResult poiResult;
                if (this.f4329h == 0) {
                    poiResult = (PoiResult) new C2070w(this.f4324c, new C0410z(this.f4323b.clone(), clone)).m4358a();
                    m4407a(poiResult);
                    return poiResult;
                }
                poiResult = m4412a(this.f4323b.getPageNum());
                if (poiResult != null) {
                    return poiResult;
                }
                poiResult = (PoiResult) new C2070w(this.f4324c, new C0410z(this.f4323b.clone(), clone)).m4358a();
                f4321i.put(Integer.valueOf(this.f4323b.getPageNum()), poiResult);
                return poiResult;
            }
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } catch (Throwable e) {
            C0390i.m1594a(e, "PoiSearch", "searchPOI");
            throw new AMapException(e.getErrorMessage());
        }
    }

    public void searchPOIAsyn() {
        new C03491(this).start();
    }

    public PoiItem searchPOIId(String str) throws AMapException {
        C0394o.m1652a(this.f4324c);
        return (PoiItem) new C2069v(this.f4324c, str).m4358a();
    }

    public void searchPOIIdAsyn(final String str) {
        new Thread(this) {
            final /* synthetic */ an f1295b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                obtainMessage.arg1 = 6;
                obtainMessage.what = 602;
                Bundle bundle = new Bundle();
                PoiItem poiItem = null;
                try {
                    poiItem = this.f1295b.searchPOIId(str);
                    bundle.putInt("errorCode", 1000);
                } catch (Throwable e) {
                    C0390i.m1594a(e, "PoiSearch", "searchPOIIdAsyn");
                    bundle.putInt("errorCode", e.getErrorCode());
                } finally {
                    C0402g c0402g = new C0402g();
                    c0402g.f1563b = this.f1295b.f4325d;
                    c0402g.f1562a = poiItem;
                    obtainMessage.obj = c0402g;
                    obtainMessage.setData(bundle);
                    this.f1295b.f4330j.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    public void setQuery(Query query) {
        this.f4323b = query;
    }

    public void setBound(SearchBound searchBound) {
        this.f4322a = searchBound;
    }

    public Query getQuery() {
        return this.f4323b;
    }

    public SearchBound getBound() {
        return this.f4322a;
    }

    private void m4407a(PoiResult poiResult) {
        f4321i = new HashMap();
        if (this.f4323b != null && poiResult != null && this.f4329h > 0 && this.f4329h > this.f4323b.getPageNum()) {
            f4321i.put(Integer.valueOf(this.f4323b.getPageNum()), poiResult);
        }
    }

    protected PoiResult m4412a(int i) {
        if (m4411b(i)) {
            return (PoiResult) f4321i.get(Integer.valueOf(i));
        }
        throw new IllegalArgumentException("page out of range");
    }

    private boolean m4411b(int i) {
        return i <= this.f4329h && i >= 0;
    }
}
