package com.mainul35.helper;

import com.mainul35.SearchResultCollector;
import com.mainul35.model.Result;
import com.mainul35.util.PropertyConfig;
import com.mainul35.util.PropertyKeySource;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultCollectorHelper {

    private final PropertyConfig propertyConfig;
    private SearchResultCollector collector;

    public SearchResultCollectorHelper() throws IOException {
        this.propertyConfig = new PropertyConfig();
    }

    private String extractUrl(String element) {
        // Example taken from
        // https://stackoverflow.com/questions/8307839/creating-java-regex-to-get-href-link
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(element);
        String url = null;
        if (m.find()) {
            url = m.group(1); // this variable should contain the link URL
        }
        return url;
    }

    private String extractText(String element) {
        return element.replaceAll("\\<[^>]*>", "");
    }

    public Map<String, String> getTopicAndUrl(CloseableHttpResponse response1) throws IOException {
        HttpEntity entity = response1.getEntity();
        Map<String, String> content = new LinkedHashMap<>();
        if (entity != null) {
            BufferedReader br = new BufferedReader
                    (new InputStreamReader(
                            response1.getEntity().getContent()));

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("class=\""
                        + propertyConfig.getPropertyValue(PropertyKeySource.TOPIC_SELECTOR_CLASS)
                        + "\"")) {
                    line = br.readLine();
                    String url = extractUrl(line);
                    String key = extractText(line);
                    content.put(key, url);
                }

            }
        }
        return content;
    }

    public List<Result> getResultSet(CloseableHttpResponse response1, String topicTitle) throws IOException {
        HttpEntity entity = response1.getEntity();
        collector = new SearchResultCollector();
        StringBuilder content = new StringBuilder("");
        if (entity != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response1.getEntity().getContent()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        }
        return collector.collect(topicTitle, content.toString());
    }

}
