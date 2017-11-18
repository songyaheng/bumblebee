package cn.bumblebee.spider.processer;

import java.util.Collection;

public interface CollectionProcessor<T, R> {
    R process(Collection<T> tCollection);
}
