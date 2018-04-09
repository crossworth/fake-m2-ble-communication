package org.andengine.util.debug;

import android.util.Log;
import org.andengine.util.Constants;

public class Debug {
    private static DebugLevel sDebugLevel = DebugLevel.VERBOSE;
    private static String sDebugUser = "";
    private static String sTag = Constants.DEBUGTAG;

    public enum DebugLevel {
        NONE,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        VERBOSE;
        
        public static DebugLevel ALL;

        static {
            ALL = VERBOSE;
        }

        public boolean isSameOrLessThan(DebugLevel pDebugLevel) {
            return compareTo(pDebugLevel) >= 0;
        }
    }

    public static String getTag() {
        return sTag;
    }

    public static void setTag(String pTag) {
        sTag = pTag;
    }

    public static DebugLevel getDebugLevel() {
        return sDebugLevel;
    }

    public static void setDebugLevel(DebugLevel pDebugLevel) {
        if (pDebugLevel == null) {
            throw new IllegalArgumentException("pDebugLevel must not be null!");
        }
        sDebugLevel = pDebugLevel;
    }

    public static void setDebugUser(String pDebugUser) {
        if (pDebugUser == null) {
            throw new IllegalArgumentException("pDebugUser must not be null!");
        }
        sDebugUser = pDebugUser;
    }

    public static void log(DebugLevel pDebugLevel, String pMessage) {
        switch (pDebugLevel) {
            case VERBOSE:
                m4597v(pMessage);
                return;
            case INFO:
                m4593i(pMessage);
                return;
            case DEBUG:
                m4584d(pMessage);
                return;
            case WARNING:
                m4601w(pMessage);
                return;
            case ERROR:
                m4588e(pMessage);
                return;
            default:
                return;
        }
    }

    public static void log(DebugLevel pDebugLevel, String pMessage, Throwable pThrowable) {
        switch (pDebugLevel) {
            case VERBOSE:
                m4600v(pMessage, pThrowable);
                return;
            case INFO:
                m4596i(pMessage, pThrowable);
                return;
            case DEBUG:
                m4587d(pMessage, pThrowable);
                return;
            case WARNING:
                m4604w(pMessage, pThrowable);
                return;
            case ERROR:
                m4591e(pMessage, pThrowable);
                return;
            default:
                return;
        }
    }

    public static void log(DebugLevel pDebugLevel, String pTag, String pMessage) {
        switch (pDebugLevel) {
            case VERBOSE:
                m4598v(pTag, pMessage);
                return;
            case INFO:
                m4594i(pTag, pMessage);
                return;
            case DEBUG:
                m4585d(pTag, pMessage);
                return;
            case WARNING:
                m4602w(pTag, pMessage);
                return;
            case ERROR:
                m4589e(pTag, pMessage);
                return;
            default:
                return;
        }
    }

    public static void log(DebugLevel pDebugLevel, String pTag, String pMessage, Throwable pThrowable) {
        switch (pDebugLevel) {
            case VERBOSE:
                m4599v(pTag, pMessage, pThrowable);
                return;
            case INFO:
                m4595i(pTag, pMessage, pThrowable);
                return;
            case DEBUG:
                m4586d(pTag, pMessage, pThrowable);
                return;
            case WARNING:
                m4603w(pTag, pMessage, pThrowable);
                return;
            case ERROR:
                m4590e(pTag, pMessage, pThrowable);
                return;
            default:
                return;
        }
    }

    public static void m4597v(String pMessage) {
        m4599v(sTag, pMessage, null);
    }

    public static void m4600v(String pMessage, Throwable pThrowable) {
        m4599v(sTag, pMessage, pThrowable);
    }

    public static void m4598v(String pTag, String pMessage) {
        m4599v(pTag, pMessage, null);
    }

    public static void m4599v(String pTag, String pMessage, Throwable pThrowable) {
        if (!sDebugLevel.isSameOrLessThan(DebugLevel.VERBOSE)) {
            return;
        }
        if (pThrowable == null) {
            Log.v(pTag, pMessage);
        } else {
            Log.v(pTag, pMessage, pThrowable);
        }
    }

