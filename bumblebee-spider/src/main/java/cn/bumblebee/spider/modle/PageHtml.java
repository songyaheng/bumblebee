package cn.bumblebee.spider.modle;

import org.apache.http.client.methods.HttpRequestBase;

public class PageHtml<Q> {

    private Q q;
    private String htmlOrJson;

    public Q getRequest() {
        return q;
    }

    public void setRequest(Q request) {
        this.q = request;
    }

    public String getHtmlOrJson() {
        return htmlOrJson;
    }

    public void setHtmlOrJson(String htmlOrJson) {
        this.htmlOrJson = htmlOrJson;
    }
}
