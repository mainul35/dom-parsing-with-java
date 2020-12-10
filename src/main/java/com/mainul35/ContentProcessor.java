package com.mainul35;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

@FunctionalInterface
public interface ContentProcessor<T> {
    T process(CloseableHttpResponse response) throws IOException;
}
