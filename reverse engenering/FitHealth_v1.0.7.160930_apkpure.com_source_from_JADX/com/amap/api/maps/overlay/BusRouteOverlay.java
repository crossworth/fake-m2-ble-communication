package com.amap.api.maps.overlay;

import android.content.Context;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.WalkStep;
import com.umeng.socialize.common.SocializeConstants;
import java.util.List;

public class BusRouteOverlay extends C0314b {
    private BusPath f4254a;
    private LatLng f4255b;

    public /* bridge */ /* synthetic */ void removeFromMap() {
        super.removeFromMap();
    }

    public /* bridge */ /* synthetic */ void setNodeIconVisibility(boolean z) {
        super.setNodeIconVisibility(z);
    }

    public /* bridge */ /* synthetic */ void zoomToSpan() {
        super.zoomToSpan();
    }

    public BusRouteOverlay(Context context, AMap aMap, BusPath busPath, LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
        super(context);
        this.f4254a = busPath;
        this.startPoint = C0313a.m1131a(latLonPoint);
        this.endPoint = C0313a.m1131a(latLonPoint2);
        this.mAMap = aMap;
    }

    public void addToMap() {
        try {
            List steps = this.f4254a.getSteps();
            for (int i = 0; i < steps.size(); i++) {
                BusStep busStep = (BusStep) steps.get(i);
                if (i < steps.size() - 1) {
                    BusStep busStep2 = (BusStep) steps.get(i + 1);
                    if (!(busStep.getWalk() == null || busStep.getBusLine() == null)) {
                        m4327b(busStep);
                    }
                    if (!(busStep.getBusLine() == null || busStep2.getWalk() == null)) {
                        m4334c(busStep, busStep2);
                    }
                    if (!(busStep.getBusLine() == null || busStep2.getWalk() != null || busStep2.getBusLine() == null)) {
                        m4328b(busStep, busStep2);
                    }
                    if (!(busStep.getBusLine() == null || busStep2.getWalk() != null || busStep2.getBusLine() == null)) {
                        m4324a(busStep, busStep2);
                    }
                }
                if (busStep.getWalk() != null && busStep.getWalk().getSteps().size() > 0) {
                    m4323a(busStep);
                } else if (busStep.getBusLine() == null) {
                    m4320a(this.f4255b, this.endPoint);
                }
                if (busStep.getBusLine() != null) {
                    RouteBusLineItem busLine = busStep.getBusLine();
                    m4325a(busLine);
                    m4329b(busLine);
                }
            }
            addStartAndEndMarker();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m4323a(BusStep busStep) {
        List steps = busStep.getWalk().getSteps();
        for (int i = 0; i < steps.size(); i++) {
            WalkStep walkStep = (WalkStep) steps.get(i);
            if (i == 0) {
                m4321a(C0313a.m1131a((LatLonPoint) walkStep.getPolyline().get(0)), walkStep.getRoad(), m4333c(steps));
            }
            List a = C0313a.m1132a(walkStep.getPolyline());
            this.f4255b = (LatLng) a.get(a.size() - 1);
            m4330b(a);
            if (i < steps.size() - 1) {
                LatLng latLng = (LatLng) a.get(a.size() - 1);
                LatLng a2 = C0313a.m1131a((LatLonPoint) ((WalkStep) steps.get(i + 1)).getPolyline().get(0));
                if (!latLng.equals(a2)) {
                    m4320a(latLng, a2);
                }
            }
        }
    }

    private void m4325a(RouteBusLineItem routeBusLineItem) {
        m4326a(routeBusLineItem.getPolyline());
    }

    private void m4326a(List<LatLonPoint> list) {
        if (list.size() >= 1) {
            addPolyLine(new PolylineOptions().width(getRouteWidth()).color(getBusColor()).addAll(C0313a.m1132a((List) list)));
        }
    }

    private void m4321a(LatLng latLng, String str, String str2) {
        addStationMarker(new MarkerOptions().position(latLng).title(str).snippet(str2).anchor(0.5f, 0.5f).visible(this.nodeIconVisible).icon(getWalkBitmapDescriptor()));
    }

    private void m4329b(RouteBusLineItem routeBusLineItem) {
        LatLng a = C0313a.m1131a(routeBusLineItem.getDepartureBusStation().getLatLonPoint());
        String busLineName = routeBusLineItem.getBusLineName();
        addStationMarker(new MarkerOptions().position(a).title(busLineName).snippet(m4332c(routeBusLineItem)).anchor(0.5f, 0.5f).visible(this.nodeIconVisible).icon(getBusBitmapDescriptor()));
    }

    private void m4324a(BusStep busStep, BusStep busStep2) {
        LatLng a = C0313a.m1131a(m4336e(busStep));
        LatLng a2 = C0313a.m1131a(m4337f(busStep2));
        if (a2.latitude - a.latitude > 1.0E-4d || a2.longitude - a.longitude > 1.0E-4d) {
            drawLineArrow(a, a2);
        }
    }

    private void m4328b(BusStep busStep, BusStep busStep2) {
        LatLng a = C0313a.m1131a(m4336e(busStep));
        LatLng a2 = C0313a.m1131a(m4337f(busStep2));
        if (!a.equals(a2)) {
            drawLineArrow(a, a2);
        }
    }

    private void m4334c(BusStep busStep, BusStep busStep2) {
        LatLonPoint e = m4336e(busStep);
        LatLonPoint c = m4331c(busStep2);
        if (!e.equals(c)) {
            m4322a(e, c);
        }
    }

    private void m4327b(BusStep busStep) {
        LatLonPoint d = m4335d(busStep);
        LatLonPoint f = m4337f(busStep);
        if (!d.equals(f)) {
            m4322a(d, f);
        }
    }

    private LatLonPoint m4331c(BusStep busStep) {
        return (LatLonPoint) ((WalkStep) busStep.getWalk().getSteps().get(0)).getPolyline().get(0);
    }

    private void m4322a(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
        m4320a(C0313a.m1131a(latLonPoint), C0313a.m1131a(latLonPoint2));
    }

    private void m4320a(LatLng latLng, LatLng latLng2) {
        addPolyLine(new PolylineOptions().add(latLng, latLng2).width(getRouteWidth()).color(getWalkColor()));
    }

    private void m4330b(List<LatLng> list) {
        addPolyLine(new PolylineOptions().addAll(list).color(getWalkColor()).width(getRouteWidth()));
    }

    private String m4333c(List<WalkStep> list) {
        float f = 0.0f;
        for (WalkStep distance : list) {
            f = distance.getDistance() + f;
        }
        return "步行" + f + "米";
    }

    public void drawLineArrow(LatLng latLng, LatLng latLng2) {
        addPolyLine(new PolylineOptions().add(latLng, latLng2).width(3.0f).color(getBusColor()).width(getRouteWidth()));
    }

    private String m4332c(RouteBusLineItem routeBusLineItem) {
        return SocializeConstants.OP_OPEN_PAREN + routeBusLineItem.getDepartureBusStation().getBusStationName() + "-->" + routeBusLineItem.getArrivalBusStation().getBusStationName() + ") 经过" + (routeBusLineItem.getPassStationNum() + 1) + "站";
    }

    private LatLonPoint m4335d(BusStep busStep) {
        List steps = busStep.getWalk().getSteps();
        steps = ((WalkStep) steps.get(steps.size() - 1)).getPolyline();
        return (LatLonPoint) steps.get(steps.size() - 1);
    }

    private LatLonPoint m4336e(BusStep busStep) {
        List polyline = busStep.getBusLine().getPolyline();
        return (LatLonPoint) polyline.get(polyline.size() - 1);
    }

    private LatLonPoint m4337f(BusStep busStep) {
        return (LatLonPoint) busStep.getBusLine().getPolyline().get(0);
    }
}
