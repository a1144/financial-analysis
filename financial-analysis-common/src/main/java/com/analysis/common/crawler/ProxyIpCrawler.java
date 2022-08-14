package com.analysis.common.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lvshuzheng
 * @className IPCrawler
 * @description
 * @date 2020/4/22
 */
public class ProxyIpCrawler extends CrawlerWrapper{
    @Override
    public Object analysis(Object object) {
        Document document = (Document) object;
        Elements trElements = document.getElementsByClass("layui-table").get(0).getElementsByTag("tr");
        Set<String> ipSet = new HashSet<>();
        //遍历所有tr
        trElements.stream().forEach(element -> {
            if(element.getElementsByTag("button").size() != 0){
                ipSet.add(element.getElementsByTag("button").get(0).attr("data-url"));
            }
        });
        return ipSet;
    }
    public static void main1(String args[]){
        CrawlerWrapper crawler = new ProxyIpCrawler();
        Elements trElements = crawler.crawling("https://ip.jiangxianli.com/?page=5&country=%E4%B8%AD%E5%9B%BD").getElementsByClass("layui-table").get(0).getElementsByTag("tr");
        System.out.println(crawler.crawling("https://ip.jiangxianli.com/?page=5&country=%E4%B8%AD%E5%9B%BD").getElementsByClass("layui-table").get(0).getElementsByTag("tr").getClass());
        /*System.out.println(crawler.crawling("https://ip.jiangxianli.com/?page=5&country=%E4%B8%AD%E5%9B%BD").
                getElementsByClass("layui-table").get(0).getElementsByTag("tr").get(0));*/
        for(int i = 1; i < trElements.size();i++){
            Element trElement = trElements.get(i);
            //System.out.println(trElement);
            System.out.println("url: " + trElement.getElementsByTag("button").get(0).attr("data-url"));
        }
        //redisUtil.sSet()

    }
}
