package ru.chiefs.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by yolo on 24.09.14.
 */
public class Broadcaster {
    private ArrayList<Client> clients;
    //private ArrayList<Thread> clientThreads; //в пизду его
    private IServerUi ui;

    public Broadcaster(IServerUi ui){
        this.ui = ui;
        this.clients = new ArrayList<>();
        //this.clientThreads = new ArrayList<>();
    }

    public void addClient(Socket socket) {
        final Client currentClient;
        try{
            currentClient = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
            ui.showLogMessage(e.getMessage());
            return;
        }
        clients.add(currentClient);
        /*try {
            clientOutputs.add(new DataOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            ui.showLogMessage(e.getMessage()); //wtf could happen here
        }*/

        //OMG ANONYMOUS THREAD
        new Thread(new Runnable() {
            boolean working = true;
            String temp;
            @Override
            public void run() {
                try{
                    DataInputStream input = currentClient.getInputStream();
                    while (working) {
                        temp = input.readUTF();
                        broadcast(temp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    ui.showLogMessage("Someone has disconnected");
                    working = false;
                    removeDisconnected(currentClient);
                }
            }
        }).start();
    }

    private void broadcast(String message){
        for (Client client : clients){
            try {
                client.getOutputStream().writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
                ui.showLogMessage("Someone has disconnected");
                removeDisconnected(client);
            }
        }
    }

    private void removeDisconnected(Client client){
        //int index = clients.indexOf(client);
        clients.remove(client);
        //clientThreads.remove(index); //Ящетаю, что тред сам себя успешно завершит. А лист нахер не нужен лол
    }
}
