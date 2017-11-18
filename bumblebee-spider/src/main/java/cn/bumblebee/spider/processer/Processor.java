package cn.bumblebee.spider.processer;

public interface Processor<E, T> {
    T process(E e);
}
