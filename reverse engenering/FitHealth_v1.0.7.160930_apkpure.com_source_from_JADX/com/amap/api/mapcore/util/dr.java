package com.amap.api.mapcore.util;

import com.umeng.socialize.common.SocializeConstants;
import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: Encrypt */
public class dr {
    private static final char[] f512a = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] f513b = new byte[128];

    static {
        int i;
        for (i = 0; i < 128; i++) {
            f513b[i] = (byte) -1;
        }
        for (i = 65; i <= 90; i++) {
            f513b[i] = (byte) (i - 65);
        }
        for (i = 97; i <= 122; i++) {
            f513b[i] = (byte) ((i - 97) + 26);
        }
        for (i = 48; i <= 57; i++) {
            f513b[i] = (byte) ((i - 48) + 52);
        }
        f513b[43] = (byte) 62;
        f513b[47] = (byte) 63;
    }

    public static String m671a(byte[] bArr) {
        try {
            return m677c(bArr);
        } catch (Throwable th) {
            eb.m742a(th, "Encrypt", "encodeBase64");
            return null;
        }
    }

    public static String m670a(String str) {
        return dx.m715a(m675b(str));
    }

    public static String m674b(byte[] bArr) {
        try {
            return m677c(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    static byte[] m673a(byte[] bArr, byte[] bArr2) {
        try {
            return m676b(bArr, bArr2);
        } catch (Throwable th) {
            eb.m742a(th, "Encrypt", "aesEncrypt");
            return null;
        }
    }

    private static byte[] m676b(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(dx.m720a());
        Key secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        try {
            instance.init(1, secretKeySpec, ivParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return instance.doFinal(bArr2);
    }

    static byte[] m672a(byte[] bArr, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(1, key);
        return instance.doFinal(bArr);
    }

    public static byte[] m675b(String str) {
        int i = 0;
        if (str == null) {
            return new byte[0];
        }
        byte[] a = dx.m721a(str);
        int length = a.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
        while (i < length) {
            byte b;
            while (true) {
                int i2 = i + 1;
                byte b2 = f513b[a[i]];
                byte b3;
                byte b4;
                if (i2 >= length || b2 != (byte) -1) {
                    if (b2 != (byte) -1) {
                        while (true) {
                            i = i2 + 1;
                            b3 = f513b[a[i2]];
                            if (i >= length || b3 != (byte) -1) {
                                if (b3 == (byte) -1) {
                                    break;
                                }
                                byteArrayOutputStream.write((b2 << 2) | ((b3 & 48) >>> 4));
                                while (true) {
                                    i2 = i + 1;
                                    b = a[i];
                                    if (b != (byte) 61) {
                                        return byteArrayOutputStream.toByteArray();
                                    }
                                    b2 = f513b[b];
                                    if (i2 >= length || b2 != (byte) -1) {
                                        if (b2 == (byte) -1) {
                                            break;
                                        }
                                        byteArrayOutputStream.write(((b3 & 15) << 4) | ((b2 & 60) >>> 2));
                                        while (true) {
                                            i = i2 + 1;
                                            b4 = a[i2];
                                            if (b4 == (byte) 61) {
                                                return byteArrayOutputStream.toByteArray();
                                            }
                                            b4 = f513b[b4];
                                            if (i >= length || b4 != (byte) -1) {
                                                if (b4 == (byte) -1) {
                                                    break;
                                                }
                                                byteArrayOutputStream.write(b4 | ((b2 & 3) << 6));
                                            } else {
                                                i2 = i;
                                            }
                                        }
                                        if (b4 == (byte) -1) {
                                            break;
                                        }
                                        byteArrayOutputStream.write(b4 | ((b2 & 3) << 6));
                                    } else {
                                        i = i2;
                                    }
                                }
                                if (b2 == (byte) -1) {
                                    byteArrayOutputStream.write(((b3 & 15) << 4) | ((b2 & 60) >>> 2));
                                    while (true) {
                                        i = i2 + 1;
                                        b4 = a[i2];
                                        if (b4 == (byte) 61) {
                                            b4 = f513b[b4];
                                            if (i >= length) {
                                                break;
                                            }
                                            break;
                                        }
                                        return byteArrayOutputStream.toByteArray();
                                        i2 = i;
                                    }
                                    if (b4 == (byte) -1) {
                                        break;
                                    }
                                    byteArrayOutputStream.write(b4 | ((b2 & 3) << 6));
                                } else {
                                    break;
                                }
                            }
                            i2 = i;
                        }
                        if (b3 == (byte) -1) {
                            byteArrayOutputStream.write((b2 << 2) | ((b3 & 48) >>> 4));
                            while (true) {
                                i2 = i + 1;
                                b = a[i];
                                if (b != (byte) 61) {
                                    b2 = f513b[b];
                                    if (i2 >= length) {
                                        break;
                                    }
                                    break;
                                }
                                return byteArrayOutputStream.toByteArray();
                                i = i2;
                            }
                            if (b2 == (byte) -1) {
                                break;
                            }
                            byteArrayOutputStream.write(((b3 & 15) << 4) | ((b2 & 60) >>> 2));
                            while (true) {
                                i = i2 + 1;
                                b4 = a[i2];
                                if (b4 == (byte) 61) {
                                    b4 = f513b[b4];
                                    if (i >= length) {
                                        break;
                                    }
                                    break;
                                }
                                return byteArrayOutputStream.toByteArray();
                                i2 = i;
                            }
                            if (b4 == (byte) -1) {
                                break;
                            }
                            byteArrayOutputStream.write(b4 | ((b2 & 3) << 6));
                        } else {
                            break;
                        }
                    }
                    break;
                }
                i = i2;
            }
            if (b2 != (byte) -1) {
                break;
            }
            while (true) {
                i = i2 + 1;
                b3 = f513b[a[i2]];
                if (i >= length) {
                    break;
                }
                break;
                i2 = i;
            }
            if (b3 == (byte) -1) {
                break;
            }
            byteArrayOutputStream.write((b2 << 2) | ((b3 & 48) >>> 4));
            while (true) {
                i2 = i + 1;
                b = a[i];
                if (b != (byte) 61) {
                    b2 = f513b[b];
                    if (i2 >= length) {
                        break;
                    }
                    break;
                }
                return byteArrayOutputStream.toByteArray();
                i = i2;
            }
            if (b2 == (byte) -1) {
                byteArrayOutputStream.write(((b3 & 15) << 4) | ((b2 & 60) >>> 2));
                while (true) {
                    i = i2 + 1;
                    b4 = a[i2];
                    if (b4 == (byte) 61) {
                        b4 = f513b[b4];
                        if (i >= length) {
                            break;
                        }
                        break;
                    }
                    return byteArrayOutputStream.toByteArray();
                    i2 = i;
                }
                if (b4 == (byte) -1) {
                    break;
                }
                byteArrayOutputStream.write(b4 | ((b2 & 3) << 6));
            } else {
                break;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static String m677c(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            int i3 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(f512a[i3 >>> 2]);
                stringBuffer.append(f512a[(i3 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i4 = i2 + 1;
            i2 = bArr[i2] & 255;
            if (i4 == length) {
                stringBuffer.append(f512a[i3 >>> 2]);
                stringBuffer.append(f512a[((i3 & 3) << 4) | ((i2 & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >>> 4)]);
                stringBuffer.append(f512a[(i2 & 15) << 2]);
                stringBuffer.append("=");
                break;
            }
            i = i4 + 1;
            i4 = bArr[i4] & 255;
            stringBuffer.append(f512a[i3 >>> 2]);
            stringBuffer.append(f512a[((i3 & 3) << 4) | ((i2 & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >>> 4)]);
            stringBuffer.append(f512a[((i2 & 15) << 2) | ((i4 & 192) >>> 6)]);
            stringBuffer.append(f512a[i4 & 63]);
        }
        return stringBuffer.toString();
    }
}