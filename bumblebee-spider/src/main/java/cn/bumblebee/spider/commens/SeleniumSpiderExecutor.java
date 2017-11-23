package cn.bumblebee.spider.commens;

import cn.bumblebee.spider.processer.CallableReturn;
import cn.bumblebee.spider.selenium.SeleniumTaskSpider;


/**
 * @author songyaheng on 2017/11/23
 * @version 1.0
 */
public class SeleniumSpiderExecutor extends AbstractExecutor<SeleniumTaskSpider, String> {
    public SeleniumSpiderExecutor(int nThread, int sleepTime, CallableReturn<SeleniumTaskSpider> callableReturn) {
        super(nThread, sleepTime, callableReturn);
    }
}
