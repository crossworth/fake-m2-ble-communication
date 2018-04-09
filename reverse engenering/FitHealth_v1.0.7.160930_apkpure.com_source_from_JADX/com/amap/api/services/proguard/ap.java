package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.LatLonSharePoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IShareSearch;
import com.amap.api.services.share.ShareSearch.OnShareSearchListener;
import com.amap.api.services.share.ShareSearch.ShareBusRouteQuery;
import com.amap.api.services.share.ShareSearch.ShareDrivingRouteQuery;
import com.amap.api.services.share.ShareSearch.ShareFromAndTo;
import com.amap.api.services.share.ShareSearch.ShareNaviQuery;
import com.amap.api.services.share.ShareSearch.ShareWalkRouteQuery;

/* compiled from: ShareSearchCore */
public class ap implements IShareSearch {
    private static String f4334b = "http://wb.amap.com/?r=%f,%f,%s,%f,%f,%s,%d,%d,%d,%s,%s,%s&sourceapplication=openapi/0";
    private static String f4335c = "http://wb.amap.com/?q=%f,%f,%s&sourceapplication=openapi/0";
    private static String f4336d = "http://wb.amap.com/?n=%f,%f,%f,%f,%d&sourceapplication=openapi/0";
    private static String f4337e = "http://wb.amap.com/?p=%s,%f,%f,%s,%s&sourceapplication=openapi/0";
    private static final String f4338f = String.valueOf("");
    private Context f4339a;
    private OnShareSearchListener f4340g;

    public ap(Context context) {
        this.f4339a = context;
    }

    public void setOnShareSearchListener(OnShareSearchListener onShareSearchListener) {
        this.f4340g = onShareSearchListener;
    }

