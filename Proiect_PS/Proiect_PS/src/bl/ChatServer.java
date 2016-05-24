package bl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.net.httpserver.HttpServer;

public class ChatServer {
    private UserService userService = new UserService();

    private static final String HOSTNAME = "192.168.0.2";
    private static final int PORT = 8080;
    private static final int BACKLOG = 1;
    private final Map<String, List<String>> usersWithMessages = new ConcurrentHashMap<String, List<String>>();

    public void startServer() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOSTNAME, PORT);
        final HttpServer server = HttpServer.create(inetSocketAddress, BACKLOG);
        ChatHttpHandler messageRequesterHandler = new MessageRequesterHandler(this);
        ChatHttpHandler userHandler = new UserHandler(this);
        ChatHttpHandler messageReceiverHandler = new MessageReceiverHandler(this);
        server.createContext(MessageRequesterHandler.CONTEXT_SEND_MESSAGE_TO_THREAD, messageRequesterHandler);
        server.createContext(UserHandler.CONTEXT_LOGIN, userHandler);
        server.createContext(MessageReceiverHandler.CONTEXT_OPEN_CONVERSATION, messageReceiverHandler);
        server.start();
    }

    public void addUser(String username) {
        usersWithMessages.put(username, new CopyOnWriteArrayList<String>());
    }

    public void addMessageForUser(String username, String message) {
        for (Entry<String, List<String>> entrySet : usersWithMessages.entrySet()) {
            List<String> listOfMsg = entrySet.getValue();
            listOfMsg.add(username + ":" + message);
        }
    }

    public List<String> getMesssagesForUser(String username) {
        return usersWithMessages.get(username);
    }

    public boolean isUser(String username, String password) {
        return userService.isUser(username, password);
    }
}