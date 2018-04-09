package twitter4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class HttpResponseImpl extends HttpResponse {
    private HttpURLConnection con;

    HttpResponseImpl(HttpURLConnection con, HttpClientConfiguration conf) throws IOException {
        super(conf);
        this.con = con;
        try {
            this.statusCode = con.getResponseCode();
        } catch (IOException e) {
            if ("Received authentication challenge is null".equals(e.getMessage())) {
                this.statusCode = con.getResponseCode();
            } else {
                throw e;
            }
        }
        InputStream errorStream = con.getErrorStream();
        this.is = errorStream;
        if (errorStream == null) {
            this.is = con.getInputStream();
        }
        if (this.is != null && "gzip".equals(con.getContentEncoding())) {
            this.is = new StreamingGZIPInputStream(this.is);
        }
    }

    HttpResponseImpl(String content) {
        this.responseAsString = content;
    }

    public String getResponseHeader(String name) {
        return this.con.getHeaderField(name);
    }

    public Map<String, List<String>> getResponseHeaderFields() {
        return this.con.getHeaderFields();
    }

    public void disconnect() {
        this.con.disconnect();
    }
}
