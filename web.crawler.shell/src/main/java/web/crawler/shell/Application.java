/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import web.crawler.shell.core.impl.DefaultWebCrawlerService;
import web.crawler.shell.core.impl.StreamsWebCrawlerService;
import web.crawler.shell.core.impl.ThreadExecutorWebCrawlerService;

/**
 *
 * @author raven
 */
@SpringBootApplication
@ShellComponent
public class Application {

    private final List<String> patternOptions;

    public Application() {
        patternOptions = new ArrayList<>();
        patternOptions.add("*");
        patternOptions.add("twitter-links");
        patternOptions.add("twitter-accounts");
        patternOptions.add("twitter-hashtags");
        patternOptions.add("name-locations");
        patternOptions.add("name-persons");
    }

    public static void main(String[] args) throws IOException {

        String[] disabledCommands = {"--spring.shell.command.quit.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, fullArgs);
    }

    @ShellMethod(value = "This methods crawls a web sites list from a file.", key = "crawl-web-sites")
    public String crawl(
            @ShellOption(value = {"-i", "--impl"},
            defaultValue = "default",
            help = "This parameter defines the implementation type for the Job") String implementation,
            @ShellOption(value = {"-op", "--origin-path"}, help = "This parameter defines the origin path") String originPath,
            @ShellOption(value = {"-dp", "--destination-path"}, help = "This parameter defines the destination path") String destinationPath,
            @ShellOption(value = {"-p", "--pattern"}, defaultValue = "*", help = "This parameter defines the pattern to be searched") String pattern
    ) {

        String message = "";
        if (validatePath(originPath) && validatePath(destinationPath)) {

            if (validatePattern(pattern)) {

                List<String> patternList = createPatternList(pattern);
                switch (implementation) {
                    case "default":
                        new DefaultWebCrawlerService().crawl(patternList, originPath, destinationPath);
                        break;

                    case "threadExecutor":
                        new ThreadExecutorWebCrawlerService().crawl(patternList, originPath, destinationPath);
                        break;

                    case "streams":
                        new StreamsWebCrawlerService().crawl(patternList, originPath, destinationPath);
                        break;

                    default:
                        message = "ERROR: The impl parameter may not be valid.";
                        break;
                }
            } else {
                message = "ERROR: The pattern parameters may not be valid.\n";
                message += "Posible values " + patternOptions;
            }

        } else {
            message = "ERROR: The path parameters may not be valid.";
        }

        return message;
    }

    @ShellMethod(value = "Force quit from the application", key = "quit")
    public String quit() {
        System.exit(0);
        return "";
    }

    private boolean validatePath(String path) {
        return new File(path).exists();
    }

    private boolean validatePattern(String pattern) {
        return patternOptions.contains(pattern);
    }

    private List<String> createPatternList(String pattern) {

        List<String> patternList = new ArrayList<>(patternOptions.size());

        if (pattern.equals("*")) {
            patternList.addAll(patternOptions);
            patternList.remove("*");
        } else {
            patternList.add(pattern);
        }

        return patternList;
    }
}
