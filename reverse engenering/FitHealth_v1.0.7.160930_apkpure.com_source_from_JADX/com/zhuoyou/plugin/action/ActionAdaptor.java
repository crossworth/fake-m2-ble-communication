package com.zhuoyou.plugin.action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fithealth.running.R;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import com.zhuoyou.plugin.rank.AsyncImageLoader;
import com.zhuoyou.plugin.rank.AsyncImageLoader.ImageCallback;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActionAdaptor extends BaseAdapter {
    private static final long VALID_VALUE = -1;
    private boolean IS_NOACTION = false;
    public AsyncImageLoader mAsyncImageLoader;
    private ListView mListView;
    private CacheTool mcachetool;
    private Context mcontext;
    private List<ActionListItemInfo> mylist = null;

    static class ViewHolder {
        public View Action_FillView;
        public View Action_Join;
        public TextView Action_Num;
        public TextView Action_State;
        public TextView Action_Time;
        public ImageView Action_preview;

        ViewHolder() {
        }
    }

    class C18581 implements ImageCallback {
        C18581() {
        }

        public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            ImageView imageViewByTag = (ImageView) ActionAdaptor.this.mListView.findViewWithTag(imageUrl);
            if (imageViewByTag == null) {
                return;
            }
            if (imageDrawable != null) {
                imageViewByTag.setImageDrawable(imageDrawable);
            } else {
                imageViewByTag.setImageResource(R.drawable.action_2);
            }
        }
    }

    public ActionAdaptor(Context context, ListView list, CacheTool cachetool) {
        this.mcontext = context;
        this.mListView = list;
        this.mcachetool = cachetool;
        this.mAsyncImageLoader = new AsyncImageLoader();
        SetMyListItem();
    }

    public int getCount() {
        int listitem_count = 0;
        if (this.mylist != null) {
            listitem_count = this.mylist.size();
            if (listitem_count == 0) {
                this.IS_NOACTION = true;
            }
        } else {
            this.IS_NOACTION = true;
        }
        if (this.IS_NOACTION) {
            return 1;
        }
        return listitem_count;
    }

    public Object getItem(int position) {
        if (this.mylist == null || position >= this.mylist.size()) {
            return null;
        }
        return this.mylist.get(position);
    }

    public long getItemId(int position) {
        if (this.IS_NOACTION || this.mylist == null || position >= this.mylist.size()) {
            return -1;
        }
        return (long) ((ActionListItemInfo) this.mylist.get(position)).GetActivtyId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mcontext).inflate(R.layout.action_listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.Action_preview = (ImageView) convertView.findViewById(R.id.action_preview);
            viewHolder.Action_Join = convertView.findViewById(R.id.is_join);
            viewHolder.Action_Num = (TextView) convertView.findViewById(R.id.action_num);
            viewHolder.Action_Time = (TextView) convertView.findViewById(R.id.action_time);
            viewHolder.Action_State = (TextView) convertView.findViewById(R.id.action_state);
            viewHolder.Action_FillView = convertView.findViewById(R.id.fill_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!this.IS_NOACTION) {
            ActionListItemInfo mm = (ActionListItemInfo) this.mylist.get(position);
            if (viewHolder.Action_preview != null) {
                String url = mm.GetActiviyImgUrl();
                try {
                    url = url.substring(0, url.lastIndexOf("/") + 1) + URLEncoder.encode(url.substring(url.lastIndexOf("/") + 1), "UTF-8").replace(SocializeConstants.OP_DIVIDER_PLUS, "%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                viewHolder.Action_preview.setTag(url);
                Drawable drawable = this.mAsyncImageLoader.loadDrawable(url, new C18581());
                if (drawable == null) {
                    viewHolder.Action_preview.setImageResource(R.drawable.action_2);
                } else {
                    viewHolder.Action_preview.setImageDrawable(drawable);
                }
            }
            viewHolder.Action_Time.setText(GetActionTimeString(mm));
            switch (GetActionState(mm)) {
                case 1:
                    viewHolder.Action_State.setBackgroundResource(R.drawable.action_state_bebeginning);
                    viewHolder.Action_State.setText(R.string.to_start);
                    viewHolder.Action_Num.setText(mm.GetActivtyNum() + this.mcontext.getResources().getString(R.string.people_registered));
                    break;
                case 2:
                    viewHolder.Action_State.setBackgroundResource(R.drawable.action_state_ongoing);
                    viewHolder.Action_State.setText(R.string.in_progress);
                    viewHolder.Action_Num.setText(mm.GetActivtyNum() + this.mcontext.getResources().getString(R.string.people_joining));
                    break;
                case 3:
                    viewHolder.Action_State.setBackgroundResource(R.drawable.action_state_closed);
                    viewHolder.Action_State.setText(R.string.ended);
                    viewHolder.Action_Num.setText(mm.GetActivtyNum() + this.mcontext.getResources().getString(R.string.people_involved));
                    break;
                default:
                    break;
            }
        }
        viewHolder.Action_preview.setImageResource(R.drawable.action_ready);
        viewHolder.Action_Join.setVisibility(8);
        viewHolder.Action_Num.setVisibility(8);
        viewHolder.Action_Time.setVisibility(8);
        viewHolder.Action_State.setVisibility(8);
        viewHolder.Action_FillView.setVisibility(8);
        return convertView;
    }

    public void SetMyListItem() {
        if (this.mcachetool != null) {
            if (!(this.mylist == null || this.mylist.size() == 0)) {
                this.mylist.clear();
            }
            this.mylist = this.mcachetool.GetActionListItemDate();
            if (this.mylist == null || this.mylist.size() <= 0) {
                this.IS_NOACTION = true;
            } else {
                this.IS_NOACTION = false;
            }
        }
    }

    public void SetMyListItem(List<ActionListItemInfo> mlist) {
        if (this.mcachetool != null) {
            if (!(this.mylist == null || this.mylist.size() == 0)) {
                this.mylist.clear();
            }
            this.mylist.addAll(mlist);
            if (this.mylist == null || this.mylist.size() <= 0) {
                this.IS_NOACTION = true;
            } else {
                this.IS_NOACTION = false;
            }
        }
    }

    public void AddListitem(List<ActionListItemInfo> mlist) {
        if (this.mcachetool != null) {
            this.mylist.addAll(mlist);
            if (this.mylist == null || this.mylist.size() <= 0) {
                this.IS_NOACTION = true;
            } else {
                this.IS_NOACTION = false;
            }
        }
    }

    public String GetActionTimeString(ActionListItemInfo mm) {
        StringBuilder atcion_time = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = mm.GetActivtyStartTime();
        String cur = mm.GetActivtyCurTime();
        String end = mm.GetActivtyEndTime();
        try {
            Date start_date = simpleDateFormat.parse(start);
            Date cur_date = simpleDateFormat.parse(cur);
            Date end_date = simpleDateFormat.parse(end);
            long st = start_date.getTime();
            long ct = cur_date.getTime();
            long et = end_date.getTime();
            int leave;
            long day;
            long hour;
            long min;
            long sec;
            if (ct < st) {
                leave = (int) (st - ct);
                day = ((long) leave) / 86400000;
                hour = ((((long) leave) % 86400000) / 3600000) + (24 * day);
                min = (((((long) leave) % 86400000) % 3600000) / TimeManager.UNIT_MINUTE) + ((24 * day) * 60);
                sec = ((((((long) leave) % 86400000) % 3600000) % TimeManager.UNIT_MINUTE) / 1000) + (((24 * day) * 60) * 60);
                atcion_time.append(this.mcontext.getResources().getString(R.string.to_start_have));
                if (day != 0) {
                    atcion_time.append(day);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.day1));
                    atcion_time.append(hour);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.hour));
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (hour != 0) {
                    atcion_time.append(hour);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.hour));
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (min != 0) {
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (sec != 0) {
                    atcion_time.append(sec);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.gps_second));
                }
            } else if (ct < et) {
                leave = (int) (et - ct);
                day = ((long) leave) / 86400000;
                hour = ((((long) leave) % 86400000) / 3600000) + (24 * day);
                min = (((((long) leave) % 86400000) % 3600000) / TimeManager.UNIT_MINUTE) + ((24 * day) * 60);
                sec = ((((((long) leave) % 86400000) % 3600000) % TimeManager.UNIT_MINUTE) / 1000) + (((24 * day) * 60) * 60);
                atcion_time.append(this.mcontext.getResources().getString(R.string.to_end));
                if (day != 0) {
                    atcion_time.append(day);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.day1));
                    atcion_time.append(hour);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.hour));
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (hour != 0) {
                    atcion_time.append(hour);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.hour));
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (min != 0) {
                    atcion_time.append(min);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.minute));
                } else if (sec != 0) {
                    atcion_time.append(sec);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.gps_second));
                }
            } else if (ct > et) {
                Calendar cld = Calendar.getInstance();
                cld.setTime(end_date);
                int month = cld.get(2) + 1;
                int day2 = cld.get(5);
                int hour2 = cld.get(11);
                if (this.mcontext.getResources().getConfiguration().locale.getLanguage().endsWith("en")) {
                    atcion_time.append(translateToEn(month));
                    atcion_time.append(" ");
                    atcion_time.append(day2);
                    atcion_time.append(" ");
                    atcion_time.append(hour2);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.clock));
                    atcion_time.append(" ");
                    atcion_time.append(this.mcontext.getResources().getString(R.string.to_finish));
                } else {
                    atcion_time.append(month);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.mouth));
                    atcion_time.append(day2);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.Sunday));
                    atcion_time.append(hour2);
                    atcion_time.append(this.mcontext.getResources().getString(R.string.clock));
                    atcion_time.append(this.mcontext.getResources().getString(R.string.to_finish));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return atcion_time.toString();
    }

    private String translateToEn(int month) {
        String monthEn = "";
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return monthEn;
        }
    }

    public int GetActionState(ActionListItemInfo mm) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = mm.GetActivtyStartTime();
        String cur = mm.GetActivtyCurTime();
        String end = mm.GetActivtyEndTime();
        try {
            Date start_date = sdf1.parse(start);
            Date cur_date = sdf1.parse(cur);
            Date end_date = sdf1.parse(end);
            long st = start_date.getTime();
            long ct = cur_date.getTime();
            long et = end_date.getTime();
            if (ct < st) {
                return 1;
            }
            if (ct < et) {
                return 2;
            }
            if (ct > et) {
                return 3;
            }
            return 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
