package org.example;

import java.io.IOException;

public class ServerController implements Runnable{
    private ChatServer server;

    public ServerController() {
        server = ChatServer.getInstance();
    }

    @Override
    public void run() {
        Client client;
        while (true) {
            try {
                client = server.accept();
                Message greet = new Message("Your nickname?", ChatServer.SERVER_USER);
                client.sendMessageToClient(greet);
                System.out.println(greet);
            } catch (IOException e) {
                // TODO: inspect on DANGER!!!
                throw new RuntimeException(e); // TODO: is it DANGER?
            }
            new Thread(new ClientController(client)).start();
        }

    }
}
