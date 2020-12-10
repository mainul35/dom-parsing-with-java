package com.mainul35;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient();
        ContentProvider<Map<String, String>> mapContentProvider = new ContentProvider<>();
        ContentProviderHelper helper = new ContentProviderHelper();
        try {

            CloseableHttpResponse response = webClient.get("http://www.cochranelibrary.com/home/topic-and-review-group-list.html?page=topic");

            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Map<String, String> urlPerTopic = mapContentProvider.getContent(response, helper::getTopicAndUrl);

                    /*for (Map.Entry<String, String> entry: urlPerTopic.entrySet()) {
                        response = webClient.get(entry.getValue());
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                        }
                    }*/
                    FileOutputStream outputStream = new FileOutputStream(Main.class.getClassLoader().getResource("static/cochrain.html").getFile());
                    byte b[] = urlPerTopic.toString().getBytes();
                    outputStream.write(b);
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

    }
}
