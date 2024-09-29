package ru.otus.chat.client;

import ru.otus.chat.utils.Connection;
import ru.otus.chat.utils.MessageReceiver;

import java.io.IOException;

public class Client {
    private final Connection connection;
    private final MessageReceiver messageReceiver;
    private final UserInputListener userInputListener;
    private final InboundMessageHandlerClientImpl inboundMessageHandler;

    public Client() throws IOException {
        this.connection = new Connection("localhost", 8189);
        this.inboundMessageHandler = new InboundMessageHandlerClientImpl(this);
        this.messageReceiver = new MessageReceiver(inboundMessageHandler, connection.getInputStream());
        this.userInputListener = new UserInputListener(this);
    }

    public Connection getConnection() {
        return connection;
    }

    public void start() {
        messageReceiver.start();
        userInputListener.start();
    }

    public void stop() {
        messageReceiver.setActive(false);
        userInputListener.setActive(false);
        connection.disconnect();
    }
}
