package ${package};

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
    }

    @Test
    @DisplayName("Test greeting message")
    void testGetGreeting() {
        String expected = "Hello from dataliquid Open Source Software!";
        String actual = app.getGreeting();
        assertEquals(expected, actual, "Greeting message should match");
    }

    @Test
    @DisplayName("Test greeting is not null")
    void testGetGreetingNotNull() {
        assertNotNull(app.getGreeting(), "Greeting should not be null");
    }

    @Test
    @DisplayName("Test greeting contains dataliquid")
    void testGetGreetingContainsDataLiquid() {
        assertTrue(app.getGreeting().contains("dataliquid"), 
                  "Greeting should contain 'dataliquid'");
    }
}