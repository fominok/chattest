package ru.chiefs.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by yolo on 24.09.14.
 */
public class Acceptor {
    private ServerSocket socket;
    private boolean working;
    private IServerUi ui;
    private Broadcaster broadcaster;

    public Acceptor(ServerSocket socket, IServerUi ui){
        this.socket = socket;

        this.ui = ui;
        broadcaster = new Broadcaster(ui);
    }

    public void run(){
        working = true;
        while (working) {
            try {
                broadcaster.addClient(socket.accept());
            } catch (IOException e) {
                e.printStackTrace();
                ui.showLogMessage(e.getMessage());
            }
        }
    }

    /*public void stop() {
        working = false;
        for(Socket client : connectedClients){
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                ui.showLogMessage(e.getMessage());
            }
        }
    }*/
}
