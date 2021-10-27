package local.tin.tests.log.aggregates;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author benitodarder
 * @param <L> extends LogEntry
 */
public abstract class LogEntriesPool<L extends LogEntry> implements ILogEntriesPool<L>{

    private Map<String, L> entries;

    public LogEntriesPool() {
        entries = new ConcurrentHashMap<>();
    }    
    
    @Override
    public void put(String id, L logEntry) {
        entries.put(id, logEntry);
    }
    
    @Override
    public L get(String id) {
        return entries.get(id);
    }
    
    @Override
    public L remove(String id) {
        return entries.remove(id);
    }
    
    @Override
    public void resetPool() {
        entries = new ConcurrentHashMap<>();
    }
}
