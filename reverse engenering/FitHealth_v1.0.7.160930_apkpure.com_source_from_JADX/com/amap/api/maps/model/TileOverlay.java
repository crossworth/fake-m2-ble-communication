package com.amap.api.maps.model;

import com.autonavi.amap.mapcore.interfaces.ITileOverlayDelegate;

public final class TileOverlay {
    private ITileOverlayDelegate f961a;

    public TileOverlay(ITileOverlayDelegate iTileOverlayDelegate) {
        this.f961a = iTileOverlayDelegate;
    }

    public void remove() {
        this.f961a.remove();
    }

    public void clearTileCache() {
        this.f961a.clearTileCache();
    }

    public String getId() {
        return this.f961a.getId();
    }

    public void setZIndex(float f) {
        this.f961a.setZIndex(f);
    }

    public float getZIndex() {
        return this.f961a.getZIndex();
    }

    public void setVisible(boolean z) {
        this.f961a.setVisible(z);
    }

    public boolean isVisible() {
        return this.f961a.isVisible();
    }

    public boolean equals(Object obj) {
        if (obj instanceof TileOverlay) {
            return this.f961a.equalsRemote(((TileOverlay) obj).f961a);
        }
        return false;
    }

    public int hashCode() {
        return this.f961a.hashCodeRemote();
    }
}
