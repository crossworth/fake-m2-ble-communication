package p031u.aly;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: UMEntry */
public class av implements Serializable {
    public static long f3691c = 0;
    private static final long f3692d = -5254997387189944418L;
    public C1472n f3693a = new C1472n();
    public C1471m f3694b = new C1471m();

    /* compiled from: UMEntry */
    public static class C1461a {
        public static final int f3598a = 0;
        public static final int f3599b = 1;
        public static final int f3600c = 2;
    }

    /* compiled from: UMEntry */
    public static class C1462b implements Serializable {
        private static final long f3601b = 395432415169525323L;
        public long f3602a = 0;
    }

    /* compiled from: UMEntry */
    public static class C1463c implements Serializable {
        private static final long f3603c = -6648526015472635581L;
        public String f3604a = null;
        public String f3605b = null;
    }

    /* compiled from: UMEntry */
    public static class C1464d implements Serializable {
        private static final long f3606c = -4761083466478982295L;
        public Map<String, List<C1465e>> f3607a = new HashMap();
        public Map<String, List<C1466f>> f3608b = new HashMap();
    }

    /* compiled from: UMEntry */
    public static class C1465e implements Serializable {
        private static final long f3609f = 8614138410597604223L;
        public long f3610a = 0;
        public long f3611b = 0;
        public int f3612c = 0;
        public int f3613d = 0;
        public List<String> f3614e = new ArrayList();
    }

    /* compiled from: UMEntry */
    public static final class C1466f implements Serializable {
        private static final long f3615d = -7569163627707250811L;
        public int f3616a = 0;
        public long f3617b = 0;
        public String f3618c = null;
    }

    /* compiled from: UMEntry */
    public static class C1467g implements Serializable {
        private static final long f3619d = -1010993116426830703L;
        public Integer f3620a = Integer.valueOf(0);
        public long f3621b = 0;
        public boolean f3622c = false;
    }

    /* compiled from: UMEntry */
    public static class C1468h implements Serializable {
        private static final long f3623c = -7833224895044623144L;
        public String f3624a = null;
        public List<C1922j> f3625b = new ArrayList();
    }

    /* compiled from: UMEntry */
    public static final class C1469k implements Serializable {
        private static final long f3626d = -1397960951960451474L;
        public double f3627a = 0.0d;
        public double f3628b = 0.0d;
        public long f3629c = 0;
    }

    /* compiled from: UMEntry */
    public static final class C1470l implements Serializable {
        private static final long f3630e = 2506525905874738341L;
        public String f3631a = null;
        public long f3632b = 0;
        public long f3633c = 0;
        public long f3634d = 0;
    }

    /* compiled from: UMEntry */
    public static class C1471m implements Serializable {
        private static final long f3635k = 5703014667657688269L;
        public List<C1468h> f3636a = new ArrayList();
        public List<C1468h> f3637b = new ArrayList();
        public List<C1923o> f3638c = new ArrayList();
        public C1462b f3639d = new C1462b();
        public C1467g f3640e = new C1467g();
        public Map<String, Integer> f3641f = new HashMap();
        public C1463c f3642g = new C1463c();
        public C1464d f3643h = new C1464d();
        public List<C1921i> f3644i = new ArrayList();
        public String f3645j = null;
    }

    /* compiled from: UMEntry */
    public static class C1472n implements Serializable {
        private static final long f3646P = 4568484649280698573L;
        public String f3647A = Build.DEVICE;
        public String f3648B = null;
        public String f3649C = null;
        public long f3650D = 8;
        public String f3651E = null;
        public String f3652F = null;
        public String f3653G = null;
        public String f3654H = null;
        public String f3655I = null;
        public String f3656J = null;
        public long f3657K = 0;
        public long f3658L = 0;
        public long f3659M = 0;
        public String f3660N = null;
        public String f3661O = null;
        public String f3662a = null;
        public String f3663b = null;
        public String f3664c = null;
        public String f3665d = null;
        public String f3666e = null;
        public String f3667f = null;
        public String f3668g = null;
        public int f3669h = 0;
        public String f3670i = AnalyticsConfig.mWrapperType;
        public String f3671j = AnalyticsConfig.mWrapperVersion;
        public String f3672k = "Android";
        public String f3673l = null;
        public int f3674m = 0;
        public String f3675n = null;
        public String f3676o = bj.m3512a();
        public String f3677p = "Android";
        public String f3678q = VERSION.RELEASE;
        public String f3679r = null;
        public String f3680s = null;
        public String f3681t = null;
        public String f3682u = Build.MODEL;
        public String f3683v = Build.BOARD;
        public String f3684w = Build.BRAND;
        public long f3685x = Build.TIME;
        public String f3686y = Build.MANUFACTURER;
        public String f3687z = Build.ID;
    }

