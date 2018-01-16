package pri.smilly.config;

import org.springframework.context.annotation.Configuration;
import pri.smilly.util.SystemUtil;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class SystemConfiguration {

    public static File cachePath;

    @PostConstruct
    public void initial() throws Exception {
        cachePath = SystemUtil.getResource("cache");
    }


}
