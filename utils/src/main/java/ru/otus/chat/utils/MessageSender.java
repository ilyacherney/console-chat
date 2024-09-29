package ru.otus.chat.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MessageSender {

    private final DataOutputStream outputStream;

    public MessageSender(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void send (String message) throws IOException {
            outputStream.writeUTF(message);
    }
}
