package view;

import controller.FileManager;
import controller.Main;
import model.About;
import model.Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The Welcome page of the applications where you can create projects to work
 * on.
 * 
 * @author Parker Johnson (5/5/2023)
 */
public class WelcomePage extends JFrame implements ActionListener {

    /**
     * The columns for the table headings.
     */
    private final String[] COLUMNS = {
            "Project Name",
            "Start Date",
            "End Date",
            "Budget",
            "Days Until Deadline",
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
     * The edit project button
     */
    private JButton editProject;
    /**
     * The logout button.
     */
    private JButton logoutButton;

    private JButton exportButton;

    // HashMap<String, String> ids = new IDandPasswords().getLoginInfo();

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
     * The list of all project objects stored.
     */
    private ArrayList<Project> projectObjectList;

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
     * Initialize Fields of the WelcomePge
     * 
     * @param userID    The user who logged in.
     * @param userEmail The user's email address.
     * @author Parker J.
     */
    public WelcomePage(String userID, String userEmail) {
        ownerName = new JMenuItem(userID);
        ownerEmail = new JMenuItem(userEmail);
        this.userID = userID;
        projectList = new ArrayList<>(0);
        projectObjectList = new ArrayList<>();

        initializeFields();
        // Create the JTable
        table = new JTable(model);
        setUpLabel(userID);
        setUpFrame();
        loadProjects(userID);
    }

    /**
     * Load projects from file
     * 
     * @author Lixin W.
     */
    private void loadProjects(String userID) {
        java.util.List<Project> projects = FileManager.loadProjects(new File("data/" + userID + ".xml"));
        for (Project project : projects) {
            ProjectPage projectPage = new ProjectPage(project, userID);
            projectPage.setVisible(false);
            projectList.add(projectPage);

            addProject(project);
        }
    }

    /**
     * Method to initialize fields because constructor was too long.
     * 
     * @author Parker J.
     */
    private void initializeFields() {
        frame = new JFrame();
        welcomeLabel = new JLabel("Hello!");
        addProject = new JButton("Add Project...");
        goToProject = new JButton("Go To Project");
        deleteProject = new JButton("Delete Project");
        editProject = new JButton("Edit Project");

        logoutButton = new JButton("Logout");
        exportButton = new JButton("Export Data");
        // HashMap<String, String> ids = new IDandPasswords().getLoginInfo();
        nameLabel = new JLabel("Project Name");
        startLabel = new JLabel("Start Date (mm/dd/yyyy)");
        endLabel = new JLabel("End Date (mm/dd/yyyy)");
        budgetLabel = new JLabel("Budget");
        nameField = new JTextField();
        startDateField = new JTextField();
        endDateField = new JTextField();
        budgetField = new JTextField();
        menuBar = new JMenuBar();
        ownerMenu = new JMenu("Owner");
        model = new DefaultTableModel(COLUMNS, 0);
    }

    /**
     * Setting up welcome label.
     * 
     * @param userID The username of current user.
     * @author Parker J.
     */
    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0, 0, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 18));
        welcomeLabel.setText("Hello " + userID.toUpperCase(Locale.US));
    }

    /**
     * A method to set up the frame elements.
     * 
     * @author Parker J.
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
     * 
     * @author Parker J.
     */
    private void addComponentsToFrame() {
        frame.add(logoutButton);
        frame.add(goToProject);
        frame.add(deleteProject);
        frame.add(editProject);
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
     * 
     * @author Parker J.
     * @author Lixin W.
     */
    private void addActions() {
        goToProject.addActionListener(this);
        addProject.addActionListener(this);
        deleteProject.addActionListener(this);
        editProject.addActionListener(this);
        logoutButton.addActionListener(this);
        exportButton.addActionListener(this);
        aboutItem.addActionListener(aboutItem -> {
            About about = new About();
            about.show(true);
        });

    }

    /**
     * Set the boundaries for the components in the frame.
     * 
     * @author Parker J.
     */
    private void setBounds() {
        addProject.setBounds(1300, 135, 150, 25);
        logoutButton.setBounds(0, 30, 100, 25);
        nameField.setBounds(1300, 50, 150, 25);
        nameLabel.setBounds(1150, 50, 150, 25);
        startLabel.setBounds(1150, 70, 150, 25);
        endLabel.setBounds(1150, 90, 150, 25);
        budgetLabel.setBounds(1150, 110, 150, 25);
        startDateField.setBounds(1300, 70, 150, 25);
        endDateField.setBounds(1300, 90, 150, 25);
        budgetField.setBounds(1300, 110, 150, 25);
        deleteProject.setBounds(1300, 175, 150, 25);
        editProject.setBounds(1300, 265, 150, 25);
        goToProject.setBounds(1300, 220, 150, 25);
        exportButton.setBounds(1300, 310, 150, 25);

    }

    /**
     * Set up the table for the main page.
     * 
     * @return The JScrollPane for the table.
     * @author Parker J.
     */
    private JScrollPane setUpTable() {
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(table);

    }

    /**
     * {@inheritDoc}
     * 
     * @param e the event to be processed
     * @author Parker J.
     * @author Lixin W.
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
        if (e.getSource() == editProject) {
            editProject();
        }
        if (e.getSource() == goToProject) {
            goToProject();
        }

        if (e.getSource() == exportButton) {
            FileManager.exportData(frame, userID);
        }
    }

    /**
     * The action to do when goToProject is selected.
     * 
     * @author Parker J.
     */
    private void goToProject() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            projectList.get(selectedRow).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "No Project Selected.");
        }
    }

    /**
     * The action to do when deleteProject is selected.
     * 
     * @author Parker J.
     */
    private void deleteProject() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String message = "Are you sure you want to delete the project?";
            int deleteChoice = JOptionPane.showConfirmDialog(null, message);
            if (deleteChoice == JOptionPane.OK_OPTION) {
                Main.manager.deleteProject(this.userID, (String) model.getValueAt(selectedRow, 0));
                model.removeRow(selectedRow);
            }
        }
    }

    /**
     * load project from file.
     * 
     * @author Lixin W.
     */
    private void addProject(Project project) {
        projectObjectList.add(project);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        model.addRow(
                new Object[] {
                        project.getName(),
                        sdf.format(project.getStartDate()),
                        sdf.format(project.getEndDate()),
                        nf.format(project.getBudget()),
                        project.getDaysTillFinished(),
                });

    }

    /**
     * The action to do when addProject is selected.
     * 
     * @author Parker J.
     */
    private void addProject() {
        String startDateString = startDateField.getText();
        String endDateString = endDateField.getText();
        Date startDate = new Date(startDateString);
        Date endDate = new Date(endDateString);

        if (Double.parseDouble(budgetField.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, "Your budget must be greater than 0.");
        } else if (!validateDates(startDate, endDate)) {
            String message = "Make sure your start date is not before the current date and \n your end date is not be for your start date.";
            JOptionPane.showMessageDialog(null, message, "Invalid Date", JOptionPane.OK_OPTION);
        } else {
            Project project = new Project(startDate, endDate,
                    nameField.getText(), Double.parseDouble(budgetField.getText()));
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            model.addRow(
                    new Object[] {
                            project.getName(),
                            startDateString,
                            endDateString,
                            nf.format(project.getBudget()),
                            project.getDaysTillFinished(),
                    });
            projectObjectList.add(project);
            projectList.add(new ProjectPage(project, userID));
            Main.manager.addProject(userID, project.getName(),
                    project.getStartDate().toString(),
                    project.getEndDate().toString(),
                    String.valueOf(project.getBudget()));
            nameField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            budgetField.setText("");
        }

    }

    /**
     * @author An Ho
     *         The action to do when editProject is selected.
     */
    private void editProject() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String projectName = (String) model.getValueAt(selectedRow, 0);

            Project thisProjectObject = null;

            // Get the relevant project object
            for (Project p : projectObjectList) {
                if (p.getName() == projectName) {
                    thisProjectObject = p;
                }
            }

            EditProjectScreen editProject = new EditProjectScreen(projectName, userID, model, thisProjectObject,
                    selectedRow);
            editProject.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "No Project Selected.");
        }
    }

    /**
     * Determine if the dates given are valid.
     * 
     * @param date1 The start date.
     * @param date2 The end date.
     * @return If the dates are valid start and end dates.
     */
    public static boolean validateDates(Date date1, Date date2) {
        Date currentDate = new Date();

        if (date1.before(currentDate) || date2.before(currentDate)
                || date1.equals(date2)
                || date1.after(date2)) {
            return false;
        }

        return true;
    }
}
