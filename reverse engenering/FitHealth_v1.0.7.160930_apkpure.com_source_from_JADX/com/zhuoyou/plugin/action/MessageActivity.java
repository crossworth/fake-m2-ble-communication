package com.zhuoyou.plugin.action;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends Activity {
    private List<MessageInfo> mMsgListsRead;
    private List<MessageInfo> mMsgListsUnread;
    private ListView mUnReadList;
    private MessageAdapter unReadAdapter;

    class C10921 implements OnClickListener {
        C10921() {
        }

        public void onClick(View v) {
            MessageActivity.this.finish();
        }
    }

    class C10932 implements OnItemClickListener {
        C10932() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            MessageInfo info = MessageActivity.this.unReadAdapter.getItem(position);
            Intent intent = new Intent(MessageActivity.this, MessageInfoActivity.class);
            intent.putExtra("msg_content", info.GetMsgContent());
            MessageActivity.this.updateDateBase(info.getId(), 1);
            MessageActivity.this.startActivity(intent);
        }
    }

    private class MessageAdapter extends BaseAdapter {
        private List<MessageInfo> mMsgLists;

        private class ViewHolder {
            private TextView value1;
            private TextView value2;
            private TextView value3;

            private ViewHolder() {
            }
        }

        public MessageAdapter(List<MessageInfo> mlist) {
            this.mMsgLists = mlist;
        }

        public int getCount() {
            return this.mMsgLists.size();
        }

        public MessageInfo getItem(int position) {
            return (MessageInfo) this.mMsgLists.get(position);
        }

        public long getItemId(int position) {
            return (long) ((MessageInfo) this.mMsgLists.get(position)).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.message_item, parent, false);
                holder = new ViewHolder();
                holder.value1 = (TextView) convertView.findViewById(R.id.msg_type);
                holder.value2 = (TextView) convertView.findViewById(R.id.msg_content);
                holder.value3 = (TextView) convertView.findViewById(R.id.msg_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (((MessageInfo) this.mMsgLists.get(position)).getmState() == 0) {
                holder.value1.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                holder.value2.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                holder.value3.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            } else if (((MessageInfo) this.mMsgLists.get(position)).getmState() == 1) {
                holder.value1.setTextColor(-3881788);
                holder.value2.setTextColor(-3881788);
                holder.value3.setTextColor(-3881788);
            }
            holder.value1.setText(R.string.sys_info);
            holder.value2.setText(((MessageInfo) this.mMsgLists.get(position)).GetMsgContent());
            holder.value3.setText(((MessageInfo) this.mMsgLists.get(position)).getmMsgTime());
            return convertView;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        ((TextView) findViewById(R.id.title)).setText(R.string.message);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C10921());
    }

    protected void onResume() {
        super.onResume();
        initListData();
        initView();
    }

    protected void onPause() {
        super.onPause();
        Tools.setMsgState(this, false);
        if (MineFragment.mHandler != null) {
            Message msg = new Message();
            msg.what = 4;
            MineFragment.mHandler.sendMessage(msg);
        }
        if (Main.mHandler != null) {
            msg = new Message();
            msg.what = 4;
            Main.mHandler.sendMessage(msg);
        }
    }

    private void initView() {
        this.mUnReadList = (ListView) findViewById(R.id.listUnRead);
        this.mMsgListsUnread.addAll(this.mMsgListsRead);
        this.unReadAdapter = new MessageAdapter(this.mMsgListsUnread);
        this.mUnReadList.setAdapter(this.unReadAdapter);
        this.mUnReadList.setOnItemClickListener(new C10932());
    }

    private void initListData() {
        this.mMsgListsRead = new ArrayList();
        this.mMsgListsUnread = new ArrayList();
        Cursor cursor = getContentResolver().query(DataBaseContants.CONTENT_MSG_URI, new String[]{"_id", DataBaseContants.MSG_ID, "content", DataBaseContants.MSG_TYPE, DataBaseContants.MSG_TIME, DataBaseContants.MSG_STATE}, null, null, "_id DESC");
        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                int state = cursor.getInt(cursor.getColumnIndex(DataBaseContants.MSG_STATE));
                MessageInfo info = new MessageInfo(cursor.getInt(cursor.getColumnIndex("_id")), cursor.getInt(cursor.getColumnIndex(DataBaseContants.MSG_ID)), cursor.getString(cursor.getColumnIndex("content")), cursor.getInt(cursor.getColumnIndex(DataBaseContants.MSG_TYPE)), cursor.getString(cursor.getColumnIndex(DataBaseContants.MSG_TIME)), state);
                if (state == 0) {
                    this.mMsgListsUnread.add(info);
                } else if (state == 1) {
                    this.mMsgListsRead.add(info);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }

    private void updateDateBase(int id, int state) {
        ContentResolver cr = getContentResolver();
        ContentValues updateValues = new ContentValues();
        updateValues.put(DataBaseContants.MSG_STATE, Integer.valueOf(state));
        cr.update(DataBaseContants.CONTENT_MSG_URI, updateValues, "_id = ? ", new String[]{id + ""});
    }
}
