package cn.bumblebee.spider.processer;

/**
 * @author songyaheng on 2017/11/22
 * @version 1.0
 */
public interface WebClientProcessor<T, R> {
    R process(T t);
}
