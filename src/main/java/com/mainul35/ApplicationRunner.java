package com.mainul35;

import com.mainul35.helper.SearchResultCollectorHelper;
import com.mainul35.model.Result;
import com.mainul35.util.PropertyConfig;
import com.mainul35.util.PropertyKeySource;
import com.mainul35.util.WebClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationRunner {
    private final PropertyConfig propertyConfig;

    public ApplicationRunner() throws IOException {
        propertyConfig = new PropertyConfig();
    }

    public String run(WebClient webClient, SearchResultCollectorHelper helper) throws IOException {
        try {
            CloseableHttpResponse response = webClient.get("http://www.cochranelibrary.com/home/topic-and-review-group-list.html?page=topic");
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    Map<String, String> urlPerTopic = helper.getTopicAndUrl(response);

                    StringBuilder output = new StringBuilder("");

                    ExecutorService executor = Executors.newFixedThreadPool(5);
                    LinkedList<Callable<StringBuilder>> tasksList = new LinkedList<>();
                    for (Map.Entry<String, String> entry : urlPerTopic.entrySet()) {
                        tasksList.add(prepareSearchResultsAsync(webClient, entry, helper, output));
                    }

                    executor.invokeAll(tasksList);

                    executor.shutdownNow();

                    writeToFile(output);
                    return "Completed";
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
        return "Failed";
    }

    private Callable<StringBuilder> prepareSearchResultsAsync(
            WebClient webClient,
            Map.Entry<String, String> entry,
            SearchResultCollectorHelper helper,
            StringBuilder output) {
        Callable<StringBuilder> callableTask = () -> {
            try {
                CloseableHttpResponse response = webClient.get(entry.getValue());
                if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    List<Result> resultSet = helper.getResultSet(response, entry.getKey());
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
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            return output;
        };
        return callableTask;
    }

    private void writeToFile(StringBuilder output) throws IOException {
        URL url = getClass().getClassLoader().getResource(propertyConfig.getPropertyValue(PropertyKeySource.OUTPUT_FILE_NAME));
        if (url != null && new File(url.getFile()).exists()) {
            FileOutputStream outputStream = new FileOutputStream(url.getFile());
            byte[] b = output.toString().getBytes();
            outputStream.write(b);
            outputStream.close();
        }
    }
}
