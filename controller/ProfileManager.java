package controller;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Profile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The ProfileManager class handles creation and storage of user profiles
 * and their associated projects and items.
 * It builds an XML document which is then saved to a file.
 * 
 * @author  Nickolas Zahos (nzahos@uw.edu)
 */

public class ProfileManager {

    /**
     * The XML Document that is used to build the user profiles.
     */
    private Document doc;

    /**
     * The root element in the XML Document. All user profiles are children of this root element.
     */
    private Element rootElement;

    /**
     * Constructor for the ProfileManager class.
     * Initializes a new XML Document and sets the root element to "Profile".
     */
    public ProfileManager() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("Profile");
            doc.appendChild(rootElement);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    /**
     * Adds a new user profile to the XML document.
     *
     * @param username The username of the user.
     * @param email    The email of the user.
     */
    public void addProfile(String username, String email) {
        Element profile = doc.createElement("User");
        rootElement.appendChild(profile);

        Element usernameElement = doc.createElement("Username");
        usernameElement.appendChild(doc.createTextNode(username));
        profile.appendChild(usernameElement);

        Element emailElement = doc.createElement("Email");
        emailElement.appendChild(doc.createTextNode(email));
        profile.appendChild(emailElement);
    }

    /**
     * Adds a new project to a user's profile in the XML document.
     *
     * @param username The username of the user.
     * @param name     The name of the project.
     * @param startDate The start date of the project.
     * @param endDate   The end date of the project.
     * @param budget    The budget of the project.
     */
    public void addProject(String username, String name, String startDate, String endDate, String budget) {
        Node userNode = null;
        for (int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
            if (rootElement.getChildNodes().item(i).getNodeName().equals("User")) {
                if (rootElement.getChildNodes().item(i).getFirstChild().getTextContent().equals(username)) {
                    userNode = rootElement.getChildNodes().item(i);
                    break;
                }
            }
        }
    
        if (userNode != null) {
            Element project = doc.createElement("Project");
            userNode.appendChild(project);
    
            Element nameElement = doc.createElement("Name");
            nameElement.appendChild(doc.createTextNode(name));
            project.appendChild(nameElement);
    
            Element startDateElement = doc.createElement("StartDate");
            startDateElement.appendChild(doc.createTextNode(startDate));
            project.appendChild(startDateElement);
    
            Element endDateElement = doc.createElement("EndDate");
            endDateElement.appendChild(doc.createTextNode(endDate));
            project.appendChild(endDateElement);
    
            Element budgetElement = doc.createElement("Budget");
            budgetElement.appendChild(doc.createTextNode(budget));
            project.appendChild(budgetElement);
        }
    }

    /**
     * Adds a new item to a user's project in the XML document.
     *
     * @param username The username of the user.
     * @param projectName The name of the project.
     * @param itemName The name of the item.
     * @param description The description of the item.
     * @param costPerUnit The cost per unit of the item.
     * @param quantity The quantity of the item.
     */
    public void addItem(String username, String projectName, String itemName, String description, String costPerUnit, String quantity) {
        Node userNode = null;
        Node projectNode = null;
    
        for (int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
            if (rootElement.getChildNodes().item(i).getNodeName().equals("User")) {
                if (rootElement.getChildNodes().item(i).getFirstChild().getTextContent().equals(username)) {
                    userNode = rootElement.getChildNodes().item(i);
                    break;
                }
            }
        }
    
        if (userNode != null) {
            for (int i = 0; i < userNode.getChildNodes().getLength(); i++) {
                if (userNode.getChildNodes().item(i).getNodeName().equals("Project")) {
                    if (userNode.getChildNodes().item(i).getFirstChild().getTextContent().equals(projectName)) {
                        projectNode = userNode.getChildNodes().item(i);
                        break;
                    }
                }
            }
        }
    
        if (projectNode != null) {
            Element item = doc.createElement("Item");
            projectNode.appendChild(item);
    
            Element itemNameElement = doc.createElement("ItemName");
            itemNameElement.appendChild(doc.createTextNode(itemName));
            item.appendChild(itemNameElement);
    
            Element descriptionElement = doc.createElement("Description");
            descriptionElement.appendChild(doc.createTextNode(description));
            item.appendChild(descriptionElement);
    
            Element costPerUnitElement = doc.createElement("CostPerUnit");
            costPerUnitElement.appendChild(doc.createTextNode(costPerUnit));
            item.appendChild(costPerUnitElement);
    
            Element quantityElement = doc.createElement("Quantity");
            quantityElement.appendChild(doc.createTextNode(quantity));
            item.appendChild(quantityElement);
        }
    }

    /**
     * Adds a file path to a user's project in the XML document.
     *
     * @param username The username of the user.
     * @param projectName The name of the project.
     * @param filePath The file path to add.
     */
    public void addFilePath(String username, String projectName, String filePath) {
        Node userNode = null;
        Node projectNode = null;

        for (int i = 0; i < rootElement.getChildNodes().getLength(); i++) {
            if (rootElement.getChildNodes().item(i).getNodeName().equals("User")) {
                if (rootElement.getChildNodes().item(i).getFirstChild().getTextContent().equals(username)) {
                    userNode = rootElement.getChildNodes().item(i);
                    break;
                }
            }
        }

        if (userNode != null) {
            for (int i = 0; i < userNode.getChildNodes().getLength(); i++) {
                if (userNode.getChildNodes().item(i).getNodeName().equals("Project")) {
                    if (userNode.getChildNodes().item(i).getFirstChild().getTextContent().equals(projectName)) {
                        projectNode = userNode.getChildNodes().item(i);
                        break;
                    }
                }
            }
        }

        if (projectNode != null) {
            Element filePathElement = doc.createElement("FilePath");
            filePathElement.appendChild(doc.createTextNode(filePath));
            projectNode.appendChild(filePathElement);
        }
    }
    
    /**
     * Saves the XML document to a file.
     */
    public void saveProfile(Profile profile) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(profile.getMyDoc());
            StreamResult result = new StreamResult(new File("data/"+ profile.getName() +".xml"));
    
            transformer.transform(source, result);
    
            System.out.println("File saved!");
    
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
    
