package com.zhuoyou.plugin.running.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.droi.library.pickerviews.picker.AddressPickerDialog;
import com.droi.library.pickerviews.picker.AddressPickerView;
import com.droi.library.pickerviews.picker.AddressPickerView.OnAdressPickedListener;
import com.droi.library.pickerviews.picker.DatePickerDialog;
import com.droi.library.pickerviews.picker.DatePickerView;
import com.droi.library.pickerviews.picker.DatePickerView.OnDatePickedListener;
import com.droi.library.pickerviews.picker.NumberPickerDialog;
import com.droi.library.pickerviews.picker.NumberPickerView;
import com.droi.library.pickerviews.picker.NumberPickerView.OnNumberPickedListener;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventUserFetch;
import com.zhuoyou.plugin.running.database.WeightHelper;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.InputValueDialog;
import com.zhuoyou.plugin.running.view.InputValueDialog.OnOkClickListener;
import com.zhuoyou.plugin.running.view.MyActionBar;
import com.zhuoyou.plugin.running.view.MyAlertDialog;
import com.zhuoyou.plugin.running.view.SelectCountryDialog;
import com.zhuoyou.plugin.running.view.SelectCountryDialog.OnSelectedListener;
import java.util.Calendar;
import org.greenrobot.eventbus.Subscribe;

public class UserInfoActivity extends BaseActivity {
    static final /* synthetic */ boolean $assertionsDisabled = (!UserInfoActivity.class.desiredAssertionStatus());
    private static final int CODE_LENGTH = 6;
    private static final String DEFAULT_AREA_CODE = "110101";
    private static final int YEAR = Calendar.getInstance().get(1);
    private ProgressDialog dialog;
    private ImageView imgFemale;
    private ImageView imgMale;
    private ImageView imgPhoto;
    private User old = this.user.getBackUp();
    private TextView tvAccount;
    private TextView tvBirthDay;
    private TextView tvHeight;
    private TextView tvLocation;
    private TextView tvName;
    private TextView tvSignature;
    private TextView tvWeight;
    private User user = ((User) DroiUser.getCurrentUser(User.class));

    class C18321 implements OnClickListener {
        C18321() {
        }

        public void onClick(View v) {
            if (UserInfoActivity.this.user.equals(UserInfoActivity.this.old)) {
                UserInfoActivity.this.finish();
            } else {
                UserInfoActivity.this.saveAndExit(true);
            }
        }
    }

    class C18354 implements OnDatePickedListener {
        C18354() {
        }

        public void onDatePicked(int yearIndex, int monthIndex, int dayIndex, String dateStr) {
            UserInfoActivity.this.user.setBirth(Tools.formatDate(dateStr, Tools.BIRTH_FORMAT, Tools.DEFAULT_FORMAT_TIME));
            UserInfoActivity.this.tvBirthDay.setText(dateStr);
        }
    }

    class C18365 implements OnNumberPickedListener {
        C18365() {
        }

        public void onNumberPicked(int index, int number) {
            UserInfoActivity.this.user.setHeight(number);
            UserInfoActivity.this.tvHeight.setText(String.valueOf(number));
        }
    }

    class C18387 implements OnAdressPickedListener {
        C18387() {
        }

        public void onAddressPicked(String province, String city, String area, String code) {
            String cityName = "";
            if (city.equals("县") || city.equals("市辖区") || city.equals("省直辖")) {
                cityName = "";
            } else {
                cityName = "，" + city;
            }
            UserInfoActivity.this.user.setAddress(province + cityName + "，" + area);
            UserInfoActivity.this.user.setCityCode(code);
            UserInfoActivity.this.tvLocation.setText(UserInfoActivity.this.user.getAddress());
        }
    }

    class C18398 implements MyAlertDialog.OnClickListener {
        C18398() {
        }

        public void onClick(int witch) {
            UserInfoActivity.this.user.setBackUp(UserInfoActivity.this.old);
            UserInfoActivity.this.finish();
        }
    }

    class C18409 implements MyAlertDialog.OnClickListener {
        C18409() {
        }

        public void onClick(int witch) {
            UserInfoActivity.this.saveAndExit(true);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_user_info);
        this.dialog = Tools.getProgressDialog(this);
        initView();
        setupView();
    }

