package local.tin.tests.log.aggregates;

import java.util.UUID;

/**
 *
 * @author benitodarder
 */
public abstract class LogEntry implements ILogEntry {

    private String id;

    protected abstract void initializeSpecifics(LogStep logstep);

    @Override
    public void initialize(LogStep logstep) throws LogException {
        if (logstep.getId() == null) {
            logstep.setId(UUID.randomUUID().toString());
        } 
        id = logstep.getId();
        initializeSpecifics(logstep);
    }

    @Override
    public String getId() {
        return id;
    }

}
