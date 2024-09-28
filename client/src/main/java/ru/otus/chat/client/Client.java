package ru.otus.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;
    private final Connection connection;
    private final MessageSender messageSender;
    private final MessageReceiver messageReceiver;
    private final MessageHandler messageHandler;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.connection = new Connection(this);
        this.messageSender = new MessageSender(connection.getOutputStream(), this);
        this.messageHandler = new MessageHandler(this);
        this.messageReceiver = new MessageReceiver(connection.getInputStream(), messageHandler);
    }

    public Connection getConnection() {
        return connection;
    }

    public MessageReceiver getMessageReceiver() {
        return messageReceiver;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void start() {
        messageSender.start();
        messageReceiver.start();
    }
}
