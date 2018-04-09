package com.umeng.socialize.shareboard.p026a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.C0976a;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.shareboard.p027b.C0977a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SNSPlatformAdapter */
public class C1827a extends C0977a {
    private List<SnsPlatform> f4843a = new ArrayList();
    private Context f4844b;
    private C0976a f4845c;

    public C1827a(Context context, List<SnsPlatform> list, C0976a c0976a) {
        this.f4843a = list;
        this.f4844b = context;
        this.f4845c = c0976a;
    }

    private void m5000a(View view, SnsPlatform snsPlatform) {
        ((ImageView) view.findViewById(ResContainer.getResourceId(this.f4844b, "id", "umeng_socialize_shareboard_image"))).setImageResource(ResContainer.getResourceId(this.f4844b, "drawable", snsPlatform.mIcon));
        ((TextView) view.findViewById(ResContainer.getResourceId(this.f4844b, "id", "umeng_socialize_shareboard_pltform_name"))).setText(ResContainer.getString(this.f4844b, snsPlatform.mShowWord));
    }

    private void m5001a(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        if (snsPlatform != null && this.f4845c.m3230a() != null) {
            this.f4845c.m3230a().onclick(snsPlatform, share_media);
        }
    }

    public Object mo2204a(int i) {
        return this.f4843a == null ? null : (SnsPlatform) this.f4843a.get(i);
    }

    public int mo2202a() {
        return this.f4843a == null ? 0 : this.f4843a.size();
    }

    public View mo2203a(int i, ViewGroup viewGroup) {
        SnsPlatform snsPlatform = (SnsPlatform) this.f4843a.get(i);
        View inflate = View.inflate(this.f4844b, ResContainer.getResourceId(this.f4844b, "layout", "umeng_socialize_shareboard_item"), null);
        m5000a(inflate, snsPlatform);
        inflate.setOnClickListener(new C0974b(this, snsPlatform));
        inflate.setOnTouchListener(new C0975c(this, inflate));
        inflate.setFocusable(true);
        return inflate;
    }
}
