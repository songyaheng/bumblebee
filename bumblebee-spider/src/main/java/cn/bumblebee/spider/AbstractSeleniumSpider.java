package cn.bumblebee.spider;

import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.WebClientProcessor;
import cn.bumblebee.spider.selenium.SeleniumTaskSpider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 但任务爬虫执行器
 * @author songyaheng on 2017/11/22
 * @version 1.0
 */
public abstract class AbstractSeleniumSpider {
    private SeleniumTaskSpider seleniumTaskSpider;

    public AbstractSeleniumSpider() {
        WebClientConfig webClientConfig = getWebClientConfig();
        if (webClientConfig != null) {
            this.seleniumTaskSpider =
                    new SeleniumTaskSpider(getUrl(), getProcessor(), webClientConfig);
        } else {
            this.seleniumTaskSpider =
                    new SeleniumTaskSpider(getUrl(), getProcessor());
        }
    }

    /**
     * 设置需要爬取的页面URL
     * @return
     */
    public abstract String getUrl();

    /**
     * 设置WEB驱动的配置
     * @return
     */
    public abstract WebClientConfig getWebClientConfig();

    /**
     * 实现需要处理的方法
     * @return
     */
    public abstract WebClientProcessor<WebClient<String>, String> getProcessor();

    /**
     * 启动执行后关闭任务
     * @return
     */
    public String run() {
        String html = seleniumTaskSpider.run();
        seleniumTaskSpider.close();
        return html;
    }
}
