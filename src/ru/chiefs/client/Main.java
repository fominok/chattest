package ru.chiefs.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by yolo on 17.09.14.
 * 
 * THIS EXTREMELY IMPORTANT COMMENT MADE BY PUSSYMONSTA FOR GIT TESTING PURPOSES
 */
public class Main {
    private static String address = "178.162.5.219";
    private static Socket socket;

    public static void main(String[] args) {
        /*ConsoleUi ui = new ConsoleUi();
        try {
            ui.run();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("shiet.");
        }*/
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel helloPanel = new JPanel();
        final JTextField nameField = new JTextField("What's your name?");
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                nameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        helloPanel.add(nameField);
        int result = JOptionPane.showConfirmDialog(
                null,helloPanel,"Hellou",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            helloPanel.remove(nameField);
            JLabel connectionLabel = new JLabel("Establishing connection...");
            helloPanel.add(connectionLabel);
            final JDialog connectionDialog = new JDialog();
            connectionDialog.add(helloPanel);
            connectionDialog.setBounds(screenSize.width/2-150,screenSize.height/2-50,300,100);
            connectionDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            connectionDialog.setVisible(true);
            JButton reconnect = new JButton("Retry");
            helloPanel.add(reconnect);
            reconnect.setVisible(false);

            reconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        tryConnection();
                        runUi(connectionDialog,nameField.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            try {
                tryConnection();
                runUi(connectionDialog,nameField.getText());
            } catch (IOException e) {
                e.printStackTrace();
                connectionLabel.setText("Could not connect");
                reconnect.setVisible(true);
            }
        }
    }

    private static void tryConnection() throws IOException {
            InetAddress addr = InetAddress.getByName(address);
            Connector connector = new Connector();
            socket = connector.connectAsClient(addr);

    }

    private static void runUi(JDialog conDiag, String name){
        conDiag.dispose();
        MainWindow window = new MainWindow(socket,name);
        window.setVisible(true);
    }

}
