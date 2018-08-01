package org.logevents.destinations;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.logevents.LogEvent;
import org.slf4j.event.Level;

public class ConsoleLogEventFormatterTest {

    protected ConsoleFormatting format = ConsoleFormatting.getInstance();
    private ConsoleLogEventFormatter formatter = new ConsoleLogEventFormatter();
    private String loggerName = "com.example.LoggerName";

    @Test
    public void shouldLogMessage() {
        Instant time = ZonedDateTime.of(2018, 8, 1, 10, 0, 0, 0, ZoneId.systemDefault()).toInstant();
        String message = formatter.format(new LogEvent(loggerName, Level.INFO, null, "Hello {}", new Object[] { "there" }, time));
        assertEquals("10:00 [main] [\033[32mINFO \033[m] [\033[1;mcom.example.LoggerName\033[m]: Hello there\n",
                message);
    }

    @Test
    public void shouldColorLogLevel() {
        assertEquals(format.red("ERROR"), formatter.colorizedLevel(Level.ERROR));
        assertEquals(format.yellow("WARN "), formatter.colorizedLevel(Level.WARN));
        assertEquals(format.green("INFO "), formatter.colorizedLevel(Level.INFO));
        assertEquals(format.green("DEBUG"), formatter.colorizedLevel(Level.DEBUG));
    }
}