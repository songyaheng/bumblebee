package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.modle.PageHtml;
import cn.bumblebee.spider.processer.HtmlProcessor;
import cn.bumblebee.spider.processer.JsonProcesser;
import cn.bumblebee.spider.processer.Processor;
import cn.bumblebee.spider.utils.Webutils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.concurrent.Callable;

/**
 * 一个真实的蜘蛛单元
 * @param <T>
 */
public class TaskSpider<T extends HttpRequestBase, R> implements Callable<R>{
    private ClientConfig clientConfig = new ClientConfig();
    private T t;
    private String charSet = Webutils.BASE_CHARSET;
    private Processor<PageHtml<T>, R> processor;

    public TaskSpider(T t, Processor<PageHtml<T>, R> processor) {
        this.t = t;
        this.processor = processor;
    }

    public TaskSpider(T t, String charSet, Processor<PageHtml<T>, R> processor) {
        this.t = t;
        this.charSet = charSet;
        this.processor = processor;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public Processor<PageHtml<T>, R> getProcessor() {
        return processor;
    }

    public void setProcessor(Processor<PageHtml<T>, R> processor) {
        this.processor = processor;
    }

    public R run() {
        if (processor instanceof HtmlProcessor) {
            try {
                CloseableHttpResponse closeableHttpResponse = Webutils.getClient(clientConfig).execute(t);
                String html = Webutils.buildWithCharset(closeableHttpResponse, this.charSet);
                PageHtml<T> pageHtml = new PageHtml<T>();
                pageHtml.setHtmlOrJson(html);
                pageHtml.setRequest(t);
                return processor.process(pageHtml);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (processor instanceof JsonProcesser){
            String url = t.getURI().toString();
            String json = Webutils.jsonLoad(url);
            PageHtml<T> pageHtml = new PageHtml<T>();
            pageHtml.setHtmlOrJson(json);
            pageHtml.setRequest(t);
            return processor.process(pageHtml);
        } else {
            return null;
        }
    }

    @Override
    public R call() throws Exception {
        return run();
    }
}
