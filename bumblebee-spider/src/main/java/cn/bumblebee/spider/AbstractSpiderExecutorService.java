package cn.bumblebee.spider;

import cn.bumblebee.spider.commens.SpiderExecutor;
import cn.bumblebee.spider.commens.TaskSpider;
import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.processer.CollectionProcessor;
import org.apache.http.client.methods.HttpRequestBase;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public abstract class AbstractSpiderExecutorService<T extends HttpRequestBase, R> {
    private SpiderExecutor<T, R> spiderExecutor;
    private final BlockingQueue<TaskSpider<T, R>> queue;
    private int queueSize = 1000;
    private int sleppTime = 100;
    private int nThread = 1;
    private int bachSIze = 10;



    public AbstractSpiderExecutorService(int queueSize, int sleppTime) {
        this.queueSize = queueSize;
        this.sleppTime = sleppTime;
        this.queue = new ArrayBlockingQueue<TaskSpider<T, R>>(queueSize);
        this.spiderExecutor = newInstance(this.nThread, this.queueSize, this.bachSIze);
    }

    public AbstractSpiderExecutorService(int queueSize, int sleppTime, int bachSIze) {
        this.sleppTime = sleppTime;
        this.queueSize = queueSize;
        this.bachSIze = bachSIze;
        this.queue = new ArrayBlockingQueue<TaskSpider<T, R>>(queueSize);
        this.spiderExecutor = newInstance(this.nThread, this.sleppTime, this.bachSIze);
    }

    public AbstractSpiderExecutorService(int nThread, int queueSize, int sleppTime, int bachSIze) {
        this.nThread = nThread;
        this.sleppTime = sleppTime;
        this.queueSize = queueSize;
        this.bachSIze = bachSIze;
        this.queue = new ArrayBlockingQueue<TaskSpider<T, R>>(queueSize);
        this.spiderExecutor = newInstance(this.nThread, this.sleppTime, this.bachSIze);
    }

    /**
     * 用户自定义批处理结果或持久化方式
     * @return
     */
    public abstract CollectionProcessor<R, R> getCollectionProcessor();

    /**
     * 执行实例化执行器
     * @param nThread
     * @param sleppTime
     * @return
     */
    private SpiderExecutor<T, R> newInstance(int nThread, int sleppTime, int bachSIze) {
        return new SpiderExecutor(nThread, sleppTime, bachSIze, new CallableReturn<TaskSpider<T, R>>() {
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
        spiderExecutor.start(getCollectionProcessor());
    }

    /**
     * 关闭所有线程
     */
    public void shutdown() {
        spiderExecutor.shutdown();
    }
}
