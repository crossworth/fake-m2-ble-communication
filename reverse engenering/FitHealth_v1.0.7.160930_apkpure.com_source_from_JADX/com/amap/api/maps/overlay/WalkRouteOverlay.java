package com.amap.api.maps.overlay;

import android.content.Context;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;
import java.util.List;

public class WalkRouteOverlay extends C0314b {
    private WalkPath f4262a;

    public /* bridge */ /* synthetic */ void removeFromMap() {
        super.removeFromMap();
    }

    public /* bridge */ /* synthetic */ void setNodeIconVisibility(boolean z) {
        super.setNodeIconVisibility(z);
    }

    public /* bridge */ /* synthetic */ void zoomToSpan() {
        super.zoomToSpan();
    }

    public WalkRouteOverlay(Context context, AMap aMap, WalkPath walkPath, LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
        super(context);
        this.mAMap = aMap;
        this.f4262a = walkPath;
        this.startPoint = C0313a.m1131a(latLonPoint);
        this.endPoint = C0313a.m1131a(latLonPoint2);
    }

    public void addToMap() {
        try {
            List steps = this.f4262a.getSteps();
            for (int i = 0; i < steps.size(); i++) {
                WalkStep walkStep = (WalkStep) steps.get(i);
                LatLng a = C0313a.m1131a((LatLonPoint) walkStep.getPolyline().get(0));
                if (i < steps.size() - 1) {
                    if (i == 0) {
                        m4349a(this.startPoint, a);
                    }
                    m4352a(walkStep, (WalkStep) steps.get(i + 1));
                } else {
                    m4349a(C0313a.m1131a(m4348a(walkStep)), this.endPoint);
                }
                m4351a(walkStep, a);
                m4354c(walkStep);
            }
            addStartAndEndMarker();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m4352a(WalkStep walkStep, WalkStep walkStep2) {
        LatLonPoint a = m4348a(walkStep);
        LatLonPoint b = m4353b(walkStep2);
        if (!a.equals(b)) {
            m4350a(a, b);
        }
    }

    private LatLonPoint m4348a(WalkStep walkStep) {
        return (LatLonPoint) walkStep.getPolyline().get(walkStep.getPolyline().size() - 1);
    }

    private LatLonPoint m4353b(WalkStep walkStep) {
        return (LatLonPoint) walkStep.getPolyline().get(0);
    }

    private void m4350a(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
        m4349a(C0313a.m1131a(latLonPoint), C0313a.m1131a(latLonPoint2));
    }

    private void m4349a(LatLng latLng, LatLng latLng2) {
        addPolyLine(new PolylineOptions().add(latLng, latLng2).color(getWalkColor()).width(getRouteWidth()));
    }

    private void m4354c(WalkStep walkStep) {
        addPolyLine(new PolylineOptions().addAll(C0313a.m1132a(walkStep.getPolyline())).color(getWalkColor()).width(getRouteWidth()));
    }

    private void m4351a(WalkStep walkStep, LatLng latLng) {
        addStationMarker(new MarkerOptions().position(latLng).title("方向:" + walkStep.getAction() + "\n道路:" + walkStep.getRoad()).snippet(walkStep.getInstruction()).visible(this.nodeIconVisible).anchor(0.5f, 0.5f).icon(getWalkBitmapDescriptor()));
    }
}
