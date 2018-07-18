package org.logevents.observers.batch;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.logevents.LogEvent;

public class LogEventGroup {

    private List<LogEvent> logEvents = new ArrayList<>();

    public LogEventGroup(LogEvent logEvent) {
        logEvents.add(logEvent);
    }

    public void add(LogEvent logEvent) {
        this.logEvents.add(logEvent);
    }

    public boolean isMatching(LogEvent logEvent) {
        return headMessage().getLevel().equals(logEvent.getLevel())
                && headMessage().getLoggerName().equals(logEvent.getLoggerName())
                && headMessage().getMessage().equals(logEvent.getLoggerName());
    }

    LogEvent headMessage() {
        return logEvents.get(0);
    }

    public Instant latestEventTime() {
        return logEvents.get(logEvents.size()-1).getZonedDateTime().toInstant();
    }

    public Instant firstEventTime() {
        return logEvents.get(0).getZonedDateTime().toInstant();
    }

    public int size() {
        return logEvents.size();
    }

}