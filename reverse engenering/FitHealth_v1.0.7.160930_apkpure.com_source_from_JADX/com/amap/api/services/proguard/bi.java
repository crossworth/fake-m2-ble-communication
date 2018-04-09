package com.amap.api.services.proguard;

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
public class bi extends bl {
    private static boolean f4352a = true;
    private String[] f4353b = new String[10];
    private int f4354c = 0;
    private boolean f4355d = false;
    private int f4356e = 0;

    protected bi(int i) {
        super(i);
    }

    protected boolean mo1764a(Context context) {
        if (aw.m1257m(context) != 1 || !f4352a) {
            return false;
        }
        f4352a = false;
        synchronized (Looper.getMainLooper()) {
            by byVar = new by(context);
            bz a = byVar.m1429a();
            if (a == null) {
                return true;
            } else if (a.m1436c()) {
                a.m1435c(false);
                byVar.m1430a(a);
                return true;
            } else {
                return false;
            }
        }
    }

    protected String mo1763a(List<ba> list) {
        InputStream fileInputStream;
        co coVar;
        InputStream inputStream;
        Throwable e;
        String str;
        String str2;
        InputStream inputStream2 = null;
        co coVar2 = null;
        try {
            File file = new File("/data/anr/traces.txt");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    coVar2 = new co(fileInputStream, cp.f1511a);
                    Object obj = null;
                    while (true) {
                        try {
                            String str3;
                            Object obj2;
                            String a = coVar2.m1542a();
                            if (a.contains("pid")) {
                                while (!a.contains("\"main\"")) {
                                    a = coVar2.m1542a();
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
                                m4443b(str3);
                                if (this.f4356e == 5) {
                                    break;
                                } else if (this.f4355d) {
                                    this.f4356e++;
                                } else {
                                    for (ba baVar : list) {
                                        this.f4355d = bl.m1358a(baVar.m1311d(), str3);
                                        if (this.f4355d) {
                                            m1375a(baVar);
                                        }
                                    }
                                }
                            }
                        } catch (EOFException e2) {
                        } catch (FileNotFoundException e3) {
                            coVar = coVar2;
                            inputStream = fileInputStream;
                        } catch (IOException e4) {
                            e = e4;
                        }
                    }
                    if (coVar2 != null) {
                        try {
                            coVar2.close();
                        } catch (Throwable e5) {
                            be.m1340a(e5, "ANRWriter", "initLog1");
                        } catch (Throwable e52) {
                            be.m1340a(e52, "ANRWriter", "initLog2");
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                            e52 = e6;
                            str = "ANRWriter";
                            str2 = "initLog3";
                            be.m1340a(e52, str, str2);
                            if (this.f4355d) {
                                return null;
                            }
                            return m4444d();
                        } catch (Throwable th) {
                            e52 = th;
                            str = "ANRWriter";
                            str2 = "initLog4";
                            be.m1340a(e52, str, str2);
                            if (this.f4355d) {
                                return m4444d();
                            }
                            return null;
                        }
                    }
                } catch (FileNotFoundException e7) {
                    coVar = null;
                    inputStream = fileInputStream;
                    if (coVar != null) {
                        try {
                            coVar.close();
                        } catch (Throwable e522) {
                            be.m1340a(e522, "ANRWriter", "initLog1");
                        } catch (Throwable e5222) {
                            be.m1340a(e5222, "ANRWriter", "initLog2");
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e8) {
                            e5222 = e8;
                            str = "ANRWriter";
                            str2 = "initLog3";
                            be.m1340a(e5222, str, str2);
                            if (this.f4355d) {
                                return null;
                            }
                            return m4444d();
                        } catch (Throwable th2) {
                            e5222 = th2;
                            str = "ANRWriter";
                            str2 = "initLog4";
                            be.m1340a(e5222, str, str2);
                            if (this.f4355d) {
                                return m4444d();
                            }
                            return null;
                        }
                    }
                    if (this.f4355d) {
                        return null;
                    }
                    return m4444d();
                } catch (IOException e9) {
                    e5222 = e9;
                    coVar2 = null;
                    try {
                        be.m1340a(e5222, "ANRWriter", "initLog");
                        if (coVar2 != null) {
                            try {
                                coVar2.close();
                            } catch (Throwable e52222) {
                                be.m1340a(e52222, "ANRWriter", "initLog1");
                            } catch (Throwable e522222) {
                                be.m1340a(e522222, "ANRWriter", "initLog2");
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e10) {
                                e522222 = e10;
                                str = "ANRWriter";
                                str2 = "initLog3";
                                be.m1340a(e522222, str, str2);
                                if (this.f4355d) {
                                    return null;
                                }
                                return m4444d();
                            } catch (Throwable th3) {
                                e522222 = th3;
                                str = "ANRWriter";
                                str2 = "initLog4";
                                be.m1340a(e522222, str, str2);
                                if (this.f4355d) {
                                    return m4444d();
                                }
                                return null;
                            }
                        }
                        if (this.f4355d) {
                            return m4444d();
                        }
                        return null;
                    } catch (Throwable th4) {
                        e522222 = th4;
                        if (coVar2 != null) {
                            try {
                                coVar2.close();
                            } catch (Throwable e11) {
                                be.m1340a(e11, "ANRWriter", "initLog1");
                            } catch (Throwable e112) {
                                be.m1340a(e112, "ANRWriter", "initLog2");
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable e1122) {
                                be.m1340a(e1122, "ANRWriter", "initLog3");
                            } catch (Throwable e11222) {
                                be.m1340a(e11222, "ANRWriter", "initLog4");
                            }
                        }
                        throw e522222;
                    }
                } catch (Throwable th5) {
                    e522222 = th5;
                    coVar2 = null;
                    if (coVar2 != null) {
                        coVar2.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw e522222;
                }
                if (this.f4355d) {
                    return m4444d();
                }
                return null;
            }
            if (null != null) {
                try {
                    coVar2.close();
                } catch (Throwable e12) {
                    be.m1340a(e12, "ANRWriter", "initLog1");
                } catch (Throwable e122) {
                    be.m1340a(e122, "ANRWriter", "initLog2");
                }
            }
            if (null != null) {
                try {
                    inputStream2.close();
                } catch (Throwable e5222222) {
                    be.m1340a(e5222222, "ANRWriter", "initLog3");
                } catch (Throwable e52222222) {
                    be.m1340a(e52222222, "ANRWriter", "initLog4");
                }
            }
            return null;
        } catch (FileNotFoundException e13) {
            coVar = null;
            inputStream = null;
            if (coVar != null) {
                coVar.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (this.f4355d) {
                return null;
            }
            return m4444d();
        } catch (IOException e14) {
            e52222222 = e14;
            coVar2 = null;
            fileInputStream = null;
            be.m1340a(e52222222, "ANRWriter", "initLog");
            if (coVar2 != null) {
                coVar2.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (this.f4355d) {
                return m4444d();
            }
            return null;
        } catch (Throwable th6) {
            e52222222 = th6;
            coVar2 = null;
            fileInputStream = null;
            if (coVar2 != null) {
                coVar2.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw e52222222;
        }
    }

    private String m4444d() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int i = this.f4354c;
            while (i < 10 && i <= 9) {
                stringBuilder.append(this.f4353b[i]);
                i++;
            }
            for (i = 0; i < this.f4354c; i++) {
                stringBuilder.append(this.f4353b[i]);
            }
        } catch (Throwable th) {
            be.m1340a(th, "ANRWriter", "getLogInfo");
        }
        return stringBuilder.toString();
    }

    private void m4443b(String str) {
        try {
            if (this.f4354c > 9) {
                this.f4354c = 0;
            }
            this.f4353b[this.f4354c] = str;
            this.f4354c++;
        } catch (Throwable th) {
            be.m1340a(th, "ANRWriter", "addData");
        }
    }
}
