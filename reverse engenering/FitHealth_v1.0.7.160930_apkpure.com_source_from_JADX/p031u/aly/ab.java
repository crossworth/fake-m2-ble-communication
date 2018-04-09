package p031u.aly;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: UTDIdTracker */
public class ab extends C1522r {
    private static final String f4867a = "utdid";
    private static final String f4868b = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final Pattern f4869c = Pattern.compile("UTDID\">([^<]+)");
    private Context f4870d;

    public ab(Context context) {
        super(f4867a);
        this.f4870d = context;
    }

    public String mo2746f() {
        try {
            return (String) Class.forName("com.ut.device.UTDevice").getMethod("getUtdid", new Class[]{Context.class}).invoke(null, new Object[]{this.f4870d});
        } catch (Exception e) {
            return m5041g();
        }
    }

    private String m5041g() {
        InputStream fileInputStream;
        File h = m5042h();
        if (h == null || !h.exists()) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(h);
            String b = m5040b(bk.m3556a(fileInputStream));
            bk.m3567c(fileInputStream);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (Throwable th) {
            bk.m3567c(fileInputStream);
        }
    }

    private String m5040b(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = f4869c.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private File m5042h() {
        if (!bj.m3518a(this.f4870d, f4868b) || !Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        try {
            return new File(Environment.getExternalStorageDirectory().getCanonicalPath(), ".UTSystemConfig/Global/Alvin2.xml");
        } catch (Exception e) {
            return null;
        }
    }
}
