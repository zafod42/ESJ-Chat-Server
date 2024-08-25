package org.example;

import java.io.IOException;
import java.util.List;

public class ClientController implements Runnable {

    private final Client client;
    private final ChatServer server;

    public ClientController(Client client) {
        this.client = client;
        server = ChatServer.getInstance();
    }

    @Override
    public void run() {
        Message message;
        try {
            message = client.receiveMessageFromClient();
            client.setNickname(message.getText());
            server.broadcast(new Message(client.getNickname() + " joined to server!", ChatServer.SERVER_USER));
        } catch (IOException e) {
            getClients().remove(client);
            client.setClose();
        }
        while(client.isOpen()) {
            try {
                message = client.receiveMessageFromClient();
                server.broadcast(message);
            } catch (IOException e) {
                getClients().remove(client);
                client.setClose();
            }
        }
    }

    private List<?> getClients() {
        return server.getClients();
    }

}
