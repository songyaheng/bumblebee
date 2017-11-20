package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.config.ClientConfig;
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
    private Processor<String, R> processor;

    public TaskSpider(T t, Processor<String, R> processor) {
        this.t = t;
        this.processor = processor;
    }

    public TaskSpider(T t, String charSet, Processor<String, R> processor) {
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


    private R run(Processor<String, R> processor) {
        String name = processor.getClass().getName();
        if (name.equals(HtmlProcessor.class.getName())) {
            try {
                CloseableHttpResponse closeableHttpResponse = Webutils.getClient(clientConfig).execute(t);
                String html = Webutils.buildWithCharset(closeableHttpResponse, this.charSet);
                return processor.process(html);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else if (name.equals(JsonProcesser.class.getName())){
            String url = t.getURI().toString();
            String json = Webutils.jsonLoad(url);
            return processor.process(json);
        } else {
            return null;
        }
    }

    @Override
    public R call() throws Exception {
        return run(processor);
    }
}
