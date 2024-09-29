package ru.otus.chat.server;

import ru.otus.chat.utils.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server {
    private int port;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                Connection connection = new Connection(serverSocket.accept());
                subscribe(new ClientHandler(this, connection));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<ClientHandler> getClients() {return clients;}

    public synchronized ClientHandler getClientByUsername(String username){
        for (ClientHandler client : clients) {
            String clientUserName = client.getUser().getName();
            if (Objects.equals(clientUserName, username)) {
                return client;
            }
        }
        return null;
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized void broadcastMessage(String message) throws IOException {
        for (ClientHandler client : clients) {
            client.getMessageSender().send(message);
        }
    }
}
