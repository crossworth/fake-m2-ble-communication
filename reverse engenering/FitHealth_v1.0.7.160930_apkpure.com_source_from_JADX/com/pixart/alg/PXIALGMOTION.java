package com.pixart.alg;

public class PXIALGMOTION {
    public static native void Close();

    public static native void CloseLogFile();

    public static native int DrvClose();

    public static native int DrvGetStableTime();

    public static native int DrvOpen();

    public static native int[] DrvReadAndProcess();

    public static native void EnableAutoMode(boolean z);

    public static native void EnableFastOutput(boolean z);

    public static native void EnableMotionMode(boolean z);

    public static native float[] GetDisplayBuffer();

    public static native int GetHR();

    public static native int GetMotionFlag();

    public static native int GetReadyFlag();

    public static native float GetSigGrade();

    public static native int GetTouchFlag();

    public static native int GetVersion();

    public static native void Open(int i);

    public static native String OpenLogFile(String str);

    public static native int Process(char[] cArr, float[] fArr);

    public static native void SetMemsScale(int i);

    public static native void SetSigGradeThrd(float f);

    static {
        System.loadLibrary("paw8001motion");
    }
}
