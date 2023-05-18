package model;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the class for the about window JFrame that will popup when
 * the user clicks the 'About...' button on the main page of our App.
 * 
 * @author Lixin Wang
 * @author Nickolas Zahos (nzahos@uw.edu)
 * @author Edward Chung
 */
public class About {
    /** This constant version number will change with more iterations. It will have to be manually updated here */
    private static final double VERSION_NUMBER = 2.0;

    /** The main JFrame of this About window. */
	private JFrame myFrame;

    /** This string array will hold all the developer's info. */
    private final String[] developers;
	
    /** 
     * Default Contructor - Takes no parameters, sets up the UI for this frame.
     * @author Lixin Wang
     * @author Nickolas Zahos (nzahos@uw.edu)
     * @author Edward Chung 
     */
	public About() {
		myFrame = new JFrame("About");
        developers = new Team().getDevelopersArray();
		
		// Set the JFrame icon to my custom icon.
		URL iconURL = getClass().getResource("/images/icon.png");
        ImageIcon myIcon = new ImageIcon(iconURL);
		myFrame.setIconImage(myIcon.getImage());
		
		// Closing the window exits frame.
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// For uniform window size across different screen resolutions (Bad for Ultra-widescreen monitors, could stretch)
		// Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        
        // Sets this JFrame size to 1/4 the width of the user's screen, and half it's height.
        myFrame.setSize(screenWidth / 4, screenHeight / 2);
        
        // Create a JPanel with BorderLayout
        JPanel myPanel = new JPanel(new BorderLayout());
        
        // Set up the logo.
        setupLogo(myPanel);
        
        // Set up the text field.
        setupTextField(myPanel);
        
        // Set up the 'Ok' button.
        setupButton(myPanel);
	}
	
    /**
     * This private method will only be called by the constructor.
     * It just places an 'Ok' button below the text field.
     * @author Lixin Wang
     * @author Nickolas Zahos (nzahos@uw.edu)
     * @author Edward Chung
     * 
     * @param panel The panel that the component will be attached to.
     */
	private void setupButton(JPanel panel) {
        // Create the 'Ok' button
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
            }
        });

        // Create a new JPanel with FlowLayout to hold the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add the 'Ok' button to the button panel
        buttonPanel.add(okButton);

        // Add the button panel to the main panel using BorderLayout.SOUTH
        panel.add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * This private method will only be called by the constructor.
	 * It just sets up the large logo that will appear above the text field.
     * @author Lixin Wang
     * @author Nickolas Zahos (nzahos@uw.edu)
     * @author Edward Chung
     * 
	 * @param panel The panel that the component will be attached to.
	 */
	private void setupLogo(JPanel panel) {
        // Load the logo image and create an ImageIcon
        URL logoURL = getClass().getResource("/images/logo.png");
        ImageIcon logoIcon = new ImageIcon(logoURL);
        
        // Resize the ImageIcon
        Image image = logoIcon.getImage();
        double aspectRatio = (double) image.getWidth(null)/(double) image.getHeight(null);
        
        // Here, we're going to scale the image to 3/4 of the JFrame's height
        int scaledHeight = (int) (myFrame.getHeight() * 0.4);
        int scaledWidth = (int) (scaledHeight * aspectRatio);

        Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        
        // Create a JLabel with the ImageIcon and add it to the JPanel using BorderLayout.NORTH
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);
	}

	/**
	 * This private method will only be called by the constructor.
	 * It just sets up the text field of the about JFrame that will
	 * describe the program.
     * @author Lixin Wang
     * @author Nickolas Zahos (nzahos@uw.edu)
     * @author Edward Chung
     * 
	 * @param panel The panel that the component will be attached to.
	 */
	private void setupTextField(JPanel panel) {
        String descriptionText = "Our software is a powerful project management tool designed to help you seamlessly organize, track, and prioritize multiple projects. With intuitive features for setting deadlines, managing budgets, monitoring purchased items, storing designs and files, and adding notes for each project, it's the ultimate solution for staying on top of your work and achieving your goals. Plus, our convenient import/export functionality allows you to effortlessly sync your project data across multiple devices, ensuring you have access to the information you need, whenever and wherever you need it." + 
        "\n\nBrought to you by Team Violet!\n" + 
        "\nDevelopers:\n"; 
        
        // Add each Developer's name and contact info.
        for(int i = 0; i < developers.length; i++) {
            descriptionText += developers[i] + "\n";
        }

        // Add version number of the current build of the program.
        descriptionText += "\nVersion " + VERSION_NUMBER;

        JTextArea aboutTextArea = new JTextArea(descriptionText);

        aboutTextArea.setEditable(false);
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        
        // Set padding around the text using an empty border
        int padding = 25;
        aboutTextArea.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // Wrap the JTextArea in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(aboutTextArea);
        
        // Set padding around the JScrollPane using an empty border
        int scrollPanePadding = 20;
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, scrollPanePadding, scrollPanePadding, scrollPanePadding));


        // Add the JScrollPane to the JPanel using BorderLayout.CENTER
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the JPanel to the JFrame
        myFrame.add(panel);
	}
	
	/**
	 * This method displays this class's JFrame.
     * @author Lixin Wang
     * @author Nickolas Zahos (nzahos@uw.edu)
     * @author Edward Chung
     * 
     * @param   theBool   True for show, false for hide frame.
	 */
	public void show(boolean theBool) {
        // Center the JFrame to the UI Object (null means just center of screen)
        myFrame.setLocationRelativeTo(null);

        // Show the JFrame
        myFrame.setVisible(theBool);
	}
}
