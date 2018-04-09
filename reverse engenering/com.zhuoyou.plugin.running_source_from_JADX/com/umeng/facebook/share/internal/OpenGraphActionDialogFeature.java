package com.umeng.facebook.share.internal;

import com.umeng.facebook.internal.DialogFeature;
import com.umeng.facebook.internal.NativeProtocol;

public enum OpenGraphActionDialogFeature implements DialogFeature {
    OG_ACTION_DIALOG(NativeProtocol.PROTOCOL_VERSION_20130618);
    
    private int minVersion;

    private OpenGraphActionDialogFeature(int minVersion) {
        this.minVersion = minVersion;
    }

    public String getAction() {
        return NativeProtocol.ACTION_OGACTIONPUBLISH_DIALOG;
    }

    public int getMinVersion() {
        return this.minVersion;
    }
}
