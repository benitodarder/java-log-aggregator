package local.tin.tests.log.aggregates.tasks;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogStep;

/**
 *
 * @author benitodarder
 */
public class Appender extends Task {

    public Appender(LogEntry logEntry, LogStep logStep) {
        super(logEntry, logStep);
    }

    @Override
    public LogEntry call() throws Exception {
        getLogEntry().append(getLogStep());
        return getLogEntry();
    }

}
