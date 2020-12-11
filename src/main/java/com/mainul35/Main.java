package com.mainul35;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient();
        SearchResultCollectorHelper helper = new SearchResultCollectorHelper();
        try {
            CloseableHttpResponse response = webClient.get("http://www.cochranelibrary.com/home/topic-and-review-group-list.html?page=topic");
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Map<String, String> urlPerTopic = helper.getTopicAndUrl(response);

                    List<Result> resultSet = new ArrayList<>();
                    StringBuilder output = new StringBuilder("");

                    for (Map.Entry<String, String> entry : urlPerTopic.entrySet()) {
                        try {
                            response = webClient.get(entry.getValue());
                            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                                resultSet = helper.getResultSet(response, entry.getKey());
                                resultSet.forEach(result -> {
                                    output
                                            .append(result.getUrl())
                                            .append(" | ")
                                            .append(result.getTopic())
                                            .append(" | ")
                                            .append(result.getTitle())
                                            .append(" | ")
                                            .append(result.getAuthor())
                                            .append(" | ")
                                            .append(result.getDate())
                                            .append("\n\n");
                                });
                            }
                        } catch (SocketTimeoutException e) {
                            System.err.println(e.getMessage() + "For URL of title: " + entry.getKey());
                        }

//                        break;
                    }

                    writeToFile(output);
                    System.out.println("Completed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            webClient.close();
        }

    }

    private static void writeToFile(StringBuilder output) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(Main.class.getClassLoader()
                .getResource("static/cochrane_reviews.txt").getFile());
        byte b[] = output.toString().getBytes();
        outputStream.write(b);
        outputStream.close();
    }
}
