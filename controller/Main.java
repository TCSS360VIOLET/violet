package controller;

import model.About;
import view.InitialScreen;
import view.LogInPage;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        //About about = new About();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LogInPage login = new LogInPage();
            }
        });
    }
}
