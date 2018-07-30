/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.core.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.codec.EncoderException;
import web.crawler.shell.core.WebCrawlerService;

/**
 *
 * @author raven
 */
public class DefaultWebCrawlerService extends AbstractWebCrawlerServiceSupport implements WebCrawlerService {

    @Override
    public void crawl(List<String> patternList, String websitesPath, String dumpFilePath) {

        System.out.println(Calendar.getInstance().getTime() + " Starting Crawling the websites");

        try {

            List<String> websitesList = Files.readAllLines(Paths.get(websitesPath));
            for (String websiteURL : websitesList) {

                try {

                    super.crawl(patternList, websiteURL, dumpFilePath);

                } catch (EncoderException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Calendar.getInstance().getTime() + " Done Crawling the websites");

    }
}
