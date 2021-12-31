package src.util.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.io.IoBuilder;
import src.Server;

/**
 * Logging utility to initialize
 * @author Tyler Barton
 * ref: See references#Logging
 */
public class LogUtil {
    private LogUtil() {
        // Class should not be instantiated
    }

    /**
     * Configure the logger
     */
    public static void configure() {
        try {
            StringBuilder logPattern = new StringBuilder();
            if ("true".equalsIgnoreCase(System.getProperty("coloredLogging"))) {
                logPattern.append("%d{yyyy-MM-dd HH:mm:ss}");
                logPattern.append(" %highlight{[${LOG_LEVEL_PATTERN:-%5p}]}{FATAL=red, ERROR=red, WARN=yellow, INFO=green, DEBUG=green, TRACE=green}");
                logPattern.append("%style{[%t]}{magenta}");
                logPattern.append("%style{[%c{1}]}{cyan}");
                logPattern.append(" %m%n%ex");
            } else {
                logPattern.append("%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n");
            }

            // Enables asynchronous & garbage-free logging.
            System.setProperty("log4j.configurationFile", "src/util/logging/log4j2.xml");
            System.setProperty(
                    "Log4jContextSelector",
                    "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
            );
            System.setProperty("logPattern", logPattern.toString());

            LoggerContext ctx = (LoggerContext) LogManager.getContext();
            ctx.reconfigure();

            Logger logger = LogManager.getLogger(Server.class);
            setupStreams(logger);
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    /**
     * Redirect System.out and System.err to log4j2 stream
     * @param logger log4j2 logger
     */
    private static void setupStreams(Logger logger){
        System.setOut(
                IoBuilder.forLogger(logger)
                        .setLevel(Level.INFO)
                        .buildPrintStream()
        );
        System.setErr(
                IoBuilder.forLogger(logger)
                        .setLevel(Level.ERROR)
                        .buildPrintStream()
        );
    }
}