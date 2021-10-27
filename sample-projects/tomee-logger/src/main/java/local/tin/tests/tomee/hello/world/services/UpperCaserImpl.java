package local.tin.tests.tomee.hello.world.services;

import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import local.tin.tests.tomee.hello.world.logging.Sl4jEntriesExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
/**
 *
 * @author benitodarder
 */
public class UpperCaserImpl implements UpperCaser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UpperCaserImpl.class);
    
    @Override
    public String transform(String source) {
        LOGGER.info("UpperCaser about  to transform string: " + source);
        LogStep logStep = new LogStep();
        logStep.setId(MDC.get("X-Request-Id"));
        logStep.setMessage("Logging through aggregate at UpperCaserImpl.transform!!!");
        LOGGER.info(" With request id: {}", MDC.get("X-Request-Id"));
        try {
            Sl4jEntriesExecutor.getInstance().append(logStep);
        } catch (LogException ex) {
            LOGGER.error("Awn... Crap...", ex);
        }        
        return source.toUpperCase();
    } 

}
