package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FirstActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, Welcome.class);
        intent.addFlags(268435456);
        startActivity(intent);
        finish();
    }
}
