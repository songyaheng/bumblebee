package cn.bumblebee.spider.utils;

import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.processer.HttpProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Webutils {

    public static final String BASE_CHARSET = "utf-8";

    /**
     * 直接下载json数据
     * @param url
     * @return
     */
    public static String jsonLoad(String url) {
        StringBuffer json = new StringBuffer();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     *  传入httpRequestBase和httpClient来获取数据
     * @param httpRequestBase
     * @param httpClient
     * @return
     */
    public static String loadDataFromHttp(HttpRequestBase httpRequestBase, HttpClient httpClient) {
        return loadDataFromHttp(httpRequestBase, httpClient, null);
    }

    /**
     * 默认情况自动产生CLIENT
     * @param httpRequestBase
     * @return
     */
    public static String loadDataFromHttp(HttpRequestBase httpRequestBase) {
        return loadDataFromHttp(httpRequestBase, getClient(null), null);
    }

    /**
     * 从HTTP上LOAD数据，并做编码转换
     * @param httpRequestBase
     * @param httpClient
     * @param charset
     * @return
     */
    public static String loadDataFromHttp(HttpRequestBase httpRequestBase, HttpClient httpClient, String charset) {
        String html = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpRequestBase);
            html = buildWithCharset(httpResponse, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    /**
     * 处理编码问题
     * @param httpResponse
     * @param charset
     * @return
     */
    public static String buildWithCharset(HttpResponse httpResponse, String charset) {
        try {
            if (StringUtils.isEmpty(charset)) {
                return EntityUtils.toString(httpResponse.getEntity(), BASE_CHARSET);
            } else {
                return EntityUtils.toString(httpResponse.getEntity(), charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 初始化CLIENT
     * @param clientConfig
     * @return
     */
    public static CloseableHttpClient getClient(ClientConfig clientConfig) {
        if (clientConfig == null) {
            clientConfig = new ClientConfig();
        }
        Field[] fields = clientConfig.getClass().getDeclaredFields();
        ClientUtils clientUtils = ClientUtils.newInstance();
        for (Field field: fields) {
            try {
                field.setAccessible(true);
                Object o = field.get(clientConfig);
                if (o != null) {
                    clientUtils.with(o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return clientUtils.get();
    }

    /**
     * 用于生成请求
     * @param url
     * @param cls
     * @param httpProcessor
     * @param <T>
     * @return
     */
    public static <T extends HttpRequestBase> T getRequest(String url, Class<T> cls, HttpProcessor<T> httpProcessor) {
        T t = null;
        try {
            t = cls.newInstance();
            if (StringUtils.isEmpty(url)) {
                return t;
            }
            t.setURI(URI.create(url));
            return httpProcessor.process(t);
        } catch (Exception e) {
            e.printStackTrace();
            return t;
        }
    }

    /**
     * 带参数URL生成
     * @param url
     * @param parameer
     * @return
     */
    public static String buildUrl(String url, Map<String, String> parameer) {
        StringBuffer sb = new StringBuffer(url);
        sb.append("?");
        List<String> par = new ArrayList<>();
        try {
            StringBuffer ssb = new StringBuffer();
            for (Map.Entry entry: parameer.entrySet()) {
                ssb.append(URLEncoder.encode(entry.getKey().toString(), "UTF-8"));
                ssb.append("=");
                ssb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                par.add(ssb.toString());
                ssb.delete(0, ssb.length());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        sb.append(StringUtils.join(par, '&'));
        return sb.toString();
    }
}
