package com.zhuoyou.plugin.bluetooth.data;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import com.zhuoyou.plugin.running.RunningApp;
import java.util.HashMap;
import java.util.Map.Entry;

public class SmsContentObserver extends ContentObserver {
    private static final String TAG = "MessageObserver";
    private static final Context sContext = RunningApp.getInstance().getApplicationContext();
    private final String HEADER = "telecom/msg/";
    private SmsController mSmsController = null;
    private HashMap<Long, MsgItem> previousMessage;

    public class DatabaseMonitor extends Thread {
        public static final int MONITER_TYPE_ONLY_QUERY = 0;
        public static final int MONITER_TYPE_QUERY_AND_NOTIFY = 1;
        private int mQueryType = 0;

        private void queryMessage(java.util.HashMap<java.lang.Long, com.zhuoyou.plugin.bluetooth.data.MsgItem> r10) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005b in list [B:13:0x0058]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r9 = this;
            r8 = 0;
            r0 = com.zhuoyou.plugin.bluetooth.data.SmsContentObserver.sContext;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r1 = "content://sms/";	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r1 = android.net.Uri.parse(r1);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r2 = 3;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r3 = 0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r4 = "_id";	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r2[r3] = r4;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r3 = 1;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r4 = "type";	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r2[r3] = r4;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r3 = 2;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r4 = "read";	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r2[r3] = r4;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r3 = 0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r4 = 0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r5 = 0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r8 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            if (r8 == 0) goto L_0x005c;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
        L_0x002a:
            r0 = r8.moveToNext();	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            if (r0 == 0) goto L_0x005c;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
        L_0x0030:
            r7 = new com.zhuoyou.plugin.bluetooth.data.MsgItem;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r7.<init>();	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = 1;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = r8.getInt(r0);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r7.mType = r0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = 2;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = r8.getInt(r0);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r7.mRead = r0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = 0;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = r8.getLong(r0);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r0 = java.lang.Long.valueOf(r0);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            r10.put(r0, r7);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
            goto L_0x002a;
        L_0x0050:
            r6 = move-exception;
            if (r8 == 0) goto L_0x0056;
        L_0x0053:
            r8.close();	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
        L_0x0056:
            if (r8 == 0) goto L_0x005b;
        L_0x0058:
            r8.close();
        L_0x005b:
            return;
        L_0x005c:
            if (r8 == 0) goto L_0x005b;
        L_0x005e:
            r8.close();
            goto L_0x005b;
        L_0x0062:
            r0 = move-exception;
            if (r8 == 0) goto L_0x0068;
        L_0x0065:
            r8.close();
        L_0x0068:
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.bluetooth.data.SmsContentObserver.DatabaseMonitor.queryMessage(java.util.HashMap):void");
        }

        public DatabaseMonitor(int type) {
            this.mQueryType = type;
        }

        public void run() {
            if (this.mQueryType == 0) {
                query();
            } else if (1 == this.mQueryType) {
                queryAndNotify();
            } else {
                Log.i(SmsContentObserver.TAG, "invalid monitor type:" + this.mQueryType);
            }
        }

        private synchronized void query() {
            queryMessage(SmsContentObserver.this.previousMessage);
            Log.i(SmsContentObserver.TAG, "query: size->" + SmsContentObserver.this.previousMessage.size());
        }

        private synchronized void queryAndNotify() {
            String errorString;
            HashMap<Long, MsgItem> currentMessage = new HashMap();
            queryMessage(currentMessage);
            Log.i(SmsContentObserver.TAG, "database has been changed, mType is  previous size is " + SmsContentObserver.this.previousMessage.size() + "current size is " + currentMessage.size());
            Long key;
            Intent newSMSIntent;
            if (SmsContentObserver.this.previousMessage.size() < currentMessage.size()) {
                for (Entry entry : currentMessage.entrySet()) {
                    key = (Long) entry.getKey();
                    String folder = revertMailboxType(((MsgItem) currentMessage.get(key)).mType);
                    if (!(SmsContentObserver.this.previousMessage.containsKey(key) || folder == null || !folder.equals(Mailbox.INBOX))) {
                        SmsContentObserver.this.mSmsController.onMessageEvent(key, "telecom/msg/" + folder, 1);
                        newSMSIntent = new Intent();
                        newSMSIntent.setAction("com.mtk.btnotification.SMS_RECEIVED");
                        SmsContentObserver.sContext.sendBroadcast(newSMSIntent);
                        Log.i("gchk", "检测到新短信.准备发送数据了");
                    }
                }
            } else {
                for (Entry entry2 : SmsContentObserver.this.previousMessage.entrySet()) {
                    key = (Long) entry2.getKey();
                    if (currentMessage.containsKey(key)) {
                        String oldFolder = revertMailboxType(((MsgItem) entry2.getValue()).mType);
                        String newFolder = revertMailboxType(((MsgItem) currentMessage.get(key)).mType);
                        if (newFolder == null || oldFolder == null || oldFolder.equals(newFolder)) {
                            if (oldFolder.equals(Mailbox.INBOX) && SmsContentObserver.this.previousMessage.size() == currentMessage.size()) {
                                int oldRead = ((MsgItem) entry2.getValue()).mRead;
                                int newRead = ((MsgItem) currentMessage.get(key)).mRead;
                                if (oldRead == 0 && newRead == 1) {
                                    Log.i("gchk", "手机端有读了新短信.数据已经置为已读 ID = " + key);
                                    newSMSIntent = new Intent();
                                    newSMSIntent.setAction("com.tyd.btsecretary.SMS_UNREAD_TO_READ");
                                    newSMSIntent.putExtra("read_id", key);
                                    SmsContentObserver.sContext.sendBroadcast(newSMSIntent);
                                    Log.i("gchk", "检测到短信由未读设置成已读.准备发送数据了 KEY=" + key);
                                }
                            }
                        } else if (!newFolder.equals(Mailbox.DELETED)) {
                            SmsContentObserver.this.mSmsController.onMessageEvent(key, "telecom/msg/" + oldFolder, 3);
                        }
                    } else {
                        try {
                            SmsContentObserver.this.mSmsController.onMessageEvent(key, "telecom/msg/" + revertMailboxType(((MsgItem) SmsContentObserver.this.previousMessage.get(key)).mType), 2);
                        } catch (Exception e) {
                            errorString = e.toString();
                            if (errorString == null) {
                                errorString = "querry error";
                            }
                            Log.w(SmsContentObserver.TAG, errorString);
                        }
                    }
                }
            }
            SmsContentObserver.this.previousMessage = currentMessage;
        }

        private String revertMailboxType(int smsMailboxType) {
            switch (smsMailboxType) {
                case 1:
                    return Mailbox.INBOX;
                case 2:
                    return Mailbox.SENT;
                case 3:
                    return Mailbox.DRAFT;
                case 4:
                    return Mailbox.OUTBOX;
                default:
                    return Mailbox.DELETED;
            }
        }
    }

    public SmsContentObserver(SmsController smsController) {
        super(new Handler());
        this.mSmsController = smsController;
        this.previousMessage = new HashMap();
        new DatabaseMonitor(0).start();
    }

    public void onChange(boolean onSelf) {
        super.onChange(onSelf);
        Log.i(TAG, "DataBase State Changed");
        new DatabaseMonitor(1).start();
    }
}
