package cn.bumblebee.processer;

public interface HttpProcessor<T> {
    T process(T t);
}
