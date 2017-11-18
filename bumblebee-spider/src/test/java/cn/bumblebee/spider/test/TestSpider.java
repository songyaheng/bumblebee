package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSpider;
import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.processer.HtmlProcessor;
import cn.bumblebee.spider.processer.Processor;
import org.apache.http.client.methods.HttpPost;

public class TestSpider extends AbstractSpider<HttpPost,String> {

    public TestSpider(String charSet) {
        super(charSet);
    }

    @Override
    public ClientConfig getClientConfig() {
        return null;
    }

    @Override
    public HttpPost getRequest() {
        return new HttpPost("url");
    }

    @Override
    public Processor<String, String> getProcessor() {
        return new HtmlProcessor<String>() {
            @Override
            public String process(String html) {
                return null;
            }
        };
    }
}
