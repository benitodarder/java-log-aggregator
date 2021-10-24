package local.tin.tests.log.aggregates;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import local.tin.tests.log.aggregates.tasks.Initializer;
import local.tin.tests.log.aggregates.tasks.Task;

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
        
        Initializer initializer = new Initializer(getNewLogEntryInstance(), logStep);
        Future<LogEntry> futureInitializer = executorService.submit(initializer);    
        try {
            getLogEntriesPool().put(logStep.getId(), (L) futureInitializer.get());
        } catch (InterruptedException | ExecutionException ex) {
           throw new LogException(ex);
        }
        
        return logStep;
    }

}
