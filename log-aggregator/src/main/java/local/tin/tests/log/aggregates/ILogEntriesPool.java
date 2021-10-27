package local.tin.tests.log.aggregates;

/**
 *
 * @author benitodarder
 * @param <L>
 */
public interface ILogEntriesPool<L extends LogEntry> {
    
    /**
     * Adds log entry to the pool. Throws exception when already present.
     * 
     * @param id as String.
     * @param logEntry as extension of LogEntry.
     * @throws LogException 
     */
    public void put(String id, L logEntry) throws LogException;
    
    /**
     * Returns log entry from the pool.
     * 
     * @param id as String.
     * @return logEntry as extension of LogEntry.
     */    
    public L get(String id);
    
    /**
     * Removes log entry from the pool.
     * 
     * @param id as String.
     * @return logEntry as extension of LogEntry.
     */      
    public L remove(String id);
    
     /**
     * Empties the pool.
     * 
     */  
    public void resetPool();
    

}
