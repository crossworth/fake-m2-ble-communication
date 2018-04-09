package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Looper;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: AnrLogProcessor */
public class ef extends ei {
    private static boolean f4189a = true;
    private String[] f4190b = new String[10];
    private int f4191c = 0;
    private boolean f4192d = false;
    private int f4193e = 0;

    protected ef(int i) {
        super(i);
    }

    protected boolean mo1647a(Context context) {
        if (dq.m656m(context) != 1 || !f4189a) {
            return false;
        }
        f4189a = false;
        synchronized (Looper.getMainLooper()) {
            ev evVar = new ev(context);
            ew a = evVar.m832a();
            if (a == null) {
                return true;
            } else if (a.m839c()) {
                a.m838c(false);
                evVar.m833a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1646a(List<dv> list) {
        InputStream fileInputStream;
        fo foVar;
        InputStream inputStream;
        Throwable e;
        String str;
        String str2;
        InputStream inputStream2 = null;
        fo foVar2 = null;
        try {
            File file = new File("/data/anr/traces.txt");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    foVar2 = new fo(fileInputStream, fp.f650a);
                    Object obj = null;
                    while (true) {
                        try {
                            String str3;
                            Object obj2;
                            String a = foVar2.m945a();
                            if (a.contains("pid")) {
                                while (!a.contains("\"main\"")) {
                                    a = foVar2.m945a();
                                }
                                str3 = a;
                                obj2 = 1;
                            } else {
                                str3 = a;
                                obj2 = obj;
                            }
                            if (str3.equals("")) {
                                obj = null;
                            } else {
                                obj = obj2;
                            }
                            if (obj != null) {
                                m4250b(str3);
                                if (this.f4193e == 5) {
                                    break;
                                } else if (this.f4192d) {
                                    this.f4193e++;
                                } else {
                                    for (dv dvVar : list) {
                                        this.f4192d = ei.m760a(dvVar.m711e(), str3);
                                        if (this.f4192d) {
                                            m777a(dvVar);
                                        }
                                    }
                                }
                            }
                        } catch (EOFException e2) {
                        } catch (FileNotFoundException e3) {
                            foVar = foVar2;
                            inputStream = fileInputStream;
                        } catch (IOException e4) {
                            e = e4;
                        }
                    }
                    if (foVar2 != null) {
                        try {
                            foVar2.close();
                        } catch (Throwable e5) {
                            eb.m742a(e5, "ANRWriter", "initLog1");
                        } catch (Throwable e52) {
                            eb.m742a(e52, "ANRWriter", "initLog2");
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e52 = e6;
                            str = "ANRWriter";
                            str2 = "initLog3";
                            eb.m742a(e52, str, str2);
                            if (this.f4192d) {
                                return null;
                            }
                            return m4251d();
                        } catch (Throwable th) {
                            e52 = th;
                            str = "ANRWriter";
                            str2 = "initLog4";
                            eb.m742a(e52, str, str2);
                            if (this.f4192d) {
                                return m4251d();
                            }
                            return null;
                        }
                    }
                } catch (FileNotFoundException e7) {
                    foVar = null;
                    inputStream = fileInputStream;
                    if (foVar != null) {
                        try {
                            foVar.close();
                        } catch (Throwable e522) {
                            eb.m742a(e522, "ANRWriter", "initLog1");
                        } catch (Throwable e5222) {
                            eb.m742a(e5222, "ANRWriter", "initLog2");
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e8) {
                            e5222 = e8;
                            str = "ANRWriter";
                            str2 = "initLog3";
                            eb.m742a(e5222, str, str2);
                            if (this.f4192d) {
                                return null;
                            }
                            return m4251d();
                        } catch (Throwable th2) {
                            e5222 = th2;
                            str = "ANRWriter";
                            str2 = "initLog4";
                            eb.m742a(e5222, str, str2);
                            if (this.f4192d) {
                                return m4251d();
                            }
                            return null;
                        }
                    }
                    if (this.f4192d) {
                        return null;
                    }
                    return m4251d();
                } catch (IOException e9) {
                    e5222 = e9;
                    foVar2 = null;
                    try {
                        eb.m742a(e5222, "ANRWriter", "initLog");
                        if (foVar2 != null) {
                            try {
                                foVar2.close();
                            } catch (Throwable e52222) {
                                eb.m742a(e52222, "ANRWriter", "initLog1");
                            } catch (Throwable e522222) {
                                eb.m742a(e522222, "ANRWriter", "initLog2");
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e10) {
                                e522222 = e10;
                                str = "ANRWriter";
                                str2 = "initLog3";
                                eb.m742a(e522222, str, str2);
                                if (this.f4192d) {
                                    return null;
                                }
                                return m4251d();
                            } catch (Throwable th3) {
                                e522222 = th3;
                                str = "ANRWriter";
                                str2 = "initLog4";
                                eb.m742a(e522222, str, str2);
                                if (this.f4192d) {
                                    return m4251d();
                                }
                                return null;
                            }
                        }
                        if (this.f4192d) {
                            return m4251d();
                        }
                        return null;
                    } catch (Throwable th4) {
                        e522222 = th4;
                        if (foVar2 != null) {
                            try {
                                foVar2.close();
                            } catch (Throwable e11) {
                                eb.m742a(e11, "ANRWriter", "initLog1");
                            } catch (Throwable e112) {
                                eb.m742a(e112, "ANRWriter", "initLog2");
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable e1122) {
                                eb.m742a(e1122, "ANRWriter", "initLog3");
                            } catch (Throwable e11222) {
                                eb.m742a(e11222, "ANRWriter", "initLog4");
                            }
                        }
                        throw e522222;
                    }
                } catch (Throwable th5) {
                    e522222 = th5;
                    foVar2 = null;
                    if (foVar2 != null) {
                        foVar2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw e522222;
                }
                if (this.f4192d) {
                    return m4251d();
                }
                return null;
            }
            if (null != null) {
                try {
                    foVar2.close();
                } catch (Throwable e12) {
                    eb.m742a(e12, "ANRWriter", "initLog1");
                } catch (Throwable e122) {
                    eb.m742a(e122, "ANRWriter", "initLog2");
                }
            }
            if (null != null) {
                try {
                    inputStream2.close();
                } catch (Throwable e5222222) {
                    eb.m742a(e5222222, "ANRWriter", "initLog3");
                } catch (Throwable e52222222) {
                    eb.m742a(e52222222, "ANRWriter", "initLog4");
                }
            }
            return null;
        } catch (FileNotFoundException e13) {
            foVar = null;
            inputStream = null;
            if (foVar != null) {
                foVar.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (this.f4192d) {
                return null;
            }
            return m4251d();
        } catch (IOException e14) {
            e52222222 = e14;
            foVar2 = null;
            fileInputStream = null;
            eb.m742a(e52222222, "ANRWriter", "initLog");
            if (foVar2 != null) {
                foVar2.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (this.f4192d) {
                return m4251d();
            }
            return null;
        } catch (Throwable th6) {
            e52222222 = th6;
            foVar2 = null;
            fileInputStream = null;
            if (foVar2 != null) {
                foVar2.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw e52222222;
        }
    }

    private String m4251d() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int i = this.f4191c;
            while (i < 10 && i <= 9) {
                stringBuilder.append(this.f4190b[i]);
                i++;
            }
            for (i = 0; i < this.f4191c; i++) {
                stringBuilder.append(this.f4190b[i]);
            }
        } catch (Throwable th) {
            eb.m742a(th, "ANRWriter", "getLogInfo");
        }
        return stringBuilder.toString();
    }

    private void m4250b(String str) {
        try {
            if (this.f4191c > 9) {
                this.f4191c = 0;
            }
            this.f4190b[this.f4191c] = str;
            this.f4191c++;
        } catch (Throwable th) {
            eb.m742a(th, "ANRWriter", "addData");
        }
    }
}
