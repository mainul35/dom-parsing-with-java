package com.mainul35;

import com.mainul35.helper.SearchResultCollectorHelper;
import com.mainul35.util.WebClient;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        WebClient webClient = new WebClient();
        SearchResultCollectorHelper helper = new SearchResultCollectorHelper();
        ApplicationRunner runner = new ApplicationRunner();
        runner.run(webClient, helper);
    }
}
