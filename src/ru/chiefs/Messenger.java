package ru.chiefs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by yolo on 18.09.14.
 */
public class Messenger {
    Socket sock;
    IMessengerUi userInterface;
    Thread receiveThread;

    DataInputStream inputStream;
    DataOutputStream outputStream;

    Boolean working;

    public Messenger(Socket sock, IMessengerUi userInterface){
        this.sock = sock;
        this.userInterface = userInterface;
        working = true;

        try {
            inputStream = new DataInputStream(sock.getInputStream());
            outputStream = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            userInterface.showLog(e.getMessage());
        }

        receiveThread = new Thread(){
            @Override
            public void run() {
                String message;
                while(working) {
                    try {
                        message = inputStream.readUTF();
                        userInterface.showMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        userInterface.showLog(e.getMessage());
                    }
                }
            }
        };
        receiveThread.run();
    }


    public void sendMessage (String message) throws IOException {
        //TODO:using socket to send some shiet
        outputStream.writeUTF(message);
        outputStream.flush();
    }
}
