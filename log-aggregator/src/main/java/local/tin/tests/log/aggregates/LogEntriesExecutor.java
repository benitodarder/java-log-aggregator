package local.tin.tests.log.aggregates;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import local.tin.tests.log.aggregates.tasks.Appender;
import local.tin.tests.log.aggregates.tasks.Finalizer;
import local.tin.tests.log.aggregates.tasks.Initializer;

/**
 *
 * @author benitodarder
 * @param <L>
 */
public abstract class LogEntriesExecutor<L extends LogEntry> implements ILogEntriesExecutor<L> {

    public static final int THREADPOOL_SIZE = 4;
    private ExecutorService executorService;

    public LogEntriesExecutor() {
        executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
    }

    protected abstract ILogEntriesPool<L> getLogEntriesPool();

    protected abstract L getNewLogEntryInstance();

    @Override
    public void reset() {
        executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
    }

    @Override
    public LogStep initialize(LogStep logStep) throws LogException {
        if (logStep.getId() != null && getLogEntriesPool().get(logStep.getId()) != null) {
            throw new LogException("UUID: " + logStep.getId() + ", already present.");
        }
        
        LogEntry logEntry = getNewLogEntryInstance();
        Initializer initializer = new Initializer(logEntry, logStep);
        executorService.submit(initializer);    
        getLogEntriesPool().put(logStep.getId(), (L) logEntry);
       
        return logStep;
    }

    @Override
    public LogStep append(LogStep logStep) throws LogException {
        if (logStep.getId() == null || (logStep.getId() != null && getLogEntriesPool().get(logStep.getId()) == null)) {
            throw new LogException("UUID: " + logStep.getId() + ", not present.");
        } 
        
        LogEntry logEntry = getLogEntriesPool().get(logStep.getId());
        Appender appender = new Appender(logEntry, logStep);
        executorService.submit(appender);    
        getLogEntriesPool().put(logStep.getId(), (L) logEntry);     
        
        return logStep;
    }

    @Override
    public LogStep finalize(LogStep logStep) throws LogException {
        if (logStep.getId() == null || (logStep.getId() != null && getLogEntriesPool().get(logStep.getId()) == null)) {
            throw new LogException("UUID: " + logStep.getId() + ", not present.");
        } 
        
        LogEntry logEntry = getLogEntriesPool().get(logStep.getId());
        Finalizer finalizer = new Finalizer(logEntry, logStep);
        executorService.submit(finalizer);    
        getLogEntriesPool().put(logStep.getId(), (L) logEntry);

        return logStep;
    }

    
}
