package com.mainul35;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchResultCollector {

    public List<Result> collect(String topic, String html) throws IOException {
        Document doc = Jsoup.parse(html, "utf-8");
        List<String> titles = getTitleList(doc);
        List<String> urls = getUrls(doc);
        List<String> authors = getAuthors(doc);
        List<String> dates = getDates(doc);

        List<Result> results = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            Result result = new Result();
            result.setAuthor(authors.get(i));
            result.setDate(dates.get(i));
            result.setTitle(titles.get(i));
            result.setUrl(urls.get(i));
            result.setTopic(topic);
            results.add(result);
        }
        return results;
    }

    private List<String> getTitleList(Document doc) {
        String stringList = doc.select(".result-title > a").html();
        String[] titles = stringList.split(System.getProperty("line.separator"));
        return Stream.of(titles).collect(Collectors.toList());
    }

    private List<String> getAuthors(Document doc) {
        String stringList = doc.select(".search-result-authors > div").html();
        String[] titles = stringList.split(System.getProperty("line.separator"));
        return Stream.of(titles).collect(Collectors.toList());
    }

    private List<String> getDates(Document doc) {
        String stringList = doc.select(".search-result-date > div").html();
        String[] titles = stringList.split(System.getProperty("line.separator"));
        return Stream.of(titles).collect(Collectors.toList());
    }

    private List<String> getUrls(Document document) {
        Elements elements = document.select(".result-title > a");
        List<String> urls = new ArrayList<>();
        elements.forEach(element -> urls.add("https://www.cochranelibrary.com" + element.attr("href")));
        return urls;
    }
}
