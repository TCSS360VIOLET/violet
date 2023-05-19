package view;


import controller.Main;
import controller.ProfileManager;
import model.Item;
import model.Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;


public class ProjectPage extends JFrame implements ActionListener {

    private static final String[] columns = {
            "Item Name",
            "Quantity",
            "Estimated Price",
            "Amount Spent",
            "Over/Under spent by"
    };

    private static final String[] fileColumns = {
        "File Name"
    };

    Project project;
    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello!");

    JButton addItem = new JButton("Add Item");
    JButton deleteItem = new JButton("Delete Item");
    JButton backButton = new JButton("Back");
    JButton saveNotesButton = new JButton("Save Notes");
    JButton addFileButton = new JButton("Add File");
    JButton deleteFileButton = new JButton("Delete FIle");
    JLabel nameLabel = new JLabel("Item Name");
    JLabel quanitityLabel = new JLabel("Quantity");

    JLabel priceLabel = new JLabel("Price");
    JTextField nameField = new JTextField();

    JTextField quantityField = new JTextField();

    JTextField priceField = new JTextField();

    JTextField budgetField = new JTextField();

    JLabel budgetLabel = new JLabel("Budget");

    JLabel notesLabel = new JLabel("Notes:");

    JTextArea notesArea = new JTextArea();

    ProfileManager manager = new ProfileManager(); 

    private DefaultTableModel model = new DefaultTableModel(columns, 0) {

        // @Override
        // public boolean isCellEditable(int row, int column) {
        //     //all cells false
        //     return column != 4;
        // }
    };
    // Create the JTable
    private JTable table = new JTable(model);

    //file explorer 
    private DefaultTableModel fileModel = new DefaultTableModel(fileColumns, 0) {

    };

    private JTable fileTable = new JTable(fileModel);
    

    private String userID;

    public ProjectPage(Project project, String userID){
        this.project = project;
        this.userID = userID;
        setUpLabel(userID);
        setUpFrame();

    }


    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,18));
        welcomeLabel.setText("Hello "+ userID.toUpperCase(Locale.US));
    }
    
    private void setUpFrame() {
        addActions();
        setBounds();
        frame.add(backButton);
        frame.add(deleteItem);
        frame.add(nameField);
        frame.add(nameLabel);
        frame.add(quantityField);
        frame.add(quanitityLabel);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(addItem);
        frame.add(budgetField);
        frame.add(budgetLabel);
        frame.add(welcomeLabel, BorderLayout.NORTH);
        //adding notes section
        frame.add(notesLabel);
        JScrollPane notesPane = new JScrollPane(notesArea);
        notesPane.setBounds(1150, 220, 300, 430);
        frame.getContentPane().add(notesPane);
        frame.add(saveNotesButton);  
        //testing
        importNotes(); 
        //file explorer
        JScrollPane explorer = setUpFileTable();
        explorer.setBounds(10, 100, 150, 550);  
        frame.add(explorer, BorderLayout.WEST);
        frame.add(addFileButton);
        frame.add(deleteFileButton);
        //Items Table
        JScrollPane sp = setUpTable();
        sp.setBounds(200, 100, 850, 600);
        frame.add(sp, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private void addActions() {
        addItem.addActionListener(this);
        deleteItem.addActionListener(this);
        backButton.addActionListener(this);
        saveNotesButton.addActionListener(this);
        addFileButton.addActionListener(this);
        deleteFileButton.addActionListener(this);
    }

    private void setBounds() {
        addItem.setBounds(1300, 135, 150, 25);
        backButton.setBounds(0, 30, 100,25);
        nameField.setBounds(1300, 50, 150, 25);
        nameLabel.setBounds(1150, 50, 150, 25);
        quanitityLabel.setBounds(1150, 70, 150, 25);
        priceLabel.setBounds(1150, 90, 150, 25);
        quantityField.setBounds(1300, 70, 150, 25);
        priceField.setBounds(1300, 90, 150, 25);
        budgetField.setBounds(1300, 110, 150, 25);
        budgetLabel.setBounds(1150, 110, 150, 25);
        deleteItem.setBounds(1300, 175, 150, 25);
        notesLabel.setBounds(1150, 200, 150, 25);
        saveNotesButton.setBounds(1300, 700, 150, 25);
        addFileButton.setBounds(10, 700, 150, 25);
        deleteFileButton.setBounds(10, 730, 150, 25);
    }

    private JScrollPane setUpTable() {
        table = new JTable(model);
        return new JScrollPane(table);

    }

    private JScrollPane setUpFileTable() {
        fileTable = new JTable(fileModel);
        return new JScrollPane(fileTable);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.setVisible(false);
        }

        if (e.getSource() == addItem) {
            addItem();
        }

        if (e.getSource() == deleteItem) {
            deleteItem();
        }

        if (e.getSource() == saveNotesButton) {
            exportNotes();
        }

        if (e.getSource() == addFileButton) {
            addFile();
        }

        if (e.getSource() == deleteFileButton) {
            deleteFile();
        }


    }

    /**
     * Actions to take when addItem is selected.
     */
    private void addItem() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String name = nameField.getText();
        double budget = Double.parseDouble(budgetField.getText());

        Item item = new Item(name, quantity, budget, price);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        model.addRow(
                new Object[]{
                        item.getMyName(),
                        item.getMyQuantity(),
                        nf.format(item.getMyPrice()),
                        nf.format(item.getMyBudget()),
                        nf.format(item.getDifference()),
                }
        );
        Main.manager.addItem(this.userID,
                this.project.getName(),
                item.getMyName(),
                null,
                String.valueOf(item.getMyPrice()),
                String.valueOf(item.getMyQuantity()));
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        budgetField.setText("");
    }

    /**
     * Actions to take when deleteItem is selected.
     */
    private void deleteItem() {
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

    private void addFile() {
        JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Selected file path: " + filePath);
                    manager.addFilePath(this.userID, this.project.getName(), filePath);
                    String fileName = selectedFile.getName();
                    String fileExtension;

                    int i = fileName.lastIndexOf('.');
                    if (i > 0) {
                        fileExtension = fileName.substring(i+1);
                    }
                    String fullFileName = fileName;
                    fileModel.addRow(
                            new Object[]{
                                fullFileName
                            }
                    );
                }

    }

    private void deleteFile() {
        String choice = JOptionPane.showInputDialog(null, "Which file do would you like to delete?");
        int choiceNum = Integer.parseInt(choice);
        if (!choice.isEmpty()) {
            String message = "Are you sure you want to delete the file in row " + choice;
            int deleteChoice = JOptionPane.showConfirmDialog(null, message);
            if (deleteChoice == JOptionPane.OK_OPTION) {
                String fileName = fileModel.getValueAt(0, choiceNum - 1).toString();
                System.out.println("filename = " + fileName);
                for(String theFilePath : manager.getProjectFilePaths(this.userID, this.project.getName())){

                    if(theFilePath.contains(fileName)){
                        manager.deleteFilePath(this.userID, this.project.getName(), theFilePath);
                       
                    }
                }
                fileModel.removeRow(choiceNum - 1);
            }
        }
    }


    private void importNotes() {
        notesArea.setText(manager.getProjectNotes(this.userID, this.project.getName()));
    }

    private void exportNotes() {
        manager.addProjectNotes(this.userID, this.project.getName(), notesArea.getText());
    }


}