package local.tin.tests.log.aggregates.spring.boot.logging;

import local.tin.tests.log.aggregates.ILogEntriesPool;
import local.tin.tests.log.aggregates.LogEntriesExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author benitodarder
 */
@Component
public class Sl4jEntriesExecutor extends LogEntriesExecutor<Sl4jEntry> {

    @Autowired
    private Sl4jEntriesPool entriesPool;
    
    @Override
    protected ILogEntriesPool<Sl4jEntry> getLogEntriesPool() {
        return entriesPool;
    }

    @Override
    protected Sl4jEntry getNewLogEntryInstance() {
        return new Sl4jEntry();
    }
    
}
