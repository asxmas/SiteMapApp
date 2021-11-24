package sitemapapp.service.impl;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sitemapapp.properties.Properties;
import sitemapapp.service.Node;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@Getter
public class NodeImpl implements Node {
    Logger nodeImplLogger = Logger.getLogger(NodeImpl.class);
    private final Set<Node> childs = new HashSet<>();
    private final String url;
    private final String startUrl;
    private static final CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();

    NodeImpl(String url, String startUrl){
        this.url = url;
        this.startUrl = startUrl;
    }

    @Override
    public Set<Node> getChilds() throws IOException {
        try {
            Document document = Jsoup.connect(this.url).maxBodySize(0)
                    .userAgent(Properties.USER_AGENT.getProperty())
                    .referrer(Properties.REFERRER.getProperty())
                    .get();
            Elements elements = document.select("a[href]");
            elements.forEach(element -> {
                String newLink = element.absUrl("href");
                if (checkLink(newLink)) {
                    SiteMap siteMap = new SiteMap(new NodeImpl(newLink, startUrl), startUrl);
                    siteMap.fork();
                    childs.add(new NodeImpl(newLink, startUrl));
                    allLinks.add(newLink);
                }
            });
        } catch (HttpStatusException ex){
            URL site = new URL(ex.getUrl());
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            nodeImplLogger.warn(ex.getLocalizedMessage()
                    + " for url: "
                    + ex.getUrl()
                    + ", response of site: "
                    + connection.getResponseMessage() + "(code: "
                    + connection.getResponseCode() + ")");
        }
            return childs;
    }

    private boolean checkLink(String link){
        return !link.isEmpty()
                && link.startsWith(startUrl)
                && (link.endsWith("html") || !link.matches(startUrl + ".+\\.[a-zA-Z1-9]{3,4}$"))
                && !allLinks.contains(link)
                && !link.contains("#");
    }

    public Set<Node> getChildren (){
        return childs;
    }
}
