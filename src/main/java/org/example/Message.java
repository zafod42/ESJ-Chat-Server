package org.example;

public class Message {

    private Client from;
    private String text;

    public Message() {

    }
    public Message(String text, Client from) {
        this.text = text;
        this.from = from;
    }

    public Message(String text) {
        this.text = text;
    }

    public void setFrom(Client from) {
        this.from = from;
    }

    public Client getFrom() {
        return from;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return from.getNickname() + ": " + text + "\n";
    }
}
