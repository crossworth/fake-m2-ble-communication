package com.baidu.location.p011e;

import android.content.pm.ProviderInfo;
import android.database.Cursor;
import com.baidu.location.BDLocation;
import com.baidu.location.p011e.C0429j.C0428a;
import java.util.concurrent.Callable;

class C0427i implements Callable<BDLocation> {
    final /* synthetic */ String[] f684a;
    final /* synthetic */ C0426h f685b;

    C0427i(C0426h c0426h, String[] strArr) {
        this.f685b = c0426h;
        this.f684a = strArr;
    }

    public BDLocation m791a() {
        BDLocation a;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        BDLocation bDLocation = new BDLocation();
        if (this.f684a.length > 0) {
            ProviderInfo resolveContentProvider;
            try {
                resolveContentProvider = C0426h.f677c.getPackageManager().resolveContentProvider(C0426h.f676b, 0);
            } catch (Exception e) {
                resolveContentProvider = null;
            }
            if (resolveContentProvider == null) {
                String[] o = this.f685b.f683i.m762o();
                for (String resolveContentProvider2 : o) {
                    try {
                        resolveContentProvider = C0426h.f677c.getPackageManager().resolveContentProvider(resolveContentProvider2, 0);
                    } catch (Exception e2) {
                        resolveContentProvider = null;
                    }
                    if (resolveContentProvider != null) {
                        break;
                    }
                }
            }
            if (resolveContentProvider != null) {
                try {
                    Cursor query = C0426h.f677c.getContentResolver().query(C0426h.m771c(resolveContentProvider.authority), this.f684a, null, null, null);
                    try {
                        a = C0429j.m793a(query);
                        if (query != null) {
                            try {
                                query.close();
                            } catch (Exception e3) {
                            }
                        }
                    } catch (Exception e4) {
                        cursor = query;
                        if (cursor == null) {
                            try {
                                cursor.close();
                                a = bDLocation;
                            } catch (Exception e5) {
                                a = bDLocation;
                            }
                        } else {
                            a = bDLocation;
                        }
                        bDLocation = a;
                        bDLocation.setLocType(66);
                        return bDLocation;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor2 = query;
                        if (cursor2 != null) {
                            try {
                                cursor2.close();
                            } catch (Exception e6) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    cursor = null;
                    if (cursor == null) {
                        a = bDLocation;
                    } else {
                        cursor.close();
                        a = bDLocation;
                    }
                    bDLocation = a;
                    bDLocation.setLocType(66);
                    return bDLocation;
                } catch (Throwable th3) {
                    th = th3;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
                bDLocation = a;
            } else {
                try {
                    cursor2 = this.f685b.f681g.m696a(new C0428a(this.f684a));
                    bDLocation = C0429j.m793a(cursor2);
                    if (cursor2 != null) {
                        try {
                            cursor2.close();
                        } catch (Exception e8) {
                        }
                    }
                } catch (Exception e9) {
                    if (cursor2 != null) {
                        try {
                            cursor2.close();
                        } catch (Exception e10) {
                        }
                    }
                } catch (Throwable th4) {
                    if (cursor2 != null) {
                        try {
                            cursor2.close();
                        } catch (Exception e11) {
                        }
                    }
                }
            }
            if (!(bDLocation == null || bDLocation.getLocType() == 67)) {
                bDLocation.setLocType(66);
            }
        }
        return bDLocation;
    }

    public /* synthetic */ Object call() throws Exception {
        return m791a();
    }
}
