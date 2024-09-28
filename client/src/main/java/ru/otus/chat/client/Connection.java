package ru.otus.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Client client;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public Connection (Client client) throws IOException {
        this.client = client;
        this.socket = new Socket("localhost", 8189);
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void disconnect() {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
