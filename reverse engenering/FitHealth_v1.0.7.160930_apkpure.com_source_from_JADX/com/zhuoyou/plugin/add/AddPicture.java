package com.zhuoyou.plugin.add;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fithealth.running.R;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.album.BitmapUtils;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPicture extends Activity implements OnClickListener {
    private ImageView btn_add_bg;
    private ImageView btn_add_pic;
    private String date;
    private DateSelectPopupWindow datePopuWindow;
    private Display display;
    private EditText ed;
    private String fileName = "";
    private String filePath;
    private long id;
    private RelativeLayout im_back;
    private String img_uri = "";
    private Intent intent;
    private Button mButton;
    private String name;
    private boolean picAdded = false;
    private PicSelectorPopupWindow popupWindow;
    private LinearLayout rlayout;
    private RelativeLayout rlayout_startDate;
    private RelativeLayout rlayout_startTime;
    private int startHour;
    private int startOther;
    private SportTimePopupWindow startPopuWindow;
    private String startTime;
    private Bitmap thumbnailBitmap = null;
    private TextView tv_add_pic;
    private TextView tv_start_date;
    private TextView tv_start_time;
    private String wordsExplain;

    class C11071 implements TextWatcher {
        C11071() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            AddPicture.this.mButton.setText(R.string.ok);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.display = getWindowManager().getDefaultDisplay();
        setContentView(R.layout.add_pic);
        this.rlayout = (LinearLayout) findViewById(R.id.rv_main);
        this.im_back = (RelativeLayout) findViewById(R.id.back);
        this.im_back.setOnClickListener(this);
        this.tv_add_pic = (TextView) findViewById(R.id.title);
        this.tv_add_pic.setText(R.string.add_picture);
        this.mButton = (Button) findViewById(R.id.save);
        this.mButton.setOnClickListener(this);
        this.btn_add_bg = (ImageView) findViewById(R.id.btn_add_bg);
        this.btn_add_pic = (ImageView) findViewById(R.id.btn_add_pic);
        this.btn_add_pic.setOnClickListener(this);
        this.ed = (EditText) findViewById(R.id.ed_some_words);
        this.tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        this.tv_start_time = (TextView) findViewById(R.id.tv_chose_start);
        this.rlayout_startDate = (RelativeLayout) findViewById(R.id.rlayout_startDate);
        this.rlayout_startTime = (RelativeLayout) findViewById(R.id.rlayout_startTime);
        this.rlayout_startDate.setOnClickListener(this);
        this.rlayout_startTime.setOnClickListener(this);
        this.ed.setInputType(131072);
        this.ed.setGravity(48);
        this.ed.setSingleLine(false);
        this.ed.setHorizontallyScrolling(false);
        this.ed.addTextChangedListener(new C11071());
        this.intent = getIntent();
        this.wordsExplain = this.intent.getStringExtra("words");
        this.date = this.intent.getStringExtra("date");
        this.id = this.intent.getLongExtra("id", 0);
        if (this.date.equals(Tools.getDate(0))) {
            this.tv_start_date.setText(R.string.today);
        } else {
            this.tv_start_date.setText(this.date);
        }
        if (this.wordsExplain != null) {
            this.startTime = this.intent.getStringExtra("startTime");
            this.img_uri = this.intent.getStringExtra("imgUri");
            this.tv_add_pic.setText(R.string.edit_picture);
            this.ed.setText(this.wordsExplain);
            this.ed.setSelection(this.wordsExplain.length());
            this.mButton.setText(R.string.gpsdata_delete);
            if (!(this.img_uri == null || this.img_uri.equals(""))) {
                int w = this.display.getWidth() - (Tools.dip2px(this, 5.0f) * 2);
                this.thumbnailBitmap = BitmapUtils.decodeSampledBitmapFromFd2(this.img_uri, w, (w / 4) * 3, 2);
                this.btn_add_bg.setImageBitmap(this.thumbnailBitmap);
                this.btn_add_pic.setVisibility(8);
                this.fileName = this.img_uri;
            }
        } else {
            this.startTime = Tools.getStartTime();
            this.mButton.setText(R.string.ok);
        }
        this.tv_start_time.setText(this.startTime);
        this.filePath = Tools.getSDPath() + "/Running/sport_album/";
        File dir = new File(this.filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void onClick(View v) {
        final LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                return;
            case R.id.save:
                if (this.wordsExplain != null) {
                    if (this.mButton.getText().toString().equals(getResources().getString(R.string.ok))) {
                        updateDateBasePicture(this.fileName, this.ed.getText().toString().replace(SeparatorConstants.SEPARATOR_ADS_ID, "，"), this.date);
                        finish();
                        return;
                    }
                    deleteDateBasePicture();
                    finish();
                    return;
                } else if (this.picAdded || !this.ed.getText().toString().equals("")) {
                    insertDataBasePicture(this.date, this.startTime, this.fileName, this.ed.getText().toString().replace(SeparatorConstants.SEPARATOR_ADS_ID, "，"), 3, 0);
                    finish();
                    return;
                } else {
                    Toast.makeText(this, R.string.no_content, 0).show();
                    return;
                }
            case R.id.btn_add_pic:
                this.popupWindow = new PicSelectorPopupWindow(this, this);
                this.popupWindow.showAtLocation(this.rlayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                this.popupWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        lp.alpha = 1.0f;
                        AddPicture.this.getWindow().setAttributes(lp);
                    }
                });
                return;
            case R.id.rlayout_startDate:
                final String finalDate = this.date;
                this.datePopuWindow = new DateSelectPopupWindow(this, finalDate);
                this.datePopuWindow.setColor(-5022272);
                this.datePopuWindow.showAtLocation(this.rlayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                this.datePopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        lp.alpha = 1.0f;
                        AddPicture.this.getWindow().setAttributes(lp);
                        AddPicture.this.date = AddPicture.this.datePopuWindow.getStartDate();
                        if (!AddPicture.this.date.equals(finalDate)) {
                            AddPicture.this.mButton.setText(R.string.ok);
                        }
                        if (AddPicture.this.date.equals(Tools.getDate(0))) {
                            AddPicture.this.tv_start_date.setText(R.string.today);
                        } else {
                            AddPicture.this.tv_start_date.setText(AddPicture.this.date);
                        }
                    }
                });
                return;
            case R.id.rlayout_startTime:
                final String finalStartTime = this.startTime;
                String[] time = finalStartTime.split(":");
                String hour = time[0];
                String other = time[1];
                if (other.startsWith("0")) {
                    this.startHour = Integer.parseInt(hour.toString());
                    this.startOther = Integer.parseInt(other.substring(1).toString());
                } else {
                    this.startHour = Integer.parseInt(hour.toString());
                    this.startOther = Integer.parseInt(other.toString());
                }
                this.startPopuWindow = new SportTimePopupWindow(this, this.startHour, this.startOther);
                this.startPopuWindow.showAtLocation(this.rlayout, 81, 0, 0);
                getWindow().setAttributes(lp);
                this.startPopuWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        lp.alpha = 1.0f;
                        AddPicture.this.getWindow().setAttributes(lp);
                        int minutes = AddPicture.this.startPopuWindow.getStartTime();
                        if (minutes >= 60) {
                            if (minutes % 60 < 10) {
                                AddPicture.this.startTime = (minutes / 60) + ":0" + (minutes % 60);
                            } else {
                                AddPicture.this.startTime = (minutes / 60) + ":" + (minutes % 60);
                            }
                        } else if (minutes == 0) {
                            AddPicture.this.startTime = "00:00";
                        } else if (minutes < 10) {
                            AddPicture.this.startTime = "00:0" + minutes;
                        } else {
                            AddPicture.this.startTime = "00:" + minutes;
                        }
                        AddPicture.this.tv_start_time.setText(AddPicture.this.startTime);
                        if (!AddPicture.this.startTime.equals(finalStartTime)) {
                            AddPicture.this.mButton.setText(R.string.ok);
                        }
                    }
                });
                return;
            case R.id.tv_take_photo:
                this.name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
                Uri imageUri = Uri.fromFile(new File(this.filePath, this.name));
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", imageUri);
                startActivityForResult(intent, 1);
                this.popupWindow.dismiss();
                return;
            case R.id.tv_chose_pic:
                intent.setAction("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/jpg");
                startActivityForResult(intent, 2);
                this.popupWindow.dismiss();
                return;
            default:
                return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            int w;
            if (requestCode == 1) {
                this.fileName = this.filePath + this.name;
                this.picAdded = true;
                w = this.display.getWidth() - (Tools.dip2px(this, 5.0f) * 2);
                this.thumbnailBitmap = BitmapUtils.decodeSampledBitmapFromFd2(this.fileName, w, (w / 4) * 3, 2);
                this.btn_add_bg.setImageBitmap(this.thumbnailBitmap);
                this.btn_add_pic.setVisibility(8);
            } else if (requestCode == 2) {
                Cursor cursor = managedQuery(data.getData(), new String[]{"_data"}, null, null, null);
                int colume_index = cursor.getColumnIndexOrThrow("_data");
                cursor.moveToFirst();
                this.fileName = cursor.getString(colume_index);
                w = this.display.getWidth() - (Tools.dip2px(this, 5.0f) * 2);
                this.thumbnailBitmap = BitmapUtils.decodeSampledBitmapFromFd2(this.fileName, w, (w / 4) * 3, 2);
                this.btn_add_bg.setImageBitmap(this.thumbnailBitmap);
                this.btn_add_pic.setVisibility(8);
                this.picAdded = true;
            }
            if (!this.fileName.equals(this.img_uri)) {
                this.mButton.setText(R.string.ok);
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", this.name);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (TextUtils.isEmpty(this.name)) {
            this.name = savedInstanceState.getString("name");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.thumbnailBitmap != null) {
            this.thumbnailBitmap.recycle();
            this.thumbnailBitmap = null;
            System.gc();
        }
    }

    private void insertDataBasePicture(String date, String time, String img_uri, String explain, int type, int statistics) {
        ContentValues runningItem = new ContentValues();
        runningItem.put("_id", Long.valueOf(Tools.getPKL()));
        runningItem.put("date", date);
        runningItem.put(DataBaseContants.TIME_START, time);
        runningItem.put(DataBaseContants.IMG_URI, img_uri);
        runningItem.put(DataBaseContants.EXPLAIN, explain);
        runningItem.put("type", Integer.valueOf(type));
        runningItem.put(DataBaseContants.STATISTICS, Integer.valueOf(statistics));
        getContentResolver().insert(DataBaseContants.CONTENT_URI, runningItem);
    }

    private void updateDateBasePicture(String img_uri, String explain, String date) {
        ContentValues updateValues = new ContentValues();
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(DataBaseContants.CONTENT_URI, new String[]{DataBaseContants.SYNC_STATE}, "_id = ? ", new String[]{String.valueOf(this.id)}, null);
        if (c.getCount() > 0 && c.moveToFirst()) {
            if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                updateValues.put("date", date);
                updateValues.put(DataBaseContants.TIME_START, this.startTime);
                updateValues.put(DataBaseContants.IMG_URI, img_uri);
                updateValues.put(DataBaseContants.EXPLAIN, explain);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(0));
            } else {
                updateValues.put("date", date);
                updateValues.put(DataBaseContants.TIME_START, this.startTime);
                updateValues.put(DataBaseContants.IMG_URI, img_uri);
                updateValues.put(DataBaseContants.EXPLAIN, explain);
                updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(2));
            }
        }
        c.close();
        cr.update(DataBaseContants.CONTENT_URI, updateValues, "_id = ? ", new String[]{String.valueOf(this.id)});
    }

    private void deleteDateBasePicture() {
        ContentResolver cr = getContentResolver();
        cr.delete(DataBaseContants.CONTENT_URI, "_id = ?", new String[]{String.valueOf(this.id)});
        ContentValues values = new ContentValues();
        values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(this.id));
        cr.insert(DataBaseContants.CONTENT_DELETE_URI, values);
    }

    private int readPicDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Bitmap rotate(Bitmap b, int degree) {
        if (degree == 0) {
            return b;
        }
        if (!(degree == 0 || b == null)) {
            Matrix m = new Matrix();
            m.setRotate((float) degree, (float) b.getWidth(), (float) b.getHeight());
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError e) {
            }
        }
        return b;
    }
}
