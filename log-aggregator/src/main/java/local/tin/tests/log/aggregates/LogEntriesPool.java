package local.tin.tests.log.aggregates;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author benitodarder
 * @param <L> extends LogEntry
 */
public abstract class LogEntriesPool<L extends LogEntry> implements ILogEntriesPool<L>{

    private Map<UUID, L> entries;

    public LogEntriesPool() {
        entries = new ConcurrentHashMap<>();
    }    
    
    @Override
    public void put(UUID uuid, L logEntry) {
        entries.put(uuid, logEntry);
    }
    
    @Override
    public L get(UUID uuid) {
        return entries.get(uuid);
    }
    
    @Override
    public L remove(UUID uuid) {
        return entries.remove(uuid);
    }
    
    @Override
    public void resetPool() {
        entries = new ConcurrentHashMap<>();
    }
}
