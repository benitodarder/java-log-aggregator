package local.tin.tests.log.aggregates.spring.boot.logging;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author benitodarder
 */
public class Sl4jEntry extends LogEntry {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Sl4jEntry.class);
    private StringBuilder lines;
    
    @Override
    protected void initializeSpecifics(LogStep logstep) {
       lines = new StringBuilder();
       lines.append(System.lineSeparator()).append("Initializing log entry:").append(logstep.getMessage()).append(System.lineSeparator());
    }

    @Override
    public void append(LogStep logstep) throws LogException {
        lines.append("Appending log entry:").append(logstep.getMessage()).append(System.lineSeparator());
    }

    @Override
    public void finalize(LogStep logstep) throws LogException {
        lines.append("Finalizing log entry:").append(logstep.getMessage()).append(System.lineSeparator());
        LOGGER.info(lines.toString());
    }
    
}
