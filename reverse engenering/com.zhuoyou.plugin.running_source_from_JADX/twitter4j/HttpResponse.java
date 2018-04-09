package twitter4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import twitter4j.conf.ConfigurationContext;

public abstract class HttpResponse {
    protected final HttpClientConfiguration CONF;
    protected InputStream is;
    private JSONObject json;
    private JSONArray jsonArray;
    protected String responseAsString;
    protected int statusCode;
    private boolean streamConsumed;

    public abstract void disconnect() throws IOException;

    public abstract String getResponseHeader(String str);

    public abstract Map<String, List<String>> getResponseHeaderFields();

    HttpResponse() {
        this.responseAsString = null;
        this.streamConsumed = false;
        this.json = null;
        this.jsonArray = null;
        this.CONF = ConfigurationContext.getInstance().getHttpClientConfiguration();
    }

    public HttpResponse(HttpClientConfiguration conf) {
        this.responseAsString = null;
        this.streamConsumed = false;
        this.json = null;
        this.jsonArray = null;
        this.CONF = conf;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public InputStream asStream() {
        if (!this.streamConsumed) {
            return this.is;
        }
        throw new IllegalStateException("Stream has already been consumed.");
    }

    public String asString() throws TwitterException {
        Throwable ioe;
        Throwable th;
        if (this.responseAsString == null) {
            BufferedReader bufferedReader = null;
            InputStream inputStream = null;
            try {
                inputStream = asStream();
                if (inputStream == null) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                        }
                    }
                    disconnectForcibly();
                    return null;
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    StringBuilder buf = new StringBuilder();
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        buf.append(line).append("\n");
                    }
                    this.responseAsString = buf.toString();
                    inputStream.close();
                    this.streamConsumed = true;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                        }
                    }
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e4) {
                        }
                    }
                    disconnectForcibly();
                } catch (IOException e5) {
                    ioe = e5;
                    bufferedReader = br;
                    try {
                        throw new TwitterException(ioe.getMessage(), ioe);
                    } catch (Throwable th2) {
                        th = th2;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e6) {
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e7) {
                            }
                        }
                        disconnectForcibly();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = br;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    disconnectForcibly();
                    throw th;
                }
            } catch (IOException e8) {
                ioe = e8;
                throw new TwitterException(ioe.getMessage(), ioe);
            }
        }
        return this.responseAsString;
    }

    public JSONObject asJSONObject() throws TwitterException {
        if (this.json == null) {
            Reader reader = null;
            try {
                if (this.responseAsString == null) {
                    reader = asReader();
                    this.json = new JSONObject(new JSONTokener(reader));
                } else {
                    this.json = new JSONObject(this.responseAsString);
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                disconnectForcibly();
            } catch (Throwable jsone) {
                if (this.responseAsString == null) {
                    throw new TwitterException(jsone.getMessage(), jsone);
                }
                throw new TwitterException(jsone.getMessage() + ":" + this.responseAsString, jsone);
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e2) {
                    }
                }
                disconnectForcibly();
            }
        }
        return this.json;
    }

    public JSONArray asJSONArray() throws TwitterException {
        if (this.jsonArray == null) {
            Reader reader = null;
            try {
                if (this.responseAsString == null) {
                    reader = asReader();
                    this.jsonArray = new JSONArray(new JSONTokener(reader));
                } else {
                    this.jsonArray = new JSONArray(this.responseAsString);
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
                disconnectForcibly();
            } catch (JSONException e2) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e3) {
                    }
                }
                disconnectForcibly();
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e4) {
                    }
                }
                disconnectForcibly();
            }
        }
        return this.jsonArray;
    }

    public Reader asReader() {
        try {
            return new BufferedReader(new InputStreamReader(this.is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return new InputStreamReader(this.is);
        }
    }

    private void disconnectForcibly() {
        try {
            disconnect();
        } catch (Exception e) {
        }
    }

    public String toString() {
        return "HttpResponse{statusCode=" + this.statusCode + ", responseAsString='" + this.responseAsString + '\'' + ", is=" + this.is + ", streamConsumed=" + this.streamConsumed + '}';
    }
}
