package cn.bumblebee.spider;

import cn.bumblebee.spider.commens.SeleniumSpiderExecutor;
import cn.bumblebee.spider.commens.SpiderExecutor;
import cn.bumblebee.spider.commens.TaskSpider;
import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.processer.WebClientProcessor;
import cn.bumblebee.spider.selenium.SeleniumTaskSpider;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 多任务爬虫执行器
 * @author songyaheng on 2017/11/22
 * @version 1.0
 */
public abstract class AbstractSeleniumExecuterService {
    private SeleniumSpiderExecutor seleniumSpiderExecutor;
    private final BlockingQueue<String> queue;
    private int sleppTime = 100;
    private int nThread = 1;
    private WebClientConfig webClientConfig;
    private WebClientProcessor<WebClient<String>, String> webClientProcessor;

    public AbstractSeleniumExecuterService(int sleppTime) {
        this.sleppTime = sleppTime;
        this.queue = new LinkedBlockingDeque<>();
        this.seleniumSpiderExecutor = newInstance(this.nThread, this.sleppTime);
        init();
    }

    public AbstractSeleniumExecuterService(int nThread, int sleppTime) {
        this.nThread = nThread;
        this.sleppTime = sleppTime;
        this.queue = new LinkedBlockingDeque<>();
        this.seleniumSpiderExecutor = newInstance(this.nThread, this.sleppTime);
        init();
    }

    /**
     * 获取webdriver的配置信息，cookie以及heder代理等
     * @return
     */
    public abstract WebClientConfig getWebClientConfig();

    /**
     * 获取处理信息
     * @return
     */
    public abstract WebClientProcessor<WebClient<String>, String> getWebClientProcessor();

    /**
     * 初始化配置和处理
     */
    private void init() {
        this.webClientConfig = getWebClientConfig();
        this.webClientProcessor = getWebClientProcessor();
    }

    /**
     * 实例化任务
     * @param nThread
     * @param sleppTime
     * @return
     */
    private SeleniumSpiderExecutor newInstance(int nThread, int sleppTime) {
        return new SeleniumSpiderExecutor(nThread, sleppTime, new CallableReturn<SeleniumTaskSpider>() {
            @Override
            public SeleniumTaskSpider take() {
                try {
                    String url = queue.take();
                    return buildSpiderTask(url);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    /**
     * 构建任务
     * @param url
     * @return
     */
    private SeleniumTaskSpider buildSpiderTask(String url) {
        if (this.webClientConfig == null) {
            return new SeleniumTaskSpider(url, this.webClientProcessor);
        } else {
            return new SeleniumTaskSpider(url, this.webClientProcessor, this.webClientConfig);
        }
    }

    /**
     * 把要爬取的地址放入队列
     * @param url
     */
    public void put(String url) {
        if (StringUtils.isEmpty(url.trim())) {
            return;
        }
        try {
            queue.put(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 启动多线程爬取
     */
    public void run() {
        this.seleniumSpiderExecutor.start();
    }

    /**
     * 启动并行爬取
     */
    public void runAsyn() {
        this.seleniumSpiderExecutor.startAsyn();
    }

    /**
     * 关闭任务以及浏览器drivver
     */
    public void shutdown() {
        this.seleniumSpiderExecutor.shutdown();
    }

}
