package pri.smilly.demo.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Component
public class RequestUrlAddPrefixHandlerMapping extends RequestMappingHandlerMapping {
    @Value("${server.mvc.request.mapping.head}")
    private String mappingHead;

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        RequestMappingInfo mappingHead = RequestMappingInfo.paths(this.mappingHead.split("/")).build();
        mappingHead.combine(mapping);
        super.registerHandlerMethod(handler, method, mappingHead);
    }
}
