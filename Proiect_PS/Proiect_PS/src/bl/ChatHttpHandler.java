package bl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public abstract class ChatHttpHandler implements HttpHandler {

    protected static final int BACKLOG = 1;

    protected static final String HEADER_ALLOW = "Allow";
    protected static final String HEADER_CONTENT_TYPE = "Content-Type";

    protected static final Charset CHARSET = StandardCharsets.UTF_8;

    protected static final int STATUS_OK = 200;
    protected static final int STATUS_METHOD_NOT_ALLOWED = 405;

    protected static final int NO_RESPONSE_LENGTH = -1;

    protected static final String METHOD_GET = "GET";
    protected static final String METHOD_POST = "POST";
    protected static final String METHOD_OPTIONS = "OPTIONS";
    protected static final String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

    protected static final String ERROR = "ERROR";

    protected HttpServer server;

    protected ChatServer chatServer;

    public ChatHttpHandler(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    public abstract void handle(HttpExchange he) throws IOException;

    public static String serializeToJson(HashMap<String, ArrayList<HashMap<String, String>>> myClass) {
        Gson gson = new Gson();
        String j = gson.toJson(myClass);
        return j;
    }

    public static HashMap<String, String> jsonToObject(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, HashMap.class);
    }

    public void initServer(HttpServer server) {
        this.server = server;
    }
}
