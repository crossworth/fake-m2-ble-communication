package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.add.TosAdapterView;
import com.zhuoyou.plugin.add.TosAdapterView.OnItemSelectedListener;
import com.zhuoyou.plugin.add.TosGallery;
import com.zhuoyou.plugin.add.TosGallery.OnEndFlingListener;
import com.zhuoyou.plugin.address.CityModel;
import com.zhuoyou.plugin.address.DistrictModel;
import com.zhuoyou.plugin.address.ProvinceModel;
import com.zhuoyou.plugin.address.XmlParserHandler;
import com.zhuoyou.plugin.cloud.CloudSync;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.view.WheelView;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GoodsAddressActivity extends Activity implements OnEndFlingListener, OnClickListener {
    private TextView addressText;
    private WheelTextAdapter cityAdapter;
    private String consigneeAddress;
    private String consigneeLocation;
    private String consigneeName;
    private String consigneePhone;
    private int currentCity = 0;
    private int currentDistrict = 0;
    private int currentProvince = 0;
    private EditText detailed_address;
    private WheelTextAdapter districtAdapter;
    private Map<String, String[]> mCitisDatasMap = new HashMap();
    private String[] mCitiyDatas;
    private Context mContext;
    private String mCurrentCityName = "北京市";
    private String mCurrentDistrictName = "昌平区";
    private String mCurrentProviceName = "北京市";
    private String[] mDistrictDatas;
    private Map<String, String[]> mDistrictDatasMap = new HashMap();
    private Handler mHandler;
    private String[] mProvinceDatas;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private WheelView mViewProvince;
    private EditText mobilePhone;
    private EditText name;
    private WheelTextAdapter provinceAdapter;
    private RelativeLayout showAddress;

    class C12964 implements Runnable {
        C12964() {
        }

        public void run() {
            GoodsAddressActivity.this.updateCities();
            GoodsAddressActivity.this.addressText.setText(GoodsAddressActivity.this.mCurrentProviceName + GoodsAddressActivity.this.mCurrentCityName + GoodsAddressActivity.this.mCurrentDistrictName);
        }
    }

    class C12975 implements Runnable {
        C12975() {
        }

        public void run() {
            GoodsAddressActivity.this.updateAreas();
            GoodsAddressActivity.this.addressText.setText(GoodsAddressActivity.this.mCurrentProviceName + GoodsAddressActivity.this.mCurrentCityName + GoodsAddressActivity.this.mCurrentDistrictName);
        }
    }

    class C18831 implements OnItemSelectedListener {
        C18831() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            GoodsAddressActivity.this.currentProvince = GoodsAddressActivity.this.mViewProvince.getSelectedItemPosition();
            GoodsAddressActivity.this.provinceAdapter.SetSelecttion(GoodsAddressActivity.this.currentProvince);
            GoodsAddressActivity.this.provinceAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18842 implements OnItemSelectedListener {
        C18842() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            GoodsAddressActivity.this.currentCity = GoodsAddressActivity.this.mViewCity.getSelectedItemPosition();
            GoodsAddressActivity.this.cityAdapter.SetSelecttion(GoodsAddressActivity.this.currentCity);
            GoodsAddressActivity.this.cityAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    class C18853 implements OnItemSelectedListener {
        C18853() {
        }

        public void onItemSelected(TosAdapterView<?> tosAdapterView, View view, int position, long id) {
            GoodsAddressActivity.this.currentDistrict = GoodsAddressActivity.this.mViewDistrict.getSelectedItemPosition();
            GoodsAddressActivity.this.districtAdapter.SetSelecttion(GoodsAddressActivity.this.currentDistrict);
            GoodsAddressActivity.this.districtAdapter.notifyDataSetChanged();
        }

        public void onNothingSelected(TosAdapterView<?> tosAdapterView) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_address);
        this.mContext = RunningApp.getInstance().getApplicationContext();
        this.mHandler = new Handler();
        initView();
        initDate();
        this.mViewProvince.setOnItemSelectedListener(new C18831());
        this.mViewCity.setOnItemSelectedListener(new C18842());
        this.mViewDistrict.setOnItemSelectedListener(new C18853());
    }

    private void initView() {
        this.mViewProvince = (WheelView) findViewById(R.id.id_province);
        this.mViewCity = (WheelView) findViewById(R.id.id_city);
        this.mViewDistrict = (WheelView) findViewById(R.id.id_district);
        this.showAddress = (RelativeLayout) findViewById(R.id.show_address_select);
        this.addressText = (TextView) findViewById(R.id.show_select_address);
        this.name = (EditText) findViewById(R.id.consignee_name);
        this.mobilePhone = (EditText) findViewById(R.id.phone_number);
        this.detailed_address = (EditText) findViewById(R.id.detailed_address);
    }

    private void initDate() {
        this.consigneeName = Tools.getConsigneeName(this.mContext);
        this.name.setText(this.consigneeName);
        this.consigneePhone = Tools.getConsigneePhone(this.mContext);
        this.mobilePhone.setText(this.consigneePhone);
        this.consigneeLocation = Tools.getConsigneeAddress(this.mContext);
        if (!this.consigneeLocation.equals("")) {
            String[] temp = this.consigneeLocation.split(":");
            this.mCurrentProviceName = temp[0];
            this.mCurrentCityName = temp[1];
            this.mCurrentDistrictName = temp[2];
            this.addressText.setText(this.mCurrentProviceName + this.mCurrentCityName + this.mCurrentDistrictName);
            if (temp.length == 4) {
                this.consigneeAddress = temp[3];
                if (!this.consigneeAddress.equals("")) {
                    this.detailed_address.setText(this.consigneeAddress);
                }
            }
        }
        initProvinceDatas();
        updateProvince();
    }

    protected void initProvinceDatas() {
        try {
            int i;
            List<CityModel> cityList;
            List<DistrictModel> districtList;
            InputStream input = getAssets().open("province_data.xml");
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            List<ProvinceModel> provinceList = handler.getDataList();
            if (!(provinceList == null || provinceList.isEmpty())) {
                for (i = 0; i < provinceList.size(); i++) {
                    if (((ProvinceModel) provinceList.get(i)).getName().equals(this.mCurrentProviceName)) {
                        this.currentProvince = i;
                    }
                }
                cityList = ((ProvinceModel) provinceList.get(this.currentProvince)).getCityList();
                if (!(cityList == null || cityList.isEmpty())) {
                    for (i = 0; i < cityList.size(); i++) {
                        if (((CityModel) cityList.get(i)).getName().equals(this.mCurrentCityName)) {
                            this.currentCity = i;
                        }
                    }
                    districtList = ((CityModel) cityList.get(this.currentCity)).getDistrictList();
                    if (!(districtList == null || districtList.isEmpty())) {
                        for (i = 0; i < districtList.size(); i++) {
                            if (((DistrictModel) districtList.get(i)).getName().equals(this.mCurrentDistrictName)) {
                                this.currentDistrict = i;
                            }
                        }
                    }
                }
            }
            this.mProvinceDatas = new String[provinceList.size()];
            for (i = 0; i < provinceList.size(); i++) {
                this.mProvinceDatas[i] = ((ProvinceModel) provinceList.get(i)).getName();
                cityList = ((ProvinceModel) provinceList.get(i)).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = ((CityModel) cityList.get(j)).getName();
                    districtList = ((CityModel) cityList.get(j)).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(((DistrictModel) districtList.get(k)).getName(), ((DistrictModel) districtList.get(k)).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    this.mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                this.mCitisDatasMap.put(((ProvinceModel) provinceList.get(i)).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateProvince() {
        this.mViewProvince.setOnEndFlingListener(this);
        this.mViewProvince.setSoundEffectsEnabled(true);
        this.provinceAdapter = new WheelTextAdapter(this, this.mProvinceDatas, 20);
        this.provinceAdapter.SetSelecttion(this.currentProvince);
        this.mViewProvince.setAdapter(this.provinceAdapter);
        this.mViewProvince.setSelection(this.currentProvince);
        this.mCitiyDatas = (String[]) this.mCitisDatasMap.get(this.mCurrentProviceName);
        this.mViewCity.setOnEndFlingListener(this);
        this.mViewCity.setSoundEffectsEnabled(true);
        this.cityAdapter = new WheelTextAdapter(this, this.mCitiyDatas, 20);
        this.cityAdapter.SetSelecttion(this.currentCity);
        this.mViewCity.setAdapter(this.cityAdapter);
        this.mViewCity.setSelection(this.currentCity);
        this.mDistrictDatas = (String[]) this.mDistrictDatasMap.get(this.mCurrentCityName);
        this.mViewDistrict.setOnEndFlingListener(this);
        this.mViewDistrict.setSoundEffectsEnabled(true);
        this.districtAdapter = new WheelTextAdapter(this, this.mDistrictDatas, 20);
        this.districtAdapter.SetSelecttion(this.currentDistrict);
        this.mViewDistrict.setAdapter(this.districtAdapter);
        this.mViewDistrict.setSelection(this.currentDistrict);
    }

    private void updateCities() {
        this.mCurrentProviceName = this.mProvinceDatas[this.currentProvince];
        this.mCitiyDatas = (String[]) this.mCitisDatasMap.get(this.mCurrentProviceName);
        this.currentCity = 0;
        this.cityAdapter.SetSelecttion(0);
        this.cityAdapter.setData(this.mCitiyDatas);
        this.cityAdapter.notifyDataSetChanged();
        this.mViewCity.setSelection(0);
        updateAreas();
    }

    private void updateAreas() {
        this.mCurrentCityName = ((String[]) this.mCitisDatasMap.get(this.mCurrentProviceName))[this.currentCity];
        this.mDistrictDatas = (String[]) this.mDistrictDatasMap.get(this.mCurrentCityName);
        this.currentDistrict = 0;
        this.districtAdapter.SetSelecttion(0);
        this.districtAdapter.setData(this.mDistrictDatas);
        this.districtAdapter.notifyDataSetChanged();
        this.mViewDistrict.setSelection(0);
        this.mCurrentDistrictName = ((String[]) this.mDistrictDatasMap.get(this.mCurrentCityName))[this.currentDistrict];
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_address:
                finish();
                return;
            case R.id.title_address:
                finish();
                return;
            case R.id.save_address:
                this.consigneeName = this.name.getText().toString();
                this.consigneePhone = this.mobilePhone.getText().toString();
                this.consigneeLocation = this.mCurrentProviceName + ":" + this.mCurrentCityName + ":" + this.mCurrentDistrictName + ":" + this.detailed_address.getText().toString();
                Tools.saveConsigneeInfo(this.consigneeName, this.consigneePhone, this.consigneeLocation);
                CloudSync.startSyncInfo();
                finish();
                return;
            case R.id.click_address:
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService("input_method");
                inputMethodManager.hideSoftInputFromWindow(this.name.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(this.mobilePhone.getWindowToken(), 0);
                inputMethodManager.hideSoftInputFromWindow(this.detailed_address.getWindowToken(), 0);
                if (this.showAddress.getVisibility() == 8) {
                    this.showAddress.setVisibility(0);
                    return;
                } else {
                    this.showAddress.setVisibility(8);
                    return;
                }
            case R.id.empty_select:
                if (this.showAddress.getVisibility() == 0) {
                    this.showAddress.setVisibility(8);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        if (this.showAddress.getVisibility() == 0) {
            this.showAddress.setVisibility(8);
        } else {
            super.onBackPressed();
        }
    }

    public void onEndFling(TosGallery v) {
        switch (v.getId()) {
            case R.id.id_province:
                this.currentProvince = this.mViewProvince.getSelectedItemPosition();
                this.mHandler.postDelayed(new C12964(), 1000);
                return;
            case R.id.id_city:
                this.currentCity = this.mViewCity.getSelectedItemPosition();
                this.mHandler.postDelayed(new C12975(), 1000);
                return;
            case R.id.id_district:
                this.currentDistrict = this.mViewDistrict.getSelectedItemPosition();
                this.mCurrentDistrictName = ((String[]) this.mDistrictDatasMap.get(this.mCurrentCityName))[this.currentDistrict];
                this.addressText.setText(this.mCurrentProviceName + this.mCurrentCityName + this.mCurrentDistrictName);
                return;
            default:
                return;
        }
    }
}
