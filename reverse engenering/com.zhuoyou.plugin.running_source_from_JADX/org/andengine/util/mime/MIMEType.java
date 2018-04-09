package org.andengine.util.mime;

public enum MIMEType {
    JPEG("image/jpeg"),
    GIF("image/gif"),
    PNG("image/png");
    
    private final String mTypeString;

    private MIMEType(String pTypeString) {
        this.mTypeString = pTypeString;
    }

    public String getTypeString() {
        return this.mTypeString;
    }
}
