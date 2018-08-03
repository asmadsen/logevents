package org.logevents.destinations;

import org.logevents.LogEvent;

/**
 * A simple and convenient {@link LogEventFormatter} which outputs
 * Time, Thread, Level, Logger as well as the log message and stack trace.
 *
 * @author Johannes Brodwall
 */
public final class TTLLEventLogFormatter implements LogEventFormatter {
    @Override
    public String format(LogEvent e) {
        return String.format("%s [%s] [%s] [%s]: %s\n",
                e.getZonedDateTime().toLocalTime(),
                e.getThreadName(),
                LogEventFormatter.rightPad(e.getLevel(), 5, ' '),
                e.getLoggerName(),
                e.formatMessage())
                + e.formatStackTrace();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}