package sitemapapp.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class CheckSite {

    static Logger checkSiteLogger = Logger.getLogger(CheckSite.class);

    private CheckSite(){}

    public static String correctingUrl(String url){

        if(Pattern.compile("^(http(s)?)://.+").matcher(url).matches()){
            return url;
        }
        return "https://" + url;
    }
    public static boolean checkResponseOfSite(String url){
        try {
            URL site = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) site.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (IOException e) {
            checkSiteLogger.warn(e.getMessage());
        }
        return false;
    }
}
