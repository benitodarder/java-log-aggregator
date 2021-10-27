package local.tin.tests.tomee.hello.world.logging;

import local.tin.tests.log.aggregates.ILogEntriesPool;
import local.tin.tests.log.aggregates.LogEntriesExecutor;

/**
 *
 * @author benitodarder
 */

public class Sl4jEntriesExecutor extends LogEntriesExecutor<Sl4jEntry> {

    
    private final Sl4jEntriesPool entriesPool;
    
    public static Sl4jEntriesExecutor getInstance() {
        return Sl4jEntriesExecutorHolder.INSTANCE;
    }
    
    private static class Sl4jEntriesExecutorHolder {

        private static final Sl4jEntriesExecutor INSTANCE = new Sl4jEntriesExecutor();
    }    
    
    private Sl4jEntriesExecutor() {
        entriesPool = new Sl4jEntriesPool();
    }
    
    @Override
    protected ILogEntriesPool<Sl4jEntry> getLogEntriesPool() {
        return entriesPool;
    }

    @Override
    protected Sl4jEntry getNewLogEntryInstance() {
        return new Sl4jEntry();
    }
    
}
