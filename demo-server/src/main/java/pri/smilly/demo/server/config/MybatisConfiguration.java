package pri.smilly.demo.server.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("pri.smilly.demo.server.common.domain")
public class MybatisConfiguration {

}
