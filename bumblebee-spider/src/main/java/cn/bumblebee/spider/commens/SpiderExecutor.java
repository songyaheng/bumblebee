package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.processer.CallableReturn;
import org.apache.http.client.methods.HttpRequestBase;


public class SpiderExecutor<T extends HttpRequestBase, R> extends AbstractExecutor<TaskSpider<T, R>, R> {
    public SpiderExecutor(int nThread, int sleepTime, CallableReturn<TaskSpider<T, R>> callableReturn) {
        super(nThread, sleepTime, callableReturn);
    }
}
