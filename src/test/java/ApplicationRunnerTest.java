import com.mainul35.ApplicationRunner;
import com.mainul35.helper.SearchResultCollectorHelper;
import com.mainul35.util.WebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApplicationRunnerTest {
    WebClient webClient;
    SearchResultCollectorHelper helper;
    ApplicationRunner runner;


    @BeforeEach
    public void init() throws IOException {
        webClient = new WebClient();
        helper = new SearchResultCollectorHelper();
        runner = new ApplicationRunner();
    }

    @Test
    @DisplayName("Call the runner method")
    public void testRunner() throws IOException {
        String completionStatus = runner.run(webClient, helper);
        Assertions.assertEquals(completionStatus, "Completed");
    }
}
