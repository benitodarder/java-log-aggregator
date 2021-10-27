package local.tin.tests.log.aggregates;

import java.util.UUID;

/**
 *
 * @author benitodarder
 */
public interface ILogEntry {
    
    /**
     * Initialize/start the log entry. Assigns UUID if not provided.  
     * If timestamp is not set, the current one is assigned.
     * 
     * WARNING! It may update provided LogStep
     * 
     * @param logstep as LogStep.
     * @throws local.tin.tests.log.aggregates.LogException
     */
    public void initialize(LogStep logstep) throws LogException;
    
    /**
     * Adds step the the log entry.
     * 
     * WARNING! It may update provided LogStep
     * 
     * @param logstep as LogStep.
     * @throws local.tin.tests.log.aggregates.LogException
     */
    public void append(LogStep logstep) throws LogException;
    
    /**
     * Ends this log entry: 
     * 
     * <ul>
     * <li>Calls logger with built string.</li>
     * <li>Persists it</li>
     * <li>Enqueues it</li>
     * </ul>
     * 
     * WARNING! It may update provided LogStep
     * 
     * @param logstep as LogStep
     * @throws LogException 
     */
    public void finalize(LogStep logstep) throws LogException;
    
    /**
     * Returns log entry identifier.
     * 
     * @return String.
     */
    public String getId();
}
