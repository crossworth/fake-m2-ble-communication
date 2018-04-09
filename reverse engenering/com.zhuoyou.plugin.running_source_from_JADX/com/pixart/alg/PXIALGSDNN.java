package com.pixart.alg;

public class PXIALGSDNN {
    public static native void Close();

    public static native int DrvClose();

    public static native int DrvGetStableTime();

    public static native int DrvGetTouchFlag();

    public static native int DrvOpen();

    public static native int[] DrvReadAndProcessHRD();

    public static native float GetCVRR();

    public static native double[] GetDisplayBuffer();

    public static native float GetHF();

    public static native int GetHRAvg();

    public static native int GetHRReadyFlag();

    public static native float GetHRVReadyFlag();

    public static native float GetHRV_SDNN();

    public static native float GetLF();

    public static native float GetLH();

    public static native int GetMotionFlag();

    public static native int[] GetRRI();

    public static native int GetSDNN();

    public static native double[] GetSDNNBoundary();

    public static native float GetSDNNHR();

    public static native float GetSDNNReadyFlag();

    public static native int GetStress();

    public static native int GetVersion();

    public static native void HRVProcess();

    public static native void Open(int i);

    public static native void SDNNProcess();

    public static native void SetAge(int i);

    public static native void SetData(int i, int i2, int[] iArr, int i3, int i4);

    static {
        System.loadLibrary("paw8001sdnn");
    }
}
