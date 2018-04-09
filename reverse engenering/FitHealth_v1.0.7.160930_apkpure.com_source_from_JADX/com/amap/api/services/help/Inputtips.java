package com.amap.api.services.help;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.proguard.C0390i;
import com.amap.api.services.proguard.C0394o;
import com.amap.api.services.proguard.C0407q;
import com.amap.api.services.proguard.C2051m;
import java.util.ArrayList;
import java.util.List;

public final class Inputtips {
    private Context f1208a;
    private InputtipsListener f1209b;
    private Handler f1210c = C0407q.m1654a();
    private InputtipsQuery f1211d;

    class C03321 extends Thread {
        final /* synthetic */ Inputtips f1205a;

        C03321(Inputtips inputtips) {
            this.f1205a = inputtips;
        }

        public void run() {
            Message obtainMessage = C0407q.m1654a().obtainMessage();
            obtainMessage.obj = this.f1205a.f1209b;
            obtainMessage.arg1 = 5;
            try {
                ArrayList a = this.f1205a.m1186a(this.f1205a.f1211d);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("result", a);
                obtainMessage.setData(bundle);
                obtainMessage.what = 1000;
            } catch (AMapException e) {
                obtainMessage.what = e.getErrorCode();
            } finally {
                this.f1205a.f1210c.sendMessage(obtainMessage);
            }
        }
    }

    public interface InputtipsListener {
        void onGetInputtips(List<Tip> list, int i);
    }

    public Inputtips(Context context, InputtipsListener inputtipsListener) {
        this.f1208a = context.getApplicationContext();
        this.f1209b = inputtipsListener;
    }

    public Inputtips(Context context, InputtipsQuery inputtipsQuery) {
        this.f1208a = context.getApplicationContext();
        this.f1211d = inputtipsQuery;
    }

    public InputtipsQuery getQuery() {
        return this.f1211d;
    }

    public void setQuery(InputtipsQuery inputtipsQuery) {
        this.f1211d = inputtipsQuery;
    }

    public void setInputtipsListener(InputtipsListener inputtipsListener) {
        this.f1209b = inputtipsListener;
    }

    public void requestInputtipsAsyn() {
        new C03321(this).start();
    }

    private ArrayList<Tip> m1186a(InputtipsQuery inputtipsQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f1208a);
            if (inputtipsQuery == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            } else if (inputtipsQuery.getKeyword() != null && !inputtipsQuery.getKeyword().equals("")) {
                return (ArrayList) new C2051m(this.f1208a, inputtipsQuery).m4358a();
            } else {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "Inputtips", "requestInputtips");
            throw e;
        }
    }

    public void requestInputtips(String str, String str2) throws AMapException {
        requestInputtips(str, str2, null);
    }

    public void requestInputtips(String str, String str2, String str3) throws AMapException {
        C0394o.m1652a(this.f1208a);
        if (str == null || str.equals("")) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        }
        final InputtipsQuery inputtipsQuery = new InputtipsQuery(str, str2);
        inputtipsQuery.setType(str3);
        new Thread(this) {
            final /* synthetic */ Inputtips f1207b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                obtainMessage.obj = this.f1207b.f1209b;
                obtainMessage.arg1 = 5;
                try {
                    ArrayList a = this.f1207b.m1186a(inputtipsQuery);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("result", a);
                    obtainMessage.setData(bundle);
                    obtainMessage.what = 1000;
                } catch (Throwable e) {
                    C0390i.m1594a(e, "Inputtips", "requestInputtips");
                    obtainMessage.what = e.getErrorCode();
                } finally {
                    this.f1207b.f1210c.sendMessage(obtainMessage);
                }
            }
        }.start();
    }
}
