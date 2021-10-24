package local.tin.tests.log.aggregates;

import java.util.UUID;

/**
 *
 * @author benitodarder
 */
public abstract class LogEntry implements ILogEntry {

    private UUID uuid;

    protected abstract void initializeSpecifics(LogStep logstep);

    @Override
    public void initialize(LogStep logstep) throws LogException {
        if (logstep.getId() == null) {
            uuid = UUID.randomUUID();
            logstep.setId(uuid);
        } else {
            uuid = logstep.getId();
        }
        initializeSpecifics(logstep);
    }

    @Override
    public UUID getId() {
        return uuid;
    }

}
