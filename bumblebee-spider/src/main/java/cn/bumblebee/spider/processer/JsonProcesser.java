package cn.bumblebee.spider.processer;

import cn.bumblebee.spider.modle.PageHtml;

public interface JsonProcesser<Q, R> extends Processor<PageHtml<Q>, R> {
    /**
     *  处理json数据
     * @param json
     * @return
     */
    R process(PageHtml<Q> json);
}
