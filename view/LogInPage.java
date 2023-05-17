package view;



import controller.Main;
import controller.ProfileManager;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The login page for the application.
 * @author Parker Johnson (5/5/2023)
 */
public class LogInPage implements ActionListener {

    /**
     * The tool kit for the class
     */
    private Toolkit kit;
    /**
     * The main frame of the class.
     */
    private JFrame frame;
    /**
     * The login button.
     */
    private JButton loginButton;
    /**
     * The reset button.
     */
    private JButton resetButton;
    /**
     * The register button.
     */
    private JButton registerButton;
    /**
     * The user id field.
     */
    private JTextField userIDField;
    /**
     * user password field.
     */
    private JPasswordField userPasswordField;
    /**
     * user id label.
     */
    private JLabel userIDLabel;
    /**
     * user id label.
     */
    private JLabel userPasswordLabel;
    /**
     * The message label.
     */
    private JLabel messageLabel;
    /**
     * The user email field.
     */
    private JTextField userEmailField;
    /**
     * The user email field.
     */
    private JLabel userEmailLabel;
    /**
     * The title label.
     */
    private JLabel title;
    //HashMap<String,String> logininfo = new IDandPasswords().getLoginInfo();

    /**
     * The x size of the screen.
     */
    private int screenX;
    /**
     * The y size of the screen.
     */
    private int screenY;

    /**
     * The ProfileManager
     */
    private ProfileManager manager = new ProfileManager();

    /**
     * Initialize fields for the class.
     */
    public LogInPage(){
        //logininfo = loginInfoOriginal;
        initializeFields();
        setUpButtons();
        setUpFrame();

    }

    /**
     * helper method to initialize fields because constructor was too long.
     */
    private void initializeFields() {
        kit = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");
        registerButton = new JButton("Register");
        userIDField = new JTextField();
        userPasswordField = new JPasswordField();
        userIDLabel = new JLabel("userID:");
        userPasswordLabel = new JLabel("password:");
        messageLabel = new JLabel();
        userEmailField = new JTextField();

        userEmailLabel = new JLabel("Email: ");
        title = new JLabel("Violet Project Management Systems");
        //HashMap<String,String> logininfo = new IDandPasswords().getLoginInfo();


        screenX = kit.getScreenSize().width/2;
        screenY = kit.getScreenSize().height/2;
    }

    /**
     * Set up the elements for the frame.
     */
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

    /**
     * Set up the buttons for the page.
     */
    private void setUpButtons() {
        title.setForeground(new Color(155,38,182));
        title.setFont(new Font("Times New Roman", Font.BOLD, 35));
        title.setBounds(screenX /2, 0, 700, 200);


        userIDLabel.setBounds(50 + screenX - screenX /2,50 + screenY - screenY /2,75,25);
        userIDField.setBounds(125 + screenX - screenX /2,50 + screenY - screenY /2,200,25);
        userEmailLabel.setBounds(50 + screenX - screenX /2,100 + screenY - screenY /2,75,25);
        userEmailField.setBounds(125 + screenX - screenX /2,100 + screenY - screenY /2,200,25);
        userPasswordLabel.setBounds(50 + screenX - screenX /2,150 + screenY - screenY /2,75,25);
        userPasswordField.setBounds(125 + screenX - screenX /2,150 + screenY - screenY /2,200,25);
        loginButton.setBounds(125 + screenX - screenX /2,200 + screenY - screenY /2,100,25);

        registerButton.setBounds(125 + screenX - screenX /2, 300 + screenY - screenY /2, 200, 25);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(225 + screenX - screenX /2,200 + screenY - screenY /2 ,100,25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
    }

    /**
     * {@inheritDoc}
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == loginButton) {

            try {
                Main.manager.addProfile(userIDField.getText(), userEmailField.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            WelcomePage wp = new WelcomePage(userIDField.getText(), userEmailField.getText());
            frame.dispose();
        }
    }

    private boolean containsProfile(Document document, String userName) {
        NodeList nl = document.getElementsByTagName("Username");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getTextContent().equals(userIDField.getText())) {
                return true;
            }
        }
        return false;
    }
}

