package com.amap.api.maps.overlay;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoiOverlay {
    private List<PoiItem> f1012a;
    private AMap f1013b;
    private ArrayList<Marker> f1014c = new ArrayList();

    public PoiOverlay(AMap aMap, List<PoiItem> list) {
        this.f1013b = aMap;
        this.f1012a = list;
    }

    public void addToMap() {
        int i = 0;
        while (i < this.f1012a.size()) {
            try {
                Marker addMarker = this.f1013b.addMarker(m1130a(i));
                addMarker.setObject(Integer.valueOf(i));
                this.f1014c.add(addMarker);
                i++;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
    }

    public void removeFromMap() {
        Iterator it = this.f1014c.iterator();
        while (it.hasNext()) {
            ((Marker) it.next()).remove();
        }
    }

    public void zoomToSpan() {
        try {
            if (this.f1012a != null && this.f1012a.size() > 0 && this.f1013b != null) {
                if (this.f1012a.size() == 1) {
                    this.f1013b.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(((PoiItem) this.f1012a.get(0)).getLatLonPoint().getLatitude(), ((PoiItem) this.f1012a.get(0)).getLatLonPoint().getLongitude()), 18.0f));
                    return;
                }
                this.f1013b.moveCamera(CameraUpdateFactory.newLatLngBounds(m1129a(), 5));
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private LatLngBounds m1129a() {
        Builder builder = LatLngBounds.builder();
        for (int i = 0; i < this.f1012a.size(); i++) {
            builder.include(new LatLng(((PoiItem) this.f1012a.get(i)).getLatLonPoint().getLatitude(), ((PoiItem) this.f1012a.get(i)).getLatLonPoint().getLongitude()));
        }
        return builder.build();
    }

    private MarkerOptions m1130a(int i) {
        return new MarkerOptions().position(new LatLng(((PoiItem) this.f1012a.get(i)).getLatLonPoint().getLatitude(), ((PoiItem) this.f1012a.get(i)).getLatLonPoint().getLongitude())).title(getTitle(i)).snippet(getSnippet(i)).icon(getBitmapDescriptor(i));
    }

    protected BitmapDescriptor getBitmapDescriptor(int i) {
        return null;
    }

    protected String getTitle(int i) {
        return ((PoiItem) this.f1012a.get(i)).getTitle();
    }

    protected String getSnippet(int i) {
        return ((PoiItem) this.f1012a.get(i)).getSnippet();
    }

    public int getPoiIndex(Marker marker) {
        for (int i = 0; i < this.f1014c.size(); i++) {
            if (((Marker) this.f1014c.get(i)).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    public PoiItem getPoiItem(int i) {
        if (i < 0 || i >= this.f1012a.size()) {
            return null;
        }
        return (PoiItem) this.f1012a.get(i);
    }
}
