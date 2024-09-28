package ru.otus.chat.client;

import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) throws IOException {
        new Client("localhost", 8189).start();
    }
}