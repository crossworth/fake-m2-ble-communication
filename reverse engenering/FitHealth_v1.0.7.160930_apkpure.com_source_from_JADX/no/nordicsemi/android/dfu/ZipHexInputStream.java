package no.nordicsemi.android.dfu;

import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipHexInputStream extends ZipInputStream {
    private static final String APPLICATION_INIT = "application.dat";
    private static final String APPLICATION_NAME = "application.(hex|bin)";
    private static final String BOOTLOADER_NAME = "bootloader.(hex|bin)";
    private static final String SOFTDEVICE_NAME = "softdevice.(hex|bin)";
    private static final String SYSTEM_INIT = "system.dat";
    private byte[] applicationBytes;
    private byte[] applicationInitBytes;
    private int applicationSize;
    private byte[] bootloaderBytes;
    private int bootloaderSize;
    private int bytesRead = 0;
    private int bytesReadFromCurrentSource = 0;
    private byte[] currentSource;
    private byte[] softDeviceBytes;
    private int softDeviceSize;
    private byte[] systemInitBytes;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ZipHexInputStream(java.io.InputStream r16, int r17, int r18) throws java.io.IOException {
        /*
        r15 = this;
        r15.<init>(r16);
        r13 = 0;
        r15.bytesRead = r13;
        r13 = 0;
        r15.bytesReadFromCurrentSource = r13;
    L_0x0009:
        r12 = r15.getNextEntry();	 Catch:{ all -> 0x0049 }
        if (r12 == 0) goto L_0x011e;
    L_0x000f:
        r7 = r12.getName();	 Catch:{ all -> 0x0049 }
        r13 = "softdevice.(hex|bin)";
        r9 = r7.matches(r13);	 Catch:{ all -> 0x0049 }
        r13 = "bootloader.(hex|bin)";
        r4 = r7.matches(r13);	 Catch:{ all -> 0x0049 }
        r13 = "application.(hex|bin)";
        r1 = r7.matches(r13);	 Catch:{ all -> 0x0049 }
        r13 = "system.dat";
        r11 = r7.matches(r13);	 Catch:{ all -> 0x0049 }
        r13 = "application.dat";
        r2 = r7.matches(r13);	 Catch:{ all -> 0x0049 }
        r13 = r12.isDirectory();	 Catch:{ all -> 0x0049 }
        if (r13 != 0) goto L_0x0041;
    L_0x0037:
        if (r9 != 0) goto L_0x004e;
    L_0x0039:
        if (r4 != 0) goto L_0x004e;
    L_0x003b:
        if (r1 != 0) goto L_0x004e;
    L_0x003d:
        if (r11 != 0) goto L_0x004e;
    L_0x003f:
        if (r2 != 0) goto L_0x004e;
    L_0x0041:
        r13 = new java.io.IOException;	 Catch:{ all -> 0x0049 }
        r14 = "ZIP content not supported. Only softdevice.(hex|bin), bootloader.(hex|bin), application.(hex|bin), system.dat or application.dat are allowed.";
        r13.<init>(r14);	 Catch:{ all -> 0x0049 }
        throw r13;	 Catch:{ all -> 0x0049 }
    L_0x0049:
        r13 = move-exception;
        super.close();
        throw r13;
    L_0x004e:
        if (r18 == 0) goto L_0x0068;
    L_0x0050:
        if (r9 != 0) goto L_0x0054;
    L_0x0052:
        if (r11 == 0) goto L_0x0058;
    L_0x0054:
        r13 = r18 & 1;
        if (r13 == 0) goto L_0x0009;
    L_0x0058:
        if (r4 != 0) goto L_0x005c;
    L_0x005a:
        if (r11 == 0) goto L_0x0060;
    L_0x005c:
        r13 = r18 & 2;
        if (r13 == 0) goto L_0x0009;
    L_0x0060:
        if (r1 != 0) goto L_0x0064;
    L_0x0062:
        if (r2 == 0) goto L_0x0068;
    L_0x0064:
        r13 = r18 & 4;
        if (r13 == 0) goto L_0x0009;
    L_0x0068:
        r3 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x0049 }
        r3.<init>();	 Catch:{ all -> 0x0049 }
        r13 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r5 = new byte[r13];	 Catch:{ all -> 0x0049 }
    L_0x0071:
        r6 = super.read(r5);	 Catch:{ all -> 0x0049 }
        r13 = -1;
        if (r6 == r13) goto L_0x007d;
    L_0x0078:
        r13 = 0;
        r3.write(r5, r13, r6);	 Catch:{ all -> 0x0049 }
        goto L_0x0071;
    L_0x007d:
        r10 = r3.toByteArray();	 Catch:{ all -> 0x0049 }
        if (r9 == 0) goto L_0x00ae;
    L_0x0083:
        r13 = "hex";
        r13 = r7.endsWith(r13);	 Catch:{ all -> 0x0049 }
        if (r13 == 0) goto L_0x00a8;
    L_0x008b:
        r8 = new no.nordicsemi.android.dfu.HexInputStream;	 Catch:{ all -> 0x0049 }
        r0 = r17;
        r8.<init>(r10, r0);	 Catch:{ all -> 0x0049 }
        r13 = r8.available();	 Catch:{ all -> 0x0049 }
        r15.softDeviceSize = r13;	 Catch:{ all -> 0x0049 }
        r10 = new byte[r13];	 Catch:{ all -> 0x0049 }
        r15.softDeviceBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r15.softDeviceBytes;	 Catch:{ all -> 0x0049 }
        r8.read(r13);	 Catch:{ all -> 0x0049 }
        r8.close();	 Catch:{ all -> 0x0049 }
    L_0x00a4:
        r15.currentSource = r10;	 Catch:{ all -> 0x0049 }
        goto L_0x0009;
    L_0x00a8:
        r15.softDeviceBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r10.length;	 Catch:{ all -> 0x0049 }
        r15.softDeviceSize = r13;	 Catch:{ all -> 0x0049 }
        goto L_0x00a4;
    L_0x00ae:
        if (r4 == 0) goto L_0x00e1;
    L_0x00b0:
        r13 = "hex";
        r13 = r7.endsWith(r13);	 Catch:{ all -> 0x0049 }
        if (r13 == 0) goto L_0x00db;
    L_0x00b8:
        r8 = new no.nordicsemi.android.dfu.HexInputStream;	 Catch:{ all -> 0x0049 }
        r0 = r17;
        r8.<init>(r10, r0);	 Catch:{ all -> 0x0049 }
        r13 = r8.available();	 Catch:{ all -> 0x0049 }
        r15.bootloaderSize = r13;	 Catch:{ all -> 0x0049 }
        r10 = new byte[r13];	 Catch:{ all -> 0x0049 }
        r15.bootloaderBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r15.bootloaderBytes;	 Catch:{ all -> 0x0049 }
        r8.read(r13);	 Catch:{ all -> 0x0049 }
        r8.close();	 Catch:{ all -> 0x0049 }
    L_0x00d1:
        r13 = r15.currentSource;	 Catch:{ all -> 0x0049 }
        r14 = r15.applicationBytes;	 Catch:{ all -> 0x0049 }
        if (r13 != r14) goto L_0x0009;
    L_0x00d7:
        r15.currentSource = r10;	 Catch:{ all -> 0x0049 }
        goto L_0x0009;
    L_0x00db:
        r15.bootloaderBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r10.length;	 Catch:{ all -> 0x0049 }
        r15.bootloaderSize = r13;	 Catch:{ all -> 0x0049 }
        goto L_0x00d1;
    L_0x00e1:
        if (r1 == 0) goto L_0x0112;
    L_0x00e3:
        r13 = "hex";
        r13 = r7.endsWith(r13);	 Catch:{ all -> 0x0049 }
        if (r13 == 0) goto L_0x010c;
    L_0x00eb:
        r8 = new no.nordicsemi.android.dfu.HexInputStream;	 Catch:{ all -> 0x0049 }
        r0 = r17;
        r8.<init>(r10, r0);	 Catch:{ all -> 0x0049 }
        r13 = r8.available();	 Catch:{ all -> 0x0049 }
        r15.applicationSize = r13;	 Catch:{ all -> 0x0049 }
        r10 = new byte[r13];	 Catch:{ all -> 0x0049 }
        r15.applicationBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r15.applicationBytes;	 Catch:{ all -> 0x0049 }
        r8.read(r13);	 Catch:{ all -> 0x0049 }
        r8.close();	 Catch:{ all -> 0x0049 }
    L_0x0104:
        r13 = r15.currentSource;	 Catch:{ all -> 0x0049 }
        if (r13 != 0) goto L_0x0009;
    L_0x0108:
        r15.currentSource = r10;	 Catch:{ all -> 0x0049 }
        goto L_0x0009;
    L_0x010c:
        r15.applicationBytes = r10;	 Catch:{ all -> 0x0049 }
        r13 = r10.length;	 Catch:{ all -> 0x0049 }
        r15.applicationSize = r13;	 Catch:{ all -> 0x0049 }
        goto L_0x0104;
    L_0x0112:
        if (r11 == 0) goto L_0x0118;
    L_0x0114:
        r15.systemInitBytes = r10;	 Catch:{ all -> 0x0049 }
        goto L_0x0009;
    L_0x0118:
        if (r2 == 0) goto L_0x0009;
    L_0x011a:
        r15.applicationInitBytes = r10;	 Catch:{ all -> 0x0049 }
        goto L_0x0009;
    L_0x011e:
        super.close();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: no.nordicsemi.android.dfu.ZipHexInputStream.<init>(java.io.InputStream, int, int):void");
    }

    public void close() throws IOException {
        this.softDeviceBytes = null;
        this.bootloaderBytes = null;
        this.softDeviceBytes = null;
        this.applicationSize = 0;
        this.bootloaderSize = 0;
        this.softDeviceSize = 0;
        this.currentSource = null;
        this.bytesReadFromCurrentSource = 0;
        this.bytesRead = 0;
        super.close();
    }

    public int read(byte[] buffer) throws IOException {
        int size;
        int maxSize = this.currentSource.length - this.bytesReadFromCurrentSource;
        if (buffer.length <= maxSize) {
            size = buffer.length;
        } else {
            size = maxSize;
        }
        System.arraycopy(this.currentSource, this.bytesReadFromCurrentSource, buffer, 0, size);
        this.bytesReadFromCurrentSource += size;
        if (buffer.length > size) {
            if (startNextFile() == null) {
                this.bytesRead += size;
                return size;
            }
            int nextSize;
            maxSize = this.currentSource.length;
            if (buffer.length - size <= maxSize) {
                nextSize = buffer.length - size;
            } else {
                nextSize = maxSize;
            }
            System.arraycopy(this.currentSource, 0, buffer, size, nextSize);
            this.bytesReadFromCurrentSource += nextSize;
            size += nextSize;
        }
        this.bytesRead += size;
        return size;
    }

    public int getContentType() {
        byte b = (byte) 0;
        if (this.softDeviceSize > 0) {
            b = (byte) 1;
        }
        if (this.bootloaderSize > 0) {
            b = (byte) (b | 2);
        }
        if (this.applicationSize > 0) {
            return (byte) (b | 4);
        }
        return b;
    }

    public int setContentType(int type) {
        if (this.bytesRead > 0) {
            throw new UnsupportedOperationException("Content type must not be change after reading content");
        }
        int t = getContentType() & type;
        if ((t & 1) == 0) {
            this.softDeviceBytes = null;
            this.softDeviceSize = 0;
        }
        if ((t & 2) == 0) {
            this.bootloaderBytes = null;
            this.bootloaderSize = 0;
        }
        if ((t & 4) == 0) {
            this.applicationBytes = null;
            this.applicationSize = 0;
        }
        return t;
    }

    private byte[] startNextFile() {
        byte[] ret;
        if (this.currentSource == this.softDeviceBytes && this.bootloaderBytes != null) {
            ret = this.bootloaderBytes;
            this.currentSource = ret;
        } else if (this.currentSource == this.applicationBytes || this.applicationBytes == null) {
            ret = null;
            this.currentSource = null;
        } else {
            ret = this.applicationBytes;
            this.currentSource = ret;
        }
        this.bytesReadFromCurrentSource = 0;
        return ret;
    }

    public int available() {
        return ((this.softDeviceSize + this.bootloaderSize) + this.applicationSize) - this.bytesRead;
    }

    public int softDeviceImageSize() {
        return this.softDeviceSize;
    }

    public int bootloaderImageSize() {
        return this.bootloaderSize;
    }

    public int applicationImageSize() {
        return this.applicationSize;
    }

    public byte[] getSystemInit() {
        return this.systemInitBytes;
    }

    public byte[] getApplicationInit() {
        return this.applicationInitBytes;
    }
}
