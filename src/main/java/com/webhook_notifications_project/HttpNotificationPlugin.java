package com.webhook_notifications_project;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * HttpNotificationPlugin is a Rundeck plugin that allows flexible HTTP notifications.
 * It supports various HTTP methods and allows customization of request body and content type.
 */
public class HttpNotificationPlugin {

    private final CloseableHttpClient httpClient;

    /**
     * Constructs a new HttpNotificationPlugin with the specified HTTP client.
     *
     * @param httpClient the HTTP client to use for sending requests
     */
    public HttpNotificationPlugin(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Sends an HTTP request based on the provided parameters.
     *
     * @param url         the URL to send the request to
     * @param method      the HTTP method (GET, POST, PUT, DELETE)
     * @param body        the request body content
     * @param contentType the content type of the request body
     * @return the response content as a String
     * @throws IOException if an I/O error occurs during the request
     * @throws IllegalArgumentException if the URL or method is null or empty
     */
    public String sendHttpRequest(String url, String method, String body, String contentType) throws IOException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("HTTP method cannot be null or empty");
        }

        HttpUriRequest request = createRequest(url, method, body, contentType);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new IOException("Unexpected response status: " + statusCode);
            }
        } catch (IOException e) {
            // Log the error
            System.err.println("HTTP request failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Creates an HTTP request based on the method and other parameters.
     *
     * @param url         the URL to send the request to
     * @param method      the HTTP method
     * @param body        the request body content
     * @param contentType the content type of the request body
     * @return an HttpUriRequest object representing the HTTP request
     */
    private HttpUriRequest createRequest(String url, String method, String body, String contentType) {
        switch (method.toUpperCase()) {
            case "POST":
                HttpPost post = new HttpPost(url);
                post.setEntity(new StringEntity(body, contentType));
                return post;
            case "PUT":
                HttpPut put = new HttpPut(url);
                put.setEntity(new StringEntity(body, contentType));
                return put;
            case "DELETE":
                return new HttpDelete(url);
            case "GET":
            default:
                return new HttpGet(url);
        }
    }
}
