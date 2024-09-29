package ru.otus.chat.utils;

import java.io.IOException;

public interface InboundMessageHandler {

    public void handleMessage(String message) throws IOException;
}
