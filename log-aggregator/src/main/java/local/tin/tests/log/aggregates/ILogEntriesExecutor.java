package local.tin.tests.log.aggregates;

/**
 *
 * @author benitodarder
 */
public interface ILogEntriesExecutor<L extends LogEntry> {
    
    /**
     * Creates new instances for all inner elements.
     * 
     * @throws local.tin.tests.log.aggregates.LogException
     */
    public void reset() throws LogException;
    
    /**
     * Creates a initial LogEntry with given LogStep.
     * 
     * Returns given LogStep with updated fields if required.
     * 
     * @param logStep as LogStep
     * @return LogStep
     * @throws local.tin.tests.log.aggregates.LogException
     */
    public LogStep initialize(LogStep logStep) throws LogException;   
}
