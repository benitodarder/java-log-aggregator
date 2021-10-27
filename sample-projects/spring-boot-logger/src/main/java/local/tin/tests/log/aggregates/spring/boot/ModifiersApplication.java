package local.tin.tests.log.aggregates.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.web.servlet.ServletComponentScan;
 
@ServletComponentScan
@SpringBootApplication
public class ModifiersApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(ModifiersApplication.class, args);
    }
}