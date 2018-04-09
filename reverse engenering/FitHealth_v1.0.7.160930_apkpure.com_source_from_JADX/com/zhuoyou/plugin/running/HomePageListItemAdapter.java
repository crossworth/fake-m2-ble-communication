package com.zhuoyou.plugin.running;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.album.AlbumPopupWindow;
import com.zhuoyou.plugin.album.AsyncLoadImageTask;
import com.zhuoyou.plugin.album.BitmapUtils;
import com.zhuoyou.plugin.album.SportsAlbumAdapter;
import com.zhuoyou.plugin.album.SportsAlbumAdapter.LoadedDrawable;
import com.zhuoyou.plugin.view.PolylineChart;
import java.util.ArrayList;
import java.util.List;

public class HomePageListItemAdapter extends BaseAdapter {
    private static final int TYPE_ADD_SPORT = 3;
    private static final int TYPE_DEVICE_SPORT = 2;
    private static final int TYPE_GPS = 5;
    private static final int TYPE_GRAPHIC = 4;
    private static final int TYPE_HEARTRATE = 7;
    private static final int TYPE_PHONE_STEPS = 6;
    private static final int TYPE_SUMMARY = 0;
    private static final int TYPE_WEIGHT = 1;
    private AlbumPopupWindow albumPW;
    private int chartViewHeight;
    private String date;
    private Display display;
    private LayoutInflater inflater;
    private Context mContext;
    private Typeface mNumberTP;
    private List<RunningItem> mTodayLists;
    private float record;
    private ArrayList<Double> weightList = null;

    private class ViewHolder0 {
        private ImageView img;
        private RelativeLayout summay_lay;
        private TextView value1;

        private ViewHolder0() {
        }
    }

    private class ViewHolder1 {
        private RelativeLayout ray;
        private TextView time;
        private TextView value1;
        private RelativeLayout weight_lay;

        private ViewHolder1() {
        }
    }

    private class ViewHolder2 {
        private RelativeLayout steps_lay;
        private TextView time;
        private TextView value1;
        private TextView value2;
        private TextView value3;

        private ViewHolder2() {
        }
    }

    private class ViewHolder3 {
        private ImageView img;
        private RelativeLayout sports_lay;
        private TextView time;
        private TextView value1;
        private TextView value2;

        private ViewHolder3() {
        }
    }

    private class ViewHolder4 {
        private RelativeLayout graphic_lay;
        private ImageView imageview_thumbnail;
        private TextView time;
        private TextView value1;

        private ViewHolder4() {
        }
    }

    private class ViewHolder5 {
        private RelativeLayout gps_lay;
        private ImageView imageview;
        private TextView time;
        private TextView value1;
        private TextView value2;
        private TextView value3;
        private TextView value4;

        private ViewHolder5() {
        }
    }

    private class ViewHolder6 {
        private RelativeLayout steps_lay;
        private TextView time;
        private TextView value1;
        private TextView value2;
        private TextView value3;

        private ViewHolder6() {
        }
    }

    private class ViewHolder7 {
        private RelativeLayout heartrate_lay;
        private TextView value1;
        private TextView value2;
        private TextView value3;

        private ViewHolder7() {
        }
    }

    public HomePageListItemAdapter(Context ctx, List<RunningItem> list, String date, ArrayList<Double> weight, float z) {
        this.mContext = ctx;
        this.mTodayLists = list;
        this.mNumberTP = RunningApp.getCustomNumberFont();
        this.date = date;
        this.weightList = weight;
        this.record = z;
        this.display = ((Activity) this.mContext).getWindowManager().getDefaultDisplay();
    }

    public void UpdateDate(Context ctx, List<RunningItem> list, String date, ArrayList<Double> weight, float z) {
        this.mContext = ctx;
        this.mTodayLists = list;
        this.date = date;
        this.weightList = weight;
        this.record = z;
    }

    public int getCount() {
        return this.mTodayLists.size();
    }

