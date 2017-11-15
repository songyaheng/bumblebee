package cn.bumblebee.processer;

public interface HtmlProcessor extends Processor{
    /**
     * 处理HTML文档
     * @param html
     */
    void process(String html);
}
