package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.info.GoodsAddressActivity;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.util.List;

public class ActionIntroFragment extends Fragment {
    private int ActionPannelPosition = 0;
    private ActionInfo actioninfo;
    private ActionInfoAdaptor baseadaptor;
    private ImageView image_hide;
    private ActionPannelItemInfo mActionPannelItemInfo;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private View mView;
    private ListView mlist;
    private TextView mtitle;
    private List<ActionPannelItemInfo> pannellist;
    private TextView show_text;
    private RelativeLayout show_warn;

    class C10871 implements OnClickListener {
        C10871() {
        }

        public void onClick(View v) {
            ActionIntroFragment.this.show_warn.setVisibility(8);
        }
    }

    class C10882 implements OnClickListener {
        C10882() {
        }

        public void onClick(View v) {
            ActionIntroFragment.this.startActivity(new Intent(ActionIntroFragment.this.mContext, GoodsAddressActivity.class));
        }
    }

    public interface OnnetDateArride {
        void Updatedate(ActionPannelItemInfo actionPannelItemInfo);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.intro_fragment, container, false);
        this.mtitle = (TextView) this.mView.findViewById(R.id.my_title);
        this.mlist = (ListView) this.mView.findViewById(R.id.mylist);
        if (this.pannellist != null && this.pannellist.size() > this.ActionPannelPosition) {
            this.mActionPannelItemInfo = (ActionPannelItemInfo) this.pannellist.get(this.ActionPannelPosition);
            this.mtitle.setText(this.mActionPannelItemInfo.GetPannelTitle());
        }
        this.baseadaptor = new ActionInfoAdaptor(getActivity(), this.mActionPannelItemInfo, this.mlist);
        this.mlist.setAdapter(this.baseadaptor);
        initView();
        this.image_hide.setOnClickListener(new C10871());
        this.show_text.setOnClickListener(new C10882());
        return this.mView;
    }

    public void onResume() {
        super.onResume();
        String phone = Tools.getConsigneePhone(this.mContext);
        String name = Tools.getConsigneeName(this.mContext);
        String address = Tools.getConsigneeAddress(this.mContext);
        if (phone == null || phone.equals("") || name == null || name.equals("") || address == null || address.equals("")) {
            this.show_warn.setVisibility(0);
        } else {
            this.show_warn.setVisibility(8);
        }
    }

    public void initView() {
        this.show_warn = (RelativeLayout) this.mView.findViewById(R.id.intro_perfect_personal_data);
        this.show_text = (TextView) this.mView.findViewById(R.id.onclik_personal_data);
        this.image_hide = (ImageView) this.mView.findViewById(R.id.hide_lay);
    }
}