    public void searchPoiShareUrlAsyn(final PoiItem poiItem) {
        new Thread(this) {
            final /* synthetic */ ap f1303b;

            public void run() {
                if (this.f1303b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = AMapException.CODE_AMAP_ENGINE_RESPONSE_ERROR;
                    obtainMessage.obj = this.f1303b.f4340g;
                    try {
                        String searchPoiShareUrl = this.f1303b.searchPoiShareUrl(poiItem);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchPoiShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public void searchBusRouteShareUrlAsyn(final ShareBusRouteQuery shareBusRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ap f1305b;

            public void run() {
                if (this.f1305b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = AMapException.CODE_AMAP_ENGINE_RETURN_TIMEOUT;
                    obtainMessage.obj = this.f1305b.f4340g;
                    try {
                        String searchBusRouteShareUrl = this.f1305b.searchBusRouteShareUrl(shareBusRouteQuery);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchBusRouteShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public void searchWalkRouteShareUrlAsyn(final ShareWalkRouteQuery shareWalkRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ap f1307b;

            public void run() {
                if (this.f1307b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = 1105;
                    obtainMessage.obj = this.f1307b.f4340g;
                    try {
                        String searchWalkRouteShareUrl = this.f1307b.searchWalkRouteShareUrl(shareWalkRouteQuery);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchWalkRouteShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public void searchDrivingRouteShareUrlAsyn(final ShareDrivingRouteQuery shareDrivingRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ap f1309b;

            public void run() {
                if (this.f1309b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = 1104;
                    obtainMessage.obj = this.f1309b.f4340g;
                    try {
                        String searchDrivingRouteShareUrl = this.f1309b.searchDrivingRouteShareUrl(shareDrivingRouteQuery);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchDrivingRouteShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public void searchNaviShareUrlAsyn(final ShareNaviQuery shareNaviQuery) {
        new Thread(this) {
            final /* synthetic */ ap f1311b;

            public void run() {
                if (this.f1311b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = AMapException.CODE_AMAP_ENGINE_CONNECT_TIMEOUT;
                    obtainMessage.obj = this.f1311b.f4340g;
                    try {
                        String searchNaviShareUrl = this.f1311b.searchNaviShareUrl(shareNaviQuery);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchNaviShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public void searchLocationShareUrlAsyn(final LatLonSharePoint latLonSharePoint) {
        new Thread(this) {
            final /* synthetic */ ap f1313b;

            public void run() {
                if (this.f1313b.f4340g != null) {
                    Message obtainMessage = C0407q.m1654a().obtainMessage();
                    obtainMessage.arg1 = 11;
                    obtainMessage.what = AMapException.CODE_AMAP_ENGINE_RESPONSE_DATA_ERROR;
                    obtainMessage.obj = this.f1313b.f4340g;
                    try {
                        String searchLocationShareUrl = this.f1313b.searchLocationShareUrl(latLonSharePoint);
                        Bundle bundle = new Bundle();
                        bundle.putString("shareurlkey", searchLocationShareUrl);
                        obtainMessage.setData(bundle);
                        obtainMessage.arg2 = 1000;
                    } catch (AMapException e) {
                        obtainMessage.arg2 = e.getErrorCode();
                    } finally {
                        C0407q.m1654a().sendMessage(obtainMessage);
                    }
                }
            }
        }.start();
    }

    public String searchPoiShareUrl(PoiItem poiItem) throws AMapException {
        if (poiItem != null) {
            try {
                if (poiItem.getLatLonPoint() != null) {
                    LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                    return (String) new ab(this.f4339a, String.format(f4337e, new Object[]{poiItem.getPoiId(), Double.valueOf(latLonPoint.getLatitude()), Double.valueOf(latLonPoint.getLongitude()), poiItem.getTitle(), poiItem.getSnippet()})).m4358a();
                }
            } catch (Throwable e) {
                C0390i.m1594a(e, "ShareSearch", "searchPoiShareUrl");
                throw e;
            }
        }
        throw new AMapException("无效的参数 - IllegalArgumentException");
    }

    public String searchNaviShareUrl(ShareNaviQuery shareNaviQuery) throws AMapException {
        try {
            ShareFromAndTo fromAndTo = shareNaviQuery.getFromAndTo();
            if (fromAndTo.getTo() == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            String format;
            LatLonPoint from = fromAndTo.getFrom();
            LatLonPoint to = fromAndTo.getTo();
            int naviMode = shareNaviQuery.getNaviMode();
            if (fromAndTo.getFrom() == null) {
                format = String.format(f4336d, new Object[]{null, null, Double.valueOf(to.getLatitude()), Double.valueOf(to.getLongitude()), Integer.valueOf(naviMode)});
            } else {
                format = String.format(f4336d, new Object[]{Double.valueOf(from.getLatitude()), Double.valueOf(from.getLongitude()), Double.valueOf(to.getLatitude()), Double.valueOf(to.getLongitude()), Integer.valueOf(naviMode)});
            }
            return (String) new ab(this.f4339a, format).m4358a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareSearch", "searchNaviShareUrl");
            throw e;
        }
    }

    public String searchLocationShareUrl(LatLonSharePoint latLonSharePoint) throws AMapException {
        try {
            return (String) new ab(this.f4339a, String.format(f4335c, new Object[]{Double.valueOf(latLonSharePoint.getLatitude()), Double.valueOf(latLonSharePoint.getLongitude()), latLonSharePoint.getSharePointName()})).m4358a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareSearch", "searchLocationShareUrl");
            throw e;
        }
    }

    public String searchBusRouteShareUrl(ShareBusRouteQuery shareBusRouteQuery) throws AMapException {
        try {
            int busMode = shareBusRouteQuery.getBusMode();
            ShareFromAndTo shareFromAndTo = shareBusRouteQuery.getShareFromAndTo();
            if (shareFromAndTo.getFrom() == null || shareFromAndTo.getTo() == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            LatLonPoint from = shareFromAndTo.getFrom();
            LatLonPoint to = shareFromAndTo.getTo();
            String fromName = shareFromAndTo.getFromName();
            String toName = shareFromAndTo.getToName();
            return (String) new ab(this.f4339a, String.format(f4334b, new Object[]{Double.valueOf(from.getLatitude()), Double.valueOf(from.getLongitude()), fromName, Double.valueOf(to.getLatitude()), Double.valueOf(to.getLongitude()), toName, Integer.valueOf(busMode), Integer.valueOf(1), Integer.valueOf(0), f4338f, f4338f, f4338f})).m4358a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareSearch", "searchBusRouteShareUrl");
            throw e;
        }
    }

    public String searchDrivingRouteShareUrl(ShareDrivingRouteQuery shareDrivingRouteQuery) throws AMapException {
        try {
            int drivingMode = shareDrivingRouteQuery.getDrivingMode();
            ShareFromAndTo shareFromAndTo = shareDrivingRouteQuery.getShareFromAndTo();
            if (shareFromAndTo.getFrom() == null || shareFromAndTo.getTo() == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            LatLonPoint from = shareFromAndTo.getFrom();
            LatLonPoint to = shareFromAndTo.getTo();
            String fromName = shareFromAndTo.getFromName();
            String toName = shareFromAndTo.getToName();
            return (String) new ab(this.f4339a, String.format(f4334b, new Object[]{Double.valueOf(from.getLatitude()), Double.valueOf(from.getLongitude()), fromName, Double.valueOf(to.getLatitude()), Double.valueOf(to.getLongitude()), toName, Integer.valueOf(drivingMode), Integer.valueOf(0), Integer.valueOf(0), f4338f, f4338f, f4338f})).m4358a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareSearch", "searchDrivingRouteShareUrl");
            throw e;
        }
    }

    public String searchWalkRouteShareUrl(ShareWalkRouteQuery shareWalkRouteQuery) throws AMapException {
        try {
            int walkMode = shareWalkRouteQuery.getWalkMode();
            ShareFromAndTo shareFromAndTo = shareWalkRouteQuery.getShareFromAndTo();
            if (shareFromAndTo.getFrom() == null || shareFromAndTo.getTo() == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            LatLonPoint from = shareFromAndTo.getFrom();
            LatLonPoint to = shareFromAndTo.getTo();
            String fromName = shareFromAndTo.getFromName();
            String toName = shareFromAndTo.getToName();
            return (String) new ab(this.f4339a, String.format(f4334b, new Object[]{Double.valueOf(from.getLatitude()), Double.valueOf(from.getLongitude()), fromName, Double.valueOf(to.getLatitude()), Double.valueOf(to.getLongitude()), toName, Integer.valueOf(walkMode), Integer.valueOf(2), Integer.valueOf(0), f4338f, f4338f, f4338f})).m4358a();
        } catch (Throwable e) {
            C0390i.m1594a(e, "ShareSearch", "searchWalkRouteShareUrl");
            throw e;
        }
    }
}
