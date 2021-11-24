package sitemapapp.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import sitemapapp.properties.Properties;
import sitemapapp.service.Node;
import sitemapapp.service.PageService;
import sitemapapp.utils.MapWritter;

import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

@Setter
@Getter
public class PageServiceImpl implements PageService {

    static Logger pageServiceImplLogger = Logger.getLogger(PageServiceImpl.class);
    private final String startUrl;
    private final String fileName;
    private final long threadSleep;

    public PageServiceImpl(String startUrl, String fileName, long threadSleep){
        this.startUrl = startUrl;
        this.fileName = fileName;
        this.threadSleep = threadSleep == 0 ? Long.parseLong(Properties.THREAD_SLEEP.getProperty()) : threadSleep;

    }

    private static void getNodes(Node node, TreeSet<String> links){
        links.add(node.getUrl());
        node.getChildren().forEach(node1 -> getNodes(node1, links));
    }

    public void getSiteMap(){

        Node site = new NodeImpl(startUrl, startUrl);
        ForkJoinPool a = new ForkJoinPool();
        a.invoke(new SiteMap(site, startUrl, threadSleep));
        TreeSet<String> links = new TreeSet<>();
        getNodes(site, links);
        pageServiceImplLogger.info("Count of links: " + links.size());
        MapWritter.writeInFile(fileName.equals("") ? startUrl.replaceAll("https?://", "") : fileName, links);
    }
}
