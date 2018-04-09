package com.droi.library.pickerviews.picker;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.droi.library.pickerviews.C0738R;
import com.droi.library.pickerviews.adapter.ArrayWheelAdapter;
import com.droi.library.pickerviews.address.DBHelper;
import com.droi.library.pickerviews.lib.WheelView;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import java.util.ArrayList;

public class AddressPickerView extends LinearLayout implements IPickerView {
    private ArrayWheelAdapter<String> areaAdapter;
    private int areaIndex;
    private WheelView areaView;
    private ArrayWheelAdapter<String> cityAdapter;
    private String cityCode;
    private int cityIndex;
    private WheelView cityView;
    private DBHelper dbHelper;
    private OnAdressPickedListener listener;
    private ArrayWheelAdapter<String> provinceAdapter;
    private String provinceCode;
    private int provinceIndex;
    private WheelView provinceView;

    class C07391 implements OnItemSelectedListener {
        C07391() {
        }

        public void onItemSelected(int index) {
            AddressPickerView.this.provinceIndex = index;
            AddressPickerView.this.provinceCode = AddressPickerView.this.dbHelper.getProvinceCode((String) AddressPickerView.this.provinceAdapter.getItem(index));
            AddressPickerView.this.cityIndex = 0;
            AddressPickerView.this.areaIndex = 0;
            AddressPickerView.this.cityCode = "";
            AddressPickerView.this.initCityView();
            AddressPickerView.this.cityCode = AddressPickerView.this.dbHelper.getCityCodeByName(AddressPickerView.this.provinceCode, (String) AddressPickerView.this.cityAdapter.getItem(AddressPickerView.this.cityIndex));
            AddressPickerView.this.initAreaView();
            AddressPickerView.this.doListener();
        }
    }

    class C07402 implements OnItemSelectedListener {
        C07402() {
        }

        public void onItemSelected(int index) {
            AddressPickerView.this.cityIndex = index;
            AddressPickerView.this.cityCode = AddressPickerView.this.dbHelper.getCityCodeByName(AddressPickerView.this.provinceCode, (String) AddressPickerView.this.cityAdapter.getItem(index));
            AddressPickerView.this.areaIndex = 0;
            AddressPickerView.this.initAreaView();
            AddressPickerView.this.doListener();
        }
    }

    class C07413 implements OnItemSelectedListener {
        C07413() {
        }

        public void onItemSelected(int index) {
            AddressPickerView.this.areaIndex = index;
            AddressPickerView.this.doListener();
        }
    }

    public interface OnAdressPickedListener {
        void onAddressPicked(String str, String str2, String str3, String str4);
    }

    public AddressPickerView(Context context) {
        super(context);
        initView(context);
    }

