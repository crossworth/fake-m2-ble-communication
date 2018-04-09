package com.amap.api.mapcore.util;

import android.content.Context;
import com.autonavi.amap.mapcore.MapCore;
import com.autonavi.amap.mapcore.MapTilsCacheAndResManager;
import com.autonavi.amap.mapcore.MapTilsCacheAndResManager.RetStyleIconsFile;

/* compiled from: GLMapResManager */
public class C0284u {
    public boolean f738a = true;
    private C1592c f739b = null;
    private Context f740c = null;
    private MapCore f741d = null;

    /* compiled from: GLMapResManager */
    public enum C0281a {
        NORAML,
        SATELLITE,
        BUS
    }

    /* compiled from: GLMapResManager */
    public enum C0282b {
        NORMAL,
        PREVIEW_CAR,
        PREVIEW_BUS,
        PREVIEW_FOOT,
        NAVI_CAR,
        NAVI_BUS,
        NAVI_FOOT
    }

    /* compiled from: GLMapResManager */
    public enum C0283c {
        DAY,
        NIGHT
    }

    public C0284u(C1592c c1592c, Context context) {
        this.f739b = c1592c;
        this.f740c = context;
        this.f741d = this.f739b.getMapCore();
    }

    public void m1014a() {
        if (this.f739b != null) {
            RetStyleIconsFile retStyleIconsFile = new RetStyleIconsFile();
            byte[] styleData = MapTilsCacheAndResManager.getInstance(this.f740c).getStyleData(m1016b(), retStyleIconsFile);
            if (styleData != null) {
                this.f741d.setStyleData(styleData, 0, 1);
            }
            byte[] styleData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getStyleData("style_50_7_1445670996.data", retStyleIconsFile);
            if (styleData2 != null) {
                this.f741d.setStyleData(styleData2, 1, 1);
            }
        }
    }

    public void m1015a(boolean z) {
        if (this.f739b != null) {
            byte[] bArr = null;
            RetStyleIconsFile retStyleIconsFile = new RetStyleIconsFile();
            String c = m1018c();
            String a = m1011a(c);
            final byte[] iconsData = MapTilsCacheAndResManager.getInstance(this.f740c).getIconsData(c, retStyleIconsFile);
            if (this.f738a) {
                bArr = MapTilsCacheAndResManager.getInstance(this.f740c).getIconsData(a, new RetStyleIconsFile());
            }
            final byte[] iconsData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getIconsData("icons_50_7_1444880375.data", retStyleIconsFile);
            if (z) {
                if (iconsData != null) {
                    this.f741d.setInternaltexture(iconsData, 0);
                }
                if (iconsData2 != null) {
                    this.f741d.setInternaltexture(iconsData2, 31);
                }
                if (this.f738a && bArr != null) {
                    this.f741d.setInternaltexture(bArr, 20);
                    return;
                }
                return;
            }
            this.f739b.queueEvent(new Runnable(this) {
                final /* synthetic */ C0284u f707d;

                public void run() {
                    if (iconsData != null) {
                        this.f707d.f741d.setInternaltexture(iconsData, 0);
                    }
                    if (iconsData2 != null) {
                        this.f707d.f741d.setInternaltexture(iconsData2, 31);
                    }
                    if (this.f707d.f738a && bArr != null) {
                        this.f707d.f741d.setInternaltexture(bArr, 20);
                    }
                }
            });
        }
    }

    private String m1011a(String str) {
        if (str.equals("icons_1_7_1444880368.data")) {
            this.f738a = true;
            return "icons_4_6_1437480571.data";
        }
        this.f738a = false;
        return null;
    }

