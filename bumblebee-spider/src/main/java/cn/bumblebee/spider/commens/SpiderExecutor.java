package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.processer.CollectionProcessor;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SpiderExecutor<T extends HttpRequestBase, R> {
    private CallableReturn<TaskSpider<T , R>> callableReturn;
    private ExecutorService executorService;
    private int nThread = 1;
    private int sleepTime = 100;
    private int bachSize = 10;
    private Thread work;

    public SpiderExecutor(int nThread, int sleepTime, int bachSize, CallableReturn<TaskSpider<T, R>> callableReturn) {
        this.nThread = nThread;
        this.sleepTime = sleepTime;
        this.bachSize = bachSize;
        this.callableReturn = callableReturn;
        executorService = Executors.newFixedThreadPool(this.nThread);
    }

    public void start(CollectionProcessor<R, R> collectionProcessor) {
        this.work = new Thread(new Runnable() {
            final List<FutureTask<R>> list = new ArrayList<>();
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    try {
                        Thread.sleep(sleepTime);
                        TaskSpider<T, R> taskSpider = callableReturn.take();
                        FutureTask<R> futureTask = new FutureTask<R>(taskSpider);
                        executorService.submit(futureTask);
                        list.add(futureTask);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    count ++;
                    if (count >= bachSize) {
                        count = 0;
                        collectionProcessor.process(list.stream()
                                .map(rFutureTask -> {
                                    try {
                                        return rFutureTask.get();
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                })
                                .collect(Collectors.toList()));
                        list.clear();
                    }
                }
            }
        });
        this.work.start();
    }

    public void shutdown() {
        this.work.interrupt();
        try {
            Thread.sleep(1000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
