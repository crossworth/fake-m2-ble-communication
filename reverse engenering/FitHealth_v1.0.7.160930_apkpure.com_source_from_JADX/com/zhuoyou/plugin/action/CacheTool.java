package com.zhuoyou.plugin.action;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Message;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.mainFrame.MineFragment;
import com.zhuoyou.plugin.running.Main;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CacheTool {
    private Context mcontext;

    class ActionFilefilter implements FilenameFilter {
        String mfilter = "";

        public ActionFilefilter(String filter) {
            this.mfilter = filter;
        }

        public boolean isAction(String filename) {
            if (filename.toLowerCase().endsWith(this.mfilter)) {
                return true;
            }
            return false;
        }

        public boolean accept(File dir, String filename) {
            return isAction(filename);
        }
    }

    public CacheTool(Context context) {
        this.mcontext = context;
    }

    public boolean saveObject(Serializable ser, String file) {
        Exception e;
        Throwable th;
        boolean z = false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            Context context = this.mcontext;
            Context context2 = this.mcontext;
            fos = context.openFileOutput(file, 0);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos);
            try {
                oos2.writeObject(ser);
                oos2.flush();
                z = true;
                try {
                    oos2.close();
                } catch (Exception e2) {
                }
                try {
                    fos.close();
                } catch (Exception e3) {
                }
                oos = oos2;
            } catch (Exception e4) {
                e = e4;
                oos = oos2;
                try {
                    e.printStackTrace();
                    try {
                        oos.close();
                    } catch (Exception e5) {
                    }
                    try {
                        fos.close();
                    } catch (Exception e6) {
                    }
                    return z;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        oos.close();
                    } catch (Exception e7) {
                    }
                    try {
                        fos.close();
                    } catch (Exception e8) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                oos = oos2;
                oos.close();
                fos.close();
                throw th;
            }
        } catch (Exception e9) {
            e = e9;
            e.printStackTrace();
            oos.close();
            fos.close();
            return z;
        }
        return z;
    }

    public Serializable readObject(String file) {
        Exception e;
        Throwable th;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.mcontext.openFileInput(file);
            ObjectInputStream ois2 = new ObjectInputStream(fis);
            try {
                Serializable serializable = (Serializable) ois2.readObject();
                try {
                    ois2.close();
                } catch (Exception e2) {
                }
                try {
                    fis.close();
                } catch (Exception e3) {
                }
                ois = ois2;
                return serializable;
            } catch (FileNotFoundException e4) {
                ois = ois2;
                try {
                    ois.close();
                } catch (Exception e5) {
                }
                try {
                    fis.close();
                } catch (Exception e6) {
                }
                return null;
            } catch (Exception e7) {
                e = e7;
                ois = ois2;
                try {
                    e.printStackTrace();
                    try {
                        ois.close();
                    } catch (Exception e8) {
                    }
                    try {
                        fis.close();
                    } catch (Exception e9) {
                    }
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        ois.close();
                    } catch (Exception e10) {
                    }
                    try {
                        fis.close();
                    } catch (Exception e11) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                ois = ois2;
                ois.close();
                fis.close();
                throw th;
            }
        } catch (FileNotFoundException e12) {
            ois.close();
            fis.close();
            return null;
        } catch (Exception e13) {
            e = e13;
            e.printStackTrace();
            ois.close();
            fis.close();
            return null;
        }
    }

    public void SaveWelcomeDate(ActionWelcomeInfo welcome_data) {
        for (File mm : this.mcontext.getFilesDir().listFiles(new ActionFilefilter(".welcome"))) {
            mm.delete();
        }
        saveObject(welcome_data, welcome_data.GetID() + ".welcome");
    }

    public ActionWelcomeInfo GetWelcomeDate() {
        ActionWelcomeInfo mWelcome = null;
        for (File mm : this.mcontext.getFilesDir().listFiles(new ActionFilefilter(".welcome"))) {
            if (mWelcome == null) {
                mWelcome = (ActionWelcomeInfo) readObject(mm.getName());
            } else {
                ActionWelcomeInfo tmp = (ActionWelcomeInfo) readObject(mm.getName());
                if (tmp != null && tmp.GetID() > mWelcome.GetID()) {
                    mWelcome = tmp;
                }
            }
        }
        return mWelcome;
    }

    public void ClearListItem() {
        File file_path = this.mcontext.getFilesDir();
        List<ActionListItemInfo> mlist = new ArrayList();
        for (File mm : file_path.listFiles(new ActionFilefilter(".actionitem"))) {
            mm.delete();
        }
    }

    public void SaveActionListItem(List<ActionListItemInfo> mlist) {
        if (mlist.size() > 0) {
            int bId = GetBiggestActionIdofLocal();
            boolean state = false;
            for (int i = 0; i < mlist.size(); i++) {
                if (((ActionListItemInfo) mlist.get(i)).GetActivtyId() > bId) {
                    state = true;
                }
                saveObject((Serializable) mlist.get(i), ((ActionListItemInfo) mlist.get(i)).GetActivtyId() + ".actionitem");
            }
            if (state) {
                Tools.setActState(this.mcontext, true);
            }
        }
    }

    public List<ActionListItemInfo> GetActionListItemDate() {
        File file_path = this.mcontext.getFilesDir();
        List<ActionListItemInfo> mlist = new ArrayList();
        for (File mm : file_path.listFiles(new ActionFilefilter(".actionitem"))) {
            mlist.add((ActionListItemInfo) readObject(mm.getName()));
        }
        return mlist;
    }

    public void SaveActionInfo(ActionInfo mactioninfo) {
        int actionid = mactioninfo.GetActionId();
        if (actionid > 0) {
            saveObject(mactioninfo, actionid + ".actioninfo");
        }
    }

    public void SaveActionInfo(List<ActionInfo> mactioninfo) {
        for (int i = 0; i < mactioninfo.size(); i++) {
            SaveActionInfo((ActionInfo) mactioninfo.get(i));
        }
    }

    public ActionInfo GetActionInfoDates() {
        File file_path = this.mcontext.getFilesDir();
        ActionInfo mlist = new ActionInfo();
        for (File mm : file_path.listFiles(new ActionFilefilter(".actioninfo"))) {
            ActionWelcomeInfo tmp = (ActionWelcomeInfo) readObject(mm.getName());
            mlist = (ActionInfo) readObject(mm.getName());
        }
        return mlist;
    }

    public void SaveMsgList(List<MessageInfo> mlist) {
        ContentResolver cr = this.mcontext.getContentResolver();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        int y = 0;
        if (mlist.size() > 0) {
            String[] msgIndex = initListData().split(SeparatorConstants.SEPARATOR_ADS_ID);
            for (int i = 0; i < mlist.size(); i++) {
                if (!arryContains(msgIndex, String.valueOf(((MessageInfo) mlist.get(i)).GetMsgId()))) {
                    y = 1;
                    ContentValues values = new ContentValues();
                    values.put(DataBaseContants.ACTIVITY_ID, Integer.valueOf(((MessageInfo) mlist.get(i)).getActivityId()));
                    values.put(DataBaseContants.MSG_ID, Integer.valueOf(((MessageInfo) mlist.get(i)).GetMsgId()));
                    values.put("content", ((MessageInfo) mlist.get(i)).GetMsgContent());
                    values.put(DataBaseContants.MSG_TYPE, Integer.valueOf(((MessageInfo) mlist.get(i)).getmMsgType()));
                    values.put(DataBaseContants.MSG_TIME, formatter.format(new Date(System.currentTimeMillis())));
                    values.put(DataBaseContants.MSG_STATE, Integer.valueOf(0));
                    cr.insert(DataBaseContants.CONTENT_MSG_URI, values);
                }
            }
            if (y > 0) {
                Message msg;
                Tools.setMsgState(this.mcontext, true);
                if (Main.mHandler != null) {
                    msg = new Message();
                    msg.what = 4;
                    Main.mHandler.sendMessage(msg);
                }
                if (MineFragment.mHandler != null) {
                    msg = new Message();
                    msg.what = 4;
                    MineFragment.mHandler.sendMessage(msg);
                }
            }
        }
    }

    public static boolean arryContains(String[] stringArray, String source) {
        if (Arrays.asList(stringArray).contains(source)) {
            return true;
        }
        return false;
    }

    public boolean SaveActionInitDate(AppInitForAction mm) {
        if (mm.GetWelcomeInfo() != null) {
            SaveWelcomeDate(mm.GetWelcomeInfo());
        }
        SaveActionListItem(mm.GetActionList());
        SaveMsgList(mm.GetMsgList());
        return false;
    }

    public void ClearCachefile() {
        for (File mm : this.mcontext.getFilesDir().listFiles(new ActionFilefilter(".actionitem"))) {
            mm.delete();
        }
    }

    public int GetLocalLittleIdofActionitem() {
        int action_id = 0;
        File file_path = this.mcontext.getFilesDir();
        List<ActionInfo> mlist = new ArrayList();
        for (File mm : file_path.listFiles(new ActionFilefilter(".actionitem"))) {
            if (action_id == 0) {
                action_id = Integer.valueOf(mm.getName().split("\\.")[0]).intValue();
            } else {
                int mid = Integer.valueOf(mm.getName().split("\\.")[0]).intValue();
                if (action_id > mid) {
                    action_id = mid;
                }
            }
        }
        return action_id;
    }

    public int GetBiggestActionIdofLocal() {
        int action_id = -1;
        File file_path = this.mcontext.getFilesDir();
        List<ActionListItemInfo> mlist = new ArrayList();
        for (File mm : file_path.listFiles(new ActionFilefilter(".actionitem"))) {
            String filename;
            if (action_id == -1) {
                filename = mm.getName();
                action_id = Integer.valueOf(filename.substring(0, filename.lastIndexOf("."))).intValue();
            } else {
                filename = mm.getName();
                int tmp_action_id = Integer.valueOf(filename.substring(0, filename.lastIndexOf("."))).intValue();
                if (tmp_action_id > action_id) {
                    action_id = tmp_action_id;
                }
            }
        }
        return action_id;
    }

    private String initListData() {
        String msgId = "";
        Cursor cursor = this.mcontext.getContentResolver().query(DataBaseContants.CONTENT_MSG_URI, new String[]{DataBaseContants.MSG_ID}, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    msgId = cursor.getInt(cursor.getColumnIndex(DataBaseContants.MSG_ID)) + "";
                } else {
                    msgId = msgId + SeparatorConstants.SEPARATOR_ADS_ID + cursor.getInt(cursor.getColumnIndex(DataBaseContants.MSG_ID));
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return msgId;
    }
}
