package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Main;
import model.Project;

import java.awt.*;
import java.awt.event.*;

public class EditProjectScreen extends JFrame {
    private JTextField projectNameField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField budgetField;
    private String project;
    private String userID;
    private DefaultTableModel model;
   
    public EditProjectScreen(String project, String userID, DefaultTableModel model) {
        this.project = project;
        this.userID = userID;
        this.model = model;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Edit Project");
        
        setUpFrame();
    }
    
    private void setUpFrame() {
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
        JLabel startDateLabel = new JLabel("Start Date:");
        startDateField = new JTextField(20);
        startDateField.setText(Main.manager.getProjectStartDate(userID, project)); // Set the provided value
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(startDateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(startDateField, constraints);
        
        // End Date row
        JLabel endDateLabel = new JLabel("End Date:");
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
                    //delete the old project in XML
                    Main.manager.deleteProject(userID, project);
                    // Get the values from the text fields
                    String projectName = projectNameField.getText();
                    String startDate = startDateField.getText();
                    String endDate = endDateField.getText();
                    String budget = budgetField.getText();
                    
                    // add updated project in xml file
                    Main.manager.addProject(userID, projectName, startDate, endDate, budget);



                    dispose();
                }
            });
            constraints.gridx = 1;
            constraints.gridy = 4;
            add(saveButton, constraints);
    }    
}
