import com.mainul35.ApplicationRunner;
import com.mainul35.Main;
import com.mainul35.helper.SearchResultCollectorHelper;
import com.mainul35.util.PropertyConfig;
import com.mainul35.util.PropertyKeySource;
import com.mainul35.util.WebClient;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationRunnerTest {
    @Mock
    WebClient webClient;
    @Mock
    SearchResultCollectorHelper helper;
    ApplicationRunner runner;

    @BeforeEach
    public void init() throws IOException {
//        webClient = new WebClient();
//        helper = new SearchResultCollectorHelper();
        runner = new ApplicationRunner();
    }

    @Test
    @DisplayName("Call the runner method")
    public void testRunner() throws IOException, InterruptedException {
        var response = Mockito.mock(CloseableHttpResponse.class);
        when(webClient.get(any())).thenReturn(response);
        var statusLine = Mockito.mock(StatusLine.class);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(helper.getTopicAndUrl(response)).thenReturn(Mockito.mock(Map.class));
        String completionStatus = runner.run(webClient, helper);

        Assertions.assertEquals(completionStatus, "Completed");
    }
}
