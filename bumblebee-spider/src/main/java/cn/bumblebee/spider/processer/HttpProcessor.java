package cn.bumblebee.spider.processer;

public interface HttpProcessor<T> extends Processor<T, T>{
    T process(T t);
}
