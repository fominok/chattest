package ru.chiefs.client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by yolo on 27.09.14.
 */
public class MainWindow extends JFrame implements IMessengerUi {
    private JPanel mainPanel;
    private JPanel messengerPanel;
    private JPanel inputPanel;
    private JTextField inputText;
    private JTextArea messengerArea;
    private JScrollPane scrollPane;

    private Messenger messenger;
    private String name;

    public MainWindow(Socket socket, final String name){
        this.name = name;
        try {
            messenger = new Messenger(socket,this);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("wtf could happen here");
        }
        this.setBounds(100,100,700,600);
        setContentPane(mainPanel);

        DefaultCaret caret = (DefaultCaret) messengerArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messengerArea.setEditable(false);

        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    messenger.sendMessage(name + ": " + inputText.getText());
                } catch (IOException e1) {
                    JPanel lostPanel = new JPanel();
                    lostPanel.add(new JLabel("Connection lost"));
                    JOptionPane.showMessageDialog(null,lostPanel,"So sorry",JOptionPane.OK_CANCEL_OPTION);
                }
                inputText.setText("");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void showMessage(String message) {
        messengerArea.append(message + "\n");
    }

    @Override
    public void showLog(String logMessage) {

    }
}
