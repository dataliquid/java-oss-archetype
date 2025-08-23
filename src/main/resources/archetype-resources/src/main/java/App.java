package ${package};

import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public String getGreeting() {
        return "Hello from dataliquid Open Source Software!";
    }

    public static void main(String[] args) {
        App app = new App();
        if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
            LOGGER.info(app.getGreeting());
        }
    }
}