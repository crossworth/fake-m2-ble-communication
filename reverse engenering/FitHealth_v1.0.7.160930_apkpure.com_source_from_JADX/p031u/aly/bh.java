package p031u.aly;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: AdvertisingId */
public class bh {

    /* compiled from: AdvertisingId */
    private static final class C1483a {
        private final String f3700a;
        private final boolean f3701b;

        C1483a(String str, boolean z) {
            this.f3700a = str;
            this.f3701b = z;
        }

        private String m3489b() {
            return this.f3700a;
        }

        public boolean m3490a() {
            return this.f3701b;
        }
    }

    /* compiled from: AdvertisingId */
    private static final class C1484b implements ServiceConnection {
        boolean f3702a;
        private final LinkedBlockingQueue<IBinder> f3703b;

        private C1484b() {
            this.f3702a = false;
            this.f3703b = new LinkedBlockingQueue(1);
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f3703b.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }

        public IBinder m3491a() throws InterruptedException {
            if (this.f3702a) {
                throw new IllegalStateException();
            }
            this.f3702a = true;
            return (IBinder) this.f3703b.take();
        }
    }

    /* compiled from: AdvertisingId */
    private static final class C1485c implements IInterface {
        private IBinder f3704a;

        public C1485c(IBinder iBinder) {
            this.f3704a = iBinder;
        }

        public IBinder asBinder() {
            return this.f3704a;
        }

        public String m3492a() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f3704a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                String readString = obtain2.readString();
                return readString;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        public boolean m3493a(boolean z) throws RemoteException {
            boolean z2 = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(z ? 1 : 0);
                this.f3704a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z2 = false;
                }
                obtain2.recycle();
                obtain.recycle();
                return z2;
            } catch (Throwable th) {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    public static String m3494a(Context context) {
        String str = null;
        try {
            C1483a b = bh.m3495b(context);
            if (b != null) {
                str = b.m3489b();
            }
        } catch (Exception e) {
        }
        return str;
    }

    private static C1483a m3495b(Context context) throws Exception {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            ServiceConnection c1484b = new C1484b();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (context.bindService(intent, c1484b, 1)) {
                try {
                    C1485c c1485c = new C1485c(c1484b.m3491a());
                    C1483a c1483a = new C1483a(c1485c.m3492a(), c1485c.m3493a(true));
                    context.unbindService(c1484b);
                    return c1483a;
                } catch (Exception e) {
                    throw e;
                } catch (Throwable th) {
                    context.unbindService(c1484b);
                }
            } else {
                throw new IOException("Google Play connection failed");
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
