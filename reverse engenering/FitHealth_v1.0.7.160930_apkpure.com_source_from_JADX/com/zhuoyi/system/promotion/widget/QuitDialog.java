package com.zhuoyi.system.promotion.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhuoyi.system.network.object.PromAppInfo;
import com.zhuoyi.system.promotion.util.PromConstants;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.statistics.prom.util.StatsPromUtils;
import com.zhuoyi.system.util.BitmapUtils;
import com.zhuoyi.system.util.BitmapUtils.Callback;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.ResourceIdUtils;

public class QuitDialog {
    private static final String TAG = "PromQuitDialog";
    private Activity activity;
    private LinearLayout adLayout;
    private AdViewHolder[] adViewHolders;
    private AlertDialog alertDialog;
    private Builder dialog;
    private OnClickListener negativeBtnListener;
    private String negativeBtnText;
    private OnClickListener positiveBtnListener;
    private String positiveBtnText;

    public static class AdViewHolder {
        public PromAppInfo appInfo;
        public int position;
    }

    public QuitDialog(Activity activity, String positiveBtnText, String negativeBtnText, OnClickListener positiveBtnListener, OnClickListener negativeBtnListener, AdViewHolder[] adViewHolders) {
        this.activity = activity;
        this.positiveBtnText = positiveBtnText;
        this.negativeBtnText = negativeBtnText;
        this.positiveBtnListener = positiveBtnListener;
        this.negativeBtnListener = negativeBtnListener;
        this.adViewHolders = adViewHolders;
    }

