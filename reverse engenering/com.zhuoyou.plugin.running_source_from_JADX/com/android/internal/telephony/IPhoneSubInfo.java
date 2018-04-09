package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPhoneSubInfo extends IInterface {

    public static abstract class C0281a extends Binder implements IPhoneSubInfo {
        static final int f36a = 1;
        static final int f37b = 2;
        static final int f38c = 3;
        static final int f39d = 4;
        static final int f40e = 5;
        static final int f41f = 6;
        static final int f42g = 7;
        static final int f43h = 8;
        private static final String f44i = "com.android.internal.telephony.IPhoneSubInfo";

        private static class C0280a implements IPhoneSubInfo {
            private IBinder f35a;

            C0280a(IBinder iBinder) {
                this.f35a = iBinder;
            }

            public String m44a() {
                return C0281a.f44i;
            }

            public IBinder asBinder() {
                return this.f35a;
            }

            public String getDeviceId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getDeviceSvn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getIccSerialNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getLine1AlphaTag() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getLine1Number() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getSubscriberId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getVoiceMailAlphaTag() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getVoiceMailNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0281a.f44i);
                    this.f35a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0281a() {
            attachInterface(this, f44i);
        }

        public static IPhoneSubInfo m45a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(f44i);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPhoneSubInfo)) ? new C0280a(iBinder) : (IPhoneSubInfo) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String deviceId;
            switch (i) {
                case 1:
                    parcel.enforceInterface(f44i);
                    deviceId = getDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 2:
                    parcel.enforceInterface(f44i);
                    deviceId = getDeviceSvn();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 3:
                    parcel.enforceInterface(f44i);
                    deviceId = getSubscriberId();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 4:
                    parcel.enforceInterface(f44i);
                    deviceId = getIccSerialNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 5:
                    parcel.enforceInterface(f44i);
                    deviceId = getLine1Number();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 6:
                    parcel.enforceInterface(f44i);
                    deviceId = getLine1AlphaTag();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 7:
                    parcel.enforceInterface(f44i);
                    deviceId = getVoiceMailNumber();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 8:
                    parcel.enforceInterface(f44i);
                    deviceId = getVoiceMailAlphaTag();
                    parcel2.writeNoException();
                    parcel2.writeString(deviceId);
                    return true;
                case 1598968902:
                    parcel2.writeString(f44i);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    String getDeviceId() throws RemoteException;

    String getDeviceSvn() throws RemoteException;

    String getIccSerialNumber() throws RemoteException;

    String getLine1AlphaTag() throws RemoteException;

    String getLine1Number() throws RemoteException;

    String getSubscriberId() throws RemoteException;

    String getVoiceMailAlphaTag() throws RemoteException;

    String getVoiceMailNumber() throws RemoteException;
}
