package view;


import controller.Main;
import model.Item;
import model.Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class holds the code for the project page
 * @author Parker Johnson (ptj7@uw.edu)
 */
public class ProjectPage extends JFrame implements ActionListener {

    private static final String[] columns = {
            "Item Name",
            "Quantity",
            "Estimated Price",
            "Amount Spent",
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

    JTextField nameField = new JTextField();

    JLabel notesLabel = new JLabel("Notes:");

    JTextArea notesArea = new JTextArea();


    JTextField quantityField = new JTextField();

    JTextField priceField = new JTextField();


    private DefaultTableModel model = new DefaultTableModel(columns, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return column != 4;
        }
    };
    // Create the JTable
    private JTable table = new JTable(model);

    private String userID;

    private JLabel remainingBudget;

    private double budget;

    private JProgressBar progressBar;

    private JLabel remainingMoney = new JLabel();

    public ProjectPage(Project project, String userID){
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

    private void loadItems(java.util.List<Item> items){
        for(Item item : items){
            addItem(item);
        }
    }


    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0,0,200,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,18));
        welcomeLabel.setText("Hello "+ userID.toUpperCase(Locale.US));
    }

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
        frame.add(progressBar);
        frame.add(remainingMoney);
        JScrollPane sp = setUpTable();
        sp.setBounds(150, 100, 950, 600);
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
        progressBar.setBounds(150, 50, 740, 40);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.RED);
//        budgetField.setBounds(1300, 110, 150, 25);
//        budgetLabel.setBounds(1150, 110, 150, 25);
        deleteItem.setBounds(1300, 175, 150, 25);
        remainingBudget.setBounds(910, 50, 400, 20 );
        remainingMoney.setBounds(930, 70, 400, 20);
        notesLabel.setBounds(1150, 200, 150, 25);
        saveNotesButton.setBounds(1300, 700, 150, 25);
    }

    private JScrollPane setUpTable() {
        table = new JTable(model);
        return new JScrollPane(table);

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


    }

    /**
     * Add item from file
     */
    private void addItem(Item item){

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

        model.addRow(
                new Object[]{
                        item.getMyName(),
                        item.getMyQuantity(),
                        nf.format(item.getMyPrice()),
                        nf.format((item.getMyPrice() * item.getMyQuantity())),
                        nf.format(this.budget - (item.getMyPrice() * item.getMyQuantity())),
                }
        );
        this.budget = this.budget - (item.getMyPrice() * item.getMyQuantity());
        this.remainingBudget.setText("Remaining Budget: $");
        remainingMoney.setText("$ "+String.valueOf(this.budget));
        updateProgressBar((int) this.budget);


    }

    /**
     * Actions to take when addItem is selected.
     */
    private void addItem() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String name = nameField.getText();

        Item item = new Item(name, quantity,price);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        model.addRow(
                new Object[]{
                        item.getMyName(),
                        item.getMyQuantity(),
                        nf.format(item.getMyPrice()),
                        nf.format((item.getMyPrice() * item.getMyQuantity())),
                }
        );
        this.budget = this.budget - (item.getMyPrice() * item.getMyQuantity());

        Main.manager.addItem(this.userID,
                this.project.getName(),
                item.getMyName(),
                null,
                String.valueOf(item.getMyPrice()),
                String.valueOf(item.getMyQuantity()));
        this.remainingBudget.setText("Remaining Budget: $");
        remainingMoney.setText("$ " + String.valueOf(this.budget));
        updateProgressBar((int) this.budget);
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
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
                String itemName = (String)model.getValueAt(choiceNum-1, 0);
                this.budget +=
                        Double.valueOf(String.valueOf(Main.manager.getItemQuantity(this.userID, this.project.getName(), itemName)))
                        * Double.valueOf(String.valueOf(Main.manager.getItemCostPerUnit(this.userID, this.project.getName(), itemName)));
                remainingBudget.setText("Remaining budget: $");
                remainingMoney.setText("$ " +String.valueOf(this.budget));
                Main.manager.deleteItem(this.userID, this.project.getName(), itemName);
                model.removeRow(choiceNum - 1);
                updateProgressBar((int) this.budget);
            }
        }
    }

    private void importNotes() {
        notesArea.setText(Main.manager.getProjectNotes(this.userID, this.project.getName()));
    }

    private void exportNotes() {
        Main.manager.addProjectNotes(this.userID, this.project.getName(), notesArea.getText());
    }

    private void updateProgressBar(int newBudget) {
        this.progressBar.setValue(newBudget);
    }
}

