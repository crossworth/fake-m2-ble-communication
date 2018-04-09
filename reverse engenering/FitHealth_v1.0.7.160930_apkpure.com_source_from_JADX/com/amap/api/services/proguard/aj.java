package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch.OnCloudSearchListener;
import com.amap.api.services.cloud.CloudSearch.Query;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.ICloudSearch;
import com.amap.api.services.proguard.C0407q.C0398c;
import com.amap.api.services.proguard.C0407q.C0399d;
import java.util.HashMap;

/* compiled from: CloudSearchCore */
public class aj implements ICloudSearch {
    private Context f4293a;
    private OnCloudSearchListener f4294b;
    private Query f4295c;
    private int f4296d;
    private HashMap<Integer, CloudResult> f4297e;
    private Handler f4298f = C0407q.m1654a();

    public aj(Context context) {
        this.f4293a = context.getApplicationContext();
    }

    public void setOnCloudSearchListener(OnCloudSearchListener onCloudSearchListener) {
        this.f4294b = onCloudSearchListener;
    }

    private CloudResult m4378a(Query query) throws AMapException {
        Throwable th;
        CloudResult cloudResult;
        CloudResult cloudResult2 = null;
        if (query == null) {
            try {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            } catch (Throwable th2) {
                th = th2;
                C0390i.m1594a(th, "CloudSearch", "searchCloud");
                if (th instanceof AMapException) {
                    throw ((AMapException) th);
                }
                th.printStackTrace();
                return cloudResult2;
            }
        }
        if (!query.queryEquals(this.f4295c)) {
            this.f4296d = 0;
            this.f4295c = query.clone();
            if (this.f4297e != null) {
                this.f4297e.clear();
            }
        }
        if (this.f4296d == 0) {
            cloudResult = (CloudResult) new C2068g(this.f4293a, query).m4358a();
            try {
                m4381a(cloudResult, query);
                return cloudResult;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                cloudResult2 = cloudResult;
                th = th4;
            }
        } else {
            cloudResult2 = m4384a(query.getPageNum());
            if (cloudResult2 == null) {
                cloudResult = (CloudResult) new C2068g(this.f4293a, query).m4358a();
                this.f4297e.put(Integer.valueOf(query.getPageNum()), cloudResult);
                return cloudResult;
            }
            return cloudResult2;
        }
    }

    public void searchCloudAsyn(final Query query) {
        new Thread(this) {
            final /* synthetic */ aj f1278b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                try {
                    obtainMessage.arg1 = 12;
                    obtainMessage.what = 700;
                    C0399d c0399d = new C0399d();
                    c0399d.f1557b = this.f1278b.f4294b;
                    obtainMessage.obj = c0399d;
                    c0399d.f1556a = this.f1278b.m4378a(query);
                    obtainMessage.arg2 = 1000;
                } catch (AMapException e) {
                    obtainMessage.arg2 = e.getErrorCode();
                } finally {
                    this.f1278b.f4298f.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    private CloudItemDetail m4377a(String str, String str2) throws AMapException {
        if (str == null || str.trim().equals("")) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } else if (str2 == null || str2.trim().equals("")) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } else {
            try {
                return (CloudItemDetail) new C2067f(this.f4293a, new C0409y(str, str2)).m4358a();
            } catch (Throwable th) {
                C0390i.m1594a(th, "CloudSearch", "searchCloudDetail");
                if (th instanceof AMapException) {
                    AMapException aMapException = (AMapException) th;
                } else {
                    th.printStackTrace();
                    return null;
                }
            }
        }
    }

    public void searchCloudDetailAsyn(final String str, final String str2) {
        new Thread(this) {
            final /* synthetic */ aj f1281c;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                try {
                    obtainMessage.arg1 = 12;
                    obtainMessage.what = 701;
                    C0398c c0398c = new C0398c();
                    c0398c.f1555b = this.f1281c.f4294b;
                    obtainMessage.obj = c0398c;
                    c0398c.f1554a = this.f1281c.m4377a(str, str2);
                    obtainMessage.arg2 = 1000;
                } catch (AMapException e) {
                    obtainMessage.arg2 = e.getErrorCode();
                } finally {
                    this.f1281c.f4298f.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    private void m4381a(CloudResult cloudResult, Query query) {
        this.f4297e = new HashMap();
        if (this.f4296d > 0) {
            this.f4297e.put(Integer.valueOf(query.getPageNum()), cloudResult);
        }
    }

    protected CloudResult m4384a(int i) {
        if (m4383b(i)) {
            return (CloudResult) this.f4297e.get(Integer.valueOf(i));
        }
        throw new IllegalArgumentException("page out of range");
    }

    private boolean m4383b(int i) {
        return i <= this.f4296d && i > 0;
    }
}
