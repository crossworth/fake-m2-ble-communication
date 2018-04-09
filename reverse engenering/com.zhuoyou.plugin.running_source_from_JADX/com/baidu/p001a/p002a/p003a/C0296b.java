package com.baidu.p001a.p002a.p003a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.baidu.p001a.p002a.p003a.C0299c.C0301a;

public interface C0296b extends IInterface {

    public static abstract class C0298a extends Binder implements C0296b {

        private static class C0297a implements C0296b {
            private IBinder f46a;

            C0297a(IBinder iBinder) {
                this.f46a = iBinder;
            }

            public void mo1736a(C0299c c0299c) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.baidu.mapframework.open.aidl.IMapOpenService");
                    obtain.writeStrongBinder(c0299c != null ? c0299c.asBinder() : null);
                    this.f46a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f46a;
            }
        }

        public static C0296b m57a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.baidu.mapframework.open.aidl.IMapOpenService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0296b)) ? new C0297a(iBinder) : (C0296b) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.baidu.mapframework.open.aidl.IMapOpenService");
                    mo1736a(C0301a.m60b(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.baidu.mapframework.open.aidl.IMapOpenService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void mo1736a(C0299c c0299c) throws RemoteException;
}
