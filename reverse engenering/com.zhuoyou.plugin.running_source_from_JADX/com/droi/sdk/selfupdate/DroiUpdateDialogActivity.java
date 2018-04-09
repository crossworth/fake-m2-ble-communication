package com.droi.sdk.selfupdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.selfupdate.util.C1050d;
import java.io.File;

public class DroiUpdateDialogActivity extends Activity {
    static boolean f3383d = false;
    private static String f3384f = "DroiUpdateDialogActivity";
    ProgressBar f3385a;
    TextView f3386b;
    Button f3387c;
    @SuppressLint({"HandlerLeak"})
    Handler f3388e = new C1025b(this);
    private int f3389g = 2;
    private DroiUpdateResponse f3390h;
    private File f3391i = null;
    private boolean f3392j = false;
    private boolean f3393k;
    private Context f3394l;

    protected void onCreate(Bundle bundle) {
        boolean z = true;
        super.onCreate(bundle);
        try {
            CharSequence charSequence;
            this.f3394l = this;
            requestWindowFeature(1);
            setContentView(C1050d.m3292a((Context) this).m3294b("droi_update_dialog"));
            this.f3390h = (DroiUpdateResponse) getIntent().getExtras().getSerializable("response");
            String string = getIntent().getExtras().getString("file");
            this.f3393k = getIntent().getExtras().getBoolean("manual");
            if (string == null) {
                z = false;
            }
            if (z) {
                this.f3391i = new File(string);
            }
            int a = C1050d.m3292a((Context) this).m3293a("droi_update_dialog");
            int a2 = C1050d.m3292a((Context) this).m3293a("droi_update_hint");
            int a3 = C1050d.m3292a((Context) this).m3293a("droi_update_content");
            int a4 = C1050d.m3292a((Context) this).m3293a("droi_update_id_ok");
            int a5 = C1050d.m3292a((Context) this).m3293a("droi_update_id_cancel");
            int a6 = C1050d.m3292a((Context) this).m3293a("droi_update_id_check");
            View findViewById = findViewById(a);
            View findViewById2 = findViewById(C1050d.m3292a(this.f3394l).m3293a("droi_update_progress_dialog"));
            this.f3385a = (ProgressBar) findViewById(C1050d.m3292a(this.f3394l).m3293a("droi_update_progressbar"));
            this.f3386b = (TextView) findViewById(C1050d.m3292a(this.f3394l).m3293a("droi_update_progress_textview"));
            this.f3387c = (Button) findViewById(C1050d.m3292a(this.f3394l).m3293a("droi_update_install"));
            OnClickListener c1026c = new C1026c(this, a4, findViewById, findViewById2);
            OnCheckedChangeListener c1029f = new C1029f(this);
            if (this.f3393k || this.f3390h.getUpdateType() == 2) {
                findViewById(a6).setVisibility(8);
            }
            findViewById(a4).setOnClickListener(c1026c);
            findViewById(a5).setOnClickListener(c1026c);
            ((CheckBox) findViewById(a6)).setOnCheckedChangeListener(c1029f);
            String a7 = this.f3390h.m3194a(this, z);
            if (this.f3393k || this.f3390h.getUpdateType() != 2) {
                Object obj = a7;
            } else {
                charSequence = getString(C1050d.m3292a((Context) this).m3295c("droi_force_update")) + "\n" + a7;
            }
            TextView textView = (TextView) findViewById(a2);
            textView.requestFocus();
            textView.setText(charSequence);
            charSequence = this.f3390h.getContent();
            textView = (TextView) findViewById(a3);
            textView.requestFocus();
            textView.setText(charSequence);
        } catch (Exception e) {
            DroiLog.m2869e(f3384f, e);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.f3390h == null) {
            return;
        }
        if (this.f3390h.getUpdateType() != 2 || this.f3393k) {
            DroiUpdate.f3382a.m3210a(this.f3389g, (Context) this, this.f3390h, this.f3391i);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
