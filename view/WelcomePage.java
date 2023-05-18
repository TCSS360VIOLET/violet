package view;

import controller.Main;
import controller.ProfileManager;
import model.About;
import model.Profile;
import model.Project;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The Welcome page of the applications where you can create projects to work on.
 * @author Parker Johnson (5/5/2023)
 */
public class WelcomePage extends JFrame implements ActionListener{

    /**
     * The columns for the table headings.
     */
    private final String[] COLUMNS = {
            "Project Name",
            "Start Date",
            "End Date",
            "Budget",
            "Days Until Deadline",
            "Project Details"
    };

    /**
     * The main frame.
     */
    private JFrame frame;

    /**
     * The welcome label.
     */
    private JLabel welcomeLabel;

    /**
     * Add project button.
     */
    private JButton addProject;

    /**
     * Go to project button.
     */
    private JButton goToProject;

    /**
     * The delete project button
     */
    private JButton deleteProject;

    /**
     * The logout button.
     */
    private JButton logoutButton;

    /**
     * The export button.
     */
    private JButton exportButton;

    /**
     * The import button.
     */
    private JButton importButton;

    //HashMap<String, String> ids = new IDandPasswords().getLoginInfo();

    /**
     * The name label.
     */
    private JLabel nameLabel;

    /**
     * The start date label.
     */
    private JLabel startLabel;

    /**
     * The end date label.
     */
    private JLabel endLabel;

    /**
     * The budget label.
     */
    private JLabel budgetLabel;
    /**
     * The name field.
     */
    private JTextField nameField;

    /**
     * The start date field.
     */
    private JTextField startDateField;

    /**
     * The end date field.
     */
    private JTextField endDateField;

    /**
     * The budget field.
     */
    private JTextField budgetField;
    /**
     * The menu bar.
     */
    private JMenuBar menuBar;
    /**
     * The owner menu.
     */
    private JMenu ownerMenu;
    /**
     * The owner name item.
     */
    private final JMenuItem ownerName;
    /**
     * The owner email item.
     */
    private final JMenuItem ownerEmail;
    /**
     * The list of projects.
     */

    private final ArrayList<ProjectPage> projectList;
    /**
     * The table model for the table.
     */
    private DefaultTableModel model;
    // Create the JTable
    /**
     * The table for the projects.
     */
    private JTable table;
    /**
     * The about menu.
     */
    private final JMenu aboutMenu = new JMenu("About");
    /**
     * The about menu item.
     */
    private final JMenuItem aboutItem = new JMenuItem("About...");

    private final String userID;

    /**
     * The Profile Manager
     */

    /**
     * Initialize Fields of the WelcomePge
     * @param userID The user who logged in.
     * @param userEmail The user's email address.
     */
    public WelcomePage(String userID, String userEmail){
        ownerName = new JMenuItem(userID);
        ownerEmail = new JMenuItem(userEmail);
        this.userID = userID;
        projectList = new ArrayList<>(0);
        initializeFields();
        // Create the JTable
        table = new JTable(model);
        setUpLabel(userID);
        setUpFrame();

    }

