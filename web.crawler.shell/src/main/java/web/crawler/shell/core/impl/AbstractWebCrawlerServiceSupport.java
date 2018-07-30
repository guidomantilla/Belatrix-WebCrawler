/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.core.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.crawler.shell.util.FileHelper;
import web.crawler.shell.util.ProperNameFinder;
import web.crawler.shell.util.TwitterMatcher;

/**
 *
 * @author raven
 */
public class AbstractWebCrawlerServiceSupport {

    protected Document fetchingPage(String websiteURL) throws IOException {
        Document page = Jsoup.connect(websiteURL).get();
        return page;
    }

    protected Path createDumpFile(String websiteURL, String dumpFilePath) throws IOException, EncoderException {
        URLCodec codec = new URLCodec();

        String urlEscaped = codec.encode(websiteURL);

        String filePath = dumpFilePath;
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }
        filePath += ":urlEscaped.txt".replace(":urlEscaped", urlEscaped);

        Path path = Paths.get(filePath);
        FileHelper.createTruncate(path);

        return path;
    }

    protected void crawl(List<String> patternList, String websiteURL, String dumpFilePath) throws IOException, EncoderException, URISyntaxException {

        /**/
        Path path = createDumpFile(websiteURL, dumpFilePath);
        Document page = fetchingPage(websiteURL);

        List<String> matchedPatternList = new ArrayList<>();

        for (String pattern : patternList) {

            switch (pattern) {
                case "twitter-links":
                    matchedPatternList.addAll(TwitterMatcher.findLinks(page));
                    break;

                case "twitter-accounts":
                    matchedPatternList.addAll(TwitterMatcher.findHashtag(page));
                    break;

                case "twitter-hashtags":
                    matchedPatternList.addAll(TwitterMatcher.findAccount(page));
                    break;

                case "name-locations":
                    matchedPatternList.addAll(ProperNameFinder.findLocations(page));
                    break;

                case "name-persons":
                    matchedPatternList.addAll(ProperNameFinder.findPersons(page));
                    break;
                default:
                    break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String matchedPattern : matchedPatternList) {
            stringBuilder.append(matchedPattern).append("\n");
        }

        FileHelper.createAppend(path, stringBuilder.toString());
    }
}
