package cn.bumblebee.spider.service;

import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.*;

public abstract class AsynService<T, R> {
    private final BlockingQueue<T> queue;
    private ExecutorService pool;
    private int batchSize;
    private long timeout;
    private int nThreads;

    public AsynService(int batchSize, long timeout, int nThreads) {
        this.batchSize = batchSize;
        this.timeout = timeout;
        this.nThreads = nThreads;
        this.queue = new LinkedBlockingQueue<T>();
        pool = Executors.newFixedThreadPool(this.nThreads);
    }

    public AsynService(int queueSize, int batchSize, long timeout, int nThreads) {
        this.batchSize = batchSize;
        this.timeout = timeout;
        this.nThreads = nThreads;
        this.queue = new ArrayBlockingQueue<T>(queueSize);
        pool = Executors.newFixedThreadPool(this.nThreads);
    }

    public void put(T t) {
        try {
            this.queue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void add(T t) {
        this.queue.add(t);
    }

    public void start(){
        Thread worker = new Thread(new Runnable() {

            final List<T> ts = new ArrayList<T>(batchSize);

            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                while(true){
                    try {
                        Queues.drain(queue, ts, batchSize, timeout, TimeUnit.MILLISECONDS);
                        if(!ts.isEmpty()){
                            final List<T> copyArray = (List<T>) Arrays.asList(Arrays.copyOf(ts.toArray(), ts.size()));
                            pool.submit(new Runnable() {
                                @Override
                                public void run() {
                                    execute(copyArray);
                                }
                            });
                        }
                        ts.clear();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        worker.start();
    }


    public abstract R execute(List<T> list);

    public void shutDown() {
        if (queue.size() > 0) {
            try {
                Thread.sleep(timeout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }
}
