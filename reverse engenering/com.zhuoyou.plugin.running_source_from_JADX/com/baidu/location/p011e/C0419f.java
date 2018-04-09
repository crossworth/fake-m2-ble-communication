package com.baidu.location.p011e;

import com.baidu.location.p011e.C0417d.C0416c;
import java.util.Iterator;
import org.json.JSONObject;

class C0419f extends Thread {
    final /* synthetic */ C0416c f631a;

    C0419f(C0416c c0416c) {
        this.f631a = c0416c;
    }

    public void run() {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        Exception exception;
        Exception exception2;
        JSONObject jSONObject3;
        Iterator keys;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2;
        StringBuffer stringBuffer3;
        Object obj;
        Object obj2;
        int i;
        int i2;
        String str;
        String string;
        Double valueOf;
        int i3;
        Object obj3;
        int i4;
        int i5;
        int i6;
        Object obj4;
        Object obj5;
        Object obj6;
        int i7;
        super.run();
        if (this.f631a.f603a.f621h == null || this.f631a.f603a.f622i == null || !this.f631a.f603a.f621h.isOpen() || !this.f631a.f603a.f622i.isOpen()) {
            this.f631a.f608f = false;
            return;
        }
        Object obj7;
        int i8;
        Object obj8;
        JSONObject jSONObject4 = null;
        try {
            if (this.f631a.j != null) {
                jSONObject = new JSONObject(this.f631a.j);
                try {
                    jSONObject2 = jSONObject.has("model") ? jSONObject.getJSONObject("model") : null;
                } catch (Exception e) {
                    exception = e;
                    jSONObject2 = null;
                    exception2 = exception;
                    exception2.printStackTrace();
                    this.f631a.f603a.f621h.beginTransaction();
                    this.f631a.f603a.f622i.beginTransaction();
                    if (jSONObject4 != null) {
                        this.f631a.f603a.f614a.m786k().m833a(jSONObject4);
                    }
                    this.f631a.f611r = System.currentTimeMillis();
                    this.f631a.f607e.m691a(jSONObject.getString("bdlist").split(";"));
                    this.f631a.f607e.m689a(jSONObject.getJSONObject("loadurl").getString("host"), jSONObject.getJSONObject("loadurl").getString("module"), jSONObject.getJSONObject("loadurl").getString("req"));
                    jSONObject3 = jSONObject2.getJSONObject("cell");
                    keys = jSONObject3.keys();
                    stringBuffer = new StringBuffer();
                    stringBuffer2 = new StringBuffer();
                    stringBuffer3 = new StringBuffer();
                    obj = 1;
                    obj2 = 1;
                    obj7 = 1;
                    i = 0;
                    i2 = 0;
                    i8 = 0;
                    while (keys.hasNext()) {
                        str = (String) keys.next();
                        string = jSONObject3.getString(str);
                        valueOf = Double.valueOf(string.split(",")[3]);
                        if (obj2 == null) {
                            try {
                                stringBuffer2.append(',');
                            } catch (Exception e2) {
                                this.f631a.m665c();
                                return;
                            } finally {
                                try {
                                    if (this.f631a.f603a.f621h != null && this.f631a.f603a.f621h.isOpen()) {
                                        this.f631a.f603a.f621h.endTransaction();
                                    }
                                    if (this.f631a.f603a.f622i != null && this.f631a.f603a.f622i.isOpen()) {
                                        this.f631a.f603a.f622i.endTransaction();
                                    }
                                } catch (Exception e3) {
                                }
                                this.f631a.j = null;
                                this.f631a.f608f = false;
                            }
                        } else {
                            obj2 = null;
                        }
                        stringBuffer2.append(str);
                        i2++;
                        if (valueOf.doubleValue() <= 0.0d) {
                            if (obj == null) {
                                stringBuffer.append(',');
                            } else {
                                obj = null;
                            }
                            stringBuffer.append(str);
                            i3 = i + 1;
                            obj3 = obj;
                        } else {
                            if (obj7 == null) {
                                stringBuffer3.append(',');
                            } else {
                                obj7 = null;
                            }
                            stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                            i8++;
                            i3 = i;
                            obj3 = obj;
                        }
                        if (i2 >= 100) {
                            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                            obj2 = 1;
                            stringBuffer2.setLength(0);
                            i2 -= 100;
                        }
                        if (i8 >= 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                            obj7 = 1;
                            stringBuffer3.setLength(0);
                            i8 -= 100;
                        }
                        if (i3 < 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                            obj3 = 1;
                            stringBuffer.setLength(0);
                            i3 -= 100;
                        }
                        obj = obj3;
                        i = i3;
                    }
                    if (i2 > 0) {
                        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    }
                    if (i8 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    }
                    if (i > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    }
                    jSONObject3 = jSONObject2.getJSONObject("ap");
                    keys = jSONObject3.keys();
                    i4 = 0;
                    i5 = 0;
                    i6 = 0;
                    obj8 = 1;
                    obj4 = 1;
                    obj3 = 1;
                    stringBuffer = new StringBuffer();
                    stringBuffer2 = new StringBuffer();
                    stringBuffer3 = new StringBuffer();
                    while (keys.hasNext()) {
                        str = (String) keys.next();
                        string = jSONObject3.getString(str);
                        valueOf = Double.valueOf(string.split(",")[3]);
                        if (obj4 == null) {
                            stringBuffer2.append(',');
                        } else {
                            obj4 = null;
                        }
                        stringBuffer2.append(str);
                        i5++;
                        if (valueOf.doubleValue() <= 0.0d) {
                            if (obj8 == null) {
                                stringBuffer.append(',');
                            } else {
                                obj8 = null;
                            }
                            stringBuffer.append(str);
                            obj5 = obj3;
                            i = i6;
                            i6 = i4 + 1;
                            obj6 = obj5;
                        } else {
                            if (obj3 == null) {
                                stringBuffer3.append(',');
                            } else {
                                obj3 = null;
                            }
                            stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                            i3 = i6 + 1;
                            i6 = i4;
                            i7 = i3;
                            obj6 = obj3;
                            i = i7;
                        }
                        if (i5 >= 100) {
                            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                            obj4 = 1;
                            stringBuffer2.setLength(0);
                            i5 -= 100;
                        }
                        if (i >= 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                            obj6 = 1;
                            stringBuffer3.setLength(0);
                            i -= 100;
                        }
                        if (i6 <= 0) {
                            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                        }
                        i4 = i6;
                        i6 = i;
                        obj3 = obj6;
                    }
                    if (i5 > 0) {
                        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    }
                    if (i6 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    }
                    if (i4 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    }
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(200000)}));
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(200000)}));
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(10000)}));
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(10000)}));
                    this.f631a.m665c();
                    this.f631a.f603a.f621h.setTransactionSuccessful();
                    this.f631a.f603a.f622i.setTransactionSuccessful();
                    this.f631a.f603a.f621h.endTransaction();
                    this.f631a.f603a.f622i.endTransaction();
                    this.f631a.j = null;
                    this.f631a.f608f = false;
                }
                try {
                    if (jSONObject.has("rgc")) {
                        jSONObject4 = jSONObject.getJSONObject("rgc");
                    }
                } catch (Exception e4) {
                    exception2 = e4;
                    exception2.printStackTrace();
                    this.f631a.f603a.f621h.beginTransaction();
                    this.f631a.f603a.f622i.beginTransaction();
                    if (jSONObject4 != null) {
                        this.f631a.f603a.f614a.m786k().m833a(jSONObject4);
                    }
                    this.f631a.f611r = System.currentTimeMillis();
                    this.f631a.f607e.m691a(jSONObject.getString("bdlist").split(";"));
                    this.f631a.f607e.m689a(jSONObject.getJSONObject("loadurl").getString("host"), jSONObject.getJSONObject("loadurl").getString("module"), jSONObject.getJSONObject("loadurl").getString("req"));
                    jSONObject3 = jSONObject2.getJSONObject("cell");
                    keys = jSONObject3.keys();
                    stringBuffer = new StringBuffer();
                    stringBuffer2 = new StringBuffer();
                    stringBuffer3 = new StringBuffer();
                    obj = 1;
                    obj2 = 1;
                    obj7 = 1;
                    i = 0;
                    i2 = 0;
                    i8 = 0;
                    while (keys.hasNext()) {
                        str = (String) keys.next();
                        string = jSONObject3.getString(str);
                        valueOf = Double.valueOf(string.split(",")[3]);
                        if (obj2 == null) {
                            stringBuffer2.append(',');
                        } else {
                            obj2 = null;
                        }
                        stringBuffer2.append(str);
                        i2++;
                        if (valueOf.doubleValue() <= 0.0d) {
                            if (obj == null) {
                                stringBuffer.append(',');
                            } else {
                                obj = null;
                            }
                            stringBuffer.append(str);
                            i3 = i + 1;
                            obj3 = obj;
                        } else {
                            if (obj7 == null) {
                                stringBuffer3.append(',');
                            } else {
                                obj7 = null;
                            }
                            stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                            i8++;
                            i3 = i;
                            obj3 = obj;
                        }
                        if (i2 >= 100) {
                            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                            obj2 = 1;
                            stringBuffer2.setLength(0);
                            i2 -= 100;
                        }
                        if (i8 >= 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                            obj7 = 1;
                            stringBuffer3.setLength(0);
                            i8 -= 100;
                        }
                        if (i3 < 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                            obj3 = 1;
                            stringBuffer.setLength(0);
                            i3 -= 100;
                        }
                        obj = obj3;
                        i = i3;
                    }
                    if (i2 > 0) {
                        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    }
                    if (i8 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    }
                    if (i > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    }
                    jSONObject3 = jSONObject2.getJSONObject("ap");
                    keys = jSONObject3.keys();
                    i4 = 0;
                    i5 = 0;
                    i6 = 0;
                    obj8 = 1;
                    obj4 = 1;
                    obj3 = 1;
                    stringBuffer = new StringBuffer();
                    stringBuffer2 = new StringBuffer();
                    stringBuffer3 = new StringBuffer();
                    while (keys.hasNext()) {
                        str = (String) keys.next();
                        string = jSONObject3.getString(str);
                        valueOf = Double.valueOf(string.split(",")[3]);
                        if (obj4 == null) {
                            stringBuffer2.append(',');
                        } else {
                            obj4 = null;
                        }
                        stringBuffer2.append(str);
                        i5++;
                        if (valueOf.doubleValue() <= 0.0d) {
                            if (obj8 == null) {
                                stringBuffer.append(',');
                            } else {
                                obj8 = null;
                            }
                            stringBuffer.append(str);
                            obj5 = obj3;
                            i = i6;
                            i6 = i4 + 1;
                            obj6 = obj5;
                        } else {
                            if (obj3 == null) {
                                stringBuffer3.append(',');
                            } else {
                                obj3 = null;
                            }
                            stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                            i3 = i6 + 1;
                            i6 = i4;
                            i7 = i3;
                            obj6 = obj3;
                            i = i7;
                        }
                        if (i5 >= 100) {
                            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                            obj4 = 1;
                            stringBuffer2.setLength(0);
                            i5 -= 100;
                        }
                        if (i >= 100) {
                            this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                            obj6 = 1;
                            stringBuffer3.setLength(0);
                            i -= 100;
                        }
                        if (i6 <= 0) {
                            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                        }
                        i4 = i6;
                        i6 = i;
                        obj3 = obj6;
                    }
                    if (i5 > 0) {
                        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    }
                    if (i6 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    }
                    if (i4 > 0) {
                        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    }
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(200000)}));
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(200000)}));
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(10000)}));
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(10000)}));
                    this.f631a.m665c();
                    this.f631a.f603a.f621h.setTransactionSuccessful();
                    this.f631a.f603a.f622i.setTransactionSuccessful();
                    this.f631a.f603a.f621h.endTransaction();
                    this.f631a.f603a.f622i.endTransaction();
                    this.f631a.j = null;
                    this.f631a.f608f = false;
                }
            }
            jSONObject = null;
            jSONObject2 = null;
        } catch (Exception e5) {
            exception = e5;
            jSONObject = null;
            jSONObject2 = null;
            exception2 = exception;
            exception2.printStackTrace();
            this.f631a.f603a.f621h.beginTransaction();
            this.f631a.f603a.f622i.beginTransaction();
            if (jSONObject4 != null) {
                this.f631a.f603a.f614a.m786k().m833a(jSONObject4);
            }
            this.f631a.f611r = System.currentTimeMillis();
            this.f631a.f607e.m691a(jSONObject.getString("bdlist").split(";"));
            this.f631a.f607e.m689a(jSONObject.getJSONObject("loadurl").getString("host"), jSONObject.getJSONObject("loadurl").getString("module"), jSONObject.getJSONObject("loadurl").getString("req"));
            jSONObject3 = jSONObject2.getJSONObject("cell");
            keys = jSONObject3.keys();
            stringBuffer = new StringBuffer();
            stringBuffer2 = new StringBuffer();
            stringBuffer3 = new StringBuffer();
            obj = 1;
            obj2 = 1;
            obj7 = 1;
            i = 0;
            i2 = 0;
            i8 = 0;
            while (keys.hasNext()) {
                str = (String) keys.next();
                string = jSONObject3.getString(str);
                valueOf = Double.valueOf(string.split(",")[3]);
                if (obj2 == null) {
                    obj2 = null;
                } else {
                    stringBuffer2.append(',');
                }
                stringBuffer2.append(str);
                i2++;
                if (valueOf.doubleValue() <= 0.0d) {
                    if (obj7 == null) {
                        obj7 = null;
                    } else {
                        stringBuffer3.append(',');
                    }
                    stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                    i8++;
                    i3 = i;
                    obj3 = obj;
                } else {
                    if (obj == null) {
                        obj = null;
                    } else {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(str);
                    i3 = i + 1;
                    obj3 = obj;
                }
                if (i2 >= 100) {
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    obj2 = 1;
                    stringBuffer2.setLength(0);
                    i2 -= 100;
                }
                if (i8 >= 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    obj7 = 1;
                    stringBuffer3.setLength(0);
                    i8 -= 100;
                }
                if (i3 < 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    obj3 = 1;
                    stringBuffer.setLength(0);
                    i3 -= 100;
                }
                obj = obj3;
                i = i3;
            }
            if (i2 > 0) {
                this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
            }
            if (i8 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
            }
            if (i > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
            }
            jSONObject3 = jSONObject2.getJSONObject("ap");
            keys = jSONObject3.keys();
            i4 = 0;
            i5 = 0;
            i6 = 0;
            obj8 = 1;
            obj4 = 1;
            obj3 = 1;
            stringBuffer = new StringBuffer();
            stringBuffer2 = new StringBuffer();
            stringBuffer3 = new StringBuffer();
            while (keys.hasNext()) {
                str = (String) keys.next();
                string = jSONObject3.getString(str);
                valueOf = Double.valueOf(string.split(",")[3]);
                if (obj4 == null) {
                    obj4 = null;
                } else {
                    stringBuffer2.append(',');
                }
                stringBuffer2.append(str);
                i5++;
                if (valueOf.doubleValue() <= 0.0d) {
                    if (obj3 == null) {
                        obj3 = null;
                    } else {
                        stringBuffer3.append(',');
                    }
                    stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                    i3 = i6 + 1;
                    i6 = i4;
                    i7 = i3;
                    obj6 = obj3;
                    i = i7;
                } else {
                    if (obj8 == null) {
                        obj8 = null;
                    } else {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(str);
                    obj5 = obj3;
                    i = i6;
                    i6 = i4 + 1;
                    obj6 = obj5;
                }
                if (i5 >= 100) {
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    obj4 = 1;
                    stringBuffer2.setLength(0);
                    i5 -= 100;
                }
                if (i >= 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    obj6 = 1;
                    stringBuffer3.setLength(0);
                    i -= 100;
                }
                if (i6 <= 0) {
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                }
                i4 = i6;
                i6 = i;
                obj3 = obj6;
            }
            if (i5 > 0) {
                this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
            }
            if (i6 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
            }
            if (i4 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
            }
            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(200000)}));
            this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(200000)}));
            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(10000)}));
            this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(10000)}));
            this.f631a.m665c();
            this.f631a.f603a.f621h.setTransactionSuccessful();
            this.f631a.f603a.f622i.setTransactionSuccessful();
            this.f631a.f603a.f621h.endTransaction();
            this.f631a.f603a.f622i.endTransaction();
            this.f631a.j = null;
            this.f631a.f608f = false;
        }
        this.f631a.f603a.f621h.beginTransaction();
        this.f631a.f603a.f622i.beginTransaction();
        if (jSONObject4 != null) {
            this.f631a.f603a.f614a.m786k().m833a(jSONObject4);
        }
        if (jSONObject != null && jSONObject.has("type") && jSONObject.getString("type").equals("0")) {
            this.f631a.f611r = System.currentTimeMillis();
        }
        if (jSONObject != null && jSONObject.has("bdlist")) {
            this.f631a.f607e.m691a(jSONObject.getString("bdlist").split(";"));
        }
        if (jSONObject != null && jSONObject.has("loadurl")) {
            this.f631a.f607e.m689a(jSONObject.getJSONObject("loadurl").getString("host"), jSONObject.getJSONObject("loadurl").getString("module"), jSONObject.getJSONObject("loadurl").getString("req"));
        }
        if (jSONObject2 != null && jSONObject2.has("cell")) {
            jSONObject3 = jSONObject2.getJSONObject("cell");
            keys = jSONObject3.keys();
            stringBuffer = new StringBuffer();
            stringBuffer2 = new StringBuffer();
            stringBuffer3 = new StringBuffer();
            obj = 1;
            obj2 = 1;
            obj7 = 1;
            i = 0;
            i2 = 0;
            i8 = 0;
            while (keys.hasNext()) {
                str = (String) keys.next();
                string = jSONObject3.getString(str);
                valueOf = Double.valueOf(string.split(",")[3]);
                if (obj2 == null) {
                    obj2 = null;
                } else {
                    stringBuffer2.append(',');
                }
                stringBuffer2.append(str);
                i2++;
                if (valueOf.doubleValue() <= 0.0d) {
                    if (obj7 == null) {
                        obj7 = null;
                    } else {
                        stringBuffer3.append(',');
                    }
                    stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                    i8++;
                    i3 = i;
                    obj3 = obj;
                } else {
                    if (obj == null) {
                        obj = null;
                    } else {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(str);
                    i3 = i + 1;
                    obj3 = obj;
                }
                if (i2 >= 100) {
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    obj2 = 1;
                    stringBuffer2.setLength(0);
                    i2 -= 100;
                }
                if (i8 >= 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    obj7 = 1;
                    stringBuffer3.setLength(0);
                    i8 -= 100;
                }
                if (i3 < 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                    obj3 = 1;
                    stringBuffer.setLength(0);
                    i3 -= 100;
                }
                obj = obj3;
                i = i3;
            }
            if (i2 > 0) {
                this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
            }
            if (i8 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO CL (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
            }
            if (i > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM CL WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
            }
        }
        if (jSONObject2 != null && jSONObject2.has("ap")) {
            jSONObject3 = jSONObject2.getJSONObject("ap");
            keys = jSONObject3.keys();
            i4 = 0;
            i5 = 0;
            i6 = 0;
            obj8 = 1;
            obj4 = 1;
            obj3 = 1;
            stringBuffer = new StringBuffer();
            stringBuffer2 = new StringBuffer();
            stringBuffer3 = new StringBuffer();
            while (keys.hasNext()) {
                str = (String) keys.next();
                string = jSONObject3.getString(str);
                valueOf = Double.valueOf(string.split(",")[3]);
                if (obj4 == null) {
                    obj4 = null;
                } else {
                    stringBuffer2.append(',');
                }
                stringBuffer2.append(str);
                i5++;
                if (valueOf.doubleValue() <= 0.0d) {
                    if (obj3 == null) {
                        obj3 = null;
                    } else {
                        stringBuffer3.append(',');
                    }
                    stringBuffer3.append('(').append(str).append(',').append(string).append("," + (System.currentTimeMillis() / 1000)).append(')');
                    i3 = i6 + 1;
                    i6 = i4;
                    i7 = i3;
                    obj6 = obj3;
                    i = i7;
                } else {
                    if (obj8 == null) {
                        obj8 = null;
                    } else {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(str);
                    obj5 = obj3;
                    i = i6;
                    i6 = i4 + 1;
                    obj6 = obj5;
                }
                if (i5 >= 100) {
                    this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
                    obj4 = 1;
                    stringBuffer2.setLength(0);
                    i5 -= 100;
                }
                if (i >= 100) {
                    this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
                    obj6 = 1;
                    stringBuffer3.setLength(0);
                    i -= 100;
                }
                if (i6 <= 0) {
                    this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
                }
                i4 = i6;
                i6 = i;
                obj3 = obj6;
            }
            if (i5 > 0) {
                this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer2.toString()}));
            }
            if (i6 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("INSERT OR REPLACE INTO AP (id,x,y,r,cl,timestamp) VALUES %s;", new Object[]{stringBuffer3.toString()}));
            }
            if (i4 > 0) {
                this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM AP WHERE id IN (%s);", new Object[]{stringBuffer.toString()}));
            }
        }
        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(200000)}));
        this.f631a.f603a.f621h.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY timestamp DESC, frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(200000)}));
        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"AP", "AP", Integer.valueOf(10000)}));
        this.f631a.f603a.f622i.execSQL(String.format("DELETE FROM %s WHERE id NOT IN (SELECT id FROM %s ORDER BY frequency DESC LIMIT %d);", new Object[]{"CL", "CL", Integer.valueOf(10000)}));
        if (!(jSONObject2 == null || jSONObject2.has("ap") || jSONObject2.has("cell"))) {
            this.f631a.m665c();
        }
        this.f631a.f603a.f621h.setTransactionSuccessful();
        this.f631a.f603a.f622i.setTransactionSuccessful();
        try {
            if (this.f631a.f603a.f621h != null && this.f631a.f603a.f621h.isOpen()) {
                this.f631a.f603a.f621h.endTransaction();
            }
            if (this.f631a.f603a.f622i != null && this.f631a.f603a.f622i.isOpen()) {
                this.f631a.f603a.f622i.endTransaction();
            }
        } catch (Exception e6) {
        }
        this.f631a.j = null;
        this.f631a.f608f = false;
    }
}
