package cn.bumblebee.spider.processer;

public interface JsonProcesser<T> extends Processor<String, T> {
    /**
     *  处理json数据
     * @param json
     * @return
     */
    T process(String json);
}
