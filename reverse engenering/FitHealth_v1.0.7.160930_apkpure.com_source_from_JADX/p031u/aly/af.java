package p031u.aly;

import com.umeng.analytics.AnalyticsConfig;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: CrashHandler */
public class af implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler f3506a;
    private an f3507b;

    public af() {
        if (Thread.getDefaultUncaughtExceptionHandler() != this) {
            this.f3506a = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void m3413a(an anVar) {
        this.f3507b = anVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        m3412a(th);
        if (this.f3506a != null && this.f3506a != Thread.getDefaultUncaughtExceptionHandler()) {
            this.f3506a.uncaughtException(thread, th);
        }
    }

    private void m3412a(Throwable th) {
        if (AnalyticsConfig.CATCH_EXCEPTION) {
            this.f3507b.mo2153a(th);
        } else {
            this.f3507b.mo2153a(null);
        }
    }
}
