package local.tin.tests.log.aggregates.spring.boot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 *
 * @author benitodarder
 */
@Service
public class LowerCaserImpl implements LowerCaser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LowerCaserImpl.class);
    
    @Override
    public String transform(String source) {
        LOGGER.info("LowerCaser about  to transform string: " + source);
        return source.toLowerCase();
    } 

}
