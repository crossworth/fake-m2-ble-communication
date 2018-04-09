package com.aps;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.services.district.DistrictSearchQuery;
import com.baidu.location.p000a.C0495a;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.tencent.open.SocialConstants;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: Parser */
public class C0460m {

    /* compiled from: Parser */
    private static class C0458a extends DefaultHandler {
        public C0442c f1900a;
        private String f1901b;

        private C0458a() {
            this.f1900a = new C0442c();
            this.f1901b = "";
        }

        public void characters(char[] cArr, int i, int i2) {
            this.f1901b = String.valueOf(cArr, i, i2);
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) {
            this.f1901b = "";
        }

        public void endElement(String str, String str2, String str3) {
            if (str2.equals("retype")) {
                this.f1900a.m1867e(this.f1901b);
            } else if (str2.equals("adcode")) {
                this.f1900a.m1873h(this.f1901b);
            } else if (str2.equals("citycode")) {
                this.f1900a.m1869f(this.f1901b);
            } else if (str2.equals(C0495a.f2122char)) {
                try {
                    this.f1900a.m1854a(Float.valueOf(this.f1901b).floatValue());
                } catch (Throwable th) {
                    th.printStackTrace();
                    C0470t.m2008a(th);
                    this.f1900a.m1854a(3891.0f);
                }
            } else if (str2.equals("cenx")) {
                try {
                    this.f1901b = C0466q.m1992a(Double.valueOf(this.f1901b), "#.000000");
                    this.f1900a.m1853a(Double.valueOf(this.f1901b).doubleValue());
                } catch (Throwable th2) {
                    th2.printStackTrace();
                    C0470t.m2008a(th2);
                    this.f1900a.m1853a(0.0d);
                }
            } else if (str2.equals("ceny")) {
                try {
                    this.f1901b = C0466q.m1992a(Double.valueOf(this.f1901b), "#.000000");
                    this.f1900a.m1860b(Double.valueOf(this.f1901b).doubleValue());
                } catch (Throwable th22) {
                    th22.printStackTrace();
                    C0470t.m2008a(th22);
                    this.f1900a.m1860b(0.0d);
                }
            } else if (str2.equals(SocialConstants.PARAM_APP_DESC)) {
                this.f1900a.m1871g(this.f1901b);
            } else if (str2.equals("country")) {
                this.f1900a.m1875i(this.f1901b);
            } else if (str2.equals(DistrictSearchQuery.KEYWORDS_PROVINCE)) {
                this.f1900a.m1877j(this.f1901b);
            } else if (str2.equals(DistrictSearchQuery.KEYWORDS_CITY)) {
                this.f1900a.m1879k(this.f1901b);
            } else if (str2.equals("road")) {
                this.f1900a.m1881l(this.f1901b);
            } else if (str2.equals("street")) {
                this.f1900a.m1883m(this.f1901b);
            } else if (str2.equals(ParamKey.POINAME)) {
                this.f1900a.m1885n(this.f1901b);
            } else if (str2.equals("BIZ")) {
                if (this.f1900a.m1882m() == null) {
                    this.f1900a.m1858a(new JSONObject());
                }
                try {
                    this.f1900a.m1882m().put("BIZ", this.f1901b);
                } catch (Throwable th222) {
                    th222.printStackTrace();
                }
            } else if (str2.equals("flr")) {
                this.f1900a.m1861b(this.f1901b);
            } else if (str2.equals("pid")) {
                this.f1900a.m1857a(this.f1901b);
            } else if (str2.equals("apiTime")) {
                try {
                    if (!"".equals(this.f1901b)) {
                        this.f1900a.m1855a(Long.parseLong(this.f1901b));
                    }
                } catch (Throwable th2222) {
                    th2222.printStackTrace();
                    C0470t.m2008a(th2222);
                    this.f1900a.m1855a(C0470t.m2006a());
                }
            }
            if (this.f1900a.m1882m() == null) {
                this.f1900a.m1858a(new JSONObject());
            }
            try {
                if (str2.equals("eab")) {
                    this.f1900a.m1882m().put(str2, this.f1901b);
                } else if (str2.equals("ctl")) {
                    this.f1900a.m1882m().put(str2, this.f1901b);
                } else if (str2.equals("suc")) {
                    this.f1900a.m1882m().put(str2, this.f1901b);
                } else if (str2.equals("spa")) {
                    this.f1900a.m1882m().put(str2, this.f1901b);
                }
            } catch (Throwable th22222) {
                th22222.printStackTrace();
            }
        }
    }

    /* compiled from: Parser */
    private static class C0459b extends DefaultHandler {
        public String f1902a;
        private boolean f1903b;

        private C0459b() {
            this.f1902a = "";
            this.f1903b = false;
        }

        public void characters(char[] cArr, int i, int i2) {
            if (this.f1903b) {
                this.f1902a = String.valueOf(cArr, i, i2);
            }
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) {
            if (str2.equals("sres")) {
                this.f1903b = true;
            }
        }

        public void endElement(String str, String str2, String str3) {
            if (str2.equals("sres")) {
                this.f1903b = false;
            }
        }
    }

    protected C0460m() {
    }

    String m1977a(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        InputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            byteArrayInputStream = null;
        }
        DefaultHandler c0459b = new C0459b();
        if (byteArrayInputStream != null) {
            try {
                SAXParserFactory.newInstance().newSAXParser().parse(byteArrayInputStream, c0459b);
                byteArrayInputStream.close();
            } catch (SAXException e2) {
            } catch (Throwable th) {
                th.printStackTrace();
                C0470t.m2008a(th);
            }
        }
        return c0459b.f1902a;
    }

    C0442c m1978b(String str) {
        if (str == null || str.length() == 0 || str.contains("SuccessCode=\"0\"")) {
            return null;
        }
        InputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            byteArrayInputStream = null;
        }
        SAXParserFactory newInstance = SAXParserFactory.newInstance();
        DefaultHandler c0458a = new C0458a();
        if (byteArrayInputStream != null) {
            try {
                newInstance.newSAXParser().parse(byteArrayInputStream, c0458a);
                byteArrayInputStream.close();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        c0458a.f1900a.m1863c(LocationManagerProxy.NETWORK_PROVIDER);
        if (c0458a.f1900a.m1870g() == 0) {
            c0458a.f1900a.m1855a(C0470t.m2006a());
        }
        return c0458a.f1900a;
    }
}
