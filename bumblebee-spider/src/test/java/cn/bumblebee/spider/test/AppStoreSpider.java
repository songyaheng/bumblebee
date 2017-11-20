package cn.bumblebee.spider.test;

import cn.bumblebee.spider.AbstractSpider;
import cn.bumblebee.spider.config.ClientConfig;
import cn.bumblebee.spider.modle.PageHtml;
import cn.bumblebee.spider.processer.HtmlProcessor;
import cn.bumblebee.spider.processer.Processor;
import org.apache.http.client.methods.HttpGet;

/**
 * 爬取苹果app store 某个app的评论
 */
public class AppStoreSpider extends AbstractSpider<HttpGet,String> {

    public AppStoreSpider() {
        super("utf-8");
    }

    @Override
    public ClientConfig getClientConfig() {
        return null;
    }

    @Override
    public HttpGet getRequest() {
        HttpGet httpGet = new HttpGet("https://itunes.apple.com/WebObjects/MZStore.woa/wa/userReviewsRow?cc=cn&id=923920872&displayable-kind=11&startIndex=0&endIndex=100&sort=0&appVersion=all");
        httpGet.setHeader("User-Agent", "iTunes/11.0 (Windows; Microsoft Windows 7 Business Edition Service Pack 1 (Build 7601)) AppleWebKit/536.27.1");
        return httpGet;
    }

    @Override
    public Processor<PageHtml<HttpGet>, String> getProcessor() {
        return new HtmlProcessor<HttpGet, String>() {
            @Override
            public String process(PageHtml<HttpGet> html) {
                System.out.println(html.getHtmlOrJson());
                return null;
            }
        };
    }


    public static void main(String[] args) {
        AppStoreSpider appStoreSpider = new AppStoreSpider();
        appStoreSpider.run();
    }
}
