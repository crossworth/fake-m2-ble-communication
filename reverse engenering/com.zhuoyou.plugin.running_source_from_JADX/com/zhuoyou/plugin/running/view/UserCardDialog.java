package com.zhuoyou.plugin.running.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droi.library.pickerviews.tools.DisplayUtils;
import com.droi.sdk.core.DroiUser;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.baas.User;
import com.zhuoyou.plugin.running.base.BaseDialog;
import com.zhuoyou.plugin.running.tools.Tools;
import java.util.Calendar;

public class UserCardDialog extends BaseDialog {
    private static final int YEAR = Calendar.getInstance().get(1);
    private ImageView imgBack;
    private ImageView imgPhoto;
    private FrameLayout layoutBack;
    private TextView tvAdress;
    private TextView tvName;
    private TextView tvSexAge;
    private TextView tvSignature;
    private TextView tvZanCount;
    private User user = ((User) DroiUser.getCurrentUser(User.class));
    private int zan;

    class C19591 implements OnClickListener {
        C19591() {
        }

        public void onClick(View v) {
            UserCardDialog.this.dismiss();
        }
    }

    public UserCardDialog(Context context, User user, int zan) {
        if (user != null) {
            this.user = user;
        }
        this.zan = zan;
        this.dialog = new Dialog(context, C1680R.style.DefaultDialog);
        this.dialog.setContentView(C1680R.layout.layout_user_card_dialog);
        this.dialog.setCanceledOnTouchOutside(false);
        Window window = this.dialog.getWindow();
        LayoutParams lp = window.getAttributes();
        lp.width = DisplayUtils.getScreenMetrics(context).x;
        window.setAttributes(lp);
        initView();
        showUserData();
    }

    private void initView() {
        ImageView btnClose = (ImageView) this.dialog.findViewById(C1680R.id.btn_close);
        this.layoutBack = (FrameLayout) this.dialog.findViewById(C1680R.id.layout_back);
        this.imgPhoto = (ImageView) this.dialog.findViewById(C1680R.id.img_user_photo);
        this.imgBack = (ImageView) this.dialog.findViewById(C1680R.id.img_user_back);
        this.tvName = (TextView) this.dialog.findViewById(C1680R.id.tv_usrname);
        this.tvSignature = (TextView) this.dialog.findViewById(C1680R.id.tv_signature);
        this.tvSexAge = (TextView) this.dialog.findViewById(C1680R.id.tv_sex_age);
        this.tvZanCount = (TextView) this.dialog.findViewById(C1680R.id.tv_zan_count);
        this.tvAdress = (TextView) this.dialog.findViewById(C1680R.id.tv_adress);
        btnClose.setOnClickListener(new C19591());
        updateLayout();
    }

    private void showUserData() {
        boolean z;
        Tools.displayFace(this.imgPhoto, this.user.getHead());
        Tools.displayBack(this.imgBack, this.user.getBack());
        this.tvName.setText(TextUtils.isEmpty(this.user.getNickName()) ? this.user.getUserId() : this.user.getNickName());
        this.tvSignature.setText(this.user.getSignature());
        this.tvSexAge.setText(TheApp.getContext().getString(C1680R.string.userinfo_age, new Object[]{Integer.valueOf(YEAR - Tools.parseDefDate(this.user.getBirth()).get(1))}));
        TextView textView = this.tvSexAge;
        if (this.user.getSex() == 0) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        this.tvZanCount.setText(String.valueOf(this.zan));
        this.tvAdress.setText(this.user.getAddress());
    }

    private void updateLayout() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.layoutBack.getLayoutParams();
        FrameLayout.LayoutParams backParams = (FrameLayout.LayoutParams) this.imgBack.getLayoutParams();
        if (this.user.getBack() == null) {
            layoutParams.height = Tools.dip2px(180.0f);
            backParams.height = Tools.dip2px(155.0f);
        } else {
            layoutParams.height = Tools.dip2px(225.0f);
            backParams.height = Tools.dip2px(200.0f);
        }
        this.layoutBack.setLayoutParams(layoutParams);
        this.imgBack.setLayoutParams(backParams);
    }
}
