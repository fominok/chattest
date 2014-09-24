package ru.chiefs.server;

import java.util.Date;

/**
 * Created by yolo on 24.09.14.
 */
public class ConsoleUi implements IServerUi {
    @Override
    public void showLogMessage(String message) {
        System.err.println(message);
    }

    public void run(){

    }
}
