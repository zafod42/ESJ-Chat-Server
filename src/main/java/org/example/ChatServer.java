package org.example;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ChatServer {

    final static public Client SERVER_USER = new Client("Server");
    final static private int DEFAULT_PORT = 4444;
    final static private String DEFAULT_HOST = "localhost";
    static private ChatServer instance = null;
    private final ServerSocket serverSocket;
    private final int port;
    private final InetAddress inetAddress;

    private final List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    private ChatServer(int port, String host) {
        try {
            this.port = port;
            inetAddress = InetAddress.getByName(host);
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static public synchronized ChatServer getInstance() {
        if (instance == null) {
            instance = new ChatServer(DEFAULT_PORT, DEFAULT_HOST);
            System.out.println("Server started at: " + instance.getInetAddress() + ":" + instance.getPort());
        }

        return instance;
    }

    public void broadcast(Message msg) {
        Iterator<Client> iterator = clients.listIterator();
        while(iterator.hasNext()) {
            Client client = iterator.next();
            if (client.equals(msg.getFrom())) continue;
            try {
                client.sendMessageToClient(msg);
            } catch (IOException e) {
                iterator.remove();
            }
        }
    }

    public Client accept() throws IOException {
        Socket clientSocket = serverSocket.accept();

        Client client = new Client(clientSocket);
        client.setOpen();
        System.out.println("Connection received");

        synchronized (clients) {
            clients.add(client);
        }

        return client;
    }

    public List<?> getClients() {
        return clients;
    }

    public void shutdown() throws IOException {
        serverSocket.close();
    }

    public int getPort() {
        return port;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
}
