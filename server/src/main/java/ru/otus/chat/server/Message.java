package ru.otus.chat.server;

public class Message {
    private final String command;
    private final String receiverUsername;
    private final String text;

    public Message(String command, String receiverUsername, String text) {
        this.command = command;
        this.receiverUsername = receiverUsername;
        this.text = text;
    }

    public String getCommand() {
        return command;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getText() {
        return text;
    }
}
