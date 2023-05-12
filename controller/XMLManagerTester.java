package controller;

import model.XMLManager;

public class XMLManagerTester {
    public static void main(String[] theArgs) {
        XMLManager myXMLManager = new XMLManager();

        // Try to read a data field from and XML file
        String res = myXMLManager.getData("TestData.xml", "DataGroup", "DataFieldOne");

        // Output the grabbed data
        System.out.println(res);
    }
}