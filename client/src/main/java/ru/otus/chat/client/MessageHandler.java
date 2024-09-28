package ru.otus.chat.client;

public class MessageHandler {
    Client client;

    public MessageHandler (Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void handleMessage(String message) {
        if (message.startsWith("/exitok")) {
            client.getConnection().disconnect();
            client.getMessageReceiver().interrupt();
            client.getMessageSender().interrupt();
        }
        System.out.println(message);
    }

}
