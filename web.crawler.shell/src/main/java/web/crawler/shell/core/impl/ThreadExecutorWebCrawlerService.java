/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.core.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import org.springframework.stereotype.Service;
import web.crawler.shell.core.WebCrawlerService;
import web.crawler.shell.util.TaskExecutionAdapter;

/**
 *
 * @author raven
 */
@Service
public class ThreadExecutorWebCrawlerService extends AbstractWebCrawlerServiceSupport implements WebCrawlerService {

    @Override
    public void crawl(List<String> patternList, String websitesPath, String dumpFilePath) {

        System.out.println(Calendar.getInstance().getTime() + " Starting Crawling the websites");

        try {

            List<String> websitesList = Files.readAllLines(Paths.get(websitesPath));

            final int SIZE = websitesList.size();
            TaskExecutionAdapter<String> webCrawlingTaskExec = new TaskExecutionAdapter<>(SIZE);

            for (String websiteURL : websitesList) {
                webCrawlingTaskExec.submitTask(createCrawlingTask(patternList, websiteURL, dumpFilePath));
            }

            for (int i = 0; i < SIZE; i++) {
                webCrawlingTaskExec.retrieveTaskResult();
            }
            webCrawlingTaskExec.shutdownTaskExecutor();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Calendar.getInstance().getTime() + " Done Crawling the websites");
    }

    private Callable<String> createCrawlingTask(List<String> patternList, String websiteURL, String dumpFilePath) {

        Callable<String> callableTask = () -> {

            super.crawl(patternList, websiteURL, dumpFilePath);

            return null;
        };

        return callableTask;
    }
}
