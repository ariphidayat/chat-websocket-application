package org.arip.websocket.chat;

import java.io.IOException;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Arip Hidayat
 */
@ServerEndpoint("/chat")
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void onOpen(Session session) throws IOException {
        String message = session.getId() + " connected!";
        log.info(message);
        session.getBasicRemote().sendText(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
        log.info(message);
        session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.info(session.getId() + " disconnected!");
        session.getBasicRemote().sendText(session.getId() + " disconnected!");
    }
}