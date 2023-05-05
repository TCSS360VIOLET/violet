package view;

import model.About;
import model.Team;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialScreen extends JFrame implements ActionListener {

    private JFrame myFrame;

    private JPanel myPanel;

    private JButton myButton;

    private About myAbout;

    public InitialScreen() {
        myFrame = new JFrame("About Screen");
        myButton = new JButton("Show About Information");
        myAbout = new About();
        myPanel = new JPanel();
    }

    private void setUpFrame() {
        myAbout.createMenuBar(myFrame);
        myFrame.add(myPanel);
        myFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);

    }

    public void start() {
        setUpFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
