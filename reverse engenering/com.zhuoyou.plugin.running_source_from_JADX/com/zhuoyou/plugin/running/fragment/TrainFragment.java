package com.zhuoyou.plugin.running.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.activity.TrainDetailActivity;
import com.zhuoyou.plugin.running.base.BaseFragment;

public class TrainFragment extends BaseFragment implements OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1680R.layout.fragment_train, container, false);
    }

    protected void initView(View view) {
        view.findViewById(C1680R.id.btn_train_item1).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_train_item2).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_train_item3).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_train_item4).setOnClickListener(this);
        view.findViewById(C1680R.id.btn_train_item5).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_train_item1:
                startActivity(new Intent(getContext(), TrainDetailActivity.class).putExtra("tmp", 1));
                return;
            case C1680R.id.btn_train_item2:
                startActivity(new Intent(getContext(), TrainDetailActivity.class).putExtra("tmp", 2));
                return;
            case C1680R.id.btn_train_item3:
                startActivity(new Intent(getContext(), TrainDetailActivity.class).putExtra("tmp", 3));
                return;
            case C1680R.id.btn_train_item4:
                startActivity(new Intent(getContext(), TrainDetailActivity.class).putExtra("tmp", 4));
                return;
            case C1680R.id.btn_train_item5:
                startActivity(new Intent(getContext(), TrainDetailActivity.class).putExtra("tmp", 5));
                return;
            default:
                return;
        }
    }
}
