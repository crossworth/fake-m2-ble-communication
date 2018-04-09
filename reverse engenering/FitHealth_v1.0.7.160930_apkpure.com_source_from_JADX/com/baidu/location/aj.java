package com.baidu.location;

import android.location.Location;
import android.net.wifi.ScanResult;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.baidu.location.ai.C0503b;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class aj implements C0527k, an, C1619j {
    private long gd = 0;
    private Location ge = null;
    private Queue gf = new LinkedList();
    private List gg = null;

    public void bh() {
        this.gg = null;
        this.ge = null;
        this.gf.clear();
    }

    public boolean mo3051do(C0503b c0503b) {
        if (c0503b == null) {
            return false;
        }
        List list = c0503b.f2165for;
        List list2 = this.gg;
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            this.gg = list;
            this.gd = System.currentTimeMillis();
            return false;
        }
        Collection linkedList = new LinkedList();
        int size = list.size();
        int size2 = list2.size();
        if (size == 0 && size2 == 0) {
            return true;
        }
        if (size == 0 || size2 == 0) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        while (i < size) {
            int i3;
            String str = ((ScanResult) list.get(i)).BSSID;
            if (str == null) {
                i3 = i2;
            } else {
                int i4 = 0;
                while (i4 < size2) {
                    if (str.equals(((ScanResult) list2.get(i4)).BSSID)) {
                        i3 = i2 + 1;
                        break;
                    }
                    i4++;
                }
                i3 = i2;
                if (i4 == size2) {
                    linkedList.add(list.get(i));
                }
            }
            i++;
            i2 = i3;
        }
        if (((float) i2) >= ((float) size) * C1974b.aN) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.gd > ((long) C1974b.aP)) {
            this.gg = list;
            this.gd = currentTimeMillis;
        } else {
            this.gg.addAll(linkedList);
        }
        return false;
    }

    public int mo3052new(BDLocation bDLocation) {
        double d = 0.0d;
        Location location = new Location("gps");
        Location location2 = new Location("gps");
        try {
            if (bDLocation.getLocType() != BDLocation.TypeNetWorkLocation) {
                bh();
                return 2;
            } else if (bDLocation.getRadius() > BitmapDescriptorFactory.HUE_MAGENTA) {
                bh();
                return 1;
            } else {
                location2.setLatitude(bDLocation.getLatitude());
                location2.setLongitude(bDLocation.getLongitude());
                location2.setTime(System.currentTimeMillis());
                if (this.gf.size() != 0 && location2.getTime() - ((Location) this.gf.peek()).getTime() > ((long) C1974b.aH)) {
                    this.gf.clear();
                }
                this.gf.offer(location2);
                double d2 = 0.0d;
                for (Location location3 : this.gf) {
                    d2 += location3.getLongitude();
                    d += location3.getLatitude();
                }
                d2 /= (double) this.gf.size();
                d /= (double) this.gf.size();
                location.setLongitude(d2);
                location.setLatitude(d);
                int i = (this.ge == null || location.distanceTo(this.ge) >= C1974b.aU) ? 2 : 3;
                if (i != 2) {
                    return i;
                }
                this.ge = new Location("gps");
                this.ge.setLongitude(d2);
                this.ge.setLatitude(d);
                bDLocation.setLatitude(d);
                bDLocation.setLongitude(d2);
                return i;
            }
        } catch (Exception e) {
            return 1;
        }
    }
}
