package com.amap.api.maps.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.amap.api.mapcore.util.C0273r;
import com.amap.api.mapcore.util.dh;
import com.amap.api.mapcore.util.dj;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusLineOverlay {
    private BusLineItem f1003a;
    private AMap f1004b;
    private ArrayList<Marker> f1005c = new ArrayList();
    private Polyline f1006d;
    private List<BusStationItem> f1007e;
    private BitmapDescriptor f1008f;
    private BitmapDescriptor f1009g;
    private BitmapDescriptor f1010h;
    private Context f1011i;

    public BusLineOverlay(Context context, AMap aMap, BusLineItem busLineItem) {
        this.f1011i = context;
        this.f1003a = busLineItem;
        this.f1004b = aMap;
        this.f1007e = this.f1003a.getBusStations();
    }

    public void addToMap() {
        int i = 1;
        try {
            this.f1006d = this.f1004b.addPolyline(new PolylineOptions().addAll(C0313a.m1132a(this.f1003a.getDirectionsCoordinates())).color(getBusColor()).width(getBuslineWidth()));
            if (this.f1007e.size() >= 1) {
                while (i < this.f1007e.size() - 1) {
                    this.f1005c.add(this.f1004b.addMarker(m1127a(i)));
                    i++;
                }
                this.f1005c.add(this.f1004b.addMarker(m1127a(0)));
                this.f1005c.add(this.f1004b.addMarker(m1127a(this.f1007e.size() - 1)));
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeFromMap() {
        if (this.f1006d != null) {
            this.f1006d.remove();
        }
        try {
            Iterator it = this.f1005c.iterator();
            while (it.hasNext()) {
                ((Marker) it.next()).remove();
            }
            m1128a();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m1128a() {
        if (this.f1008f != null) {
            this.f1008f.recycle();
            this.f1008f = null;
        }
        if (this.f1009g != null) {
            this.f1009g.recycle();
            this.f1009g = null;
        }
        if (this.f1010h != null) {
            this.f1010h.recycle();
            this.f1010h = null;
        }
    }

    public void zoomToSpan() {
        if (this.f1004b != null) {
            try {
                List directionsCoordinates = this.f1003a.getDirectionsCoordinates();
                if (directionsCoordinates != null && directionsCoordinates.size() > 0) {
                    this.f1004b.moveCamera(CameraUpdateFactory.newLatLngBounds(m1126a(directionsCoordinates), 5));
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private LatLngBounds m1126a(List<LatLonPoint> list) {
        Builder builder = LatLngBounds.builder();
        for (int i = 0; i < list.size(); i++) {
            builder.include(new LatLng(((LatLonPoint) list.get(i)).getLatitude(), ((LatLonPoint) list.get(i)).getLongitude()));
        }
        return builder.build();
    }

    private MarkerOptions m1127a(int i) {
        MarkerOptions snippet = new MarkerOptions().position(new LatLng(((BusStationItem) this.f1007e.get(i)).getLatLonPoint().getLatitude(), ((BusStationItem) this.f1007e.get(i)).getLatLonPoint().getLongitude())).title(getTitle(i)).snippet(getSnippet(i));
        if (i == 0) {
            snippet.icon(getStartBitmapDescriptor());
        } else if (i == this.f1007e.size() - 1) {
            snippet.icon(getEndBitmapDescriptor());
        } else {
            snippet.anchor(0.5f, 0.5f);
            snippet.icon(getBusBitmapDescriptor());
        }
        return snippet;
    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        this.f1008f = m1125a("amap_start.png");
        return this.f1008f;
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        this.f1009g = m1125a("amap_end.png");
        return this.f1009g;
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        this.f1010h = m1125a("amap_bus.png");
        return this.f1010h;
    }

    protected String getTitle(int i) {
        return ((BusStationItem) this.f1007e.get(i)).getBusStationName();
    }

    protected String getSnippet(int i) {
        return "";
    }

    public int getBusStationIndex(Marker marker) {
        for (int i = 0; i < this.f1005c.size(); i++) {
            if (((Marker) this.f1005c.get(i)).equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    public BusStationItem getBusStationItem(int i) {
        if (i < 0 || i >= this.f1007e.size()) {
            return null;
        }
        return (BusStationItem) this.f1007e.get(i);
    }

    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected float getBuslineWidth() {
        return 18.0f;
    }

    private BitmapDescriptor m1125a(String str) {
        InputStream open;
        Bitmap a;
        IOException e;
        IOException iOException;
        BitmapDescriptor fromBitmap;
        Throwable th;
        Throwable th2;
        Throwable th3;
        Object obj;
        InputStream inputStream;
        InputStream inputStream2 = null;
        try {
            open = dh.m547a(this.f1011i).open(str);
            try {
                Bitmap decodeStream = BitmapFactory.decodeStream(open);
                try {
                    a = dj.m565a(decodeStream, C0273r.f694a);
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (IOException e3) {
                    iOException = e3;
                    a = decodeStream;
                    e2 = iOException;
                    try {
                        e2.printStackTrace();
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                        a.recycle();
                        return fromBitmap;
                    } catch (Throwable th4) {
                        th = th4;
                        if (open != null) {
                            try {
                                open.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th2 = th5;
                    a = decodeStream;
                    th3 = th2;
                    th3.printStackTrace();
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e2222) {
                            e2222.printStackTrace();
                        }
                    }
                    fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                    a.recycle();
                    return fromBitmap;
                }
            } catch (IOException e32) {
                iOException = e32;
                obj = inputStream2;
                e2222 = iOException;
                e2222.printStackTrace();
                if (open != null) {
                    open.close();
                }
                fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                a.recycle();
                return fromBitmap;
            } catch (Throwable th52) {
                th2 = th52;
                obj = inputStream2;
                th3 = th2;
                th3.printStackTrace();
                if (open != null) {
                    open.close();
                }
                fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                a.recycle();
                return fromBitmap;
            }
        } catch (IOException e322) {
            open = inputStream2;
            inputStream = inputStream2;
            e2222 = e322;
            a = inputStream;
            e2222.printStackTrace();
            if (open != null) {
                open.close();
            }
            fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
            a.recycle();
            return fromBitmap;
        } catch (Throwable th6) {
            th52 = th6;
            open = inputStream2;
            if (open != null) {
                open.close();
            }
            throw th52;
        }
        fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
        a.recycle();
        return fromBitmap;
    }
}
