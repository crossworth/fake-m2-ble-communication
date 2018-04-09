package com.baidu.location.p005a;

import android.os.Environment;
import java.io.File;

class C0342e extends Thread {
    final /* synthetic */ C0340c f204a;

    C0342e(C0340c c0340c) {
        this.f204a = c0340c;
    }

    public void run() {
        this.f204a.m224a(new File(Environment.getExternalStorageDirectory() + "/baidu/tempdata", "intime.dat"), "http://itsdata.map.baidu.com/long-conn-gps/sdk.php");
    }
}