    /* compiled from: UMEntry */
    public static final class C1473p implements Serializable {
        private static final long f3688c = -7629272972021970177L;
        public long f3689a = 0;
        public long f3690b = 0;
    }

    /* compiled from: UMEntry */
    public static class C1921i implements Serializable, ai {
        private static final long f4914d = -7911804253674023187L;
        public long f4915a = 0;
        public long f4916b = 0;
        public String f4917c = null;

        public void mo2760a(av avVar) {
            if (avVar.f3694b.f3644i != null) {
                avVar.f3694b.f3644i.add(this);
            }
        }
    }

    /* compiled from: UMEntry */
    public static class C1922j implements Serializable, ai {
        private static final long f4918g = -1062440179015494286L;
        public int f4919a = 0;
        public String f4920b = null;
        public String f4921c = null;
        public long f4922d = 0;
        public long f4923e = 0;
        public Map<String, Object> f4924f = new HashMap();

        public void mo2760a(av avVar) {
            int i;
            C1468h c1468h;
            int i2 = 0;
            if (this.f4920b == null) {
                this.f4920b = ar.m3465a();
            }
            if (avVar.f3694b.f3636a != null) {
                try {
                    if (this.f4919a == 1) {
                        int size = avVar.f3694b.f3636a.size();
                        if (size > 0) {
                            i = 0;
                            while (i < size) {
                                c1468h = (C1468h) avVar.f3694b.f3636a.get(i);
                                if (TextUtils.isEmpty(c1468h.f3624a) || !c1468h.f3624a.equals(this.f4920b)) {
                                    i++;
                                } else {
                                    avVar.f3694b.f3636a.remove(c1468h);
                                    c1468h.f3625b.add(this);
                                    avVar.f3694b.f3636a.add(c1468h);
                                    return;
                                }
                            }
                            c1468h = new C1468h();
                            c1468h.f3624a = this.f4920b;
                            c1468h.f3625b.add(this);
                            if (!avVar.f3694b.f3636a.contains(c1468h)) {
                                avVar.f3694b.f3636a.add(c1468h);
                            }
                        } else {
                            c1468h = new C1468h();
                            c1468h.f3624a = this.f4920b;
                            c1468h.f3625b.add(this);
                            avVar.f3694b.f3636a.add(c1468h);
                        }
                    }
                } catch (Throwable th) {
                    bl.m3598e(th);
                }
            }
            if (avVar.f3694b.f3637b == null) {
                return;
            }
            if (this.f4919a == 2) {
                i = avVar.f3694b.f3637b.size();
                if (i > 0) {
                    while (i2 < i) {
                        c1468h = (C1468h) avVar.f3694b.f3637b.get(i2);
                        if (TextUtils.isEmpty(c1468h.f3624a) || !c1468h.f3624a.equals(this.f4920b)) {
                            i2++;
                        } else {
                            avVar.f3694b.f3637b.remove(c1468h);
                            c1468h.f3625b.add(this);
                            avVar.f3694b.f3637b.add(c1468h);
                            return;
                        }
                    }
                    c1468h = new C1468h();
                    c1468h.f3624a = this.f4920b;
                    c1468h.f3625b.add(this);
                    avVar.f3694b.f3637b.add(c1468h);
                    return;
                }
                c1468h = new C1468h();
                c1468h.f3624a = this.f4920b;
                c1468h.f3625b.add(this);
                avVar.f3694b.f3637b.add(c1468h);
            }
        }
    }

    /* compiled from: UMEntry */
    public static class C1923o implements Serializable, ai {
        public static Map<String, C1470l> f4925g = new HashMap();
        private static final long f4926k = 8683938900576888953L;
        public int f4927a = 0;
        public String f4928b = null;
        public long f4929c = 0;
        public long f4930d = 0;
        public long f4931e = 0;
        public boolean f4932f = false;
        public List<C1470l> f4933h = new ArrayList();
        public C1473p f4934i = new C1473p();
        public C1469k f4935j = new C1469k();

        public void mo2760a(av avVar) {
            if (avVar.f3694b.f3638c != null) {
                avVar.f3694b.f3638c.add(this);
            }
        }
    }

    public boolean m3481a() {
        if (this.f3693a.f3681t == null || this.f3693a.f3680s == null || this.f3693a.f3679r == null || this.f3693a.f3662a == null || this.f3693a.f3663b == null || this.f3693a.f3667f == null || this.f3693a.f3666e == null || this.f3693a.f3665d == null) {
            return false;
        }
        return true;
    }

    public void m3482b() {
        this.f3693a = new C1472n();
        this.f3694b = new C1471m();
        f3691c = 0;
    }
}
