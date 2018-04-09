package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import com.amap.api.mapcore.util.cv.C0237d;
import com.amap.api.mapcore.util.da.C0240a;
import com.amap.api.mapcore.util.dd.C1596a;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.TileProvider;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.ITileOverlayDelegate;
import com.umeng.socialize.common.SocializeConstants;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TileOverlayDelegateImp */
public class av implements ITileOverlayDelegate {
    private static int f4010g = 0;
    private aw f4011a;
    private TileProvider f4012b;
    private Float f4013c;
    private boolean f4014d;
    private boolean f4015e;
    private IAMapDelegate f4016f;
    private int f4017h;
    private int f4018i;
    private int f4019j;
    private db f4020k;
    private CopyOnWriteArrayList<C0199a> f4021l;
    private boolean f4022m;
    private C1585b f4023n;
    private String f4024o;
    private FloatBuffer f4025p;

    /* compiled from: TileOverlayDelegateImp */
    public class C0199a implements Cloneable {
        public int f188a;
        public int f189b;
        public int f190c;
        public int f191d;
        public IPoint f192e;
        public int f193f = 0;
        public boolean f194g = false;
        public FloatBuffer f195h = null;
        public Bitmap f196i = null;
        public C1596a f197j = null;
        public int f198k = 0;
        final /* synthetic */ av f199l;

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return m212a();
        }

        public C0199a(av avVar, int i, int i2, int i3, int i4) {
            this.f199l = avVar;
            this.f188a = i;
            this.f189b = i2;
            this.f190c = i3;
            this.f191d = i4;
        }

        public C0199a(av avVar, C0199a c0199a) {
            this.f199l = avVar;
            this.f188a = c0199a.f188a;
            this.f189b = c0199a.f189b;
            this.f190c = c0199a.f190c;
            this.f191d = c0199a.f191d;
            this.f192e = c0199a.f192e;
            this.f195h = c0199a.f195h;
        }

