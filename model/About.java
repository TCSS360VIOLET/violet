package model;

import view.WelcomePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * DESCRIBE THIS CLASS HERE...
 * 
 * @author Lixin Wang
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
public class About {
    private final JFrame myFrame;
    private final JMenuBar myMenuBar;
    private final JMenu myOwnerJMenu;
    private final JMenu myAboutJMenu;
    private final JMenuItem myNameItem;
    private final JMenuItem myEmailItem;
    private final JMenuItem myVersionItem;
    private final JMenuItem myDeveloperItem;



    public About(String userId, String userEmail) {
        myFrame = new JFrame("About");
        myMenuBar = new JMenuBar();
        myOwnerJMenu = new JMenu("Owner");
        myAboutJMenu = new JMenu("About");
        myNameItem = new JMenuItem(userId);
        myEmailItem = new JMenuItem(userEmail);
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
        myFrame.add(new WelcomePanel(userId, userEmail).setUpFrame());
        myFrame.setJMenuBar(myMenuBar);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set uniform window size across different screen resolutions (Bad for Ultra-widescreen monitors, could stretch)
		// Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        
        // Sets this JFrame size to 1/4 the width of the user's screen, and half it's height.
        myFrame.setSize(screenSize);
        
        // Position the frame in the center of the screen by setting location to null.
        myFrame.setLocationRelativeTo(null);

        myFrame.setVisible(true); 

        setup();

   }
    
   private void setup() {
        myDeveloperItem.addActionListener(new AboutActionListener()); 
   }

   private class AboutActionListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            final Team team = new Team();
            Map<Integer, String> map = team.getDevelopers();
            JTable table = new JTable(map.size(), 1);
            
            // Do not allow the user to edit the table.
            table.setEnabled(false);
            
            // Set the name of the column
            TableColumnModel columnModel = table.getColumnModel();
            TableColumn column = columnModel.getColumn(0);
            column.setHeaderValue("Developer Name (Contact Email)");
            columnModel.setColumnMargin(10);

            // Set the cell renderer to center the values (Centers the text)
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(renderer);

            // Gather team info, and put it into the table
            int row = 0;
            for(Map.Entry<Integer, String> entry : map.entrySet()) {
                table.setValueAt(entry.getValue(),row,0);
                row++;
            }
            
            // Create a new JFrame to display the table
            JFrame tableFrame = new JFrame("Developers");
            tableFrame.add(new JScrollPane(table));	// Scroll pane allows the user to scroll down if theres too many rows.
            
            // Auto Size the window to fit all content
            tableFrame.pack();
            
            // Center this developers frame to the frame of the main JFrame
            tableFrame.setLocationRelativeTo(myFrame);
            
            tableFrame.setVisible(true);
        }
   }
}
