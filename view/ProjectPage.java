package view;


import controller.Main;
import controller.ProfileManager;
import model.Item;
import model.Project;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class ProjectPage extends JFrame implements ActionListener {

    private static final String[] columns = {
            "Item Name",
            "Quantity",
            "Unit Price",
            "Initial Budget",
            "Remain Balance"
    };

    Project project;
    

    JFrame frame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello!");

    JButton addItem = new JButton("Add Item");
    JButton deleteItem = new JButton("Delete Item");
    JButton backButton = new JButton("Back");
    JLabel nameLabel = new JLabel("Item Name");
    JLabel quanitityLabel = new JLabel("Quantity");

    JLabel priceLabel = new JLabel("Price");
    JTextField nameField = new JTextField();

    JTextField quantityField = new JTextField();

    JTextField priceField = new JTextField();

    JTextField budgetField = new JTextField();

    JLabel budgetLabel = new JLabel("Budget");
    
    //An Ho - 05/18 ------------
    ProfileManager pManager = new ProfileManager();
    //for project summary
    JLabel initialBudgetLabel = new JLabel();
    JLabel totalExpensesLabel = new JLabel();
    JLabel totalBalanceLabel = new JLabel();
    JPanel summaryPanel = new JPanel();
       

    //
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

    public ProjectPage(Project project, String userID){
        this.project = project;
        this.userID = userID;
        setUpLabel(userID);
        setUpSummary();
        setUpFrame();

    }

    /**
     * set up welcome label
     * @param userID
     * @author An Ho
     */
    private void setUpLabel(String userID) {
        welcomeLabel.setBounds(0,0,600,35);
        welcomeLabel.setFont(new Font(null,Font.PLAIN,18));
        welcomeLabel.setText("user: "+ userID.toUpperCase(Locale.US) + " | project: " + project.getName());
        
    }
    /**
     * set up project summary panel
     * @author An Ho
     */
    private void setUpSummary(){        
        //updated project summary
        updatedTotal();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.X_AXIS));
        summaryPanel.add(initialBudgetLabel);
        summaryPanel.add(Box.createHorizontalGlue()); // Adds horizontal space
        summaryPanel.add(totalExpensesLabel);
        summaryPanel.add(Box.createHorizontalGlue()); // Adds horizontal space
        summaryPanel.add(totalBalanceLabel);
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
        frame.setLayout(new BorderLayout());
        frame.add(welcomeLabel, BorderLayout.NORTH);
        JScrollPane sp = setUpTable();
        sp.setBounds(150, 100, 950, 600);
        frame.add(sp, BorderLayout.CENTER);

        //An Ho
        summaryPanel.setBounds(0, 0, sp.getWidth(), 100);
        frame.add(summaryPanel,BorderLayout.SOUTH);

        //
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
    }

    private JScrollPane setUpTable() {
        showAllItems();
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


    }

    /**
     * Actions to take when addItem is selected.
     * @author An Ho
     */
    private void addItem() {
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String name = nameField.getText();
        double budget = Double.parseDouble(budgetField.getText());

        Item item = new Item(name, quantity, budget, price);
        //add new item row in table
        addItemRow(item);
        //import new Item in xml file.
        addItemData(item);
        //updated project summary
        updatedTotal();

        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        budgetField.setText("");
    }
    /**
     * When user click save, the method will update the Item in XML file.
     * @param myItem the imported Item.
     * @author An Ho
     */
    private void addItemData(Item myItem){
        
        pManager.addItem(this.userID,
        this.project.getName(),
        myItem.getMyName(),
        String.valueOf(myItem.getMyBudget()),
        String.valueOf(myItem.getMyPrice()),
        String.valueOf(myItem.getMyQuantity()));

    }
    /**
     * Add new row to Item table
     * @param item
     * @author An Ho
     */
    private void addItemRow(Item item){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        model.addRow(
            new Object[]{
                    item.getMyName(),
                    item.getMyQuantity(),
                    nf.format(item.getMyPrice()),
                    nf.format(item.getMyBudget()),
                    nf.format(item.getDifference()),
            });
    }
    /**
     * Show all added items in Item Table, show all previous added item
     * @author An Ho
     */
    private void showAllItems(){
        
        List<String> itemList = new ArrayList<>(Main.manager.getProjectItems(this.userID,this.project.getName()));

        for (int i = 0; i < itemList.size(); i++) {
            String itemName = itemList.get(i);
            try {
            Item item = new Item(itemName, 
                                pManager.getItemQuantity(this.userID, this.project.getName(), itemName),
                                pManager.getItemBudget(this.userID, this.project.getName(), itemName), 
                                (double)pManager.getItemCostPerUnit(this.userID, this.project.getName(), itemName));    
            addItemRow(item);           
        ; 
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
                // You can handle the exception here or rethrow it if needed
            }           
        }        
    }

    private void updatedTotal(){
    //calculate the total of Expenses and Balance
        List<String> itemList = new ArrayList<>(Main.manager.getProjectItems(this.userID,this.project.getName()));
        double totalBalances = 0.0;
        double totalExpense = 0.0;
        double totalBudget = 0.0;
        for (int i = 0; i < itemList.size(); i++) {
            String itemName = itemList.get(i);
            try {
            Item item = new Item(itemName, 
                                pManager.getItemQuantity(this.userID, this.project.getName(), itemName),
                                pManager.getItemBudget(this.userID, this.project.getName(), itemName), 
                                (double)pManager.getItemCostPerUnit(this.userID, this.project.getName(), itemName));    
            totalBalances += item.getDifference();      
            totalExpense += item.getTotalExpense();   
            totalBudget += item.getMyBudget(); 
        
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }           
        }
        totalExpensesLabel.setText("Total Expenses: $" + totalExpense);
        totalBalanceLabel.setText("Total Remain Budget: $"+ totalBalances);
        initialBudgetLabel.setText("Initial Budget: $" + totalBudget);
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
}

