package bolts;

import java.util.ArrayList;
import java.util.List;

public class AggregateException extends Exception {
    private static final long serialVersionUID = 1;
    private Throwable[] causes;

    public AggregateException(String detailMessage, Throwable[] causes) {
        Throwable th;
        if (causes == null || causes.length <= 0) {
            th = null;
        } else {
            th = causes[0];
        }
        super(detailMessage, th);
        if (causes == null || causes.length <= 0) {
            causes = null;
        }
        this.causes = causes;
    }

    @Deprecated
    public AggregateException(List<Exception> errors) {
        this("There were multiple errors.", (Throwable[]) errors.toArray(new Exception[errors.size()]));
    }

    @Deprecated
    public List<Exception> getErrors() {
        ArrayList errors = new ArrayList();
        if (this.causes != null) {
            for (Throwable cause : this.causes) {
                if (cause instanceof Exception) {
                    errors.add((Exception) cause);
                } else {
                    errors.add(new Exception(cause));
                }
            }
        }
        return errors;
    }

    public Throwable[] getCauses() {
        return this.causes;
    }
}
