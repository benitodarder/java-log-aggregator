package local.tin.tests.log.aggregates.spring.boot.controllers;

import java.util.logging.Level;
import local.tin.tests.log.aggregates.LogException;
import local.tin.tests.log.aggregates.LogStep;
import local.tin.tests.log.aggregates.spring.boot.logging.Sl4jEntriesExecutor;
import local.tin.tests.log.aggregates.spring.boot.services.LowerCaser;
import local.tin.tests.log.aggregates.spring.boot.services.LowerCaserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author benitodarder
 */
@RestController
@RequestMapping(path = "/lowerCaser")
public class LowerCaseWS {

    private static final Logger LOGGER = LoggerFactory.getLogger(LowerCaserImpl.class);
    @Autowired
    private Sl4jEntriesExecutor sl4jEntriesExecutor;
    @Autowired
    private LowerCaser lowerCaser;

    @PostMapping(path = "/", consumes = {MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> withPOST(@RequestBody String source) {
        LogStep logStep = new LogStep();
        logStep.setId(MDC.get("X-Request-Id"));
        logStep.setMessage("In LowerCaseWS");
        try {
            sl4jEntriesExecutor.append(logStep);
        } catch (LogException ex) {
              LOGGER.error("Awn... Crap...", ex);
        }
        return new ResponseEntity(lowerCaser.transform(source), HttpStatus.OK);
    }

    @GetMapping(path = "/", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> withGET(@RequestParam(value = "source") String source) {
        return new ResponseEntity(lowerCaser.transform(source), HttpStatus.OK);
    }

}
