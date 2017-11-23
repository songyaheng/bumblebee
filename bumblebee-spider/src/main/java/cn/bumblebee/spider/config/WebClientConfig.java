package cn.bumblebee.spider.config;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

/**
 * @author songyaheng on 2017/11/22
 * @version 1.0
 */
public class WebClientConfig {
    private List<Cookie> cookies;
    private DesiredCapabilities desiredCapabilities;
    private String proxyIp;

    public String getProxyIp() {
        return proxyIp;
    }

    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    public void setDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }
}