    public RunningItem getItem(int position) {
        return (RunningItem) this.mTodayLists.get(position);
    }

    public int getItemViewType(int position) {
        switch (getItem(position).getmType()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                if (getItem(position).getSportsType() == 0) {
                    return 2;
                }
                return 3;
            case 3:
                return 4;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            default:
                return 0;
        }
    }

    public int getViewTypeCount() {
        return 8;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder0 holder0 = null;
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        ViewHolder4 holder4 = null;
        ViewHolder5 holder5 = null;
        ViewHolder6 holder6 = null;
        ViewHolder7 holder7 = null;
        int type = getItemViewType(position);
        if (convertView != null) {
            switch (type) {
                case 0:
                    holder0 = (ViewHolder0) convertView.getTag();
                    break;
                case 1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case 2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case 3:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;
                case 4:
                    holder4 = (ViewHolder4) convertView.getTag();
                    break;
                case 5:
                    holder5 = (ViewHolder5) convertView.getTag();
                    break;
                case 6:
                    holder6 = (ViewHolder6) convertView.getTag();
                    break;
                case 7:
                    holder7 = (ViewHolder7) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        this.inflater = LayoutInflater.from(this.mContext);
        HomePageListItemAdapter homePageListItemAdapter;
        switch (type) {
            case 0:
                convertView = this.inflater.inflate(R.layout.listitem_summay, parent, false);
                HomePageListItemAdapter homePageListItemAdapter2 = this;
                holder0 = new ViewHolder0();
                holder0.value1 = (TextView) convertView.findViewById(R.id.description);
                holder0.img = (ImageView) convertView.findViewById(R.id.item_run_logo);
                holder0.summay_lay = (RelativeLayout) convertView.findViewById(R.id.summay_lay);
                convertView.setTag(holder0);
                break;
            case 1:
                convertView = this.inflater.inflate(R.layout.listitem_weight, parent, false);
                homePageListItemAdapter = this;
                ViewHolder1 viewHolder1 = new ViewHolder1();
                viewHolder1.value1 = (TextView) convertView.findViewById(R.id.weight);
                viewHolder1.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder1.ray = (RelativeLayout) convertView.findViewById(R.id.polyline);
                viewHolder1.weight_lay = (RelativeLayout) convertView.findViewById(R.id.weight_lay);
                convertView.setTag(viewHolder1);
                break;
            case 2:
                convertView = this.inflater.inflate(R.layout.listitem_device_steps, parent, false);
                homePageListItemAdapter = this;
                ViewHolder2 viewHolder2 = new ViewHolder2();
                viewHolder2.value1 = (TextView) convertView.findViewById(R.id.steps);
                viewHolder2.value2 = (TextView) convertView.findViewById(R.id.distance);
                viewHolder2.value3 = (TextView) convertView.findViewById(R.id.calories);
                viewHolder2.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder2.steps_lay = (RelativeLayout) convertView.findViewById(R.id.steps_lay);
                convertView.setTag(viewHolder2);
                break;
            case 3:
                convertView = this.inflater.inflate(R.layout.listitem_sports, parent, false);
                homePageListItemAdapter = this;
                ViewHolder3 viewHolder3 = new ViewHolder3();
                viewHolder3.value1 = (TextView) convertView.findViewById(R.id.duration);
                viewHolder3.value2 = (TextView) convertView.findViewById(R.id.calories);
                viewHolder3.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder3.img = (ImageView) convertView.findViewById(R.id.item_run_logo);
                viewHolder3.sports_lay = (RelativeLayout) convertView.findViewById(R.id.sports_lay);
                convertView.setTag(viewHolder3);
                break;
            case 4:
                convertView = this.inflater.inflate(R.layout.listitem_graphic_information, parent, false);
                homePageListItemAdapter = this;
                ViewHolder4 viewHolder4 = new ViewHolder4();
                viewHolder4.imageview_thumbnail = (ImageView) convertView.findViewById(R.id.picture);
                viewHolder4.value1 = (TextView) convertView.findViewById(R.id.graphic_info);
                viewHolder4.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder4.graphic_lay = (RelativeLayout) convertView.findViewById(R.id.graphic_lay);
                convertView.setTag(viewHolder4);
                break;
            case 5:
                convertView = this.inflater.inflate(R.layout.listitem_gps, parent, false);
                homePageListItemAdapter = this;
                ViewHolder5 viewHolder5 = new ViewHolder5();
                viewHolder5.imageview = (ImageView) convertView.findViewById(R.id.picture);
                viewHolder5.value1 = (TextView) convertView.findViewById(R.id.value1);
                viewHolder5.value2 = (TextView) convertView.findViewById(R.id.value2);
                viewHolder5.value3 = (TextView) convertView.findViewById(R.id.value3);
                viewHolder5.value4 = (TextView) convertView.findViewById(R.id.value4);
                viewHolder5.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder5.gps_lay = (RelativeLayout) convertView.findViewById(R.id.gps_lay);
                convertView.setTag(viewHolder5);
                break;
            case 6:
                convertView = this.inflater.inflate(R.layout.listitem_phone_steps, parent, false);
                homePageListItemAdapter = this;
                ViewHolder6 viewHolder6 = new ViewHolder6();
                viewHolder6.value1 = (TextView) convertView.findViewById(R.id.steps);
                viewHolder6.value2 = (TextView) convertView.findViewById(R.id.distance);
                viewHolder6.value3 = (TextView) convertView.findViewById(R.id.calories);
                viewHolder6.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder6.steps_lay = (RelativeLayout) convertView.findViewById(R.id.steps_lay);
                convertView.setTag(viewHolder6);
                break;
            case 7:
                convertView = this.inflater.inflate(R.layout.listitem_heartrate, parent, false);
                homePageListItemAdapter = this;
                ViewHolder7 viewHolder7 = new ViewHolder7();
                viewHolder7.value1 = (TextView) convertView.findViewById(R.id.heartrate_count);
                viewHolder7.value2 = (TextView) convertView.findViewById(R.id.heartrate_date);
                viewHolder7.value3 = (TextView) convertView.findViewById(R.id.heartrate_time);
                viewHolder7.heartrate_lay = (RelativeLayout) convertView.findViewById(R.id.heartrate_lay);
                convertView.setTag(viewHolder7);
                break;
        }
        String start = "";
        String end = "";
        String s_time = "";
        TextView access$1000;
        TextView textView;
        String path;
        Bitmap bmp;
        AsyncLoadImageTask task;
        switch (type) {
            case 0:
                if (this.record == 10000.0f) {
                    holder0.value1.setText(R.string.day_summary_5);
                    holder0.img.setImageResource(R.drawable.day_summary_5);
                } else if (this.record == 0.0f) {
                    holder0.value1.setText(R.string.day_summary_1);
                    holder0.img.setImageResource(R.drawable.day_summary_1);
                } else if (this.record > 0.0f && ((double) this.record) < 0.5d) {
                    holder0.value1.setText(R.string.day_summary_2);
                    holder0.img.setImageResource(R.drawable.day_summary_2);
                } else if (((double) this.record) >= 0.5d && this.record < 1.0f) {
                    holder0.value1.setText(R.string.day_summary_3);
                    holder0.img.setImageResource(R.drawable.day_summary_3);
                } else if (this.record >= 1.0f) {
                    holder0.value1.setText(R.string.day_summary_4);
                    holder0.img.setImageResource(R.drawable.day_summary_4);
                }
                if (this.mTodayLists.size() - 1 == position) {
                    holder0.summay_lay.setBackgroundResource(R.drawable.listitem_summay_bg_1);
                    break;
                }
                break;
            case 1:
                holder1.value1.setTypeface(this.mNumberTP);
                holder1.time.setText(((RunningItem) this.mTodayLists.get(position)).getStartTime());
                holder1.value1.setText(((RunningItem) this.mTodayLists.get(position)).getmWeight());
                if (this.weightList.size() > 0) {
                    setPolylineView(this.weightList, holder1.ray);
                }
                if (this.mTodayLists.size() - 1 == position) {
                    holder1.weight_lay.setBackgroundResource(R.drawable.listitem_weight_bg_1);
                    break;
                }
                break;
            case 2:
                holder2.value1.setTypeface(this.mNumberTP);
                holder2.value2.setTypeface(this.mNumberTP);
                holder2.value3.setTypeface(this.mNumberTP);
                start = ((RunningItem) this.mTodayLists.get(position)).getStartTime();
                holder2.time.setText(start + " - " + ((RunningItem) this.mTodayLists.get(position)).getEndTime());
                access$1000 = holder2.value1;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getSteps() + "");
                access$1000 = holder2.value2;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getKilometer() + "");
                access$1000 = holder2.value3;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getCalories() + "");
                if (this.mTodayLists.size() - 1 == position) {
                    holder2.steps_lay.setBackgroundResource(R.drawable.listitem_sports_bg_1);
                    break;
                }
                break;
            case 3:
                holder3.value1.setTypeface(this.mNumberTP);
                holder3.value2.setTypeface(this.mNumberTP);
                holder3.img.setImageResource(Tools.sportType[((RunningItem) this.mTodayLists.get(position)).getSportsType() - 1]);
                access$1000 = holder3.value1;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getDuration() + "");
                access$1000 = holder3.value2;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getCalories() + "");
                start = ((RunningItem) this.mTodayLists.get(position)).getStartTime();
                holder3.time.setText(start + " - " + ((RunningItem) this.mTodayLists.get(position)).getEndTime());
                if (this.mTodayLists.size() - 1 == position) {
                    holder3.sports_lay.setBackgroundResource(R.drawable.listitem_sports_bg_1);
                    break;
                }
                break;
            case 4:
                LayoutParams lp = ((Activity) this.mContext).getWindow().getAttributes();
                lp.alpha = 1.0f;
                if (((RunningItem) this.mTodayLists.get(position)).getmExplain() == null || ((RunningItem) this.mTodayLists.get(position)).getmExplain().equals("")) {
                    holder4.value1.setText(null);
                } else {
                    holder4.value1.setText(((RunningItem) this.mTodayLists.get(position)).getmExplain());
                }
                final ImageView img = holder4.imageview_thumbnail;
                int w = this.display.getWidth() - (Tools.dip2px(this.mContext, 5.0f) * 2);
                int h = (w / 4) * 3;
                String url = ((RunningItem) this.mTodayLists.get(position)).getmImgUri();
                if (url == null || url.equals("") || img == null) {
                    img.setImageDrawable(null);
                } else {
                    path = Tools.getSDPath() + "/Running/.thumbnailnew/" + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                    bmp = (Bitmap) HomePageItemFragment.gridviewBitmapCaches.get(path + this.date + position);
                    if (bmp != null) {
                        img.setImageBitmap(bmp);
                    } else {
                        bmp = BitmapUtils.getScaleBitmap(path);
                        if (bmp != null) {
                            img.setImageBitmap(bmp);
                            HomePageItemFragment.gridviewBitmapCaches.put(path + this.date + position, bmp);
                        } else if (SportsAlbumAdapter.cancelPotentialLoad(url, img)) {
                            task = new AsyncLoadImageTask(img, url, w, h, path + this.date + position);
                            img.setImageDrawable(new LoadedDrawable(task));
                            task.execute(new Integer[]{Integer.valueOf(position)});
                        }
                    }
                }
                holder4.time.setText(((RunningItem) this.mTodayLists.get(position)).getStartTime());
                final int i = position;
                final LayoutParams layoutParams = lp;
                holder4.imageview_thumbnail.setOnClickListener(new OnClickListener() {

                    class C13711 implements OnDismissListener {
                        C13711() {
                        }

                        public void onDismiss() {
                            layoutParams.alpha = 1.0f;
                            ((Activity) HomePageListItemAdapter.this.mContext).getWindow().setAttributes(layoutParams);
                            HomePageListItemAdapter.this.albumPW = null;
                        }
                    }

                    public void onClick(View v) {
                        if (HomePageListItemAdapter.this.albumPW == null || !HomePageListItemAdapter.this.albumPW.isShowing()) {
                            HomePageListItemAdapter.this.albumPW = new AlbumPopupWindow(HomePageListItemAdapter.this.mContext, ((RunningItem) HomePageListItemAdapter.this.mTodayLists.get(i)).getmImgUri());
                            HomePageListItemAdapter.this.albumPW.showAtLocation(img, 17, 0, 0);
                            HomePageListItemAdapter.this.albumPW.setOnDismissListener(new C13711());
                        }
                    }
                });
                if (this.mTodayLists.size() - 1 == position) {
                    holder4.graphic_lay.setBackgroundResource(R.drawable.listitem_graphic_information_bg_1);
                    break;
                }
                break;
            case 5:
                int hour;
                String minute;
                int day;
                int newHour;
                StringBuilder builder;
                String str = "lsj";
                Log.i(str, "Explain:" + ((RunningItem) this.mTodayLists.get(position)).getmExplain());
                access$1000 = holder5.value1;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getmExplain() + "");
                int wgps = Tools.dip2px(this.mContext, 155.0f);
                int hgps = Tools.dip2px(this.mContext, 98.0f);
                String urlGps = ((RunningItem) this.mTodayLists.get(position)).getmImgUri();
                if (urlGps != null) {
                    if (!(urlGps.equals("") || holder5.imageview == null)) {
                        path = Tools.getSDPath() + "/Running/.thumbnail/" + urlGps.substring(urlGps.lastIndexOf("/") + 1, urlGps.lastIndexOf("."));
                        bmp = (Bitmap) HomePageItemFragment.gridviewBitmapCaches.get(path + this.date + position);
                        if (bmp != null) {
                            holder5.imageview.setImageBitmap(bmp);
                        } else {
                            bmp = BitmapUtils.getScaleBitmap(path);
                            if (bmp != null) {
                                holder5.imageview.setImageBitmap(bmp);
                                HomePageItemFragment.gridviewBitmapCaches.put(path + this.date + position, bmp);
                            } else {
                                if (SportsAlbumAdapter.cancelPotentialLoad(urlGps, holder5.imageview)) {
                                    task = new AsyncLoadImageTask(holder5.imageview, urlGps, wgps, path + this.date + position);
                                    holder5.imageview.setImageDrawable(new LoadedDrawable(task));
                                    task.execute(new Integer[]{Integer.valueOf(position)});
                                }
                            }
                        }
                        start = ((RunningItem) this.mTodayLists.get(position)).getStartTime();
                        end = ((RunningItem) this.mTodayLists.get(position)).getEndTime();
                        hour = Integer.valueOf(end.split(":")[0]).intValue();
                        if (hour < 24) {
                            minute = end.split(":")[1];
                            day = hour / 24;
                            newHour = hour - (day * 24);
                            if (newHour >= 10) {
                                end = "0" + newHour + ":" + minute;
                            } else {
                                end = newHour + ":" + minute;
                            }
                            builder = new StringBuilder(start);
                            builder.append(" - ");
                            builder.append(Tools.getDate(this.date, 0 - day).substring(5));
                            builder.append(" ");
                            builder.append(end);
                            s_time = builder.toString();
                        } else {
                            s_time = start + " - " + end;
                        }
                        holder5.time.setText(s_time);
                        if (this.mTodayLists.size() - 1 == position) {
                            holder5.gps_lay.setBackgroundResource(R.drawable.listitem_gps_bg_1);
                        }
                        holder5.value1.setText(((RunningItem) this.mTodayLists.get(position)).getmWeight());
                        holder5.value2.setText(((RunningItem) this.mTodayLists.get(position)).getmBmi());
                        access$1000 = holder5.value3;
                        textView = access$1000;
                        textView.setText(((RunningItem) this.mTodayLists.get(position)).getKilometer() + "");
                        access$1000 = holder5.value4;
                        textView = access$1000;
                        textView.setText(((RunningItem) this.mTodayLists.get(position)).getCalories() + "");
                        break;
                    }
                }
                holder5.imageview.setVisibility(8);
                start = ((RunningItem) this.mTodayLists.get(position)).getStartTime();
                end = ((RunningItem) this.mTodayLists.get(position)).getEndTime();
                hour = Integer.valueOf(end.split(":")[0]).intValue();
                if (hour < 24) {
                    s_time = start + " - " + end;
                } else {
                    minute = end.split(":")[1];
                    day = hour / 24;
                    newHour = hour - (day * 24);
                    if (newHour >= 10) {
                        end = newHour + ":" + minute;
                    } else {
                        end = "0" + newHour + ":" + minute;
                    }
                    builder = new StringBuilder(start);
                    builder.append(" - ");
                    builder.append(Tools.getDate(this.date, 0 - day).substring(5));
                    builder.append(" ");
                    builder.append(end);
                    s_time = builder.toString();
                }
                holder5.time.setText(s_time);
                if (this.mTodayLists.size() - 1 == position) {
                    holder5.gps_lay.setBackgroundResource(R.drawable.listitem_gps_bg_1);
                }
                holder5.value1.setText(((RunningItem) this.mTodayLists.get(position)).getmWeight());
                holder5.value2.setText(((RunningItem) this.mTodayLists.get(position)).getmBmi());
                access$1000 = holder5.value3;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getKilometer() + "");
                access$1000 = holder5.value4;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getCalories() + "");
            case 6:
                holder6.value1.setTypeface(this.mNumberTP);
                holder6.value2.setTypeface(this.mNumberTP);
                holder6.value3.setTypeface(this.mNumberTP);
                start = ((RunningItem) this.mTodayLists.get(position)).getStartTime();
                holder6.time.setText(start + " - " + ((RunningItem) this.mTodayLists.get(position)).getEndTime());
                access$1000 = holder6.value1;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getSteps() + "");
                access$1000 = holder6.value2;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getKilometer() + "");
                access$1000 = holder6.value3;
                textView = access$1000;
                textView.setText(((RunningItem) this.mTodayLists.get(position)).getCalories() + "");
                if (this.mTodayLists.size() - 1 == position) {
                    holder6.steps_lay.setBackgroundResource(R.drawable.listitem_sports_bg_1);
                    break;
                }
                break;
            case 7:
                holder7.value1.setTypeface(this.mNumberTP);
                holder7.value2.setTypeface(this.mNumberTP);
                holder7.value1.setText(((RunningItem) this.mTodayLists.get(position)).getHeart_rate_count());
                holder7.value2.setText(((RunningItem) this.mTodayLists.get(position)).getStartTime());
                holder7.value3.setText(((RunningItem) this.mTodayLists.get(position)).getDate());
                break;
        }
        return convertView;
    }

    private void setPolylineView(ArrayList<Double> weightList, RelativeLayout ray) {
        this.chartViewHeight = Tools.dip2px(this.mContext, 100.0f);
        PolylineChart polylineChart = new PolylineChart(this.mContext, weightList, 3, this.chartViewHeight, 80);
        RelativeLayout localRelativeLayout = new RelativeLayout(this.mContext);
        RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(polylineChart.getCanvasWidth(), this.chartViewHeight);
        localLayoutParams.addRule(12);
        localRelativeLayout.setLayoutParams(localLayoutParams);
        localRelativeLayout.addView(polylineChart);
        ray.addView(localRelativeLayout);
    }
}
