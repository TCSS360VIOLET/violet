package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The ProfileManager class handles creation and storage of user profiles
 * and their associated projects, project files/notes/details, and items.
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
     * This is a file holder that will store the file that is currently being modified.
     */
    private File file;

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
     * This loads a user's file for use in the ProfileManager.
     * If the file for that user does not exist, it will create it.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the user whoose file we are trying to load.
     */
    public void loadUserFile(String username) {
        try {
            // Define the file path
            file = new File("data/" + username + ".xml");
    
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    
            // Add this line to ignore whitespace in the XML content
            dbFactory.setIgnoringElementContentWhitespace(true);
    
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    
            if (file.exists()) {
                // If the file exists, parse the existing file
                doc = dBuilder.parse(file);
            } else {
                // If the file does not exist, create a new one
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("User");
                doc.appendChild(rootElement);
            }
    
            // Normalize the XML Structure
            doc.getDocumentElement().normalize();
            
            rootElement = doc.getDocumentElement();
            removeWhitespaceNodes(rootElement);
    
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This is a private method that will simply remove white space between lines,
     * for better visual formatting in the XML files.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param node The node in question in the XML file.
     */
    private void removeWhitespaceNodes(Node node) {
        NodeList nodeList = node.getChildNodes();
        Node childNode;
        for (int x = nodeList.getLength() - 1; x >= 0; x--) {
            childNode = nodeList.item(x);
            if (childNode.getNodeType() == Node.TEXT_NODE) {
                if (childNode.getTextContent().trim().isEmpty()) {
                    node.removeChild(childNode);
                }
            } else if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                removeWhitespaceNodes(childNode);
            }
        }
    }    

    /**
     * Creates an XML file for the specified user (username and email).
     * If one already exists for that user, throws an exception.
     *
     * @param username The username of the user.
     * @param email    The email of the user.
     */
    public void addProfile(String username, String email) throws IOException {
        String fileName = "data/" + username + ".xml";
        File file = new File(fileName);
        if(file.exists()) {
            loadUserFile(username);
        }
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("User");
            doc.appendChild(rootElement);
            
            Element usernameElement = doc.createElement("Username");
            usernameElement.appendChild(doc.createTextNode(username));
            rootElement.appendChild(usernameElement);
    
            Element emailElement = doc.createElement("Email");
            emailElement.appendChild(doc.createTextNode(email));
            rootElement.appendChild(emailElement);
            
            saveProfile(username);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new project to a the specified user's XML document.
     *@author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username The username of the user who owns this project.
     * @param projectName     The name of the project.
     * @param startDate The start date of the project.
     * @param endDate   The end date of the project.
     * @param budget    The budget of the project.
     */
    public void addProject(String username, String name, String startDate, String endDate, String budget) {
        loadUserFile(username);
    
        // Check if project already exists
        Node existingProjectNode = getProjectNode(name);
        if (existingProjectNode != null) {
            System.out.println("A project with this name already exists.");
            return;
        }
    
        Element project = doc.createElement("Project");
        rootElement.appendChild(project);
    
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
    
        saveProfile(username);
    }
    
    /**
     * Adds (to the user's XML) the specified item and item details into the specified project of the specified user.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The user that own's the project.
     * @param projectName   The project the item is being added to.
     * @param itemName  The name of the item being added to the project.
     * @param description   The description of the item being added.
     * @param costPerUnit   The cost per unit (single quantity) of the item being added.
     * @param quantity  The total quantity of the item being added.
     */
    public void addItem(String username, String projectName, String itemName, String description, String costPerUnit, String quantity) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
    
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
    
        saveProfile(username);
    }
    
    /**
     * Adds (to the user's XML) the specified file path to the specified project
     * of the specified user.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the project owner.
     * @param projectName   The project to add the file path to.
     * @param filePath  The file path being added.
     */
    public void addFilePath(String username, String projectName, String filePath) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
    
        if (projectNode != null) {
            Element filePathElement = doc.createElement("FilePath");
            filePathElement.appendChild(doc.createTextNode(filePath));
            projectNode.appendChild(filePathElement);
        }
    
        saveProfile(username);
    }
    
    /**
     * Private helper method to get the node of a project.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param projectName   The name of the specified project.
     * @return  The xml element node of the project.
     */
    private Node getProjectNode(String projectName) {
        Node projectNode = null;
        NodeList projectNodes = rootElement.getElementsByTagName("Project");
    
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Node node = projectNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element projectElement = (Element) node;
                String projectNameInElement = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                if (projectNameInElement.equals(projectName)) {
                    projectNode = node;
                    break;
                }
            }
        }
    
        return projectNode;
    }
    
    /**
     * Adds (to the user's XML) the specified notes into the specified project of the specified user.
     * If a "Notes" element already exists for the project, it will be replaced with the new notes.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the user.
     * @param projectName   The name of the project to add notes.
     * @param theNotes  The notes to be added.
     */
    public void addProjectNotes(String username, String projectName, String theNotes) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);

        if (projectNode != null) {
            NodeList nodeList = projectNode.getChildNodes();
            Node notesNode = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equals("Notes")) {
                    notesNode = currentNode;
                    break;
                }
            }

            if (notesNode != null) {
                notesNode.setTextContent(theNotes);
            } else {
                Element notesElement = doc.createElement("Notes");
                notesElement.appendChild(doc.createTextNode(theNotes));
                projectNode.appendChild(notesElement);
            }
        }

        saveProfile(username);
    }

    
    /**
     * Private helper method that saves the pending changes to the XML document of the specified user.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username The username of the user to save these changes to.
     */
    private void saveProfile(String username) {
        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            File file = new File("data/" + username + ".xml");
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            System.out.println(username + ".xml saved!");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * This getter method returns a List<String> of the names of all the projects in a specified
     * user's XML file.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username
     * @return  A List<String> of the names of all the projects of the given username's XML file.
     */
    public List<String> getProjects(String username) {
        loadUserFile(username);
        List<String> projectNames = new ArrayList<>();
    
        NodeList projectNodes = rootElement.getElementsByTagName("Project");
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Node node = projectNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element projectElement = (Element) node;
                String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                projectNames.add(projectName);
            }
        }
    
        return projectNames;
    }

    /**
     * This getter method returns a List<String> of all the items attached to the specified
     * project name and username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project we are reading the items from.
     * @return  A List<String> of all the item names of the specified project.
     */
    public List<String> getProjectItems(String username, String projectName) {
        loadUserFile(username);
        List<String> itemNames = new ArrayList<>();
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemName = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    itemNames.add(itemName);
                }
            }
        }
    
        return itemNames;
    }
    
    /**
     * This getter method returns a List<String> of all the file paths associated with
     * the specifed project and username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project we are reading the file paths from.
     * @return  A List<String> of all file paths found in the specified project.
     */
    public List<String> getProjectFilePaths(String username, String projectName) {
        loadUserFile(username);
        List<String> filePaths = new ArrayList<>();
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList filePathNodes = projectElement.getElementsByTagName("FilePath");
            for (int i = 0; i < filePathNodes.getLength(); i++) {
                Node node = filePathNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String filePath = node.getTextContent();
                    filePaths.add(filePath);
                }
            }
        }
    
        return filePaths;
    }
    
    /**
     * This getter method simply returns the start date of the specified project and username.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the project owner.
     * @param projectName   The name of the project that we are reading the start date of.
     * @return  A string of the start date of the project specified.
     */
    public String getProjectStartDate(String username, String projectName) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return projectElement.getElementsByTagName("StartDate").item(0).getTextContent();
        }
    
       // A project with that name was not found
       throw new NullPointerException("getProjectStartDate(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }

    /**
     * This getter method simply returns a string of the end date of the project and
     * username specified.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project we are reading the end date from.
     * @return  A string of the end date of the project specified.
     */
    public String getProjectEndDate(String username, String projectName) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return projectElement.getElementsByTagName("EndDate").item(0).getTextContent();
        }

       // A project with that name was not found
       throw new NullPointerException("getProjectEndDate(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }
    
    /**
     * This getter method simply returns a string of the specified project and username's budget.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username
     * @param projectName
     * @return
     */
    public float getProjectBudget(String username, String projectName) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            return Float.parseFloat(projectElement.getElementsByTagName("Budget").item(0).getTextContent());
        }
    
        // A project with that name was not found
        throw new NullPointerException("getProjectBudget(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }
    
    /**
     * This getter method returns a string of the description of the specified item, 
     * project name, and username.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project that contains the item.
     * @param itemName  The item we are reading the item description from.
     * @return  A string of the specified item's description.
     */
    public String getItemDescription(String username, String projectName, String itemName) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return itemElement.getElementsByTagName("Description").item(0).getTextContent();
                    }
                }
            }
        }
    
        throw new NullPointerException("getItemDescription(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }

    /**
     * This getter method returns the specified item's cost-per-unit using the given username, 
     * project name, and item name.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project that contains the item specified.
     * @param itemName  The name of the item we are reading the cost-per-unit from.
     * @return  A string of the cost-per-unit of the item specified.
     */
    public float getItemCostPerUnit(String username, String projectName, String itemName) {
        loadUserFile(username);
    
        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return Float.parseFloat(itemElement.getElementsByTagName("CostPerUnit").item(0).getTextContent());
                    }
                }
            }
        }
    
        throw new NullPointerException("getItemCostPerUnit(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }
        
    /**
     * This getter method returns an integer of the specified item's quantity given the username, 
     * project name, and item name.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the owner of the project.
     * @param projectName   The name of the project that contains the item.
     * @param itemName  The name of the item that we are reading the quantity of.
     * @return  An integer of the quantity of the specified item.
     */
    public int getItemQuantity(String username, String projectName, String itemName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);
        if (projectNode != null && projectNode.getNodeType() == Node.ELEMENT_NODE) {
            Element projectElement = (Element) projectNode;
            NodeList itemNodes = projectElement.getElementsByTagName("Item");
            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node node = itemNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) node;
                    String itemNameInElement = itemElement.getElementsByTagName("ItemName").item(0).getTextContent();
                    if (itemNameInElement.equals(itemName)) {
                        return Integer.parseInt(itemElement.getElementsByTagName("Quantity").item(0).getTextContent());
                    }
                }
            }
        }

        throw new NullPointerException("getItemQuantity(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }

    /**
     * Gets the notes of the specified project of the specified user.
     * This method throws a NullPointerException when the specified project does not exist under
     * the given username.
     * @author Nickolas Zahos (nzahos@uw.edu)
     * 
     * @param username  The username of the user.
     * @param projectName   The name of the project to get notes from.
     * @return  The notes of the project as a string.
     */
    public String getProjectNotes(String username, String projectName) {
        loadUserFile(username);

        Node projectNode = getProjectNode(projectName);

        if (projectNode != null) {
            NodeList nodeList = projectNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node currentNode = nodeList.item(i);
                if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equals("Notes")) {
                    return currentNode.getTextContent();
                }
            }
        }

        throw new NullPointerException("getProjectNotes(): No project by the name of '" + projectName + "' was found for username: '" + username);
    }

}