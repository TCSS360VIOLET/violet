package controller;

import view.LogInPage;

import java.awt.*;

/**
 * The main driver of the program.
 */
public class Main {

    /**
     * The file manager of the project.
     */
    public static final ProfileManager manager = new ProfileManager();

    /**
     * The driver method of the program
     * @param args the command line arguments.
     * @author Parker J.
     */
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
