package org.arip.websocket.chat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Arip Hidayat
 */
@ServerEndpoint(value="/chat/{username}")
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());

    private Session session;
    private String username;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String,String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        this.session = session;
        this.username = username;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        String message = username + " connected!";
        log.info(message);
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
        log.info(message);

        String to = extractTo(message);
        String toSessionId = getSessionId(to);
        String actualMessage = extractActualMessage(message);
        sendMessageToOneUser(users.get(session.getId()) + " : " + actualMessage, toSessionId);
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

    private static void sendMessageToOneUser(String message, String toSessionId) throws IOException {
        for (ChatEndpoint endpoint : chatEndpoints) {
            synchronized(endpoint) {
                if (endpoint.session.getId().equals(toSessionId)) {
                    endpoint.session.getBasicRemote().sendText(message);
                }
            }
        }
    }

    private String getSessionId(String to) {
        if (users.containsValue(to)) {
            for (String sessionId: users.keySet()) {
                if (users.get(sessionId).equals(to)) {
                    return sessionId;
                }
            }
        }
        return null;
    }

    private String extractTo(String message) {
        String[] to = message.split("~");
        return to[0];
    }

    private String extractActualMessage(String message) {
        String[] to = message.split("~");
        return to[1];
    }
}