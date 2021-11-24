package sitemapapp.service;

import java.io.IOException;
import java.util.Set;

public interface Node {

    Set<Node> getChilds() throws IOException;
    Set<Node> getChildren();


    String getUrl();
}
