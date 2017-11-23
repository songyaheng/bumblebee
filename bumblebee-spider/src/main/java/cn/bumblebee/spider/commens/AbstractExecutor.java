package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.processer.CallableReturn;

import java.util.concurrent.*;

/**
 * @author songyaheng on 2017/11/23
 * @version 1.0
 */
public abstract class AbstractExecutor<T extends Callable<R>, R> {
    private CallableReturn<T> callableReturn;
    private ExecutorService executorService;
    private CompletionService<R> service;
    private int nThread = 1;
    private int sleepTime = 100;
    private Thread work;

    public AbstractExecutor(int nThread, int sleepTime, CallableReturn<T> callableReturn) {
        this.nThread = nThread;
        this.sleepTime = sleepTime;
        this.callableReturn = callableReturn;
        executorService = Executors.newFixedThreadPool(this.nThread);
    }

    /**
     * 启动并行爬虫线程
     */
    public void startAsyn() {
        if (service == null) {
            this.service = new ExecutorCompletionService<>(this.executorService);
        }
        this.work = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    T task = callableReturn.take();
                    if (task != null) {
                        service.submit(task);
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
                    T task = callableReturn.take();
                    if (task != null) {
                        try {
                            executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        task.call();
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
