package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    private String nickname;
    private boolean open;
    private BufferedWriter out;
    private BufferedReader in;

    public Client(String nickname) {
        this.nickname = nickname;
    }

    public Client(Socket socket) {
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    // TODO: add sendBehaviour
    public void sendMessageToClient(Message msg) throws IOException {
        out.write(msg.toString());
        out.flush();
    }

    public Message receiveMessageFromClient() throws IOException {
        Message receivedMessage = new Message();
        String text = in.readLine();

        receivedMessage.setFrom(this);
        receivedMessage.setText(text);

        System.out.println(receivedMessage);

        return receivedMessage;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen() {
        this.open = true;
    }

    public void setClose() {
        this.open = false;
    }
}
