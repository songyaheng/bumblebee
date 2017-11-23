package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSeleniumExecuterService;
import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.WebClientProcessor;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

/**
 * 爬取小米应用商店评论
 * @author songyaheng on 2017/11/23
 * @version 1.0
 */
public class MultiSeleniumTest extends AbstractSeleniumExecuterService{
    public MultiSeleniumTest() {
        super(3, 1000);
    }

    @Override
    public WebClientConfig getWebClientConfig() {
        return null;
    }

    @Override
    public WebClientProcessor<WebClient<String>, String> getWebClientProcessor() {
        return new WebClientProcessor<WebClient<String>, String>() {
            @Override
            public String process(WebClient<String> stringWebClient) {
                String html = stringWebClient.getHtmlOrJson();
                System.out.println(Thread.currentThread().getName());
                System.out.println(html);
                return null;
            }
        };
    }

    public static void main(String[] args) {
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/songyaheng/Downloads/软件/phantomjs");
        MultiSeleniumTest multiSeleniumTest = new MultiSeleniumTest();
        multiSeleniumTest.run(); //多线程
//        multiSeleniumTest.runAsyn(); // 并行
        multiSeleniumTest.put("http://huaban.com/");
        multiSeleniumTest.put("http://huaban.com/");
        multiSeleniumTest.put("http://huaban.com/");
    }
}
