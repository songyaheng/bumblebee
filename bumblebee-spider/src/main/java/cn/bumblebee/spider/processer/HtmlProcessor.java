package cn.bumblebee.spider.processer;

public interface HtmlProcessor<T> extends Processor<String, T> {
    /**
     * 处理HTML文档
     * @param html
     */
    T process(String html);
}
