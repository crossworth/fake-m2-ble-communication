package org.andengine.util;

import org.andengine.util.debug.Debug;
import org.andengine.util.debug.Debug.DebugLevel;

public class ThreadUtils {
    private static final int STACKTRACE_CALLER_DEPTH = 3;

    public static void dumpCurrentThreadInfo() {
        dumpCurrentThreadInfo(DebugLevel.DEBUG, Thread.currentThread().getStackTrace()[3]);
    }

    public static void dumpCurrentThreadInfo(DebugLevel pDebugLevel) {
        dumpCurrentThreadInfo(pDebugLevel, Thread.currentThread().getStackTrace()[3]);
    }

    private static void dumpCurrentThreadInfo(DebugLevel pDebugLevel, StackTraceElement pCaller) {
        Debug.log(pDebugLevel, pCaller.getClassName() + "." + pCaller.getMethodName() + "(" + pCaller.getFileName() + ".java:" + pCaller.getLineNumber() + ") @(Thread: '" + Thread.currentThread().getName() + "')");
    }
}
