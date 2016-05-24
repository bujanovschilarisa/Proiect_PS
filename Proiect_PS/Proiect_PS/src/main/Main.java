package main;

import java.io.IOException;

import bl.ChatServer;

public class Main {

    public static void main(final String... args) throws IOException {
        ChatServer jsonServer = new ChatServer();
        jsonServer.startServer();
    }
}
