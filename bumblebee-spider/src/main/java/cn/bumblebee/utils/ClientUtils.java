package cn.bumblebee.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.lang.reflect.Method;

/**
 * 产生httpclient
 */
public class ClientUtils {

    private HttpClientBuilder httpClientBuilder = HttpClients.custom();

    public static ClientUtils newInstance() {
        return new ClientUtils();
    }
    /**
     *  自定义 http client
     * @param o
     * HttpRequestExecutor requestExec;
     * HostnameVerifier hostnameVerifier;
     * LayeredConnectionSocketFactory sslSocketFactory;
     * SSLContext sslContext;
     * HttpClientConnectionManager connManager;
     * boolean connManagerShared;
     * SchemePortResolver schemePortResolver;
     * ConnectionReuseStrategy reuseStrategy;
     * ConnectionKeepAliveStrategy keepAliveStrategy;
     * AuthenticationStrategy targetAuthStrategy;
     * AuthenticationStrategy proxyAuthStrategy;
     * UserTokenHandler userTokenHandler;
     * HttpProcessor httpprocessor;
     * DnsResolver dnsResolver;
     * LinkedList<HttpRequestInterceptor> requestFirst;
     * LinkedList<HttpRequestInterceptor> requestLast;
     * LinkedList<HttpResponseInterceptor> responseFirst;
     * LinkedList<HttpResponseInterceptor> responseLast;
     * HttpRequestRetryHandler retryHandler;
     * HttpRoutePlanner routePlanner;
     * RedirectStrategy redirectStrategy;
     * ConnectionBackoffStrategy connectionBackoffStrategy;
     * BackoffManager backoffManager;
     * ServiceUnavailableRetryStrategy serviceUnavailStrategy;
     * Lookup<AuthSchemeProvider> authSchemeRegistry;
     * Lookup<CookieSpecProvider> cookieSpecRegistry;
     * Map<String, InputStreamFactory> contentDecoderMap;
     * CookieStore cookieStore;
     * CredentialsProvider credentialsProvider;
     * String userAgent;
     * HttpHost proxy;
     * Collection<? extends Header> defaultHeaders;
     * SocketConfig defaultSocketConfig;
     * ConnectionConfig defaultConnectionConfig;
     * RequestConfig defaultRequestConfig;
     * boolean evictExpiredConnections;
     * boolean evictIdleConnections;
     * long maxIdleTime;
     * TimeUnit maxIdleTimeUnit;
     * boolean systemProperties;
     * boolean redirectHandlingDisabled;
     * boolean automaticRetriesDisabled;
     * boolean contentCompressionDisabled;
     * boolean cookieManagementDisabled;
     * boolean authCachingDisabled;
     * boolean connectionStateDisabled;
     * int maxConnTotal = 0;
     * int maxConnPerRoute = 0;
     * long connTimeToLive = -1L;
     * TimeUnit connTimeToLiveTimeUnit;
     * List<Closeable> closeables;
     * PublicSuffixMatcher publicSuffixMatcher;
     * @return
     */
    public ClientUtils with(Object o){
        if (o == null) {
            return this;
        }
        String name = o.getClass().getName();
        String methodName = "set" + name;
        Method[] methods = httpClientBuilder.getClass().getDeclaredMethods();
        for (Method method: methods) {
            if (method.getName().equals(methodName)) {
                try {
                    method.invoke(httpClientBuilder, o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return this;
    }

    public CloseableHttpClient get() {
        return httpClientBuilder.build();
    }
}
