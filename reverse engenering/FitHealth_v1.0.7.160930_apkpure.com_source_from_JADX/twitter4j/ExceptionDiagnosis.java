package twitter4j;

import com.umeng.socialize.common.SocializeConstants;
import java.io.Serializable;

final class ExceptionDiagnosis implements Serializable {
    private static final long serialVersionUID = 8501009773274399369L;
    private String hexString;
    private int lineNumberHash;
    private int stackLineHash;

    ExceptionDiagnosis(Throwable th) {
        this(th, new String[0]);
    }

    ExceptionDiagnosis(Throwable th, String[] inclusionFilter) {
        this.hexString = "";
        Throwable th1 = th;
        StackTraceElement[] stackTrace = th.getStackTrace();
        this.stackLineHash = 0;
        this.lineNumberHash = 0;
        for (int i = stackTrace.length - 1; i >= 0; i--) {
            StackTraceElement line = stackTrace[i];
            for (String filter : inclusionFilter) {
                if (line.getClassName().startsWith(filter)) {
                    this.stackLineHash = (this.stackLineHash * 31) + (line.getClassName().hashCode() + line.getMethodName().hashCode());
                    this.lineNumberHash = (this.lineNumberHash * 31) + line.getLineNumber();
                    break;
                }
            }
        }
        this.hexString += toHexString(this.stackLineHash) + SocializeConstants.OP_DIVIDER_MINUS + toHexString(this.lineNumberHash);
        if (th.getCause() != null) {
            this.hexString += " " + new ExceptionDiagnosis(th.getCause(), inclusionFilter).asHexString();
        }
    }

    int getStackLineHash() {
        return this.stackLineHash;
    }

    String getStackLineHashAsHex() {
        return toHexString(this.stackLineHash);
    }

    int getLineNumberHash() {
        return this.lineNumberHash;
    }

    String getLineNumberHashAsHex() {
        return toHexString(this.lineNumberHash);
    }

    String asHexString() {
        return this.hexString;
    }

    private String toHexString(int value) {
        String str = "0000000" + Integer.toHexString(value);
        return str.substring(str.length() - 8, str.length());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExceptionDiagnosis that = (ExceptionDiagnosis) o;
        if (this.lineNumberHash != that.lineNumberHash) {
            return false;
        }
        if (this.stackLineHash != that.stackLineHash) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.stackLineHash * 31) + this.lineNumberHash;
    }

    public String toString() {
        return "ExceptionDiagnosis{stackLineHash=" + this.stackLineHash + ", lineNumberHash=" + this.lineNumberHash + '}';
    }
}
