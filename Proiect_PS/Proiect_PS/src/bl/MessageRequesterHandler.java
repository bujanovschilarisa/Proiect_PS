package bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class MessageRequesterHandler extends ChatHttpHandler {
    public static final String CONTEXT_SEND_MESSAGE_TO_THREAD = "/sendMessageToThread";

    public MessageRequesterHandler(ChatServer chatServer) {
        super(chatServer);
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        try {
            final Headers headers = he.getResponseHeaders();
            final String requestMethod = he.getRequestMethod().toUpperCase();
            switch (requestMethod) {
            case METHOD_POST:
                String body = IOUtils.toString(he.getRequestBody(), "UTF-8");
                HashMap<String, String> objectRequest = jsonToObject(body);
                headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
                HashMap<String, ArrayList<HashMap<String, String>>> response = new HashMap<String, ArrayList<HashMap<String, String>>>();

                String username = objectRequest.get("username");
                try {
                    ArrayList<HashMap<String, String>> jsonArray = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> obj = new HashMap<String, String>();
                    List<String> listOfMessage = chatServer.getMesssagesForUser(username);
                    for (String msg : listOfMessage) {
                        obj.put("message", msg);
                        listOfMessage.clear();
                    }
                    jsonArray.add(obj);
                    response.put("posts", jsonArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final byte[] rawResponseBody = serializeToJson(response).getBytes(CHARSET);
                he.sendResponseHeaders(STATUS_OK, rawResponseBody.length);
                he.getResponseBody().write(rawResponseBody);
                break;
            case METHOD_OPTIONS:
                headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                he.sendResponseHeaders(STATUS_OK, NO_RESPONSE_LENGTH);
                break;
            default:
                headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                he.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            he.close();
        }
    }
}
