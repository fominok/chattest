package ru.chiefs.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by yolo on 18.09.14.
 */
public class Messenger {
    private Socket sock;
    private IMessengerUi userInterface;
    private Thread receiveThread;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private Boolean working;

    public Messenger(Socket sock,final IMessengerUi userInterface) throws IOException {
        this.sock = sock;
        this.userInterface = userInterface;
        working = true;


        inputStream = new DataInputStream(sock.getInputStream());
        outputStream = new DataOutputStream(sock.getOutputStream());

        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while(working) {
                    try { //Надо бы вынести обработчик, по-хорошему. ХЗ какт
                        message = inputStream.readUTF();
                        userInterface.showMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        userInterface.showLog(e.getMessage());
                        working = false;
                    }
                }
            }
        });
        receiveThread.start();
    }


    public void sendMessage (String message) throws IOException {
        outputStream.writeUTF(message);
        outputStream.flush();
    }

    public void endSession() throws IOException {
        inputStream.close();
        outputStream.close();
        working = false;
        sock.close();
    }
}
