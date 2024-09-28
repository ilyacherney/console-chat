package ru.otus.chat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketException;

public class MessageReceiver extends Thread {
    private final DataInputStream inputStream;
    private final MessageHandler messageHandler;

    public MessageReceiver(DataInputStream inputStream, MessageHandler messageHandler1) {
        this.inputStream = inputStream;
        this.messageHandler = messageHandler1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inputStream.readUTF();
                messageHandler.handleMessage(message);
            }
        } catch (SocketException e) {
            this.interrupt();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
