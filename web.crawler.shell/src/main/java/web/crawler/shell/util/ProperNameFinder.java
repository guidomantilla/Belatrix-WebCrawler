/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import org.jsoup.nodes.Document;
import org.springframework.util.ResourceUtils;

/**
 *
 * @author raven
 */
public class ProperNameFinder {

    public static List<String> findLocations(Document page) throws IOException, URISyntaxException {
        return find("en-ner-location.bin", page.text());
    }

    public static List<String> findOrganizations(Document page) throws IOException, URISyntaxException {
        return find("en-ner-organization.bin", page.text());
    }

    public static List<String> findPersons(Document page) throws IOException, URISyntaxException {
        return find("en-ner-person.bin", page.text());
    }

    private static List<String> find(String modelName, String txt) throws IOException, URISyntaxException {

        List<String> matchedList = new ArrayList<>();

        URL fileURL = ResourceUtils.getURL("classpath::model".replace(":model", modelName));

        try (InputStream modelIn = ProperNameFinder.class.getClassLoader().getResourceAsStream(fileURL.toString())) {
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

            String[] textArray = txt.split(" ");
            Span nameSpans[] = nameFinder.find(textArray);
            for (Span names : nameSpans) {

                String fullName = "";
                for (int i = names.getStart(); i < names.getEnd(); i++) {
                    fullName += textArray[i] + " ";
                }

                fullName = fullName.trim()
                        .replace(".", "")
                        .replace(",", "")
                        .replace(";", "")
                        .replace("!", "")
                        .replace("?", "");

                matchedList.add(fullName);
            }

            nameFinder.clearAdaptiveData();
        }
        return matchedList;
    }
}
