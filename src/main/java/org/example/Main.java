package org.example;

public class Main {
    static public void main(String[] args) {
        new Thread(new ServerController()).start();
    }
}