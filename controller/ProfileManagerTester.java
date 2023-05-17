package controller;

// This is just a tester class for our profile manager
public class ProfileManagerTester {

    public static void main(String[] args) {

        //ProfileManager profileManager = new ProfileManager();

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

        // // Adding the second project to Patty's profile
        // profileManager.addProject("Patty", "Garden Upgrade", "2023-07-01", "2023-08-15", "2000");

        // // Adding file paths to Patty's second project
        // profileManager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/plant_varieties.pdf");
        // profileManager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/materials_list.xlsx");

        // // Adding items to Patty's second project
        // profileManager.addItem("Patty", "Garden Upgrade", "Flower Seeds", "Assorted flower seeds for garden upgrade", "1.00", "100");
        // profileManager.addItem("Patty", "Garden Upgrade", "Fertilizer", "High-quality fertilizer for plant growth", "20.00", "5");


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
        //     + "\nFIRST ITEM QUANTITY: " + profileManager.getItemQuantity("Patty", s, profileManager.getProjectItems("Patty", s).get(0)));
        // }
    }
}







// BACKUP CODE

// package controller;

// /**
//  * This is a tester class to test our ProfileManager class.
//  * It will generate fake data into the ProfileData.xml located in the 'data' package.
//  */
// public class ProfileManagerTester {
//     public static void main(String[] args) {

//         ProfileManager manager = new ProfileManager();

//         // Adding the first profile - Patty
//         manager.addProfile("Patty", "epicflowers@hotmail.com");

//         // Adding the first project to Patty's profile
//         manager.addProject("Patty", "Flower Shop Remodel", "2023-05-01", "2023-06-10", "1500");

//         // Adding file paths to Patty's first project
//         manager.addFilePath("Patty", "Flower Shop Remodel", "documents/flower_shop_folder/design_idea.pdf");
//         manager.addFilePath("Patty", "Flower Shop Remodel", "documents/flower_shop_folder/budget_estimate.xlsx");

//         // Adding items to Patty's first project
//         manager.addItem("Patty", "Flower Shop Remodel", "Metal Fixture", "Metal rack arms for the new shelving", "5.00", "6");
//         manager.addItem("Patty", "Flower Shop Remodel", "Wood Panel", "Wooden flat panels to replace bottom of boxes", "12.35", "9");

//         // Adding the second project to Patty's profile
//         manager.addProject("Patty", "Garden Upgrade", "2023-07-01", "2023-08-15", "2000");

//         // Adding file paths to Patty's second project
//         manager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/plant_varieties.pdf");
//         manager.addFilePath("Patty", "Garden Upgrade", "documents/garden_upgrade_folder/materials_list.xlsx");

//         // Adding items to Patty's second project
//         manager.addItem("Patty", "Garden Upgrade", "Flower Seeds", "Assorted flower seeds for garden upgrade", "1.00", "100");
//         manager.addItem("Patty", "Garden Upgrade", "Fertilizer", "High-quality fertilizer for plant growth", "20.00", "5");

//         // Adding the second profile - John
//         manager.addProfile("John", "johndoe@gmail.com");

//         // Adding the first project to John's profile
//         manager.addProject("John", "Kitchen Renovation", "2023-05-15", "2023-07-15", "5000");

//         // Adding file paths to John's first project
//         manager.addFilePath("John", "Kitchen Renovation", "documents/kitchen_renovation_folder/design_sketch.jpg");
//         manager.addFilePath("John", "Kitchen Renovation", "documents/kitchen_renovation_folder/materials_and_costs.xlsx");

//         // Adding items to John's first project
//         manager.addItem("John", "Kitchen Renovation", "Cabinets", "Wooden cabinets for kitchen storage", "100.00", "10");
//         manager.addItem("John", "Kitchen Renovation", "Countertop", "Granite countertop for kitchen", "500.00", "1");

//         // Save profiles to the XML file
//         manager.saveProfile();
//     }
// }