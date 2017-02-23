package cn.com.warlock.wisp.test.log.logback;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;

/**
 * inspired by https://github.com/thekua/Sample-Code/tree/master/java/logback-spike
 */
public class LogbackCapturingAppender extends AppenderBase<ILoggingEvent> {

    public static class Factory {
        private static List<LogbackCapturingAppender> ALL = new ArrayList<LogbackCapturingAppender>();

        public static LogbackCapturingAppender weaveInto(org.slf4j.Logger sl4jLogger) {
            LogbackCapturingAppender appender = new LogbackCapturingAppender(sl4jLogger);
            ALL.add(appender);
            return appender;
        }

        public static void cleanUp() {
            for (LogbackCapturingAppender appender : ALL) {
                appender.cleanUp();
            }
        }

    }

    private final Logger logger;
    private ILoggingEvent captured;

    public LogbackCapturingAppender(org.slf4j.Logger sl4jLogger) {
        this.logger = (Logger) sl4jLogger;
        connect(logger);
        detachDefaultConsoleAppender();
    }

    private void detachDefaultConsoleAppender() {
        Logger rootLogger = getRootLogger();
        Appender<ILoggingEvent> consoleAppender = rootLogger.getAppender("console");
        rootLogger.detachAppender(consoleAppender);
    }

    private Logger getRootLogger() {
        return logger.getLoggerContext().getLogger("ROOT");
    }

    private void connect(Logger logger) {
        logger.setLevel(Level.ALL);
        logger.addAppender(this);
        this.start();
    }

    public String getCapturedLogMessage() {
        if (captured == null) {
            return null;
        }
        return captured.getMessage();
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        captured = iLoggingEvent;
    }

    private void cleanUp() {
        logger.detachAppender(this);

    }
}