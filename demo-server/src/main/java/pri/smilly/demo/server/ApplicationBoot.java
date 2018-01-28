package pri.smilly.demo.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@EnableWebSocket
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
@ServletComponentScan
@MapperScan("pri.smilly.demo.server.common.domain")
public class ApplicationBoot {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            ApplicationContext ctx = SpringApplication.run(ApplicationBoot.class, args);
            long time = System.currentTimeMillis() - start;
            log.info("application launch up success, time spend " + time);
        } catch (IllegalStateException e) {
            log.error("application launch error, " + e.getMessage(), e);
        }
    }

}
