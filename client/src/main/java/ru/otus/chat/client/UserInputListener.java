package ru.otus.chat.client;


import ru.otus.chat.utils.MessageSender;

import java.io.IOException;
import java.util.Scanner;

public class UserInputListener extends Thread {

    private final Client client;
    private final Scanner scanner;
    private volatile boolean isActive;
    private final MessageSender messageSender;

    public UserInputListener(Client client) {
        this.client = client;
        this.scanner = new Scanner(System.in);
        this.isActive = true;
        this.messageSender = new MessageSender(client.getConnection().getOutputStream());
    }


    @Override
    public void run() {
        while (isActive) {
            try {
                String message = scanner.nextLine();
                messageSender.send(message);
                if (message.startsWith("/exit")) break;
            } catch (IOException e) {
                System.err.println("Error while sending message: " + e.getMessage());
                isActive = false;
            }
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

