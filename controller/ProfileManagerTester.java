package controller;

import java.io.IOException;
// import java.util.List;

/**
 * A simple tester class for the xml ProfileManager.java.
 * Uncomment one chunk at a time to see how it affects the .xml files
 * in /data/ folder.
 * @author Nickolas Zahos (nzahos@uw.edu)
 */
public class ProfileManagerTester {

    public static void main(String[] args) {

        ProfileManager profileManager = new ProfileManager();

        System.out.println(profileManager.getProjectFilePaths("", "1"));

        // // Adding the first profile - Patty
        // try {
        //     profileManager.addProfile("Patty", "epicflowers@hotmail.com");
        // } catch (IOException e) {
        //     e.getMessage();
        // }

        // // Adding the first project to Patty's profile
        // profileManager.addProject("Patty", "Flower Shop Remodel", "2023-05-01", "2023-06-10", "1500");

        // // Adding file paths to Patty's first project
        // profileManager.addFilePath("Patty", "Flower Shop Remodel", "documents/flower_shop_folder/design_idea.pdf");
        // profileManager.addFilePath("Patty", "Flower Shop Remodel", "documents/flower_shop_folder/budget_estimate.xlsx");

        // // Adding items to Patty's first project
        // profileManager.addItem("Patty", "Flower Shop Remodel", "Metal Fixture", "Metal rack arms for the new shelving", "5.00", "6");
        // profileManager.addItem("Patty", "Flower Shop Remodel", "Wood Panel", "Wooden flat panels to replace bottom of boxes", "12.35", "9");

        // //Adding notes to Patty's first project
        // profileManager.addProjectNotes("Patty", "Flower Shop Remodel", "Remember to call Bob before the deadline!");

        // // Adding the second project to Patty's profile
        // profileManager.addProject("Patty", "Garden Upgrade", "2023-07-01", "2023-08-15", "2000");

        // // Adding file paths to Patty's second project
        // profileManager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/plant_varieties.pdf");
        // profileManager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/materials_list.xlsx");

        // // Adding items to Patty's second project
        // profileManager.addItem("Patty", "Garden Upgrade", "Flower Seeds", "Assorted flower seeds for garden upgrade", "1.00", "100");
        // profileManager.addItem("Patty", "Garden Upgrade", "Fertilizer", "High-quality fertilizer for plant growth", "20.00", "5");

        // System.out.println(profileManager.getProjectNotes("Patty", "Flower Shop Remodel"));

        // // Adding the second profile - John
        // try {
        //     profileManager.addProfile("John", "johndoe@gmail.com");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // // Adding the first project to John's profile
        // profileManager.addProject("John", "Kitchen Renovation", "2023-05-15", "2023-07-15", "5000");

        // // Adding file paths to John's first project
        // profileManager.addFilePath("John", "Kitchen Renovation", "documents/kitchen_renovation_folder/design_sketch.jpg");
        // profileManager.addFilePath("John", "Kitchen Renovation", "documents/kitchen_renovation_folder/materials_and_costs.xlsx");

        // // Adding items to John's first project
        // profileManager.addItem("John", "Kitchen Renovation", "Cabinets", "Wooden cabinets for kitchen storage", "100.00", "10");
        // profileManager.addItem("John", "Kitchen Renovation", "Countertop", "Granite countertop for kitchen", "500.00", "1");

        // Adding notes to John's first project.
        // profileManager.addProjectNotes("John", "Kitchen Renovation", "Don't be dumb! Remember what Jake said. Don't work later than 7pm on this. Don't forget to eat dinner! Yep.");

        // // TEST GETTERS
        // List<String> listOfProjects = profileManager.getProjects("Patty");

        // for(String s : listOfProjects) {
        //     System.out.println("Project Name: " + s 
        //     + "\nPROJECT START: " + profileManager.getProjectStartDate("Patty", s)
        //     + "\nPROJECT END: " + profileManager.getProjectEndDate("Patty", s)
        //     + "\nPROFILE BUDGET: " + profileManager.getProjectBudget("Patty", s)
        //     + "\nFILES: " + profileManager.getProjectFilePaths("Patty", s)
        //     + "\nITEMS: " + profileManager.getProjectItems("Patty", s)
        //     + "\nFIRST ITEM NAME: " + profileManager.getItemDescription("Patty", s, profileManager.getProjectItems("Patty", s).get(0))
        //     + "\nFIRST ITEM COST/P/U: " + profileManager.getItemCostPerUnit("Patty", s, profileManager.getProjectItems("Patty", s).get(0))
        //     + "\nFIRST ITEM QUANTITY: " + profileManager.getItemQuantity("Patty", s, profileManager.getProjectItems("Patty", s).get(0))
        //     + "\nPROJECT NOTES: " + profileManager.getProjectNotes("Patty", s));
        // }
    }
}
