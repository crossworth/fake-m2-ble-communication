package p031u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: TIOStreamTransport */
public class da extends dc {
    protected InputStream f5194a = null;
    protected OutputStream f5195b = null;

    protected da() {
    }

    public da(InputStream inputStream) {
        this.f5194a = inputStream;
    }

    public da(OutputStream outputStream) {
        this.f5195b = outputStream;
    }

    public da(InputStream inputStream, OutputStream outputStream) {
        this.f5194a = inputStream;
        this.f5195b = outputStream;
    }

    public boolean mo2814a() {
        return true;
    }

    public void mo2815b() throws dd {
    }

    public void mo2817c() {
        if (this.f5194a != null) {
            try {
                this.f5194a.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.f5194a = null;
        }
        if (this.f5195b != null) {
            try {
                this.f5195b.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.f5195b = null;
        }
    }

    public int mo2813a(byte[] bArr, int i, int i2) throws dd {
        if (this.f5194a == null) {
            throw new dd(1, "Cannot read from null inputStream");
        }
        try {
            int read = this.f5194a.read(bArr, i, i2);
            if (read >= 0) {
                return read;
            }
            throw new dd(4);
        } catch (Throwable e) {
            throw new dd(0, e);
        }
    }

    public void mo2816b(byte[] bArr, int i, int i2) throws dd {
        if (this.f5195b == null) {
            throw new dd(1, "Cannot write to null outputStream");
        }
        try {
            this.f5195b.write(bArr, i, i2);
        } catch (Throwable e) {
            throw new dd(0, e);
        }
    }

    public void mo2818d() throws dd {
        if (this.f5195b == null) {
            throw new dd(1, "Cannot flush null outputStream");
        }
        try {
            this.f5195b.flush();
        } catch (Throwable e) {
            throw new dd(0, e);
        }
    }
}
