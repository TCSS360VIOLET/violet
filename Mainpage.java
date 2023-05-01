import javax.swing.*;
import java.awt.*;

public class Mainpage {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.add(new JLabel("hello"));
        frame.setTitle("test"); //title of app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit from app
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
    }
}