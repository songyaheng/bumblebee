package cn.bumblebee.spider.processer;

import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.modle.PageHtml;
import org.apache.http.client.methods.HttpRequestBase;

public interface BuildRequestProcessor<Q extends HttpRequestBase, R> {
    ClientConfig buildClientConfig();
    Processor<PageHtml<Q>, R> buildProcessor();
    Q buildRequest();
}
