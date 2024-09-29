package ru.otus.chat.server;
import ru.otus.chat.utils.InboundMessageHandler;
import ru.otus.chat.utils.User;

import java.io.IOException;


public class InboundMessageHandlerServerImpl implements InboundMessageHandler {

    private ClientHandler client;
    private User user;
    private Server server;

    public InboundMessageHandlerServerImpl(ClientHandler client) {
        this.client = client;
        this.user = client.getUser();
        this.server = client.getServer();
    }

    @Override
    public void handleMessage(String message) throws IOException {
        System.out.println(message);
        if (message.startsWith("/exit")){
            client.disconnect();
            client.getMessageSender().send("/exitok");
        }

        if (message.startsWith("/kick") && user.getRole().equals("ADMIN")){
            String[] messageWords = message.split(" ");
            String kickingUserUsername = messageWords[1];
            System.out.println("kickingUserUsername: " + kickingUserUsername);
            ClientHandler kickingClient = server.getClientByUsername(kickingUserUsername);
            kickingClient.getMessageSender().send("/exitok");
        }
        client.getServer().broadcastMessage(client.getUser().getName() + ": " + message);
    }

}
