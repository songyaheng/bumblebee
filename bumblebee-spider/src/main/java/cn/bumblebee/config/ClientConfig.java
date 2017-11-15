package cn.bumblebee.config;

import org.apache.http.*;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.Closeable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClientConfig {

    private HttpRequestExecutor requestExec;
    private HostnameVerifier hostnameVerifier;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private SSLContext sslContext;
    private HttpClientConnectionManager connManager;
    private boolean connManagerShared;
    private SchemePortResolver schemePortResolver;
    private ConnectionReuseStrategy reuseStrategy;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private AuthenticationStrategy targetAuthStrategy;
    private AuthenticationStrategy proxyAuthStrategy;
    private UserTokenHandler userTokenHandler;
    private HttpProcessor httpprocessor;
    private DnsResolver dnsResolver;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private HttpRequestRetryHandler retryHandler;
    private HttpRoutePlanner routePlanner;
    private RedirectStrategy redirectStrategy;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private BackoffManager backoffManager;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private Lookup<AuthSchemeProvider> authSchemeRegistry;
    private Lookup<CookieSpecProvider> cookieSpecRegistry;
    private Map<String, InputStreamFactory> contentDecoderMap;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private String userAgent;
    private HttpHost proxy;
    private Collection<? extends Header> defaultHeaders;
    private SocketConfig defaultSocketConfig;
    private ConnectionConfig defaultConnectionConfig;
    private RequestConfig defaultRequestConfig;
    private boolean evictExpiredConnections;
    private boolean evictIdleConnections;
    private long maxIdleTime;
    private TimeUnit maxIdleTimeUnit;
    private boolean systemProperties;
    private boolean redirectHandlingDisabled;
    private boolean automaticRetriesDisabled;
    private boolean contentCompressionDisabled;
    private boolean cookieManagementDisabled;
    private boolean authCachingDisabled;
    private boolean connectionStateDisabled;
    private int maxConnTotal = 0;
    private int maxConnPerRoute = 0;
    private long connTimeToLive = -1L;
    private TimeUnit connTimeToLiveTimeUnit;
    private List<Closeable> closeables;
    private PublicSuffixMatcher publicSuffixMatcher;

    public HttpRequestExecutor getRequestExec() {
        return requestExec;
    }

    public void setRequestExec(HttpRequestExecutor requestExec) {
        this.requestExec = requestExec;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public LayeredConnectionSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(LayeredConnectionSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public HttpClientConnectionManager getConnManager() {
        return connManager;
    }

    public void setConnManager(HttpClientConnectionManager connManager) {
        this.connManager = connManager;
    }

    public boolean isConnManagerShared() {
        return connManagerShared;
    }

    public void setConnManagerShared(boolean connManagerShared) {
        this.connManagerShared = connManagerShared;
    }

    public SchemePortResolver getSchemePortResolver() {
        return schemePortResolver;
    }

    public void setSchemePortResolver(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver;
    }

    public ConnectionReuseStrategy getReuseStrategy() {
        return reuseStrategy;
    }

    public void setReuseStrategy(ConnectionReuseStrategy reuseStrategy) {
        this.reuseStrategy = reuseStrategy;
    }

    public ConnectionKeepAliveStrategy getKeepAliveStrategy() {
        return keepAliveStrategy;
    }

    public void setKeepAliveStrategy(ConnectionKeepAliveStrategy keepAliveStrategy) {
        this.keepAliveStrategy = keepAliveStrategy;
    }

    public AuthenticationStrategy getTargetAuthStrategy() {
        return targetAuthStrategy;
    }

    public void setTargetAuthStrategy(AuthenticationStrategy targetAuthStrategy) {
        this.targetAuthStrategy = targetAuthStrategy;
    }

    public AuthenticationStrategy getProxyAuthStrategy() {
        return proxyAuthStrategy;
    }

    public void setProxyAuthStrategy(AuthenticationStrategy proxyAuthStrategy) {
        this.proxyAuthStrategy = proxyAuthStrategy;
    }

    public UserTokenHandler getUserTokenHandler() {
        return userTokenHandler;
    }

    public void setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
    }

    public HttpProcessor getHttpprocessor() {
        return httpprocessor;
    }

    public void setHttpprocessor(HttpProcessor httpprocessor) {
        this.httpprocessor = httpprocessor;
    }

    public DnsResolver getDnsResolver() {
        return dnsResolver;
    }

    public void setDnsResolver(DnsResolver dnsResolver) {
        this.dnsResolver = dnsResolver;
    }

    public LinkedList<HttpRequestInterceptor> getRequestFirst() {
        return requestFirst;
    }

    public void setRequestFirst(LinkedList<HttpRequestInterceptor> requestFirst) {
        this.requestFirst = requestFirst;
    }

    public LinkedList<HttpRequestInterceptor> getRequestLast() {
        return requestLast;
    }

    public void setRequestLast(LinkedList<HttpRequestInterceptor> requestLast) {
        this.requestLast = requestLast;
    }

    public LinkedList<HttpResponseInterceptor> getResponseFirst() {
        return responseFirst;
    }

    public void setResponseFirst(LinkedList<HttpResponseInterceptor> responseFirst) {
        this.responseFirst = responseFirst;
    }

    public LinkedList<HttpResponseInterceptor> getResponseLast() {
        return responseLast;
    }

    public void setResponseLast(LinkedList<HttpResponseInterceptor> responseLast) {
        this.responseLast = responseLast;
    }

    public HttpRequestRetryHandler getRetryHandler() {
        return retryHandler;
    }

    public void setRetryHandler(HttpRequestRetryHandler retryHandler) {
        this.retryHandler = retryHandler;
    }

    public HttpRoutePlanner getRoutePlanner() {
        return routePlanner;
    }

    public void setRoutePlanner(HttpRoutePlanner routePlanner) {
        this.routePlanner = routePlanner;
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    public ConnectionBackoffStrategy getConnectionBackoffStrategy() {
        return connectionBackoffStrategy;
    }

    public void setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
    }

    public BackoffManager getBackoffManager() {
        return backoffManager;
    }

    public void setBackoffManager(BackoffManager backoffManager) {
        this.backoffManager = backoffManager;
    }

    public ServiceUnavailableRetryStrategy getServiceUnavailStrategy() {
        return serviceUnavailStrategy;
    }

    public void setServiceUnavailStrategy(ServiceUnavailableRetryStrategy serviceUnavailStrategy) {
        this.serviceUnavailStrategy = serviceUnavailStrategy;
    }

    public Lookup<AuthSchemeProvider> getAuthSchemeRegistry() {
        return authSchemeRegistry;
    }

    public void setAuthSchemeRegistry(Lookup<AuthSchemeProvider> authSchemeRegistry) {
        this.authSchemeRegistry = authSchemeRegistry;
    }

    public Lookup<CookieSpecProvider> getCookieSpecRegistry() {
        return cookieSpecRegistry;
    }

    public void setCookieSpecRegistry(Lookup<CookieSpecProvider> cookieSpecRegistry) {
        this.cookieSpecRegistry = cookieSpecRegistry;
    }

    public Map<String, InputStreamFactory> getContentDecoderMap() {
        return contentDecoderMap;
    }

    public void setContentDecoderMap(Map<String, InputStreamFactory> contentDecoderMap) {
        this.contentDecoderMap = contentDecoderMap;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public CredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public HttpHost getProxy() {
        return proxy;
    }

    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    public Collection<? extends Header> getDefaultHeaders() {
        return defaultHeaders;
    }

    public void setDefaultHeaders(Collection<? extends Header> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    public SocketConfig getDefaultSocketConfig() {
        return defaultSocketConfig;
    }

    public void setDefaultSocketConfig(SocketConfig defaultSocketConfig) {
        this.defaultSocketConfig = defaultSocketConfig;
    }

    public ConnectionConfig getDefaultConnectionConfig() {
        return defaultConnectionConfig;
    }

    public void setDefaultConnectionConfig(ConnectionConfig defaultConnectionConfig) {
        this.defaultConnectionConfig = defaultConnectionConfig;
    }

    public RequestConfig getDefaultRequestConfig() {
        return defaultRequestConfig;
    }

    public void setDefaultRequestConfig(RequestConfig defaultRequestConfig) {
        this.defaultRequestConfig = defaultRequestConfig;
    }

    public boolean isEvictExpiredConnections() {
        return evictExpiredConnections;
    }

    public void setEvictExpiredConnections(boolean evictExpiredConnections) {
        this.evictExpiredConnections = evictExpiredConnections;
    }

    public boolean isEvictIdleConnections() {
        return evictIdleConnections;
    }

    public void setEvictIdleConnections(boolean evictIdleConnections) {
        this.evictIdleConnections = evictIdleConnections;
    }

    public long getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public TimeUnit getMaxIdleTimeUnit() {
        return maxIdleTimeUnit;
    }

    public void setMaxIdleTimeUnit(TimeUnit maxIdleTimeUnit) {
        this.maxIdleTimeUnit = maxIdleTimeUnit;
    }

    public boolean isSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(boolean systemProperties) {
        this.systemProperties = systemProperties;
    }

    public boolean isRedirectHandlingDisabled() {
        return redirectHandlingDisabled;
    }

    public void setRedirectHandlingDisabled(boolean redirectHandlingDisabled) {
        this.redirectHandlingDisabled = redirectHandlingDisabled;
    }

    public boolean isAutomaticRetriesDisabled() {
        return automaticRetriesDisabled;
    }

    public void setAutomaticRetriesDisabled(boolean automaticRetriesDisabled) {
        this.automaticRetriesDisabled = automaticRetriesDisabled;
    }

    public boolean isContentCompressionDisabled() {
        return contentCompressionDisabled;
    }

    public void setContentCompressionDisabled(boolean contentCompressionDisabled) {
        this.contentCompressionDisabled = contentCompressionDisabled;
    }

    public boolean isCookieManagementDisabled() {
        return cookieManagementDisabled;
    }

    public void setCookieManagementDisabled(boolean cookieManagementDisabled) {
        this.cookieManagementDisabled = cookieManagementDisabled;
    }

    public boolean isAuthCachingDisabled() {
        return authCachingDisabled;
    }

    public void setAuthCachingDisabled(boolean authCachingDisabled) {
        this.authCachingDisabled = authCachingDisabled;
    }

    public boolean isConnectionStateDisabled() {
        return connectionStateDisabled;
    }

    public void setConnectionStateDisabled(boolean connectionStateDisabled) {
        this.connectionStateDisabled = connectionStateDisabled;
    }

    public int getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public long getConnTimeToLive() {
        return connTimeToLive;
    }

    public void setConnTimeToLive(long connTimeToLive) {
        this.connTimeToLive = connTimeToLive;
    }

    public TimeUnit getConnTimeToLiveTimeUnit() {
        return connTimeToLiveTimeUnit;
    }

    public void setConnTimeToLiveTimeUnit(TimeUnit connTimeToLiveTimeUnit) {
        this.connTimeToLiveTimeUnit = connTimeToLiveTimeUnit;
    }

    public List<Closeable> getCloseables() {
        return closeables;
    }

    public void setCloseables(List<Closeable> closeables) {
        this.closeables = closeables;
    }

    public PublicSuffixMatcher getPublicSuffixMatcher() {
        return publicSuffixMatcher;
    }

    public void setPublicSuffixMatcher(PublicSuffixMatcher publicSuffixMatcher) {
        this.publicSuffixMatcher = publicSuffixMatcher;
    }
}
