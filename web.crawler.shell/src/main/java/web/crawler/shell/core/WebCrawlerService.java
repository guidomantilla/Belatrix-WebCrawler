/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.core;

import java.util.List;

/**
 *
 * @author raven
 */
public interface WebCrawlerService {

    public void crawl(List<String> patternList, String websitesPath, String dumpFilePath);
}
