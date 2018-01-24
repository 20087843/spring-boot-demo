package pri.smilly.demo.server.controller.websocket;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

import java.io.IOException;

@Slf4j
public abstract class BaseWebSocketController<T> implements WebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            handleMessage(session, (TextMessage) message);
        } else if (message instanceof BinaryMessage) {
            handleMessage(session, (BinaryMessage) message);
        } else {

        }
    }

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public abstract void sendData(WebSocketSession session, T data) throws IOException;

    protected void sendMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        session.sendMessage(message);
    }

    @SneakyThrows
    protected void close(WebSocketSession session) {
        session.close();
    }
}
