package ru.chiefs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * Created by yolo on 17.09.14.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("ThA BIEST SHIET BEGINEN TERE");
        System.out.println("Y U DON COMMITIN FUCK IDEA"); // Lol i forgot to make master branch

        //event test starts there
        //i will delete it later
        //maybe
        //на самом деле это дерьмо должно было запускаться с той стороны

        Parent parent = new Parent();
        FunctionCaller caller = new FunctionCaller(parent);
        caller.tryinShit(); //Это что-то типо произошло на стороне, оттуда берем обратный вызов. МАКЕТ ХУЛИ


    }

}

class FunctionCaller {
    private Runnable parentClass;
    public FunctionCaller(Runnable parent) {
        parentClass = parent;
    }
    public void tryinShit() {
        parentClass.run();
    }
}

class Parent implements Runnable{
    @Override
    public void run() {
        System.out.println("callback received from child Function caller. Message from parent ");
    }
}
