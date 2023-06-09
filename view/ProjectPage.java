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
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This class holds the code for the project page
 * 
 * @author Parker Johnson (ptj7@uw.edu)
 */
public class ProjectPage extends JFrame implements ActionListener {

    private static final String[] columns = {
            "Item Name",
            "Quantity",
            "Estimated Price",
            "Amount Spent",
    };

    private static final String[] fileColumns = {
            "File Name"
    };

    Project project;
    JFrame frame = this;
    JLabel welcomeLabel = new JLabel("Hello!");

    JButton addItem = new JButton("Add Item");

    JButton deleteItem = new JButton("Delete Item");
    JButton backButton = new JButton("Back");

    JLabel nameLabel = new JLabel("Item Name");
    JLabel quanitityLabel = new JLabel("Quantity");

    JLabel priceLabel = new JLabel("Price");

    JButton saveNotesButton = new JButton("Save Notes");
    JButton addFileButton = new JButton("Add File");
    JButton deleteFileButton = new JButton("Delete FIle");
    JButton openFileButton = new JButton("Open File");

    JTextField nameField = new JTextField();

    JLabel notesLabel = new JLabel("Notes:");

    JTextArea notesArea = new JTextArea();

    JTextField quantityField = new JTextField();

    JTextField priceField = new JTextField();

    ProfileManager manager = new ProfileManager();

    private DefaultTableModel model = new DefaultTableModel(columns, 0) {

        // @Override
        // public boolean isCellEditable(int row, int column) {
        // //all cells false
        // return column != 4;
        // }
    };
    // Create the JTable
    private JTable table = new JTable(model);

    private DefaultTableModel fileModel = new DefaultTableModel(fileColumns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable fileTable = new JTable(fileModel);

    private String userID;

    private JLabel remainingBudget;

    private double budget;

    private JProgressBar progressBar;

    private JLabel remainingMoney = new JLabel();

    /**
     * Initialize the fields of the project page.
     * 
     * @param project The project to present.
     * @param userID  The current user.
     * @author Parker J.
     */
    public ProjectPage(Project project, String userID) {
        this.project = project;
        this.userID = userID;
        this.remainingBudget = new JLabel("Remaining Budget: ");
        this.remainingMoney = new JLabel("$ " + String.valueOf(this.project.getBudget()));
        remainingMoney.setFont(new Font("Comic Sans", Font.BOLD, 18));
        remainingBudget.setFont(new Font("Comic Sans", Font.BOLD, 18));
        this.budget = this.project.getBudget();
        progressBar = new JProgressBar(0, (int) this.budget);
        setUpLabel(userID);
        setUpFrame();
        loadItems(project.getItems());
    }

    /**
     * Load the items from the xml file.
     * 
     * @param items The list of items.
     */
    private void loadItems(java.util.List<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    /**
     * Set up the label.
     * 
     * @param userID The current user.
     * @author Parker J.
     */
    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0, 0, 200, 35);
        welcomeLabel.setFont(new Font(null, Font.PLAIN, 18));
        welcomeLabel.setText("Hello " + userID.toUpperCase(Locale.US));
    }

