package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSeleniumSpider;
import cn.bumblebee.spider.config.WebClientConfig;
import cn.bumblebee.spider.modle.Login;
import cn.bumblebee.spider.modle.PageHtml;
import cn.bumblebee.spider.modle.UserPassWord;
import cn.bumblebee.spider.modle.WebClient;
import cn.bumblebee.spider.processer.HtmlProcessor;
import cn.bumblebee.spider.processer.WebClientProcessor;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.util.concurrent.TimeUnit;

/**
 * @author songyaheng on 2017/12/21
 * @version 1.0
 */
public class TestLogin extends AbstractSeleniumSpider{
    @Override
    public String getUrl() {
        return "http://host:port/login.html";
    }

    @Override
    public WebClientConfig getWebClientConfig() {
        WebClientConfig webClientConfig = new WebClientConfig();
        Login login = new Login("username", "password", "login_button");
        login.putUserNameAndPassWord("user", "passwd");
        webClientConfig.setLogin(login);
        return webClientConfig;
    }

    @Override
    public WebClientProcessor<WebClient<String>, String> getProcessor() {
        return new WebClientProcessor<WebClient<String>, String>() {
            @Override
            public String process(WebClient<String> stringWebClient) {
                WebDriver webDriver = stringWebClient.getWebDriver();
                // 等待加载完成
                webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                //获取用户登陆信息
                Login login = stringWebClient.getLogin();
                //可以随机选择一个用户
                UserPassWord userPassWord = login.getUserPassWords().get(0);
                // 根据具体的页面情况获取登陆表单
                WebElement elemUsername = webDriver.findElement(new By.ById(login.getUserNameField()));
                WebElement elemPassword = webDriver.findElement(new By.ById(login.getPassWordField()));
                //获取登陆的按钮
                WebElement btn = webDriver.findElement(new By.ById(login.getLoginButton()));
                // 设置用户名和密码
                elemUsername.sendKeys(userPassWord.getUserName());
                elemPassword.sendKeys(userPassWord.getPassWord());
                //登陆 or btn.submit();
                btn.click();
                String html = webDriver.getPageSource();
                System.out.println(html);
                return null;
            }
        };
    }

    public static void main(String[] args) {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "/Users/songyaheng/Downloads/软件/chromedriver");
        TestLogin testLogin = new TestLogin();
        testLogin.run();
    }
}
