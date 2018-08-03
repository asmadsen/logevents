package org.logevents.observers;

import java.io.IOException;
import java.util.Properties;

import org.logevents.LogEvent;
import org.logevents.LogEventObserver;
import org.logevents.destinations.LogEventDestination;
import org.logevents.destinations.LogEventFormatter;
import org.logevents.status.LogEventStatus;
import org.logevents.util.Configuration;

public class TextLogEventObserver implements LogEventObserver {

    private final LogEventDestination destination;
    private final LogEventFormatter formatter;

    public TextLogEventObserver(Properties properties, String prefix) {
        Configuration configuration = new Configuration(properties, prefix);
        destination = configuration.createInstance("destination", LogEventDestination.class);
        formatter = configuration.createInstance("formatter", LogEventFormatter.class);
        LogEventStatus.getInstance().addInfo(this, "Configured " + prefix);
    }

    public TextLogEventObserver(LogEventDestination eventDestination, LogEventFormatter logEventFormatter) {
        this.destination = eventDestination;
        this.formatter = logEventFormatter;
    }

    @Override
    public void logEvent(LogEvent logEvent) {
        try {
            destination.writeEvent(formatter.format(logEvent));
        } catch (IOException e) {
            // PANICK!
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{eventDestination=" + destination
                + ",logEventFormatter=" + formatter + "}";
    }

}
