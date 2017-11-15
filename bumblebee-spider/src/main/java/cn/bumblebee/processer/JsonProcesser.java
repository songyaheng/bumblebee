package cn.bumblebee.processer;

public interface JsonProcesser<T> extends Processor{
    /**
     *  处理json数据
     * @param json
     * @return
     */
    T process(String json);
}
