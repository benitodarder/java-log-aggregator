package local.tin.tests.log.aggregates.spring.boot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 *
 * @author benitodarder
 */
@Service
public class UpperCaserImpl implements UpperCaser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UpperCaserImpl.class);
    
    @Override
    public String transform(String source) {
        LOGGER.info("UpperCaser about  to transform string: " + source);
        return source.toUpperCase();
    } 

}
