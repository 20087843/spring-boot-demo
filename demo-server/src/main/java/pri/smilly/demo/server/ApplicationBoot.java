package pri.smilly.demo.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
public class ApplicationBoot {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            SpringApplication.run(ApplicationBoot.class, args);
            long time = System.currentTimeMillis() - start;
            log.info("application launch up success, time spend " + time);
        } catch (IllegalStateException e) {
            log.error("application launch error, " + e.getMessage(), e);
        }
    }

}
