package com.amap.api.maps.model;

import android.graphics.Typeface;
import android.os.RemoteException;
import com.autonavi.amap.mapcore.interfaces.ITextDelegate;

public final class Text {
    public static final int ALIGN_BOTTOM = 16;
    public static final int ALIGN_CENTER_HORIZONTAL = 4;
    public static final int ALIGN_CENTER_VERTICAL = 32;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_TOP = 8;
    private ITextDelegate f946a;

    public Text(ITextDelegate iTextDelegate) {
        this.f946a = iTextDelegate;
    }

    public void remove() {
        try {
            this.f946a.remove();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (this.f946a != null) {
                this.f946a.destroy();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String getId() {
        try {
            return this.f946a.getId();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(LatLng latLng) {
        try {
            this.f946a.setPosition(latLng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public LatLng getPosition() {
        try {
            return this.f946a.getPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setText(String str) {
        try {
            this.f946a.setText(str);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getText() {
        try {
            return this.f946a.getText();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setBackgroundColor(int i) {
        try {
            this.f946a.setBackgroundColor(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getBackgroundColor() {
        try {
            return this.f946a.getBackgroundColor();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setFontColor(int i) {
        try {
            this.f946a.setFontColor(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getFontColor() {
        try {
            return this.f946a.getFontColor();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setFontSize(int i) {
        try {
            this.f946a.setFontSize(i);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getFontSize() {
        try {
            return this.f946a.getFontSize();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTypeface(Typeface typeface) {
        try {
            this.f946a.setTypeface(typeface);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public Typeface getTypeface() {
        try {
            return this.f946a.getTypeface();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setAlign(int i, int i2) {
        try {
            this.f946a.setAlign(i, i2);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getAlignX() {
        try {
            return this.f946a.getAlignX();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int getAlignY() {
        try {
            return this.f946a.getAlignY();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setVisible(boolean z) {
        try {
            this.f946a.setVisible(z);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isVisible() {
        try {
            return this.f946a.isVisible();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean equals(Object obj) {
        try {
            if (obj instanceof Text) {
                return this.f946a.equalsRemote(((Text) obj).f946a);
            }
            return false;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        return this.f946a.hashCodeRemote();
    }

    public void setObject(Object obj) {
        this.f946a.setObject(obj);
    }

    public Object getObject() {
        return this.f946a.getObject();
    }

    public void setRotate(float f) {
        try {
            this.f946a.setRotateAngle(f);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getRotate() {
        return this.f946a.getRotateAngle();
    }

    public void setZIndex(float f) {
        this.f946a.setZIndex(f);
    }

    public float getZIndex() {
        return this.f946a.getZIndex();
    }
}
