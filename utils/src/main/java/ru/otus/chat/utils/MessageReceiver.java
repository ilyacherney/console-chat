package ru.otus.chat.utils;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class MessageReceiver extends Thread {
    private boolean isActive = true;
    InboundMessageHandler inboundMessageHandler;
    DataInputStream inputStream;

    public MessageReceiver(InboundMessageHandler inboundMessageHandler, DataInputStream inputStream) {
        this.inboundMessageHandler = inboundMessageHandler;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                String message = inputStream.readUTF();
                inboundMessageHandler.handleMessage(message);
            } catch (EOFException | SocketException e) {
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
