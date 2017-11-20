package cn.bumblebee.spider.processer;

import cn.bumblebee.spider.modle.PageHtml;

public interface HtmlProcessor<Q, R> extends Processor<PageHtml<Q>, R> {
    /**
     * 处理HTML文档
     * @param html
     */
    R process(PageHtml<Q> html);
}
