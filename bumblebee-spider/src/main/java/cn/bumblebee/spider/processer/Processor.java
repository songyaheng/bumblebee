package cn.bumblebee.spider.processer;

public interface Processor<T, R> {
    R process(T t);
}
