package com.mainul35;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class ContentProvider<T> {

    public T getContent(CloseableHttpResponse response, ContentProcessor<T> contentProcessor)
            throws IOException {
        return contentProcessor.process(response);
    }
}
