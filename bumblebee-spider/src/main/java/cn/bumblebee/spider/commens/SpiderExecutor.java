package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.service.AsynService;
import com.google.common.collect.Queues;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SpiderExecutor<T extends HttpRequestBase, R> {
    private CallableReturn<TaskSpider<T , R>> callableReturn;
    private ExecutorService executorService;
    private CompletionService<R> service;
    private int nThread = 1;
    private int sleepTime = 100;
    private Thread work;

    public SpiderExecutor(int nThread, int sleepTime, CallableReturn<TaskSpider<T, R>> callableReturn) {
        this.nThread = nThread;
        this.sleepTime = sleepTime;
        this.callableReturn = callableReturn;
        executorService = Executors.newFixedThreadPool(this.nThread);
    }

    /**
     * 启动并行爬虫线程
     */
    public void startAsyn() {
        this.service = new ExecutorCompletionService<>(this.executorService);
        this.work = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    TaskSpider<T, R> taskSpider = callableReturn.take();
                    if (taskSpider != null) {
                        service.submit(taskSpider);
                        count ++;
                        if (count >= nThread) {
                            for (int i = 0; i < count; i ++) {
                                try {
                                    service.take().get();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            count = 0;
                        }
                    } else {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.work.start();
    }

    /**
     * 启动多线程穿行爬虫
     */
    public void start() {
        this.work = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    TaskSpider<T, R> taskSpider = callableReturn.take();
                    if (taskSpider != null) {
                        try {
                            executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        taskSpider.call();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        count ++;
                        if (count >= nThread) {
                            count = 0;
                            try {
                                Thread.sleep(sleepTime);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.work.start();
    }

    /**
     * 关闭所有从线程
     */
    public void shutdown() {
        if (this.work != null) {
            this.work.interrupt();
        }
        if (this.executorService != null) {
            executorService.shutdown();
        }
    }

}
