package cn.bumblebee.spider;

import cn.bumblebee.spider.commens.SpiderExecutor;
import cn.bumblebee.spider.commens.TaskSpider;
import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.modle.PageHtml;
import cn.bumblebee.spider.processer.BuildRequestProcessor;
import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.processer.Processor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractSpiderExecutorService<T extends HttpRequestBase, R> {
    private SpiderExecutor<T, R> spiderExecutor;
    private final BlockingQueue<TaskSpider<T, R>> queue;
    private int sleppTime = 100;
    private int nThread = 1;

    public AbstractSpiderExecutorService(int sleppTime) {
        this.sleppTime = sleppTime;
        this.queue = new LinkedBlockingDeque<>();
        this.spiderExecutor = newInstance(this.nThread, this.sleppTime);
    }

    public AbstractSpiderExecutorService(int nThread, int sleppTime) {
        this.nThread = nThread;
        this.sleppTime = sleppTime;
        this.queue = new LinkedBlockingDeque<>();
        this.spiderExecutor = newInstance(this.nThread, this.sleppTime);
    }

    /**
     * 执行实例化执行器
     * @param nThread
     * @param sleppTime
     * @return
     */
    private SpiderExecutor<T, R> newInstance(int nThread, int sleppTime) {
        return new SpiderExecutor(nThread, sleppTime, new CallableReturn<TaskSpider<T, R>>() {
            @Override
            public TaskSpider<T, R> take() {
                try {
                    return queue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

    public abstract Processor<PageHtml<T>, R> getProcessor();

    public abstract ClientConfig getClientConfig();


    /**
     * 放入队列TASK
     * @param taskSpider
     */
    public void put(TaskSpider<T, R> taskSpider) {
        try {
            queue.put(taskSpider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 完全自定义请求处理以及ClientConfig
     * @param buildRequestProcessor
     */
    public void put(BuildRequestProcessor<T, R> buildRequestProcessor) {
        TaskSpider<T, R> trTaskSpider = new TaskSpider<T, R>(buildRequestProcessor.buildRequest(),
                buildRequestProcessor.buildProcessor());
        trTaskSpider.setClientConfig(buildRequestProcessor.buildClientConfig());
        try {
            queue.put(trTaskSpider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一切都是默认的逻辑，要求实现的抽象方法不返回null
     * @param url
     * @param cls
     */
    public void put(String url, Class<T> cls) {
        if (StringUtils.isEmpty(url.trim())) {
            return;
        }
        try {
            T t = cls.newInstance();
            t.setURI(new URI(url));
            TaskSpider<T, R> trTaskSpider = new TaskSpider<T, R>(t, getProcessor());
            ClientConfig clientConfig = getClientConfig();
            if (clientConfig == null) {
                clientConfig = new ClientConfig();
            }
            trTaskSpider.setClientConfig(clientConfig);
            queue.put(trTaskSpider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置处理逻辑
     * @param url
     * @param cls
     * @param processor
     */
    public void put(String url, Class<T> cls, Processor<PageHtml<T>, R> processor) {
        if (StringUtils.isEmpty(url.trim())) {
            return;
        }
        try {
            T t = cls.newInstance();
            TaskSpider<T, R> trTaskSpider = new TaskSpider<T, R>(t, processor);
            queue.put(trTaskSpider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 从队列中取出TASK
     * @return
     * @throws InterruptedException
     */
    public TaskSpider<T, R> take() throws InterruptedException {
        return queue.take();
    }

    /**
     * 启动多线程爬虫
     */
    public void run() {
        spiderExecutor.start();
    }

    /**
     * 启动并行任务执行
     */
    public void runAsyn() {
        spiderExecutor.startAsyn();
    }


    /**
     * 关闭所有线程
     */
    public void shutdown() {
        spiderExecutor.shutdown();
    }
}
