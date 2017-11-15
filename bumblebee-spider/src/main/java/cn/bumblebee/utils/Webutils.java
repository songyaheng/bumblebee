package cn.bumblebee.utils;

import cn.bumblebee.processer.HttpProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

public class Webutils {

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
}
