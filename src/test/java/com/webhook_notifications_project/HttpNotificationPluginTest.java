package com.webhook_notifications_project;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.apache.http.StatusLine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HttpNotificationPluginTest {

    private HttpNotificationPlugin plugin;
    private CloseableHttpClient mockHttpClient;
    private CloseableHttpResponse mockResponse;

    @Before
    public void setUp() {
        mockHttpClient = mock(CloseableHttpClient.class);
        mockResponse = mock(CloseableHttpResponse.class);
        plugin = new HttpNotificationPlugin(mockHttpClient);
    }

    @Test
    public void testSendHttpRequest() throws IOException {
        // Arrange
        String expectedResponse = "Success";
        HttpEntity mockEntity = mock(HttpEntity.class);
        StatusLine mockStatusLine = mock(StatusLine.class);

        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));
        when(mockResponse.getEntity()).thenReturn(mockEntity);
        when(mockStatusLine.getStatusCode()).thenReturn(200); // Mock the status code
        when(mockResponse.getStatusLine()).thenReturn(mockStatusLine); // Mock the status line
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(mockResponse);

        // Act
        String actualResponse = plugin.sendHttpRequest("http://example.com", "GET", null, null);

        // Assert
        verify(mockHttpClient, times(1)).execute(any(HttpUriRequest.class));
        assertEquals(expectedResponse, actualResponse);
    }
}
