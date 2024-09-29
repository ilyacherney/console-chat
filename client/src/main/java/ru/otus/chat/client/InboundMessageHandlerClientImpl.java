package ru.otus.chat.client;

import ru.otus.chat.utils.InboundMessageHandler;

public class InboundMessageHandlerClientImpl implements InboundMessageHandler {

    private final Client client;

    public InboundMessageHandlerClientImpl(Client client) {
        this.client = client;
    }

    @Override
    public void handleMessage(String message) {
        if (message.startsWith("/exitok")) {
            client.stop();
        }
        System.out.println(message);
    }
}
