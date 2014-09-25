package ru.chiefs.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by yolo on 25.09.14.
 */
public class Client {
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private Socket socket;

    public Client (Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {

        return outputStream;
    }
}
