package cn.bumblebee.test;

import cn.bumblebee.Spider;
import cn.bumblebee.config.ClientConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

public class TestSpider extends Spider<HttpPost>{

    public ClientConfig getClientConfig() {
        return null;
    }

    public HttpPost getRequest() {
        return null;
    }
}
