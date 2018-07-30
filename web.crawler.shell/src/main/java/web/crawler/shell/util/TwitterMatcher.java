/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author raven
 */
public class TwitterMatcher {

    public static List<String> findLinks(Document page) throws IOException {

        List<String> matchedList = new ArrayList<>();
        Elements links = page.select("a[href]");
        links.stream()
                .map((link) -> link.attr("abs:href"))
                .filter((href) -> (href.contains("twitter.com/")))
                .forEachOrdered((href) -> {
                    matchedList.add(href);
                });

        return matchedList;
    }

    public static List<String> findHashtag(Document page) throws IOException {

        List<String> matchedList = new ArrayList<>();

        String patternStr = "(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(page.text());

        while (matcher.find()) {
            String aux = matcher.group();
            aux = aux.replace(" ", "");
            matchedList.add(aux);
        }

        return matchedList;
    }

    public static List<String> findAccount(Document page) throws IOException {

        List<String> matchedList = new ArrayList<>();

        String patternStr = "(?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(page.text());

        while (matcher.find()) {
            String aux = matcher.group();
            aux = aux.replace(" ", "");
            matchedList.add(aux);
        }

        return matchedList;
    }

}
