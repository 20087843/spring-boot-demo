package pri.smilly.demo.server;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Slf4j
@EnableWebMvc
@EnableWebSocket
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
@ServletComponentScan
@MapperScan("pri.smilly.demo.server.domain")
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
