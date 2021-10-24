package local.tin.tests.log.aggregates.tasks;

import java.util.concurrent.Callable;
import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogStep;

/**
 *
 * @author benitodarder
 */
public abstract class Task implements Callable<LogEntry> {

    private final LogEntry logEntry;
    private final LogStep logStep;

    public Task(LogEntry logEntry, LogStep logStep) {
        this.logEntry = logEntry;
        this.logStep = logStep;
    }

    public LogEntry getLogEntry() {
        return logEntry;
    }

    public LogStep getLogStep() {
        return logStep;
    }
    
    
}