    /**
     * Method to initialize fields because constructor was too long.
     */
    private void initializeFields() {
        frame = new JFrame();
        welcomeLabel = new JLabel("Hello!");
        addProject = new JButton("Add Project...");
        goToProject = new JButton("Go To Project");
        deleteProject = new JButton("Delete Project");

         // Create the buttons
        exportButton = new JButton("Export Data");
        importButton = new JButton("Import Data");

        logoutButton = new JButton("Logout");
        //HashMap<String, String> ids = new IDandPasswords().getLoginInfo();
        nameLabel = new JLabel("Project Name");
        startLabel = new JLabel("Start Date (mm/dd/yyyy)");
        endLabel = new JLabel("End Date (mm/dd/yyyy)");
        budgetLabel  = new JLabel("Budget");
        nameField = new JTextField();
        startDateField = new JTextField();
        endDateField = new JTextField();
        budgetField = new JTextField();
        menuBar = new JMenuBar();
        ownerMenu = new JMenu("Owner");
        model = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return column != 4;
            }
        };
    }

    /**
     * Setting up welcome label.
     * @param userID The username of current user.
     */
    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,18));
        welcomeLabel.setText("Hello "+ userID.toUpperCase(Locale.US));
    }

    /**
     * A method to set up the frame elements.
     */
    public void setUpFrame() {
        addActions();
        setBounds();
        addComponentsToFrame();

        JScrollPane sp = setUpTable();
        sp.setBounds(100, 100, 1000, 600);
        frame.add(sp, BorderLayout.CENTER);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * Add necessary components to frame.
     */
    private void addComponentsToFrame() {
        frame.add(logoutButton);
        frame.add(goToProject);
        frame.add(deleteProject);
        frame.add(nameField);
        frame.add(nameLabel);
        frame.add(startDateField);
        frame.add(startLabel);
        frame.add(endLabel);
        frame.add(endDateField);
        frame.add(budgetLabel);
        frame.add(budgetField);
        frame.add(addProject);
        frame.add(exportButton);
        frame.add(importButton);
        ownerMenu.add(ownerName);
        ownerMenu.add(ownerEmail);
        aboutMenu.add(aboutItem);
        menuBar.add(aboutMenu);
        menuBar.add(ownerMenu);
        frame.setJMenuBar(menuBar);
        frame.add(welcomeLabel, BorderLayout.NORTH);
    }

    /**
     * Adding actions to the items that need actions.
     */
    private void addActions() {
        goToProject.addActionListener(this);
        addProject.addActionListener(this);
        deleteProject.addActionListener(this);
        exportButton.addActionListener(this);
        importButton.addActionListener(this);
        logoutButton.addActionListener(this);
        aboutItem.addActionListener(aboutItem -> {
            About about = new About();
            about.show(true);
        });

    }

    /**
     * Set the boundaries for the components in the frame.
     */
    private void setBounds() {
        addProject.setBounds(1300, 135, 150, 25);
        logoutButton.setBounds(0, 30, 100,25);
        nameField.setBounds(1300, 50, 150, 25);
        nameLabel.setBounds(1150, 50, 150, 25);
        startLabel.setBounds(1150, 70, 150, 25);
        endLabel.setBounds(1150, 90, 150, 25);
        budgetLabel.setBounds(1150, 110, 150, 25);
        startDateField.setBounds(1300, 70, 150, 25);
        endDateField.setBounds(1300, 90, 150, 25);
        budgetField.setBounds(1300, 110, 150, 25);
        deleteProject.setBounds(1300, 175, 150, 25);
        goToProject.setBounds(1300, 220, 150, 25);
        exportButton.setBounds(1300, 265, 150, 25);
        importButton.setBounds(1300, 310, 150, 25);

    }

    /**
     * Set up the table for the main page.
     * @return The JScrollPane for the table.
     */
    private JScrollPane setUpTable() {
        table = new JTable(model);
        return new JScrollPane(table);

    }

    /**
     * Verify that the dates entered for a project are in a valid range.
     * @param d1 The first date as a string.
     * @param d2 The second date as a string.
     * @return Whether the date range is valid
     */
    private boolean validDateRange(String d1, String d2) {
        String d1Days = d1.substring(3,5);
        String d2Days = d2.substring(3,5);
        String d1Month = d1.substring(0,2);
        String d2Month = d2.substring(0,2);
        String d1Year = d1.substring(6,10);
        String d2Year = d2.substring(6,10);
        boolean monthsDiff = (Integer.parseInt(d2Month) - Integer.parseInt(d1Month)) < 0;
        boolean yearsDiff = (Integer.parseInt(d2Year) - Integer.parseInt(d1Year)) < 0;
        boolean daysDiff = Integer.parseInt(d2Days) - Integer.parseInt(d1Days) <= 0;
        if (new Date(d1).getTime() < new Date().getTime()) {
            return false;
        }
        if (monthsDiff || yearsDiff) {
            return false;
        } else if (Integer.parseInt(d2Month) - Integer.parseInt(d1Days) == 0){
            return !daysDiff || !yearsDiff;
        }
        return true;
    }


    /**
     * {@inheritDoc}
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            frame.dispose();
            LogInPage page = new LogInPage();
        }

        if (e.getSource() == addProject) {
            addProject();
        }

        if (e.getSource() == deleteProject) {
            deleteProject();
        }

        if (e.getSource() == goToProject) {
            goToProject();
        }

        if (e.getSource() == exportButton) {
            exportData();
        }

        if (e.getSource() == importButton) {
            importData();
        }
    }

    /**
     * The action to do when goToProject is selected.
     */
    private void goToProject() {
        String choice = JOptionPane.showInputDialog(null, "Which project do would you like to visit?");
        int choiceNum = Integer.parseInt(choice);
        if (projectList.size() != 0 && projectList.get(choiceNum-1) != null && projectList.get(choiceNum-1).isActive()) {
            projectList.get(choiceNum-1).setVisible(true);
        } else if (!choice.isEmpty()) {
            ProjectPage projectPage = new ProjectPage((Project) model.getValueAt(choiceNum - 1, model.getColumnCount()-1), userID);
            projectList.add(projectPage);
        }
    }

    /**
     * The action to do when deleteProject is selected.
     */
    private void deleteProject() {
        String choice = JOptionPane.showInputDialog(null,
                "Which project do would you like to delete?");
        int choiceNum = Integer.parseInt(choice);
        if (!choice.isEmpty()) {
            String message = "Are you sure you want to delete the project in row " + choice;
            int deleteChoice = JOptionPane.showConfirmDialog(null, message);
            if (deleteChoice == JOptionPane.OK_OPTION) {
                model.removeRow(choiceNum - 1);
            }
        }
    }

    /**
     * The action to do when addProject is selected.
     */
    private void addProject() {
        String startDateString = startDateField.getText();
        String endDateString = endDateField.getText();
        Date startDate = new Date(startDateString);
        Date endDate = new Date(endDateString);
        if (validDateRange(startDateString, endDateString)) {


            Project project = new Project(startDate, endDate,
                    nameField.getText(), Double.parseDouble(budgetField.getText()));
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            model.addRow(
                    new Object[]{
                            project.getName(),
                            startDateString,
                            endDateString,
                            nf.format(project.getBudget()),
                            project.getDaysTillFinished(),
                            project
                    }
            );
            Main.manager.addProject(userID, project.getName(),
                    project.getStartDate().toString(),
                    project.getEndDate().toString(),
                    String.valueOf(project.getBudget()));
            nameField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            budgetField.setText("");
        } else {
            JOptionPane.showInternalMessageDialog(null, "Start Date must be before the End Date");
        }
    }

    /*
     * The action to export data.
     */
    private void exportData() {
        try {
            File dataFolder = new File("data");
            File[] xmlFiles = dataFolder.listFiles((dir, name) -> name.endsWith(".xml"));
    
            if (xmlFiles.length == 0) {
                JOptionPane.showMessageDialog(this, "No XML files found in the data folder.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Destination for Exported ZIP File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("export.zip"));
    
            int result = fileChooser.showSaveDialog(this);
    
            if (result == JFileChooser.APPROVE_OPTION) {
                File exportFile = fileChooser.getSelectedFile();
    
                FileOutputStream fos = new FileOutputStream(exportFile);
                ZipOutputStream zos = new ZipOutputStream(fos);
    
                byte[] buffer = new byte[1024];
    
                for (File xmlFile : xmlFiles) {
                    ZipEntry zipEntry = new ZipEntry(xmlFile.getName());
                    zos.putNextEntry(zipEntry);
    
                    FileInputStream fis = new FileInputStream(xmlFile);
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();
    
                    zos.closeEntry();
                }
    
                zos.close();
                fos.close();
    
                JOptionPane.showMessageDialog(this, "Data exported successfully.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "An error occurred during export: " + e.getMessage(), "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /*
     * The action to import data.
     */
    private void importData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select Zip File to Import");
        int result = fileChooser.showOpenDialog(this);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();
    
            try {
                // Create a folder to extract the contents of the zip file
                File extractionFolder = new File("imported_data");
                if (!extractionFolder.exists()) {
                    extractionFolder.mkdir();
                }
    
                // Extract the zip file contents
                FileInputStream fis = new FileInputStream(importFile);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry zipEntry = zis.getNextEntry();
    
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File extractedFile = new File(extractionFolder, fileName);
    
                    FileOutputStream fos = new FileOutputStream(extractedFile);
                    byte[] buffer = new byte[1024];
                    int length;
    
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
    
                    fos.close();
                    zipEntry = zis.getNextEntry();
                }
    
                zis.close();
                fis.close();
    
                JOptionPane.showMessageDialog(this, "Data imported successfully.", "Import",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred during import: " + e.getMessage(),
                        "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
