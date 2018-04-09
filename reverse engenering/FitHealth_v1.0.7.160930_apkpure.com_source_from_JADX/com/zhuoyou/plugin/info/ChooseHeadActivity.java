package com.zhuoyou.plugin.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.info.ObservableScrollView.Callbacks;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class ChooseHeadActivity extends Activity implements Callbacks {
    public static WRHandler mHandler;
    OnClickListener OnClickListener = new C12924();
    private byte[] bitmapByte = null;
    private Bitmap bmp = null;
    private Bitmap customeBmp = null;
    private String filePath;
    private int[] headIcon1 = Tools.headIcon1;
    private int[] headIcon2 = Tools.headIcon2;
    private int[] headIcon3 = Tools.headIcon3;
    private int[] headIcon4 = Tools.headIcon4;
    private int headIndex;
    private String headType;
    private Boolean isCustom = Boolean.valueOf(false);
    private Boolean isLogin;
    private Context mContext = RunningApp.getInstance().getApplicationContext();
    private ImageView mCustom_icon;
    private RelativeLayout mCustom_layout;
    private ImageView mCustom_select;
    private RelativeLayout mDefault_layout;
    private ImageView mDefault_select;
    private GridView mGridView;
    private HeadAdapter mHeadAdapter;
    private ObservableScrollView mObservableScrollView;
    private View mPlaceholderView;
    private ImageView mQq_icon;
    private RelativeLayout mQq_layout;
    private ImageView mQq_select;
    private TextView mStickyView;
    private ImageView mWeibo_icon;
    private RelativeLayout mWeibo_layout;
    private ImageView mWeibo_select;
    private CustomAvatarPopupWindow popupWindow;
    private int selectIndex = -1;

    class C12891 implements OnClickListener {
        C12891() {
        }

        public void onClick(View v) {
            ChooseHeadActivity.this.finish();
        }
    }

    class C12902 implements OnGlobalLayoutListener {
        C12902() {
        }

        public void onGlobalLayout() {
            ChooseHeadActivity.this.onScrollChanged(ChooseHeadActivity.this.mObservableScrollView.getScrollY());
        }
    }

    class C12913 implements Runnable {
        C12913() {
        }

        public void run() {
            ChooseHeadActivity.this.mObservableScrollView.scrollTo(0, 0);
        }
    }

    class C12924 implements OnClickListener {
        C12924() {
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.default_layout:
                    ChooseHeadActivity.this.selectIndex = -1;
                    ChooseHeadActivity.this.headIndex = 6;
                    ChooseHeadActivity.this.mDefault_select.setVisibility(0);
                    ChooseHeadActivity.this.mQq_select.setVisibility(8);
                    ChooseHeadActivity.this.mWeibo_select.setVisibility(8);
                    ChooseHeadActivity.this.mCustom_select.setVisibility(8);
                    ChooseHeadActivity.this.mHeadAdapter.notifyDataSetChanged();
                    return;
                case R.id.qq_layout:
                    ChooseHeadActivity.this.selectIndex = -1;
                    ChooseHeadActivity.this.headIndex = 1000;
                    ChooseHeadActivity.this.mDefault_select.setVisibility(8);
                    ChooseHeadActivity.this.mQq_select.setVisibility(0);
                    ChooseHeadActivity.this.mWeibo_select.setVisibility(8);
                    ChooseHeadActivity.this.mCustom_select.setVisibility(8);
                    ChooseHeadActivity.this.mHeadAdapter.notifyDataSetChanged();
                    return;
                case R.id.weibo_layout:
                    ChooseHeadActivity.this.selectIndex = -1;
                    ChooseHeadActivity.this.headIndex = 1000;
                    ChooseHeadActivity.this.mDefault_select.setVisibility(8);
                    ChooseHeadActivity.this.mQq_select.setVisibility(8);
                    ChooseHeadActivity.this.mWeibo_select.setVisibility(0);
                    ChooseHeadActivity.this.mCustom_select.setVisibility(8);
                    ChooseHeadActivity.this.mHeadAdapter.notifyDataSetChanged();
                    return;
                case R.id.custom_layout:
                    ChooseHeadActivity.this.popupWindow = new CustomAvatarPopupWindow(ChooseHeadActivity.this.mContext, ChooseHeadActivity.this.isCustom, ChooseHeadActivity.this.OnClickListener);
                    ChooseHeadActivity.this.popupWindow.showAtLocation(ChooseHeadActivity.this.findViewById(R.id.main), 81, 0, 0);
                    return;
                case R.id.tv_select:
                    ChooseHeadActivity.this.popupWindow.dismiss();
                    ChooseHeadActivity.this.selectIndex = -1;
                    ChooseHeadActivity.this.headIndex = 10000;
                    ChooseHeadActivity.this.mDefault_select.setVisibility(8);
                    ChooseHeadActivity.this.mQq_select.setVisibility(8);
                    ChooseHeadActivity.this.mWeibo_select.setVisibility(8);
                    ChooseHeadActivity.this.mCustom_select.setVisibility(0);
                    ChooseHeadActivity.this.mHeadAdapter.notifyDataSetChanged();
                    return;
                case R.id.tv_take_photo:
                    ChooseHeadActivity.this.popupWindow.dismiss();
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra("output", Uri.fromFile(new File(ChooseHeadActivity.this.filePath, "temp.jpg")));
                    ChooseHeadActivity.this.startActivityForResult(intent, 1);
                    return;
                case R.id.tv_chose_pic:
                    ChooseHeadActivity.this.popupWindow.dismiss();
                    Intent intent1 = new Intent("android.intent.action.PICK", null);
                    intent1.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/jpeg");
                    ChooseHeadActivity.this.startActivityForResult(intent1, 2);
                    return;
                default:
                    return;
            }
        }
    }

    static class WRHandler extends Handler {
        WeakReference<ChooseHeadActivity> mChooseHeadActivity;

        public WRHandler(ChooseHeadActivity cha) {
            this.mChooseHeadActivity = new WeakReference(cha);
        }

        public void handleMessage(Message msg) {
            if (this.mChooseHeadActivity != null) {
                ChooseHeadActivity chooseHeadActivity = (ChooseHeadActivity) this.mChooseHeadActivity.get();
                if (chooseHeadActivity != null) {
                    switch (msg.what) {
                        case 1:
                            chooseHeadActivity.selectIndex = msg.arg1;
                            chooseHeadActivity.mDefault_select.setVisibility(8);
                            chooseHeadActivity.mQq_select.setVisibility(8);
                            chooseHeadActivity.mWeibo_select.setVisibility(8);
                            chooseHeadActivity.mCustom_select.setVisibility(8);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_head);
        ((TextView) findViewById(R.id.title)).setText(R.string.choose_head);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C12891());
        this.headIndex = Tools.getHead(this.mContext);
        this.isLogin = Boolean.valueOf(Tools.getLogin(this.mContext));
        this.headType = Tools.getHeadType(this.mContext);
        initView();
        initData();
        mHandler = new WRHandler(this);
        this.filePath = Tools.getSDPath() + "/Running/";
        File dir = new File(this.filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void initView() {
        this.mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
        this.mObservableScrollView.setCallbacks(this);
        this.mStickyView = (TextView) findViewById(R.id.sticky);
        this.mPlaceholderView = findViewById(R.id.placeholder);
        this.mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new C12902());
        if (this.mObservableScrollView != null) {
            this.mObservableScrollView.post(new C12913());
        }
        this.mGridView = (GridView) findViewById(R.id.head_edit);
        this.mHeadAdapter = new HeadAdapter(this.mContext, this.mGridView);
        this.mGridView.setAdapter(this.mHeadAdapter);
        setListViewHeightBasedOnChildren(this.mGridView);
        this.mDefault_layout = (RelativeLayout) findViewById(R.id.default_layout);
        this.mDefault_layout.setOnClickListener(this.OnClickListener);
        this.mDefault_select = (ImageView) findViewById(R.id.default_select);
        this.mQq_layout = (RelativeLayout) findViewById(R.id.qq_layout);
        this.mQq_layout.setOnClickListener(this.OnClickListener);
        this.mQq_icon = (ImageView) findViewById(R.id.qq_icon);
        this.mQq_select = (ImageView) findViewById(R.id.qq_select);
        this.mWeibo_layout = (RelativeLayout) findViewById(R.id.weibo_layout);
        this.mWeibo_layout.setOnClickListener(this.OnClickListener);
        this.mWeibo_icon = (ImageView) findViewById(R.id.weibo_icon);
        this.mWeibo_select = (ImageView) findViewById(R.id.weibo_select);
        this.mCustom_layout = (RelativeLayout) findViewById(R.id.custom_layout);
        this.mCustom_layout.setOnClickListener(this.OnClickListener);
        this.mCustom_icon = (ImageView) findViewById(R.id.custom_icon);
        this.mCustom_select = (ImageView) findViewById(R.id.custom_select);
    }

    private void initData() {
        if (this.isLogin.booleanValue()) {
            this.bmp = Tools.convertFileToBitmap("/Running/download/logo");
            if (this.headType.equals("openqq")) {
                this.mQq_icon.setImageBitmap(this.bmp);
                this.mWeibo_layout.setVisibility(8);
            } else if (this.headType.equals("openweibo")) {
                this.mQq_layout.setVisibility(8);
                this.mWeibo_icon.setImageBitmap(this.bmp);
            } else {
                this.mQq_layout.setVisibility(8);
                this.mWeibo_layout.setVisibility(8);
            }
        } else {
            this.mQq_layout.setVisibility(8);
            this.mWeibo_layout.setVisibility(8);
        }
        this.customeBmp = Tools.convertFileToBitmap("/Running/download/custom");
        if (this.customeBmp != null) {
            this.isCustom = Boolean.valueOf(true);
            this.mCustom_icon.setImageBitmap(this.customeBmp);
        }
        int headindex = Tools.getHead(this.mContext);
        if (headindex == 6) {
            this.mDefault_select.setVisibility(0);
        } else if (headindex == 10000) {
            this.mCustom_select.setVisibility(0);
        }
    }

    private void setListViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter != null) {
            int rows;
            int columns = 0;
            int horizontalBorderHeight = 0;
            Class<?> clazz = gridView.getClass();
            try {
                Field column = clazz.getDeclaredField("mRequestedNumColumns");
                column.setAccessible(true);
                columns = ((Integer) column.get(gridView)).intValue();
                Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
                horizontalSpacing.setAccessible(true);
                horizontalBorderHeight = ((Integer) horizontalSpacing.get(gridView)).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listAdapter.getCount() % columns > 0) {
                rows = (listAdapter.getCount() / columns) + 1;
            } else {
                rows = listAdapter.getCount() / columns;
            }
            int totalHeight = 0;
            for (int i = 0; i < rows; i++) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            LayoutParams params = gridView.getLayoutParams();
            params.height = ((rows - 1) * horizontalBorderHeight) + totalHeight;
            gridView.setLayoutParams(params);
        }
    }

    public void onScrollChanged(int scrollY) {
        this.mStickyView.setTranslationY((float) Math.max(this.mPlaceholderView.getTop(), scrollY));
    }

    public void onDownMotionEvent() {
    }

    public void onUpOrCancelMotionEvent() {
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bmp != null || this.customeBmp != null) {
            if (this.bmp != null) {
                this.bmp.recycle();
                this.bmp = null;
            }
            if (this.customeBmp != null) {
                this.customeBmp.recycle();
                this.customeBmp = null;
            }
            System.gc();
        }
    }

    public void finish() {
        if (this.selectIndex != -1) {
            int length1 = this.headIcon1.length;
            int length2 = this.headIcon2.length;
            int length3 = this.headIcon3.length;
            int length4 = this.headIcon4.length;
            if (this.selectIndex < length1) {
                this.headIndex = this.selectIndex;
            } else if (this.selectIndex >= length1 && this.selectIndex < length1 + length2) {
                this.headIndex = (this.selectIndex - length1) + 100;
            } else if (this.selectIndex >= length1 + length2 && this.selectIndex < (length1 + length2) + length3) {
                this.headIndex = ((this.selectIndex - length1) - length2) + 200;
            } else if (this.selectIndex >= (length1 + length2) + length3 && this.selectIndex < ((length1 + length2) + length3) + length4) {
                this.headIndex = (((this.selectIndex - length1) - length2) - length3) + 300;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("headIndex", this.headIndex);
        intent.putExtra("bitmap", this.bitmapByte);
        setResult(-1, intent);
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    startPhotoZoom(Uri.fromFile(new File(this.filePath + "/temp.jpg")));
                    return;
                case 2:
                    startPhotoZoom(data.getData());
                    return;
                case 3:
                    if (data != null) {
                        this.bitmapByte = data.getByteArrayExtra("bitmap");
                        this.customeBmp = BitmapFactory.decodeByteArray(this.bitmapByte, 0, this.bitmapByte.length);
                        this.mCustom_icon.setImageBitmap(this.customeBmp);
                        this.isCustom = Boolean.valueOf(true);
                        this.selectIndex = -1;
                        this.headIndex = 10000;
                        this.mDefault_select.setVisibility(8);
                        this.mQq_select.setVisibility(8);
                        this.mWeibo_select.setVisibility(8);
                        this.mCustom_select.setVisibility(0);
                        this.mHeadAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent(this.mContext, EditPictureActivity.class);
        intent.setDataAndType(uri, "image/jpeg");
        startActivityForResult(intent, 3);
    }
}
