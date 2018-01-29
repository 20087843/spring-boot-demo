package pri.smilly.demo.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class RequestUrlAddPrefixHandlerMapping extends RequestMappingHandlerMapping {
    @Value("${server.mvc.request.mapping.head}")
    private String mappingHead;
    @Value("#{'${server.mvc.request.mapping.basepackage}'.split(',')}")
    private List<String> basePackages;

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        for (String baskPackage : basePackages) {
            if (method.getDeclaringClass().getTypeName().contains(baskPackage)) {
                RequestMappingInfo mappingHead = RequestMappingInfo.paths(this.mappingHead).build();
                mapping = mappingHead.combine(mapping);
                break;
            }
        }

        super.registerHandlerMethod(handler, method, mapping);
    }
}
