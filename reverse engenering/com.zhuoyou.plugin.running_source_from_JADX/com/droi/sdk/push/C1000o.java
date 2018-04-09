package com.droi.sdk.push;

import android.content.Context;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import com.droi.sdk.internal.DroiLog;
import com.droi.sdk.push.utils.C1016k;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class C1000o extends Thread {
    LocalServerSocket f3316a = null;
    volatile boolean f3317b = false;
    final /* synthetic */ DroiPushService f3318c;

    public C1000o(DroiPushService droiPushService, Context context) {
        this.f3318c = droiPushService;
    }

    public void run() {
        BufferedReader bufferedReader;
        Exception exception;
        Exception exception2;
        Throwable th;
        Throwable th2;
        Object obj;
        BufferedWriter bufferedWriter = null;
        try {
            this.f3316a = new LocalServerSocket("com.droi.server.UNIX_DOMAIN_SOCKET_NAME");
            BufferedWriter bufferedWriter2 = null;
            while (!this.f3317b) {
                try {
                    BufferedWriter bufferedWriter3;
                    LocalSocket accept = this.f3316a.accept();
                    if (accept != null) {
                        bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream(), "UTF-8"));
                        try {
                            bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream(), "UTF-8"));
                            try {
                                String readLine = bufferedReader.readLine();
                                String readLine2 = bufferedReader.readLine();
                                bufferedWriter2.write(this.f3318c.f3199o.f3360a + "\n");
                                bufferedWriter2.write(this.f3318c.f3199o.f3361b + "\n");
                                bufferedWriter2.flush();
                                if (new C1016k(readLine, readLine2).m3174a(this.f3318c.f3199o) == 1) {
                                    this.f3318c.stopSelf();
                                    bufferedWriter = bufferedWriter2;
                                    break;
                                }
                                Object obj2 = bufferedReader;
                            } catch (Exception e) {
                                exception = e;
                                bufferedWriter = bufferedWriter2;
                                exception2 = exception;
                            } catch (Throwable th3) {
                                th = th3;
                                bufferedWriter = bufferedWriter2;
                                th2 = th;
                            }
                        } catch (Exception e2) {
                            exception2 = e2;
                        }
                    } else {
                        bufferedWriter3 = bufferedWriter;
                        bufferedWriter = bufferedWriter2;
                        bufferedWriter2 = bufferedWriter3;
                    }
                    bufferedWriter3 = bufferedWriter2;
                    bufferedWriter2 = bufferedWriter;
                    bufferedWriter = bufferedWriter3;
                } catch (Exception e3) {
                    exception = e3;
                    obj = bufferedWriter2;
                    exception2 = exception;
                } catch (Throwable th4) {
                    th = th4;
                    obj = bufferedWriter2;
                    th2 = th;
                }
            }
            obj = bufferedWriter2;
            this.f3316a.close();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e5) {
                }
            }
        } catch (Exception e6) {
            exception2 = e6;
            bufferedReader = null;
            try {
                DroiLog.m2869e(getClass().getName(), exception2);
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e7) {
                    }
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e8) {
                    }
                }
            } catch (Throwable th5) {
                th2 = th5;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e9) {
                    }
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e10) {
                    }
                }
                throw th2;
            }
        } catch (Throwable th6) {
            th2 = th6;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            throw th2;
        }
    }
}
