package com.amap.api.maps;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.amap.api.mapcore.util.ab;
import com.amap.api.mapcore.util.dj;
import com.amap.api.mapcore.util.dk;
import com.amap.api.mapcore.util.fe;
import com.amap.api.maps.model.RuntimeRemoteException;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMapFragmentDelegate;

public class TextureMapView extends FrameLayout {
    private IMapFragmentDelegate f814a;
    private AMap f815b;
    private int f816c = 0;

    public TextureMapView(Context context) {
        super(context);
        ab.f3938a = context.getApplicationContext();
        getMapFragmentDelegate().setContext(context);
    }

    public TextureMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f816c = attributeSet.getAttributeIntValue(16842972, 0);
        ab.f3938a = context.getApplicationContext();
        getMapFragmentDelegate().setContext(context);
        getMapFragmentDelegate().setVisibility(this.f816c);
    }

    public TextureMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f816c = attributeSet.getAttributeIntValue(16842972, 0);
        ab.f3938a = context.getApplicationContext();
        getMapFragmentDelegate().setContext(context);
        getMapFragmentDelegate().setVisibility(this.f816c);
    }

    public TextureMapView(Context context, AMapOptions aMapOptions) {
        super(context);
        ab.f3938a = context.getApplicationContext();
        getMapFragmentDelegate().setContext(context);
        getMapFragmentDelegate().setOptions(aMapOptions);
    }

    protected IMapFragmentDelegate getMapFragmentDelegate() {
        if (this.f814a == null) {
            try {
                this.f814a = (IMapFragmentDelegate) fe.m885a(getContext(), dj.m597e(), "com.amap.api.mapcore.wrapper.MapFragmentDelegateWrapper", ab.class, new Class[]{Integer.class}, new Object[]{Integer.valueOf(ab.f3940d)});
            } catch (dk e) {
                e.printStackTrace();
                this.f814a = new ab(ab.f3940d);
            }
        }
        return this.f814a;
    }

    public AMap getMap() {
        try {
            IAMapDelegate map = getMapFragmentDelegate().getMap();
            if (map == null) {
                return null;
            }
            if (this.f815b == null) {
                this.f815b = new AMap(map);
            }
            return this.f815b;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onCreate(Bundle bundle) {
        try {
            addView(getMapFragmentDelegate().onCreateView(null, null, bundle), new LayoutParams(-1, -1));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onResume() {
        try {
            getMapFragmentDelegate().onResume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onPause() {
        try {
            getMapFragmentDelegate().onPause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onDestroy() {
        try {
            getMapFragmentDelegate().onDestroy();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onLowMemory() {
        try {
            getMapFragmentDelegate().onLowMemory();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        try {
            getMapFragmentDelegate().onSaveInstanceState(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        getMapFragmentDelegate().setVisibility(i);
    }
}
