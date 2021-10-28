package local.tin.tests.tomee.hello.world.logging;

import local.tin.tests.log.aggregates.LogEntry;
import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author benitodarder
 */
public class Sl4jEntry extends LogEntry {
    
    private static final Logger LOGGER_DEBUG = LoggerFactory.getLogger(Sl4jEntry.class);
    private static final Logger LOGGER = LoggerFactory.getLogger("LOG-AGGREGATES");
    private StringBuilder lines;
    
    @Override
    protected void initializeSpecifics(LogStep logstep) {
       lines = new StringBuilder();
       lines.append(logstep.getMessage()).append(System.lineSeparator());
    }

    @Override
    public void append(LogStep logstep) throws LogException {
        lines.append(logstep.getMessage()).append(System.lineSeparator());
    }

    @Override
    public void finalize(LogStep logstep) throws LogException {
        MDC.put("X-Request-Id", logstep.getId());
        lines.append(logstep.getMessage()).append(System.lineSeparator()).append("===================================================");
        LOGGER.info(lines.toString());
    }
    
}
