package pri.smilly.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import pri.smilly.demo.util.SystemUtil;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Configuration
public class SystemConfiguration implements SignalHandler {

    public static File cachePath;

    @PostConstruct
    public void initial() throws Exception {
        cachePath = SystemUtil.getResource("cache");
        log.info("");
    }

    @Override
    public void handle(Signal signal) {
        log.info("signal " + signal + " triggered");
    }

    public void registerSignal(String... signals) {
        for (String signal : signals) {
            try {
                Signal.handle(new Signal(signal), this);
            } catch (Exception e) {
                log.warn("signal " + signal + " not supported");
            }
        }
    }

}
