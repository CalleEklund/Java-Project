package handlers;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * ProjectLogger klass som används för att spara det som sker under programmets gång som t.ex. felaktigt input vid inloggning men även
 * vilken användare som inloggad som när en användare väljer att logga ut.
 */
public class ProjectLogger
{
    final static String FILE_SEPERATOR = File.separator;
    final static private String LOGGER_PROPERTIES = "logger.properties";
    final static private String LOG_FILE_LOCATION = "res" + FILE_SEPERATOR + "budget_logs.log";

    private static final LogManager LOG_MANAGER = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("pages");

    public ProjectLogger() {
	try {
	    LOG_MANAGER.readConfiguration(ClassLoader.getSystemResourceAsStream(LOGGER_PROPERTIES));
	    LOGGER.fine("Conf Loaded");
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error in loading configuration", e);
	    e.printStackTrace();
	}
	try {
	    Formatter simpleFormatter = new SimpleFormatter();
	    Handler fileHandler = new FileHandler(LOG_FILE_LOCATION);
	    fileHandler.setFormatter(simpleFormatter);
	    LOGGER.addHandler(fileHandler);
	} catch (IOException e) {
	    LOGGER.log(Level.SEVERE, "Error occur in FileHandler", e);
	    e.printStackTrace();
	}


	disableConsoleOutput();


	LOGGER.log(Level.INFO, "ProjectLogger loaded correctly");
    }

    /**
     * Används för att onödig data in ska skrivas till logfilen som bland annat de grafiska som skapas vid de olika sidorna.
     */
    public void disableConsoleOutput() {
	Logger l = LOGGER;
	while (l != null) {
	    l.setLevel(Level.OFF);
	    l = l.getParent();
	}
	LOGGER.setLevel(Level.FINER);
    }

    /**
     * En loggning funktion som väljer nivå på loggning samt meddelande.
     *
     * @param level Vilken nivå problemer/information har
     * @param msg   Meddelandet som skrivs till log filen
     */
    public void logMsg(Level level, String msg) {
	LOGGER.log(level, msg);
    }
}