    /**
     * Set up the frame of the page.
     * 
     * @author Parker J.
     */
    private void setUpFrame() {
        addActions();
        setBounds();
        progressBar.setValue((int) this.budget);
        frame.add(remainingBudget);
        frame.add(backButton);
        frame.add(deleteItem);
        frame.add(nameField);
        frame.add(nameLabel);
        frame.add(quantityField);
        frame.add(quanitityLabel);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(addItem);
        frame.add(welcomeLabel, BorderLayout.NORTH);

        frame.add(notesLabel);
        JScrollPane notesPane = new JScrollPane(notesArea);
        notesPane.setBounds(1150, 220, 300, 430);
        frame.add(notesPane);
        frame.add(saveNotesButton);
        importNotes();
        importFiles();
        // file explorer
        JScrollPane explorer = setUpFileTable();
        explorer.setBounds(10, 100, 150, 550);
        frame.add(explorer, BorderLayout.WEST);
        frame.add(addFileButton);
        frame.add(deleteFileButton);
        frame.add(openFileButton);
        frame.add(progressBar);
        frame.add(remainingMoney);
        JScrollPane sp = setUpTable();
        sp.setBounds(200, 100, 900, 600);
        frame.add(sp, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
    }

    /**
     * Set the actions for the page.
     * 
     * @author Parker J.
     */
    private void addActions() {
        addItem.addActionListener(this);
        deleteItem.addActionListener(this);
        backButton.addActionListener(this);
        saveNotesButton.addActionListener(this);
        addFileButton.addActionListener(this);
        deleteFileButton.addActionListener(this);
        openFileButton.addActionListener(this);

    }

    /**
     * Set the bounds of the components.
     * 
     * @author Parker J.
     */
    private void setBounds() {
        addItem.setBounds(1300, 135, 150, 25);
        backButton.setBounds(0, 30, 100, 25);
        nameField.setBounds(1300, 50, 150, 25);
        nameLabel.setBounds(1150, 50, 150, 25);
        quanitityLabel.setBounds(1150, 70, 150, 25);
        priceLabel.setBounds(1150, 90, 150, 25);
        quantityField.setBounds(1300, 70, 150, 25);
        priceField.setBounds(1300, 90, 150, 25);
        progressBar.setBounds(200, 50, 690, 40);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.RED);
        // budgetField.setBounds(1300, 110, 150, 25);
        // budgetLabel.setBounds(1150, 110, 150, 25);
        deleteItem.setBounds(1300, 175, 150, 25);
        remainingBudget.setBounds(910, 50, 400, 20);
        remainingMoney.setBounds(930, 70, 400, 20);
        notesLabel.setBounds(1150, 200, 150, 25);
        saveNotesButton.setBounds(1300, 700, 150, 25);
        addFileButton.setBounds(10, 700, 150, 25);
        deleteFileButton.setBounds(10, 760, 150, 25);
        openFileButton.setBounds(10, 730, 150, 25);
    }

    /**
     * Set up the table.
     * 
     * @return The JScrollPane for the projects.
     */
    private JScrollPane setUpTable() {
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(table);

    }

    /**
     * Set up the file table.
     * 
     * @return The scroll pa
     * @author Ed C.
     */
    private JScrollPane setUpFileTable() {
        fileTable = new JTable(fileModel);
        fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new JScrollPane(fileTable);
    }

