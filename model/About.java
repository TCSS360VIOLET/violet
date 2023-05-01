package model;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Toolkit;


public class About {
    private final JFrame myFrame;
    private final JMenuBar myMenuBar;
    private final JMenu myOwnerJMenu;
    private final JMenu myAboutJMenu;
    private final JMenuItem myNameItem;
    private final JMenuItem myEmailItem;
    private final JMenuItem myVersionItem;
    private final JMenuItem myDeveloperItem;

    public About() {
        myFrame = new JFrame("About");
        myMenuBar = new JMenuBar();
        myOwnerJMenu = new JMenu("Owner");
        myAboutJMenu = new JMenu("About");
        myNameItem = new JMenuItem("Name");
        myEmailItem = new JMenuItem("Email");
        myVersionItem = new JMenuItem("Version");
        myDeveloperItem = new JMenuItem("Developers");

        myOwnerJMenu.add(myNameItem);
        myOwnerJMenu.addSeparator();
        myOwnerJMenu.add(myEmailItem);

        myAboutJMenu.add(myVersionItem);
        myAboutJMenu.addSeparator();
        myAboutJMenu.add(myDeveloperItem);

        myMenuBar.add(myOwnerJMenu);
        myMenuBar.add(myAboutJMenu);

        myFrame.setJMenuBar(myMenuBar);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();

        final Toolkit kit = Toolkit.getDefaultToolkit();
        // position the frame in the center of the screen
        myFrame.setLocation((int) (kit.getScreenSize().getWidth() 
        / 2 - myFrame.getWidth() / 2),
        (int) (kit.getScreenSize().getHeight() / 2 - myFrame.getHeight() / 2));

        myFrame.setVisible(true); 
   }

}
