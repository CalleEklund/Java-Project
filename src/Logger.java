import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.StreamHandler;

public class Logger
{
    private static final LogManager LOG_MANAGER = LogManager.getLogManager();
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    public Logger() {
	try {
	    LOG_MANAGER.readConfiguration(ClassLoader.getSystemResourceAsStream("logger.properties"));
	    LOGGER.fine("Conf Loaded");
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error in loading configuration", e);
	}
	FileHandler fileHandler;
	try {
	    fileHandler = new FileHandler("budget_logs.log");
	    LOGGER.addHandler(fileHandler);
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error occur in FileHandler", e);
	}
    }

    public static void main(String[] args) {
	new Logger();
	LOGGER.finer("Finest example on LOGGER handler completed.");

    }
}