    private void initView() {
        this.imgPhoto = (ImageView) findViewById(C1680R.id.img_photo);
        this.imgMale = (ImageView) findViewById(C1680R.id.img_male);
        this.imgFemale = (ImageView) findViewById(C1680R.id.img_female);
        this.tvName = (TextView) findViewById(C1680R.id.tv_nickname);
        this.tvSignature = (TextView) findViewById(C1680R.id.tv_signature);
        this.tvBirthDay = (TextView) findViewById(C1680R.id.tv_birthday);
        this.tvHeight = (TextView) findViewById(C1680R.id.tv_height);
        this.tvWeight = (TextView) findViewById(C1680R.id.tv_weight);
        this.tvLocation = (TextView) findViewById(C1680R.id.tv_location);
        this.tvAccount = (TextView) findViewById(C1680R.id.tv_account);
        MyActionBar actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        if ($assertionsDisabled || actionBar != null) {
            actionBar.setOnRightTitleClickListener(new C18321());
            return;
        }
        throw new AssertionError();
    }

    private void setupView() {
        Tools.displayFace(this.imgPhoto, this.user.getHead());
        this.tvName.setText(this.user.getNickName());
        this.tvSignature.setText(this.user.getSignature());
        this.tvBirthDay.setText(Tools.formatDate(this.user.getBirth(), Tools.DEFAULT_FORMAT_TIME, Tools.BIRTH_FORMAT));
        this.tvHeight.setText(String.valueOf(this.user.getHeight()));
        if (this.user.getCityCode() != null && this.user.getCityCode().length() <= 6) {
            this.tvLocation.setText(this.user.getAddress());
        }
        this.tvAccount.setText(this.user.getUserId());
        setupSex(this.user.getSex());
    }

