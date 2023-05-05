package view;


import model.About;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LogInPage implements ActionListener {


    Toolkit kit = Toolkit.getDefaultToolkit();
    JFrame frame = new JFrame();
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton registerButton = new JButton("Register");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("userID:");
    JLabel userPasswordLabel = new JLabel("password:");
    JLabel messageLabel = new JLabel();
    JTextField userEmailField = new JTextField();

    JLabel userEmailLabel = new JLabel("Email: ");
    JLabel title = new JLabel("Violet Project Management Systems");
    //HashMap<String,String> logininfo = new IDandPasswords().getLoginInfo();


    int x = kit.getScreenSize().width/2;
    int y = kit.getScreenSize().height/2;

    public LogInPage(){
        System.out.println(x * 2);
        System.out.println(y * 2);
        //logininfo = loginInfoOriginal;
        setUpButtons();
        setUpFrame();

    }

    private void setUpFrame() {
        frame.add(title);
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(registerButton);
        frame.add(userEmailLabel);
        frame.add(userEmailField);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(kit.getScreenSize());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setUpButtons() {
        title.setForeground(new Color(155,38,182));
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        title.setBounds(x/2, 0, 700, 200);


        userIDLabel.setBounds(50 + x - x/2,50 + y - y/2,75,25);
        userIDField.setBounds(125 + x - x/2,50 + y - y/2,200,25);
        userEmailLabel.setBounds(50 + x - x/2,100 + y - y/2,75,25);
        userEmailField.setBounds(125 + x - x/2,100 + y - y/2,200,25);
        userPasswordLabel.setBounds(50 + x - x/2,150 + y - y/2,75,25);
        userPasswordField.setBounds(125 + x - x/2,150 + y - y/2,200,25);
        loginButton.setBounds(125 + x - x/2,200 + y - y/2,100,25);

        registerButton.setBounds(125 + x - x/2, 300 + y - y/2, 200, 25);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225 + x - x/2,200 + y - y/2 ,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == loginButton) {


            About about = new About(userIDField.getText(), userEmailField.getText());

        }
    }
}

