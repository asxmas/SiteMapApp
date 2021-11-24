package sitemapapp.utils;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class MapWritter {

    static Logger mapWritterLogger = Logger.getLogger(MapWritter.class);

    private MapWritter(){}

    public static void writeInFile(String fileName, Set<String> links) {

        mapWritterLogger.info("Create new file");
        FileWriter writer = null;
        try {
            writer = new FileWriter("data\\" + fileName);
        } catch (IOException e) {
            mapWritterLogger.error("Error to create file: " + fileName + " hasn't permission");
        }
        try {
            mapWritterLogger.info("Start write links to file");
            for(String link : links){
                assert writer != null;
                writer.write(slashes(slashes(link)) + "\n");
        }
            assert writer != null;
            writer.flush();
            writer.close();
            mapWritterLogger.info("End write links to file");
        } catch (IOException e) {
            mapWritterLogger.error(e.getMessage());
        }
    }

    private static String slashes(String url){
        StringBuilder newUrl = new StringBuilder();
        int counts = url.replaceAll("[^/]", "").length() - 3;
        newUrl.append("\t".repeat(Math.max(0, counts)));
        newUrl.append(url);
        return newUrl.toString();
    }
}