    public static void vUser(String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4597v(pMessage);
        }
    }

    public static void vUser(String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4600v(pMessage, pThrowable);
        }
    }

    public static void vUser(String pTag, String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4598v(pTag, pMessage);
        }
    }

    public static void vUser(String pTag, String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4599v(pTag, pMessage, pThrowable);
        }
    }

    public static void m4584d(String pMessage) {
        m4586d(sTag, pMessage, null);
    }

    public static void m4587d(String pMessage, Throwable pThrowable) {
        m4586d(sTag, pMessage, pThrowable);
    }

    public static void m4585d(String pTag, String pMessage) {
        m4586d(pTag, pMessage, null);
    }

    public static void m4586d(String pTag, String pMessage, Throwable pThrowable) {
        if (!sDebugLevel.isSameOrLessThan(DebugLevel.DEBUG)) {
            return;
        }
        if (pThrowable == null) {
            Log.d(pTag, pMessage);
        } else {
            Log.d(pTag, pMessage, pThrowable);
        }
    }

    public static void dUser(String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4584d(pMessage);
        }
    }

    public static void dUser(String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4587d(pMessage, pThrowable);
        }
    }

    public static void dUser(String pTag, String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4585d(pTag, pMessage);
        }
    }

    public static void dUser(String pTag, String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4586d(pTag, pMessage, pThrowable);
        }
    }

    public static void m4593i(String pMessage) {
        m4595i(sTag, pMessage, null);
    }

    public static void m4596i(String pMessage, Throwable pThrowable) {
        m4595i(sTag, pMessage, pThrowable);
    }

    public static void m4594i(String pTag, String pMessage) {
        m4595i(pTag, pMessage, null);
    }

    public static void m4595i(String pTag, String pMessage, Throwable pThrowable) {
        if (!sDebugLevel.isSameOrLessThan(DebugLevel.INFO)) {
            return;
        }
        if (pThrowable == null) {
            Log.i(pTag, pMessage);
        } else {
            Log.i(pTag, pMessage, pThrowable);
        }
    }

    public static void iUser(String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4593i(pMessage);
        }
    }

    public static void iUser(String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4596i(pMessage, pThrowable);
        }
    }

    public static void iUser(String pTag, String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4594i(pTag, pMessage);
        }
    }

    public static void iUser(String pTag, String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4595i(pTag, pMessage, pThrowable);
        }
    }

    public static void m4601w(String pMessage) {
        m4603w(sTag, pMessage, null);
    }

    public static void m4605w(Throwable pThrowable) {
        m4604w("", pThrowable);
    }

    public static void m4604w(String pMessage, Throwable pThrowable) {
        m4603w(sTag, pMessage, pThrowable);
    }

    public static void m4602w(String pTag, String pMessage) {
        m4603w(pTag, pMessage, null);
    }

    public static void m4603w(String pTag, String pMessage, Throwable pThrowable) {
        if (!sDebugLevel.isSameOrLessThan(DebugLevel.WARNING)) {
            return;
        }
        if (pThrowable == null) {
            Log.w(pTag, pMessage);
        } else {
            Log.w(pTag, pMessage, pThrowable);
        }
    }

    public static void wUser(String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4601w(pMessage);
        }
    }

    public static void wUser(Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4605w(pThrowable);
        }
    }

    public static void wUser(String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4604w(pMessage, pThrowable);
        }
    }

    public static void wUser(String pTag, String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4602w(pTag, pMessage);
        }
    }

    public static void wUser(String pTag, String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4603w(pTag, pMessage, pThrowable);
        }
    }

    public static void m4588e(String pMessage) {
        m4590e(sTag, pMessage, null);
    }

    public static void m4592e(Throwable pThrowable) {
        m4591e(sTag, pThrowable);
    }

    public static void m4591e(String pMessage, Throwable pThrowable) {
        m4590e(sTag, pMessage, pThrowable);
    }

    public static void m4589e(String pTag, String pMessage) {
        m4590e(pTag, pMessage, null);
    }

    public static void m4590e(String pTag, String pMessage, Throwable pThrowable) {
        if (!sDebugLevel.isSameOrLessThan(DebugLevel.ERROR)) {
            return;
        }
        if (pThrowable == null) {
            Log.e(pTag, pMessage);
        } else {
            Log.e(pTag, pMessage, pThrowable);
        }
    }

    public static void eUser(String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4588e(pMessage);
        }
    }

    public static void eUser(Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4592e(pThrowable);
        }
    }

    public static void eUser(String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4591e(pMessage, pThrowable);
        }
    }

    public static void eUser(String pTag, String pMessage, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4589e(pTag, pMessage);
        }
    }

    public static void eUser(String pTag, String pMessage, Throwable pThrowable, String pDebugUser) {
        if (sDebugUser.equals(pDebugUser)) {
            m4590e(pTag, pMessage, pThrowable);
        }
    }
}
