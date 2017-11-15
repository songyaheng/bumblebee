package cn.bumblebee;

import cn.bumblebee.config.ClientConfig;
import cn.bumblebee.processer.Processor;
import cn.bumblebee.utils.ClientUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;

import java.lang.reflect.Field;

public abstract class Spider<T extends HttpRequestBase>{

    private CloseableHttpClient closeableHttpClient;

    /**
     * 设置ClientConfig，包括COOKIE等内容
     * @return
     */
    public abstract ClientConfig getClientConfig();

    /**
     * 获取查询的Request
     * @return
     */
    public abstract T getRequest();

    /**
     * 启动爬取
     * @return
     */
    public void run(Processor processor) {

    }

    /**
     * 根据配置初始化引擎
     */
    private void initClient() {
        ClientConfig clientConfig = getClientConfig();
        Field[] fields = clientConfig.getClass().getDeclaredFields();
        ClientUtils clientUtils = ClientUtils.newInstance();
        for (Field field: fields) {
            try {
                Object o = field.get(clientConfig);
                if (o != null) {
                    clientUtils.with(o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        closeableHttpClient =  clientUtils.get();
    }
}
