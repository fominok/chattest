package ru.chiefs.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by yolo on 20.09.14.
 */
public class Connector {
    static int port = 1337;

    public Socket connectAsClient(InetAddress ip) throws IOException {
        Socket socket = new Socket(ip,port);
        return socket;
    }
}
