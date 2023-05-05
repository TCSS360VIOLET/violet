package view;

import model.Project;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WelcomePanel extends JPanel implements ActionListener{

    private final String[] columns = {
            "Project Name",
            "Start Date",
            "End Date",
            "Budget",
            "Days Until Deadline",
            "Project Details"
    };


    JPanel panel = new JPanel();
    JLabel welcomeLabel = new JLabel("Hello!");

    JButton addProject = new JButton("Add Project...");

    JButton goToProject = new JButton("Go To Project");

    JButton deleteProject = new JButton("Delete Project");

    JButton logoutButton = new JButton("Logout");

    //HashMap<String, String> ids = new IDandPasswords().getLoginInfo();

    JLabel nameLabel = new JLabel("Project Name");

    JLabel startLabel = new JLabel("Start Date (mm/dd/yyyy)");

    JLabel endLabel = new JLabel("End Date (mm/dd/yyyy)");

    JLabel budgetLabel  = new JLabel("Budget");

    JTextField nameField = new JTextField();

    JTextField startDateField = new JTextField();

    JTextField endDateField = new JTextField();

    JTextField budgetField = new JTextField();

    ArrayList<ProjectPage> projectList;
    private DefaultTableModel model = new DefaultTableModel(columns, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            if (column == 4) {
                return false;
            }
            return true;
        }
    };
    // Create the JTable
    private JTable table = new JTable(model);

    String userID;
    public WelcomePanel(String userID, String userEmail){
        this.userID = userID;
        projectList = new ArrayList<>(0);
        setUpLabel(userID);
        setUpFrame();

    }


    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,18));
        welcomeLabel.setText("Hello "+ userID.toUpperCase(Locale.US));
    }
    public JPanel setUpFrame() {
        addActions();
        setBounds();
        panel.add(logoutButton);
        panel.add(goToProject);
        panel.add(deleteProject);
        panel.add(nameField);
        panel.add(nameLabel);
        panel.add(startDateField);
        panel.add(startLabel);
        panel.add(endLabel);
        panel.add(endDateField);
        panel.add(budgetLabel);
        panel.add(budgetField);
        panel.add(addProject);
        panel.add(welcomeLabel, BorderLayout.NORTH);
        JScrollPane sp = setUpTable();
        sp.setBounds(100, 100, 1000, 600);
        panel.add(sp, BorderLayout.CENTER);
        panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLayout(null);
        panel.setVisible(true);
        return panel;
    }


    private void addActions() {
        goToProject.addActionListener(this);
        addProject.addActionListener(this);
        deleteProject.addActionListener(this);
        logoutButton.addActionListener(this);
    }

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
    }

    private JScrollPane setUpTable() {
        table = new JTable(model);
        return new JScrollPane(table);

    }


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
        if (monthsDiff || yearsDiff) {
            return false;
        } else if (Integer.parseInt(d2Month) - Integer.parseInt(d1Days) == 0){
            if (daysDiff && yearsDiff) {
                return false;
            }
        }



        return true;
    }

    public ArrayList<ProjectPage> getProjectList() {
        return new ArrayList<>(projectList);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            //panel.((JFrame)getParent()).dispose();
            //LogInPage page = new LogInPage(ids);
        }

        if (e.getSource() == addProject) {
            String startDateString = startDateField.getText();
            String endDateString = endDateField.getText();
            Date startDate = new Date(startDateString);
            Date endDate = new Date(endDateString);
            if (validDateRange(startDateString, endDateString)) {


                long daysLeft = endDate.getTime() - startDate.getTime();
                int deadline = (int) TimeUnit.DAYS.convert(daysLeft, TimeUnit.MILLISECONDS);
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
                nameField.setText("");
                startDateField.setText("");
                endDateField.setText("");
                budgetField.setText("");
            } else {
                JOptionPane.showInternalMessageDialog(null, "Start Date must be before the End Date");
            }

        }

        if (e.getSource() == deleteProject) {
            String choice = JOptionPane.showInputDialog(null, "Which project do would you like to delete?");
            int choiceNum = Integer.parseInt(choice);
            if (!choice.isEmpty()) {
                String message = "Are you sure you want to delete the project in row " + choice;
                int deleteChoice = JOptionPane.showConfirmDialog(null, message);
                if (deleteChoice == JOptionPane.OK_OPTION) {
                    model.removeRow(choiceNum - 1);
                }
            }
        }

        if (e.getSource() == goToProject) {
            String choice = JOptionPane.showInputDialog(null, "Which project do would you like to visit?");
            int choiceNum = Integer.parseInt(choice);
            if (projectList.size() != 0 && projectList.get(choiceNum-1) != null && projectList.get(choiceNum-1).isActive()) {
                projectList.get(choiceNum-1).setVisible(true);
            } else if (!choice.isEmpty()) {
                ProjectPage projectPage = new ProjectPage((Project) model.getValueAt(choiceNum - 1, model.getColumnCount()-1), userID);
                projectList.add(projectPage);
            }

        }
    }
}