    private void setupSex(int sex) {
        if (sex == 0) {
            this.imgMale.setImageResource(C1680R.drawable.ic_male_pressed);
            this.imgFemale.setImageResource(C1680R.drawable.ic_female);
            return;
        }
        this.imgMale.setImageResource(C1680R.drawable.ic_male);
        this.imgFemale.setImageResource(C1680R.drawable.ic_female_pressed);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_edit_photo:
                startActivity(new Intent(this, SetPhotoActivity.class));
                return;
            case C1680R.id.btn_edit_nickname:
                showInputNameDialog();
                return;
            case C1680R.id.btn_edit_signature:
                showInputSignatureDialog();
                return;
            case C1680R.id.img_male:
                this.user.setSex(0);
                setupSex(this.user.getSex());
                return;
            case C1680R.id.img_female:
                this.user.setSex(1);
                setupSex(this.user.getSex());
                return;
            case C1680R.id.btn_edit_birthday:
                showDatePickerDialog();
                return;
            case C1680R.id.btn_edit_height:
                showNumberPickerDialog();
                return;
            case C1680R.id.btn_edit_weight:
                startActivity(new Intent(this, WeightActivity.class));
                return;
            case C1680R.id.btn_edit_location:
                showSelectCountryDialog();
                return;
            case C1680R.id.btn_edit_account:
                startActivity(new Intent(this, AccountActivity.class));
                return;
            case C1680R.id.btn_logout:
                showLogoutDialog();
                return;
            default:
                return;
        }
    }

    @Subscribe
    public void onEventMainThread(EventUserFetch event) {
        setupView();
    }

    protected void onResume() {
        super.onResume();
        this.tvWeight.setText(String.valueOf(WeightHelper.getNewestWeight().getWeight()));
    }

    private void showInputNameDialog() {
        final InputValueDialog dialog = new InputValueDialog(this);
        dialog.setTitle((int) C1680R.string.userinfo_nickname);
        EditText editText = dialog.getEditText();
        editText.setText(this.user.getNickName());
        editText.setSelection(editText.getText().length());
        editText.setHint(C1680R.string.userinfo_input_nickname);
        editText.setFilters(new InputFilter[]{new LengthFilter(10)});
        dialog.setOnOkClickListener(new OnOkClickListener() {
            public void onClick(String value) {
                UserInfoActivity.this.tvName.setText(value);
                UserInfoActivity.this.user.setNickName(value);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showInputSignatureDialog() {
        final InputValueDialog dialog = new InputValueDialog(this);
        dialog.setTitle((int) C1680R.string.userinfo_signature);
        EditText editText = dialog.getEditText();
        editText.setText(this.user.getSignature());
        editText.setSelection(editText.getText().length());
        editText.setHint(C1680R.string.userinfo_signature_hint);
        editText.setFilters(new InputFilter[]{new LengthFilter(20)});
        dialog.setOnOkClickListener(new OnOkClickListener() {
            public void onClick(String value) {
                UserInfoActivity.this.tvSignature.setText(value);
                UserInfoActivity.this.user.setSignature(value);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this);
        DatePickerView picker = (DatePickerView) dialog.getPickerView();
        picker.setYearRange(YEAR - 100, YEAR);
        picker.setTodayLimit(true);
        picker.showLabel(true);
        picker.setOnDatePickedListener(new C18354());
        picker.setInitDatePicked(Tools.formatDate(this.user.getBirth(), Tools.DEFAULT_FORMAT_TIME, Tools.BIRTH_FORMAT));
        picker.show();
        dialog.show();
    }

    private void showNumberPickerDialog() {
        NumberPickerDialog dialog = new NumberPickerDialog(this);
        NumberPickerView picker = (NumberPickerView) dialog.getPickerView();
        picker.setNumberRange(60, 220);
        picker.setLabel("CM");
        picker.setOnNumberPickedListener(new C18365());
        picker.setInitNumberPicked(this.user.getHeight());
        picker.show();
        dialog.show();
    }

    private void showSelectCountryDialog() {
        final SelectCountryDialog dialog = new SelectCountryDialog(this);
        dialog.setOnSelectedListener(new OnSelectedListener() {
            public void onSelected(int position, String select) {
                dialog.dismiss();
                if (select.equals("中国")) {
                    UserInfoActivity.this.showAddressPickerDialog();
                    return;
                }
                UserInfoActivity.this.user.setAddress(select);
                UserInfoActivity.this.user.setCityCode(String.valueOf(-position));
                UserInfoActivity.this.tvLocation.setText(UserInfoActivity.this.user.getAddress());
            }
        });
        dialog.show();
    }

    private void showAddressPickerDialog() {
        AddressPickerDialog dialog = new AddressPickerDialog(this);
        AddressPickerView picker = (AddressPickerView) dialog.getPickerView();
        String userCityCode = this.user.getCityCode();
        if (TextUtils.isEmpty(userCityCode) || userCityCode.length() > 6 || Integer.parseInt(userCityCode) < 0) {
            picker.setInitAddressPicked(DEFAULT_AREA_CODE);
        } else {
            picker.setInitAddressPicked(userCityCode);
        }
        picker.setOnAddressPickedListener(new C18387());
        picker.show();
        dialog.show();
    }

    private void showSavaHintDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.userinfo_changed_hint);
        dialog.setLeftButton((int) C1680R.string.string_cancel, new C18398());
        dialog.setRightButton((int) C1680R.string.userinfo_save, new C18409());
        dialog.show();
    }

    private void showLogoutDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.userinfo_logout_hint);
        dialog.setLeftButton((int) C1680R.string.string_cancel, null);
        dialog.setRightButton((int) C1680R.string.string_ok, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                Tools.logout(UserInfoActivity.this);
            }
        });
        dialog.show();
    }

    public void onBackPressed() {
        if (this.user.equals(this.old)) {
            super.onBackPressed();
        } else {
            showSavaHintDialog();
        }
    }

    private void saveAndExit(final boolean exit) {
        if (exit) {
            Tools.showProgressDialog(this.dialog, getString(C1680R.string.userinfo_saving));
        } else {
            Tools.makeToast((int) C1680R.string.userinfo_saving);
        }
        this.user.saveInBackground(new DroiCallback<Boolean>() {
            public void result(Boolean aBoolean, DroiError droiError) {
                Tools.hideProgressDialog(UserInfoActivity.this.dialog);
                if (droiError.isOk()) {
                    Tools.makeToast((int) C1680R.string.userinfo_save_success);
                    UserInfoActivity.this.old = UserInfoActivity.this.user.getBackUp();
                    if (exit) {
                        UserInfoActivity.this.finish();
                    }
                    Tools.sendHeightWeightToDevice();
                } else if (exit) {
                    UserInfoActivity.this.showFailDialog();
                } else {
                    Tools.makeToast((int) C1680R.string.userinfo_save_fail_hint);
                }
            }
        });
    }

    private void showFailDialog() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setTitle((int) C1680R.string.string_reminder);
        dialog.setMessage((int) C1680R.string.userinfo_save_fail);
        dialog.setLeftButton((int) C1680R.string.string_cancel, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                UserInfoActivity.this.user.setBackUp(UserInfoActivity.this.old);
                UserInfoActivity.this.finish();
            }
        });
        dialog.setRightButton((int) C1680R.string.string_retry, new MyAlertDialog.OnClickListener() {
            public void onClick(int witch) {
                UserInfoActivity.this.saveAndExit(true);
            }
        });
        dialog.show();
    }
}
