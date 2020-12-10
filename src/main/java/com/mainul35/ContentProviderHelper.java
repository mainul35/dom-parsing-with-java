package com.mainul35;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentProviderHelper {

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
                if (line.contains("class=\"browse-by-list-item\"")) {
                    line = br.readLine();
                    String url = extractUrl(line);
                    String key = extractText(line);
                    content.put(key, url);
                }

            }
        }
        return content;
    }
}