    public String m1016b() {
        String str = "";
        if (this.f739b == null) {
            return str;
        }
        C0283c h = this.f739b.m4154h();
        C0281a i = this.f739b.m4155i();
        C0282b j = this.f739b.m4156j();
        if (C0283c.DAY == h) {
            if (C0281a.NORAML == i) {
                if (C0282b.NAVI_CAR == j) {
                    return "style_4_7_1445391691.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                if (C0282b.PREVIEW_CAR == j) {
                    return "style_8_7_1445391734.data";
                }
                if (C0282b.NAVI_BUS == j) {
                    return "style_9_7_1445327958.data";
                }
                return "style_1_7_1445219169.data";
            } else if (C0281a.SATELLITE == i) {
                if (C0282b.NAVI_CAR == j) {
                    return "style_4_7_1445391691.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                return "style_3_7_1445827513.data";
            } else if (C0281a.BUS != i) {
                return str;
            } else {
                if (C0282b.NAVI_CAR == j) {
                    return "style_4_7_1445391691.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                return "style_6_7_1445325996.data";
            }
        } else if (C0283c.NIGHT != h) {
            return str;
        } else {
            if (C0281a.NORAML == i) {
                if (C0282b.NAVI_CAR == j) {
                    return "style_5_7_1445391719.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                return "style_1_7_1445219169.data";
            } else if (C0281a.SATELLITE == i) {
                if (C0282b.NAVI_CAR == j) {
                    return "style_5_7_1445391719.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                return "style_3_7_1445827513.data";
            } else if (C0281a.BUS != i) {
                return str;
            } else {
                if (C0282b.NAVI_CAR == j) {
                    return "style_5_7_1445391719.data";
                }
                if (C0282b.PREVIEW_BUS == j) {
                    return "style_6_7_1445325996.data";
                }
                return "style_6_7_1445325996.data";
            }
        }
    }

    public String m1018c() {
        String str = "";
        if (this.f739b == null) {
            return str;
        }
        C0283c h = this.f739b.m4154h();
        C0281a i = this.f739b.m4155i();
        if (C0283c.DAY == h) {
            if (C0281a.BUS == i) {
                return "icons_3_7_1444880372.data";
            }
            return "icons_1_7_1444880368.data";
        } else if (C0283c.NIGHT != h) {
            return str;
        } else {
            if (C0281a.BUS == i) {
                return "icons_3_7_1444880372.data";
            }
            return "icons_2_7_1445580283.data";
        }
    }

    public void m1017b(boolean z) {
        byte[] otherResData;
        byte[] otherResData2;
        byte[] otherResData3;
        byte[] otherResData4;
        byte[] otherResData5;
        if (this.f739b.m4154h() != C0283c.NIGHT) {
            otherResData = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tgl_l.data");
            otherResData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("trl_l.data");
            otherResData3 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tyl_l.data");
            otherResData4 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tbl_l.data");
            otherResData5 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tnl_l.data");
        } else {
            otherResData = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tgl_n.data");
            otherResData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("trl_n.data");
            otherResData3 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tyl_n.data");
            otherResData4 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tbl_n.data");
            otherResData5 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("tnl_n.data");
        }
        if (z) {
            m1013a(otherResData, otherResData2, otherResData3, otherResData4, otherResData5);
            return;
        }
        final byte[] bArr = otherResData;
        final byte[] bArr2 = otherResData2;
        final byte[] bArr3 = otherResData3;
        final byte[] bArr4 = otherResData4;
        final byte[] bArr5 = otherResData5;
        this.f739b.queueEvent(new Runnable(this) {
            final /* synthetic */ C0284u f713f;

            public void run() {
                this.f713f.m1013a(bArr, bArr2, bArr3, bArr4, bArr5);
            }
        });
    }

    private void m1013a(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        if (bArr != null) {
            this.f741d.setInternaltexture(bArr, 6);
        }
        if (bArr2 != null) {
            this.f741d.setInternaltexture(bArr2, 4);
        }
        if (bArr3 != null) {
            this.f741d.setInternaltexture(bArr3, 5);
        }
        if (bArr4 != null) {
            this.f741d.setInternaltexture(bArr4, 7);
        }
        if (bArr5 != null) {
            this.f741d.setInternaltexture(bArr5, 18);
        }
    }

    public void m1019c(boolean z) {
        byte[] otherResData;
        byte[] otherResData2;
        if (this.f739b.m4154h() != C0283c.NIGHT) {
            otherResData = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("bktile.data");
            otherResData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("3d_sky_day.dat");
        } else {
            otherResData = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("bktile_n.data");
            otherResData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("3d_sky_night.dat");
        }
        if (z) {
            this.f741d.setInternaltexture(otherResData, 1);
            this.f741d.setInternaltexture(otherResData2, 41);
            return;
        }
        this.f739b.queueEvent(new Runnable(this) {
            final /* synthetic */ C0284u f716c;

            public void run() {
                this.f716c.f741d.setInternaltexture(otherResData, 1);
                this.f716c.f741d.setInternaltexture(otherResData2, 41);
            }
        });
    }

    public void m1020d(boolean z) {
    }

    public void m1021e(boolean z) {
        final byte[] otherResData = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("roadarrow.data");
        final byte[] otherResData2 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("lineround.data");
        final byte[] otherResData3 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("dash.data");
        final byte[] otherResData4 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("dash_tq.data");
        final byte[] otherResData5 = MapTilsCacheAndResManager.getInstance(this.f740c).getOtherResData("dash_cd.data");
        if (z) {
            this.f741d.setInternaltexture(otherResData, 2);
            this.f741d.setInternaltexture(otherResData2, 3);
            this.f741d.setInternaltexture(otherResData3, 8);
            this.f741d.setInternaltexture(otherResData4, 9);
            this.f741d.setInternaltexture(otherResData5, 10);
            return;
        }
        this.f739b.queueEvent(new Runnable(this) {
            final /* synthetic */ C0284u f722f;

            public void run() {
                this.f722f.f741d.setInternaltexture(otherResData, 2);
                this.f722f.f741d.setInternaltexture(otherResData2, 3);
                this.f722f.f741d.setInternaltexture(otherResData3, 8);
                this.f722f.f741d.setInternaltexture(otherResData4, 9);
                this.f722f.f741d.setInternaltexture(otherResData5, 10);
            }
        });
    }
}