    /**
     * {{@inheritDoc}}
     * 
     * @param e the event to be processed
     */
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
        if (e.getSource() == openFileButton) {
            try {
                openFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    /**
     * Add item from file
     * 
     * @param item the item to be added.
     * @author Lixin W.
     */
    private void addItem(Item item) {

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

        model.addRow(
                new Object[] {
                        item.getMyName(),
                        item.getMyQuantity(),
                        nf.format(item.getMyPrice()),
                        nf.format((item.getMyPrice() * item.getMyQuantity())),
                        nf.format(this.budget - (item.getMyPrice() * item.getMyQuantity())),
                });
        this.budget = this.budget - (item.getMyPrice() * item.getMyQuantity());
        this.remainingBudget.setText("Remaining Budget: ");
        remainingMoney.setText("$" + String.valueOf(this.budget));
        updateProgressBar((int) this.budget);

    }

    /**
     * Actions to take when addItem is selected.
     * 
     * @author Parker J.
     */
    private void addItem() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String name = nameField.getText();
        if (quantity <= 0 || price <= 0) {
            JOptionPane.showMessageDialog(null, "Budget and Price must be greater than 0");
        } else {
            Item item = new Item(name, quantity, price);
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            model.addRow(
                    new Object[] {
                            item.getMyName(),
                            item.getMyQuantity(),
                            nf.format(item.getMyPrice()),
                            nf.format((item.getMyPrice() * item.getMyQuantity())),
                    });
            this.budget = this.budget - (item.getMyPrice() * item.getMyQuantity());

            Main.manager.addItem(this.userID,
                    this.project.getName(),
                    item.getMyName(),
                    null,
                    String.valueOf(item.getMyPrice()),
                    String.valueOf(item.getMyQuantity()));
            this.remainingBudget.setText("Remaining Budget: ");
            remainingMoney.setText("$" + String.valueOf(this.budget));
            updateProgressBar((int) this.budget);
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
        }
    }

    /**
     * Actions to take when deleteItem is selected.
     * 
     * @author Parker J.
     */
    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // -1 means no row is selected
            String message = "Are you sure you want to delete the item in row " + (selectedRow + 1);
            int deleteChoice = JOptionPane.showConfirmDialog(null, message, "Delete Item", JOptionPane.YES_NO_OPTION);
            if (deleteChoice == JOptionPane.YES_OPTION) {
                String itemName = (String) model.getValueAt(selectedRow, 0);
                this.budget += Double.valueOf(
                        String.valueOf(Main.manager.getItemQuantity(this.userID, this.project.getName(), itemName)))
                        * Double.valueOf(String.valueOf(
                                Main.manager.getItemCostPerUnit(this.userID, this.project.getName(), itemName)));
                remainingBudget.setText("Remaining budget: ");
                remainingMoney.setText("$ " + String.valueOf(this.budget));
                Main.manager.deleteItem(this.userID, this.project.getName(), itemName);
                model.removeRow(selectedRow);
                updateProgressBar((int) this.budget);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a file to filemodel JScrollPane.
     * 
     * @author Edward Chung
     */
    private void addFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file path: " + filePath);
            manager.addFilePath(this.userID, this.project.getName(), filePath);
            String fileName = selectedFile.getName();
            String fileExtension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = fileName.substring(i + 1);
            }
            String fullFileName = fileName;
            fileModel.addRow(
                    new Object[] {
                            fullFileName
                    });
        }

    }

    /**
     * Delete selected file from the filemodel JSrollpane
     * 
     * @author Edward Chung
     */
    private void deleteFile() {

        int selectedRow = fileTable.getSelectedRow();
        if (selectedRow != -1) {
            String message = "Are you sure you want to delete file " + fileTable.getValueAt(selectedRow, 0);
            int deleteChoice = JOptionPane.showConfirmDialog(null, message);
            if (deleteChoice == JOptionPane.OK_OPTION) {
                String fileName = fileModel.getValueAt(0, selectedRow).toString();
                System.out.println("filename = " + fileName);
                for (String theFilePath : manager.getProjectFilePaths(this.userID, this.project.getName())) {

                    if (theFilePath.contains(fileName)) {
                        manager.deleteFilePath(this.userID, this.project.getName(), theFilePath);

                    }
                }
                fileModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Selecte a File to Delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Add saved file paths to the project.
     * 
     * @param filePaths The paths to add
     * @author Ed C.
     * @author Nick Z.
     */
    private void addFile(ArrayList<String> filePaths) {
        for (String s : filePaths) {
            File selectedFile = new File(s);
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file path: " + filePath);
            String fileName = selectedFile.getName();
            String fileExtension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = fileName.substring(i + 1);
            }
            String fullFileName = fileName;
            fileModel.addRow(
                    new Object[] {
                            fullFileName
                    });
        }
    }

    /**
     * Open selected file from the filemodel JScrollPane.
     * 
     * @author Edward Chung
     */
    private void openFile() throws IOException {
        int choiceNum = fileTable.getSelectedRow();
        String fileName = fileModel.getValueAt(choiceNum, 0).toString();
        File fileToOpen = null;
        for (String theFilePath : Main.manager.getProjectFilePaths(this.userID, this.project.getName())) {

            if (theFilePath.contains(fileName)) {
                try {
                    fileToOpen = new File(theFilePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Desktop desktop = Desktop.getDesktop();
        if (fileToOpen.exists()) {
            desktop.open(fileToOpen);
        } else {
            JOptionPane.showConfirmDialog(null,
                    "File Not Found, Check Directory!",
                    "Notification",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Import Notes from XML file.
     * 
     * @author Edward Chung
     */
    private void importNotes() {
        notesArea.setText(Main.manager.getProjectNotes(this.userID, this.project.getName()));
    }

    /**
     * Export Notes to XML file.
     * 
     * @author Edward Chung
     */
    private void exportNotes() {
        Main.manager.addProjectNotes(this.userID, this.project.getName(), notesArea.getText());
        JOptionPane.showConfirmDialog(null,
                "Notes Saved!",
                "Notification",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Update the progress bar.
     * 
     * @param newBudget The new value of the progress bar.
     * @author Parker J.
     */
    private void updateProgressBar(int newBudget) {
        this.progressBar.setValue(newBudget);
    }

    /**
     * Import files from the xml file.
     * 
     * @author Nick Z.
     * @author Ed C.
     */
    private void importFiles() {
        addFile((ArrayList) Main.manager.getProjectFilePaths(userID, this.project.getName()));
    }
}
