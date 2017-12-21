package cn.bumblebee.spider.modle;

import org.openqa.selenium.WebDriver;

public class WebClient<Q> extends PageHtml<Q>{
    private WebDriver webDriver;
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
