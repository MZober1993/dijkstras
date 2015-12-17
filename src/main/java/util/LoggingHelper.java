package util;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 17.12.15 - 13:30
 */
public class LoggingHelper {

    public static <T> Logger buildLoggerWithStandardOutputConfig(Class<T> clazz) {
        String classname = clazz.getTypeName().replace(".", "_");
        final Logger LOGGER = Logger.getLogger(classname);
        try {

            FileHandler fileHandler = new FileHandler("./src/main/resources/Logging/" + classname + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            ConsoleHandler consoleHandler = new ConsoleHandler();
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
            LOGGER.config("Configuration done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LOGGER;
    }

    public static <T> void logWarningIfNull(String name, T elem, Logger logger) {
        if (elem == null) {
            logger.warning("element: " + name + " is null.");
        }
    }

    public static void logMinusLine(Logger logger) {
        logger.config("-------------------------------------------------------------");
    }

    public static void logNewLine(Logger logger) {
        logger.config("\n");
    }
}
