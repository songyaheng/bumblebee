package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSeleniumSpider;
import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.Processor;
import cn.bumblebee.spider.processer.WebClientProcessor;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

/**
 * @author songyaheng on 2017/11/22
 * @version 1.0
 */
public class SeleniumTest extends AbstractSeleniumSpider{
    @Override
    public String getUrl() {
        return "http://huaban.com/";
    }

    @Override
    public WebClientConfig getWebClientConfig() {
        return null;
    }

    @Override
    public WebClientProcessor<WebClient<String>, String> getProcessor() {
        return new WebClientProcessor<WebClient<String>, String>() {
            @Override
            public String process(WebClient<String> stringWebClient) {
                WebClient<String> webClient = stringWebClient;
                System.out.println(webClient.getHtmlOrJson());
                //拿到Webdriver你还可以做其他的事情,这里可以去看Webdriver的用法了
                WebDriver webDriver = stringWebClient.getWebDriver();
                return null;
            }
        };
    }

    public static void main(String[] args) {
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/songyaheng/Downloads/软件/phantomjs");
        SeleniumTest seleniumTest = new SeleniumTest();
        String html = seleniumTest.run();
        System.out.println(html);
    }
}
