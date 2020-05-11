package handlers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loggertest
{
    private static final LogManager LOG_MANAGER = LogManager.getLogManager();
    private static Logger LOGGER = Logger.getLogger("pages");

    public Loggertest() {
	try {
	    LOG_MANAGER.readConfiguration(ClassLoader.getSystemResourceAsStream("logger.properties"));
	    LOGGER.fine("Conf Loaded");
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error in loading configuration", e);
	}
	try {
	    Formatter simpleFormatter = new SimpleFormatter();
	    Handler fileHandler = new FileHandler("res/budget_logs.log");
	    fileHandler.setFormatter(simpleFormatter);
	    LOGGER.addHandler(fileHandler);
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error occur in FileHandler", e);
	}


	disableConsoleOutput();


	LOGGER.log(Level.INFO, "Logger loaded correctly");
    }

    public void disableConsoleOutput() {
	Logger l = LOGGER;
	while (l != null) {
	    l.setLevel(Level.OFF);
	    l = l.getParent();
	}
	LOGGER.setLevel(Level.FINER);
    }

    public void logMsg(Level level, String msg) {
	LOGGER.log(level, msg);
    }
//    public static void main(String[] args) {
//	new handlers.Logger();
//    }
}

