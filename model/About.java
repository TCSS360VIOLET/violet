package model;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * This is the class for the about window JFrame that will popup when
 * the user clicks the 'About...' button on the main page of our App.
 * 
 * @author Lixin Wang
 * @author Nickolas Zahos (nzahos@uw.edu)
 * @author Edward Chung
 */
public class About {
    private static final double VERSION_NUMBER = 1.1;
    private final JFrame myFrame;
    private final String[] developers;

    public About() {
        // Get the team info for later
        developers = new Team().getDevelopersArray();

        // Setup the About JFrame
        myFrame = new JFrame("About");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the Window icon
        myFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));

        
		// Set uniform window size across different screen resolutions (Bad for Ultra-widescreen monitors, could stretch)
		// Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        
        // Sets this JFrame size to 1/3 the width of the user's screen, and half it's height.
        myFrame.setSize(screenWidth / 4, screenHeight / 2);
        
        // Position the frame in the center of the screen by setting location to null.
        myFrame.setLocationRelativeTo(null);

        // Text basic description about our software. 
        String descriptionText = "Our software is a powerful project management tool designed to help you seamlessly organize, track, and prioritize multiple projects. With intuitive features for setting deadlines, managing budgets, monitoring purchased items, storing designs and files, and adding notes for each project, it's the ultimate solution for staying on top of your work and achieving your goals. Plus, our convenient import/export functionality allows you to effortlessly sync your project data across multiple devices, ensuring you have access to the information you need, whenever and wherever you need it.<br>" +
                                 "<br>Brought to you by Team Violet!<br>" + 
                                 "<br>- - - Developers - - -<br>"; 

        // Add each Developer's name and contact info.
        for(int i = 0; i < developers.length; i++) {
            descriptionText += developers[i] + "<br>";
        }

        // Add version number of the current build of the program.
        descriptionText += "<br>Version " + VERSION_NUMBER;

        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" + descriptionText + "</div></html>", SwingConstants.CENTER);

        // Add padding to the border of the JLabel (makes it look nicer).
        descriptionLabel.setBorder(new EmptyBorder(0, 20, 10, 20));

        myFrame.getContentPane().add(descriptionLabel, BorderLayout.CENTER);

        // Logo image 
        URL logoURL = getClass().getResource("/images/logo.png");
        ImageIcon image = new ImageIcon(logoURL);
        JLabel logoImage = new JLabel(image);
        myFrame.getContentPane().add(logoImage, BorderLayout.NORTH);

        // Add 'Ok' button to the bottom of the JFrame
        JButton okButton = new JButton("Ok");

        // Action Listener that makes the About window close when you click the 'Ok' button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit(); // Close the About window
            }
        });

        JPanel buttonPanel = new JPanel();

        // Add lower padding to the 'Ok' button, makes it look nicer.
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        buttonPanel.add(okButton);
        myFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Ignores the screen size we set earlier to pack the window to the neceasary size to fit all its contents (Bad format for long descriptions like ours, causes stretched window).
        //myFrame.pack();
   }

   /**
    * This basic method will show/hide the main JFrame (About) window
    * depending on which boolean is passed as a parameter.
    * NOTE: Hiding the JFrame does NOT close it (will still be taking memory in the background)!
    *
    * @param b  true = show, false = hide.
    */
   public void show(boolean b) {
        myFrame.setVisible(b); 
   }

   /**
    * This basic method will close/exit the JFrame for the about window.
    * Completely exits it out, restoring all memory that was being used.
    */
   public void exit() {
        myFrame.dispose();
   }
}