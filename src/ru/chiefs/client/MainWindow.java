package ru.chiefs.client;

import javax.swing.*;
import java.net.Socket;

/**
 * Created by yolo on 27.09.14.
 */
public class MainWindow extends JFrame implements IMessengerUi {
    private JPanel mainPanel;
    private Socket socket;

    public JLabel getLabel() {
        return label;
    }

    private JLabel label;

    public MainWindow(Socket socket){
        this.socket = socket;
        this.setBounds(100,100,700,600);
        setContentPane(mainPanel);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLog(String logMessage) {

    }
}
