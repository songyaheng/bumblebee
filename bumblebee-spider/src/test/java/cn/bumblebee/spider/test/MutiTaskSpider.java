package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSpiderExecutorService;
import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.modle.PageHtml;
import cn.bumblebee.spider.processer.JsonProcesser;
import cn.bumblebee.spider.processer.Processor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.methods.HttpGet;

import java.util.concurrent.atomic.AtomicInteger;

public class MutiTaskSpider extends AbstractSpiderExecutorService<HttpGet, String>{

    public MutiTaskSpider() {
        super(3, 1000);
    }

    private static final String baseUrl = "http://miassist.app.xiaomi.com/v3/getComments?appId=74172&ps=1&page=";

    private AtomicInteger atomicInteger = new AtomicInteger(1);


    @Override
    public Processor<PageHtml<HttpGet>, String> getProcessor() {
        return new JsonProcesser<HttpGet, String>() {
            @Override
            public String process(PageHtml<HttpGet> json) {
                int page = atomicInteger.incrementAndGet();
                System.out.println(Thread.currentThread().getName());
                String js = json.getHtmlOrJson();
                JSONObject jo = JSON.parseObject(js);
                JSONArray ja = jo.getJSONArray("list");
                ja.forEach(o -> {
                    String content = ((JSONObject) o).getString("comment");
                    String r = StringEscapeUtils.unescapeHtml4(content);
                    System.out.println(r);
                });
                put(baseUrl + page, HttpGet.class);
                return null;
            }
        };
    }

    @Override
    public ClientConfig getClientConfig() {
        return null;
    }

    public static void main(String[] args) {
        MutiTaskSpider mutiTaskSpider = new MutiTaskSpider();
        mutiTaskSpider.run();
        mutiTaskSpider.put("http://miassist.app.xiaomi.com/v3/getComments?appId=74172&ps=1&page=", HttpGet.class);
    }
}
