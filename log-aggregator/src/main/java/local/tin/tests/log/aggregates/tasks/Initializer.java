package local.tin.tests.log.aggregates.tasks;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogStep;

/**
 *
 * @author benitodarder
 */
public class Initializer extends Task {

    public Initializer(LogEntry logEntry, LogStep logStep) {
        super(logEntry, logStep);
    }

    @Override
    public LogEntry call() throws Exception {
        getLogEntry().initialize(getLogStep());
        return getLogEntry();
    }

}