        public C0199a m212a() {
            try {
                C0199a c0199a = (C0199a) super.clone();
                c0199a.f188a = this.f188a;
                c0199a.f189b = this.f189b;
                c0199a.f190c = this.f190c;
                c0199a.f191d = this.f191d;
                c0199a.f192e = (IPoint) this.f192e.clone();
                c0199a.f195h = this.f195h.asReadOnlyBuffer();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new C0199a(this.f199l, this);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0199a)) {
                return false;
            }
            C0199a c0199a = (C0199a) obj;
            if (this.f188a == c0199a.f188a && this.f189b == c0199a.f189b && this.f190c == c0199a.f190c && this.f191d == c0199a.f191d) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((this.f188a * 7) + (this.f189b * 11)) + (this.f190c * 13)) + this.f191d;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.f188a);
            stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
            stringBuilder.append(this.f189b);
            stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
            stringBuilder.append(this.f190c);
            stringBuilder.append(SocializeConstants.OP_DIVIDER_MINUS);
            stringBuilder.append(this.f191d);
            return stringBuilder.toString();
        }

        public void m213a(Bitmap bitmap) {
            if (bitmap != null && !bitmap.isRecycled()) {
                try {
                    this.f197j = null;
                    this.f196i = dj.m566a(bitmap, dj.m559a(bitmap.getWidth()), dj.m559a(bitmap.getHeight()));
                    this.f199l.f4016f.setRunLowFrame(false);
                } catch (Throwable th) {
                    ee.m4243a(th, "TileOverlayDelegateImp", "setBitmap");
                    th.printStackTrace();
                    if (this.f198k < 3) {
                        this.f199l.f4020k.m523a(true, this);
                        this.f198k++;
                    }
                }
            } else if (this.f198k < 3) {
                this.f199l.f4020k.m523a(true, this);
                this.f198k++;
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        public void m214b() {
            try {
                dd.m514a(this);
                if (this.f194g) {
                    this.f199l.f4011a.f202c.add(Integer.valueOf(this.f193f));
                }
                this.f194g = false;
                this.f193f = 0;
                if (!(this.f196i == null || this.f196i.isRecycled())) {
                    this.f196i.recycle();
                }
                this.f196i = null;
                if (this.f195h != null) {
                    this.f195h.clear();
                }
                this.f195h = null;
                this.f197j = null;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* compiled from: TileOverlayDelegateImp */
    private class C1585b extends cv<IAMapDelegate, Void, List<C0199a>> {
        final /* synthetic */ av f4007a;
        private int f4008e;
        private boolean f4009f;

        public C1585b(av avVar, boolean z) {
            this.f4007a = avVar;
            this.f4009f = z;
        }

        protected List<C0199a> m3988a(IAMapDelegate... iAMapDelegateArr) {
            List<C0199a> list = null;
            try {
                int mapWidth = iAMapDelegateArr[0].getMapWidth();
                int mapHeight = iAMapDelegateArr[0].getMapHeight();
                this.f4008e = (int) iAMapDelegateArr[0].getZoomLevel();
                if (mapWidth > 0 && mapHeight > 0) {
                    list = this.f4007a.m3993a(this.f4008e, mapWidth, mapHeight);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return list;
        }

        protected void m3990a(List<C0199a> list) {
            if (list != null) {
                try {
                    if (list.size() > 0) {
                        this.f4007a.m3998a((List) list, this.f4008e, this.f4009f);
                        list.clear();
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static String m3992a(String str) {
        f4010g++;
        return str + f4010g;
    }

    public av(TileOverlayOptions tileOverlayOptions, aw awVar) {
        this.f4015e = false;
        this.f4017h = 256;
        this.f4018i = 256;
        this.f4019j = -1;
        this.f4021l = new CopyOnWriteArrayList();
        this.f4022m = false;
        this.f4023n = null;
        this.f4024o = null;
        this.f4025p = null;
        this.f4011a = awVar;
        this.f4012b = tileOverlayOptions.getTileProvider();
        this.f4017h = this.f4012b.getTileWidth();
        this.f4018i = this.f4012b.getTileHeight();
        int a = dj.m559a(this.f4017h);
        float f = ((float) this.f4017h) / ((float) a);
        float a2 = ((float) this.f4018i) / ((float) dj.m559a(this.f4018i));
        this.f4025p = dj.m575a(new float[]{0.0f, a2, f, a2, f, 0.0f, 0.0f, 0.0f});
        this.f4013c = Float.valueOf(tileOverlayOptions.getZIndex());
        this.f4014d = tileOverlayOptions.isVisible();
        this.f4024o = getId();
        this.f4016f = this.f4011a.m215a();
        this.f4019j = Integer.valueOf(this.f4024o.substring("TileOverlay".length())).intValue();
        C0240a c0240a = new C0240a(this.f4011a.getContext(), this.f4024o);
        c0240a.m491a(tileOverlayOptions.getMemoryCacheEnabled());
        c0240a.m493b(tileOverlayOptions.getDiskCacheEnabled());
        c0240a.m489a(tileOverlayOptions.getMemCacheSize());
        c0240a.m492b(tileOverlayOptions.getDiskCacheSize());
        String diskCacheDir = tileOverlayOptions.getDiskCacheDir();
        if (!(diskCacheDir == null || diskCacheDir.equals(""))) {
            c0240a.m490a(diskCacheDir);
        }
        this.f4020k = new db(this.f4011a.getContext(), this.f4017h, this.f4018i);
        this.f4020k.m5763a(this.f4012b);
        this.f4020k.m521a(c0240a);
        refresh(true);
    }

    public av(TileOverlayOptions tileOverlayOptions, aw awVar, boolean z) {
        this(tileOverlayOptions, awVar);
        this.f4015e = z;
    }

    public void remove() {
        if (this.f4023n != null && this.f4023n.m462a() == C0237d.RUNNING) {
            this.f4023n.m466a(true);
        }
        Iterator it = this.f4021l.iterator();
        while (it.hasNext()) {
            ((C0199a) it.next()).m214b();
        }
        this.f4021l.clear();
        this.f4020k.m531h();
        this.f4011a.m221b((ITileOverlayDelegate) this);
        this.f4016f.setRunLowFrame(false);
    }

    public void clearTileCache() {
        this.f4020k.m529f();
    }

    public String getId() {
        if (this.f4024o == null) {
            this.f4024o = m3992a("TileOverlay");
        }
        return this.f4024o;
    }

    public void setZIndex(float f) {
        this.f4013c = Float.valueOf(f);
        this.f4011a.m222c();
    }

    public float getZIndex() {
        return this.f4013c.floatValue();
    }

    public void setVisible(boolean z) {
        this.f4014d = z;
        this.f4016f.setRunLowFrame(false);
        if (z) {
            refresh(true);
        }
    }

    public boolean isVisible() {
        return this.f4014d;
    }

    public boolean equalsRemote(ITileOverlayDelegate iTileOverlayDelegate) {
        if (equals(iTileOverlayDelegate) || iTileOverlayDelegate.getId().equals(getId())) {
            return true;
        }
        return false;
    }

    public int hashCodeRemote() {
        return super.hashCode();
    }

    private boolean m3996a(C0199a c0199a) {
        MapProjection mapProjection = this.f4016f.getMapProjection();
        float f = (float) c0199a.f190c;
        int i = this.f4017h;
        int i2 = this.f4018i;
        int i3 = c0199a.f192e.f2030x;
        int i4 = c0199a.f192e.f2031y + ((1 << (20 - ((int) f))) * i2);
        r6 = new float[12];
        FPoint fPoint = new FPoint();
        mapProjection.geo2Map(i3, i4, fPoint);
        FPoint fPoint2 = new FPoint();
        mapProjection.geo2Map(((1 << (20 - ((int) f))) * i) + i3, i4, fPoint2);
        FPoint fPoint3 = new FPoint();
        mapProjection.geo2Map((i * (1 << (20 - ((int) f)))) + i3, i4 - ((1 << (20 - ((int) f))) * i2), fPoint3);
        FPoint fPoint4 = new FPoint();
        mapProjection.geo2Map(i3, i4 - ((1 << (20 - ((int) f))) * i2), fPoint4);
        r6[0] = fPoint.f2028x;
        r6[1] = fPoint.f2029y;
        r6[2] = 0.0f;
        r6[3] = fPoint2.f2028x;
        r6[4] = fPoint2.f2029y;
        r6[5] = 0.0f;
        r6[6] = fPoint3.f2028x;
        r6[7] = fPoint3.f2029y;
        r6[8] = 0.0f;
        r6[9] = fPoint4.f2028x;
        r6[10] = fPoint4.f2029y;
        r6[11] = 0.0f;
        if (c0199a.f195h == null) {
            c0199a.f195h = dj.m575a(r6);
        } else {
            c0199a.f195h = dj.m576a(r6, c0199a.f195h);
        }
        return true;
    }

    private void m3995a(GL10 gl10, int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (floatBuffer != null && floatBuffer2 != null) {
            gl10.glEnable(3042);
            gl10.glTexEnvf(8960, 8704, 8448.0f);
            gl10.glBlendFunc(1, 771);
            gl10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            gl10.glEnable(3553);
            gl10.glEnableClientState(32884);
            gl10.glEnableClientState(32888);
            gl10.glBindTexture(3553, i);
            gl10.glVertexPointer(3, 5126, 0, floatBuffer);
            gl10.glTexCoordPointer(2, 5126, 0, floatBuffer2);
            gl10.glDrawArrays(6, 0, 4);
            gl10.glDisableClientState(32884);
            gl10.glDisableClientState(32888);
            gl10.glDisable(3553);
            gl10.glDisable(3042);
        }
    }

    public void drawTiles(GL10 gl10) {
        if (this.f4021l != null && this.f4021l.size() != 0) {
            Iterator it = this.f4021l.iterator();
            while (it.hasNext()) {
                C0199a c0199a = (C0199a) it.next();
                if (!c0199a.f194g) {
                    try {
                        IPoint iPoint = c0199a.f192e;
                        if (!(c0199a.f196i == null || c0199a.f196i.isRecycled() || iPoint == null)) {
                            c0199a.f193f = dj.m561a(gl10, c0199a.f196i);
                            if (c0199a.f193f != 0) {
                                c0199a.f194g = true;
                            }
                            c0199a.f196i = null;
                        }
                    } catch (Throwable th) {
                        ee.m4243a(th, "TileOverlayDelegateImp", "drawTiles");
                    }
                }
                if (c0199a.f194g) {
                    m3996a(c0199a);
                    m3995a(gl10, c0199a.f193f, c0199a.f195h, this.f4025p);
                }
            }
        }
    }

    private ArrayList<C0199a> m3993a(int i, int i2, int i3) {
        MapProjection mapProjection = this.f4016f.getMapProjection();
        FPoint fPoint = new FPoint();
        IPoint iPoint = new IPoint();
        IPoint iPoint2 = new IPoint();
        mapProjection.win2Map(0, 0, fPoint);
        mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        int min = Math.min(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, iPoint.f2030x);
        int max = Math.max(0, iPoint.f2030x);
        int min2 = Math.min(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, iPoint.f2031y);
        int max2 = Math.max(0, iPoint.f2031y);
        mapProjection.win2Map(i2, 0, fPoint);
        mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        min = Math.min(min, iPoint.f2030x);
        max = Math.max(max, iPoint.f2030x);
        min2 = Math.min(min2, iPoint.f2031y);
        max2 = Math.max(max2, iPoint.f2031y);
        mapProjection.win2Map(0, i3, fPoint);
        mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        min = Math.min(min, iPoint.f2030x);
        max = Math.max(max, iPoint.f2030x);
        min2 = Math.min(min2, iPoint.f2031y);
        max2 = Math.max(max2, iPoint.f2031y);
        mapProjection.win2Map(i2, i3, fPoint);
        mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        min = Math.min(min, iPoint.f2030x);
        int max3 = Math.max(max, iPoint.f2030x);
        max = Math.min(min2, iPoint.f2031y);
        int max4 = Math.max(max2, iPoint.f2031y);
        int i4 = min - ((1 << (20 - i)) * this.f4017h);
        int i5 = max - ((1 << (20 - i)) * this.f4018i);
        mapProjection.getGeoCenter(iPoint2);
        max = (iPoint2.f2030x >> (20 - i)) / this.f4017h;
        min2 = (iPoint2.f2031y >> (20 - i)) / this.f4018i;
        int i6 = (max << (20 - i)) * this.f4017h;
        int i7 = (min2 << (20 - i)) * this.f4018i;
        C0199a c0199a = new C0199a(this, max, min2, i, this.f4019j);
        c0199a.f192e = new IPoint(i6, i7);
        ArrayList<C0199a> arrayList = new ArrayList();
        arrayList.add(c0199a);
        min = 1;
        while (true) {
            Object obj = null;
            for (i6 = max - min; i6 <= max + min; i6++) {
                i7 = min2 + min;
                IPoint iPoint3 = new IPoint((i6 << (20 - i)) * this.f4017h, (i7 << (20 - i)) * this.f4018i);
                if (iPoint3.f2030x < max3 && iPoint3.f2030x > i4 && iPoint3.f2031y < max4 && iPoint3.f2031y > i5) {
                    if (obj == null) {
                        obj = 1;
                    }
                    C0199a c0199a2 = new C0199a(this, i6, i7, i, this.f4019j);
                    c0199a2.f192e = iPoint3;
                    arrayList.add(c0199a2);
                }
                i7 = min2 - min;
                iPoint3 = new IPoint((i6 << (20 - i)) * this.f4017h, (i7 << (20 - i)) * this.f4018i);
                if (iPoint3.f2030x < max3 && iPoint3.f2030x > i4 && iPoint3.f2031y < max4 && iPoint3.f2031y > i5) {
                    if (obj == null) {
                        obj = 1;
                    }
                    c0199a2 = new C0199a(this, i6, i7, i, this.f4019j);
                    c0199a2.f192e = iPoint3;
                    arrayList.add(c0199a2);
                }
            }
            for (i7 = (min2 + min) - 1; i7 > min2 - min; i7--) {
                i6 = max + min;
                iPoint3 = new IPoint((i6 << (20 - i)) * this.f4017h, (i7 << (20 - i)) * this.f4018i);
                if (iPoint3.f2030x < max3 && iPoint3.f2030x > i4 && iPoint3.f2031y < max4 && iPoint3.f2031y > i5) {
                    if (obj == null) {
                        obj = 1;
                    }
                    c0199a2 = new C0199a(this, i6, i7, i, this.f4019j);
                    c0199a2.f192e = iPoint3;
                    arrayList.add(c0199a2);
                }
                i6 = max - min;
                iPoint3 = new IPoint((i6 << (20 - i)) * this.f4017h, (i7 << (20 - i)) * this.f4018i);
                if (iPoint3.f2030x < max3 && iPoint3.f2030x > i4 && iPoint3.f2031y < max4 && iPoint3.f2031y > i5) {
                    if (obj == null) {
                        obj = 1;
                    }
                    c0199a2 = new C0199a(this, i6, i7, i, this.f4019j);
                    c0199a2.f192e = iPoint3;
                    arrayList.add(c0199a2);
                }
            }
            if (obj == null) {
                return arrayList;
            }
            min++;
        }
    }

    private boolean m3998a(List<C0199a> list, int i, boolean z) {
        int i2 = 0;
        if (list == null) {
            return false;
        }
        if (this.f4021l == null) {
            return false;
        }
        int i3;
        Iterator it = this.f4021l.iterator();
        while (it.hasNext()) {
            C0199a c0199a = (C0199a) it.next();
            for (C0199a c0199a2 : list) {
                if (c0199a.equals(c0199a2) && c0199a.f194g) {
                    c0199a2.f194g = c0199a.f194g;
                    c0199a2.f193f = c0199a.f193f;
                    i3 = 1;
                    break;
                }
            }
            i3 = 0;
            if (i3 == 0) {
                c0199a.m214b();
            }
        }
        this.f4021l.clear();
        if (i > ((int) this.f4016f.getMaxZoomLevel()) || i < ((int) this.f4016f.getMinZoomLevel())) {
            return false;
        }
        i3 = list.size();
        if (i3 <= 0) {
            return false;
        }
        while (i2 < i3) {
            c0199a = (C0199a) list.get(i2);
            if (c0199a != null && (!this.f4015e || (c0199a.f190c >= 10 && !dg.m546a(c0199a.f188a, c0199a.f189b, c0199a.f190c)))) {
                this.f4021l.add(c0199a);
                if (!c0199a.f194g) {
                    this.f4020k.m523a(z, c0199a);
                }
            }
            i2++;
        }
        return true;
    }

    public void refresh(boolean z) {
        if (!this.f4022m) {
            if (this.f4023n != null && this.f4023n.m462a() == C0237d.RUNNING) {
                this.f4023n.m466a(true);
            }
            this.f4023n = new C1585b(this, z);
            this.f4023n.m470c((Object[]) new IAMapDelegate[]{this.f4016f});
        }
    }

    public void onPause() {
        this.f4020k.m525b(false);
        this.f4020k.m522a(true);
        this.f4020k.m530g();
    }

    public void onResume() {
        this.f4020k.m522a(false);
    }

    public void onFling(boolean z) {
        if (this.f4022m != z) {
            this.f4022m = z;
            this.f4020k.m525b(z);
        }
    }

    public void reLoadTexture() {
        if (this.f4021l != null && this.f4021l.size() != 0) {
            Iterator it = this.f4021l.iterator();
            while (it.hasNext()) {
                C0199a c0199a = (C0199a) it.next();
                c0199a.f194g = false;
                c0199a.f193f = 0;
            }
        }
    }
}
