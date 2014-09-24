package ru.chiefs.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by yolo on 22.09.14.
 */
public class ConsoleUi implements IMessengerUi {
    Messenger messenger;
    Connector connector;
    Socket socket;

    public void run() throws IOException {
        String userInput = new String();
        Scanner scan = new Scanner(System.in);
        boolean pedor = true;
        connector = new Connector();

        while (pedor) {
            try {
                //System.out.print("айпи слыш: ");
                //userInput = scan.nextLine();
                //InetAddress addr = InetAddress.getByName(userInput);
                InetAddress addr = InetAddress.getByName("178.162.5.219");
                socket = connector.connectAsClient(addr);
                messenger = new Messenger(socket,this);
                pedor = false;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                this.showLog(e.getMessage());
                this.showLog("Server is offline or bad connection");
            } catch (IOException e) {
                e.printStackTrace();
                this.showLog(e.getMessage());
            }
        }
        while (!userInput.equals("exit")) {
            userInput = scan.nextLine();
            messenger.sendMessage(userInput);
        }
        messenger.endSession();
    }

    @Override
    public void showMessage(String message) {
        System.out.println("<< " + message);

    }

    @Override
    public void showLog(String logMessage) {
        System.out.println("XX: " + logMessage);
    }
}
