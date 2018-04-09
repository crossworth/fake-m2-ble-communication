package com.zhuoyou.plugin.selfupdate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestAsyncTask extends AsyncTask<Object, Object, String> {
    public static String ENCODE_DECODE_KEY = "x_s0_s22";
    public static final int SelfUpdateMsgCode = 103001;
    public static final String SelfUpdateUrl = "http://update-erunning.yy845.com:2520";
    public static String mAppId = "";
    public static String mChId = "";
    private Context mContext;
    private Handler mHandler;
    private int mMessageCode = 0;
    private int mMsgWhat;
    private boolean mStarting = false;

    public RequestAsyncTask(Context context, Handler handler, int msgWhat, String appid, String chnid) {
        this.mContext = context;
        this.mHandler = handler;
        this.mMsgWhat = msgWhat;
        mAppId = appid;
        mChId = chnid;
    }

    protected String doInBackground(Object... params) {
        String result = "";
        String url = "";
        String contents = "";
        url = params[0];
        this.mMessageCode = ((Integer) params[1]).intValue();
        contents = buildToJSONData(this.mContext, this.mMessageCode);
        Log.i("gchk", contents);
        Log.i("hph", "contents=" + contents);
        try {
            result = accessNetworkByPost(url, contents);
            Log.i("hph", " version result=" + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }

    protected void onCancelled() {
        this.mStarting = false;
        super.onCancelled();
    }

    protected void onPostExecute(String result) {
        Log.i("gchk", "onPostExecute = " + result);
        HashMap<String, Object> map = null;
        int msgCode = -1;
        if (!TextUtils.isEmpty(result)) {
            map = new ApkCheckSelfUpdateCodec().splitMySelfData(result);
            if (map == null || map.size() == 0) {
                msgCode = -1;
            } else if (map.containsKey("errorCode")) {
                msgCode = Integer.valueOf(map.get("errorCode").toString()).intValue();
            }
        }
        try {
            Message msg = new Message();
            msg.what = this.mMsgWhat;
            msg.arg1 = msgCode;
            msg.obj = map;
            this.mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }

    public void startRun() {
        if (!this.mStarting) {
            this.mStarting = true;
            execute(new Object[]{SelfUpdateUrl, Integer.valueOf(103001)});
        }
    }

    private String buildToJSONData(Context context, int msgCode) {
        String result = "";
        String body = "";
        JSONObject jsObject = new JSONObject();
        if (context == null) {
            return result;
        }
        body = TerminalInfo.generateTerminalInfo(context, mAppId, mChId).toString();
        try {
            jsObject.put("head", buildHeadData(msgCode));
            jsObject.put("body", body);
            result = jsObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildHeadData(int msgCode) {
        String result = "";
        UUID uuid = UUID.randomUUID();
        Header header = new Header();
        header.setBasicVer((byte) 1);
        header.setLength(84);
        header.setType((byte) 1);
        header.setReserved((short) 0);
        header.setFirstTransaction(uuid.getMostSignificantBits());
        header.setSecondTransaction(uuid.getLeastSignificantBits());
        header.setMessageCode(msgCode);
        return header.toString();
    }

    private String accessNetworkByPost(String urlString, String contents) throws IOException {
        BufferedInputStream bis;
        Exception e;
        DataOutputStream dataOutputStream;
        Throwable th;
        String line = "";
        BufferedInputStream bis2 = null;
        ByteArrayBuffer baf = null;
        HttpURLConnection connection = null;
        try {
            byte[] encrypted = DESUtil.encrypt(contents.getBytes("utf-8"), ENCODE_DECODE_KEY.getBytes());
            connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(20000);
            connection.setRequestMethod("POST");
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("contentType", "utf-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + encrypted.length);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            try {
                out.write(encrypted);
                out.flush();
                out.close();
                bis = new BufferedInputStream(connection.getInputStream());
            } catch (Exception e2) {
                e = e2;
                dataOutputStream = out;
                try {
                    e.printStackTrace();
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis2 != null) {
                        bis2.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    return line.trim();
                } catch (Throwable th2) {
                    th = th2;
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis2 != null) {
                        bis2.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = out;
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis2 != null) {
                    bis2.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                throw th;
            }
            try {
                ByteArrayBuffer baf2 = new ByteArrayBuffer(1024);
                try {
                    boolean isPress = Boolean.valueOf(connection.getHeaderField("isPress")).booleanValue();
                    while (true) {
                        int current = bis.read();
                        if (current == -1) {
                            break;
                        }
                        baf2.append((byte) current);
                    }
                    if (baf2.length() > 0) {
                        byte[] decrypted;
                        if (isPress) {
                            Log.e("shuaiqingDe@@@@", "compress length:" + baf2.length());
                            byte[] unCompressByte = ZipUtil.uncompress(baf2.toByteArray());
                            Log.e("shuaiqingDe@@@@", "length:" + unCompressByte.length);
                            decrypted = DESUtil.decrypt(unCompressByte, ENCODE_DECODE_KEY.getBytes());
                        } else {
                            decrypted = DESUtil.decrypt(baf2.toByteArray(), ENCODE_DECODE_KEY.getBytes());
                        }
                        line = new String(decrypted);
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (baf2 != null) {
                        baf2.clear();
                        baf = baf2;
                        bis2 = bis;
                        dataOutputStream = out;
                    } else {
                        bis2 = bis;
                        dataOutputStream = out;
                    }
                } catch (Exception e3) {
                    e = e3;
                    baf = baf2;
                    bis2 = bis;
                    dataOutputStream = out;
                    e.printStackTrace();
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis2 != null) {
                        bis2.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    return line.trim();
                } catch (Throwable th4) {
                    th = th4;
                    baf = baf2;
                    bis2 = bis;
                    dataOutputStream = out;
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (bis2 != null) {
                        bis2.close();
                    }
                    if (baf != null) {
                        baf.clear();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                bis2 = bis;
                dataOutputStream = out;
                e.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis2 != null) {
                    bis2.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                return line.trim();
            } catch (Throwable th5) {
                th = th5;
                bis2 = bis;
                dataOutputStream = out;
                if (connection != null) {
                    connection.disconnect();
                }
                if (bis2 != null) {
                    bis2.close();
                }
                if (baf != null) {
                    baf.clear();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (connection != null) {
                connection.disconnect();
            }
            if (bis2 != null) {
                bis2.close();
            }
            if (baf != null) {
                baf.clear();
            }
            return line.trim();
        }
        return line.trim();
    }
}
