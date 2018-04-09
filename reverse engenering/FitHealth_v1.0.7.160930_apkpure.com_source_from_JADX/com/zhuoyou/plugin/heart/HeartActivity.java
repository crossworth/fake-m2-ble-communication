package com.zhuoyou.plugin.heart;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.droi.alg.DROIALGMOTION;
import com.fithealth.running.R;
import com.pixart.alg.PXIALGMOTION;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class HeartActivity extends Activity {
    private String fileName;
    private float[] gesnsor_value = new float[]{0.0f, 0.0f, 0.0f};
    private Button heartSwitchBt;
    private TextView heartValueTV;
    private char[] heart_value = new char[]{'\u0000'};
    private File hrFile;
    private boolean isOpenHeartSwitch = false;
    private Context mContext;
    private BroadcastReceiver mGetDataReceiver = new C12884();
    private TextView myHeartTV;
    private TextView myLibVersionTV;
    private OutputStreamWriter outWriter;
    private boolean writeFlag = false;

    class C12851 implements OnClickListener {
        C12851() {
        }

        public void onClick(DialogInterface arg0, int arg1) {
            HeartActivity.this.hrFile.renameTo(new File(Environment.getExternalStorageDirectory().getPath() + "/HRLog/" + HeartActivity.this.fileName + "-未命名" + ".txt"));
        }
    }

    class C12873 implements View.OnClickListener {
        C12873() {
        }

        public void onClick(View arg0) {
            String name = Util.getDeviceName();
            if (HeartActivity.this.isOpenHeartSwitch) {
                HeartActivity.this.heartSwitchBt.setText("开");
                HeartActivity.this.isOpenHeartSwitch = false;
                PXIALGMOTION.Close();
                DROIALGMOTION.Close();
                HeartActivity.this.closeFile();
                if (name.equals("Rumor-2")) {
                    HeartActivity.this.sendBroadcast(new Intent(BleManagerService.ACTION_DISABLE_HEART_INFO));
                }
                Log.i("hph", "else isOpenHeartSwitch=" + HeartActivity.this.isOpenHeartSwitch);
                return;
            }
            HeartActivity.this.heartSwitchBt.setText("关");
            HeartActivity.this.isOpenHeartSwitch = true;
            PXIALGMOTION.Close();
            DROIALGMOTION.Open(20);
            HeartActivity.this.createFile();
            if (name.equals("Rumor-2")) {
                HeartActivity.this.sendBroadcast(new Intent(BleManagerService.ACTION_HEART_DATA_READ));
            }
            Log.i("hph", "if isOpenHeartSwitch=" + HeartActivity.this.isOpenHeartSwitch);
        }
    }

    class C12884 extends BroadcastReceiver {
        C12884() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.zhuoyou.plugin.running.heart.data")) {
                char[] heart_data = intent.getCharArrayExtra("heart_data");
                float[] gsensor_data = intent.getFloatArrayExtra("gsensor_data");
                HeartActivity.this.heart_value = heart_data;
                HeartActivity.this.gesnsor_value = gsensor_data;
                PXIALGMOTION.Process(HeartActivity.this.heart_value, HeartActivity.this.gesnsor_value);
                DROIALGMOTION.Process(HeartActivity.this.heart_value, HeartActivity.this.gesnsor_value);
                HeartActivity.this.heartValueTV.setText(PXIALGMOTION.GetHR() + "");
                HeartActivity.this.myHeartTV.setText(DROIALGMOTION.GetHR() + "");
                Log.i("hph", "heart_value=" + HeartActivity.this.heart_value.toString());
                Log.i("hph", "gesnsor_value=" + HeartActivity.this.gesnsor_value.toString());
                Log.i("hph", "GetHR=" + PXIALGMOTION.GetHR());
                Log.d("gaol", "heart_value.length=" + HeartActivity.this.heart_value.length);
                Log.d("gaol", "gesnsor_value.length=" + HeartActivity.this.gesnsor_value.length);
                int i = 0;
                while (i < HeartActivity.this.heart_value.length) {
                    try {
                        HeartActivity.this.outWriter.append((HeartActivity.this.heart_value[i] & 255) + " ,");
                        Log.d("gaol", "heart_value[i]=" + (HeartActivity.this.heart_value[i] & 255));
                        i++;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                HeartActivity.this.outWriter.append(" | ");
                for (i = 0; i < HeartActivity.this.gesnsor_value.length; i++) {
                    HeartActivity.this.outWriter.append(HeartActivity.this.gesnsor_value[i] + " ,");
                    Log.d("gaol", "gesnsor_value[i]=" + HeartActivity.this.gesnsor_value[i]);
                }
                HeartActivity.this.outWriter.append(" , droi-hr= " + DROIALGMOTION.GetHR() + " ");
                HeartActivity.this.outWriter.append(" , pxi-hr= " + PXIALGMOTION.GetHR() + "");
                int temp = 0;
                for (i = 1; i < 5; i++) {
                    temp = (temp | heart_data[i]) << 8;
                }
                HeartActivity.this.outWriter.append(" , ppg= " + temp + "\n");
            } else if (intent.getAction().equals("com.zhuoyou.plugin.running.gsensor.data")) {
                HeartActivity.this.gesnsor_value = intent.getFloatArrayExtra("gsenor_data");
            } else if (!intent.getAction().equals("com.zhuoyou.plugin.running.gsensor.data")) {
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_layout);
        initView();
        initSaveFile();
        registerBc();
        this.mContext = this;
    }

    private void initSaveFile() {
        File destDir = new File(Environment.getExternalStorageDirectory().getPath() + "/HRLog/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    private void createFile() {
        this.fileName = System.currentTimeMillis() + "";
        this.hrFile = new File(Environment.getExternalStorageDirectory().getPath() + "/HRLog/" + this.fileName + ".txt");
        try {
            this.outWriter = new OutputStreamWriter(new FileOutputStream(this.hrFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.writeFlag = true;
    }

    private void closeFile() {
        final EditText et = new EditText(this.mContext);
        new Builder(this.mContext).setTitle("请输入文件名！").setView(et).setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                HeartActivity.this.hrFile.renameTo(new File(Environment.getExternalStorageDirectory().getPath() + "/HRLog/" + HeartActivity.this.fileName + SocializeConstants.OP_DIVIDER_MINUS + et.getText().toString() + ".txt"));
            }
        }).setNegativeButton("取消", new C12851()).show();
        try {
            this.outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        this.heartValueTV = (TextView) findViewById(R.id.heart_value_tv);
        this.myHeartTV = (TextView) findViewById(R.id.my_heart_tv);
        this.heartSwitchBt = (Button) findViewById(R.id.heart_switch_bt);
        this.myLibVersionTV = (TextView) findViewById(R.id.my_lib_version);
        this.myLibVersionTV.setText("" + DROIALGMOTION.GetVersion());
        this.heartSwitchBt.setOnClickListener(new C12873());
    }

    private void registerBc() {
        IntentFilter intentData = new IntentFilter();
        intentData.addAction("com.zhuoyou.plugin.running.heart.data");
        intentData.addAction("com.zhuoyou.plugin.running.gsensor.data");
        intentData.addAction("com.zhuoyou.plugin.running.heart.gsensor.data");
        registerReceiver(this.mGetDataReceiver, intentData);
    }

    private void unRegisterBc() {
        unregisterReceiver(this.mGetDataReceiver);
    }

    protected void onDestroy() {
        super.onDestroy();
        unRegisterBc();
    }
}