    public AddressPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AddressPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.dbHelper = new DBHelper(context);
        View contentView = LayoutInflater.from(context).inflate(C0738R.layout.address_picker_layout, this, true);
        this.provinceView = (WheelView) contentView.findViewById(C0738R.id.province_view);
        this.cityView = (WheelView) contentView.findViewById(C0738R.id.city_view);
        this.areaView = (WheelView) contentView.findViewById(C0738R.id.area_view);
        this.cityView.setCyclic(false);
        this.areaView.setCyclic(false);
        setTextSize(18);
        setLineSpacingMultiplier(1.8f);
        setTypeface(Typeface.DEFAULT);
        this.provinceView.setOnItemSelectedListener(new C07391());
        this.cityView.setOnItemSelectedListener(new C07402());
        this.areaView.setOnItemSelectedListener(new C07413());
    }

    private void doListener() {
        if (this.listener != null) {
            ArrayList<String> list = getSelectedAddress();
            this.listener.onAddressPicked((String) list.get(0), (String) list.get(1), (String) list.get(2), this.dbHelper.selectCode((String) list.get(0), (String) list.get(1), (String) list.get(2)));
        }
    }

    public ArrayList<String> getSelectedAddress() {
        ArrayList<String> list = new ArrayList();
        list.add(this.provinceAdapter.getItem(this.provinceIndex).toString());
        list.add(this.cityAdapter.getItem(this.cityIndex).toString());
        list.add(this.areaAdapter.getItem(this.areaIndex).toString());
        return list;
    }

    public void showLabel(boolean show) {
    }

    public void setTextSize(int size) {
        this.provinceView.setTextSize((float) size);
        this.cityView.setTextSize((float) size);
        this.areaView.setTextSize((float) size);
    }

    public void setTextColorOut(int textColorOut) {
        this.provinceView.setTextColorOut(textColorOut);
        this.cityView.setTextColorOut(textColorOut);
        this.areaView.setTextColorOut(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.provinceView.setTextColorCenter(textColorCenter);
        this.cityView.setTextColorCenter(textColorCenter);
        this.areaView.setTextColorCenter(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.provinceView.setDividerColor(dividerColor);
        this.cityView.setDividerColor(dividerColor);
        this.areaView.setDividerColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.provinceView.setLabelColor(labelColor);
        this.cityView.setLabelColor(labelColor);
        this.areaView.setLabelColor(labelColor);
    }

    public void setLineSpacingMultiplier(float space) {
        this.provinceView.setLineSpacingMultiplier(space);
        this.cityView.setLineSpacingMultiplier(space);
        this.areaView.setLineSpacingMultiplier(space);
    }

    public void setTypeface(Typeface type) {
        this.provinceView.setTypeface(type);
        this.cityView.setTypeface(type);
        this.areaView.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.provinceView.setDividerWidth(width);
        this.cityView.setDividerWidth(width);
        this.areaView.setDividerWidth(width);
    }

    public void setInitAddressPicked(String code) {
        ArrayList<String> list = this.dbHelper.selectByCode(code);
        if (list.size() == 3) {
            this.provinceAdapter = new ArrayWheelAdapter(this.dbHelper.selectProvince());
            this.cityAdapter = new ArrayWheelAdapter(this.dbHelper.selectCity((String) list.get(0)));
            this.areaAdapter = new ArrayWheelAdapter(this.dbHelper.selectArea((String) list.get(0)));
            this.provinceIndex = this.provinceAdapter.indexOf(this.dbHelper.getProvinceName((String) list.get(0)));
            this.cityCode = (String) list.get(0);
            this.provinceCode = this.dbHelper.getProvinceCode(this.dbHelper.getProvinceName((String) list.get(0)));
            this.areaIndex = this.areaAdapter.indexOf(list.get(1));
        }
    }

    private void initProvinceView() {
        this.provinceAdapter = new ArrayWheelAdapter(this.dbHelper.selectProvince());
        this.provinceView.setAdapter(this.provinceAdapter);
        this.provinceView.setCurrentItem(this.provinceIndex);
    }

    private void initCityView() {
        String cityName;
        this.cityAdapter = new ArrayWheelAdapter(this.dbHelper.selectCity(this.dbHelper.getProvinceCode((String) this.provinceAdapter.getItem(this.provinceIndex))));
        this.cityView.setAdapter(this.cityAdapter);
        if (TextUtils.isEmpty(this.cityCode)) {
            cityName = (String) this.cityAdapter.getItem(0);
        } else {
            cityName = this.dbHelper.getCityName(this.cityCode);
        }
        this.cityIndex = this.cityAdapter.indexOf(cityName);
        this.cityView.setCurrentItem(this.cityIndex);
    }

    private void initAreaView() {
        this.areaAdapter = new ArrayWheelAdapter(this.dbHelper.selectArea(this.cityCode));
        this.areaView.setAdapter(this.areaAdapter);
        this.areaView.setCurrentItem(this.areaIndex);
    }

    public void show() {
        initProvinceView();
        initCityView();
        initAreaView();
    }

    public void setOnAddressPickedListener(OnAdressPickedListener listener) {
        this.listener = listener;
    }
}
