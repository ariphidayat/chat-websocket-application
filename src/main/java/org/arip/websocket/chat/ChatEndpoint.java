package org.arip.websocket.chat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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

    private Session session;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        chatEndpoints.add(this);

        String message = session.getId() + " connected!";
        log.info(message);
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
        log.info(message);
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        log.info(session.getId() + " disconnected!");
        broadcast(session.getId() + " disconnected!");
    }

    private static void broadcast(String message) throws IOException {
        for (ChatEndpoint endpoint : chatEndpoints) {
            synchronized(endpoint) {
                endpoint.session.getBasicRemote().sendText(message);
            }
        }
    }
}