package sitemapapp.service.impl;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import sitemapapp.properties.Properties;
import sitemapapp.service.Node;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

@Getter
public class SiteMap extends RecursiveAction {

    static Logger siteMapLogger = Logger.getLogger(SiteMap.class);
    private final Node node;
    private final String startUrl;
    private final long threadSleep;
    public SiteMap(Node node, String startUrl, long threadSleep) {
        this.node = node;
        this.startUrl = startUrl;
        this.threadSleep = threadSleep;
    }

    public SiteMap(Node node, String startUrl) {
        this.node = node;
        this.startUrl = startUrl;
        this.threadSleep = 0;
    }

    @SneakyThrows
    @Override
    protected void compute() {
        Set<SiteMap> subTasks = new CopyOnWriteArraySet<>();
        this.node.getChilds().forEach(child -> {
            SiteMap task = new SiteMap(child, startUrl);
            try {
                Thread.sleep(threadSleep == 0 ? Long.parseLong(Properties.THREAD_SLEEP.getProperty()) : threadSleep);
            } catch (InterruptedException e){
                siteMapLogger.error(e.getMessage());
            }
            task.fork();
            subTasks.add(task);
        });
        subTasks.forEach(SiteMap::join);
    }

}
