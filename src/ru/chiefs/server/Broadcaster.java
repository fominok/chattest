package ru.chiefs.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by yolo on 24.09.14.
 */
public class Broadcaster {
    private ArrayList<Socket> clients;
    private ArrayList<Thread> clientThreads;
    private ArrayList<DataOutputStream> clientOutputs;
    private IServerUi ui;

    public Broadcaster(IServerUi ui){
        this.ui = ui;
        this.clients = new ArrayList<>();
    }

    public void addClient(Socket socket){
        clients.add(socket);
        clientThreads.add(new Thread(new Runnable() {
            boolean working = true;
            Socket thisClient = socket;
            String temp = new String();
            @Override
            public void run() {
                try{
                    DataInputStream input = new DataInputStream(thisClient.getInputStream());
                    while (working) {
                        temp = input.readUTF();
                        broadcast(temp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    ui.showLogMessage("Someone has disconnected");
                    working = false;
                    removeDisconnected(thisClient);
                }
            }
        }));
        clientThreads.get(clientThreads.size()).start();
    }

    private void broadcast(String message){
        for (DataOutputStream outputStream : clientOutputs){
            try {
                outputStream.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
                ui.showLogMessage("Someone has disconnected");
                removeDisconnected(outputStream);
            }
        }
    }

    private void removeDisconnected(Socket sock){
        int index = clients.indexOf(sock);
        clients.remove(index);
        clientOutputs.remove(index);
        clientThreads.remove(index);
    }

    private void removeDisconnected(DataOutputStream outputStream){
        int index = clientOutputs.indexOf(outputStream);
        clients.remove(index);
        clientOutputs.remove(index);
        clientThreads.remove(index);
    }
}
