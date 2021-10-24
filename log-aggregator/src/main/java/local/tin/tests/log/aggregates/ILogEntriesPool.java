package local.tin.tests.log.aggregates;

import java.util.UUID;

/**
 *
 * @author benitodarder
 */
public interface ILogEntriesPool<L extends LogEntry> {
    
    /**
     * Adds log entry to the pool. Throws exception when already present.
     * 
     * @param uuid as UUID.
     * @param logEntry as extension of LogEntry.
     * @throws LogException 
     */
    public void put(UUID uuid, L logEntry) throws LogException;
    
    /**
     * Returns log entry from the pool.
     * 
     * @param uuid as UUID.
     * @return logEntry as extension of LogEntry.
     */    
    public L get(UUID uuid);
    
    /**
     * Removes log entry from the pool.
     * 
     * @param uuid as UUID.
     * @return logEntry as extension of LogEntry.
     */      
    public L remove(UUID uuid);
    
     /**
     * Empties the pool.
     * 
     */  
    public void resetPool();
    

}
