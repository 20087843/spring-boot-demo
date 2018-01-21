package pri.smilly.demo.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Controller
public class TextMessageController extends BaseWebSocketController<String> {

    @Override
    public void sendData(WebSocketSession session, String data) throws IOException {
        TextMessage message = new TextMessage(data);
        sendMessage(session, message);
    }

}
