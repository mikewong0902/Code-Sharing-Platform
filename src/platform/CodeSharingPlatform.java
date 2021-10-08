package platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class CodeSharingPlatform {

    private static final Logger log = LoggerFactory.getLogger(CodeSharingPlatform.class);
    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }
}
