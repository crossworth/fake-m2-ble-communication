package com.zhuoyou.plugin.running.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.DisplayUtils;

public class SelectCountryDialog extends BaseDialog {
    private static final String[] COUNTRYS = new String[]{"中国", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Virgin Islands", "British indian Ocean Ter-ritory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Canada", "Cape Verde", "Central Africa", "Chad", "Chile", "Christmas Island", "Colombia", "Congo", "Costa Rica", "Cuba", "Cyprus", "Czech Repoublic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Faroe Islands", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "French Southern Territo-ries", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guine-bissau", "Guinea", "Guyana", "Haiti", "Heard islands and Mc Donald Islands", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kiribati", "Korea Democratic People's of Republic ", "Korea of Republic", "Kyrgyzstan", "Macau", "Mexico", "Moldova", "Monaco", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Marianas", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Philippines", "Pitcairn Islands Group", "Poland", "Portugal", "Puerto Rico", "Russia", "Saint Kitts and nevis", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Saint helena", "Saint lucia", "San Marion", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychells", "Sierra leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and South Sandwich Islands", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican", "Venezuela", "Viet Nam", "Wallis and Futuna Is-lands", "Western Sahara", "Western Samoa", "Yemen", "Yugoslavia", "Zaire", "Zambia", "Zimbabwe"};
    private OnSelectedListener listener = new C19533();
    private Context mContext;

    public interface OnSelectedListener {
        void onSelected(int i, String str);
    }

    class C19511 implements OnItemClickListener {
        C19511() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            SelectCountryDialog.this.listener.onSelected(position, SelectCountryDialog.COUNTRYS[position]);
        }
    }

    class C19522 implements OnClickListener {
        C19522() {
        }

        public void onClick(View v) {
            SelectCountryDialog.this.dialog.dismiss();
        }
    }

    class C19533 implements OnSelectedListener {
        C19533() {
        }

        public void onSelected(int position, String select) {
        }
    }

    public SelectCountryDialog(Context context) {
        this.mContext = context;
        this.dialog = new Dialog(context, C1680R.style.DefaultDialog);
        this.dialog.setContentView(C1680R.layout.layout_select_country_dialog);
        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(C1680R.style.BottomDialogShowAnim);
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        lp.height = DisplayUtils.getScreenMetrics(context).y / 2;
        window.setAttributes(lp);
        initView();
    }

    public void initView() {
        ListView countryList = (ListView) this.dialog.findViewById(C1680R.id.country_list);
        ArrayAdapter<String> adapter = new ArrayAdapter(this.mContext, C1680R.layout.layout_country_list_item, C1680R.id.tv_country);
        adapter.addAll(COUNTRYS);
        countryList.setAdapter(adapter);
        countryList.setOnItemClickListener(new C19511());
        this.dialog.findViewById(C1680R.id.btn_cancel).setOnClickListener(new C19522());
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }
}
