package com.droi.sdk.feedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.droi.sdk.feedback.p018a.C0950b;
import java.util.List;

public class C0963k extends Fragment {
    @SuppressLint({"HandlerLeak"})
    Handler f3136a = new C0964l(this);
    private ListView f3137b;
    private ListAdapter f3138c;
    private Context f3139d;
    private TextView f3140e;
    private SwipeRefreshLayout f3141f;

    class C0962a extends BaseAdapter {
        final /* synthetic */ C0963k f3131a;
        private List<DroiFeedbackInfo> f3132b;
        private int f3133c;
        private Context f3134d;
        private LayoutInflater f3135e;

        private final class C0961a {
            TextView f3126a;
            TextView f3127b;
            TextView f3128c;
            TextView f3129d;
            final /* synthetic */ C0962a f3130e;

            private C0961a(C0962a c0962a) {
                this.f3130e = c0962a;
                this.f3126a = null;
                this.f3127b = null;
                this.f3128c = null;
                this.f3129d = null;
            }
        }

        public C0962a(C0963k c0963k, int i, Context context, List<DroiFeedbackInfo> list) {
            this.f3131a = c0963k;
            this.f3132b = list;
            this.f3134d = context;
            this.f3133c = i;
            this.f3135e = LayoutInflater.from(context);
        }

        public int getCount() {
            return this.f3132b.size();
        }

        public Object getItem(int i) {
            return null;
        }

        public long getItemId(int i) {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            C0961a c0961a;
            if (view == null) {
                view = this.f3135e.inflate(this.f3133c, null);
                c0961a = new C0961a();
                c0961a.f3126a = (TextView) view.findViewById(C0950b.m2819a(this.f3134d).m2820a("droi_feedback_content_textview"));
                c0961a.f3127b = (TextView) view.findViewById(C0950b.m2819a(this.f3134d).m2820a("droi_feedback_create_time_textview"));
                c0961a.f3128c = (TextView) view.findViewById(C0950b.m2819a(this.f3134d).m2820a("droi_feedback_reply_textview"));
                c0961a.f3129d = (TextView) view.findViewById(C0950b.m2819a(this.f3134d).m2820a("droi_feedback_reply_time_textview"));
                view.setTag(c0961a);
            } else {
                c0961a = (C0961a) view.getTag();
            }
            DroiFeedbackInfo droiFeedbackInfo = (DroiFeedbackInfo) this.f3132b.get(i);
            c0961a.f3126a.setText(droiFeedbackInfo.getContent() + "\n");
            c0961a.f3127b.setText(droiFeedbackInfo.getCreateTime());
            String reply = droiFeedbackInfo.getReply();
            CharSequence replyTime = droiFeedbackInfo.getReplyTime();
            if (reply == null || reply.equals("")) {
                c0961a.f3129d.setVisibility(8);
                replyTime = this.f3131a.getString(C0950b.m2819a(this.f3134d).m2822c("droi_feedback_no_reply_text"));
            } else {
                c0961a.f3129d.setText(replyTime);
                c0961a.f3129d.setVisibility(0);
                replyTime = this.f3131a.getString(C0950b.m2819a(this.f3134d).m2822c("droi_feedback_reply_text")) + reply;
            }
            c0961a.f3128c.setText(replyTime);
            return view;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f3139d = getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C0950b.m2819a(this.f3139d).m2821b("droi_feedback_reply_layout"), viewGroup, false);
        this.f3141f = (SwipeRefreshLayout) inflate.findViewById(C0950b.m2819a(this.f3139d).m2820a("droi_feedback_refresh_layout"));
        this.f3141f.setColorSchemeColors(-26368, -16738048, -16777063);
        this.f3141f.setOnRefreshListener(new C0965m(this));
        this.f3140e = (TextView) inflate.findViewById(C0950b.m2819a(this.f3139d).m2820a("droi_feedback_no_reply"));
        this.f3137b = (ListView) inflate.findViewById(C0950b.m2819a(this.f3139d).m2820a("droi_feedback_reply_item_contatiner"));
        this.f3137b.setClickable(false);
        return inflate;
    }

    public void onStart() {
        super.onStart();
        m2838a();
    }

    private void m2838a() {
        this.f3141f.setRefreshing(true);
        C0958h.m2833a(new C0966n(this));
    }

    public void m2843a(List<DroiFeedbackInfo> list) {
        if (list != null) {
            this.f3138c = new C0962a(this, C0950b.m2819a(this.f3139d).m2821b("droi_feedback_reply_item"), this.f3139d, list);
            if (this.f3138c != null) {
                this.f3137b.setAdapter(this.f3138c);
                this.f3140e.setVisibility(8);
            }
        }
    }
}
