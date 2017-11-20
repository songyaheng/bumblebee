package cn.bumblebee.spider.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParserutils {
    /**
     * jsoup 解析 html
     * @param html
     * @return
     */
    public static Document jsoupParser(String html) {
        return Jsoup.parse(html);
    }

}
