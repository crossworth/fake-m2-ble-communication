package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.services.core.AMapException;
import com.fithealth.running.R;
import com.zhuoyi.account.IAccountListener;
import com.zhuoyi.account.ZyAccount;
import com.zhuoyi.account.constant.UrlConstant;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.add.TosGallery;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.bluetooth.data.Util;
import com.zhuoyou.plugin.cloud.AlarmUtils;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.custom.CustomAlertDialog;
import com.zhuoyou.plugin.custom.CustomAlertDialog.Builder;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.PersonalGoal;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.running.Welcome;
import com.zhuoyou.plugin.selfupdate.Constants;
import com.zhuoyou.plugin.share.WeiboConstant;
import com.zhuoyou.plugin.view.FloatPickerView;
import com.zhuoyou.plugin.view.FloatPickerView.OnFloatPickedListener;
import com.zhuoyou.plugin.view.MofeiScrollView;
import com.zhuoyou.plugin.view.NumberPickerView;
import com.zhuoyou.plugin.view.NumberPickerView.OnNumberPickedListener;
import com.zhuoyou.plugin.view.WheelView;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PersonalInformation extends Activity implements OnClickListener, OnEndFlingListener {
    int Default_tagert = 10000;
    private NumberPickerView agePickerView;
    WheelTextAdapter age_adaptor;
    FrameLayout age_layout;
    TextView age_text_unit;
    private byte[] bitmapByte = null;
    private Bitmap bmp = null;
    private Button btnLogout;
    LinearLayout choiceSports;
    int current_weight = 75;
    int current_weight_point = 0;
    RelativeLayout editHead;
    LinearLayout editUsrinfo;
    private ImageView face;
    private boolean from_center = false;
    int headIndex;
    private NumberPickerView heightPickerView;
    WheelTextAdapter height_adaptor;
    FrameLayout height_layout;
    TextView height_text_unit;
    int f4864i = 0;
    String likeSportIndex = null;
    private LinearLayout likeSports;
    private TextView mGoals1;
    private TextView mGoals2;
    private TextView mGoals3;
    private TextView mGoals4;
    private PersonalConfig mPersonalConfig;
    private PersonalGoal mPersonalGoal;
    private MofeiScrollView mScrollView;
    private ZyAccount mZyAccount;
    TextView mage_text;
    WheelView mage_wheelView;
    TextView mheight_text;
    WheelView mheight_wheelView;
    TextView mweight_text;
    WheelView mweight_wheelView;
    WheelView mweight_wheelView_point;
    private Boolean needCloud = Boolean.valueOf(false);
    private EditText nickname;
    private Context sContext = RunningApp.getInstance().getApplicationContext();
    private TextView sMan;
    private TextView sWoman;
    private int selectPostion;
    private EditText signature;
    private int size = Tools.dip2px(this.sContext, BitmapDescriptorFactory.HUE_ORANGE);
    private String[] sportArray = new String[100];
    int sportIndex;
    private List<String> sportType;
    private EditText target;
    private FloatPickerView weightPickerView;
    WheelTextAdapter weight_adaptor;
    FrameLayout weight_layout;
    WheelTextAdapter weight_point_adaptor;
    TextView weight_text_point_unit;
    TextView weight_text_unit;

    class C12994 implements DialogInterface.OnClickListener {
        C12994() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class C13005 implements DialogInterface.OnClickListener {
        C13005() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (Tools.getLoginName(PersonalInformation.this.sContext).equals(PersonalInformation.this.nickname.getText().toString())) {
                PersonalInformation.this.nickname.setText(null);
            }
            Tools.saveInfoToSharePreference(PersonalInformation.this.sContext, "");
            Tools.setLogin(PersonalInformation.this.sContext, false);
            PersonalInformation.this.btnLogout.setText(R.string.login);
            PersonalInformation.this.initDate();
            AlarmUtils.cancelAutoSyncAlarm(PersonalInformation.this.sContext);
            CloudSync.autoSyncType = 1;
            Tools.clearFeedTable("data", PersonalInformation.this.sContext);
            Tools.clearFeedTable(DataBaseContants.TABLE_DELETE_NAME, PersonalInformation.this.sContext);
            Tools.saveConsigneeInfo("", "", "");
            Welcome.isentry = true;
            Tools.saveAccountRegistDay(1);
            String address = Util.getLatestConnectDeviceAddress(PersonalInformation.this);
            if (address.equals(Util.getLatestConnectDeviceAddress(PersonalInformation.this))) {
                Util.updateLatestConnectDeviceAddress(PersonalInformation.this, "");
            }
            Intent unBindIntent = new Intent(BleManagerService.ACTION_DISCONNECT_BINDED_DEVICE);
            unBindIntent.putExtra("BINDED_DEVICE_ADDRESS", address);
            PersonalInformation.this.sendBroadcast(unBindIntent);
        }
    }

    class C13017 implements DialogInterface.OnClickListener {
        C13017() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    class C18861 implements OnNumberPickedListener {
        C18861() {
        }

        public void onNumberPicked(int index, int number) {
            PersonalInformation.this.mPersonalConfig.setYear(index + 1917);
            PersonalInformation.this.mage_text.setText((Calendar.getInstance().get(1) - PersonalInformation.this.mPersonalConfig.getYear()) + PersonalInformation.this.getString(R.string.unit_age));
        }
    }

    class C18872 implements OnNumberPickedListener {
        C18872() {
        }

        public void onNumberPicked(int index, int number) {
            PersonalInformation.this.mPersonalConfig.setHeight(number);
            PersonalInformation.this.mheight_text.setText(PersonalInformation.this.mPersonalConfig.getHeight() + "CM");
        }
    }

    class C18883 implements OnFloatPickedListener {
        C18883() {
        }

        public void onFloatPicked(int numberIndex, int pointIndex, String floatStr) {
            PersonalInformation.this.mPersonalConfig.setWeight(floatStr);
            PersonalInformation.this.mweight_text.setText(PersonalInformation.this.mPersonalConfig.getWeight() + "KG");
        }
    }

    class C18896 implements IAccountListener {
        C18896() {
        }

        public void onSuccess(String userInfo) {
            Tools.saveInfoToSharePreference(PersonalInformation.this.sContext, userInfo);
            Tools.setLogin(PersonalInformation.this.sContext, true);
            PersonalInformation.this.needCloud = Boolean.valueOf(true);
            PersonalInformation.this.finish();
        }

        public void onCancel() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        this.mZyAccount = new ZyAccount(getApplicationContext(), "1102927580", WeiboConstant.CONSUMER_KEY);
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.mPersonalGoal = Tools.getPersonalGoal();
        initView();
        initDate();
        this.agePickerView = (NumberPickerView) findViewById(R.id.age_pickerview);
        this.agePickerView.setInitNumberPicked(this.mPersonalConfig.getYear() - 1917);
        this.agePickerView.setNumberRange(1917, 2016);
        this.agePickerView.setLabel(getString(R.string.persional_info_weight_nian));
        this.agePickerView.setOnNumberPickedListener(new C18861());
        this.agePickerView.show();
        this.heightPickerView = (NumberPickerView) findViewById(R.id.height_pickerview);
        this.heightPickerView.setNumberRange(60, 220);
        this.heightPickerView.setLabel("CM");
        this.heightPickerView.setOnNumberPickedListener(new C18872());
        this.heightPickerView.show();
        this.weightPickerView = (FloatPickerView) findViewById(R.id.weight_pickerview);
        this.weightPickerView.setNumberRange(15, 200);
        this.weightPickerView.setLabel("KG");
        this.weightPickerView.setOnFloatPickedListener(new C18883());
        this.weightPickerView.show();
    }

    private void initDate() {
        this.likeSportIndex = null;
        this.likeSports.removeAllViews();
        this.sportArray = getResources().getStringArray(R.array.whole_sport_type);
        this.headIndex = Tools.getHead(this);
        if (this.headIndex == 10000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
            this.face.setImageBitmap(this.bmp);
        } else if (this.headIndex == 1000) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            this.face.setImageBitmap(this.bmp);
        } else {
            this.face.setImageResource(Tools.selectByIndex(this.headIndex));
        }
        if (Tools.getUsrName(this).equals("")) {
            if (Tools.getLoginName(this).equals("")) {
                this.nickname.setText(null);
            } else if (Tools.getLoginName(this).contains("@")) {
                this.nickname.setText(Tools.getLoginName(this).split("@")[0]);
            } else {
                this.nickname.setText(Tools.getLoginName(this));
            }
        } else if (Tools.getUsrName(this).contains("@")) {
            this.nickname.setText(Tools.getUsrName(this).split("@")[0]);
        } else {
            this.nickname.setText(Tools.getUsrName(this));
        }
        this.nickname.setSelection(this.nickname.getText().length());
        if (this.mPersonalConfig.getSex() == 0) {
            selectSex(0);
        } else if (this.mPersonalConfig.getSex() == 1) {
            selectSex(1);
        }
        this.mage_text.setText((Calendar.getInstance().get(1) - this.mPersonalConfig.getYear()) + getResources().getString(R.string.unit_age));
        this.mheight_text.setText(this.mPersonalConfig.getHeight() + getResources().getString(R.string.unit_length));
        this.mweight_text.setText(this.mPersonalConfig.getWeight() + getResources().getString(R.string.unit_weight));
        if (this.mPersonalGoal.getStep() == 7000) {
            selectGoals(1);
        } else if (this.mPersonalGoal.getStep() == 10000) {
            selectGoals(2);
        } else if (this.mPersonalGoal.getStep() == UrlConstant.SEDN_MMS_DELAY) {
            selectGoals(3);
        } else {
            selectGoals(4);
        }
        this.target.setText(this.mPersonalGoal.getStep() + "");
        if (!Tools.getSignature(this.sContext).equals("")) {
            this.signature.setText(Tools.getSignature(this.sContext));
            this.signature.setSelection(this.signature.getText().length());
        }
        this.likeSportIndex = Tools.getLikeSportsIndex(this.sContext);
        if (this.likeSportIndex.equals("")) {
            TextView text = new TextView(this.sContext);
            text.setText(R.string.choose_favorite_sport);
            this.likeSports.addView(text);
        } else {
            String[] likeIndex = this.likeSportIndex.split(SeparatorConstants.SEPARATOR_ADS_ID);
            if (likeIndex.length > 0) {
                for (String parseInt : likeIndex) {
                    ImageView img = new ImageView(this.sContext);
                    LayoutParams params = new LayoutParams(this.size, this.size);
                    params.leftMargin = 10;
                    img.setLayoutParams(params);
                    img.setImageResource(Tools.sportType[Integer.parseInt(parseInt)]);
                    this.likeSports.addView(img);
                }
            }
        }
        if (Tools.getLogin(this.sContext)) {
            this.btnLogout.setText(R.string.log_out);
        } else {
            this.btnLogout.setText(R.string.login);
        }
    }

    public void initView() {
        if (getIntent() != null) {
            this.from_center = getIntent().getBooleanExtra("from_center", false);
        }
        this.mScrollView = (MofeiScrollView) findViewById(R.id.scrollview);
        this.mage_text = (TextView) findViewById(R.id.age_text);
        this.mheight_text = (TextView) findViewById(R.id.height_text);
        this.mweight_text = (TextView) findViewById(R.id.weight_text);
        this.face = (ImageView) findViewById(R.id.face_logo);
        this.nickname = (EditText) findViewById(R.id.nickname);
        this.nickname.setFilters(new InputFilter[]{Tools.newInputFilter(14, this.sContext.getResources().getString(R.string.reach_input_limit))});
        this.signature = (EditText) findViewById(R.id.signature);
        this.signature.setFilters(new InputFilter[]{Tools.newInputFilter(50, this.sContext.getResources().getString(R.string.reach_input_limit))});
        this.likeSports = (LinearLayout) findViewById(R.id.like_sportss);
        this.btnLogout = (Button) findViewById(R.id.logouts);
        if (this.from_center) {
            this.btnLogout.setVisibility(4);
        }
        this.sMan = (TextView) findViewById(R.id.sex_man);
        this.sWoman = (TextView) findViewById(R.id.sex_woman);
        this.mGoals1 = (TextView) findViewById(R.id.information_goals1);
        this.mGoals1.setOnClickListener(this);
        this.mGoals2 = (TextView) findViewById(R.id.information_goals2);
        this.mGoals2.setOnClickListener(this);
        this.mGoals3 = (TextView) findViewById(R.id.information_goals3);
        this.mGoals3.setOnClickListener(this);
        this.mGoals4 = (TextView) findViewById(R.id.information_goals4);
        this.mGoals4.setOnClickListener(this);
        this.target = (EditText) findViewById(R.id.target);
        this.age_layout = (FrameLayout) findViewById(R.id.show_age_layout);
        this.height_layout = (FrameLayout) findViewById(R.id.show_height_layout);
        this.weight_layout = (FrameLayout) findViewById(R.id.weight_layout);
    }

    private void selectSex(int index) {
        switch (index) {
            case 0:
                this.sMan.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.information_man_select, 0, 0);
                this.sWoman.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.information_woman, 0, 0);
                return;
            case 1:
                this.sMan.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.information_man, 0, 0);
                this.sWoman.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.information_woman_select, 0, 0);
                return;
            default:
                return;
        }
    }

    private void selectGoals(int index) {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        switch (index) {
            case 1:
                this.mGoals1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_primary_select, 0, 0);
                this.mGoals2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_middle, 0, 0);
                this.mGoals3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_high, 0, 0);
                this.mGoals4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_custom, 0, 0);
                this.mGoals1.setTextColor(-615054);
                this.mGoals2.setTextColor(-4605511);
                this.mGoals3.setTextColor(-4605511);
                this.mGoals4.setTextColor(-4605511);
                this.target.setFocusableInTouchMode(false);
                this.target.clearFocus();
                this.target.setText("7000");
                return;
            case 2:
                this.mGoals1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_primary, 0, 0);
                this.mGoals2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_middle_select, 0, 0);
                this.mGoals3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_high, 0, 0);
                this.mGoals4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_custom, 0, 0);
                this.mGoals1.setTextColor(-4605511);
                this.mGoals2.setTextColor(-615054);
                this.mGoals3.setTextColor(-4605511);
                this.mGoals4.setTextColor(-4605511);
                this.target.setFocusableInTouchMode(false);
                this.target.clearFocus();
                this.target.setText("10000");
                return;
            case 3:
                this.mGoals1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_primary, 0, 0);
                this.mGoals2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_middle, 0, 0);
                this.mGoals3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_high_select, 0, 0);
                this.mGoals4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_custom, 0, 0);
                this.mGoals1.setTextColor(-4605511);
                this.mGoals2.setTextColor(-4605511);
                this.mGoals3.setTextColor(-615054);
                this.mGoals4.setTextColor(-4605511);
                this.target.setFocusableInTouchMode(false);
                this.target.clearFocus();
                this.target.setText("15000");
                return;
            case 4:
                this.mGoals1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_primary, 0, 0);
                this.mGoals2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_middle, 0, 0);
                this.mGoals3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_high, 0, 0);
                this.mGoals4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.goals_custom_select, 0, 0);
                this.mGoals1.setTextColor(-4605511);
                this.mGoals2.setTextColor(-4605511);
                this.mGoals3.setTextColor(-4605511);
                this.mGoals4.setTextColor(-615054);
                this.target.setFocusableInTouchMode(true);
                imm.showSoftInput(this.target, 0);
                this.target.requestFocus();
                this.target.setSelection(this.target.getText().toString().length());
                return;
            default:
                return;
        }
    }

    private String[] getAge() {
        return getResources().getStringArray(R.array.age_item);
    }

    private String[] getHeight() {
        return getResources().getStringArray(R.array.height_item);
    }

    private String[] getWeight() {
        return getResources().getStringArray(R.array.weight_item);
    }

    private String[] getWeightPoint() {
        return getResources().getStringArray(R.array.weight_point);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bmp != null) {
            this.bmp.recycle();
            this.bmp = null;
            System.gc();
        }
    }

    public void finish() {
        if (this.needCloud.booleanValue()) {
            setResult(-1);
        }
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == -1) {
                this.headIndex = data.getIntExtra("headIndex", 6);
                this.bitmapByte = data.getByteArrayExtra("bitmap");
                if (this.headIndex == 10000) {
                    if (this.bitmapByte == null) {
                        this.bmp = Tools.convertFileToBitmap("/Running/download/custom");
                    } else {
                        this.bmp = BitmapFactory.decodeByteArray(this.bitmapByte, 0, this.bitmapByte.length);
                    }
                    this.face.setImageBitmap(this.bmp);
                } else if (this.headIndex == 1000) {
                    this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
                    this.face.setImageBitmap(this.bmp);
                } else {
                    this.face.setImageResource(Tools.selectByIndex(this.headIndex));
                }
            }
        } else if (requestCode == 101 && resultCode == -1) {
            List<String> selectedIndex = new ArrayList();
            this.sportType = data.getStringArrayListExtra("sports");
            if (this.sportType.size() == 0) {
                this.likeSportIndex = "";
                this.likeSports.removeAllViews();
                TextView text = new TextView(this.sContext);
                text.setText(R.string.choose_favorite_sport);
                this.likeSports.addView(text);
                return;
            }
            int i;
            for (i = 0; i < this.sportType.size(); i++) {
                selectedIndex.add(Integer.toString(Tools.getSportIndex(this.sportArray, (String) this.sportType.get(i))));
            }
            for (i = 0; i < selectedIndex.size(); i++) {
                ImageView img = new ImageView(this.sContext);
                LayoutParams params = new LayoutParams(this.size, this.size);
                params.leftMargin = 10;
                img.setLayoutParams(params);
                img.setImageResource(Tools.sportType[Integer.parseInt((String) selectedIndex.get(i))]);
                this.likeSports.addView(img);
                if (i == 0) {
                    this.likeSportIndex = (String) selectedIndex.get(i);
                } else {
                    this.likeSportIndex += SeparatorConstants.SEPARATOR_ADS_ID + ((String) selectedIndex.get(i));
                }
            }
            while (this.likeSports.getChildCount() > selectedIndex.size()) {
                this.likeSports.removeViewAt(0);
                if (this.likeSports.getChildCount() <= selectedIndex.size()) {
                    return;
                }
            }
        }
    }

    private void saveCustomHead() {
        Exception e;
        Throwable th;
        if (this.bitmapByte != null) {
            String file = Tools.getSDPath() + Constants.DownloadApkPath;
            File dirFile = new File(file);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            FileOutputStream fops = null;
            try {
                FileOutputStream fops2 = new FileOutputStream(new File(file + "custom"));
                try {
                    fops2.write(this.bitmapByte);
                    try {
                        fops2.flush();
                        fops2.close();
                        fops = fops2;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        fops = fops2;
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    fops = fops2;
                    try {
                        e2.printStackTrace();
                        try {
                            fops.flush();
                            fops.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fops.flush();
                            fops.close();
                        } catch (Exception e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fops = fops2;
                    fops.flush();
                    fops.close();
                    throw th;
                }
            } catch (Exception e4) {
                e222 = e4;
                e222.printStackTrace();
                fops.flush();
                fops.close();
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.save:
                if (TextUtils.isEmpty(this.nickname.getText()) && Tools.getLogin(this.sContext)) {
                    Toast.makeText(this, R.string.toast_cannot_be_empty, 0).show();
                    return;
                } else if (this.target.getText().toString().equals("")) {
                    save();
                    CloudSync.startSyncInfo();
                    finish();
                    return;
                } else {
                    int targetStep = Integer.parseInt(this.target.getText().toString());
                    int cal = Tools.calcCalories(Tools.calcDistance(targetStep, this.mPersonalConfig.getHeight()), this.mPersonalConfig.getWeightNum());
                    if (targetStep < 5000) {
                        showAlertDilog(getResources().getString(R.string.least_step));
                        return;
                    } else if (targetStep >= 60000) {
                        showAlertDilog(getResources().getString(R.string.most_step));
                        return;
                    } else {
                        this.mPersonalGoal.mGoalSteps = targetStep;
                        this.mPersonalGoal.mGoalCalories = cal;
                        save();
                        CloudSync.startSyncInfo();
                        finish();
                        return;
                    }
                }
            case R.id.edit_heads:
                Intent intent = new Intent();
                intent.setClass(this.sContext, ChooseHeadActivity.class);
                startActivityForResult(intent, 100);
                return;
            case R.id.sex_woman:
                this.mPersonalConfig.setSex(1);
                selectSex(1);
                return;
            case R.id.sex_man:
                this.mPersonalConfig.setSex(0);
                selectSex(0);
                return;
            case R.id.logouts:
                if (Tools.getLogin(this.sContext)) {
                    Builder builder = new Builder(this.sContext);
                    builder.setTitle((int) R.string.log_out);
                    builder.setMessage((int) R.string.log_out_message);
                    builder.setPositiveButton((int) R.string.cancle, new C12994());
                    builder.setNegativeButton((int) R.string.continueto, new C13005());
                    builder.setCancelable(Boolean.valueOf(false));
                    CustomAlertDialog dialog = builder.create();
                    dialog.getWindow().setType(AMapException.CODE_AMAP_ENGINE_TABLEID_NOT_EXIST);
                    dialog.show();
                    return;
                }
                this.mZyAccount.login(new C18896());
                return;
            case R.id.goods_address:
                Intent mIntent = new Intent();
                mIntent.setClass(this.sContext, GoodsAddressActivity.class);
                startActivity(mIntent);
                return;
            case R.id.weight_lay:
                this.mScrollView.setView(null);
                if (this.weight_layout.getVisibility() == 8) {
                    int[] weight = getPersonalWeight();
                    this.weightPickerView.setVisibility(0);
                    this.weightPickerView.setFloatPicked(weight[0], weight[1]);
                    this.mScrollView.setView(this.weight_layout);
                    this.weightPickerView.setVisibility(0);
                    this.weight_layout.setVisibility(0);
                    this.agePickerView.setVisibility(8);
                    this.heightPickerView.setVisibility(8);
                    this.age_layout.setVisibility(8);
                    this.height_layout.setVisibility(8);
                    return;
                }
                this.weightPickerView.setVisibility(8);
                this.weight_layout.setVisibility(8);
                return;
            case R.id.age_lay:
                this.mScrollView.setView(null);
                if (this.age_layout.getVisibility() == 8) {
                    this.mScrollView.setView(this.age_layout);
                    this.agePickerView.setVisibility(0);
                    this.age_layout.setVisibility(0);
                    this.heightPickerView.setVisibility(8);
                    this.weightPickerView.setVisibility(8);
                    this.weight_layout.setVisibility(8);
                    this.height_layout.setVisibility(8);
                    return;
                }
                this.agePickerView.setVisibility(8);
                this.age_layout.setVisibility(8);
                return;
            case R.id.height_lay:
                this.mScrollView.setView(null);
                if (this.height_layout.getVisibility() == 8) {
                    this.heightPickerView.setNumberPicked(this.mPersonalConfig.getHeight());
                    this.mScrollView.setView(this.height_layout);
                    this.heightPickerView.setVisibility(0);
                    this.height_layout.setVisibility(0);
                    int positon = this.mPersonalConfig.getHeight() - 55;
                    this.agePickerView.setVisibility(8);
                    this.weightPickerView.setVisibility(8);
                    this.weight_layout.setVisibility(8);
                    this.age_layout.setVisibility(8);
                    return;
                }
                this.heightPickerView.setVisibility(8);
                this.height_layout.setVisibility(8);
                return;
            case R.id.choice_sportss:
                Intent sIntent = new Intent();
                sIntent.putExtra("sportlike", this.likeSportIndex);
                sIntent.setClass(this.sContext, ChooseSport.class);
                startActivityForResult(sIntent, 101);
                return;
            case R.id.information_goals1:
                selectGoals(1);
                return;
            case R.id.information_goals2:
                selectGoals(2);
                return;
            case R.id.information_goals3:
                selectGoals(3);
                return;
            case R.id.information_goals4:
                selectGoals(4);
                return;
            default:
                return;
        }
    }

    private void showAlertDilog(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(string);
        builder.setPositiveButton(R.string.know, new C13017());
        builder.create().show();
    }

    private void save() {
        saveCustomHead();
        Tools.updatePersonalGoal(this.mPersonalGoal);
        Tools.updatePersonalConfig(this.mPersonalConfig);
        Tools.setHead(this.sContext, this.headIndex);
        Tools.setUsrName(this.sContext, this.nickname.getText().toString());
        Tools.setSignature(this.sContext, this.signature.getText().toString());
        Tools.setLikeSportsIndex(this.sContext, this.likeSportIndex);
        Tools.setHeightWeightData("3|" + decimalFormat3(this.mPersonalConfig.getHeight()) + "|" + decimalFormat4(Integer.valueOf(deleteWeightPoint(String.valueOf(Float.parseFloat(this.mPersonalConfig.getWeight())))).intValue()) + "|" + "||||");
        sendHeighWeightData();
        Log.i("hph", "getHeightWeightData=" + Tools.getHeightWeightData());
    }

    private void sendHeighWeightData() {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", Tools.getHeightWeightData());
        sendBroadcast(intent);
    }

    private int[] getPersonalWeight() {
        weightArray = new int[2];
        String[] weight = this.mPersonalConfig.getWeight().split("\\.");
        weightArray[0] = Integer.parseInt(weight[0]);
        if (weight.length == 1) {
            weightArray[1] = 0;
        } else {
            weightArray[1] = Integer.parseInt(weight[1]);
        }
        return weightArray;
    }

    private String decimalFormat3(int data) {
        return new DecimalFormat("#000").format((long) data);
    }

    private String decimalFormat4(int data) {
        return new DecimalFormat("#0000").format((long) data);
    }

    private String deleteWeightPoint(String weight) {
        Log.i("hph", "deleteWeight=" + weight.replace(".", ""));
        return weight.replace(".", "");
    }

    public void onEndFling(TosGallery v) {
        v.getId();
    }
}
