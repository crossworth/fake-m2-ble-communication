package twitter4j;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public final class HttpParameter implements Comparable<HttpParameter>, Serializable {
    private static final String GIF = "image/gif";
    private static final String JPEG = "image/jpeg";
    private static final String OCTET = "application/octet-stream";
    private static final String PNG = "image/png";
    private static final long serialVersionUID = 4046908449190454692L;
    private File file = null;
    private InputStream fileBody = null;
    private String name = null;
    private String value = null;

    public HttpParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public HttpParameter(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public HttpParameter(String name, String fileName, InputStream fileBody) {
        this.name = name;
        this.file = new File(fileName);
        this.fileBody = fileBody;
    }

    public HttpParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public HttpParameter(String name, long value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public HttpParameter(String name, double value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public HttpParameter(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public File getFile() {
        return this.file;
    }

    public InputStream getFileBody() {
        return this.fileBody;
    }

    public boolean isFile() {
        return this.file != null;
    }

    public boolean hasFileBody() {
        return this.fileBody != null;
    }

    public String getContentType() {
        if (isFile()) {
            String extensions = this.file.getName();
            if (-1 == extensions.lastIndexOf(".")) {
                return "application/octet-stream";
            }
            extensions = extensions.substring(extensions.lastIndexOf(".") + 1).toLowerCase();
            if (extensions.length() == 3) {
                if ("gif".equals(extensions)) {
                    return GIF;
                }
                if ("png".equals(extensions)) {
                    return PNG;
                }
                if ("jpg".equals(extensions)) {
                    return JPEG;
                }
                return "application/octet-stream";
            } else if (extensions.length() != 4) {
                return "application/octet-stream";
            } else {
                if ("jpeg".equals(extensions)) {
                    return JPEG;
                }
                return "application/octet-stream";
            }
        }
        throw new IllegalStateException("not a file");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpParameter)) {
            return false;
        }
        HttpParameter that = (HttpParameter) o;
        if (this.file == null ? that.file != null : !this.file.equals(that.file)) {
            return false;
        }
        if (this.fileBody == null ? that.fileBody != null : !this.fileBody.equals(that.fileBody)) {
            return false;
        }
        if (!this.name.equals(that.name)) {
            return false;
        }
        if (this.value != null) {
            if (this.value.equals(that.value)) {
                return true;
            }
        } else if (that.value == null) {
            return true;
        }
        return false;
    }

    public static boolean containsFile(HttpParameter[] params) {
        int i = 0;
        boolean containsFile = false;
        if (params == null) {
            return false;
        }
        int length = params.length;
        while (i < length) {
            if (params[i].isFile()) {
                containsFile = true;
                break;
            }
            i++;
        }
        return containsFile;
    }

    static boolean containsFile(List<HttpParameter> params) {
        for (HttpParameter param : params) {
            if (param.isFile()) {
                return true;
            }
        }
        return false;
    }

    public static HttpParameter[] getParameterArray(String name, String value) {
        return new HttpParameter[]{new HttpParameter(name, value)};
    }

    public static HttpParameter[] getParameterArray(String name, int value) {
        return getParameterArray(name, String.valueOf(value));
    }

    public static HttpParameter[] getParameterArray(String name1, String value1, String name2, String value2) {
        return new HttpParameter[]{new HttpParameter(name1, value1), new HttpParameter(name2, value2)};
    }

    public static HttpParameter[] getParameterArray(String name1, int value1, String name2, int value2) {
        return getParameterArray(name1, String.valueOf(value1), name2, String.valueOf(value2));
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        int hashCode2 = this.name.hashCode() * 31;
        if (this.value != null) {
            hashCode = this.value.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode2 = (hashCode2 + hashCode) * 31;
        if (this.file != null) {
            hashCode = this.file.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (hashCode2 + hashCode) * 31;
        if (this.fileBody != null) {
            i = this.fileBody.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "PostParameter{name='" + this.name + '\'' + ", value='" + this.value + '\'' + ", file=" + this.file + ", fileBody=" + this.fileBody + '}';
    }

    public int compareTo(HttpParameter o) {
        int compared = this.name.compareTo(o.name);
        if (compared == 0) {
            return this.value.compareTo(o.value);
        }
        return compared;
    }

    public static String encodeParameters(HttpParameter[] httpParams) {
        if (httpParams == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < httpParams.length; j++) {
            if (httpParams[j].isFile()) {
                throw new IllegalArgumentException("parameter [" + httpParams[j].name + "]should be text");
            }
            if (j != 0) {
                buf.append("&");
            }
            buf.append(encode(httpParams[j].name)).append("=").append(encode(httpParams[j].value));
        }
        return buf.toString();
    }

    public static String encode(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        int i = 0;
        while (i < encoded.length()) {
            char focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && i + 1 < encoded.length() && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
            i++;
        }
        return buf.toString();
    }

    public static String decode(String value) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(value.replace("%2A", "*").replace("%2a", "*").replace("%20", " "), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return decoded;
    }

    public static List<HttpParameter> decodeParameters(String queryParameters) {
        List<HttpParameter> result = new ArrayList();
        for (String pair : queryParameters.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) {
                String name = decode(parts[0]);
                String value = decode(parts[1]);
                if (!(name.equals("") || value.equals(""))) {
                    result.add(new HttpParameter(name, value));
                }
            }
        }
        return result;
    }
}
