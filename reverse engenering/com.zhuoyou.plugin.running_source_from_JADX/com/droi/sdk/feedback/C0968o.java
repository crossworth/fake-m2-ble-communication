package com.droi.sdk.feedback;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.droi.sdk.feedback.p018a.C0949a;
import com.droi.sdk.feedback.p018a.C0950b;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import twitter4j.HttpResponseCode;

public class C0968o extends Fragment {
    private static String f3148e;
    private static String f3149f;
    public TextView f3150a;
    public EditText f3151b;
    public EditText f3152c;
    private Button f3153d;
    private Toast f3154g = null;
    private Context f3155h;

    private class C0967a implements InputFilter {
        int f3145a;
        String f3146b = "[\\u4e00-\\u9fa5]";
        final /* synthetic */ C0968o f3147c;

        public C0967a(C0968o c0968o, int i) {
            this.f3147c = c0968o;
            this.f3145a = i;
        }

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if ((spanned.toString().length() + m2844a(spanned.toString())) + (charSequence.toString().length() + m2844a(charSequence.toString())) > this.f3145a) {
                return "";
            }
            return charSequence;
        }

        private int m2844a(String str) {
            Matcher matcher = Pattern.compile(this.f3146b).matcher(str);
            int i = 0;
            while (matcher.find()) {
                int i2 = 0;
                while (i2 <= matcher.groupCount()) {
                    i2++;
                    i++;
                }
            }
            return i;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f3155h = getActivity();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C0950b.m2819a(this.f3155h).m2821b("droi_feedback_send_layout"), viewGroup, false);
        int a = C0950b.m2819a(this.f3155h).m2820a("droi_feedback_title");
        int a2 = C0950b.m2819a(this.f3155h).m2820a("droi_feedback_contact");
        int a3 = C0950b.m2819a(this.f3155h).m2820a("droi_feedback_content");
        int a4 = C0950b.m2819a(this.f3155h).m2820a("droi_feedback_send");
        this.f3150a = (TextView) inflate.findViewById(a);
        this.f3150a.setText(C0950b.m2819a(this.f3155h).m2822c("droi_feedback_title"));
        this.f3152c = (EditText) inflate.findViewById(a3);
        this.f3152c.setFilters(new InputFilter[]{new C0967a(this, HttpResponseCode.BAD_REQUEST)});
        this.f3151b = (EditText) inflate.findViewById(a2);
        this.f3151b.setFocusable(true);
        this.f3151b.setFocusableInTouchMode(true);
        this.f3151b.requestFocus();
        this.f3153d = (Button) inflate.findViewById(a4);
        if (C0951a.f3114b != null) {
            this.f3153d.setBackground(C0951a.f3114b);
        }
        this.f3153d.setOnClickListener(new C0969p(this));
        return inflate;
    }

    void m2850a() {
        if (C0949a.m2814a(this.f3155h)) {
            ProgressDialog progressDialog = new ProgressDialog(this.f3155h);
            progressDialog.setProgressStyle(0);
            progressDialog.setTitle(this.f3155h.getString(C0950b.m2819a(this.f3155h).m2822c("droi_feedback_commit_title")));
            progressDialog.setMessage(this.f3155h.getString(C0950b.m2819a(this.f3155h).m2822c("droi_feedback_commiting")));
            progressDialog.setIndeterminate(false);
            progressDialog.show();
            f3149f = this.f3152c.getText().toString().trim();
            f3148e = this.f3151b.getText().toString().trim();
            C0958h.m2834a(f3148e, f3149f, new C0970q(this, progressDialog));
            return;
        }
        m2848a(this.f3155h.getString(C0950b.m2819a(this.f3155h).m2822c("droi_feedback_commit_failed_network")));
    }

    private void m2848a(String str) {
        new Handler(Looper.getMainLooper()).post(new C0971s(this, str));
    }
}
