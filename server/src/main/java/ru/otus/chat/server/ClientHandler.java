package ru.otus.chat.server;

import ru.otus.chat.utils.Connection;
import ru.otus.chat.utils.MessageReceiver;
import ru.otus.chat.utils.MessageSender;
import ru.otus.chat.utils.User;
import java.io.IOException;

public class ClientHandler {
    private Connection connection;
    private User user;
    private Server server;
    private InboundMessageHandlerServerImpl inboundMessageHandler;
    private MessageSender messageSender;
    private MessageReceiver messageReceiver;

    public ClientHandler(Server server, Connection connection) throws IOException {
        this.connection = connection;
        this.user = new User();
        this.server = server;
        this.inboundMessageHandler = new InboundMessageHandlerServerImpl(this);
        this.messageSender = new MessageSender(connection.getOutputStream());
        this.messageReceiver = new MessageReceiver(inboundMessageHandler, connection.getInputStream());

        messageReceiver.start();
        System.out.println("Клиент подключился ");
    }

    public Server getServer() {
        return server;
    }

    public User getUser() {
        return user;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }


    public void disconnect(){
        messageReceiver.setActive(false);
        server.unsubscribe(this);
        connection.disconnect();
    }
}
