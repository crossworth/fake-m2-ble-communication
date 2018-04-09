package com.umeng.socialize.shareboard;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.p026a.C1827a;
import com.umeng.socialize.shareboard.p027b.C0977a;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShareBoard */
public class C0976a extends PopupWindow {
    private final ResContainer f3336a;
    private Context f3337b = null;
    private C0980c f3338c = null;
    private C0973a f3339d;
    private ShareBoardlistener f3340e;
    private C0977a f3341f;
    private List<SnsPlatform> f3342g = new ArrayList();

    /* compiled from: ShareBoard */
    interface C0973a {
        void m3228a(SHARE_MEDIA share_media);
    }

    public C0976a(Context context, List<SnsPlatform> list) {
        super(context);
        setWindowLayoutMode(-1, -2);
        this.f3336a = ResContainer.get(context);
        this.f3337b = context;
        this.f3338c = m3229a(context);
        setContentView(this.f3338c);
        this.f3342g = list;
        this.f3341f = new C1827a(this.f3337b, list, this);
        this.f3338c.m3246a(this.f3341f);
        setAnimationStyle(this.f3336a.style("umeng_socialize_shareboard_animation"));
        setFocusable(true);
    }

    public ShareBoardlistener m3230a() {
        return this.f3340e;
    }

    public void m3231a(ShareBoardlistener shareBoardlistener) {
        this.f3340e = shareBoardlistener;
    }

    private C0980c m3229a(Context context) {
        C0980c c0980c = new C0980c(context);
        c0980c.setLayoutParams(new LayoutParams(-1, -1));
        c0980c.m3245a(new C0979b(this));
        return c0980c;
    }
}
