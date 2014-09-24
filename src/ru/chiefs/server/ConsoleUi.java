package ru.chiefs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

/**
 * Created by yolo on 24.09.14.
 */
public class ConsoleUi implements IServerUi {
    boolean working;

    public ConsoleUi(){

    }

    @Override
    public void showLogMessage(String message) {
        System.err.println(message);
    }

    public void run() {
        working = true;
        int retriesCount = 0;
        while (working) {
            try {
                ServerSocket socket = new ServerSocket(1337);
                Acceptor acceptor = new Acceptor(socket, this);
                acceptor.run();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (retriesCount < 5) {
                        showLogMessage("Cannot open socket. Retrying in 5 seconds");
                        Thread.sleep(5000);
                    }
                    else {
                        showLogMessage("Failed after 5 retries");
                        working = false;
                    }
                    retriesCount++;
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    showLogMessage(e.getMessage());
                    working = false;
                }
            }
        }
    }
}
