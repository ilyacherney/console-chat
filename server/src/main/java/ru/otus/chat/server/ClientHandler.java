package ru.otus.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String username;
    private String userRole;
    private static int userCount = 0;
    private boolean isAcitve;

    public String getUsername() {
        return username;
    }

    public String getUserRole() { return userRole; }

    public ClientHandler(Server server, Socket socket) throws IOException {
        isAcitve = true;
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        userCount++;
        username = "user" + userCount;
        userRole = userCount == 1 ? "ADMIN" : "USER";
        new Thread(() -> {
            try {
                System.out.println("Клиент подключился ");
                while (isAcitve) {
                    String inputText = in.readUTF();
                    if (inputText.startsWith("/")) {
                        Message message = parseMessage(inputText);

                        if (inputText.startsWith("/exit")){
                            sendMessage("/exitok");
                            break;
                        }

                        if (message.getCommand().equals("/kick") && userRole.equals("ADMIN")) {
                            String kickingUserUsername = message.getReceiverUsername();
                            ClientHandler kickingClient = server.getClientByUsername(kickingUserUsername);
                            kickingClient.isAcitve = false;
                            kickingClient.sendMessage("/exitok");
                        }
                        

                    } else {
                        server.broadcastMessage(username + " : " + inputText);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message parseMessage(String inputText) {
        String[] messageElements = inputText.split(" ", 3);

        switch (messageElements.length) {
            case 2: return new Message(messageElements[0], messageElements[1], null);
            case 3:return new Message(messageElements[0], messageElements[1], messageElements[2]);
            default: return null;
        }
    }

    public void disconnect(){
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
