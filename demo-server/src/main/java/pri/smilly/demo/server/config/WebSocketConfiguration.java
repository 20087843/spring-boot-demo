package pri.smilly.demo.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import pri.smilly.demo.server.controller.websocket.TextMessageController;
import pri.smilly.demo.server.common.interceptor.WebSocketInterceptor;

@EnableWebSocket
@Configuration
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {
    @Value("${web.socket.idle.timeout}")
    private long idleTimeout;
    @Value("${web.socket.binary.limit}")
    private int binaryLimit;
    @Value("${web.socket.text.limit}")
    private int textLimit;
    @Autowired
    private TextMessageController textMessageController;
    @Autowired
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry handlerRegistry) {
        handlerRegistry.addHandler(textMessageController, "/demo/message")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpointRegistry) {
        endpointRegistry.addEndpoint("/gs-guide-websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
        brokerRegistry.enableSimpleBroker("/topic");
        brokerRegistry.setApplicationDestinationPrefixes("/app");
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean factoryBean = new ServletServerContainerFactoryBean();
        factoryBean.setMaxSessionIdleTimeout(idleTimeout);
        factoryBean.setMaxTextMessageBufferSize(textLimit);
        factoryBean.setMaxBinaryMessageBufferSize(binaryLimit);
        return factoryBean;
    }
}
