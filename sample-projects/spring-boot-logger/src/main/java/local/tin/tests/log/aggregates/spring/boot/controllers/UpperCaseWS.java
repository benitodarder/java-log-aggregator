package local.tin.tests.log.aggregates.spring.boot.controllers;

import local.tin.tests.log.aggregates.spring.boot.services.UpperCaser;
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
@RequestMapping(path = "/upperCaser")
public class UpperCaseWS {

    @Autowired
    private UpperCaser upperCaser;
    
    @PostMapping(path = "/", consumes = {MediaType.TEXT_PLAIN_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> withPOST(@RequestBody String source) {
        return new ResponseEntity(upperCaser.transform(source), HttpStatus.OK);
    }

    @GetMapping(path = "/", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> withGET(@RequestParam(value = "source") String source) {
        return new ResponseEntity(upperCaser.transform(source), HttpStatus.OK);
    }
     
}
