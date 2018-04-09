package org.andengine.opengl.view;

import android.opengl.GLSurfaceView.EGLConfigChooser;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class ConfigChooser implements EGLConfigChooser {
    private static final int ALPHA_SIZE = 0;
    private static final int BLUE_SIZE = 5;
    private static final int[] BUFFER = new int[1];
    private static final int DEPTH_SIZE = 0;
    private static final int[] EGLCONFIG_ATTRIBUTES_COVERAGEMULTISAMPLE_NVIDIA = new int[]{12324, 5, 12323, 6, 12322, 5, 12321, 0, 12325, 0, 12326, 0, 12352, 4, EGL_COVERAGE_BUFFERS_NV, 1, EGL_COVERAGE_SAMPLES_NV, 2, 12344};
    private static final int[] EGLCONFIG_ATTRIBUTES_FALLBACK = new int[]{12324, 5, 12323, 6, 12322, 5, 12321, 0, 12325, 0, 12326, 0, 12352, 4, 12344};
    private static final int[] EGLCONFIG_ATTRIBUTES_MULTISAMPLE = new int[]{12324, 5, 12323, 6, 12322, 5, 12321, 0, 12325, 0, 12326, 0, 12352, 4, 12338, 1, 12337, 2, 12344};
    private static final int EGL_COVERAGE_BUFFERS_NV = 12512;
    private static final int EGL_COVERAGE_SAMPLES_NV = 12513;
    private static final int EGL_GLES2_BIT = 4;
    private static final int GREEN_SIZE = 6;
    private static final int MULTISAMPLE_COUNT = 2;
    private static final int RED_SIZE = 5;
    private static final int STENCIL_SIZE = 0;
    private int mAlphaSize = -1;
    private int mBlueSize = -1;
    private boolean mCoverageMultiSampling;
    private int mDepthSize = -1;
    private int mGreenSize = -1;
    private boolean mMultiSampling;
    private final boolean mMultiSamplingRequested;
    private int mRedSize = -1;
    private int mStencilSize = -1;

    public enum ConfigChooserMatcher {
        STRICT {
            public boolean matches(int pRedSize, int pGreenSize, int pBlueSize, int pAlphaSize, int pDepthSize, int pStencilSize) {
                if (pDepthSize == 0 && pStencilSize == 0 && pRedSize == 5 && pGreenSize == 6 && pBlueSize == 5 && pAlphaSize == 0) {
                    return true;
                }
                return false;
            }
        },
        LOOSE_STENCIL {
            public boolean matches(int pRedSize, int pGreenSize, int pBlueSize, int pAlphaSize, int pDepthSize, int pStencilSize) {
                if (pDepthSize == 0 && pStencilSize >= 0 && pRedSize == 5 && pGreenSize == 6 && pBlueSize == 5 && pAlphaSize == 0) {
                    return true;
                }
                return false;
            }
        },
        LOOSE_DEPTH_AND_STENCIL {
            public boolean matches(int pRedSize, int pGreenSize, int pBlueSize, int pAlphaSize, int pDepthSize, int pStencilSize) {
                if (pDepthSize >= 0 && pStencilSize >= 0 && pRedSize == 5 && pGreenSize == 6 && pBlueSize == 5 && pAlphaSize == 0) {
                    return true;
                }
                return false;
            }
        },
        ANY {
            public boolean matches(int pRedSize, int pGreenSize, int pBlueSize, int pAlphaSize, int pDepthSize, int pStencilSize) {
                return true;
            }
        };

        public abstract boolean matches(int i, int i2, int i3, int i4, int i5, int i6);
    }

    public ConfigChooser(boolean pMultiSamplingRequested) {
        this.mMultiSamplingRequested = pMultiSamplingRequested;
    }

    public boolean isMultiSampling() {
        return this.mMultiSampling;
    }

    public boolean isCoverageMultiSampling() {
        return this.mCoverageMultiSampling;
    }

    public int getRedSize() {
        return this.mRedSize;
    }

    public int getGreenSize() {
        return this.mGreenSize;
    }

    public int getBlueSize() {
        return this.mBlueSize;
    }

    public int getAlphaSize() {
        return this.mAlphaSize;
    }

    public int getDepthSize() {
        return this.mDepthSize;
    }

    public int getStencilSize() {
        return this.mStencilSize;
    }

    public EGLConfig chooseConfig(EGL10 pEGL, EGLDisplay pEGLDisplay) {
        try {
            return chooseConfig(pEGL, pEGLDisplay, ConfigChooserMatcher.STRICT);
        } catch (IllegalArgumentException e) {
            try {
                return chooseConfig(pEGL, pEGLDisplay, ConfigChooserMatcher.LOOSE_STENCIL);
            } catch (IllegalArgumentException e2) {
                try {
                    return chooseConfig(pEGL, pEGLDisplay, ConfigChooserMatcher.LOOSE_DEPTH_AND_STENCIL);
                } catch (IllegalArgumentException e3) {
                    return chooseConfig(pEGL, pEGLDisplay, ConfigChooserMatcher.ANY);
                }
            }
        }
    }

    private EGLConfig chooseConfig(EGL10 pEGL, EGLDisplay pEGLDisplay, ConfigChooserMatcher pConfigChooserMatcher) throws IllegalArgumentException {
        int eglConfigCount;
        BUFFER[0] = 0;
        if (this.mMultiSamplingRequested) {
            eglConfigCount = getEGLConfigCount(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_MULTISAMPLE);
            if (eglConfigCount > 0) {
                this.mMultiSampling = true;
                return findEGLConfig(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_MULTISAMPLE, eglConfigCount, pConfigChooserMatcher);
            }
            eglConfigCount = getEGLConfigCount(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_COVERAGEMULTISAMPLE_NVIDIA);
            if (eglConfigCount > 0) {
                this.mCoverageMultiSampling = true;
                return findEGLConfig(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_COVERAGEMULTISAMPLE_NVIDIA, eglConfigCount, pConfigChooserMatcher);
            }
        }
        eglConfigCount = getEGLConfigCount(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_FALLBACK);
        if (eglConfigCount > 0) {
            return findEGLConfig(pEGL, pEGLDisplay, EGLCONFIG_ATTRIBUTES_FALLBACK, eglConfigCount, pConfigChooserMatcher);
        }
        throw new IllegalArgumentException("No " + EGLConfig.class.getSimpleName() + " found!");
    }

    private static int getEGLConfigCount(EGL10 pEGL, EGLDisplay pEGLDisplay, int[] pEGLConfigAttributes) {
        if (pEGL.eglChooseConfig(pEGLDisplay, pEGLConfigAttributes, null, 0, BUFFER)) {
            return BUFFER[0];
        }
        throw new IllegalArgumentException("EGLCONFIG_FALLBACK failed!");
    }

    private EGLConfig findEGLConfig(EGL10 pEGL, EGLDisplay pEGLDisplay, int[] pEGLConfigAttributes, int pEGLConfigCount, ConfigChooserMatcher pConfigChooserMatcher) {
        EGLConfig[] eglConfigs = new EGLConfig[pEGLConfigCount];
        if (pEGL.eglChooseConfig(pEGLDisplay, pEGLConfigAttributes, eglConfigs, pEGLConfigCount, BUFFER)) {
            return findEGLConfig(pEGL, pEGLDisplay, eglConfigs, pConfigChooserMatcher);
        }
        throw new IllegalArgumentException("findEGLConfig failed!");
    }

    private EGLConfig findEGLConfig(EGL10 pEGL, EGLDisplay pEGLDisplay, EGLConfig[] pEGLConfigs, ConfigChooserMatcher pConfigChooserMatcher) {
        for (EGLConfig config : pEGLConfigs) {
            if (config != null) {
                int redSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12324, 0);
                int greenSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12323, 0);
                int blueSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12322, 0);
                int alphaSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12321, 0);
                int depthSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12325, 0);
                int stencilSize = getConfigAttrib(pEGL, pEGLDisplay, config, 12326, 0);
                if (pConfigChooserMatcher.matches(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize)) {
                    this.mRedSize = redSize;
                    this.mGreenSize = greenSize;
                    this.mBlueSize = blueSize;
                    this.mAlphaSize = alphaSize;
                    this.mDepthSize = depthSize;
                    this.mStencilSize = stencilSize;
                    return config;
                }
            }
        }
        throw new IllegalArgumentException("No EGLConfig found!");
    }

    private static int getConfigAttrib(EGL10 pEGL, EGLDisplay pEGLDisplay, EGLConfig pEGLConfig, int pAttribute, int pDefaultValue) {
        if (pEGL.eglGetConfigAttrib(pEGLDisplay, pEGLConfig, pAttribute, BUFFER)) {
            return BUFFER[0];
        }
        return pDefaultValue;
    }
}
