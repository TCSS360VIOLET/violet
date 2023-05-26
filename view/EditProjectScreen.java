package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Project;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EditProjectScreen extends JFrame {
    private JTextField projectNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField budgetField;
    private String project;
    private String userID;
    private DefaultTableModel model;
    private int selectedRow;
   
    public EditProjectScreen(String project, String userID, DefaultTableModel model, Project projectObject, int theSelectedRow) {
        this.project = project;
        this.userID = userID;
        this.model = model;
        this.selectedRow = theSelectedRow;
        setTitle("Edit Project");
        
        setUpFrame();
    }
    
    private void setUpFrame() {

        // Set uplocation and size of JFrame
                // Get the screen size
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int screenWidth = screenSize.width;
                int screenHeight = screenSize.height;
        
                // Set the size of the JFrame
                int frameWidth = screenWidth / 2;
                int frameHeight = screenHeight / 2;
                setSize(frameWidth, frameHeight);
                
                // Calculate the position to center the JFrame
                int x = (screenWidth - frameWidth) / 2;
                int y = (screenHeight - frameHeight) / 2;
                setLocation(x, y);
        // Set the layout for the JFrame using a GridBagLayout
        setLayout(new GridBagLayout());
        
        // Create GridBagConstraints for layout control
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5); // Add some padding
        
        // Project Name row
        JLabel projectNameLabel = new JLabel("Project Name:");
        projectNameField = new JTextField(20);
        projectNameField.setText(project); // Set the provided value
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(projectNameLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(projectNameField, constraints);
        
        // Start Date row
        JLabel startDateLabel = new JLabel("Start Date (mm/dd/yyyy):");
        startDateField = new JTextField(20);
        startDateField.setText(Main.manager.getProjectStartDate(userID, project)); // Set the provided value
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(startDateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(startDateField, constraints);
        
        // End Date row
        JLabel endDateLabel = new JLabel("End Date (mm/dd/yyyy)");
        endDateField = new JTextField(20);
        endDateField.setText(Main.manager.getProjectEndDate(userID, project)); // Set the provided value
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(endDateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(endDateField, constraints);
        
        // Budget row
        JLabel budgetLabel = new JLabel("Budget:");
        budgetField = new JTextField(20);
        budgetField.setText(Float.toString(Main.manager.getProjectBudget(userID, project))); // Set the provided value
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(budgetLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(budgetField, constraints);
        
            // Cancel button
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();  // Close the JFrame when the cancel button is clicked
                }
            });
            constraints.gridx = 0;
            constraints.gridy = 4;
            add(cancelButton, constraints);
            
            // Save button
            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get the values from the text fields
                    String projectName = projectNameField.getText();
                    String startDate = startDateField.getText();
                    String endDate = endDateField.getText();
                    String budget = budgetField.getText();

                    // Set new project info
                    Main.manager.setProjectName(userID, project, projectName);
                    Main.manager.setProjectStartDate(userID, projectName, startDate);
                    Main.manager.setProjectEndDate(userID, projectName, endDate);
                    Main.manager.setProjectBudget(userID, projectName, budget);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
                    long daysLeft = new Date(endDate).getTime() - new Date(startDate).getTime();
                    int daysTillFinished = (int) TimeUnit.DAYS.convert(daysLeft, TimeUnit.MILLISECONDS);

                    // Update row UI for project edited
                    model.setValueAt(projectName, selectedRow, 0);
                    model.setValueAt(sdf.format(new Date(startDate)), selectedRow, 1);
                    model.setValueAt(sdf.format(new Date(endDate)), selectedRow, 2);
                    model.setValueAt(nf.format(Double.parseDouble(budget)), selectedRow, 3);
                    model.setValueAt(daysTillFinished, selectedRow,4 );

                    

                    dispose();
                }
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            add(saveButton, constraints);
    }    
}
