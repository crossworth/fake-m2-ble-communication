package twitter4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import twitter4j.conf.ConfigurationContext;

public final class HttpClientFactory {
    private static final Constructor<?> HTTP_CLIENT_CONSTRUCTOR;
    private static final String HTTP_CLIENT_IMPLEMENTATION = "twitter4j.http.httpClient";
    private static final HashMap<HttpClientConfiguration, HttpClient> confClientMap = new HashMap();

    static {
        Class<?> clazz = null;
        String httpClientImpl = System.getProperty(HTTP_CLIENT_IMPLEMENTATION);
        if (httpClientImpl != null) {
            try {
                clazz = Class.forName(httpClientImpl);
            } catch (ClassNotFoundException e) {
            }
        }
        if (clazz == null) {
            try {
                clazz = Class.forName("twitter4j.AlternativeHttpClientImpl");
            } catch (ClassNotFoundException e2) {
            }
        }
        if (clazz == null) {
            try {
                clazz = Class.forName("twitter4j.HttpClientImpl");
            } catch (ClassNotFoundException cnfe) {
                throw new AssertionError(cnfe);
            }
        }
        try {
            HTTP_CLIENT_CONSTRUCTOR = clazz.getConstructor(new Class[]{HttpClientConfiguration.class});
        } catch (NoSuchMethodException nsme) {
            throw new AssertionError(nsme);
        }
    }

    public static HttpClient getInstance() {
        return getInstance(ConfigurationContext.getInstance().getHttpClientConfiguration());
    }

    public static HttpClient getInstance(HttpClientConfiguration conf) {
        HttpClient client = (HttpClient) confClientMap.get(conf);
        if (client != null) {
            return client;
        }
        try {
            client = (HttpClient) HTTP_CLIENT_CONSTRUCTOR.newInstance(new Object[]{conf});
            confClientMap.put(conf, client);
            return client;
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (InvocationTargetException e3) {
            throw new AssertionError(e3);
        }
    }
}
