package controller;

import model.Profile;
import view.LogInPage;

import java.awt.*;

public class Main {

    public static ProfileManager manager = new ProfileManager();
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
