package cn.bumblebee.spider.selenium;

import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.WebClientProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.Callable;


public class SeleniumTaskSpider implements Callable<String>{
    private String url;
    private WebClientProcessor<WebClient<String>, String> processor;
    private WebClient<String> webClient;

    public SeleniumTaskSpider(String url, WebClientProcessor<WebClient<String>, String> processor) {
        this.url = url;
        this.webClient = new WebClient<>();
        webClient.setWebDriver(new PhantomJSDriver());
        this.processor = processor;
    }

    public SeleniumTaskSpider(String url, WebClientProcessor<WebClient<String>, String> processor, WebClientConfig webClientConfig ) {
        this.url = url;
        this.webClient = new WebClient<>();
        WebDriver webDriver = null;
        if (webClientConfig.getDesiredCapabilities() != null) {
            if (!StringUtils.isEmpty(webClientConfig.getProxyIp())) {
                Proxy proxy = new Proxy();
                proxy.setProxyType(Proxy.ProxyType.MANUAL);
                proxy.setAutodetect(false);
                proxy.setHttpProxy(webClientConfig.getProxyIp());
                DesiredCapabilities desiredCapabilities = webClientConfig.getDesiredCapabilities();
                desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
                webClientConfig.setDesiredCapabilities(desiredCapabilities);
            }
            webDriver = new PhantomJSDriver(webClientConfig.getDesiredCapabilities());
        } else {
            if (!StringUtils.isEmpty(webClientConfig.getProxyIp())) {
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                Proxy proxy = new Proxy();
                proxy.setProxyType(Proxy.ProxyType.MANUAL);
                proxy.setAutodetect(false);
                proxy.setHttpProxy(webClientConfig.getProxyIp());
                desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
                webDriver = new PhantomJSDriver(desiredCapabilities);
            } else {
                webDriver = new PhantomJSDriver();
            }
        }

        if (! CollectionUtils.isEmpty(webClientConfig.getCookies())) {
            for (Cookie cookie: webClientConfig.getCookies()) {
                webDriver.manage().addCookie(cookie);
            }
        }
        webClient.setWebDriver(webDriver);
        this.processor = processor;
    }

    public String run() {
        WebDriver webDriver = webClient.getWebDriver();
        webDriver.get(url);
        webClient.setRequest(url);
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        webClient.setHtmlOrJson(webElement.getAttribute("outerHTML"));
        processor.process(webClient);
        return webDriver.getPageSource();
    }


    @Override
    public String call() throws Exception {
        return run();
    }

    public void close() {
        webClient.getWebDriver().quit();
    }
}
