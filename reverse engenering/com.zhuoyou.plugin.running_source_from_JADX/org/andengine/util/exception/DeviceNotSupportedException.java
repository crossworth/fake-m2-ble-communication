package org.andengine.util.exception;

public class DeviceNotSupportedException extends AndEngineException {
    private static final long serialVersionUID = 2640523490821876076L;
    private final DeviceNotSupportedCause mDeviceNotSupportedCause;

    public enum DeviceNotSupportedCause {
        CODEPATH_INCOMPLETE,
        EGLCONFIG_NOT_FOUND
    }

    public DeviceNotSupportedException(DeviceNotSupportedCause pDeviceNotSupportedCause) {
        this.mDeviceNotSupportedCause = pDeviceNotSupportedCause;
    }

    public DeviceNotSupportedException(DeviceNotSupportedCause pDeviceNotSupportedCause, Throwable pThrowable) {
        super(pThrowable);
        this.mDeviceNotSupportedCause = pDeviceNotSupportedCause;
    }

    public DeviceNotSupportedCause getDeviceNotSupportedCause() {
        return this.mDeviceNotSupportedCause;
    }
}
