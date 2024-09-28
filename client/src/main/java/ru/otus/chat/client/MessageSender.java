package ru.otus.chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MessageSender extends Thread {

    Client client;
    DataOutputStream outputStream;

    public MessageSender (DataOutputStream outputStream, Client client) {
        this.outputStream = outputStream;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            try {
                outputStream.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (message.startsWith("/exit")) {
                break;
            }
        }
        client.getConnection().disconnect();
    }
}
