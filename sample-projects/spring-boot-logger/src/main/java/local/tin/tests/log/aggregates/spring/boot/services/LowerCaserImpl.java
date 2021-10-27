package local.tin.tests.log.aggregates.spring.boot.services;

import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import local.tin.tests.log.aggregates.spring.boot.logging.Sl4jEntriesExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author benitodarder
 */
@Service
public class LowerCaserImpl implements LowerCaser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LowerCaserImpl.class);
    @Autowired
    private Sl4jEntriesExecutor sl4jEntriesExecutor;
    
    @Override
    public String transform(String source) {
        LOGGER.info("LowerCaser about  to transform string: " + source);
        LogStep logStep = new LogStep();
        logStep.setId(MDC.get("X-Request-Id"));
        logStep.setMessage("Logging through aggregate!");
        LOGGER.info(" With request id: {}", MDC.get("X-Request-Id"));
        try {
            sl4jEntriesExecutor.append(logStep);
        } catch (LogException ex) {
            LOGGER.error("Awn... Crap...", ex);
        }
        return source.toLowerCase();
    } 

}