    public void show() {
        try {
            if (this.dialog == null) {
                this.dialog = new Builder(this.activity);
            }
            initAdViews();
            if (this.adLayout == null || this.adLayout.getChildCount() == 0) {
                this.dialog.setTitle(ResourceIdUtils.getResourceId(this.activity, "R.string.zy_error_title"));
                this.dialog.setView(null);
                this.dialog.setMessage(ResourceIdUtils.getResourceId(this.activity, "R.string.zy_yes_or_not_quit"));
            } else {
                this.dialog.setTitle(ResourceIdUtils.getResourceId(this.activity, "R.string.zy_quit_title"));
                this.dialog.setMessage(null);
                this.dialog.setView(this.adLayout);
            }
            if (this.positiveBtnText != null) {
                this.dialog.setPositiveButton(this.positiveBtnText, this.positiveBtnListener);
            }
            if (this.negativeBtnText != null) {
                this.dialog.setNegativeButton(this.negativeBtnText, this.negativeBtnListener);
            }
            this.alertDialog = this.dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdImageViews(AdViewHolder[] adViewHolders) {
        this.adViewHolders = adViewHolders;
    }

    private void initAdViews() {
        if (this.adLayout != null) {
            this.adLayout.removeAllViews();
        }
        this.adLayout = new LinearLayout(this.activity);
        this.adLayout.setOrientation(1);
        this.adLayout.setGravity(1);
        this.adLayout.setLayoutParams(new LayoutParams(-1, -2));
        if (this.adViewHolders != null) {
            for (final AdViewHolder avh : this.adViewHolders) {
                if (avh != null) {
                    final LinearLayout ll_general_loading = (LinearLayout) LayoutInflater.from(this.activity).inflate(ResourceIdUtils.getResourceId(this.activity, "R.layout.zy_general_loading_layout"), null);
                    RelativeLayout ralLayout = new RelativeLayout(this.activity);
                    RelativeLayout.LayoutParams ralLayoutParams = new RelativeLayout.LayoutParams(-1, 150);
                    ralLayoutParams.addRule(12);
                    ralLayout.setLayoutParams(ralLayoutParams);
                    final RelativeLayout textLayout = getTextLayout(avh);
                    final ImageView iv = getAdImageView(avh);
                    LayoutParams params1 = new LayoutParams(-1, 150);
                    ralLayout.addView(ll_general_loading, params1);
                    ralLayout.addView(iv, params1);
                    ralLayout.addView(textLayout);
                    BitmapUtils.bind(iv, avh.appInfo.getShowPicUrl(), PromConstants.PROM_AD_IMAGES_PATH, new StringBuilder(String.valueOf(avh.appInfo.getShowIconId())).toString(), new Callback() {
                        public void onImageLoaded(ImageView view, Bitmap bitmap) {
                            if (bitmap != null) {
                                Activity access$0 = QuitDialog.this.activity;
                                final RelativeLayout relativeLayout = textLayout;
                                final LinearLayout linearLayout = ll_general_loading;
                                final ImageView imageView = iv;
                                final ImageView imageView2 = view;
                                final Bitmap bitmap2 = bitmap;
                                access$0.runOnUiThread(new Runnable() {
                                    public void run() {
                                        relativeLayout.setVisibility(0);
                                        linearLayout.setVisibility(8);
                                        imageView.setVisibility(0);
                                        imageView2.setImageBitmap(bitmap2);
                                    }
                                });
                            }
                            StatsPromUtils.getInstance(QuitDialog.this.activity).addAdDisplayAction(avh.appInfo.getPackageName(), avh.appInfo.getShowIconId(), 15, 0);
                        }
                    });
                    this.adLayout.addView(ralLayout);
                }
            }
        }
    }

    private RelativeLayout getTextLayout(AdViewHolder avh) {
        RelativeLayout textLayout = new RelativeLayout(this.activity);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(-1, 35);
        textParams.addRule(12);
        textLayout.setLayoutParams(textParams);
        textLayout.setBackgroundColor(this.activity.getResources().getColor(ResourceIdUtils.getResourceId(this.activity, "R.color.zy_half_tra")));
        textLayout.getBackground().setAlpha(150);
        TextView tv_desc = new TextView(this.activity);
        tv_desc.setText(avh.appInfo.getDesc());
        tv_desc.setTextColor(this.activity.getResources().getColor(ResourceIdUtils.getResourceId(this.activity, "R.color.zy_white")));
        tv_desc.setGravity(3);
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(-2, -2);
        descParams.addRule(15);
        descParams.addRule(9);
        tv_desc.setLayoutParams(descParams);
        textLayout.addView(tv_desc);
        TextView tv_size = new TextView(this.activity);
        String fileSize = "";
        if (avh.appInfo.getFileSize() != 0) {
            fileSize = Formatter.formatFileSize(this.activity, (long) avh.appInfo.getFileSize());
        }
        tv_size.setText(fileSize);
        tv_size.setTextColor(this.activity.getResources().getColor(ResourceIdUtils.getResourceId(this.activity, "R.color.zy_white")));
        tv_size.setGravity(5);
        RelativeLayout.LayoutParams sizeParams = new RelativeLayout.LayoutParams(-2, -2);
        sizeParams.addRule(11);
        sizeParams.addRule(15);
        tv_size.setLayoutParams(sizeParams);
        textLayout.addView(tv_size);
        textLayout.setPadding(10, 0, 10, 0);
        textLayout.setVisibility(8);
        return textLayout;
    }

    private ImageView getAdImageView(final AdViewHolder avh) {
        ImageView iv = new ImageView(this.activity);
        iv.setScaleType(ScaleType.FIT_XY);
        iv.setVisibility(8);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Logger.m3373e(QuitDialog.TAG, "click image");
                if (avh.appInfo != null) {
                    Intent intent = PromUtils.getInstance(QuitDialog.this.activity).clickPromAppInfoListener(avh.appInfo, 15);
                    StatsPromUtils.getInstance(QuitDialog.this.activity).addAdClickAction(avh.appInfo.getPackageName(), avh.appInfo.getShowIconId(), 15, 0);
                    if (intent == null) {
                        return;
                    }
                    if (intent.getSerializableExtra(BundleConstants.BUNDLE_APP_INFO) == null || avh.appInfo.getType() != (byte) 1) {
                        Logger.m3373e(QuitDialog.TAG, "startActivity");
                        QuitDialog.this.activity.startActivity(intent);
                        if (QuitDialog.this.alertDialog != null) {
                            QuitDialog.this.alertDialog.dismiss();
                        }
                        QuitDialog.this.activity.finish();
                        return;
                    }
                    QuitDialog.this.activity.startService(intent);
                    if (QuitDialog.this.alertDialog != null) {
                        QuitDialog.this.alertDialog.dismiss();
                    }
                    QuitDialog.this.activity.finish();
                }
            }
        });
        return iv;
    }

    public void dismiss() {
    }
}
