package org.andengine;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import org.andengine.opengl.view.ConfigChooser;
import org.andengine.util.exception.DeviceNotSupportedException;
import org.andengine.util.exception.DeviceNotSupportedException.DeviceNotSupportedCause;
import org.andengine.util.system.SystemUtils;

public class AndEngine {
    public static boolean isDeviceSupported() {
        try {
            checkDeviceSupported();
            return true;
        } catch (DeviceNotSupportedException e) {
            return false;
        }
    }

    public static void checkDeviceSupported() throws DeviceNotSupportedException {
        checkCodePathSupport();
        checkOpenGLSupport();
    }

    private static void checkCodePathSupport() throws DeviceNotSupportedException {
        if (SystemUtils.isAndroidVersionOrLower(8)) {
            try {
                System.loadLibrary("andengine");
            } catch (UnsatisfiedLinkError e) {
                throw new DeviceNotSupportedException(DeviceNotSupportedCause.CODEPATH_INCOMPLETE, e);
            }
        }
    }

    private static void checkOpenGLSupport() throws DeviceNotSupportedException {
        checkEGLConfigChooserSupport();
    }

    private static void checkEGLConfigChooserSupport() throws DeviceNotSupportedException {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(eglDisplay, new int[2]);
        try {
            new ConfigChooser(false).chooseConfig(egl, eglDisplay);
        } catch (IllegalArgumentException e) {
            throw new DeviceNotSupportedException(DeviceNotSupportedCause.EGLCONFIG_NOT_FOUND, e);
        }
    }
}
