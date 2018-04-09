package com.zhuoyou.plugin.bluetooth.connection;

import android.content.Context;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MessageDataList {
    private static final String LOG_TAG = "MessageDataList";
    private static final int MAX_MSG_COUNT = 5;
    private static final String SAVE_FILE_NAME = "MessageDataList";
    private Context mContext = null;
    private LinkedList<byte[]> mMsgList = null;

    public MessageDataList(Context context) {
        Log.i("MessageDataList", "MessageList(), MessageList created!");
        this.mContext = context;
        loadMessageDataList();
    }

    public void saveMessageData(byte[] msgData) {
        Log.i("MessageDataList", "saveMessageData(), msgData=" + Arrays.toString(msgData));
        if (this.mMsgList.size() >= 5) {
            this.mMsgList.remove(0);
        }
        this.mMsgList.add(msgData);
    }

    public List<byte[]> getMessageDataList() {
        Log.i("MessageDataList", "getMessageDataList(), msgData=" + this.mMsgList);
        if (this.mMsgList == null) {
            loadMessageDataList();
        }
        return this.mMsgList;
    }

    private void loadMessageDataList() {
        Log.i("MessageDataList", "loadMessageDataList(),  file_name= MessageDataList");
        try {
            Object obj = new ObjectInputStream(this.mContext.openFileInput("MessageDataList")).readObject();
            if (obj instanceof LinkedList) {
                this.mMsgList = (LinkedList) obj;
            }
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception2) {
            exception2.printStackTrace();
        }
        if (this.mMsgList == null) {
            this.mMsgList = new LinkedList();
        }
    }

    public void saveMessageDataList() {
        Log.i("MessageDataList", "saveMessageDataList(),  file_name= MessageDataList");
        if (this.mMsgList != null) {
            try {
                FileOutputStream fileoutputstream = this.mContext.openFileOutput("MessageDataList", 0);
                ObjectOutputStream objectoutputstream = new ObjectOutputStream(fileoutputstream);
                objectoutputstream.writeObject(this.mMsgList);
                objectoutputstream.close();
                fileoutputstream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Log.i("MessageDataList", "saveMessageDataList(),  mMsgList= " + this.mMsgList);
        }
    }
}
