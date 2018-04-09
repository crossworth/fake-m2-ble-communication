package org.apache.http.impl.auth;

import com.facebook.internal.ServerProtocol;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

@NotThreadSafe
public class DigestScheme extends RFC2617Scheme {
    private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;
    private String a1;
    private String a2;
    private String cnonce;
    private boolean complete = false;
    private String lastNonce;
    private long nounceCount;

    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        if (getParameter("realm") == null) {
            throw new MalformedChallengeException("missing realm in challenge");
        } else if (getParameter("nonce") == null) {
            throw new MalformedChallengeException("missing nonce in challenge");
        } else {
            this.complete = true;
        }
    }

    public boolean isComplete() {
        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    public String getSchemeName() {
        return "digest";
    }

    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String name, String value) {
        getParameters().put(name, value);
    }

    public Header authenticate(Credentials credentials, HttpRequest request) throws AuthenticationException {
        if (credentials == null) {
            throw new IllegalArgumentException("Credentials may not be null");
        } else if (request == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        } else {
            getParameters().put("methodname", request.getRequestLine().getMethod());
            getParameters().put("uri", request.getRequestLine().getUri());
            if (getParameter("charset") == null) {
                getParameters().put("charset", AuthParams.getCredentialCharset(request.getParams()));
            }
            return createDigestHeader(credentials);
        }
    }

    private static MessageDigest createMessageDigest(String digAlg) throws UnsupportedDigestAlgorithmException {
        try {
            return MessageDigest.getInstance(digAlg);
        } catch (Exception e) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + digAlg);
        }
    }

    private Header createDigestHeader(Credentials credentials) throws AuthenticationException {
        String uri = getParameter("uri");
        String realm = getParameter("realm");
        String nonce = getParameter("nonce");
        String opaque = getParameter("opaque");
        String method = getParameter("methodname");
        String algorithm = getParameter("algorithm");
        if (uri == null) {
            throw new IllegalStateException("URI may not be null");
        } else if (realm == null) {
            throw new IllegalStateException("Realm may not be null");
        } else if (nonce == null) {
            throw new IllegalStateException("Nonce may not be null");
        } else {
            int qop = -1;
            String qoplist = getParameter("qop");
            if (qoplist != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(qoplist, SeparatorConstants.SEPARATOR_ADS_ID);
                while (stringTokenizer.hasMoreTokens()) {
                    if (stringTokenizer.nextToken().trim().equals("auth")) {
                        qop = 2;
                        break;
                    }
                }
            }
            qop = 0;
            if (qop == -1) {
                throw new AuthenticationException("None of the qop methods is supported: " + qoplist);
            }
            if (algorithm == null) {
                algorithm = "MD5";
            }
            String charset = getParameter("charset");
            if (charset == null) {
                charset = "ISO-8859-1";
            }
            String digAlg = algorithm;
            if (digAlg.equalsIgnoreCase("MD5-sess")) {
                digAlg = "MD5";
            }
            try {
                String digestValue;
                MessageDigest digester = createMessageDigest(digAlg);
                String uname = credentials.getUserPrincipal().getName();
                String pwd = credentials.getPassword();
                if (nonce.equals(this.lastNonce)) {
                    this.nounceCount++;
                } else {
                    this.nounceCount = 1;
                    this.cnonce = null;
                    this.lastNonce = nonce;
                }
                Appendable stringBuilder = new StringBuilder(256);
                new Formatter(stringBuilder, Locale.US).format("%08x", new Object[]{Long.valueOf(this.nounceCount)});
                String nc = stringBuilder.toString();
                if (this.cnonce == null) {
                    this.cnonce = createCnonce();
                }
                this.a1 = null;
                this.a2 = null;
                if (algorithm.equalsIgnoreCase("MD5-sess")) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(uname).append(':').append(realm).append(':').append(pwd);
                    String checksum = encode(digester.digest(EncodingUtils.getBytes(stringBuilder.toString(), charset)));
                    stringBuilder.setLength(0);
                    stringBuilder.append(checksum).append(':').append(nonce).append(':').append(this.cnonce);
                    this.a1 = stringBuilder.toString();
                } else {
                    stringBuilder.setLength(0);
                    stringBuilder.append(uname).append(':').append(realm).append(':').append(pwd);
                    this.a1 = stringBuilder.toString();
                }
                String hasha1 = encode(digester.digest(EncodingUtils.getBytes(this.a1, charset)));
                if (qop == 2) {
                    this.a2 = method + ':' + uri;
                } else if (qop == 1) {
                    throw new AuthenticationException("qop-int method is not suppported");
                } else {
                    this.a2 = method + ':' + uri;
                }
                String hasha2 = encode(digester.digest(EncodingUtils.getBytes(this.a2, charset)));
                if (qop == 0) {
                    stringBuilder.setLength(0);
                    stringBuilder.append(hasha1).append(':').append(nonce).append(':').append(hasha2);
                    digestValue = stringBuilder.toString();
                } else {
                    stringBuilder.setLength(0);
                    stringBuilder.append(hasha1).append(':').append(nonce).append(':').append(nc).append(':').append(this.cnonce).append(':').append(qop == 1 ? "auth-int" : "auth").append(':').append(hasha2);
                    digestValue = stringBuilder.toString();
                }
                String digest = encode(digester.digest(EncodingUtils.getAsciiBytes(digestValue)));
                CharArrayBuffer buffer = new CharArrayBuffer(128);
                if (isProxy()) {
                    buffer.append("Proxy-Authorization");
                } else {
                    buffer.append("Authorization");
                }
                buffer.append(": Digest ");
                List<BasicNameValuePair> arrayList = new ArrayList(20);
                arrayList.add(new BasicNameValuePair("username", uname));
                arrayList.add(new BasicNameValuePair("realm", realm));
                arrayList.add(new BasicNameValuePair("nonce", nonce));
                arrayList.add(new BasicNameValuePair("uri", uri));
                arrayList.add(new BasicNameValuePair("response", digest));
                if (qop != 0) {
                    arrayList.add(new BasicNameValuePair("qop", qop == 1 ? "auth-int" : "auth"));
                    arrayList.add(new BasicNameValuePair("nc", nc));
                    arrayList.add(new BasicNameValuePair("cnonce", this.cnonce));
                }
                if (algorithm != null) {
                    arrayList.add(new BasicNameValuePair("algorithm", algorithm));
                }
                if (opaque != null) {
                    arrayList.add(new BasicNameValuePair("opaque", opaque));
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    boolean z;
                    BasicNameValuePair param = (BasicNameValuePair) arrayList.get(i);
                    if (i > 0) {
                        buffer.append(", ");
                    }
                    boolean noQuotes = "nc".equals(param.getName()) || "qop".equals(param.getName());
                    BasicHeaderValueFormatter basicHeaderValueFormatter = BasicHeaderValueFormatter.DEFAULT;
                    if (noQuotes) {
                        z = false;
                    } else {
                        z = true;
                    }
                    basicHeaderValueFormatter.formatNameValuePair(buffer, (NameValuePair) param, z);
                }
                return new BufferedHeader(buffer);
            } catch (UnsupportedDigestAlgorithmException e) {
                throw new AuthenticationException("Unsuppported digest algorithm: " + digAlg);
            }
        }
    }

    String getCnonce() {
        return this.cnonce;
    }

    String getA1() {
        return this.a1;
    }

    String getA2() {
        return this.a2;
    }

    private static String encode(byte[] binaryData) {
        int n = binaryData.length;
        char[] buffer = new char[(n * 2)];
        for (int i = 0; i < n; i++) {
            int low = binaryData[i] & 15;
            buffer[i * 2] = HEXADECIMAL[(binaryData[i] & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >> 4];
            buffer[(i * 2) + 1] = HEXADECIMAL[low];
        }
        return new String(buffer);
    }

    public static String createCnonce() {
        byte[] tmp = new byte[8];
        new SecureRandom().nextBytes(tmp);
        return encode(tmp);
    }
}
